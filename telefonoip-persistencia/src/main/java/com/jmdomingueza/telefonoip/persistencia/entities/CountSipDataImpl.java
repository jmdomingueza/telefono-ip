package com.jmdomingueza.telefonoip.persistencia.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.log4j.Logger;

/**
 * Clase  que implementa los atributos y operaciones de la entidad 
 * CountSipData que corresponde con la tabla CUENTA_Sip.
 * @author jmdomingueza
 *
 */
@Entity
@Table(name="CUENTA_SIP")
public class CountSipDataImpl implements CountSipData {

	

	private static final long serialVersionUID = 3403680987464516638L;

	private final static Logger logger = Logger.getLogger(CountSipDataImpl.class);
	
	/**
	 * PrimaryKey con el user y hostServer.
	 */
	@EmbeddedId
	private CountSipDataPKImpl pk;
	
	/**
	 * Password de la cuenta
	 */
	@Column(name = "PASSWORD")
	private String password;
	
	/**
	 * Estado de la cuenta.
	 */
	@Column(name = "ESTADO")
	private State state;
	
	/**
	 * Constructor sin parametros
	 */
	public CountSipDataImpl() {
		logger.trace("Enter CountSipDataImpl - sin parametros");
		logger.trace("Exit CountSipDataImpl - sin parametros");
	}
	
	/**
	 * Construtor con todos los parametros editables
	 * @param user Nombre del usuario.
	 * @param hostServer  Host del servidor donde esta registrada la cuenta
	 * @param password Password de la cuenta
	 * @param state  Estado de la cuenta.
	 */
	public CountSipDataImpl(String user, String hostServer,String password, State state) {
		
		logger.trace("Enter CountSipDataImpl - todos los parametros editables");
		
		this.pk = new CountSipDataPKImpl(user,hostServer);
		this.password = password;
		this.state = state;
		
		logger.trace("Exit CountSipDataImpl - todos los parametros editables");
	}

	@Override
	public String getUser() {
		String user;
		
		logger.trace("Enter getUser");
		
		if(pk==null){
			logger.trace("Exit getUser - pk==null");
			return null;
		}
		user= pk.getUser();
		
		logger.trace("Exit getUser");
		return user;
	}

	@Override
	public void setUser(String user) {
		
		logger.trace("Enter setUser");
		
		if(pk!=null){
			pk.setUser(user);
		}
		
		logger.trace("Exit setUser");
	}
	
	@Override
	public String getPassword() {
		logger.trace("Enter getPassword");
		logger.trace("Exit getPassword");
		return password;
	}

	@Override
	public void setPassword(String password) {
		logger.trace("Enter setPassword");
		this.password = password;
		logger.trace("Exit setPassword");
	}
	
	@Override
	public String getHostServer() {
		String hostServer;
		
		logger.trace("Enter getHostServer");
		
		if(pk==null){
			logger.trace("Exit getHostServer - pk==null");
			return null;
		}
		hostServer= pk.getHostServer();
		
		logger.trace("Exit getHostServer");
		return hostServer;
	}

	@Override
	public void setHostServer(String hostServer) {
		
		logger.trace("Enter setHostServer");
		
		if(pk!=null){
			pk.setHostServer(hostServer);
		}
		
		logger.trace("Exit setHostServer");
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
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		
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
		if (!(obj instanceof CountSipDataImpl)) {
			logger.trace("Exit equals - !(obj instanceof CountSipDataImpl)");
			return false;
		}
		CountSipDataImpl other = (CountSipDataImpl) obj;
		if (pk == null) {
			if (other.pk != null) {
				logger.trace("Exit equals - other.pk != null");
				return false;
			}
		} else if (!pk.equals(other.pk)) {
			logger.trace("Exit equals - !pk.equals(other.pk)");
			return false;
		}
		
		logger.trace("Exit equals");
		return true;
	}

	@Override
	public String toString() {
		logger.trace("Enter toString");
		logger.trace("Exit toString");
		return "CountSipDataImpl [user=" + pk.getUser() + ", server="+pk.getHostServer() + ", password=" + password + ", state=" + state + "]";
	}
}
