/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljavatar.udistrital.ritmocardiaco.view.utils;

import com.eljavatar.udistrital.ritmocardiaco.annotations.UserInfo;
import com.eljavatar.udistrital.ritmocardiaco.utils.Constantes;
import com.eljavatar.udistrital.ritmocardiaco.utils.UsuarioAplicacion;
import java.util.Objects;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andres
 */
public class SecurityProducer {
    
    @Inject
    private FacesUtils facesUtils;
    
    @Named("usuarioAplicacion")
    @Produces
    @SessionScoped
    @UserInfo
    public UsuarioAplicacion getUsuarioAplicacion(HttpSession httpSession) {
    	UsuarioAplicacion usuario = (UsuarioAplicacion) httpSession.getAttribute(Constantes.KEY_USER_SESSION_MAP);
        
        if (Objects.isNull(usuario)) {
            httpSession.invalidate();
            this.facesUtils.urlRedirect("../app/login");
            return new UsuarioAplicacion();
        }
        
        return usuario;
    }
    
}
