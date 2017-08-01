package com.jmdomingueza.telefonoip.negocio.beans;

import org.apache.log4j.Logger;

/**
 * Clase que implementa los atributos y operaciones del bean CountDummy.
 * 
 * @author jmdomingueza
 *
 */
public class CountDummyImpl extends CountImpl implements CountDummy {

	private static final long serialVersionUID = 3403680987464516638L;

	private final static Logger logger = Logger.getLogger(CountDummyImpl.class);
	
	/**
	 * Atributo editable.
	 */
	private String editable;
	
	/**
	 * Estado de la cuenta.
	 */
	private State state;
	
	/**
	 * Constructor sin parametros
	 */
	public CountDummyImpl() {
		logger.trace("Enter CountDummyImpl - sin parametros");
		logger.trace("Exit CountDummyImpl - sin parametros");
	}
	
	@Override
	public String getEditable() {
		logger.trace("Enter getEditable");
		logger.trace("Exit getEditable");
		return editable;
	}

	@Override
	public void setEditable(String editable) {
		logger.trace("Enter setEditable");
		this.editable = editable;
		logger.trace("Exit setEditable");
		
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
		if (!(obj instanceof CountDummyImpl)) {
			logger.trace("Exit equals - !(obj instanceof CountDummyImpl)");
			return false;
		}
		CountDummyImpl other = (CountDummyImpl) obj;
		if (user == null) {
			if (other.user != null) {
				logger.trace("Exit equals - other.user != null");
				return false;
			}
		} else if (!user.equals(other.user)) {
			logger.trace("Exit equals - !user.equals(other.user)");
			return false;
		}
		
		logger.trace("Exit equals");
		return true;
	}
}
