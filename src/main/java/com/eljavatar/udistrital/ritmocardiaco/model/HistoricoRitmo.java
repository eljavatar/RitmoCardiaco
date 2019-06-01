/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljavatar.udistrital.ritmocardiaco.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "historico_ritmo", schema = "ritmo_cardiaco")
@DynamicInsert
public class HistoricoRitmo implements Serializable {
    
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
    
    @Column(name = "valor_medicion")
    private Integer valorMedicion;
    
    @Column(name = "fecha_medicion")
    @ColumnDefault("now()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMedicion;
    
    @Column(name = "genero_alerta_bradicardia")
    @ColumnDefault("false")
    private Boolean generoAlertaBradicardia;
    
    @Column(name = "genero_alerta_taquicardia")
    @ColumnDefault("false")
    private Boolean generoAlertaTaquicardia;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Integer getValorMedicion() {
        return valorMedicion;
    }

    public void setValorMedicion(Integer valorMedicion) {
        this.valorMedicion = valorMedicion;
    }

    public Date getFechaMedicion() {
        return fechaMedicion;
    }

    public void setFechaMedicion(Date fechaMedicion) {
        this.fechaMedicion = fechaMedicion;
    }

    public Boolean getGeneroAlertaBradicardia() {
        return generoAlertaBradicardia;
    }

    public void setGeneroAlertaBradicardia(Boolean generoAlertaBradicardia) {
        this.generoAlertaBradicardia = generoAlertaBradicardia;
    }

    public Boolean getGeneroAlertaTaquicardia() {
        return generoAlertaTaquicardia;
    }

    public void setGeneroAlertaTaquicardia(Boolean generoAlertaTaquicardia) {
        this.generoAlertaTaquicardia = generoAlertaTaquicardia;
    }
    
}
