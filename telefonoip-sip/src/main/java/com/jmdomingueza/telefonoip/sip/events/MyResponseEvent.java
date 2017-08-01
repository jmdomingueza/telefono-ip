package com.jmdomingueza.telefonoip.sip.events;

import javax.sip.ResponseEvent;

import com.jmdomingueza.telefonoip.sip.managers.SipManager;

public class MyResponseEvent {

	private ResponseEvent evt;
	
	private SipManager sipManager;
	
	public MyResponseEvent(ResponseEvent evt,SipManager sipManager) {
		this.evt = evt;
		this.sipManager = sipManager;
	}

	public ResponseEvent getResponseEvent() {
		return evt;
	}

	public void setResponseEvent(ResponseEvent evt) {
		this.evt = evt;
	}

	public SipManager getSipManager() {
		return sipManager;
	}

	public void setSipManager(SipManager sipManager) {
		this.sipManager = sipManager;
	} 
	
}
