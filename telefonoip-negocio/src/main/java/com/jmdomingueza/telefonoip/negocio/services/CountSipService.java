package com.jmdomingueza.telefonoip.negocio.services;

import java.util.Collection;

import com.jmdomingueza.telefonoip.negocio.beans.CountSip;

public interface CountSipService {

	/**
	 * Metodo que crea una cuenta en la capa de persistencia e inicia
	 * el registro en la capa sip.
	 * @param countSip
	 */
	public void createCountSip(CountSip countSip);

	/**
	 * Metodo que modifica una cuenta en la capa de persistencia y para/inicia
	 * el registro en la capa sip.
	 * @param countSip
	 */
	public void modifyCountSip(CountSip countSip);
	
	/**
	 * Metodo que borra una cuenta en la capa de persistencia y para
	 * el registro en la capa sip.
	 * @param countSip
	 */
	public void removeCountSip(CountSip countSip);
	
	/**
	 * Metodo que devuelve una cuenta de la capa de persistencia.
	 * @param user
	 * @param hostServer
	 */
	public CountSip getCountSipPersistence(String user, String hostServer);
	
	/**
	 * Metodo que devulve todas cuenta de la capa de persistencia.
	 * @param countSip
	 */
	public Collection<CountSip> getAllCountSipPersistence();
	
	/**
	 * Metodo que registra una cuenta en la capa sip.
	 * @param countSip
	 */
	public void registerCountSip(CountSip countSip);
	
	/**
	 * Metodo que desregistra una cuenta en la capa sip.
	 * @param countSip
	 */
	public void unregisterCountSip(CountSip countSip);

}
