package com.eljavatar.udistrital.ritmocardiaco.view.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.eljavatar.udistrital.ritmocardiaco.annotations.I18n;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.BusinessException;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.ExceptionUtils;
import com.eljavatar.udistrital.ritmocardiaco.logical.manager.PersonaManagerEjb;
import com.eljavatar.udistrital.ritmocardiaco.model.Persona;
import com.eljavatar.udistrital.ritmocardiaco.model.SexoEnum;
import com.eljavatar.udistrital.ritmocardiaco.view.utils.FacesMessagesUtil;
import com.eljavatar.udistrital.ritmocardiaco.view.utils.FacesUtils;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Named
@ViewScoped
@URLMapping(id = "registro", pattern = "/app/registro", viewId = "/faces/registro.xhtml")
public class RegistroController implements Serializable {

    private static final long serialVersionUID = -1964410862426978957L;

    @Inject
    private PersonaManagerEjb personaManagerEjb;

    @Inject
    private FacesMessagesUtil facesMessagesUtil;

    @Inject
    @I18n
    transient ResourceBundle resourceBundle;

    @Inject
    private ExceptionUtils exceptionUtils;

    @Inject
    private FacesUtils facesUtils;

    private Persona persona;

    @PostConstruct
    public void init() {
        this.persona = new Persona();
    }

    public void registrar() {
        try {
            personaManagerEjb.registrarPersona(persona);
            persona = new Persona();
            PrimeFaces.current().executeScript("PF('confirmRegistro').show()");
        } catch (BusinessException ex) {
            this.facesMessagesUtil.addError(exceptionUtils.createMessage(resourceBundle, ex));
        }
    }

    public void redirectToLogin() {
        this.facesUtils.urlRedirect("../app/login");
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<SexoEnum> getListSexo() {
        return Arrays.asList(SexoEnum.values());
    }

}
