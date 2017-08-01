package com.jmdomingueza.telefonoip.negocio.services;

import com.jmdomingueza.telefonoip.negocio.beans.CallDummy;
import com.jmdomingueza.telefonoip.negocio.beans.CountDummy;

/**
 * Interfaz que define el servicio que gestiona las operaciones que se 
 * hacen para gestinar llamadas.
 * 
 * @author jmdomingueza
 *
 */
public interface CallDummyService{

	/**
	 *  Metodo que realiza una llamada en la capa dummy.
	 * @param count
	 * @param number
	 */
	public void dial(CountDummy count,String number);

	/**
	 *  Metodo que reponde una llamada en la capa dummy.
	 * @param callDummy
	 */
	public void response(CallDummy callDummy);
	
	/**
	 *  Metodo que cuelga una llamada en la capa dummy.
	 * @param callDummy
	 */
	public void drop(CallDummy callDummy);
	
	/**
	 *  Metodo que cancela una llamada en la capa dummy.
	 * @param callDummy
	 */
	public void cancel(CallDummy callDummy);
	
	/**
	 *  Metodo que transfiere  una llamada a otra en la capa dummy.
	 * @param callDummyTranfer
	 * @param callDummyTranfered
	 */
	public void transfer(CallDummy callDummyTranfer, CallDummy callDummyTranfered);
	
	/**
	 *  Metodo que pone en conferencia dos llamadas en la capa dummy.
	 * @param callDummyConference1
	 * @param callDummyConference2
	 */
	public void conference(CallDummy callDummyConference1, CallDummy callDummyConference2);
	
	/**
	 *  Metodo que pone en espera una llamada en la capa dummy.
	 * @param callDummy
	 */
	public void held(CallDummy callDummy);
	
	/**
	 *  Metodo que recupera una llamada en la capa dummy.
	 * @param callDummy
	 */
	public void retieve(CallDummy callDummy);
}