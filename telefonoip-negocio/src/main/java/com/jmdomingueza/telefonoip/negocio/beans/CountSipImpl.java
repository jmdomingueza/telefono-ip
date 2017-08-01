package com.jmdomingueza.telefonoip.negocio.beans;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Clase que implementa los atributos y operaciones del bean CountSip.
 * 
 * @author jmdomingueza
 *
 */
public class CountSipImpl extends CountImpl implements CountSip {

	private static final long serialVersionUID = 1785443869802858660L;

	private final static Logger logger = Logger.getLogger(CountSipImpl.class);
	
	/**
	 * Host local por donde se mandan los mensajes SIP de la cuenta sip.
	 */
	private  String localHost;
	
	/**
	 * Puerto local  por donde se mandan los mensajes SIP de la cuenta sip.
	 */
	private  Integer localPort;
	
	/**
	 * Protocolo que se usa para mandan los mensajes SIP de la cuenta sip.
	 */
	private  String protocol;
	/**
	 * Host del servidor donde esta registrada la cuenta sip.
	 */
	private  String hostServer;
	
	/**
	 * Password de la cuenta sip.
	 */
	private String password;

	/**
	 * Estado de la cuenta sip.
	 */
	private State state;
	
	/**
	 * Valor del  display que tiene la cuenta sip.
	 */
	private String display;

	/**
	 * Lista de enteros que corresponde con los 
	 * audios permitidos por la cuenta sip.
	 */
	private List<Integer> audioAvalibleList;
	
	/**
	 * Constructor sin parametros
	 */
	public CountSipImpl() {
		logger.trace("Enter CountSipImpl - sin parametros");
		logger.trace("Exit CountSipImpl - sin parametros");
	}
	
	@Override
	public String getLocalHost() {
		logger.trace("Enter getLocalHost");
		logger.trace("Exit getLocalHost");
		return localHost;
	}

	@Override
	public void setLocalHost(String localHost) {
		logger.trace("Enter setLocalHost");
		this.localHost = localHost;
		logger.trace("Exit setLocalHost");
	}

	@Override
	public Integer getLocalPort() {
		logger.trace("Enter getLocalPort");
		logger.trace("Exit getLocalPort");
		return localPort;
	}

	@Override
	public void setLocalPort(Integer localPort) {
		logger.trace("Enter setLocalPort");
		this.localPort = localPort;
		logger.trace("Exit setLocalPort");
	}

	@Override
	public String getProtocol() {
		logger.trace("Enter getProtocol");
		logger.trace("Exit getProtocol");
		return protocol;
	}

	@Override
	public void setProtocol(String protocol) {
		logger.trace("Enter setProtocol");
		this.protocol = protocol;
		logger.trace("Exit setProtocol");
	}
	
	@Override
	public String getHostServer() {
		logger.trace("Enter getHostServer");
		logger.trace("Exit getHostServer");
		return hostServer;
	}

	@Override
	public void setHostServer(String hostServer) {
		logger.trace("Enter setHostServer");
		this.hostServer = hostServer;
		logger.trace("Exit setHostServer");
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
	public String getDisplay() {
		logger.trace("Enter getDisplay");
		logger.trace("Exit getDisplay");
		return display;
	}

	@Override
	public void setDisplay(String display) {
		logger.trace("Enter setDisplay");
		this.display = display;
		logger.trace("Exit setDisplay");
	}
	
	@Override
	public List<Integer> getAudioAvalibleList() {
		logger.trace("Enter getAudioAvalibleList");
		logger.trace("Exit getAudioAvalibleList");
		return audioAvalibleList;
	}

	@Override
	public void setAudioAvalibleList(List<Integer> audioAvalibleList) {
		logger.trace("Enter setAudioAvalibleList");
		this.audioAvalibleList = audioAvalibleList;
		logger.trace("Exit setAudioAvalibleList");
	}

	@Override
	public int hashCode() {
		
		logger.trace("Enter hashCode");
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((hostServer == null) ? 0 : hostServer.hashCode());
		
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
		if (!(obj instanceof CountSipImpl)) {
			logger.trace("Exit equals - !(obj instanceof CountSipImpl)");
			return false;
		}
		CountSipImpl other = (CountSipImpl) obj;
		if (user != other.user) {
			logger.trace("Exit equals - user != other.user");
			return false;
		}
		if (hostServer != other.hostServer) {
			logger.trace("Exit equals - hostServer != other.hostServer");
			return false;
		}
		
		logger.trace("Exit equals");
		return true;
	}
	
	@Override
	public String toString() {
		logger.trace("Enter toString");
		logger.trace("Exit toString");
		return getUser()+"@"+getHostServer();
	}

	
}
