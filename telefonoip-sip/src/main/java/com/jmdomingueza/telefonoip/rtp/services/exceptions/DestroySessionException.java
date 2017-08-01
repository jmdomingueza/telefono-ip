package com.jmdomingueza.telefonoip.rtp.services.exceptions;

public class DestroySessionException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 443179590014333203L;

	public DestroySessionException() {

	}

	public DestroySessionException(String message) {
		super(message);
	}

	public DestroySessionException(Throwable tw) {
		super(tw);
	}
}
