package com.jmdomingueza.telefonoip.negocio.beans;

import java.io.Serializable;

/**
 * Interfaz que define las operaciones del bean Count.
 * @author jmdomingueza
 *
 */
public interface Count extends Serializable{

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

}
