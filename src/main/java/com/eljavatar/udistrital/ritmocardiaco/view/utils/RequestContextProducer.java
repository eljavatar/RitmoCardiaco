package com.eljavatar.udistrital.ritmocardiaco.view.utils;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import org.primefaces.context.RequestContext;

public class RequestContextProducer {

	@Produces
    @RequestScoped
    public RequestContext getRequestContext() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (requestContext == null) {
            throw new IllegalArgumentException("Request context inyectado en un punto errado.");
        }
        return requestContext;
    }
	
}
