package com.eljavatar.udistrital.ritmocardiaco.logical.manager;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.eljavatar.udistrital.ritmocardiaco.exceptions.BusinessException;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.ExceptionUtils;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.MensajeErrorEnum;
import com.eljavatar.udistrital.ritmocardiaco.logfactory.Log;
import com.eljavatar.udistrital.ritmocardiaco.utils.ObjectRestriction;
import com.eljavatar.udistrital.ritmocardiaco.utils.QueryUtils;
import com.eljavatar.udistrital.ritmocardiaco.utils.RelationalOperatorJpaEnum;

public class AbstractManagerEjb<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1450586487556385473L;

	@PersistenceContext(unitName = "ritmoCardiacoDS")
	private EntityManager em;
	
	private Class<T> entityClass;
    
    @Inject
    private ExceptionUtils exceptionUtils;
    
    @Inject
    private Log log;
    
//    public AbstractManagerEjb(Class<T> entityClass) {
//        this.entityClass = entityClass;
//    }
    
    public AbstractManagerEjb() {
    }
    
    public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
    
    public void insert(T entity) throws BusinessException {
        try {
            em.persist(entity);
        } catch (PersistenceException ex) {
            if (exceptionUtils.isConstraintViolation(ex)) {
                log.error("Error de duplicidad insertando la entidad", ex);
                throw exceptionUtils.createBusinessException(MensajeErrorEnum.EXCEPTION_CONSTRAINT_ENTITY);
            } else {
                log.error("Error de persistence insertando la entidad", ex);
            }
        } catch (Exception ex) {
            log.error("Error insertando la entidad", ex);
            throw exceptionUtils.createBusinessException(MensajeErrorEnum.EXCEPTION_INSERT_ENTITY);
        }
    }
    
    // @see http://piotrnowicki.com/2013/03/jpa-and-cmt-why-catching-persistence-exception-is-not-enough/
    //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public T update(T entity) throws BusinessException {
        try {
        	T entityMerge = em.merge(entity);
        	em.flush();
            return entityMerge;
        } catch (Exception ex) {
            log.error("Error actualizando la entidad", ex);
            throw exceptionUtils.createBusinessException(MensajeErrorEnum.EXCEPTION_UPDATE_ENTITY);
            //throw new BusinessException(CodigoMensajeErrorEnum.EXCEPTION_UPDATE_ENTITY, ex);
        }
    }
    
    public void delete(T entity) throws BusinessException {
        try {
            em.remove(entity);
        } catch (Exception ex) {
            log.error("Error eliminando la entidad", ex);
            throw exceptionUtils.createBusinessException(MensajeErrorEnum.EXCEPTION_DELETE_ENTITY);
            //throw new BusinessException(CodigoMensajeErrorEnum.EXCEPTION_DELETE_ENTITY, ex);
        }
    }
    
    public T findById(Object id) throws BusinessException {
        try {
            return em.find(entityClass, id);
        } catch (Exception ex) {
            log.error("Error buscando la entidad por su Id", ex);
            throw exceptionUtils.createBusinessException(MensajeErrorEnum.EXCEPTION_FINDBYID_ENTITY);
            //throw new BusinessException(CodigoMensajeErrorEnum.EXCEPTION_FINDBYID_ENTITY, ex);
        }
    }
    
    public List<T> findAll() throws BusinessException {
        try {
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
            criteriaQuery.select(criteriaQuery.from(entityClass));
            return em.createQuery(criteriaQuery).getResultList();
        } catch (Exception ex) {
            log.error("Error cargando la lista de entidades findAll", ex);
            throw exceptionUtils.createBusinessException(MensajeErrorEnum.EXCEPTION_OBTENIENDO_LIST_ENTITY);
            //throw new BusinessException(CodigoMensajeErrorEnum.EXCEPTION_OBTENIENDO_LIST_ENTITY, ex);
        }
    }
    
    public List<T> findRange(Integer firstResult, Integer maxResults) throws BusinessException {
        try {
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
            criteriaQuery.select(criteriaQuery.from(entityClass));
            TypedQuery<T> query = em.createQuery(criteriaQuery);
            query.setMaxResults(maxResults);
            query.setFirstResult(firstResult);
            return query.getResultList();
        } catch (Exception ex) {
            log.error("Error cargando la lista de entidades findRange", ex);
            throw exceptionUtils.createBusinessException(MensajeErrorEnum.EXCEPTION_OBTENIENDO_LIST_ENTITY);
            //throw new BusinessException(CodigoMensajeErrorEnum.EXCEPTION_OBTENIENDO_LIST_ENTITY, ex);
        }
    }
    
    public Long count() throws BusinessException {
        try {
        	CriteriaQuery<Long> criteriaQuery = em.getCriteriaBuilder().createQuery(Long.class);
            Root<T> root = criteriaQuery.from(entityClass);
            criteriaQuery.select(em.getCriteriaBuilder().count(root));
            Query query = em.createQuery(criteriaQuery);
            return (Long) query.getSingleResult();
        } catch (Exception ex) {
            log.error("Error obteniendo conteo total de entidades", ex);
            throw exceptionUtils.createBusinessException(MensajeErrorEnum.EXCEPTION_SELECT_COUNT_ENTITY);
            //throw new BusinessException(CodigoMensajeErrorEnum.EXCEPTION_SELECT_COUNT_ENTITY, ex);
        }
    }
    
    public List<T> getListData(List<ObjectRestriction> listRestrictionsToAdd) throws BusinessException {
        return getListData(listRestrictionsToAdd, null, null);
    }
    
    // http://gnuteam.blogspot.com.co/2012/03/jpa-criteriabuilder-conjunction.html
    // http://www.objectdb.com/java/jpa/query/jpql/from
    // https://en.wikibooks.org/wiki/Java_Persistence/Querying#Joining.2C_querying_on_a_OneToMany_relationship
    public List<T> getListData(List<ObjectRestriction> listRestrictionsToAdd, Integer firstResult, Integer maxResults) throws BusinessException {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
            Root<T> root = criteriaQuery.from(entityClass);
            
            // Creamos un predicado conjunction, que vienen a ser un tipo de restriccion en el query
            Predicate restrictions = criteriaBuilder.conjunction();
            
            buildRestrictions(listRestrictionsToAdd, restrictions, criteriaBuilder, root);
            
            if (restrictions.getExpressions().size() > 0) {
                criteriaQuery.where(restrictions);
            }
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
            
            TypedQuery<T> queryDataList = em.createQuery(criteriaQuery);
            if (maxResults != null) {
                queryDataList.setMaxResults(maxResults);
            }
            if (firstResult != null) {
                queryDataList.setFirstResult(firstResult);
            }
            return queryDataList.getResultList();
        } catch (Exception ex) {
            log.error("Error cargando la lista de entidades getListData", ex);
            throw new BusinessException(MensajeErrorEnum.EXCEPTION_OBTENIENDO_LIST_ENTITY);
        }
    }
    
    public Long getCountData(List<ObjectRestriction> listRestrictionsToAdd) throws BusinessException {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<T> root = criteriaQuery.from(entityClass);
            
            // Creamos un predicado conjunction, que vienen a ser un tipo de restriccion en el query
            Predicate restrictions = criteriaBuilder.conjunction();
            
            buildRestrictions(listRestrictionsToAdd, restrictions, criteriaBuilder, root);
            
            criteriaQuery.select(criteriaBuilder.count(root));
            if (restrictions.getExpressions().size() > 0) {
                criteriaQuery.where(restrictions);
            }

            Query query = em.createQuery(criteriaQuery);
            return (Long) query.getSingleResult();
        } catch (Exception ex) {
            log.error("Error obteniendo count de entidades getCountData", ex);
            throw new BusinessException(MensajeErrorEnum.EXCEPTION_SELECT_COUNT_ENTITY);
        }
    }
    
    @SuppressWarnings({"unchecked"})
	private void buildRestrictions(List<ObjectRestriction> listRestrictionsToAdd, Predicate restrictions, CriteriaBuilder criteriaBuilder, Root<T> root) {
    	if (listRestrictionsToAdd != null) {
    		listRestrictionsToAdd.stream().forEach(object -> {
    			if (object.getParameterName().contains(".")) {
                    String attributeName = object.getParameterName();
                    String[] splitAttributeName = attributeName.split("\\.");
                    
                    if (object.getValues() != null && object.getValues()[0] != null) {
                    	Join join = getJoinFromAttribute(root, splitAttributeName);
                    	if (Objects.equals(RelationalOperatorJpaEnum.EQUAL, object.getOperator())) {
                        	restrictions.getExpressions().add(criteriaBuilder.equal(join.get(splitAttributeName[splitAttributeName.length - 1]), object.getValues()[0]));
                        } else if (Objects.equals(RelationalOperatorJpaEnum.LIKE, object.getOperator())) {
                        	restrictions.getExpressions().add(criteriaBuilder.like(criteriaBuilder.lower(join.get(splitAttributeName[splitAttributeName.length - 1]).as(String.class)), QueryUtils.getStringLike(object.getValues()[0].toString().toLowerCase())));
                        } else if (Objects.equals(RelationalOperatorJpaEnum.BETWEEN, object.getOperator())) {
                        	Class<? extends Comparable> clazz = (Class<? extends Comparable>) join.get(splitAttributeName[splitAttributeName.length - 1]).getJavaType();
                        	restrictions.getExpressions().add(criteriaBuilder.between(join.get(splitAttributeName[splitAttributeName.length - 1]).as(clazz), clazz.cast(object.getValues()[0]), clazz.cast(object.getValues()[1])));
                        } 
                    }
                    
                    if (Objects.equals(RelationalOperatorJpaEnum.IS_NOT_NULL, object.getOperator())) {
                    	Join join = getJoinFromAttribute(root, splitAttributeName);
                    	restrictions.getExpressions().add(criteriaBuilder.isNotNull(join.get(object.getParameterName())));
                    } else if (Objects.equals(RelationalOperatorJpaEnum.IS_NULL, object.getOperator())) {
                    	Join join = getJoinFromAttribute(root, splitAttributeName);
                    	restrictions.getExpressions().add(criteriaBuilder.isNull(join.get(object.getParameterName())));
                    }
                } else {
                	if (object.getValues() != null && object.getValues()[0] != null) {
                		if (Objects.equals(RelationalOperatorJpaEnum.EQUAL, object.getOperator())) {
                    		restrictions.getExpressions().add(criteriaBuilder.equal(root.get(object.getParameterName()), object.getValues()[0]));
                        } else if (Objects.equals(RelationalOperatorJpaEnum.LIKE, object.getOperator())) {
                        	restrictions.getExpressions().add(criteriaBuilder.like(criteriaBuilder.lower(root.get(object.getParameterName()).as(String.class)), QueryUtils.getStringLike(object.getValues()[0].toString().toLowerCase())));
                        } else if (Objects.equals(RelationalOperatorJpaEnum.BETWEEN, object.getOperator())) {
                        	Class<? extends Comparable> clazz = (Class<? extends Comparable>) root.get(object.getParameterName()).getJavaType();
                        	restrictions.getExpressions().add(criteriaBuilder.between(root.get(object.getParameterName()).as(clazz), clazz.cast(object.getValues()[0]), clazz.cast(object.getValues()[1])));
                        } else if (Objects.equals(RelationalOperatorJpaEnum.NOT_EQUAL, object.getOperator())) {
                        	restrictions.getExpressions().add(criteriaBuilder.notEqual(root.get(object.getParameterName()), object.getValues()[0]));
                        }
                	}
                	
                	if (Objects.equals(RelationalOperatorJpaEnum.IS_NOT_NULL, object.getOperator())) {
                		restrictions.getExpressions().add(criteriaBuilder.isNotNull(root.get(object.getParameterName())));
                    } else if (Objects.equals(RelationalOperatorJpaEnum.IS_NULL, object.getOperator())) {
                    	restrictions.getExpressions().add(criteriaBuilder.isNull(root.get(object.getParameterName())));
                    }
                }
    		});
    	}
    }
    
    private Join getJoinFromAttribute(Root<T> root, String[] splitAttributeName) {
        Join join = root.join(splitAttributeName[0]);
        if (splitAttributeName.length > 2) {
            for (int i = 1; i < (splitAttributeName.length - 1); i++) {
                join = join.join(splitAttributeName[i]);
            }
        }
        return join;
    }
    
}
