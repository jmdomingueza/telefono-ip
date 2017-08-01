package com.jmdomingueza.telefonoip.persistencia.daos;

import java.util.Collection;

import com.jmdomingueza.telefonoip.persistencia.entities.CountDummyData;

/**
 * Interfaz que define las operaciones que se pueden realizar con la tabla
 * CUENTA_DUMMY.
 * @author jmdomingueza
 *
 */
public interface CountDummyDao {

	/**
	 * Metodo que salva una cuenta dummy en la tabla CUENTA_DUMMY.
	 * 
	 * @param user Nombre del usuario de la cuenta dummy.
	 * @param editable Atributo editable de la cuenta dummy.
	 * @param state Estado de la cuenta dummy.
	 */
	public void save(String user, String editable, CountDummyData.State state);
	
	/** 
     * Metodo que actualiza una cuenta dummy en la tabla CUENTA_DUMMY.
	 * 
	 * @param user Nombre del usuario de la cuenta dummy, clave primaria.
	 * @param editable Atributo editable de la cuenta dummy.
	 * @param state Estado de la cuenta dummy.
	 */
	public void update(String user, String editable, CountDummyData.State state);
	
	/**
	 * Metodo que borra una cuenta dummy de la tabla CUENTA_DUMMY.
	 * 
	 * @param user Nombre del usuario de la cuenta dummy, clave primaria.
	 */
	public void remove(String user);
	
	/**
	 * Metodo que devuelve todas las cuentas dummy que hay en la tabla 
	 * CUENTA_DUMMY.
	 * 
	 * @return Collecion de cuentas dummy persistidas
	 */
	public Collection<CountDummyData> findAll();
	
	 /** 
     * Metodo que busca una cuenta dummy en la tabla CUENTA_DUMMY.
	 * 
	 * @param user Nombre del usuario de la cuenta dummy, clave primaria.
	 * @return Cuenta dummy persistida
	 */
	public CountDummyData findbyId(String user);
}
