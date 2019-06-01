/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljavatar.udistrital.ritmocardiaco.webservice;

import com.eljavatar.udistrital.ritmocardiaco.model.HistoricoRitmo;
import com.eljavatar.udistrital.ritmocardiaco.model.Persona;
import com.eljavatar.udistrital.ritmocardiaco.model.SexoEnum;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Andres
 */
public class RitmoCardiacoControl {
    
    public HistoricoRitmo getHistoricoRitmoConAlerta(Persona persona, Integer medicionRitmo) {
        LocalDate now = LocalDate.now();
        LocalDate ldFechaNac = new java.sql.Date(persona.getFechaNacimiento().getTime()).toLocalDate();
        Period period = Period.between(ldFechaNac, now);
        Integer edad = period.getYears();
        
        HistoricoRitmo historicoRitmo = new HistoricoRitmo();
        historicoRitmo.setValorMedicion(medicionRitmo);
        historicoRitmo.setFechaMedicion(new Date());
        historicoRitmo.setPersona(persona);
        historicoRitmo.setGeneroAlertaBradicardia(false);
        historicoRitmo.setGeneroAlertaTaquicardia(false);
        
        if (edad <= 2) {
            calcularRiesgoArritmia(historicoRitmo, medicionRitmo, 80, 140);
        } else if (edad >= 3 && edad <= 7) {
            calcularRiesgoArritmia(historicoRitmo, medicionRitmo, 80, 115);
        } else if (edad >= 8 && edad <= 17) {
            calcularRiesgoArritmia(historicoRitmo, medicionRitmo, 80, 110);
        } else if (edad >= 18 && edad <= 25) {
            calcularRiesgoArritmiaPorSexo(persona, historicoRitmo, medicionRitmo, 56, 81, 61, 84);
        } else if (edad >= 26 && edad <= 35) {
            calcularRiesgoArritmiaPorSexo(persona, historicoRitmo, medicionRitmo, 55, 81, 60, 82);
        } else if (edad >= 36 && edad <= 45) {
            calcularRiesgoArritmiaPorSexo(persona, historicoRitmo, medicionRitmo, 57, 82, 60, 84);
        } else if (edad >= 46 && edad <= 55) {
            calcularRiesgoArritmiaPorSexo(persona, historicoRitmo, medicionRitmo, 58, 83, 61, 83);
        } else if (edad >= 56 && edad <= 65) {
            calcularRiesgoArritmiaPorSexo(persona, historicoRitmo, medicionRitmo, 57, 81, 60, 83);
        } else { // > 65
            calcularRiesgoArritmiaPorSexo(persona, historicoRitmo, medicionRitmo, 56, 79, 60, 84);
        }
        
        return historicoRitmo;
    }
    
    private void calcularRiesgoArritmia(HistoricoRitmo historicoRitmo, Integer medicionRitmo, int limInferior, int limSuperior) {
        int porcentajeDesviacion = 10;
        int promedio = (limInferior + limSuperior) / 2;
        int desviacion = (promedio * porcentajeDesviacion) / 100;
        
        int min = limInferior - desviacion;
        int max = limSuperior + desviacion;
        
        if (medicionRitmo <= min) {
            historicoRitmo.setGeneroAlertaBradicardia(true);
        }

        if (medicionRitmo >= max) {
            historicoRitmo.setGeneroAlertaTaquicardia(true);
        }
    }
    
    private void calcularRiesgoArritmiaPorSexo(Persona persona, HistoricoRitmo historicoRitmo, Integer medicionRitmo, int limInferiorM, int limSuperiorM, int limInferiorF, int limSuperiorF) {
        if (Objects.equals(persona.getSexo(), SexoEnum.M)) {
            calcularRiesgoArritmia(historicoRitmo, medicionRitmo, limInferiorM, limSuperiorM);
        }
        if (Objects.equals(persona.getSexo(), SexoEnum.F)) {
            calcularRiesgoArritmia(historicoRitmo, medicionRitmo, limInferiorF, limSuperiorF);
        }
    }
    
}
