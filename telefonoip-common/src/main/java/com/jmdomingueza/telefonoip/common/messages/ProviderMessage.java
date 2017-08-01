package com.jmdomingueza.telefonoip.common.messages;

/**
 * Esta clase implementa un Message del grupo provider_group.
 */
public class ProviderMessage extends DefaultMessage {
	
	/**
	 * Tipo No inicializado
	 */
	public final static int UNINITIZED = 1;
	
	/**
	 * Tipo Vivo
	 */
	public final static int ALIVE = 2;
	
	/**
	 * Tipo Destruido
	 */
	public final static int DESTROY = 3;
	
	/**
	 * Tipo Error
	 */
	public final static int ERROR = 4;
	
	/**
     * Constructor de la clase. Crea un nuevo Objeto de ProviderMessage con 
     * el grupo provider_group, un origen y un tipo. El valor de value sera null.
     *
     * @param source Define  objeto origen que a enviado el ProviderMessage
     * @param type Define el tipo de ProviderMessage
     */
    public ProviderMessage(Object source, 
    					   int type) {
        super(MessageService.PROVIDER_GROUP_NAME, source,type);
    }

    /**
     * Constructor de la clase. Crea un nuevo Objeto de ProviderMessage con 
     * el grupo provider_group, un origen, un tipo y un valor.
     *
     * @param source Define  objeto origen que a enviado el ProviderMessage
     * @param type Define el tipo de ProviderMessage
     * @param value Define el valor devuelto por ProviderMessage
     */
    public ProviderMessage(Object source,
    					   int type,
                           Object value) {
        super(MessageService.PROVIDER_GROUP_NAME, source, value);
    }

	@Override
	public boolean isDispatchThread() {
		return false;
	}

}
