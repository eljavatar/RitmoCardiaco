/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljavatar.udistrital.ritmocardiaco.logical.manager;

import com.eljavatar.udistrital.ritmocardiaco.exceptions.BusinessException;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.MensajeErrorEnum;
import com.eljavatar.udistrital.ritmocardiaco.model.HistoricoRitmo;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Andres
 */
@Stateless
public class HistoricoRitmoManagerEjb implements Serializable {
    
    @Inject
    private AbstractManagerEjb<HistoricoRitmo> managerHistoricoRitmo;
    
    public void guardarHistorico(HistoricoRitmo historicoRitmo) throws BusinessException {
        managerHistoricoRitmo.setEntityClass(HistoricoRitmo.class);
        try {
            managerHistoricoRitmo.insert(historicoRitmo);
        } catch (BusinessException ex) {
            throw new BusinessException(MensajeErrorEnum.EXCEPTION_INSERT_HISTORICO_RITMO);
        }
    }
    
}
