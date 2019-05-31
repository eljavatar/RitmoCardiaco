package com.eljavatar.udistrital.ritmocardiaco.logfactory;

import org.slf4j.Logger;

public class LoggerImpl implements Log {

/**
	 * 
	 */
	private static final long serialVersionUID = 7304801181122907473L;
	private final Logger LOGGER;
    
    public LoggerImpl(Logger logger) {
        this.LOGGER = logger;
    }
    
    @Override
    public void info(String msg) {
        LOGGER.info(msg);
    }
    
    @Override
    public void info(String msg, Throwable e) {
        LOGGER.info(msg, e);
    }
    
    @Override
    public void debug(String msg) {
        LOGGER.debug(msg);
    }
    
    @Override
    public void debug(String msg, Throwable e) {
        LOGGER.debug(msg, e);
    }
    
    @Override
    public void error(String msg) {
        LOGGER.error(msg);
    }
    
    @Override
    public void error(String msg, Throwable e) {
        LOGGER.error(msg, e);
    }

    
    @Override
    public void trace(String msg) {
        LOGGER.trace(msg);
    }
    
    @Override
    public void trace(String msg, Throwable e) {
        LOGGER.trace(msg, e);
    }
    
    @Override
    public void warn(String msg) {
        LOGGER.warn(msg);
    }
    
    @Override
    public void warn(String msg, Throwable e) {
        LOGGER.warn(msg, e);
    }
    
}
