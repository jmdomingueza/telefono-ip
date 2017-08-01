package com.jmdomingueza.telefonoip.persistencia.daos;

import java.util.Collection;

import com.jmdomingueza.telefonoip.persistencia.entities.CountSipData;

/**
 * Interfaz que define las operaciones que se pueden realizar con la tabla
 * CUENTA_SIP.
 * @author jmdomingueza
 *
 */
public interface CountSipDao {

	/**
	 * Metodo que salva una cuenta sip en la tabla CUENTA_SIP.
	 * 
	 * @param user Nombre del usuario de la cuenta sip.
	 * @param password Password de la cuenta sip.
	 * @param hostServer  Host del servidor donde esta registrada la cuenta sip.
	  * @param state Estado de la cuenta sip.
	 */
	public void save(String user, String password, String hostServer, CountSipData.State state);
	
	/** 
     * Metodo que actualiza una cuenta sip en la tabla CUENTA_SIP.
	 * 
	 * @param user Nombre del usuario de la cuenta sip.
	 * @param hostServer  Host del servidor donde esta registrada la cuenta sip.
	 * @param password Password de la cuenta sip.
	 * @param state Estado de la cuenta sip.
	 */
	public void update(String user,  String hostServer,String password, CountSipData.State state);
	
	/**
	 * Metodo que borra una cuenta sip de la tabla CUENTA_SIP.
	 * 
	 * @param user Nombre del usuario de la cuenta sip, clave primaria.
	 * @param hostServer  Host del servidor donde esta registrada la cuenta, clave primaria. 
	 */
	public void remove(String user,String hostServer);
	
	/**
	 * Metodo que devuelve todas las cuentas sip que hay en la tabla 
	 * CUENTA_SIP.
	 * 
	 * @return Collecion de cuentas sip persistidas
	 */
	public Collection<CountSipData> findAll();
	
	 /** 
     * Metodo que busca una cuenta sip en la tabla CUENTA_SIP.
	 * 
	 * @param user Nombre del usuario de la cuenta sip, clave primaria.
	 * @param hostServer  Host del servidor donde esta registrada la cuenta sip, clave primaria.
	 * @return Cuenta sip persistida
	 */
	public CountSipData findbyId(String user,String hostServer);
}
