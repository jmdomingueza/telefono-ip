package com.jmdomingueza.telefonoip.common.messages.sip;

import com.jmdomingueza.telefonoip.common.messages.DefaultMessage;
import com.jmdomingueza.telefonoip.common.messages.MessageService;

/**
 * Esta clase implementa un Message del grupo sip_group.
 */
public  class SipMessage extends DefaultMessage {
	
	/**
	 * Tipo de mensaje para los registros
	 */
	public final static int REGISTER_TYPE = 1;
	/**
	 * Tipo de mensaje para las llamadas
	 */
	public final static int CALL_TYPE = 2;
	
    /**
     * Constructor de la clase. Crea un nuevo Objeto de SipMessage con 
     * el grupo sip_group, un origen, un tipo y un valor.
     *
     * @param source Define  objeto origen que a enviado el SipMessage
     * @param type Define el tipo de SipMessage
     * @param value Define el valor devuelto por SipMessage
     */
    public SipMessage(Object source,
    					   int type,
                           Object value) {
        super(MessageService.SIP_GROUP_NAME, source, type, value);
    }

	@Override
	public boolean isDispatchThread() {
		return false;
	}
	
	@Override
	public String toString() {
		return "SipMessage [dispatchThread=" + isDispatchThread() + ", value=" + getValue() + ", source="
				+ getSource() + ", type=" + getType() + ", group()=" + getGroup() + "]";
	}

}
