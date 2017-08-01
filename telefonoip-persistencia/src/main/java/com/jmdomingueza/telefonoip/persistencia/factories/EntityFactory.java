package com.jmdomingueza.telefonoip.persistencia.factories;

import java.util.Date;

import org.apache.log4j.Logger;

import com.jmdomingueza.telefonoip.persistencia.entities.CountDummyData;
import com.jmdomingueza.telefonoip.persistencia.entities.CountDummyDataImpl;
import com.jmdomingueza.telefonoip.persistencia.entities.CountSipData;
import com.jmdomingueza.telefonoip.persistencia.entities.CountSipDataImpl;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallDummyData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallDummyDataImpl;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallSipData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallSipDataImpl;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountDummyData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountDummyDataImpl;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountSipData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountSipDataImpl;

/** 
 * Clase que contiene las operaciones para crear las entidades 
 * de la capa de persistencia.
 * 
 * @author jmdomingueza
 *
 */
public class EntityFactory {
	
	private final static Logger logger = Logger.getLogger(EntityFactory.class);

	/**
	 * Metodo que crea la entidad de CountDummyData con todos los parametros editables
	 * @param user Nombre del usuario de la cuenta dummy.
	 * @param editable Atributo editable de la cuenta dummy.
	 * @param state Estado de la cuenta dummy.
	 * 
	 * @return Cuenta dummy creada
	 */
	public static CountDummyData createCountDummyData(String user, String editable, CountDummyData.State state){
		CountDummyData countDummyData;
		
		logger.trace("Enter createCountDummyData - con parametros editables");
		
		countDummyData =  new CountDummyDataImpl(user,editable,state);
		
		logger.trace("Exit createCountDummyData - con parametros editables");
		return countDummyData;
	}

	/**
	 * Metodo que crea la entidad de CountSipData con todos los parametros editables
	 * @param user Nombre del usuario de la cuenta sip.
	 * @param hostServer  Host del servidor donde esta registrada la cuenta sip
	 * @param password Password de la cuenta sip
	 * @param state Estado de la cuenta sip
	 * 
	 * @return Cuenta sip creada
	 */
	public static CountSipData createCountSipData(String user, String hostServer,String password, CountSipData.State state) {
		CountSipData countSipData;
		
		logger.trace("Enter createCountSipData - con parametros editables");
		
		countSipData = new CountSipDataImpl(user,hostServer, password,state);
				
		logger.trace("Exit createCountSipData - con parametros editables");
		return countSipData;
	}
	
	/**
	 * Metodo que crea la entidad de HistoryCallDummyData con todos los parametros editables
	 * @param id Identificador del historico de llamada dummy.
	 * @param count Cuenta desde la que se hizo el historico de llamada dummy.
	 * @param number Numero de destino o origen del historico de llamada dummy,
	 * 				 depende del sentido del historico de llamada dummy.
	 * @param state Estado del historico de llamada dummy.
	 * @param direction Sentido del historico de llamada dummy.
	 * @param description Descripcion del historico de llamada dummy.
	 * @param date Fecha(dia y hora) del historico de llamada dummy.
	 * 
	 * @return Historico de cuenta dummy creado
	 */
	public static HistoryCallDummyData createHistoryCallDummyData(int id, String count, String number, HistoryCallDummyData.State state, HistoryCallDummyData.Direction direction,
			String description, Date date) {
		HistoryCallDummyData historyCallDummyData;
		
		logger.trace("Enter createHistoryCallDummyData - con parametros editables");
		
		historyCallDummyData = new HistoryCallDummyDataImpl(id, count, number, state, direction, description, date);
		
		logger.trace("Exit createHistoryCallDummyData - con parametros editables");
		return historyCallDummyData;
	}
	
	/**
	 * Metodo que crea la entidad de HistoryCallSipData con todos los parametros editables
	 * @param id Identificador del historico de llamada sip.
	 * @param count Cuenta desde la que se hizo el historico de llamada sip.
	 * @param number Numero de destino o origen del historico de llamada sip,
	 * 				 depende del sentido del historico de llamada sip.
	 * @param state Estado del historico de llamada sip.
	 * @param direction Sentido del historico de llamada sip.
	 * @param description Descripcion del historico de llamada sip.
	 * @param date Fecha(dia y hora) del historico de llamada sip.
	 * 
	 * @return Historico de cuenta sip creado
	 */
	public static HistoryCallSipData createHistoryCallSipData(int id, String count, String number, HistoryCallSipData.State state, HistoryCallSipData.Direction direction,
			String description, Date date) {
		HistoryCallSipData historyCallSipData;
		
		logger.trace("Enter createHistoryCallSipData - con parametros editables");
		
		historyCallSipData = new HistoryCallSipDataImpl(id, count, number, state, direction, description,date);
		
		logger.trace("Exit createHistoryCallSipData - con parametros editables");
		return historyCallSipData;
	}
	
	/**
	 * Metodo que crea la entidad de HistoryCountDummyData con todos los parametros editables
	 * @param user Nombre del usuario del historico de cuenta dummy.
	 * @param action Accion del historico de cuenta dummy. 
	 * @param state Estado del historico de cuenta dummy.
	 * @param date Fecha(dia y hora) del historico de cuenta dummy.
	 * @param editable Atributo editable del historico de cuenta dummy.
	 * 
	 * @return Historico de cuenta dummy creado
	 */
	public static HistoryCountDummyData createHistoryCountDummyData(String user, HistoryCountDummyData.Action action, HistoryCountDummyData.State state, Date date, String editable){
		HistoryCountDummyData historyCountDummyData;
		
		logger.trace("Enter createHistoryCountDummyData - con parametros editables");
		
		historyCountDummyData =  new HistoryCountDummyDataImpl(user, action, state, date,editable);
		
		logger.trace("Exit createHistoryCountDummyData - con parametros editables");
		return historyCountDummyData;
	}

	/**
	 * Metodo que crea la entidad de HistoryCountSipData con todos los parametros editables
	 * @param user Nombre del usuario del historico de cuenta sip
	 * @param hostServer  Host del servidor donde esta registrado el historico de cuenta sip
	 * @param action Accion del historico de cuenta sip.
	 * @param state Estado del historico de cuenta sip.
	 * @param date Fecha(dia y hora) del historico de cuenta sip.
	 * @param password Password del historico de cuenta sip.
	 * 
	 * @return Historico de cuenta sip creado
	 */
	public static HistoryCountSipData createHistoryCountSipData(String user,String hostServer, HistoryCountSipData.Action action, HistoryCountSipData.State state, Date date, String password) {
		HistoryCountSipData historyCountSipData;
		
		logger.trace("Enter createHistoryCountSipData - con parametros editables");
		
		historyCountSipData = new HistoryCountSipDataImpl(user, hostServer, action, state,date,password);
				
		logger.trace("Exit createHistoryCountSipData - con parametros editables");
		return historyCountSipData;
	}
}
