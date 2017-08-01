package com.jmdomingueza.telefonoip.persistencia.daos;

import java.util.Collection;
import java.util.Date;

import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountDummyData;

/**
 * Interfaz que define las operaciones que se pueden realizar con la tabla
 * HISTORICO_CUENTA_DUMMY.
 * @author jmdomingueza
 *
 */
public interface HistoryCountDummyDao {

	/**
	 * Metodo que salva un historico de cuenta dummy en la tabla 
	 * HISTORICO_CUENTA_DUMMY.
	 * 
	 * @param user Nombre del usuario del historico de cuenta dummy.
	 * @param action Accion del historico de la cuenta dummy.
	 * @param state Estado del historico de la cuenta dummy.
	 * @param date Fecha(dia y hora) del historico de cuenta dummy.
	 * @param editable Atributo editable del historico de cuenta dummy.
	 */
	public void save(String user, HistoryCountDummyData.Action action, HistoryCountDummyData.State state, Date date,String editable);
	
	/** 
     * Metodo que actualiza un historico de cuenta dummy en la tabla 
     * HISTORICO_CUENTA_DUMMY.
	 * 
	 * @param id Identifidor autogenerado del historico de cuenta dummy, clave priamria.
	 * @param user Nombre del usuario del historico de cuenta dummy.
	 * @param action Accion del historico de la cuenta dummy.
	 * @param state Estado del historico de la cuenta dummy.
	 * @param date Fecha(dia y hora) del historico de cuenta dummy.
	 * @param editable Atributo editable del historico de cuenta dummy.
	 */
	public void update(Long  id,String user, HistoryCountDummyData.Action action, HistoryCountDummyData.State state, Date date, String editable);
	
	/**
	 * Metodo que borra un historico de cuenta dummy de la tabla 
	 * HISTORICO_CUENTA_DUMMY.
	 * 
	 * @param id Identifidor autogenerado del historico de cuenta dummy.
	 */
	public void remove(Long  id);
	
	/**
	 * Metodo que borra todos los historicos de cuenta dummy en la tabla HISTORICO_CUENTA_DUMMY.
	 * 
	 */
	public void removeAll();
	
	/**
	 * Metodo que devuelve todos los historicos de  cuenta dummys dummy que hay en la 
	 * tabla HISTORICO_CUENTA_DUMMY.
	 * 
	 * @return Collecion de historicos de cuenta dummys dummy persistidos
	 */
	public Collection<HistoryCountDummyData> findAll();
	
	 /** 
     * Metodo que busca un historico de cuenta dummy en la tabla 
     * HISTORICO_CUENTA_DUMMY.
	 * 
	 * @param user Nombre del usuario.
	 * @return Historico de cuenta dummy persistido
	 */
	public HistoryCountDummyData findbyId(Long  id);
}
