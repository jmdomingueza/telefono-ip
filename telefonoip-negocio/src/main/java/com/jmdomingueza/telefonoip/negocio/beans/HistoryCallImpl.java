package com.jmdomingueza.telefonoip.negocio.beans;

import java.util.Date;

import org.apache.log4j.Logger;

/**
* Clase abstracta que implementa los atributos y operaciones del bean HistoryCall.
*  
* @author jmdomingueza
*
*/
public abstract class HistoryCallImpl  implements HistoryCall{

	private static final long serialVersionUID = -1172940175883536277L;

	private final static Logger logger = Logger.getLogger(HistoryCallImpl.class);
	
	/**
	 * Identifidor autogenerado del historico de llamada , clave priamria.
	 */
	private Long id;
	
	/**
	 * Identifidor de la llamada  que corresponde con el historico de llamada .
	 */
	private int idLlamada;
	
	/**
	 * Cuenta de la llamada  que corresponde con el historico de llamada .
	 */
	private String count;
	
	/**
	 * Numero de destino o origen de la llamada  que corresponde
	 * con el historico de llamada .
	 */
	private String number;
	
	/**
	 * Estado del historico de llamada 
	 */
	private State state;
	
	/**
	 * Sentido de la llamada  que corresponde con el historico de llamada .
	 */
	private Direction direction;
	
	/**
	 * Descripcion de la llamada  que corresponde con el historico de llamada .
	 */
	private String description;
	
	/**
	 * Accion del historico de llamada 
	 */
	protected Action action;
	
	/**
	 * Fecha(dia y hora) del historico de llamada  
	 */
	private  Date date;
	
	/**
	 * Constructor sin parametros
	 */
	public HistoryCallImpl() {
		logger.trace("Enter HistoryCallImpl - sin parametros");
		logger.trace("Exit HistoryCallImpl - sin parametros");
	}
	
	@Override
	public Long getId() {
		logger.trace("Enter getId");
		logger.trace("Exit getId");
		return id;
	}

	@Override
	public void setId(Long id) {
		logger.trace("Enter setId");
		this.id = id;
		logger.trace("Exit setId");
	}
	
	@Override
	public int getIdLlamada() {
		logger.trace("Enter getIdLlamada");
		logger.trace("Exit getIdLlamada");
		return idLlamada;
	}

	@Override
	public void setIdLlamada(int idLlamada) {
		logger.trace("Enter setIdLlamada");
		this.idLlamada = idLlamada;
		logger.trace("Exit setIdLlamada");
	}
	
	@Override
	public String getCount() {
		logger.trace("Enter getCount");
		logger.trace("Exit getCount");
		return count;
	}

	@Override
	public void setCount(String count) {
		logger.trace("Enter setCount");
		this.count = count;
		logger.trace("Exit setCount");
	}

	@Override
	public String getNumber() {
		logger.trace("Enter getNumber");
		logger.trace("Exit getNumber");
		return number;
	}

	@Override
	public void setNumber(String number) {
		logger.trace("Enter setNumber");
		this.number = number;
		logger.trace("Exit setNumber");
	}

	@Override
	public State getState() {
		logger.trace("Enter getState");
		logger.trace("Exit getState");
		return state;
	}

	@Override
	public void setState(State state) {
		logger.trace("Enter setState");
		this.state = state;
		logger.trace("Exit setState");
	}

	@Override
	public Direction getDirection() {
		logger.trace("Enter getDirection");
		logger.trace("Exit getDirection");
		return direction;
	}

	@Override
	public void setDirection(Direction direction) {
		logger.trace("Enter setDirection");
		this.direction = direction;
		logger.trace("Exit setDirection");
	}
	
	@Override
	public String getDescription(){
		logger.trace("Enter getDescription");
		logger.trace("Exit getDescription");
		return this.description;
	}
	
	@Override
	public void setDescription(String description){
		logger.trace("Enter setDescription");
		this.description = description;
		logger.trace("Exit setDescription");
	}
	
	@Override
	public Action getAction() {
		logger.trace("Enter getAction");
		logger.trace("Exit getAction");
		return action;
	}

	@Override
	public void setAction(Action action) {
		logger.trace("Enter getAction");
		this.action = action;
		logger.trace("Exit getAction");
		
	}
	
	@Override
	public Date getDate() {
		logger.trace("Enter getDate");
		logger.trace("Exit getDate");
		return date;
	}

	@Override
	public void setDate(Date date) {
		logger.trace("Enter setDate");
		this.date = date;
		logger.trace("Exit setDate");
	}

	@Override
	public int hashCode() {
		
		logger.trace("Enter hashCode");
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		
		logger.trace("Exit hashCode");
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		logger.trace("Enter equals");
		
		if (this == obj) {
			logger.trace("Exit equals - this == obj");
			return true;
		}
		if (obj == null) {
			logger.trace("Exit equals - obj == null");
			return false;
		}
		if (!(obj instanceof HistoryCallImpl)) {
			logger.trace("Exit equals - !(obj instanceof HistoryCallImpl) ");
			return false;
		}
		HistoryCallImpl other = (HistoryCallImpl) obj;
		if (id == null) {
			if (other.id != null) {
				logger.trace("Exit equals - other.id != null");
				return false;
			}
		} else if (!id.equals(other.id)) {
			logger.trace("Exit equals - !id.equals(other.id)");
			return false;
		}
		
		logger.trace("Exit equals");
		return true;
	}

	@Override
	public String toString() {
		logger.trace("Enter toString");
		logger.trace("Exit toString");
		return "HistoryCallImpl [id=" + id + ", idLlamada=" + idLlamada + ", count=" + count + ", number="
				+ number + ", state=" + state + ", direction=" + direction + ", description=" + description + ", date="
				+ date + "]";
	}

	
}
