package com.jmdomingueza.telefonoip.sip.beans;

import java.io.Serializable;

public interface Call extends Serializable {
	
	public enum State{created,ringing,talking,terminated,error}
	
	public enum Direction{in,out}
	
	/**
	 * Metodo que devuelve la cuenta desde la que se hace la llamada
	 * @return
	 */
	public Count getCount();

	/**
	 * Metodo que pone la cuenta desde la que se hace la llamada
	 * @param count
	 */
	public void setCount(Count count) ;

	/**
	 * Metodo que devuelve el numero de destino o origen de la llamada,
	 * depende del sentido de la llamada.
	 * @return
	 */
	public String getNumber() ;

	/**
	 * Metodo que pone el destino de la llamada
	 * @param userRemote
	 */
	public void setNumber(String number);

	/**
	 * Metodo que devuelve la etiqueta del usuario que realiza la llamada
	 * @return
	 */
	public String getTagFrom();

	/**
	 * Metodo que pone la etiqueta del usuario que realiza la llamada
	 * @param tagFrom
	 */
	public void setTagFrom(String tagFrom);

	/**
	 * Metodo que devuelve la etiqueta del usuario que recibe la llamada
	 * @return
	 */
	public String getTagTo();

	/**
	 * Metodo que pone la etiqueta del usuario que recibe la llamada
	 * @param tagTo
	 */
	public void setTagTo(String tagTo);

	/**
	 * Metodo que devuelve la rama de la via 
	 * @return
	 */
	public String getBranchVia();

	/**
	 *  Metodo que pone la rama de la via 
	 * @param branchVia
	 */
	public void setBranchVia(String branchVia);
	
	/**
	 * Metodo que devuelve el tipo del contenido
	 * @return
	 */
	public String getContentType();

	/**
	 *  Metodo que pone  el tipo del contenido
	 * @param contentType
	 */
	public void setContentType(String contentType);
	
	/**
	 * Metodo que devuelve el subtipo del contenido
	 * @return
	 */
	public String getContentSubType();

	/**
	 * Metodo que pone el subtipo del contenido
	 * @param contentSubType
	 */
	public void setContentSubType(String contentSubType);
	
	/**
	 * Metodo que devuelve el modo del SessionDescription
	 * @return
	 */
	public Integer getSessionDescriptionMode();
	
	/**
	 * Metodo que pone el modo del SessionDescription
	 * @param contentSubType
	 */
	public void setSessionDescriptionMode(Integer sessionDescriptionMode);
	
	/**
	 * Metodo que devuelve el estado de la llamada SIP
	 * @return
	 */
	public State getState();
	
	/**
	 * Metodo que pone el estado de la llamada SIP
	 * @param state
	 */
	public void setState(State state);

	/**
	 * Metodo que devuelve el sentido de la llamada
	 * @return
	 */
	public Direction getDirection();
	
	/**
	 * Metodo que pone el sentido de la llamada
	 * @param direction
	 */
	public void setDirection(Direction direction);
	
}
