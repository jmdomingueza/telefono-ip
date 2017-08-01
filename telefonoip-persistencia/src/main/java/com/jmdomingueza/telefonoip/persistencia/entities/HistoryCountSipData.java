package com.jmdomingueza.telefonoip.persistencia.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Interfaz que define las operaciones de la entidad HistoryCountSipData que 
 * corresponde con la tabla HISTORICO_CUENTA_SIP.
 * @author jmdomingueza
 *
 */
public interface HistoryCountSipData extends Serializable{

	public enum Action{created,removed,modified,error}
	
	public enum State{registering,unregistering,unregistered,registered,unauthorized,error}
	
	/**
	 * Devuelve el identifidor autogenerado del historico de cuenta sip, clave primaria.
	 * @return 
	 */
	public Long getId();

	/**
	 * Pone identifidor autogenerado del historico de cuenta sip, clave primaria.
	 * @param user
	 */
	public void setId(Long id);
	
	/**
	 * Devuelve el usuario de la cuenta sip.
	 * @return 
	 */
	public String getUser();

	/**
	 * Pone el usuario de la cuenta sip.
	 * @param user
	 */
	public void setUser(String user);

	/**
	 * Devuelve el host del servidor donde esta registrada la cuenta sip.
	 * @return
	 */
	public String getHostServer();

	/**
	 * Pone el host del servidor donde esta registrada la cuenta sip. 
	 * @param hostServer
	 */
	public void setHostServer(String hostServer);
	
	/**
	 * Devuelve la accion del historico de cuenta sip. 
	 * @return 
	 */
	public Action getAction();

	/**
	 * Pone la accion del historico de cuenta sip. 
	 * @param action
	 */
	public void setAction(Action action);
	
	/**
	 * Devuelve el estado del historico de cuenta sip. 
	 * @return 
	 */
	public State getState();

	/**
	 * Pone el estado del historico de cuenta sip. 
	 * @param action
	 */
	public void setState(State state);

	/**
	 * Devuelve la fecha(dia y hora) del historico de cuenta sip 
	 * @return 
	 */
	public Date getDate();

	/**
	 * Pone la fecha(dia y hora) del historico de cuenta sip
	 * @param date
	 */
	public void setDate(Date date);
	
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

	
	
}
