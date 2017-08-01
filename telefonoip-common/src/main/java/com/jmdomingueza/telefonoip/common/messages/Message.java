package com.jmdomingueza.telefonoip.common.messages;

/**
 * Esta interfaz define Message que es el Objeto que se envia en por el MessageService
 * a todos los MessageListener.
 */
public interface Message {
	
	/**
	 * Devuelve el grupo al que pertenece Message
	 * 
	 * @return 
	 */
	public String getGroup();

    /**
     * Devuelve el valor devuelto por Message
     *
     * @return 
     */
    public Object getValue();

    /**
     *Devuelve el objeto origen que a enviado el Message
     *
     * @return 
     */
    public Object getSource();

    /**
     * Devuelve el tipo de Message. Por defecto 0
     *
     * @return 
     */
    public int getType();
    
    /**
     * Devuelve si el Message debe ser tratado en un DispatchThread
     * @return
     */
    public boolean isDispatchThread();
}
