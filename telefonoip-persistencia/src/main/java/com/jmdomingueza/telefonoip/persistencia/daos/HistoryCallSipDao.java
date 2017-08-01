package com.jmdomingueza.telefonoip.persistencia.daos;

import java.util.Collection;
import java.util.Date;

import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallSipData;

/**
 * Interfaz que define las operaciones que se pueden realizar con la tabla
 * HISTORICO_LLAMADA_SIP.
 * @author jmdomingueza
 *
 */
public interface HistoryCallSipDao {

	/**
	 * Metodo que salva un historico de llamada sip en la tabla HISTORICO_LLAMADA_SIP.
	 * 
	 * @param idLlamada Identifidor de la llamada sip que corresponde con el historico de llamada sip.
	 * @param count Cuenta de la llamada sip que corresponde con el historico de llamada sip.
	 * @param number Numero de destino o origen de la llamada sip que corresponde
	 * 				 con el historico de llamada sip.
	 * @param state Estado del historico de llamada sip
	 * @param direction Sentido de la llamada sip que corresponde con el historico de llamada sip.
	 * @param description  Descripcion de la llamada sip que corresponde con el historico de llamada sip.
	 * @param date Fecha(dia y hora) del historico de llamada sip.
	 */
	public void save(int idLlamada, String count, String number, HistoryCallSipData.State state, HistoryCallSipData.Direction direction,
			String description, Date date);
	
	/** 
     * Metodo que actualiza un historico de llamada sip en la tabla HISTORICO_LLAMADA_SIP.
	 * 
	 * @param id Identifidor autogenerado del historico de llamada sip, clave priamria.
	 * @param idLlamada Identifidor de la llamada sip que corresponde con el historico de llamada sip.
	 * @param count Cuenta de la llamada sip que corresponde con el historico de llamada sip.
	 * @param number Numero de destino o origen de la llamada sip que corresponde
	 * 				 con el historico de llamada sip.
	 * @param state Estado del historico de llamada sip
	 * @param direction Sentido de la llamada sip que corresponde con el historico de llamada sip.
	 * @param description  Descripcion de la llamada sip que corresponde con el historico de llamada sip.
	 * @param date Fecha(dia y hora) del historico de llamada sip.
	 */
	public void update(Long id,int idLlamada, String count, String number, HistoryCallSipData.State state, HistoryCallSipData.Direction direction,
			String description, Date date);
	
	/**
	 * Metodo que borra un historico de llamada sip en la tabla HISTORICO_LLAMADA_SIP.
	 * 
	 * @param id Identifidor autogenerado del historico de llamada sip, clave priamria.
	 */
	public void remove(Long id);
	
	/**
	 * Metodo que borra todos los historicos de llamada sip en la tabla HISTORICO_LLAMADA_SIP.
	 * 
	 */
	public void removeAll();
	
	/**
	 * Metodo que devuelve todas las llamada sips sip finalizadas en la tabla
	 * HISTORICO_LLAMADA_SIP.
	 * 
	 * @return Collecion de los historicos de llamada sip persistidos
	 */
	public Collection<HistoryCallSipData> findAll();
	
	 /** 
     * Metodo que busca una llamada sip finalizada en la tabla HISTORICO_LLAMADA_SIP.
	 * 
	 *  @param id Identifidor autogenerado del historico de llamada sip, clave priamria.
	 * @return Historico de llamada sip  persistido.
	 */
	public HistoryCallSipData findbyId(Long id);
}
