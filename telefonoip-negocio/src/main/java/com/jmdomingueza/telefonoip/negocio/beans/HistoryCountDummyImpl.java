package com.jmdomingueza.telefonoip.negocio.beans;

import org.apache.log4j.Logger;

/**
 * Clase que implementa los atributos y operaciones del bean HistoryCountDummy.
 * 
 * @author jmdomingueza
 *
 */
public class HistoryCountDummyImpl extends HistoryCountImpl implements HistoryCountDummy {

	private static final long serialVersionUID = 3403680987464516638L;

	private final static Logger logger = Logger.getLogger(HistoryCountDummyImpl.class);
	
	/**
	 * Atributo editable del historico de cuenta dummy.
	 */
	private String editable;
	
	/**
	 * Estado del historico de cuenta.
	 */
	private State state;
	
	/**
	 * Constructor sin parametros
	 */
	public HistoryCountDummyImpl() {
		logger.trace("Enter HistoryCountDummyImpl - sin parametros");
		logger.trace("Exit HistoryCountDummyImpl - sin parametros");
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
}
