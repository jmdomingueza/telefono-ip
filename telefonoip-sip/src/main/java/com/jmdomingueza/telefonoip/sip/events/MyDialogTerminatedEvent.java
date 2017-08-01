package com.jmdomingueza.telefonoip.sip.events;

import javax.sip.DialogTerminatedEvent;

import com.jmdomingueza.telefonoip.sip.managers.SipManager;

public class MyDialogTerminatedEvent {

	private DialogTerminatedEvent evt;
	
	private SipManager sipManager;
	
	public MyDialogTerminatedEvent(DialogTerminatedEvent evt,SipManager sipManager) {
		this.evt = evt;
		this.sipManager = sipManager;
	}

	public DialogTerminatedEvent getDialogTerminatedEvent() {
		return evt;
	}

	public void setDialogTerminatedEvent(DialogTerminatedEvent evt) {
		this.evt = evt;
	}

	public SipManager getSipManager() {
		return sipManager;
	}

	public void setSipManager(SipManager sipManager) {
		this.sipManager = sipManager;
	} 
	
}
