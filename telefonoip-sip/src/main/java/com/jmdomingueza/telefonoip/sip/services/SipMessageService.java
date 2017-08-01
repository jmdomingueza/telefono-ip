package com.jmdomingueza.telefonoip.sip.services;

import com.jmdomingueza.telefonoip.sip.events.MyDialogTerminatedEvent;
import com.jmdomingueza.telefonoip.sip.events.MyRequestEvent;
import com.jmdomingueza.telefonoip.sip.events.MyResponseEvent;
import com.jmdomingueza.telefonoip.sip.events.MyTimeoutEvent;
import com.jmdomingueza.telefonoip.sip.events.MyTransactionTerminatedEvent;

public interface SipMessageService {

	/**
     * Añade un SipMessageListener al GroupManager para que  cuando se envie
     * un mensaje llegue a ese SipMessageListener
     *
     * @param listener SipMessageListener que añade al GroupManager
     */
    public void addSipMessageListener(SipMessageListener listener);
    
    /**
     * Añade un SipMessageListener al GroupManager para que  cuando se envie
     * un mensaje llegue a ese SipMessageListener
     *
     * @param listener SipMessageListener que se añade al GroupManager
     * @param topPriority Es prioritario el mensaje
     */
    public void addSipMessageListener(SipMessageListener listener,
            boolean topPriority) ;
    
    /**
     * Elimina el SipMessageListener del GroupManager para que ya 
     * no reciba mensajes
     *
     * @param listener SipMessageListener que se elimina
     */
	public void removeSipMessageListener(SipMessageListener listener);
	
	/**
	 * Metodo que envia un mensaje de RequestEvent
	 * @param evt
	 */
	public void sendRequest(MyRequestEvent evt);

	/**
	 * Metodo que envia un mensaje de ResponseEvent
	 * @param evt
	 */
	public void sendResponse(MyResponseEvent evt);

	/**
	 * Metodo que envia un mensaje de TimeoutEvent
	 * @param arg0
	 */
	public void sendTimeout(MyTimeoutEvent evt);

	/**
	 * Metodo que envia un mensaje de DialogTerminatedEvent
	 * @param arg0
	 */
	public void sendDialogTerminated(MyDialogTerminatedEvent evt);
	
	/**
	 * Metodo que envia un mensaje de TransactionTerminatedEvent
	 * @param arg0
	 */
	public void sendTransactionTerminated(MyTransactionTerminatedEvent evt);

}
