package com.eljavatar.udistrital.ritmocardiaco.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class Assert implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1957454992636807243L;

    private Assert() {
    }

    /**
     * @param object
     * @return TRUE assertion when given objects is not null, FALSE otherwise
     */
    public static boolean has(Object object) {
        return object != null;
    }

    /**
     * @param text
     * @return TRUE when given text has any character, FALSE otherwise
     */
    public static boolean has(String text) {
        return text != null && text.trim().length() > 0;
    }

    /**
     * @param textToSearch
     * @param substring
     * @return TRUE when given text contains the given substring, FALSE
     * otherwise
     */
    public static boolean contains(String textToSearch, String substring) {
        return textToSearch != null && textToSearch.contains(substring);
    }

    /**
     * @param array
     * @return TRUE when given array has elements; that is, it must not be
     * {@code null} and must have at least one element. FALSE otherwise
     */
    public static boolean has(Object[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        for (Object element : array) {
            if (element != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param collection
     * @return TRUE when given collection has elements; that is, it must not be
     * {@code null} and must have at least one element. @return FALSE otherwise
     */
    public static boolean has(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * @param map
     * @return TRUE if given Map has entries; that is, it must not be
     * {@code null} and must have at least one entry. Queue FALSE otherwise
     */
    public static boolean has(Map<?, ?> map) {
        if (map == null) {
            return false;
        }
        return has(map.entrySet().toArray());
    }

}
