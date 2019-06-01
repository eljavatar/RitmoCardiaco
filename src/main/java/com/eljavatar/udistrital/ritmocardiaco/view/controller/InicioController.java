package com.eljavatar.udistrital.ritmocardiaco.view.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.eljavatar.udistrital.ritmocardiaco.annotations.UserInfo;
import com.eljavatar.udistrital.ritmocardiaco.utils.UsuarioAplicacion;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Named
@ViewScoped
@URLMapping(id = "index", pattern = "/app/index", viewId = "/faces/index.xhtml")
public class InicioController implements Serializable {
    
    private static final long serialVersionUID = 5154840181847946088L;

    private String welcome;
    
    @UserInfo
    @Inject
    private UsuarioAplicacion usuario;

    public InicioController() {
        
    }
    
    @PostConstruct
    public void init() {
        this.welcome = "Bienvenido a Ritmo Cardiaco " + usuario.getNombreCompleto();
    }
    
    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public UsuarioAplicacion getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioAplicacion usuario) {
        this.usuario = usuario;
    }
    
}
