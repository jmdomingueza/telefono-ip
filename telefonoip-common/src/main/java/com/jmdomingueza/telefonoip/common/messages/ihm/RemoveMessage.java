package com.jmdomingueza.telefonoip.common.messages.ihm;

/**
 * Esta clase implementa un Message del grupo ihm_group.
 */
public class RemoveMessage extends IhmMessage {

	 /**
     * Constructor de la clase. Crea un nuevo Objeto de RemoveMessage con 
     * el grupo ihm_group, un origen, tipo remove y un valor.
     *
     * @param source Define  objeto origen que a enviado el RemoveMessage
     * @param value Define el valor devuelto por AddMessage
     */
	public RemoveMessage(Object source, Object value) {
		super(source, REMOVE_TYPE, value);
	}

	
	@Override
	public boolean isDispatchThread() {
		return true;
	}
	
	@Override
	public String toString() {
		return "RemoveMessage [dispatchThread=" + isDispatchThread() + ", value=" + getValue() + ", source="
				+ getSource() + ", type=" + getType() + ", group()=" + getGroup() + "]";
	}

}
