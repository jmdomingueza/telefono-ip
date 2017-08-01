package com.jmdomingueza.telefonoip.sip.events;

import javax.sip.TimeoutEvent;

import com.jmdomingueza.telefonoip.sip.managers.SipManager;

public class MyTimeoutEvent {

	private TimeoutEvent evt;
	
	private SipManager sipManager;
	
	public MyTimeoutEvent(TimeoutEvent evt,SipManager sipManager) {
		this.evt = evt;
		this.sipManager = sipManager;
	}

	public TimeoutEvent getTimeoutEvent() {
		return evt;
	}

	public void setTimeoutEvent(TimeoutEvent evt) {
		this.evt = evt;
	}

	public SipManager getSipManager() {
		return sipManager;
	}

	public void setSipManager(SipManager sipManager) {
		this.sipManager = sipManager;
	} 
	
}
