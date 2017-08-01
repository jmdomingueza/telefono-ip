package com.jmdomingueza.telefonoip.common.messages.dummy;

import com.jmdomingueza.telefonoip.common.messages.DefaultMessage;
import com.jmdomingueza.telefonoip.common.messages.MessageService;

/**
 * Esta clase implementa un Message del grupo dummy_group.
 */
public  class DummyMessage extends DefaultMessage {
	
	/**
	 * Tipo de mensaje para los registros
	 */
	public final static int REGISTER_TYPE = 1;
	/**
	 * Tipo de mensaje para las llamadas
	 */
	public final static int CALL_TYPE = 2;
	
    /**
     * Constructor de la clase. Crea un nuevo Objeto de DummyMessage con 
     * el grupo dummy_group, un origen, un tipo y un valor.
     *
     * @param source Define  objeto origen que a enviado el DummyMessage
     * @param type Define el tipo de DummyMessage
     * @param value Define el valor devuelto por DummyMessage
     */
    public DummyMessage(Object source,
    					   int type,
                           Object value) {
        super(MessageService.DUMMY_GROUP_NAME, source, type, value);
    }

	@Override
	public boolean isDispatchThread() {
		return false;
	}
	

}
