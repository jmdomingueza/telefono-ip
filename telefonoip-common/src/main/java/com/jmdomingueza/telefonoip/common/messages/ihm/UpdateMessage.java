package com.jmdomingueza.telefonoip.common.messages.ihm;

/**
 * Esta clase implementa un Message del grupo ihm_group.
 */
public class UpdateMessage extends IhmMessage {

	 /**
     * Constructor de la clase. Crea un nuevo Objeto de UpdateMessage con 
     * el grupo ihm_group, un origen, tipo update y un valor.
     *
     * @param source Define  objeto origen que a enviado el UpdateMessage
     * @param value Define el valor devuelto por UpdateMessage
     */
	public UpdateMessage(Object source, Object value) {
		super(source, UPDATE_TYPE, value);
	}

	
	@Override
	public boolean isDispatchThread() {
		return true;
	}

	@Override
	public String toString() {
		return "UpdateMessage [dispatchThread=" + isDispatchThread() + ", value=" + getValue() + ", source="
				+ getSource() + ", type=" + getType() + ", group()=" + getGroup() + "]";
	}
}
