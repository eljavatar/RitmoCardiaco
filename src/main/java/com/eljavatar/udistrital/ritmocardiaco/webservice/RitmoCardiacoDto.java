/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljavatar.udistrital.ritmocardiaco.webservice;

import java.io.Serializable;

/**
 *
 * @author Andres
 */
public class RitmoCardiacoDto implements Serializable {

    private Integer id;
    private Integer medicion;
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMedicion() {
        return medicion;
    }

    public void setMedicion(Integer medicion) {
        this.medicion = medicion;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
