package com.jmdomingueza.telefonoip.negocio.services;

import java.util.Collection;

import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountDummy;

/**
 * Interfaz que define el servicio que gestiona las operaciones que se 
 * hacen para gestinar el historico dummy.
 * 
 * @author jmdomingueza
 *
 */
public interface HistoryDummyService{

	/**
	 *  Metodo que crea un historico de llamada dummy.
	 * @param historyCallDummy
	 */
	public void createHistoryCall(HistoryCallDummy historyCallDummy);
	
	/**
	 *  Metodo que elimina un historico de llamada dummy.
	 * @param historyCallDummy
	 */
	public void removeHistoryCall(HistoryCallDummy historyCallDummy);

	/**
	 * Metodo que elimina todos los historicos de llamada dummy.
	 */
	public void removeAllHistoryCall();
	
	/**
	 * Metodo que devulve todos los historicos de llamadas dummy.
	 * @return
	 */
	public Collection<HistoryCallDummy> getAllHistoryCall();
	
	/**
	 *  Metodo que crea un historico de cuenta dummy.
	 * @param historyCountDummy
	 */
	public void createHistoryCount(HistoryCountDummy historyCountDummy);
	
	/**
	 *  Metodo que elimina un historico de cuenta dummy.
	 * @param historyCountDummy
	 */
	public void removeHistoryCount(HistoryCountDummy historyCountDummy);

	/**
	 * Meotod que elimina todos los historicos de cuenta dummy.
	 */
	public void removeAllHistoryCount();
	
	/**
	 * Metodo que devulve todos los historicos de cuentas dummy.
	 * @return
	 */
	public Collection<HistoryCountDummy> getAllHistoryCount();

}