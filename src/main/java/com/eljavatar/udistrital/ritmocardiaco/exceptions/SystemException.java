package com.eljavatar.udistrital.ritmocardiaco.exceptions;

import java.text.MessageFormat;
import java.util.Objects;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class SystemException extends RuntimeException implements IException {

    /**
     *
     */
    private static final long serialVersionUID = -1971641002239458907L;

    private Enum codigoError;
    private String[] params;
    private String mensaje;

    public SystemException(String message, Throwable cause, String[] params) {
        super(cause);
        MessageFormat messageFormat = new MessageFormat(message);
        this.mensaje = messageFormat.format(params);
        this.params = params;
    }

    public SystemException(Enum codigoError) {
        super(codigoError.name());
        Objects.requireNonNull(codigoError);
        this.codigoError = codigoError;
    }

    public SystemException(Enum codigoError, String[] params) {
        this(codigoError);
        this.params = params;
    }

    public SystemException(Enum codigoError, Throwable cause) {
        super(cause);
        Objects.requireNonNull(codigoError);
        this.codigoError = codigoError;
    }

    public SystemException(Enum codigoError, String message, Throwable cause) {
        super(message, cause);
        this.mensaje = message;
        this.codigoError = codigoError;
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
        this.mensaje = message;
    }

    public SystemException(String message) {
        super(message);
        this.mensaje = message;
    }

    public SystemException(Throwable cause) {
        super(cause);
        this.mensaje = cause.getMessage();
    }

    public SystemException() {
        super();
    }

    @Override
    public String[] getParams() {
        return this.params;
    }

    @Override
    public Enum getCodigoError() {
        return this.codigoError;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }

}
