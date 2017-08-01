package com.jmdomingueza.telefonoip.dummy.factories;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.dummy.beans.Call;
import com.jmdomingueza.telefonoip.dummy.beans.CallImpl;
import com.jmdomingueza.telefonoip.dummy.beans.Count;
import com.jmdomingueza.telefonoip.dummy.beans.CountImpl;

/** 
 * Clase que contiene las operaciones para crear los beans 
 *  de dummy.
 * 
 * @author jmdomingueza
 *
 */
public class BeanDummyFactory {

	private static final Log logger = LogFactory.getLog(BeanDummyFactory.class);
	
	/**
	 * Metodo que crea el bean de Count con todos los parametros
	 * @param user Nombre del usuario
	 * @param state Estado de la cuenta
	 * @return
	 */
	public static Count createCount(String user,Count.State state) {
		Count count;
		
		logger.trace("Enter createCount");
		
		count = new CountImpl();
		count.setUser(user);
		count.setState(state);
		
		logger.trace("Exit createCount");
		return count;
	}
	
	/**
	 * Metodo que crea el bean de Call con todos los parametros
	 * @param id Identificador de la llamada
	 * @param count Cuenta desde la que se hace la llamada
	 * @param number Numero de destino o origen de la llamada, depende del sentido de la llamada
	 * @param state Estado de la llamada
	 * @param direction Sentido de la llamada
	 * @param description Descripcion de la llamada
	 * @return Llamada creada 
	 */
	public static Call createCall(int id, Count count, String number, Call.State state,
			Call.Direction direction, String description) {
		Call call;
		
		logger.trace("Enter createCall");
		
		call = new CallImpl();
		call.setId(id);
		call.setCount(count);
		call.setNumber(number);
		call.setState(state);
		call.setDirection(direction);
		call.setDescription(description);
		
		logger.trace("Exit createCall");
		return call;
	}

}
