package com.eljavatar.udistrital.ritmocardiaco.view.controller;

import java.io.Serializable;
import java.util.Objects;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.eljavatar.udistrital.ritmocardiaco.annotations.UserInfo;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.BusinessException;
import com.eljavatar.udistrital.ritmocardiaco.logical.manager.SecurityManagerEjb;
import com.eljavatar.udistrital.ritmocardiaco.utils.Constantes;
import com.eljavatar.udistrital.ritmocardiaco.utils.UsuarioAplicacion;
import com.eljavatar.udistrital.ritmocardiaco.view.utils.FacesUtils;

@Named
@SessionScoped
public class ServiciosController implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 7051485716184123757L;
	
	@Inject
    private FacesUtils facesUtils;
    
	@Inject
    private SecurityManagerEjb securityManagerEjb;
	
    @Inject
    @UserInfo
    private UsuarioAplicacion userApp;
    
    @Named("usuarioAplicacion")
    @Produces
    @SessionScoped
    @UserInfo
    public UsuarioAplicacion getUsuarioAplicacion(HttpSession httpSession) {
    	UsuarioAplicacion usuario = (UsuarioAplicacion) httpSession.getAttribute(Constantes.KEY_USER_SESSION_MAP);
        
        if (Objects.isNull(usuario)) {
        	try {
        		usuario = securityManagerEjb.searchUserByUsername("andres");
        	} catch (BusinessException ex) {
        		return new UsuarioAplicacion();
        	}
        	
//        	httpSession.invalidate();
//        	this.facesUtils.urlRedirect("../app/login");
//        	return new UsuarioAplicacion();
        }
        
        return usuario;
    }
    
}