package com.jmdomingueza.telefonoip.negocio.beans;

import org.apache.log4j.Logger;

/**
 * Clase que implementa los atributos y operaciones del bean HistoryCountSip.
 * 
 * @author jmdomingueza
 *
 */
public class HistoryCountSipImpl extends HistoryCountImpl implements HistoryCountSip {

	private static final long serialVersionUID = 1785443869802858660L;

	private final static Logger logger = Logger.getLogger(HistoryCountSipImpl.class);
	
	/**
	 * Host del servidor donde esta registrado el historico de  cuenta
	 */
	private  String hostServer;
	
	/**
	 * Password del historico de cuenta sip
	 */
	private String password;

	/**
	 * Estado del historico de cuenta sip.
	 */
	private State state;
	
	/**
	 * Constructor sin parametros
	 */
	public HistoryCountSipImpl() {
		logger.trace("Enter HistoryCountSipImpl - sin parametros");
		logger.trace("Exit HistoryCountSipImpl - sin parametros");
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
	public String toString() {
		logger.trace("Enter toString");
		logger.trace("Exit toString");
		return getId()+"-"+getUser()+"@"+getHostServer();
	}
}
