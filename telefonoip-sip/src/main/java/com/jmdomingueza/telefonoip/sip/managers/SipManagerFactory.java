package com.jmdomingueza.telefonoip.sip.managers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SipManagerFactory {
	
	/**
	 * Logger de la clase
	 */
	private static final Log logger = LogFactory.getLog(SipManagerFactory.class);
	
	
	public static SipManager createSipManager(String host)throws Exception{
		SipManager sipManager;
		logger.trace("Enter createSipManager - host");
		
		sipManager = new SipManagerImpl(host);
		
		logger.trace("Exit createSipManager - host");
		return sipManager;
	}
	
	public static SipManager createSipManager(String host,int port, String transport) throws Exception{
		SipManager sipManager;
		logger.trace("Enter createSipManager - host port transport");
		
		sipManager = new SipManagerImpl(host, port,transport);
		
		logger.trace("Exit createSipManager host port transport");
		return sipManager;
	}

}
