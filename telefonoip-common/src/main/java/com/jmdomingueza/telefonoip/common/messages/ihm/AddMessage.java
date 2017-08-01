package com.jmdomingueza.telefonoip.common.messages.ihm;

/**
 * Esta clase implementa un Message del grupo ihm_group.
 */
public class AddMessage extends IhmMessage {

	 /**
     * Constructor de la clase. Crea un nuevo Objeto de AddMessage con 
     * el grupo ihm_group, un origen, tipo add y un valor.
     *
     * @param source Define  objeto origen que a enviado el AddMessage
     * @param value Define el valor devuelto por AddMessage
     */
	public AddMessage(Object source, Object value) {
		super(source, ADD_TYPE, value);
	}

	@Override
	public boolean isDispatchThread() {
		return true;
	}

	@Override
	public String toString() {
		return "AddMessage [dispatchThread=" + isDispatchThread() + ", value=" + getValue() + ", source="
				+ getSource() + ", type=" + getType() + ", group()=" + getGroup() + "]";
	}

}
