package com.jmdomingueza.telefonoip.persistencia.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Interfaz que define las operaciones de la entidad HistoryCallDummyData que 
 * corresponde con la tabla HISTORICO_LLAMADA_DUMMY.
 * 
 * @author jmdomingueza
 *
 */
public interface HistoryCallDummyData extends Serializable {

	public enum State{transfered,terminated,canceled,lost,error}
	
	public enum Direction{in,out}
	
	/**
	 * Metodo que devuelve el identifidor autogenerado del historico de llamada dummy, 
	 * clave priamria.
	 * @return
	 */
	public Long getId();

	/**
	 * Metodo que pone el identifidor autogenerado del historico de llamada dummy, 
	 * clave priamria.
	 * @param id
	 */
	public void setId(Long id);
	
	/**
	 * Metodo que devuelve el identifidor de la llamada dummy que corresponde
	 * con el historico de llamada dummy.
	 * @return
	 */
	public int getIdLlamada();

	/**
	 * Metodo que pone el identifidor de la llamada dummy que corresponde
	 * con el historico de llamada dummy.
	 * @param idLlamada
	 */
	public void setIdLlamada(int idLlamada);
	
	/**
	 * Metodo que devuelve la cuenta de la llamada dummy que corresponde 
	 * con el historico de llamada dummy.
	 * @return
	 */
	public String getCount();

	/**
	 * Metodo que pone la cuenta de la llamada dummy que corresponde 
	 * con el historico de llamada dummy.
	 * @param count
	 */
	public void setCount(String count) ;

	/**
	 * Metodo que devuelve el numero de destino o origen de la llamada dummy que corresponde
	 * con el historico de llamada dummy.
	 * @return
	 */
	public String getNumber() ;

	/**
	 * Metodo que pone el numero de destino o origen de la llamada dummy que corresponde
	 * con el historico de llamada dummy.
	 * @param userRemote
	 */
	public void setNumber(String number);
	
	/**
	 * Metodo que devuelve el estado del historico de llamada dummy
	 * @return
	 */
	public State getState();
	
	/**
	 * Metodo que pone el estado del historico de llamada dummy
	 * @param state
	 */
	public void setState(State state);
	
	/**
	 * Metodo que devuelve el sentido de la llamada dummy que corresponde 
	 * con el historico de llamada dummy.
	 * @return
	 */
	public Direction getDirection();
	
	/**
	 * Metodo que pone el sentido de la llamada dummy que corresponde 
	 * con el historico de llamada dummy.
	 * @param direction
	 */
	public void setDirection(Direction direction);

	/**
	 * Metodo que devuelve de la llamada dummy que corresponde
	 * con el historico de llamada dummy.
	 * @return
	 */
	public String getDescription();
	
	/**
	 * Metodo que pone la descripcion de la llamada dummy que corresponde 
	 * con el historico de llamada dummy.
	 * @param description
	 */
	public void setDescription(String description);
	
	/**
	 * Devuelve la fecha(dia y hora) del historico de llamada dummy 
	 * @return 
	 */
	public Date getDate();

	/**
	 * Pone la fecha(dia y hora) del historico de llamada dummy
	 * @param date
	 */
	public void setDate(Date date);

}
