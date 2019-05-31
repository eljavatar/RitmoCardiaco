package com.eljavatar.udistrital.ritmocardiaco.view.utils;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class FacesMessagesUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8735136173412394721L;

	@Inject
    private FacesContext facesContext;
    
    private void addMessage(Severity severity, String sumary, String detail) {
        facesContext.addMessage(null, new FacesMessage(severity, sumary, detail));
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, sumary, detail));
        //RequestContext.getCurrentInstance().update(Arrays.asList(":messages"));
    }
    
    public void addInfo(String sumary) {
        this.addInfo(sumary, "");
    }
    
    public void addInfo(String sumary, String detail) {
        this.addMessage(FacesMessage.SEVERITY_INFO, sumary, detail);
    }
    
    public void addWarn(String sumary) {
        this.addWarn(sumary, "");
    }
    
    public void addWarn(String sumary, String detail) {
        this.addMessage(FacesMessage.SEVERITY_WARN, sumary, detail);
    }
    
    public void addError(String sumary) {
        this.addError(sumary, "");
    }
    
    public void addError(String sumary, String detail) {
        this.addMessage(FacesMessage.SEVERITY_ERROR, sumary, detail);
    }
    
    public void addFatal(String sumary) {
        this.addFatal(sumary, "");
    }
    
    public void addFatal(String sumary, String detail) {
        this.addMessage(FacesMessage.SEVERITY_FATAL, sumary, detail);
    }
    
}
