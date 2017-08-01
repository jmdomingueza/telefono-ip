package com.jmdomingueza.telefonoip.negocio.beans;

/**
 * Interfaz que define las operaciones del bean HistoryCountDummy.
 * 
 * @author jmdomingueza
 *
 */
public interface HistoryCountDummy extends HistoryCount{

	public enum State{registering,unregistering,unregistered,registered}
	
	/**
	 * Metodo que devuelve el atributo editable del historico de cuenta dummy.
	 * @return
	 */
	public String getEditable();

	/**
	 * Metodo que pone el atributo editable del historico de cuenta dummy.
	 * @param state
	 */
	public void setEditable(String editable);
	
	/** Devuelve el estado del historico de cuenta 
	 * @return 
	 */
	public State getState();
	
	/**
	 * Pone el estado del historico de cuenta
	 * @param state
	 */
	public void setState(State state);
}
