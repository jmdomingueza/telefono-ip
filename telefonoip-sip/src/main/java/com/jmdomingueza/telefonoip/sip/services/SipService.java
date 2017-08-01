package com.jmdomingueza.telefonoip.sip.services;

import com.jmdomingueza.telefonoip.sip.beans.Call;
import com.jmdomingueza.telefonoip.sip.beans.Count;

/**
 * Clase que implemnenta la funcionalidad SIP que se es visible desde la capa de 
 * negocio.
 * @author jmdomingueza
 *
 */
public interface SipService {

	
	public void destroy();
	
	/**
	 * Metodo que crea y envia un REGISTER 
	 * @param count
	 * @throws Exception
	 */
	public void register(Count count) throws Exception;
	
	/**
	 * Metodo que crea y envia un REGISTER a 0 para que se desregistre una cuenta
	 * @param count
	 * @throws Exception
	 */
	public void unregister(Count count) throws Exception;
	
	/**
	 * Metodo que crea y envia un INVITE 
	 * @param call
	 * @throws Exception
	 */
	public void invite(Call call) throws Exception;
	
	/**
	 * Metodo que crea y envia un BYE
	 * @param call
	 * @throws Exception 
	 */
	public void bye(Call call) throws Exception;
	
}
