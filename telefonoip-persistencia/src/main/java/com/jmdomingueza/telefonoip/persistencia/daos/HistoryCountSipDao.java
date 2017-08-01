package com.jmdomingueza.telefonoip.persistencia.daos;

import java.util.Collection;
import java.util.Date;

import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountSipData;

/**
 * Interfaz que define las operaciones que se pueden realizar con la tabla
 * HISTORICO_CUENTA_SIP.
 * @author jmdomingueza
 *
 */
public interface HistoryCountSipDao {

	/**
	 * Metodo que salva un historico de cuenta sip en la tabla HISTORICO_CUENTA_SIP.
	 * 
	 * @param user Nombre del usuario del historico de cuenta sip.
	 * @param hostServer Host del servidor donde esta registrado el historico de cuenta sip.
	 * @param action Accion del historico de la cuenta sip.
	 * @param state Estado del historico de cuenta sip.
	 * @param date Fecha(dia y hora) del historico de cuenta sip.
	 * @param password Password del historico de cuenta sip
	 * 
	 */
	public void save(String user, String hostServer, HistoryCountSipData.Action action, HistoryCountSipData.State state, Date date, String password);
	
	/** 
     * Metodo que actualiza un historico de cuenta sip en la tabla HISTORICO_CUENTA_SIP.
	 * 
	 * @param id Identifidor autogenerado del historico de cuenta sip, clave priamria.
	 * @param user Nombre del usuario del historico de cuenta sip.
	 * @param hostServer  Host del servidor donde esta registrado el historico de cuenta sip.
	 * @param action Accion del historico de la cuenta sip.
	 * @param state Estado del historico de cuenta sip.
	 * @param date Fecha(dia y hora) del historico de cuenta sip.
	 * @param password Password del historico de cuenta sip
	 */
	public void update(Long id,String user, String hostServer, HistoryCountSipData.Action action,  HistoryCountSipData.State state, Date date, String password);
	
	/**
	 * Metodo que borra un historico de cuenta sip de la tabla HISTORICO_CUENTA_SIP.
	 * 
	 * @param id Identifidor autogenerado del historico de cuenta sip, clave priamria.
	 */
	public void remove(Long id);
	
	/**
	 * Metodo que borra todos los historicos de cuenta sip en la tabla HISTORICO_CEUNTA_SIP.
	 * 
	 */
	public void removeAll();
	
	/**
	 * Metodo que devuelve todos los historicos de cuenta sip que hay en la tabla 
	 * HISTORICO_CUENTA_SIP.
	 * 
	 * @return Collecion de historicos de cuenta sip persistidos
	 */
	public Collection<HistoryCountSipData> findAll();
	
	 /** 
     * Metodo que busca un historico de cuenta sip en la tabla HISTORICO_CUENTA_SIP.
	 * 
	 *@param id Identifidor autogenerado del historico de cuenta sip, clave priamria.
	 * @return Historico de cuenta sip persistido
	 */
	public HistoryCountSipData findbyId(Long id);
}
