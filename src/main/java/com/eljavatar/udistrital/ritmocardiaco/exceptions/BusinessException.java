package com.eljavatar.udistrital.ritmocardiaco.exceptions;

import java.text.MessageFormat;
import java.util.Objects;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class BusinessException extends Exception implements IException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4757728946817296374L;
	
	private Enum codigoError;
    private String[] params;
    private String mensaje;

    public BusinessException(String message, Throwable cause, String[] params) {
        super(cause);
        MessageFormat messageFormat = new MessageFormat(message);
        this.mensaje = messageFormat.format(params);
        this.params = params;
    }

    public BusinessException(Enum codigoError) {
        super(codigoError.name());
        Objects.requireNonNull(codigoError);
        this.codigoError = codigoError;
    }
    
    public BusinessException(Enum codigoError, String[] params) {
        this(codigoError);
        this.params = params;
    }

    public BusinessException(Enum codigoError, Throwable cause) {
        super(cause);
        Objects.requireNonNull(codigoError);
        this.codigoError = codigoError;
    }
    
    public BusinessException(Enum codigoError, String message, Throwable cause) {
        super(message, cause);
        this.mensaje = message;
        this.codigoError = codigoError;
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.mensaje = message;
    }

    public BusinessException(String message) {
        super(message);
        this.mensaje = message;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.mensaje = cause.getMessage();
    }
    
    public BusinessException() {
        super();
    }

    @Override
    public String[] getParams() {
        return params;
    }

    @Override
    public Enum getCodigoError() {
        return codigoError;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }
    
}
