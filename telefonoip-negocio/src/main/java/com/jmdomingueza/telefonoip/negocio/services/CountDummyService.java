package com.jmdomingueza.telefonoip.negocio.services;

import java.util.Collection;

import com.jmdomingueza.telefonoip.negocio.beans.CountDummy;

/**
 * Interfaz que define el servicio que gestiona las operaciones que se 
 * hacen para gestinar cuentas.
 * 
 * @author jmdomingueza
 *
 */
public interface CountDummyService{

	/**
	 * Metodo que crea una cuenta en la capa de persistencia e inicia
	 * el registro en la capa dummy.
	 * @param countDummy
	 */
	public void createCountDummy(CountDummy countDummy);

	/**
	 * Metodo que modifica una cuenta en la capa de persistencia y para/inicia
	 * el registro en la capa dummy.
	 * @param countDummy
	 */
	public void modifyCountDummy(CountDummy count);
	
	/**
	 * Metodo que borra una cuenta en la capa de persistencia y para
	 * el registro en la capa dummy.
	 * @param countDummy
	 */
	public void removeCountDummy(CountDummy count);
	
	/**
	 * Metodo que devuelve una cuenta de la capa de persistencia.
	 * @param user
	 * @return
	 */
	public CountDummy getCountDummyPersistence(String user);
	
	/**
	 * Metodo que devulve todas cuenta de la capa de persistencia.
	 * @return
	 */
	public Collection<CountDummy> getAllCountDummyPersistence();
	
	/**
	 * Metodo que registra una cuenta en la capa dummy.
	 * @param countDummy
	 */
	public void registerCountDummy(CountDummy countDummy);
	
	/**
	 * Metodo que desregistra una cuenta en la capa dummy.
	 * @param countDummy
	 */
	public void unregisterCountDummy(CountDummy countDummy);


}
