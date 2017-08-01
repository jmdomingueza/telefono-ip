package com.jmdomingueza.telefonoip.presentacion.labels;

import javax.swing.Icon;
import javax.swing.JLabel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.negocio.beans.CountSip;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;

/**
 * Clase que implelementa una etiqueta pero escribe el texto lo pinta dependiendo
 * del estado de la cuenta dummy que tiene
 * @author jmdomingueza
 *
 */
public class SipStateLabel extends JLabel {

	private static final long serialVersionUID = 6428311139023832540L;
	
	private static final Log logger = LogFactory.getLog(DummyStateLabel.class);

	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");
	
	/**
	 * Constuctor de la clase
	 * @param state
	 */
	public SipStateLabel(CountSip.State state ){
		super();
		
		logger.trace("Enter SipStateLabel");
		
		setText(state);
		setIcon(state);
	    setHorizontalAlignment(JLabel.CENTER);
	    logger.trace("Exit SipStateLabel");
	}
	
	/**
	 * Metodo que pone el texto de la etiqueta dependiendo del estado de la
	 * cuenta dummy que pasamos
	 * @param state
	 */
	public void setText(CountSip.State state){
		String key;
		
		logger.trace("Enter setText");
		
		key = getKey(state);
		
		String text = resourceService.getProperty(key+".text");
		
		setText(text);
		logger.trace("Exit setText");
	}
	
	/**
	 * Metodo que pone el icono de la etiqueta dependiendo del estado de la
	 * cuenta dummy que pasamos
	 * @param state
	 */
	public void setIcon(CountSip.State state){
		String key;
		
		logger.trace("Enter setIcon");
		
		key = getKey(state);
		
		Icon icon = resourceService.getIcon(key+".icon");
		
		if(icon!=null){
			setIcon(icon);
		}
		logger.trace("Exit setIcon");
	}
	
	/**
	 * Metodo que devuelve la clave de los recurso que corresponde con
	 * estado de la cuenta dummy que pasamos
	 * @param state
	 * @return
	 */
	private String getKey(CountSip.State state){
		String key;
		
		logger.trace("Enter getKey");
		
		switch (state) {
		case registering:
			key = "bean.count.sip.state.registering";
			break;
		case unregistering:
			key = "bean.count.sip.state.unregistering";
			break;
		case registered:
			key = "bean.count.sip.state.registered";
			break;
		case unauthorized:
			key = "bean.count.sip.state.unauthorized";
			break;
		case error:
			key = "bean.count.sip.state.error";
			break;
		default:
			key = "bean.count.sip.state.unregistered";
			break;
		}
		
		logger.trace("Exit getKey");
		return key;
	}
}
