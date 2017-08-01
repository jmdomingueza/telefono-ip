package com.jmdomingueza.telefonoip.dummy.beans;

import java.io.Serializable;

/**
 * Interfaz que define las operaciones de bean Count.
 * @author jmdomingueza
 *
 */
public interface Count extends Serializable{

	public enum State{registering,unregistering,unregistered,registered}

	/**
	 * Devuelve el usuario de la cuenta 
	 * @return 
	 */
	public String getUser();

	/**
	 * Pone el usuario de la cuenta
	 * @param user
	 */
	public void setUser(String user);
	
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
