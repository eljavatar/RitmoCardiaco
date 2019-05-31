package com.eljavatar.udistrital.ritmocardiaco.exceptions;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

    private final ExceptionHandlerFactory parent;

    public CustomExceptionHandlerFactory(final ExceptionHandlerFactory parent) {
        this.parent = parent;
        org.primefaces.application.exceptionhandler.PrimeExceptionHandlerFactory e;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new CustomExceptionHandler(this.parent.getExceptionHandler());
    }

}