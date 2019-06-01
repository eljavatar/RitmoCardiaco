package com.eljavatar.udistrital.ritmocardiaco.exceptions;

public interface IException {

    public abstract String[] getParams();

    public abstract Enum getCodigoError();

    public abstract String getMensaje();

}
