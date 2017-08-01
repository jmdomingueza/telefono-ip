package com.jmdomingueza.telefonoip.sip.services;

import javax.sip.DialogTerminatedEvent;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.TimeoutEvent;
import javax.sip.TransactionTerminatedEvent;

import com.jmdomingueza.telefonoip.sip.managers.SipManager;

public interface SipMessageListener {

	/**
	 * Metodo que trata un RequestEvent y notifica la informacion que sea necesaria
	 * @param evt
	 */
	public void processRequest(RequestEvent evt,SipManager sipManager);

	/**
	 * Metodo que trata un ResponseEvent y notifica la informacion que sea necesaria
	 * @param evt
	 */
	public void processResponse(ResponseEvent evt,SipManager sipManager);

	/**
	 * Metodo que trata un TimeoutEvent y notifica la informacion que sea necesaria
	 * @param evt
	 */
	public void processTimeout(TimeoutEvent evt,SipManager sipManager);

	/**
	 * Metodo que trata un DialogTerminatedEvent y notifica la informacion que sea necesaria
	 * @param dialogTerminatedEvent
	 * @param sipManager
	 */
	public void processDialogTerminated(DialogTerminatedEvent evt, SipManager sipManager);

	/**
	 * Metodo que trata un TransactionTerminatedEvent y notifica la informacion que sea necesaria
	 * @param evt
	 * @param sipManager
	 */
	public void processTransactionTerminatedEvent(TransactionTerminatedEvent evt,
			SipManager sipManager);
}
