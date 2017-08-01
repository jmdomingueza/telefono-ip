package com.jmdomingueza.telefonoip.negocio.services;

import java.util.Collection;

import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallSip;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountSip;

/**
 * Interfaz que define el servicio que gestiona las operaciones que se 
 * hacen para gestinar el historico sip.
 * 
 * @author jmdomingueza
 *
 */
public interface HistorySipService{

	/**
	 *  Metodo que crea un historico de llamada sip.
	 * @param historyCallSip
	 */
	public void createHistoryCall(HistoryCallSip historyCallSip);
	
	/**
	 *  Metodo que elimina un historico de llamada sip.
	 * @param historyCallSip
	 */
	public void removeHistoryCall(HistoryCallSip historyCallSip);

	/**
	 * Metodo que elimina todos los historicos de llamada sip.
	 */
	public void removeAllHistoryCall();
	
	/**
	 * Metodo que devulve todos los historicos de llamadas sip.
	 * @return
	 */
	public Collection<HistoryCallSip> getAllHistoryCall();
	
	/**
	 *  Metodo que crea un historico de cuenta sip.
	 * @param historyCountSip
	 */
	public void createHistoryCount(HistoryCountSip historyCountSip);
	
	/**
	 *  Metodo que elimina un historico de cuenta sip.
	 * @param historyCountSip
	 */
	public void removeHistoryCount(HistoryCountSip historyCountSip);

	/**
	 * Meotod que elimina todos los historicos de cuenta sip.
	 */
	public void removeAllHistoryCount();
	
	/**
	 * Metodo que devulve todos los historicos de cuentas sip.
	 * @return
	 */
	public Collection<HistoryCountSip> getAllHistoryCount();
	
}