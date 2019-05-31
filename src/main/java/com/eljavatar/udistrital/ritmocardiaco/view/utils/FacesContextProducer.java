package com.eljavatar.udistrital.ritmocardiaco.view.utils;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class FacesContextProducer {

	@Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) {
            throw new IllegalArgumentException("Faces context inyectado en un punto errado.");
        }
        return facesContext;
    }
	
}
