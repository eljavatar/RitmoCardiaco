package com.eljavatar.udistrital.ritmocardiaco.logical.manager;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.eljavatar.udistrital.ritmocardiaco.exceptions.BusinessException;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.MensajeErrorEnum;
import com.eljavatar.udistrital.ritmocardiaco.logfactory.Log;
import com.eljavatar.udistrital.ritmocardiaco.utils.UsuarioAplicacion;

@Stateless
public class SecurityManagerEjb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5182166128917279364L;
	
	@PersistenceContext(unitName = "ritmoCardiacoDS")
    private EntityManager em;
	
	@Inject
	private Log log;
    
    public UsuarioAplicacion searchUserByUsername(String username) throws BusinessException {
        // Obtenemos el usuario
        StringBuilder sqlUsuario = new StringBuilder();
        sqlUsuario.append("SELECT new com.eljavatar.udistrital.ritmocardiaco.utils.UsuarioAplicacion(");
        sqlUsuario.append("    p.id,");
        sqlUsuario.append("    p.username,");
        sqlUsuario.append("    p.password,");
        sqlUsuario.append("    p.nombres,");
        sqlUsuario.append("    p.apellidos,");
        sqlUsuario.append("    CONCAT(p.nombres, ' ', p.apellidos)");
        sqlUsuario.append(") FROM ");
        sqlUsuario.append("    Persona p ");
        sqlUsuario.append("WHERE ");
        sqlUsuario.append("    p.username = :username");
        
        try {
        	TypedQuery<UsuarioAplicacion> query = em.createQuery(sqlUsuario.toString(), UsuarioAplicacion.class)
                    .setParameter("username", username);
            
            UsuarioAplicacion usuario = query.getSingleResult();
            
            return usuario;
        } catch (NoResultException ex) {
        	return null;
        } catch (PersistenceException ex) {
        	log.error("PersistenceException buscando usuario", ex);
        	throw new BusinessException(MensajeErrorEnum.EXCEPTION_SEARCH_USER_BY_USERNAME);
        }
    }

}
