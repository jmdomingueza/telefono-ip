package com.jmdomingueza.telefonoip.negocio.beans;

/**
 * Interfaz que define las operaciones del bean CountDummy.
 * 
 * @author jmdomingueza
 *
 */
public interface CountDummy extends Count{

	public enum State{registering,unregistering,unregistered,registered}

	/**
	 * Metodo que devuelve el atributo editable.
	 * @return
	 */
	public String getEditable();

	/**
	 * Metodo que pone el atributo editable.
	 * @param state
	 */
	public void setEditable(String editable);
	
	/**
	 * Metodo que devuelve el estado de la cuenta.
	 * @return
	 */
	public State getState();
	
	/**
	 * Metodo que pone el estado de la cuenta.
	 * @param state
	 */
	public void setState(State state);

	
}
