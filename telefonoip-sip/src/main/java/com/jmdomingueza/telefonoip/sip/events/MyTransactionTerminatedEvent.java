package com.jmdomingueza.telefonoip.sip.events;

import javax.sip.TransactionTerminatedEvent;

import com.jmdomingueza.telefonoip.sip.managers.SipManager;

public class MyTransactionTerminatedEvent {

	private TransactionTerminatedEvent evt;
	
	private SipManager sipManager;
	
	public MyTransactionTerminatedEvent(TransactionTerminatedEvent evt,SipManager sipManager) {
		this.evt = evt;
		this.sipManager = sipManager;
	}

	public TransactionTerminatedEvent getTransactionTerminatedEvent() {
		return evt;
	}

	public void setTransactionTerminatedEvent(TransactionTerminatedEvent evt) {
		this.evt = evt;
	}

	public SipManager getSipManager() {
		return sipManager;
	}

	public void setSipManager(SipManager sipManager) {
		this.sipManager = sipManager;
	} 
	
}
