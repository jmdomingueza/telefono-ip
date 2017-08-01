package com.jmdomingueza.telefonoip.sip.beans;

import java.util.List;

public interface Count {

	public enum State{registering,unregistering,unregistered,registered,unauthorized,error}
	
	/**
	 * Gets name user registered on Server
	 * @return new strirng
	 */
	public String getUser();

	/**
	 * Sets name user registered on Server
	 * @param user
	 */
	public void setUser(String user);

	/**
	 * Devuelve el host local por donde se mandan los mensajes SIP 
	 * de la cuenta sip.
	 * @return
	 */
	public String getLocalHost();
	
	/**
	 * Pone el host local por donde se mandan los mensajes SIP 
	 * de la cuenta sip.
	 * @param localHost
	 */
	public void setLocalHost(String localHost);

	/**
	 * Devuelve el puerto local  por donde se mandan los mensajes SIP 
	 * de la cuenta sip.
	 * @return
	 */
	public Integer getLocalPort();

	/**
	 * Pone el puerto local  por donde se mandan los mensajes SIP 
	 * de la cuenta sip.
	 * @param localPort
	 */
	public void setLocalPort(Integer localPort);

	/**
	 * Devuelve el protocolo que se usa para mandan los mensajes SIP 
	 * de la cuenta sip.
	 * @return
	 */
	public String getProtocol();

	/**
	 * Pone el protocolo que se usa para mandan los mensajes SIP 
	 * de la cuenta sip.
	 * @param protocol
	 */
	public void setProtocol(String protocol);
	
	/**
	 * Gets password registered on Server
	 * @return
	 */
	public String getPassword();

	/**
	 * Sets password registered on Server
	 * @param password
	 */
	public void setPassword(String password);

	/**
	 * Gets text shows on display
	 * @return
	 */
	public String getDisplay();

	/**
	 * Sets text shows on display
	 * @param display
	 */
	public void setDisplay(String display);

	/**
	 * Gets host of server where has registed the user
	 * @return
	 */
	public String getHostServer();

	/**
	 * Sets host of server where has registed the user
	 * @param hostServer
	 */
	public void setHostServer(String hostServer);
	
	/**
	 * Gets list of audios avalibles for the user
	 * @return
	 */
	public List<Integer> getAudioAvalibleList();

	/**
	 * Sets list of audios avalibles for the user
	 * @param audiosAvalibles
	 */
	public void setAudioAvalibleList(List<Integer> audioAvalibleList);

	/**
	 * Metodo que devuelve el estado en el que se encuentra el Usuario
	 * @return
	 */
	public State getState();
	
	/**
	 * Metodo que pone el estado en el que se encuentra el Usuario
	 * @param state
	 */
	public void setState(State state);
}
