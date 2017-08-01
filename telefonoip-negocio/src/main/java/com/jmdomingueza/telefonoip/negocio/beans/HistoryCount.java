package com.jmdomingueza.telefonoip.negocio.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Interfaz que define las operaciones del bean HistoryCount.
 * @author jmdomingueza
 *
 */
public interface HistoryCount extends Serializable{

	public enum Action{created,removed,modified,error}

	/**
	 * Devuelve el identifidor autogenerado del historico de cuenta, clave priamria.
	 * @return 
	 */
	public Long getId();

	/**
	 * Pone identifidor autogenerado del historico de cuenta, clave priamria.
	 * @param user
	 */
	public void setId(Long id);
	
	/**
	 * Devuelve el usuario del historico de cuenta 
	 * @return 
	 */
	public String getUser();

	/**
	 * Pone el usuario del historico de cuenta
	 * @param user
	 */
	public void setUser(String user);
	
	/**
	 * Devuelve la accion del historico de cuenta 
	 * @return 
	 */
	public Action getAction();
	
	/**
	 * Pone laa ccion del historico de cuenta
	 * @param action
	 */
	public void setAction(Action action);

	/**
	 * Devuelve la fecha(dia y hora) del historico de cuenta 
	 * @return
	 */
	public Date getDate();
	
	/**
	 * Pone la fecha(dia y hora) del historico de cuenta 
	 * @param date
	 */
	public void setDate(Date date);
}
