package com.jmdomingueza.telefonoip.sip.services.exception;

/**
 * Excepcion que se devuelve cunado parametro de los que se pasa en el metodo no es valido
 * @author jmdomingueza
 *
 */
public class ParameterNotValidedException extends Exception {

	private static final long serialVersionUID = -5762867847706828066L;

	/**
	 * Construtor de la clase
	 * @param message Mensaje de porque el parametro no es valido
	 */
	public ParameterNotValidedException(String message) {
		super(message);
	}

}
