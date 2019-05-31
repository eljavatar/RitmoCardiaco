package com.eljavatar.udistrital.ritmocardiaco.exceptions;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ExceptionUtils implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -2057285377557697996L;

	public BusinessException createBusinessException(Enum codigo, String... params) {
        return new BusinessException(codigo, params);
    }
    
    public SystemException createSystemException(Enum codigo, String... params) {
        return new SystemException(codigo, params);
    }
    
    public SystemException createSystemErrorException() {
        return new SystemException(MensajeErrorEnum.SYSTEM_ERROR);
    }
    
    public String createSystemErrorMessage(ResourceBundle resourceBundle) {
        return resourceBundle.getString(MensajeErrorEnum.SYSTEM_ERROR.name().toLowerCase());
    }
    
    public String createMessage(ResourceBundle resourceBundle, IException iException) {
        if (iException.getCodigoError() instanceof Enum) {
            return this.createMessage(resourceBundle, iException.getCodigoError(), iException.getParams());
        } else {
            throw new IllegalArgumentException("Excepcion a procesar debe tener codigos de tipo CodigoMensajeErrorEnum");
        }
    }
    
    public String createMessage(ResourceBundle resourceBundle, Enum codigoError, String... params) {
        try {
            String e = resourceBundle.getString(codigoError.name().toLowerCase());
            if (params != null) {
                e = String.format(e, (Object[]) params);
            }
            return e;
        } catch (MissingResourceException ex) {
            String msg = resourceBundle.getString("recurso_no_encontrado");
            return String.format(msg, new Object[]{codigoError});
        }
    }
    
    public boolean isExceptionOfType(Exception ex, Class clazz) {
        Throwable t = ex.getCause();
        while ((t != null) && !(clazz.isInstance(t))) {
            t = t.getCause();
        }
        return clazz.isInstance(t);
    }
    
    public boolean isConstraintViolation(Exception ex) {
        Throwable t = ex.getCause();
        while ((t != null) && !(t instanceof org.hibernate.exception.ConstraintViolationException)) {
            t = t.getCause();
        }
        return t instanceof org.hibernate.exception.ConstraintViolationException;
    }
    
}
