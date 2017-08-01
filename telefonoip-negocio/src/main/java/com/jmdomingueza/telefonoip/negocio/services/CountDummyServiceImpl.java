package com.jmdomingueza.telefonoip.negocio.services;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.common.messages.MessageListener;
import com.jmdomingueza.telefonoip.common.messages.MessageService;
import com.jmdomingueza.telefonoip.common.messages.dummy.DummyMessage;
import com.jmdomingueza.telefonoip.common.messages.ihm.AddMessage;
import com.jmdomingueza.telefonoip.common.messages.ihm.RemoveMessage;
import com.jmdomingueza.telefonoip.common.messages.ihm.UpdateMessage;
import com.jmdomingueza.telefonoip.dummy.beans.Count;
import com.jmdomingueza.telefonoip.dummy.services.DummyService;
import com.jmdomingueza.telefonoip.negocio.beans.CountDummy;
import com.jmdomingueza.telefonoip.negocio.factories.ParseDummyFactory;

/**
 * Clase que implementa el servicio que gestiona las operaciones que se 
 * hacen para gestinar cuentas.
 * 
 * @author jmdomingueza
 *
 */
@Service(value = "countDummyService")
public class CountDummyServiceImpl implements CountDummyService,MessageListener<DummyMessage>{

	private static Log logger = LogFactory.getLog(CountDummyServiceImpl.class);

	/**
	 * Servicio que gestiona las operacion con la capa de persistencia
	 */
	@Autowired
	private PersistenceService persistenceService;
	
	/**
	 * Servicio que gestiona las operacion con la capa de dummy
	 */
	@Autowired
	private DummyService dummyService;
	
	/**
	 * Construtor de la clase
	 */
	public CountDummyServiceImpl() {
		
		logger.trace("Enter CountDummyServiceImpl");
		
		MessageService.addMessageListener(MessageService.DUMMY_GROUP_NAME, this);
		
		logger.trace("Exit CountDummyServiceImpl");
	}
	
	@Override
	public void createCountDummy(CountDummy countDummy) {
		
		logger.trace("Enter createCountDummy");
		
		try{
			//creamos el CountDummy de la capa de persistencia
			persistenceService.addCountDummy(countDummy);
			
			// Notificar para arriba el add
			MessageService.sendMessage(new AddMessage(this, countDummy));
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit createCountDummy");
	}
	
	@Override
	public void modifyCountDummy(CountDummy countDummy) {
		
		logger.trace("Enter modifyCountDummy");
		try{
			
			//actualizamos el CountDummy de la capa de persistencia
			persistenceService.updateCountDummy(countDummy);
			
			// Notificar para arriba el update
			MessageService.sendMessage(new UpdateMessage(this, countDummy));
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit modifyCountDummy");
	}

	@Override
	public void removeCountDummy(CountDummy countDummy) {

		logger.trace("Enter removeCountDummy");
		try{
			//borramos el CountDummy de la capa de persistencia
			persistenceService.removeCountDummy(countDummy);
			
			// Notificar para arriba el remove
			MessageService.sendMessage(new RemoveMessage(this, countDummy));
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit removeCountDummy");
	}

	@Override
	public CountDummy getCountDummyPersistence(String user) {
		CountDummy countDummy;
		
		logger.trace("Enter getCountDummyPersistence");
		try{
			//obtenemos el CountDummy de la capa de persistencia
			countDummy = persistenceService.getCountDummy(user);
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			countDummy=null;
		}
		
		logger.trace("Exit getCountDummyPersistence");
		return countDummy;
	}

	@Override
	public Collection<CountDummy> getAllCountDummyPersistence() {
		Collection<CountDummy> countDummyCollection;
		
		logger.trace("Enter getAllCountDummyPersistence");
		try{
			//obtenemos todos los CountDummy de la capa de persistencia
			countDummyCollection = persistenceService.getAllCountDummy();
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			countDummyCollection=null;
		}
		
		logger.trace("Exit getAllCountDummyPersistence");
		return countDummyCollection;
	}

	@Override
	public void registerCountDummy(CountDummy countDummy) {
		com.jmdomingueza.telefonoip.dummy.beans.Count countCapaDummy;
		
		logger.trace("Enter registerCountDummy");
		try{
			//registramos el CountDummy de la capa de dummy
			countCapaDummy = ParseDummyFactory.parseCoDToCo(countDummy);
			dummyService.registerCount(countCapaDummy);
		
			// Notificar para arriba el update
			countDummy.setState(CountDummy.State.registering);
			MessageService.sendMessage(new UpdateMessage(this, countDummy));
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit registerCountDummy");
	}

	@Override
	public void unregisterCountDummy(CountDummy countDummy) {
		com.jmdomingueza.telefonoip.dummy.beans.Count countCapaDummy;
		
		logger.trace("Enter unregisterCountDummy");
		try{
			//registramos el CountDummy de la capa de dummy
			countCapaDummy = ParseDummyFactory.parseCoDToCo(countDummy);
			dummyService.unregisterCount(countCapaDummy);
			
			// Notificar para arriba el update
			countDummy.setState(CountDummy.State.unregistering);
			MessageService.sendMessage(new UpdateMessage(this, countDummy));
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit unregisterCountDummy");
	}

	@Override
	public void receiveMessage(DummyMessage message) {
		
		logger.trace("Enter receiveMessage");
		try{
		switch (message.getType()) {
			case DummyMessage.REGISTER_TYPE:
				Count count = (Count) message.getValue();
				CountDummy countDummy = ParseDummyFactory.parseCoToCoD(count);
				// Actulizamos la BBDD con los cambio recibos de la capa de
				// dummy
				countDummy.setEditable(getCountDummyPersistence(countDummy.getUser()).getEditable());
				persistenceService.updateCountDummy(countDummy);
				// Notificamos a las otras capas el cambio.
				MessageService.sendMessage(new UpdateMessage(this, countDummy));
				break;
			case DummyMessage.CALL_TYPE:
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

	
}
