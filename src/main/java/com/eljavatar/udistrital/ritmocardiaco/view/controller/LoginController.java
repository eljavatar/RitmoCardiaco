package com.eljavatar.udistrital.ritmocardiaco.view.controller;

import java.io.Serializable;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.eljavatar.udistrital.ritmocardiaco.annotations.I18n;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.BusinessException;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.ExceptionUtils;
import com.eljavatar.udistrital.ritmocardiaco.logfactory.Log;
import com.eljavatar.udistrital.ritmocardiaco.logical.manager.SecurityManagerEjb;
import com.eljavatar.udistrital.ritmocardiaco.model.Persona;
import com.eljavatar.udistrital.ritmocardiaco.utils.Constantes;
import com.eljavatar.udistrital.ritmocardiaco.utils.UsuarioAplicacion;
import com.eljavatar.udistrital.ritmocardiaco.view.utils.FacesMessagesUtil;
import com.eljavatar.udistrital.ritmocardiaco.view.utils.FacesUtils;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Named
@ViewScoped
@URLMapping(id = "login", pattern = "/app/login", viewId = "/faces/login.xhtml")
public class LoginController implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5064662828493799265L;

    @Inject
    private FacesMessagesUtil facesMessagesUtil;

    @Inject
    private Log logger;

    @Inject
    private FacesUtils facesUtils;

    @Inject
    @I18n
    transient ResourceBundle resourceBundle;

    @Inject
    private SecurityManagerEjb securityManagerEjb;

    @Inject
    private ExceptionUtils exceptionUtils;

    private Persona usuario;

    public LoginController() {

    }

    @PostConstruct
    public void init() {
        this.usuario = new Persona();
    }

    public void iniciarSesion() {
        logger.info("Iniciamos sesion...");

        try {
            UsuarioAplicacion user = securityManagerEjb.searchUserByUsername(usuario.getUsername());

            if (user == null || !Objects.equals(user.getPassword(), usuario.getPassword())) {
                this.facesMessagesUtil.addError(resourceBundle.getString("login_mensaje_error_credenciales"));
                return;
            }

            HttpSession session = facesUtils.getHttpSession();
            session.setAttribute(Constantes.KEY_USER_SESSION_MAP, user);

            this.facesUtils.urlRedirect("../app/index");
        } catch (BusinessException ex) {
            this.facesMessagesUtil.addError(exceptionUtils.createMessage(resourceBundle, ex));
        }
    }

    public Persona getUsuario() {
        return usuario;
    }

    public void setUsuario(Persona usuario) {
        this.usuario = usuario;
    }

}
