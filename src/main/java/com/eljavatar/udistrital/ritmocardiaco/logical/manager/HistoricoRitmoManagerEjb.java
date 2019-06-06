/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljavatar.udistrital.ritmocardiaco.logical.manager;

import com.eljavatar.udistrital.ritmocardiaco.exceptions.BusinessException;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.MensajeErrorEnum;
import com.eljavatar.udistrital.ritmocardiaco.logfactory.Log;
import com.eljavatar.udistrital.ritmocardiaco.model.HistoricoRitmo;
import com.eljavatar.udistrital.ritmocardiaco.utils.ObjectRestriction;
import com.eljavatar.udistrital.ritmocardiaco.utils.RelationalOperatorJpaEnum;
import com.eljavatar.udistrital.ritmocardiaco.view.utils.PaginationDto;
import com.eljavatar.udistrital.ritmocardiaco.view.utils.ResponsePaginationDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    
    @Inject
    private Log log;
    
    public void guardarHistorico(HistoricoRitmo historicoRitmo) throws BusinessException {
        managerHistoricoRitmo.setEntityClass(HistoricoRitmo.class);
        try {
            managerHistoricoRitmo.insert(historicoRitmo);
        } catch (BusinessException ex) {
            throw new BusinessException(MensajeErrorEnum.EXCEPTION_INSERT_HISTORICO_RITMO);
        }
    }
    
    public ResponsePaginationDto getHistoricoByUserAndDatee(Integer personaId, Date fechaIni, Date fechaFin, PaginationDto paginationDto) throws BusinessException {
        managerHistoricoRitmo.setEntityClass(HistoricoRitmo.class);
        try {
            List<ObjectRestriction> listRestrictions = new ArrayList<>();
            listRestrictions.add(new ObjectRestriction("persona.id", RelationalOperatorJpaEnum.EQUAL, personaId));
            listRestrictions.add(new ObjectRestriction("fechaMedicion", RelationalOperatorJpaEnum.BETWEEN, fechaIni, fechaFin));
            
            List<HistoricoRitmo> listData = managerHistoricoRitmo.getListData(listRestrictions, paginationDto.getFirst(), paginationDto.getPageSize());
            Integer countItems = managerHistoricoRitmo.getCountData(listRestrictions).intValue();
            
            return new ResponsePaginationDto(countItems, listData);
        } catch (BusinessException ex) {
            log.error("Error buscando histórico de ritmo cardiaco", ex);
            throw new BusinessException(MensajeErrorEnum.EXCEPTION_SELECT_LIST_HISTORICO_RITMO);
        }
    }
    
}
