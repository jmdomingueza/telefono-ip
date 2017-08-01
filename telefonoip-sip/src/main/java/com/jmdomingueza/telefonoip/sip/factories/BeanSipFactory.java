package com.jmdomingueza.telefonoip.sip.factories;

import java.net.Inet4Address;
import java.util.List;

import javax.sip.ListeningPoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.sip.beans.Call;
import com.jmdomingueza.telefonoip.sip.beans.CallImpl;
import com.jmdomingueza.telefonoip.sip.beans.Count;
import com.jmdomingueza.telefonoip.sip.beans.CountImpl;

/**
 * Clase que implementa la Factoria de Stack.
 * @author jmdomingueza
 *
 */
public class BeanSipFactory {

	private static final Log logger = LogFactory.getLog(BeanSipFactory.class);
	
	
	public static Count createCount(String user,String hostServer, String password, Count.State state,
			String display, List<Integer> audioAvalibleList) {
		Count count;
		
		logger.trace("Enter createCount");
		
		count = new CountImpl();
		count.setUser(user);
		try{
			count.setLocalHost(Inet4Address.getLocalHost().getHostAddress());
		}catch (Exception e) {
			logger.warn("Se ha producido una excepcion a la hora de  obtener el host local, se pone la de porDefecto");
			count.setLocalHost("127.0.0.1");
			
		}
		count.setLocalPort(ListeningPoint.PORT_5060);
		count.setProtocol(ListeningPoint.UDP);
		count.setHostServer(hostServer);
		count.setPassword(password);
		count.setState(state);
		count.setDisplay(display);
		count.setAudioAvalibleList(audioAvalibleList);
		
		logger.trace("Exit createCount");
		return count;
	}
	
	
	public static Call createCall(Count count, String number, String tagFrom, String tagTo,
			String branchVia, String contentType, String contentSubType, Integer mode, Call.State state) {
		Call call;
		
		logger.trace("Enter createCall");
		
		call = new CallImpl();
		call.setCount(count);
		call.setNumber(number);
		call.setTagFrom(tagFrom);
		call.setTagTo(tagTo);
		call.setContentType(contentType);
		call.setContentSubType(contentSubType);
		call.setSessionDescriptionMode(mode);
		call.setState(state);
		
		logger.trace("Exit createCall");
		return call;
	}

	

	
	
	

}
