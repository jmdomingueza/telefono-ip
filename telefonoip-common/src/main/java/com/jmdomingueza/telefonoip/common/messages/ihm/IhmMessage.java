package com.jmdomingueza.telefonoip.common.messages.ihm;

import com.jmdomingueza.telefonoip.common.messages.DefaultMessage;
import com.jmdomingueza.telefonoip.common.messages.MessageService;

/**
 * Esta clase implementa un Message del grupo ihm_group.
 */
public abstract class IhmMessage extends DefaultMessage {

	public static final int ADD_TYPE = 1;
	
	public static final int UPDATE_TYPE = 2;
	
	public static final int REMOVE_TYPE = 3;
	
	public static final int SELECTION_TYPE = 4;
	
	 /**
     * Constructor de la clase. Crea un nuevo Objeto de IhmMessage con 
     * el grupo ihm_group, un origen, un tipo y un valor.
     *
     * @param source Define  objeto origen que a enviado el IhmMessage
     * @param type Define el tipo de IhmMessage
     * @param value Define el valor devuelto por IhmMessage
     */
	public IhmMessage(Object source, int type, Object value) {
		super(MessageService.IHM_GROUP_NAME, source, type, value);
	}

	
	@Override
	public boolean isDispatchThread() {
		return true;
	}

}
