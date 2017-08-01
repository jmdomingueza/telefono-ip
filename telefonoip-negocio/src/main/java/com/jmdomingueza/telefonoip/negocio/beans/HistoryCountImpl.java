package com.jmdomingueza.telefonoip.negocio.beans;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Clase abstracta que implementa los atributos y operaciones del bean HistoryCount.
 *  
 * @author jmdomingueza
 *
 */
public  abstract class HistoryCountImpl implements HistoryCount{

	private static final long serialVersionUID = 1102618484647915488L;
	
	private final static Logger logger = Logger.getLogger(HistoryCountImpl.class);
	
	
	/**
	 * Identifidor autogenerado del historico de cuenta, clave priamria.
	 */
	private Long id;
	
	/**
	 * Usuario del historico de cuenta 
	 */
	protected String user;
	
	/**
	 * Accion del historico de cuenta 
	 */
	protected Action action;
	
	/**
	 * Fecha(dia y hora) del historico de cuenta 
	 */
	protected Date date;

	/**
	 * Constructor sin parametros
	 */
	public HistoryCountImpl() {
		logger.trace("Enter HistoryCountImpl - sin parametros");
		logger.trace("Exit HistoryCountImpl - sin parametros");
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
	public String getUser() {
		logger.trace("Enter getUser");
		logger.trace("Exit getUser");
		return user;
	}

	@Override
	public void setUser(String user) {
		logger.trace("Enter setUser");
		this.user = user;
		logger.trace("Exit setUser");
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
		if (!(obj instanceof HistoryCountImpl)) {
			logger.trace("Exit equals - !(obj instanceof HistoryCountImpl)");
			return false;
		}
		HistoryCountImpl other = (HistoryCountImpl) obj;
		if (id == null) {
			if (other.id != null) {
				logger.trace("Exit equals - other.id != null");
				return false;
			}
		} else if (!id.equals(other.id)) {
			logger.trace("Exit equals - !id.equals(other.id");
			return false;
		}
		
		logger.trace("Exit equals");
		return true;
	}

	@Override
	public String toString() {
		logger.trace("Enter toString");
		logger.trace("Exit toString");
		return getId()+"-"+getUser();
	}
	
}
