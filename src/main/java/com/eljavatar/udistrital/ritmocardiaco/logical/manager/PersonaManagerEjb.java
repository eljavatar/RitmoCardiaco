package com.eljavatar.udistrital.ritmocardiaco.logical.manager;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.eljavatar.udistrital.ritmocardiaco.exceptions.BusinessException;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.MensajeErrorEnum;
import com.eljavatar.udistrital.ritmocardiaco.model.Persona;

@Stateless
public class PersonaManagerEjb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -677020628892571779L;
	
//	@PersistenceContext(unitName = "ritmoCardiacoDS")
//    private EntityManager em;
	
	@Inject
	private AbstractManagerEjb<Persona> managerPersona;
	
	public void registrarPersona(Persona persona) throws BusinessException {
		managerPersona.setEntityClass(Persona.class);
		try {
			managerPersona.insert(persona);
		} catch (BusinessException ex) {
			throw new BusinessException(MensajeErrorEnum.EXCEPTION_INSERT_ENTITY);
		}
	}

}
