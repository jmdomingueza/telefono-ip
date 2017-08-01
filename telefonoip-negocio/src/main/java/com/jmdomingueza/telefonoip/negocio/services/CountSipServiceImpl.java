package com.jmdomingueza.telefonoip.negocio.services;

import java.util.Collection;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.common.messages.MessageListener;
import com.jmdomingueza.telefonoip.common.messages.MessageService;
import com.jmdomingueza.telefonoip.common.messages.ihm.AddMessage;
import com.jmdomingueza.telefonoip.common.messages.ihm.RemoveMessage;
import com.jmdomingueza.telefonoip.common.messages.ihm.UpdateMessage;
import com.jmdomingueza.telefonoip.common.messages.sip.SipMessage;
import com.jmdomingueza.telefonoip.negocio.beans.CountSip;
import com.jmdomingueza.telefonoip.negocio.factories.ParseSipFactory;
import com.jmdomingueza.telefonoip.sip.beans.Count;
import com.jmdomingueza.telefonoip.sip.services.SipService;
import com.jmdomingueza.telefonoip.sip.services.SipServiceImpl;

@Service(value = "countSipService")
public class CountSipServiceImpl implements CountSipService,MessageListener<SipMessage> {

	/**
	 * Logger de la clase
	 */
	private static Log logger = LogFactory.getLog(CountSipServiceImpl.class);

	
	@Autowired
	private SipService sipService;
	
	/**
	 * Servicio que gestiona las operacion con la capa persistencia
	 */
	@Autowired
	private PersistenceService persistenceService;
	
	
	public CountSipServiceImpl() {
		
		logger.trace("Enter CountSipServiceImpl");
		
		logger.info("Creamos el objeto del servicio CountSipService");
		
		//Subscribimos el CountSipService a los mensajes SipMessage
		logger.debug("Subscribimos el "+this+" a los mensajes "+MessageService.SIP_GROUP_NAME);
		MessageService.addMessageListener(MessageService.SIP_GROUP_NAME, this);
		
		logger.trace("Exit CountSipServiceImpl");
	}
	
	
	public void init() {

	}
	
	public void destroy() {
		//Desubscribimos el CountSipService a los mensajes SipMessage
		logger.debug("Desubscribimos el "+this+" a los mensajes "+MessageService.SIP_GROUP_NAME);
		MessageService.removeMessageListener(MessageService.SIP_GROUP_NAME, this);
				
	}
	

	@Override
	public void createCountSip(CountSip countSip) {
		
		logger.trace("Enter createCountSip");
		
		try {
			logger.info("Creamos la cuenta sip: "+countSip);
			
			//Creamos el CountSip de la capa persistencia
			logger.debug("Creamos "+countSip+" de la capa persistencia");
			persistenceService.addCountSip(countSip);
			
			// Notificar para arriba el add
			AddMessage message = new AddMessage(this, countSip);
			logger.debug("Notificamos "+message+" a los subscriptores");
			MessageService.sendMessage(message);
			
			//Realizamos el registro en la capa sip
			register(countSip);
		} catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit createCountSip");

	}
	
	@Override
	public void modifyCountSip(CountSip countSip) {
		
		logger.trace("Enter modifyCountSip");
		
		try {
			logger.info("Modificamos la cuenta sip: "+countSip);
			
			//Modificamos el CountSip de la capa persistencia
			logger.debug("Modificamos "+countSip+" de la capa persistencia");
			persistenceService.updateCountSip(countSip);
			
			// Notificar para arriba el update
			UpdateMessage message = new UpdateMessage(this, countSip);
			logger.debug("Notificamos "+message+" a los subscriptores");
			MessageService.sendMessage(message);
		} catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit modifyCountSip");

	}

	@Override
	public void removeCountSip(CountSip countSip) {
		
		logger.trace("Enter removeCountSip");
		
		try {
			logger.info("Eliminamos la cuenta sip: "+countSip);
			
			//Si el CountSip esta registrado realizamos el desregistro del 
			//CountSip en la capa sip
			if(countSip.getState()==CountSip.State.registered){
				unregister(countSip);
			}
			
			//Borramos el CountSip de la capa persistencia
			logger.debug("Borramos "+countSip+" de la capa persistencia");
			persistenceService.removeCountSip(countSip);
			
			// Notificar para arriba el remove
			RemoveMessage message = new RemoveMessage(this, countSip);
			logger.debug("Notificamos "+message+" a los subscriptores");
			MessageService.sendMessage(message);
		} catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		logger.trace("Exit removeCountSip");

	}

	@Override
	public CountSip getCountSipPersistence(String user, String hostServer) {
		CountSip countSip;
		
		logger.trace("Enter getCountSipPersistence");
		try{
			logger.info("Obtenemos la cuenta sip: "+user+"@"+hostServer);
			
			//Obtenemos el CountSip de la capa persistencia
			logger.debug("Obtenemos "+user+"@"+hostServer+" de la capa persistencia");
			countSip = persistenceService.getCountSip(user,hostServer);
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			countSip=null;
		}
		
		logger.trace("Exit getCountSipPersistence");
		return countSip;
	}
	
	
	@Override
	public Collection<CountSip> getAllCountSipPersistence() {
		Collection<CountSip> countSipCollection;
		
		logger.trace("Enter getAllCountSipPersistence");
		try{
			logger.info("Obtenemos todas las cuenta sip");
			
			//Obtenemos todos los CountDummy de la capa persistencia
			logger.debug("Obtenemos todas la cuentas sip de la capa persistencia");
			countSipCollection = persistenceService.getAllCountSip();
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			countSipCollection=null;
		}
		
		logger.trace("Exit getAllCountSipPersistence");
		return countSipCollection;
	}

	@Override
	public void registerCountSip(CountSip countSip) {
		
		logger.trace("Enter registerCountSip");
		
		try {
			logger.info("Registramos la cuenta sip: "+countSip);
			
			register(countSip);
		} catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit registerCountSip");
	}


	@Override
	public void unregisterCountSip(CountSip countSip) {
		
		logger.trace("Enter unregisterCountSip");
		
		try {
			logger.info("Desregistramos la cuenta sip: "+countSip);
			
			unregister(countSip);
		} catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit unregisterCountSip");
	}
	
	@Override
	public void receiveMessage(SipMessage message) {
		
		logger.trace("Enter receiveMessage");
		try{
			switch (message.getType()) {
			case SipMessage.REGISTER_TYPE:
				if (message.getSource() instanceof SipServiceImpl) {
					logger.debug("Recibimos "+message+" que es tratado");
					
					//Parseamos el Count a CountSip de la capa negocio 
					Count count = (Count) message.getValue();
					logger.debug("Parseamos "+count+" a CountSip de la capa negocio");
					CountSip countSip = ParseSipFactory.parseCoToCoS(count);
					
					//Modificamos el CountSip de la capa persistencia
					logger.debug("ModiSipMessageficamos "+countSip+" de la capa persistencia");
					persistenceService.updateCountSip(countSip);
					
					// Notificar para arriba el update
					UpdateMessage updateMessage = new UpdateMessage(this, countSip);
					logger.debug("Notificamos "+updateMessage+" a los subscriptores");
					MessageService.sendMessage(updateMessage);
				}
				break;
			case SipMessage.CALL_TYPE:
				// No se hace nada con estos tipo en esta clase
				break;
			default:
				logger.warn("Se recibe un tipo que no es tratado: " + message.getType());
				break;
			}
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		logger.trace("Exit receiveMessage");

	}
	
	/**
	 * Metodo que modifica el estado de una CountSip a registering guardandolo
	 * en la capa persistencia y luego realiza el registro en la capa sip
	 * @param countSip
	 * @throws Exception
	 */
	private void register(CountSip countSip) throws Exception{
		Count countCapaSip; 
		
		logger.trace("Enter register");
		
		//Modificamos el CountSip de la capa persistencia
		countSip.setState(CountSip.State.registering);
		logger.debug("Modificamos "+countSip+" de la capa persistencia");
		persistenceService.updateCountSip(countSip);
		
		// Notificar para arriba el update
		UpdateMessage message = new UpdateMessage(this, countSip);
		logger.debug("Notificamos "+message+" a los subscriptores");
		MessageService.sendMessage(message);
		
		//Realizamos el registro del CountSip en la capa sip, para ellos 
		//primero parsemaos el CountSip a Count y luego realizamo el registro
		logger.debug("Parseamos "+countSip+" a Count de la capa sip");
		countCapaSip = ParseSipFactory.parseCoSToCo(countSip);
		logger.debug("Realizamos el registro de "+countSip+" de la capa sip");
		sipService.register(countCapaSip);
		
		logger.trace("Exit register");
	}
	
	/**
	 * Metodo que modifica el estado de una CountSip a registering guardandolo
	 * en la capa persistencia y luego realiza el desregistro en la capa sip
	 * @param countSip
	 * @throws Exception
	 */
	private void unregister(CountSip countSip) throws Exception{
		Count countCapaSip; 
		
		logger.trace("Enter unregister");
		
		//Modificamos el CountSip de la capa persistencia
		countSip.setState(CountSip.State.unregistering);
		logger.debug("Modificamos "+countSip+" de la capa persistencia");
		persistenceService.updateCountSip(countSip);
		
		// Notificar para arriba el update
		UpdateMessage message = new UpdateMessage(this, countSip);
		logger.debug("Notificamos "+message+" a los subscriptores");
		MessageService.sendMessage(message);
		
		//Realizamos el desregistro del CountSip en la capa sip, 
		//para ellos primero parsemaos el CountSip a Count y 
		//luego realizamo el desregistro
		logger.debug("Parseamos "+countSip+" a Count de la capa sip");
		countCapaSip = ParseSipFactory.parseCoSToCo(countSip);
		logger.debug("Realizamos el desregistro de "+countSip+" de la capa sip");
		sipService.unregister(countCapaSip);
			
		logger.trace("Exit unregister");
	}

}
