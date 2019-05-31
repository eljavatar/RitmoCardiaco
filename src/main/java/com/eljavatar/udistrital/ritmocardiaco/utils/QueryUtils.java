package com.eljavatar.udistrital.ritmocardiaco.utils;

import java.util.Map;

import javax.persistence.Query;

public class QueryUtils {

	public static String getStringLike(String str) {
        str = "%" + str.trim() + "%";
        return str.replace(" ", "%");
    }
    
    public static void loadParameters(Map<String, Object> parameters, Query query) {
        parameters.entrySet().stream().forEach((entry) -> {
            query.setParameter(entry.getKey(), entry.getValue());
        });
    }
    
    public static void setQueryFiltersLike(Map<String, Object> filters, Map<String, Object> parameters, StringBuilder sbQueryFrom, String nameFilter, String nameField, String nameParameter, String operadorLogico) {
        if (filters.get(nameFilter) != null && !filters.get(nameFilter).toString().isEmpty()) {
            sbQueryFrom.append(operadorLogico).append(" ");
            sbQueryFrom.append("    LOWER(").append(nameField).append(") LIKE LOWER(:").append(nameParameter).append(") ");
            parameters.put(nameParameter, QueryUtils.getStringLike(filters.get(nameFilter).toString()));
        }
    }
    
    public static void setQueryFiltersExact(Map<String, Object> filters, Map<String, Object> parameters, StringBuilder sbQueryFrom, String nameFilter, String nameField, String nameParameter, String operadorLogico, Object value) {
        if (filters.get(nameFilter) != null) {
            sbQueryFrom.append(operadorLogico).append(" ");
            sbQueryFrom.append("    ").append(nameField).append(" = :").append(nameParameter).append(" ");
            parameters.put(nameParameter, value);
        }
    }
    
}
