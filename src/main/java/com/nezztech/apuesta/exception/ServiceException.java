package com.nezztech.apuesta.exception;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
public class ServiceException extends Exception {

	/**
	 * UID Serial de la clase.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructor
     * @param message Mensaje de error
     */
    public ServiceException(String message) {
        super(message);
    }
    

    /**
     * Constructor
     * @param message Mensaje de error
     * @param causa Causa completa del error
     */
    public ServiceException(String message, Throwable causa) {
        super(message, causa);
    }
    


    
}
