package com.jmdomingueza.telefonoip.negocio.services;

import com.jmdomingueza.telefonoip.negocio.beans.CallSip;
import com.jmdomingueza.telefonoip.negocio.beans.CountSip;

public interface CallSipService{

	/**
	 *  Metodo que realiza una llamada en la capa sip.
	 * @param count
	 * @param number
	 */
	public void dial(CountSip count,String number);

	/**
	 *  Metodo que reponde una llamada en la capa sip.
	 * @param callSip
	 */
	public void response(CallSip callSip);
	
	/**
	 *  Metodo que cuelga una llamada en la capa sip.
	 * @param callSip
	 */
	public void drop(CallSip callSip);
	
	/**
	 *  Metodo que cancela una llamada en la capa sip.
	 * @param callSip
	 */
	public void cancel(CallSip callSip);
	
	/**
	 *  Metodo que transfiere  una llamada a otra en la capa sip.
	 * @param callSipTranfer
	 * @param callSipTranfered
	 */
	public void transfer(CallSip callSipTranfer, CallSip callSipTranfered);
	
	/**
	 *  Metodo que pone en conferencia dos llamadas en la capa sip.
	 * @param callSipConference1
	 * @param callSipConference2
	 */
	public void conference(CallSip callSipConference1, CallSip callSipConference2);
	
	/**
	 *  Metodo que pone en espera una llamada en la capa sip.
	 * @param callSip
	 */
	public void held(CallSip callSip);
	
	/**
	 *  Metodo que recupera una llamada en la capa sip.
	 * @param callSip
	 */
	public void retieve(CallSip callSip);

}
