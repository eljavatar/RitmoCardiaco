package com.eljavatar.udistrital.ritmocardiaco.view.utils;

import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.eljavatar.udistrital.ritmocardiaco.annotations.I18n;

public class ResourceBundleProducer {

	@Inject
    private FacesContext facesContext;
    
    @Produces
    @I18n
    public ResourceBundle getResourceBundleI18n(InjectionPoint injectionPoint) {
        String resourceName = ((I18n) injectionPoint.getAnnotated().getAnnotation(I18n.class)).value();
        return facesContext.getApplication().evaluateExpressionGet(facesContext, "#{" + resourceName + "}", ResourceBundle.class);
    }
    
}
