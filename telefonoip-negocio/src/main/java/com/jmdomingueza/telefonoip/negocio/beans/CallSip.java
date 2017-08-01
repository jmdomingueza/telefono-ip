package com.jmdomingueza.telefonoip.negocio.beans;

/**
 * Interfaz que define las operaciones del bean CallSip.
 * 
 * @author jmdomingueza
 *
 */
public interface CallSip extends Call {

	/**
	 * Metodo que devuelve la etiqueta del usuario que realiza la llamada sip
	 * @return
	 */
	public String getTagFrom();

	/**
	 * Metodo que pone la etiqueta del usuario que realiza la llamada sip
	 * @param tagFrom
	 */
	public void setTagFrom(String tagFrom);

	/**
	 * Metodo que devuelve la etiqueta del usuario que recibe la llamada sip
	 * @return
	 */
	public String getTagTo();

	/**
	 * Metodo que pone la etiqueta del usuario que recibe la llamada sip
	 * @param tagTo
	 */
	public void setTagTo(String tagTo);

	/**
	 * Metodo que devuelve la rama de la via de la llamada sip
	 * @return
	 */
	public String getBranchVia();

	/**
	 *  Metodo que pone la rama de la via de la llamada sip
	 * @param branchVia
	 */
	public void setBranchVia(String branchVia);
	
	/**
	 * Metodo que devuelve el tipo del contenido de la llamada sip
	 * @return
	 */
	public String getContentType();

	/**
	 *  Metodo que pone  el tipo del contenido de la llamada sip
	 * @param contentType
	 */
	public void setContentType(String contentType);
	
	/**
	 * Metodo que devuelve el subtipo del contenido de la llamada sip
	 * @return
	 */
	public String getContentSubType();

	/**
	 * Metodo que pone el subtipo del contenido de la llamada sip
	 * @param contentSubType
	 */
	public void setContentSubType(String contentSubType);
	
	/**
	 * Metodo que devuelve el modo del SessionDescription de la llamada sip
	 * @return
	 */
	public Integer getSessionDescriptionMode();
	
	/**
	 * Metodo que pone el el modo del SessionDescription de la llamada sip
	 * @param contentSubType
	 */
	public void setSessionDescriptionMode(Integer sessionDescriptionMode);
	
}
