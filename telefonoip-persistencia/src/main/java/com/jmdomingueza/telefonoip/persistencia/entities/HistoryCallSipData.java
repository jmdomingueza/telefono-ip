package com.jmdomingueza.telefonoip.persistencia.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Interfaz que define las operaciones de la entidad HistoryCallSipData que 
 * corresponde con la tabla HISTORICO_LLAMADA_SIP.
 * 
 * @author jmdomingueza
 *
 */
public interface HistoryCallSipData extends Serializable {

	public enum State{transfered,terminated,canceled,lost,error}
	
	public enum Direction{in,out}
	
	/**
	 * Metodo que devuelve el identifidor autogenerado del historico de llamada sip, 
	 * clave priamria.
	 * @return
	 */
	public Long getId();

	/**
	 * Metodo que pone el identifidor autogenerado del historico de llamada sip, 
	 * clave priamria.
	 * @param id
	 */
	public void setId(Long id);
	
	/**
	 * Metodo que devuelve el identifidor de la llamada sip que corresponde
	 * con el historico de llamada sip.
	 * @return
	 */
	public int getIdLlamada();

	/**
	 * Metodo que pone el identifidor de la llamada sip que corresponde
	 * con el historico de llamada sip.
	 * @param idLlamada
	 */
	public void setIdLlamada(int idLlamada);
	
	/**
	 * Metodo que devuelve la cuenta de la llamada sip que corresponde 
	 * con el historico de llamada sip.
	 * @return
	 */
	public String getCount();

	/**
	 * Metodo que pone la cuenta de la llamada sip que corresponde 
	 * con el historico de llamada sip.
	 * @param count
	 */
	public void setCount(String count) ;

	/**
	 * Metodo que devuelve el numero de destino o origen de la llamada sip que corresponde
	 * con el historico de llamada sip.
	 * @return
	 */
	public String getNumber() ;

	/**
	 * Metodo que pone el numero de destino o origen de la llamada sip que corresponde
	 * con el historico de llamada sip.
	 * @param userRemote
	 */
	public void setNumber(String number);
	
	/**
	 * Metodo que devuelve el estado del historico de llamada sip
	 * @return
	 */
	public State getState();
	
	/**
	 * Metodo que pone el estado del historico de llamada sip
	 * @param state
	 */
	public void setState(State state);
	
	/**
	 * Metodo que devuelve el sentido de la llamada sip que corresponde 
	 * con el historico de llamada sip.
	 * @return
	 */
	public Direction getDirection();
	
	/**
	 * Metodo que pone el sentido de la llamada sip que corresponde 
	 * con el historico de llamada sip.
	 * @param direction
	 */
	public void setDirection(Direction direction);

	/**
	 * Metodo que devuelve de la llamada sip que corresponde
	 * con el historico de llamada sip.
	 * @return
	 */
	public String getDescription();
	
	/**
	 * Metodo que pone la descripcion de la llamada sip que corresponde 
	 * con el historico de llamada sip.
	 * @param description
	 */
	public void setDescription(String description);
	
	/**
	 * Devuelve la fecha(dia y hora) del historico de llamada sip 
	 * @return 
	 */
	public Date getDate();

	/**
	 * Pone la fecha(dia y hora) del historico de llamada sip
	 * @param date
	 */
	public void setDate(Date date);
}
