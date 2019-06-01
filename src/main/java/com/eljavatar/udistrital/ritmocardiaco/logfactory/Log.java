package com.eljavatar.udistrital.ritmocardiaco.logfactory;

import java.io.Serializable;

public interface Log extends Serializable {

    public void info(String msg);

    public void info(String msg, Throwable cause);

    public void debug(String msg);

    public void debug(String msg, Throwable cause);

    public void error(String msg);

    public void error(String msg, Throwable cause);

    public void trace(String msg);

    public void trace(String msg, Throwable cause);

    public void warn(String msg);

    public void warn(String msg, Throwable cause);

}
