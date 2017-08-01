package com.jmdomingueza.telefonoip.persistencia.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.log4j.Logger;

/**
 * Clase  que implementa los atributos y operaciones de la pk de la entidad 
 * CountSipData que corresponde con la tabla CUENTA_SIP.
 * @author jmdomingueza
 *
 */
@Embeddable
public class CountSipDataPKImpl implements Serializable {

	private static final long serialVersionUID = 3403680987464516638L;

	private final static Logger logger = Logger.getLogger(CountSipDataPKImpl.class);
	
	/**
	 * Nombre del usuario.
	 */
	@Column(name = "USUARIO")
	private String user;
		
	/**
	 * Host del servidor donde esta registrada la cuenta
	 */
	@Column(name = "SERVIDOR")
	private String hostServer;
	
	/**
	 * Constructor sin parametros
	 */
	public CountSipDataPKImpl() {
		logger.trace("Enter CountSipDataPKImpl - sin parametros");
		logger.trace("Exit CountSipDataPKImpl - sin parametros");
	}
	
	/**
	 * Construtor con todos los parametros editables
	 * @param user Nombre del usuario.
	 * @param hostServer  Host del servidor donde esta registrada la cuenta
	 */
	public CountSipDataPKImpl(String user, String hostServer) {
		
		logger.trace("Enter CountSipDataPKImpl - todos los parametros editables");
		
		this.user = user;
		this.hostServer = hostServer;
		
		logger.trace("Exit CountSipDataPKImpl - todos los parametros editables");
	}

	
	/**
	 * Devuelve el usuario de la cuenta, clave primaria
	 * @return 
	 */
	public String getUser() {
		logger.trace("Enter getUser");
		logger.trace("Exit getUser");
		return user;
	}

	/**
	 * Pone el usuario de la cuenta, clave primaria
	 * @param user
	 */
	public void setUser(String user) {
		logger.trace("Enter setUser");
		logger.trace("Exit setUser");
		this.user = user;
	}

	
	/**
	 * Devuelve el host del servidor donde esta registrada la cuenta,
	 * clave primaria
	 * @return
	 */
	public String getHostServer() {
		logger.trace("Enter getHostServer");
		logger.trace("Exit getHostServer");
		return hostServer;
	}

	/**
	 * Pone el host del servidor donde esta registrada la cuenta, 
	 * clave primaria
	 * @param hostServer
	 */
	public void setHostServer(String hostServer) {
		logger.trace("Enter setHostServer");
		this.hostServer = hostServer;
		logger.trace("Exit setHostServer");
	}

	
	@Override
	public int hashCode() {
		
		logger.trace("Enter hashCode");
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hostServer == null) ? 0 : hostServer.hashCode());
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
		if (!(obj instanceof CountSipDataPKImpl)) {
			logger.trace("Exit equals - !(obj instanceof CountSipDataPKImpl)");
			return false;
		}
		CountSipDataPKImpl other = (CountSipDataPKImpl) obj;
		if (hostServer == null) {
			if (other.hostServer != null) {
				logger.trace("Exit equals - other.hostServer != null");
				return false;
			}
		} else if (!hostServer.equals(other.hostServer)) {
			logger.trace("Exit equals - !hostServer.equals(other.hostServer)");
			return false;
		}
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
		return "CountSipDataPKImpl [user=" + user + ", hostServer=" + hostServer
				+ "]";
	}

	
	

}
