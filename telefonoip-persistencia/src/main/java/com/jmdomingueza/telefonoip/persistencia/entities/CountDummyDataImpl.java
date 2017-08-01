package com.jmdomingueza.telefonoip.persistencia.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;

/**
 * Clase  que implementa los atributos y operaciones de la entidad 
 * CountDummyData que corresponde con la tabla CUENTA_DUMMY.
 * 
 * @author jmdomingueza
 *
 */
@Entity
@Table(name="CUENTA_DUMMY")
public class CountDummyDataImpl implements CountDummyData {

	private static final long serialVersionUID = 3403680987464516638L;

	private final static Logger logger = Logger.getLogger(CountDummyDataImpl.class);
	
	/**
	 * Nombre del usuario.
	 */
	@Id
	@Column(name = "USUARIO")
	private String user;
	
	/**
	 * Atributo editable.
	 */
	@Column(name = "EDITABLE")
	private String editable;
	
	/**
	 * Estado de la cuenta.
	 */
	@Column(name = "ESTADO")
	private State state;
	
	/**
	 * Constructor sin parametros
	 */
	public CountDummyDataImpl() {
		logger.trace("Enter CountDummyDataImpl - sin parametros");
		logger.trace("Exit CountDummyDataImpl - sin parametros");
	}
	
	/**
	 * Construtor con todos los parametros editables
	 * @param user Nombre del usuario.
	 * @param state  Estado de la cuenta.
	 */
	public CountDummyDataImpl(String user,String editable, State state) {
		logger.trace("Enter CountDummyDataImpl - todos los parametros editables");
		
		this.user = user;
		this.editable = editable;
		this.state = state;
		
		logger.trace("Exit CountDummyDataImpl - todos los parametros editables");
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
		if (!(obj instanceof CountDummyDataImpl)) {
			logger.trace("Exit equals - !(obj instanceof CountDummyDataImpl)");
			return false;
		}
		CountDummyDataImpl other = (CountDummyDataImpl) obj;
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
		return "CountDummyDataImpl [user=" + user + ", state=" + state + "]";
	}

	
	

}
