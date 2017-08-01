package com.jmdomingueza.telefonoip.sip.sip;


public class CreateResponseException extends Exception {

	private static final long serialVersionUID = -5762866301046841965L;

	/**
	 * Constructs a new exception with the specified detail message. 
	 * @param message  the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 */
	public CreateResponseException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * @param message  the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 * @param e  the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public CreateResponseException(String message, Exception e) {
		super(message,e);
	}
	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * @param e  the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public CreateResponseException(Exception e) {
		super(e);
	}
}
