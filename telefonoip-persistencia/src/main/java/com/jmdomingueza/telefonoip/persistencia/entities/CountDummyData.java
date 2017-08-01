package com.jmdomingueza.telefonoip.persistencia.entities;

import java.io.Serializable;

/**
 * Interfaz que define las operaciones de la entidad CountDummyData que 
 * corresponde con la tabla CUENTA_DUMMY.
 * 
 * @author jmdomingueza
 *
 */
public interface CountDummyData extends Serializable{

	public enum State{registering,unregistering,unregistered,registered}

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
	 * Metodo que devuelve el atributo editable.
	 * @return
	 */
	public String getEditable();

	/**
	 * Metodo que pone el atributo editable.
	 * @param state
	 */
	public void setEditable(String editable);
	
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
