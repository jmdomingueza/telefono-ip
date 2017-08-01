package com.jmdomingueza.telefonoip.negocio.beans;

import org.apache.log4j.Logger;

/**
 * Clase abstracta que implementa los atributos y operaciones del bean Count.
 *  
 * @author jmdomingueza
 *
 */
public  abstract class CountImpl implements Count{

	private static final long serialVersionUID = 1102618484647915488L;
	
	private final static Logger logger = Logger.getLogger(CountImpl.class);
	
	/**
	 * Usuario de la cuenta 
	 */
	protected String user;

	/**
	 * Constructor sin parametros
	 */
	public CountImpl() {
		logger.trace("Enter CountImpl - sin parametros");
		logger.trace("Exit CountImpl - sin parametros");
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
	public int hashCode() {
		
		logger.trace("Enter hashCode");
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		
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
		if (!(obj instanceof CountImpl)) {
			logger.trace("Exit equals - !(obj instanceof CountImpl)");
			return false;
		}
		CountImpl other = (CountImpl) obj;
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

	@Override
	public String toString() {
		logger.trace("Enter toString");
		logger.trace("Exit toString");
		return getUser();
	}
}
