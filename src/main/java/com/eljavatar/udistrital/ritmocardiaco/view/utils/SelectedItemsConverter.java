package com.eljavatar.udistrital.ritmocardiaco.view.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.primefaces.util.Base64;

import com.eljavatar.udistrital.ritmocardiaco.logfactory.Log;

@FacesConverter("eljavatar.SelectedItemsConverter")
public class SelectedItemsConverter implements Converter {

    @Inject
    private Log log;

    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
        try {
            byte[] data = Base64.decode(value);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            ois.close();
            return o;
        } catch (Exception e) {
            log.error("Exception obteniendo Objeto desde String del componente", e);
        }
        return null;
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            oos.close();
            return new String(Base64.encodeToByte(baos.toByteArray(), false));
        } catch (IOException e) {
            log.error("Exception obteniendo String desde Objeto del componente", e);
        }
        return null;
    }

}
