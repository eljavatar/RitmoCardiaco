package com.eljavatar.udistrital.ritmocardiaco.logfactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.logging.Logger;

public class LogProducer {

    @Produces
    public Log getLogger(InjectionPoint injectionPoint) {
        // Slf4j
        //Logger logger = LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
        // JBoss Logging
        Logger logger = Logger.getLogger(injectionPoint.getMember().getDeclaringClass());
        return new LoggerImpl(logger);
    }
	
}
