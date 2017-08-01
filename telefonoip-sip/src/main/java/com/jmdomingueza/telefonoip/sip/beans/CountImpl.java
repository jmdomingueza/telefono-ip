package com.jmdomingueza.telefonoip.sip.beans;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Class implements a user SIP.
 * @author jmdomingueza
 *
 */
public  class CountImpl implements Count {

	private final static Logger logger = Logger.getLogger(CountImpl.class);
	
	/**
	 * Name user registered on Server
	 */
	protected String user;
	
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
	 * Password registered on Server
	 */
	private String password;

	/**
	 * Text shows on display
	 */
	protected String display;

	/**
	 * Host of server where has registed the user
	 */
	protected String hostServer;
	
	/**
	 * List of audios avalibles for the user
	 */
	private List<Integer> audioAvalibleList;
	
	/**
	 * 
	 */
	private State state;


	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
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
	public String getDisplay() {
		return display;
	}

	@Override
	public void setDisplay(String display) {
		this.display = display;
	}

	@Override
	public String getHostServer() {
		return hostServer;
	}

	@Override
	public void setHostServer(String hostServer) {
		this.hostServer = hostServer;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public List<Integer> getAudioAvalibleList() {
		return audioAvalibleList;
	}

	@Override
	public void setAudioAvalibleList(List<Integer> audioAvalibleList) {
		this.audioAvalibleList = audioAvalibleList;
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public void setState(State state) {
		this.state = state;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountImpl other = (CountImpl) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		logger.trace("Enter toString");
		logger.trace("Exit toString");
		return getUser()+"@"+getHostServer();
	}
}
