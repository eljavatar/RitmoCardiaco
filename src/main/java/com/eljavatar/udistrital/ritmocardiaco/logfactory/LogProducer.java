package com.eljavatar.udistrital.ritmocardiaco.logfactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogProducer {

	@Produces
    public Log getLogger(InjectionPoint injectionPoint) {
        Logger logger = LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
        return new LoggerImpl(logger);
    }
	
}
