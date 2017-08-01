package com.jmdomingueza.telefonoip.negocio.beans;

import org.apache.log4j.Logger;

/**
 * Clase que implementa los atributos y operaciones del bean CallSip.
 * 
 * @author jmdomingueza
 *
 */
public class CallSipImpl  extends CallImpl implements CallSip {

	private static final long serialVersionUID = 4052189764202224153L;
	
	private final static Logger logger = Logger.getLogger(CallSipImpl.class);
	
	/**
	 * Etiqueta del usuario que realiza la llamada sip
	 */
	private String tagFrom;
	
	/**
	 * Etiqueta del usuario que recibe la llamada sip
	 */
	private String tagTo;
	
	/**
	 * Rama de la via de la llamada sip
	 */
	private String branchVia;
	
	/**
	 * Tipo del contenido de la llamada sip
	 */
	private String contentType;
	
	/**
	 * Subtipo del contenido de la llamada sip
	 */
	private String contentSubType;
	
	/**
	 * Modo del SessionDescription de la llamada sip
	 */
	private Integer sessionDescriptionMode;
	
	@Override
	public String getTagFrom() {
		logger.trace("Enter getTagFrom");
		logger.trace("Exit getTagFrom");
		return tagFrom;
	}

	@Override
	public void setTagFrom(String tagFrom) {
		logger.trace("Enter setTagFrom");
		this.tagFrom = tagFrom;
		logger.trace("Exit setTagFrom");
	}

	@Override
	public String getTagTo() {
		logger.trace("Enter getTagTo");
		logger.trace("Exit getTagTo");
		return tagTo;
	}

	@Override
	public void setTagTo(String tagTo) {
		logger.trace("Enter setTagTo");
		this.tagTo = tagTo;
		logger.trace("Exit setTagTo");
	}

	@Override
	public String getBranchVia() {
		logger.trace("Enter getBranchVia");
		logger.trace("Exit getBranchVia");
		return branchVia;
	}
	
	@Override
	public void setBranchVia(String branchVia) {
		logger.trace("Enter setBranchVia");
		this.branchVia = branchVia;
		logger.trace("Exit setBranchVia");
	}

	@Override
	public String getContentType() {
		logger.trace("Enter getContentType");
		logger.trace("Exit getContentType");
		return contentType;
	}

	@Override
	public void setContentType(String contentType) {
		logger.trace("Enter setContentType");
		this.contentType = contentType;
		logger.trace("Exit setContentType");
	}

	@Override
	public String getContentSubType() {
		logger.trace("Enter getContentSubType");
		logger.trace("Exit getContentSubType");
		return contentSubType;
	}

	@Override
	public void setContentSubType(String contentSubType) {
		logger.trace("Enter setContentSubType");
		this.contentSubType = contentSubType;
		logger.trace("Exit setContentSubType");
	}

	@Override
	public Integer getSessionDescriptionMode() {
		logger.trace("Enter getSessionDescriptionMode");
		logger.trace("Exit getSessionDescriptionMode");
		return sessionDescriptionMode;
	}

	@Override
	public void setSessionDescriptionMode(Integer sessionDescriptionMode) {
		logger.trace("Enter setSessionDescriptionMode");
		this.sessionDescriptionMode = sessionDescriptionMode;
		logger.trace("Exit setSessionDescriptionMode");
	}
	
}
