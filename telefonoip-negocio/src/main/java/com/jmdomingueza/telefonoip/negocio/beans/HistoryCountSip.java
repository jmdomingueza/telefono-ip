package com.jmdomingueza.telefonoip.negocio.beans;


/**
 * Interfaz que define las operaciones del bean HistoryCountSip.
 * 
 * @author jmdomingueza
 *
 */
public interface HistoryCountSip extends HistoryCount{

	public enum State{registering,unregistering,unregistered,registered,unauthorized,error}
	
	/**
	 * Devuelve el host del servidor donde esta registrado el historico de  cuenta sip
	 * @return
	 */
	public String getHostServer();

	/**
	 * Pone el host del servidor donde esta registrado el historico de  cuenta sip
	 * @param hostServer
	 */
	public void setHostServer(String hostServer);
	
	/**
	 * Devuelve el password del historico de cuenta sip
	 * @return
	 */
	public String getPassword();

	/**
	 * Pone el password del historico de cuenta sip
	 * @param password
	 */
	public void setPassword(String password);

	/**
	 * Metodo que devuelve el estado de la cuenta sip al 
	 * al que pertence el historico de cuenta
	 * @return
	 */
	public State getState();
	
	/**
	 * Metodo que pone el estado de la cuenta sip al 
	 * al que pertence el historico de cuenta
	 * @param state
	 */
	public void setState(State state);

	
}
