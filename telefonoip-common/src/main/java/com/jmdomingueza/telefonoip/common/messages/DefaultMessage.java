package com.jmdomingueza.telefonoip.common.messages;

/**
 * Esta clase abstracta que define e implementa el Message por defecto. Estos metodos
 * seran heredados por todos los mensajes que hereden de Message y podran ser sobreescritos.
 * @author jmdomingueza
 *
 */
public abstract class DefaultMessage implements Message {

	/**
	 * Define el grupo  de Message
	 */
	private String group = null;
	
    /**
     *  Define el valor devuelto por el Message
     */
    private Object value = null;

    /**
     * Define el objeto origen que a enviado el Message
     */
    private Object source = null;

    /**
     * Define el tipo Message (por defecto 0)
     */
    private int type = 0;

    /**
     * Constuctor de la clase. Crea un nuevo Objeto de DefaultMessage con 
     * un grupo, un origen, un tipo y un valor .El tipo sera 0(por defeto). El valor de value sera null.
     *
     * @param group Define el grupo de Message
     * @param source Define  objeto origen que a enviado el Message
     * @param type Define el tipo de Message
     * @param value Define el valor devuelto por Message
     */
    public DefaultMessage(String group,
    					  Object source,
                          int    type,
                          Object value) {
    	this.group 	  = group;
        this.source   = source;
        this.type     = type;
        this.value    = value;
    }

    /**
     * Constuctor de la clase. Crea un nuevo Objeto de DefaultMessage con 
     * un grupo, un origen y un valor.El tipo sera 0(por defeto). El valor de value sera null.
     *
     * @param group Define el grupo de Message
     * @param source Define el objeto origen que a enviado el Message
     * @param value Define el valor devuelto por Message
     */
    public DefaultMessage(String group,
			  			  Object source,
                          Object value) {
        this(group, source, 0, value);
    }

    /**
     * Constuctor de la clase. Crea un nuevo Objeto de DefaultMessage con 
     * un grupo un origen.El tipo sera 0(por defeto). El valor de value sera null.
     *
     * @param group Define el grupo de Message
     * @param source Define el objeto origen donde se crea el Message
     */
    public DefaultMessage(String group,
			  			  Object source) {
        this(group, source, null);
    }

    /**
     * Constuctor de la clase. Crea un nuevo Objeto de DefaultMessage con 
     * un grupo un origen y un tipo. El valor de value sera null.
     *
     * @param group Define el grupo de Message
     * @param source Define el objeto origen donde se crea el Message
     * @param type Define el tipo de Message
     */
    public DefaultMessage(String group,
			  			  Object source,
                          int    type) {
        this(group, source, type, null);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Object getSource() {
        return source;
    }

    @Override
    public int getType() {
        return type;
    }
    
    @Override
    public String getGroup() {
    	return group;
    }

    @Override
	public boolean isDispatchThread() {
		return false;
	}
}
