package com.jmdomingueza.telefonoip.common.messages;


/**
 * Esta intefaz define el metodo que implementara todo listener de Message
 */
public interface MessageListener<T extends Message> {
	
	/**
	 * Metodo que recibe un Message e implementa el metodo que trata este
	 * Message
	 * 
	 * @param message 
	 */
	public abstract void receiveMessage(T message);

	
}
