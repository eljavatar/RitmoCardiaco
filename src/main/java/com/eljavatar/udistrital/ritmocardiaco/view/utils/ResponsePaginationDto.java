/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljavatar.udistrital.ritmocardiaco.view.utils;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Andres
 */
public class ResponsePaginationDto<T> implements Serializable {
    
    private Integer totalItems;
    private List<T> dataList;

    public ResponsePaginationDto(Integer totalItems, List<T> dataList) {
        this.totalItems = totalItems;
        this.dataList = dataList;
    }

    public ResponsePaginationDto() {
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
    
}
