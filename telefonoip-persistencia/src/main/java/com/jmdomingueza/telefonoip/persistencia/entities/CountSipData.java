package com.jmdomingueza.telefonoip.persistencia.entities;

import java.io.Serializable;

/**
 * Interfaz que define las operaciones de la entidad CountSipData que 
 * corresponde con la tabla CUENTA_SIP.
 * @author jmdomingueza
 *
 */
public interface CountSipData extends Serializable{

	public enum State{registering,unregistering,unregistered,registered,unauthorized,error}

	/**
	 * Devuelve el usuario de la cuenta, clave primaria
	 * @return 
	 */
	public String getUser();

	/**
	 * Pone el usuario de la cuenta, clave primaria
	 * @param user
	 */
	public void setUser(String user);

	/**
	 * Devuelve el password de la cuenta
	 * @return
	 */
	public String getPassword();

	/**
	 * Pone el password de la cuenta
	 * @param password
	 */
	public void setPassword(String password);

	/**
	 * Devuelve el host del servidor donde esta registrada la cuenta,
	 * clave primaria
	 * @return
	 */
	public String getHostServer();

	/**
	 * Pone el host del servidor donde esta registrada la cuenta, 
	 * clave primaria
	 * @param hostServer
	 */
	public void setHostServer(String hostServer);
	
	/**
	 * Metodo que devuelve el estado de la cuenta
	 * @return
	 */
	public State getState();
	
	/**
	 * Metodo que pone el estado de la cuenta
	 * @param state
	 */
	public void setState(State state);
	
}
