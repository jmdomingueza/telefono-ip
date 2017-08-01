package com.jmdomingueza.telefonoip.sip.services.exception;

public class PortRTPNotAvalibleException extends Exception {

	private static final long serialVersionUID = -8327079785411754487L;

	public PortRTPNotAvalibleException(String message, Exception e) {
		super(message,e);
	}

}
