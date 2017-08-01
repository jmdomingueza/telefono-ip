package com.jmdomingueza.telefonoip.negocio.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Interfaz que define las operaciones del bean HistoryCall.
 * @author jmdomingueza
 *
 */
public interface HistoryCall extends Serializable {

	public enum Action{created,removed,modified,error}
	
	public enum State{transfered,terminated,canceled,lost,error}
	
	public enum Direction{in,out}
	
	/**
	 * Metodo que devuelve el identifidor autogenerado del historico de llamada.
	 * @return
	 */
	public Long getId();

	/**
	 * Metodo que pone el identifidor autogenerado del historico de llamada.
	 * @param id
	 */
	public void setId(Long id);
	
	/**
	 * Metodo que devuelve el identifidor de la llamada que corresponde
	 * con el historico de llamada.
	 * @return
	 */
	public int getIdLlamada();

	/**
	 * Metodo que pone el identifidor de la llamada que corresponde
	 * con el historico de llamada.
	 * @param idLlamada
	 */
	public void setIdLlamada(int idLlamada);
	
	/**
	 * Metodo que devuelve la cuenta de la llamada que corresponde 
	 * con el historico de llamada.
	 * @return
	 */
	public String getCount();

	/**
	 * Metodo que pone la cuenta de la llamada que corresponde 
	 * con el historico de llamada.
	 * @param count
	 */
	public void setCount(String count) ;

	/**
	 * Metodo que devuelve el numero de destino o origen de la llamada que corresponde
	 * con el historico de llamada.
	 * @return
	 */
	public String getNumber() ;

	/**
	 * Metodo que pone el numero de destino o origen de la llamada que corresponde
	 * con el historico de llamada.
	 * @param userRemote
	 */
	public void setNumber(String number);
	
	/**
	 * Metodo que devuelve el estado del historico de llamada.
	 * @return
	 */
	public State getState();
	
	/**
	 * Metodo que pone el estado del historico de llamada. 
	 * @param state
	 */
	public void setState(State state);
	
	/**
	 * Metodo que devuelve el sentido de la llamada que corresponde 
	 * con el historico de llamada.
	 * @return
	 */
	public Direction getDirection();
	
	/**
	 * Metodo que pone el sentido de la llamada que corresponde 
	 * con el historico de llamada.
	 * @param direction
	 */
	public void setDirection(Direction direction);

	/**
	 * Metodo que devuelve de la llamada que corresponde
	 * con el historico de llamada.
	 * @return
	 */
	public String getDescription();
	
	/**
	 * Metodo que pone la descripcion de la llamada que corresponde 
	 * con el historico de llamada.
	 * @param description
	 */
	public void setDescription(String description);
	
	/**
	 * Devuelve la accion del historico de llamada. 
	 * @return 
	 */
	public Action getAction();
	
	/**
	 * Pone la accion del historico de llamada.
	 * @param action
	 */
	public void setAction(Action action);
	
	/**
	 * Devuelve la fecha(dia y hora) del historico de llamada. 
	 * @return 
	 */
	public Date getDate();

	/**
	 * Pone la fecha(dia y hora) del historico de llamada.
	 * @param date
	 */
	public void setDate(Date date);

}
