package com.jmdomingueza.telefonoip.dummy.beans;

import org.apache.log4j.Logger;

public class CallImpl  implements Call{

	private static final long serialVersionUID = -1172940175883536277L;

	private final static Logger logger = Logger.getLogger(CallImpl.class);
	
	/**
	 * Identificador de la llamada
	 */
	private int id;
	
	/**
	 * Cuenta desde la que se hace la llamada
	 */
	private Count count;
	
	/**
	 * Numero de destino o origen de la llamada,
	 * depende del sentido de la llamada
	 */
	private String number;
	
	/**
	 * Estado de la llamada
	 */
	private State state;
	
	/**
	 * Sentido de la llamada
	 */
	private Direction direction;
	
	/**
	 * Descripcion de la llamada
	 */
	private String description;
	
	/**
	 * Constructor sin parametros
	 */
	public CallImpl() {
		logger.trace("Enter CallImpl - sin parametros");
		logger.trace("Exit CallImpl - sin parametros");
	}
	
	@Override
	public int getId() {
		logger.trace("Enter getId");
		logger.trace("Exit getId");
		return id;
	}

	@Override
	public void setId(int id) {
		logger.trace("Enter setId");
		this.id = id;
		logger.trace("Exit setId");
	}
	
	@Override
	public Count getCount() {
		logger.trace("Enter getCount");
		logger.trace("Exit getCount");
		return count;
	}

	@Override
	public void setCount(Count count) {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		
		logger.trace("Enter hashCode");
		
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		
		logger.trace("Exit hashCode");
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		logger.trace("Enter equals");
		
		if (this == obj) {
			logger.trace("Exit equals");
			return true;
		}
		if (obj == null) {
			logger.trace("Exit equals");
			return false;
		}
		if (!(obj instanceof CallImpl)) {
			logger.trace("Exit equals");
			return false;
		}
		CallImpl other = (CallImpl) obj;
		if (id != other.id) {
			logger.trace("Exit equals");
			return false;
		}
		logger.trace("Exit equals");
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		logger.trace("Enter toString");
		logger.trace("Exit toString");
		return "CallImpl [id=" + id + ", count=" + count + ", number=" + number + ", state=" + state + ", direction="
				+ direction + ", description=" + description + "]";
	}

	
}
