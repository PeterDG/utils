package com.peter.util.exceptions;

import org.apache.log4j.Logger;

/**
 * Created by Peter on 2/2/2016.
 */
public class TechnicalException extends Exception {

    private static final Logger logger = Logger.getLogger(TechnicalException.class);

    public TechnicalException(String message) {
        super(message);
        logger.error(message);
    }

    public TechnicalException(Exception e) {
        super(e);
        logger.error("Error - :" + e.getMessage(), e);
    }

    public TechnicalException(Exception e, String message) {
        super(e);
        logger.error("Error - :" + message + " :" + e.getMessage(), e);
    }
}
