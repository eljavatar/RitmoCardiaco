/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljavatar.udistrital.ritmocardiaco.webservice;

import com.eljavatar.udistrital.ritmocardiaco.exceptions.BusinessException;
import com.eljavatar.udistrital.ritmocardiaco.logical.manager.HistoricoRitmoManagerEjb;
import com.eljavatar.udistrital.ritmocardiaco.logical.manager.PersonaManagerEjb;
import com.eljavatar.udistrital.ritmocardiaco.logical.manager.SecurityManagerEjb;
import com.eljavatar.udistrital.ritmocardiaco.model.HistoricoRitmo;
import com.eljavatar.udistrital.ritmocardiaco.model.Persona;
import com.eljavatar.udistrital.ritmocardiaco.utils.UsuarioAplicacion;
import java.util.Objects;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

/**
 * REST Web Service
 *
 * @author Andres
 */
@Path("rc")
@RequestScoped
public class RitmoCardiacoWS {

    @Context
    private UriInfo context;
    
    @Inject
    private SecurityManagerEjb securityManagerEjb;
    
    @Inject
    private PersonaManagerEjb personaManagerEjb;
    
    @Inject
    private HistoricoRitmoManagerEjb historicoRitmoManagerEjb;
    
    @Inject
    private RitmoCardiacoControl ritmoCardiacoControl;

    /**
     * Creates a new instance of RitmoCardiacoWS
     */
    public RitmoCardiacoWS() {
    }

    /**
     * Método para iniciar sesión desde el dispositivo
     * @param login
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(LoginDto login) {
        if (login == null || StringUtils.isEmpty(login.getUsername()) || StringUtils.isEmpty(login.getPassword())) {
            login = new LoginDto(); // En caso de que login venga null
            login.setLogueado(false);
            login.setMessage("Debe completar los datos para Iniciar Sesión");
            return Response.ok(login).build();
        }
        
        login.setLogueado(false);
        try {
            UsuarioAplicacion user = securityManagerEjb.searchUserByUsername(login.getUsername());

            if (user == null || !Objects.equals(user.getPassword(), login.getPassword())) {
                login.setMessage("Error de credenciales");
            } else {
                login.setId(user.getId());
                login.setLogueado(true);
                login.setMessage("Acceso Concedido");
            }
            
            //return Response.ok(login).build();
        } catch (BusinessException ex) {
            login.setMessage("No ha sido posible Loguearse, Intente en otro momento");
        }
        
        return Response.ok(login).build();
    }

    /**
     * POSY method for creating an instance of RitmoCardiacoWS
     * @param ritmo representation for the resource
     * @return 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarMedicion(RitmoCardiacoDto ritmo) {
        if (ritmo == null || ritmo.getId() == null || ritmo.getMedicion() == null) {
            ritmo = new RitmoCardiacoDto(); // En caso de que ritmo venga
            ritmo.setMessage("Debe enviar los datos completos de la medición");
            return Response.ok(ritmo).build();
        }
        
        System.out.println("Id: " + ritmo.getId());
        System.out.println("Medicion: " + ritmo.getMedicion());
        ritmo.setMessage("Alerta de Taquicardia / Bradicardia");
        
        Persona persona;
        try {
            persona = personaManagerEjb.buscarPersonaById(ritmo.getId());
        } catch (BusinessException ex) {
            ritmo.setMessage("No se ha encontrado un paciente con este Identificador");
            return Response.ok(ritmo).build();
        }
        
        HistoricoRitmo historicoRitmo = ritmoCardiacoControl.getHistoricoRitmoConAlerta(persona, ritmo.getMedicion());
        
        try {
            historicoRitmoManagerEjb.guardarHistorico(historicoRitmo);
            
            if (historicoRitmo.getGeneroAlertaBradicardia()) {
                ritmo.setMessage("Cuidado. Alerta de Bradicardia");
            } else if (historicoRitmo.getGeneroAlertaTaquicardia()) {
                ritmo.setMessage("Cuidado. Alerta de Taquicardia");
            } else {
                ritmo.setMessage("Ritmo Cardiaco Normal");
            }
        } catch (BusinessException ex) {
            ritmo.setMessage("No ha sido posible guardar los datos de la medición del ritmo");
        }
        
        
        return Response.ok(ritmo).build();
    }
    
}
