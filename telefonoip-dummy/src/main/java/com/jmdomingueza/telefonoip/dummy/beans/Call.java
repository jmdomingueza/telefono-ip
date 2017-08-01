package com.jmdomingueza.telefonoip.dummy.beans;

import java.io.Serializable;

public interface Call extends Serializable {

	public enum State{dialing,ringing,responding,talking,transfering,transfered, confering,conference,helding,held,retrieving,terminating,terminated,canceling,canceled,lost,error}
	
	public enum Direction{in,out}
	
	/**
	 * Metodo que pone el identificador de la llamada
	 * @return
	 */
	public int getId();

	/**
	 * Metodo que devuelve el identificador de la llamada
	 * @param id
	 */
	public void setId(int id);
	
	/**
	 * Metodo que devuelve la cuenta desde la que se hace la llamada
	 * @return
	 */
	public Count getCount();

	/**
	 * Metodo que pone la cuenta desde la que se hace la llamada
	 * @param count
	 */
	public void setCount(Count count) ;

	/**
	 * Metodo que devuelve el numero de destino o origen de la llamada,
	 * depende del sentido de la llamada.
	 * @return
	 */
	public String getNumber() ;

	/**
	 * Metodo que pone el destino de la llamada
	 * @param userRemote
	 */
	public void setNumber(String number);
	
	/**
	 * Metodo que devuelve el estado de la llamada
	 * @return
	 */
	public State getState();
	
	/**
	 * Metodo que pone el estado de la llamada
	 * @param state
	 */
	public void setState(State state);
	
	/**
	 * Metodo que devuelve el sentido de la llamada
	 * @return
	 */
	public Direction getDirection();
	
	/**
	 * Metodo que pone el sentido de la llamada
	 * @param direction
	 */
	public void setDirection(Direction direction);

	/**
	 * Metodo que devuelve la descripcion de la llamada
	 * @return
	 */
	public String getDescription();
	
	/**
	 * Metodo que pone la descripcion de la llamada
	 * @param description
	 */
	public void setDescription(String description);

}
