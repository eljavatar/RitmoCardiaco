/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljavatar.udistrital.ritmocardiaco.view.utils;

import java.io.Serializable;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andres
 */
public class PaginationDto implements Serializable {
    
    private Map<String, Object> filters;
    private String sortField;
    private SortOrder sortOrder;
    private Integer first;
    private Integer pageSize;

    public PaginationDto(Map<String, Object> filters, String sortField, SortOrder sortOrder, Integer first, Integer pageSize) {
        this.filters = filters;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
        this.first = first;
        this.pageSize = pageSize;
    }

    public PaginationDto() {
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
}
