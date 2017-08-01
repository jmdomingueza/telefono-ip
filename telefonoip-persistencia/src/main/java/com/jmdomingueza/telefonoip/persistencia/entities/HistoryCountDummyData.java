package com.jmdomingueza.telefonoip.persistencia.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Interfaz que define las operaciones de la entidad HistoryCountDummyData que 
 * corresponde con la tabla HISTORICO_CUENTA_DUMMY.
 * 
 * @author jmdomingueza
 *
 */
public interface HistoryCountDummyData extends Serializable{

	public enum Action{created,removed,modified,error}
	
	public enum State{registering,unregistering,registered,unregistered,error}
	
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
	 * Devuelve el usuario del historico de cuenta dummy.
	 * @return 
	 */
	public String getUser();

	/**
	 * Pone el usuario del historico de cuenta dummy.
	 * @param user
	 */
	public void setUser(String user);
	
	/**
	 * Devuelve la accion del historico de cuenta dummy. 
	 * @return 
	 */
	public Action getAction();

	/**
	 * Pone la accion del historico de cuenta dummy. 
	 * @param action
	 */
	public void setAction(Action action);
	
	/**
	 * Devuelve el estado del historico de cuenta dummy. 
	 * @return 
	 */
	public State getState();

	/**
	 * Pone el estado del historico de cuenta dummy. 
	 * @param action
	 */
	public void setState(State state);
	
	/**
	 * Devuelve la fecha(dia y hora) del historico de cuenta dummy 
	 * @return 
	 */
	public Date getDate();

	/**
	 * Pone la fecha(dia y hora) del historico de cuenta dummy
	 * @param date
	 */
	public void setDate(Date date);

	/**
	 * Metodo que devuelve el atributo editable del historico de cuenta dummy.
	 * @return
	 */
	public String getEditable();

	/**
	 * Metodo que pone el atributo editable del historico de cuenta dummy.
	 * @param editable
	 */
	public void setEditable(String editable);
	
}
