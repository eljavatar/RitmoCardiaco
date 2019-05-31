package com.eljavatar.udistrital.ritmocardiaco.view.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.eljavatar.udistrital.ritmocardiaco.utils.Constantes;
import com.eljavatar.udistrital.ritmocardiaco.view.utils.FacesUtils;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Named
@ViewScoped
@URLMapping(id = "logout", pattern = "/app/logout", viewId = "/faces/logout.xhtml")
public class LogoutController implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -1023751459740273971L;
	
	@Inject
    private FacesUtils facesUtils;
    
    public LogoutController() {
        
    }
    
    @PostConstruct
    public void init() {
        //
    }
    
    public void cerrarSesion() {
    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(Constantes.KEY_USER_SESSION_MAP);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        this.facesUtils.urlRedirect("../app/login");
    }
    
}
