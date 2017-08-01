package com.jmdomingueza.telefonoip.sip.services.exception;

/**
 * Excepcion que es lanzada cuando se intenta crear una Stack que ya esta creada.
 * @author jmdomingueza
 *
 */
public class IsAlivedSipStackException extends RuntimeException {

	private static final long serialVersionUID = 443179590014333203L;


	public IsAlivedSipStackException(String message) {
		super(message);
	}

}
