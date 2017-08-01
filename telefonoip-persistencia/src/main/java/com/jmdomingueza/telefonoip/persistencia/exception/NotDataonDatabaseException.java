package com.jmdomingueza.telefonoip.persistencia.exception;

public class NotDataonDatabaseException extends RuntimeException {

	private static final long serialVersionUID = 7033712222003646059L;

	public NotDataonDatabaseException() {
		super("Not Data on Database");
	}

	public NotDataonDatabaseException(String message) {
		super(message);
	}

	public NotDataonDatabaseException(Throwable tw) {
		super(tw);
	}
}
