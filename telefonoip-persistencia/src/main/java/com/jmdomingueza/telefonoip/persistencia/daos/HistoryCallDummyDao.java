package com.jmdomingueza.telefonoip.persistencia.daos;

import java.util.Collection;
import java.util.Date;

import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallDummyData;

/**
 * Interfaz que define las operaciones que se pueden realizar con la tabla
 * HISTORICO_LLAMADA_DUMMY.
 * @author jmdomingueza
 *
 */
public interface HistoryCallDummyDao {

	/**
	 * Metodo que salva un historico de llamada dummy en la tabla HISTORICO_LLAMADA_DUMMY.
	 * 
	 *@param idLlamada Identifidor de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param count Cuenta de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param number Numero de destino o origen de la llamada dummy que corresponde
	 * 				 con el historico de llamada dummy.
	 * @param state Estado del historico de llamada dummy
	 * @param direction Sentido de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param description  Descripcion de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param date Fecha(dia y hora) del historico de llamada dummy.
	 */
	public void save(int idLlamada, String count, String number, HistoryCallDummyData.State state, HistoryCallDummyData.Direction direction,
			String description, Date date);
	
	/** 
     * Metodo que actualiza un historico de llamada dummy en la tabla HISTORICO_LLAMADA_DUMMY.
	 * 
	 * @param id Identificador del historico de llamada dummy, clave primaria.
	 * @param idLlamada Identifidor de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param count Cuenta de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param number Numero de destino o origen de la llamada dummy que corresponde
	 * 				 con el historico de llamada dummy.
	 * @param state Estado del historico de llamada dummy
	 * @param direction Sentido de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param description  Descripcion de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param date Fecha(dia y hora) del historico de llamada dummy.
	 */
	public void update(Long id, int idLlamada, String count, String number, HistoryCallDummyData.State state, HistoryCallDummyData.Direction direction,
			String description, Date date);
	
	/**
	 * Metodo que borra un historico de llamada dummy en la tabla HISTORICO_LLAMADA_DUMMY.
	 * 
	 * @param id Identifidor autogenerado del historico de llamada dummy, clave priamria.
	 */
	public void remove(Long id);
	
	/**
	 * Metodo que borra todos los historicos de llamada dummy en la tabla HISTORICO_LLAMADA_DUMMY.
	 * 
	 */
	public void removeAll();
	
	/**
	 * Metodo que devuelve todos los historicos de llamada dummy que hay en la tabla
	 * HISTORICO_LLAMADA_DUMMY.
	 * 
	 * @return Collecion de los historicos de llamada dummy persistidos
	 */
	public Collection<HistoryCallDummyData> findAll();
	
	 /** 
     * Metodo que busca un historico de llamada dummy en la tabla HISTORICO_LLAMADA_DUMMY.
	 * 
	 *  @param id Identifidor autogenerado del historico de llamada dummy, clave priamria.
	 * @return Historico de Lamada dummy persistido.
	 */
	public HistoryCallDummyData findbyId(Long id);
}
