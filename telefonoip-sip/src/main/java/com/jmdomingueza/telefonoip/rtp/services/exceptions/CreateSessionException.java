package com.jmdomingueza.telefonoip.rtp.services.exceptions;

public class CreateSessionException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 443179590014333203L;

	public CreateSessionException() {

	}

	public CreateSessionException(String message) {
		super(message);
	}

	public CreateSessionException(Throwable tw) {
		super(tw);
	}
}
