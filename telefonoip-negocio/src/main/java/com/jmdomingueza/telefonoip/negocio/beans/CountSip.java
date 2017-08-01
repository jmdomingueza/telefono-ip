package com.jmdomingueza.telefonoip.negocio.beans;

import java.util.List;

/**
 * Interfaz que define las operaciones del bean CountSip.
 * 
 * @author jmdomingueza
 *
 */
public interface CountSip extends Count{

	public enum State{registering,unregistering,unregistered,registered,unauthorized,error}
	
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
	 * Devuelve el host del servidor donde esta registrada la cuenta sip
	 * @return
	 */
	public String getHostServer();

	/**
	 * Pone el host del servidor donde esta registrada la cuenta sip
	 * @param hostServer
	 */
	public void setHostServer(String hostServer);
	
	
	/**
	 * Devuelve el password de la cuenta sip
	 * @return
	 */
	public String getPassword();

	/**
	 * Pone el password de la cuenta sip
	 * @param password
	 */
	public void setPassword(String password);

	
	/**
	 * Metodo que devuelve el estado de la cuenta sip
	 * @return
	 */
	public State getState();
	
	/**
	 * Metodo que pone el estado de la cuenta sip
	 * @param state
	 */
	public void setState(State state);

	
	/**
	 * Devuelve el valor del display que tiene la cuenta sip
	 * @return
	 */
	public String getDisplay();

	/**
	 * Pone el valor del display que tiene la cuenta sip
	 * @param display
	 */
	public void setDisplay(String display);
	
	/**
	 * Devuelve una lista de enteros que corresponde con los 
	 * audios permitidos por la cuenta sip
	 * @return
	 */
	public List<Integer> getAudioAvalibleList();

	/**
	 * Pone una lista de enteros que corresponde con los 
	 * audios permitidos por la cuenta sip
	 * @param audiosAvalibles
	 */
	public void setAudioAvalibleList(List<Integer> audioAvalibleList);

}
