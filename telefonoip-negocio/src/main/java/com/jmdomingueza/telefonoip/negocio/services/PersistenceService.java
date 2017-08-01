package com.jmdomingueza.telefonoip.negocio.services;

import java.util.Collection;

import com.jmdomingueza.telefonoip.negocio.beans.CountDummy;
import com.jmdomingueza.telefonoip.negocio.beans.CountSip;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallSip;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountSip;

/**
 * Interfaz que define el servicio que gestiona las operaciones que 
 * se pueden hacer con la capa de persistencia.
 * 
 * @author jmdomingueza
 *
 */
public interface PersistenceService {
	
	/**
	 * Metodo que crea una cuenta dummy en la capa de persistencia.
	 * @param countDummy Cuenta dummy antes de persistirla.
	 */
	public void addCountDummy(CountDummy countDummy);
	
	/**
	 * Metodo que actualiza una cuenta dummy en la capa de persistencia.
	 * @param countDummy Cuenta dummy antes de persistirla.
	 */
	public void updateCountDummy(CountDummy countDummy);
	
	/**
	 * Metodo que borra una cuenta dummy de la capa de persistencia.
	 * @param countDummy Cuenta dummy antes de persistirla.
	 */
	public void removeCountDummy(CountDummy countDummy);
	
	/**
	 *  Metodo que devuelve la cuenta dummy que corresponde con el 
	 *  identificador que pasamos. 
	 * @param user Nombre del usuario de la cuenta dummy.
	 * @return  Cuenta dummy persistida
	 */
	public CountDummy getCountDummy(String user);

	/**
	 *  Metodo que devuelve todas las cuentas dummy que hay en la capa de
	 *  persistencia.
	 * @return Coleccion de cuentas dummy persistidas
	 */
	public Collection<CountDummy> getAllCountDummy();
	
	/**
	 * Metodo que crea una cuenta sip en la capa de persistencia.
	 * @param countSip Cuenta sip antes de persistirla.
	 */
	public void addCountSip(CountSip countSip);
	
	/**
	 * Metodo que actualiza una cuenta sip en la capa de persistencia.
	 * @param countSip Cuenta sip antes de persistirla.
	 */
	public void updateCountSip(CountSip countSip);
	
	/**
	 * Metodo que borra una cuenta sip de la capa de persistencia.
	 * @param countSip Cuenta sip antes de persistirla.
	 */
	public void removeCountSip(CountSip countSip);
	
	/**
	 * Metodo que devuelve la cuenta sip que corresponde con el 
	 * identificador que pasamos. 
	 * @param user Nombre del usuario de la cuenta sip.
	 * @param hostServer Host del servidor donde esta registrada la cuenta sip.
	 * @return  Cuenta sip persistida
	 */
	public CountSip getCountSip(String user, String hostServer);

	/**
	 *  Metodo que devuelve todas las cuentas sip que hay en la capa de
	 *  persistencia.
	 * @return Coleccion de cuentas sip persistidas
	 */
	public Collection<CountSip> getAllCountSip();

	/**
	 * Metodo que crea un historico de llamada dummy en la capa de persistencia.
	 * @param historyCallDummy Historico de llamada dummy antes de persistirlo.
	 * 
	 */
	public void addHistoryCallDummy(HistoryCallDummy historyCallDummy);

	/**
	 * Metodo que borra un historico de llamada dummy de la capa de persistencia.
	 * @param historyCallDummy Historico de llamada dummy antes de persistirlo.
	 */
	public void removeHistoryCallDummy(HistoryCallDummy historyCallDummy);
	
	/**
	 * Metodo que borra todos los historicos de llamada dummy de la capa de persistencia.
	 */
	public void removeAllHistoryCallDummy();
	
	/**
	 *  Metodo que devuelve todos los historicos de llamadas dummy que hay en la capa de
	 *  persistencia.
	 * @return Coleccion de historicos de llamadas dummy persistidos
	 */
	public Collection<HistoryCallDummy> getAllHistoryCallDummy();
	
	/**
	 * Metodo que crea un historico de llamada sip en la capa de persistencia.
	 * @param historyCallSip Historico de llamada sip antes de persistirlo.
	 */
	public void addHistoryCallSip(HistoryCallSip historyCallSip);

	/**
	 * Metodo que borra un historico de llamada sip de la capa de persistencia.
	 * @param historyCallSip Historico de llamada sip antes de persistirlo.
	 */
	public void removeHistoryCallSip(HistoryCallSip historyCallSip);
	
	/**
	 * Metodo que borra todos los historicos de llamada sip de la capa de persistencia.
	 */
	public void removeAllHistoryCallSip();
	
	/**
	 *  Metodo que devuelve todas los historicos de llamadas sip que hay en la capa de
	 *  persistencia.
	 * @return Coleccion de historicos de llamadas sip persistidos
	 */
	public Collection<HistoryCallSip> getAllHistoryCallSip();
	
	
	/**
	 * Metodo que crea un historico de cuenta dummy en la capa de persistencia.
	 * @param historyCountDummy Historico de cuenta dummy antes de persistirlo.
	 */
	public void addHistoryCountDummy(HistoryCountDummy historyCountDummy);

	/**
	 * Metodo que borra un historico de cuenta dummy de la capa de persistencia.
	 * @param historyCountDummy Historico de cuenta dummy antes de persistirlo.
	 */
	public void removeHistoryCountDummy(HistoryCountDummy historyCountDummy);
	
	/**
	 * Metodo que borra todos los historicos de cuenta dummy de la capa de persistencia.
	 */
	public void removeAllHistoryCountDummy();
	
	/**
	 *  Metodo que devuelve todos los historicos de cuentas dummy  que hay en la capa de
	 *  persistencia.
	 * @return Coleccion de historicos de cuentas dummy persistidos
	 */
	public Collection<HistoryCountDummy> getAllHistoryCountDummy();
	
	/**
	 * Metodo que crea un historico de cuenta sip  en la capa de persistencia.
	 * @param historyCountSip Historico de cuenta sip  antes de persistirlo.
	 */
	public void addHistoryCountSip(HistoryCountSip historyCountSip);

	/**
	 * Metodo que borra un historico de cuenta sip  de la capa de persistencia.
	 * @param historyCountSip Historico de cuenta sip antes de persistirlo.
	 */
	public void removeHistoryCountSip(HistoryCountSip historyCountSip);
	
	/**
	 * Metodo que borra todos los historicos de cuenta sip de la capa de persistencia.
	 */
	public void removeAllHistoryCountSip();

	/**
	 *  Metodo que devuelve todas los historicos de cuentas sip  que hay en la capa de
	 *  persistencia.
	 * @return Coleccion de historicos de cuentas sip persistidos
	 */
	public Collection<HistoryCountSip> getAllHistoryCountSip();

}
