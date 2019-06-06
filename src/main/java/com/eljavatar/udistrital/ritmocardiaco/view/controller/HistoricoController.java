/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljavatar.udistrital.ritmocardiaco.view.controller;

import com.eljavatar.udistrital.ritmocardiaco.annotations.I18n;
import com.eljavatar.udistrital.ritmocardiaco.annotations.UserInfo;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.BusinessException;
import com.eljavatar.udistrital.ritmocardiaco.exceptions.ExceptionUtils;
import com.eljavatar.udistrital.ritmocardiaco.logfactory.Log;
import com.eljavatar.udistrital.ritmocardiaco.logical.manager.HistoricoRitmoManagerEjb;
import com.eljavatar.udistrital.ritmocardiaco.model.HistoricoRitmo;
import com.eljavatar.udistrital.ritmocardiaco.utils.UsuarioAplicacion;
import com.eljavatar.udistrital.ritmocardiaco.view.utils.FacesMessagesUtil;
import com.eljavatar.udistrital.ritmocardiaco.view.utils.FacesUtils;
import com.eljavatar.udistrital.ritmocardiaco.view.utils.PaginationDto;
import com.eljavatar.udistrital.ritmocardiaco.view.utils.ResponsePaginationDto;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
@URLMapping(id = "historico", pattern = "/app/historico", viewId = "/faces/historico.xhtml")
public class HistoricoController implements Serializable {
    
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
    private ExceptionUtils exceptionUtils;
    
    @Inject
    private HistoricoRitmoManagerEjb historicoRitmoManagerEjb;
    
    @Inject
    @UserInfo
    private UsuarioAplicacion userApp;
    
    private DateFormat dateFormat;
    private Date fechaIni;
    private Date fechaFin;
    
    private LazyDataModel<HistoricoRitmo> lazyDataModelHistorico;
    
    private LineChartModel lineModelHistorico;
    
    @PostConstruct
    public void init() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // yyyy-MM-dd HH:mm:ss.SSS
        loadLazyDataModelCategorias();
        //createLineModels();
    }
    
    public void loadLazyDataModelCategorias() {
        lazyDataModelHistorico = new LazyDataModel<HistoricoRitmo>() {
            
            @Override
            public List<HistoricoRitmo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
                try {
                    PaginationDto paginationDto = new PaginationDto(filters, sortField, sortOrder, first, pageSize);
                    
                    ResponsePaginationDto response = historicoRitmoManagerEjb.getHistoricoByUserAndDatee(userApp.getId(), fechaIni, fechaFin, paginationDto);
                    setRowCount(response.getTotalItems());
                    return response.getDataList();
                } catch (BusinessException ex) {
                    facesMessagesUtil.addError(exceptionUtils.createMessage(resourceBundle, ex));
                    return new ArrayList<>();
                }
            }
            
            @Override
            public HistoricoRitmo getRowData(String rowKey) {
                for (HistoricoRitmo historico : (List<HistoricoRitmo>) getWrappedData()) {
                    if (Objects.equals(historico.getId().toString(), rowKey)) {
                        return historico;
                    }
                }
                return null;
            }
            
            @Override
            public Object getRowKey(HistoricoRitmo historico) {
                return historico.getId();
            }
        };
    }
    
    public void createLineModels() {
        lineModelHistorico = initLinearModel();
        lineModelHistorico.setTitle("Frecuencia Cardiaca a través del Tiempo");
        //lineModelHistorico.setZoom(true);
        lineModelHistorico.setLegendPosition("e");
        
        lineModelHistorico.getAxis(AxisType.Y).setLabel("Frecuencia Cardiaca");
        
        DateAxis axis = new DateAxis("Fecha/Hora");
        
        if (lazyDataModelHistorico.getWrappedData() != null && !lazyDataModelHistorico.getWrappedData().isEmpty()) {
            axis.setTickAngle(-50);
            axis.setMin(dateFormat.format(lazyDataModelHistorico.getWrappedData().get(0).getFechaMedicion()));
            axis.setMax(dateFormat.format(lazyDataModelHistorico.getWrappedData().get(lazyDataModelHistorico.getWrappedData().size() - 1).getFechaMedicion()));
            axis.setTickFormat("%Y-%m-%d %H:%M:%S");
            // %Y-%m-%d %H:%M:%S.%N
        }
 
        lineModelHistorico.getAxes().put(AxisType.X, axis);
    }
    
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
 
        LineChartSeries series = new LineChartSeries();
        series.setLabel("Frecuencia");
 
        if (lazyDataModelHistorico.getWrappedData() != null && !lazyDataModelHistorico.getWrappedData().isEmpty()) {
            lazyDataModelHistorico.getWrappedData().forEach((hist) -> {
                series.set(dateFormat.format(hist.getFechaMedicion()), hist.getValorMedicion());
            });
        }
 
        model.addSeries(series);
 
        return model;
    }

    public LazyDataModel<HistoricoRitmo> getLazyDataModelHistorico() {
        return lazyDataModelHistorico;
    }

    public void setLazyDataModelHistorico(LazyDataModel<HistoricoRitmo> lazyDataModelHistorico) {
        this.lazyDataModelHistorico = lazyDataModelHistorico;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LineChartModel getLineModelHistorico() {
        return lineModelHistorico;
    }

    public void setLineModelHistorico(LineChartModel lineModelHistorico) {
        this.lineModelHistorico = lineModelHistorico;
    }
    
}
