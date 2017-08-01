package com.jmdomingueza.telefonoip.common.messages.ihm;

/**
 * Esta clase implementa un Message del grupo ihm_group.
 */
public class SelectionMessage extends IhmMessage {

	 /**
     * Constructor de la clase. Crea un nuevo Objeto de SelectionMessage con 
     * el grupo ihm_group, un origen, tipo selection y un valor.
     *
     * @param source Define  objeto origen que a enviado el SelectionMessage
     * @param value Define el valor devuelto por SelectionMessage
     */
	public SelectionMessage(Object source, Object value) {
		super(source, SELECTION_TYPE, value);
	}

	
	@Override
	public boolean isDispatchThread() {
		return true;
	}

}
