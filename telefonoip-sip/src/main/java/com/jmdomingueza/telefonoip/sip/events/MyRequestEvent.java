package com.jmdomingueza.telefonoip.sip.events;

import javax.sip.RequestEvent;

import com.jmdomingueza.telefonoip.sip.managers.SipManager;

public class MyRequestEvent {
	
	private RequestEvent evt;
	
	private SipManager sipManager;
	
	public MyRequestEvent(RequestEvent evt,SipManager sipManager) {
		this.evt = evt;
		this.sipManager = sipManager;
	}

	public RequestEvent getRequestEvent() {
		return evt;
	}

	public void setRequestEvent(RequestEvent evt) {
		this.evt = evt;
	}

	public SipManager getSipManager() {
		return sipManager;
	}

	public void setSipManager(SipManager sipManager) {
		this.sipManager = sipManager;
	} 
	
}
