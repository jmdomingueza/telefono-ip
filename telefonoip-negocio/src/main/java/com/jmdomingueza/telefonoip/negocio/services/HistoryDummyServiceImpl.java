package com.jmdomingueza.telefonoip.negocio.services;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountDummy;

@Service(value = "historyDummyService")
public class HistoryDummyServiceImpl implements HistoryDummyService {

	/**
	 * Logger de la clase
	 */
	private static Log logger = LogFactory.getLog(HistoryDummyServiceImpl.class);

	/**
	 * Servicio que gestiona las operacion con la capa de persistencia
	 */
	@Autowired
	private PersistenceService persistenceService;
	
	
	@Override
	public void createHistoryCall(HistoryCallDummy historyCallDummy) {
		
		logger.trace("Enter createHistoryCall");
		
		try{
			//creamos el HistoryCallDummy de la capa de persistencia
			persistenceService.addHistoryCallDummy(historyCallDummy);
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit createHistoryCall");

	}

	@Override
	public void removeHistoryCall(HistoryCallDummy historyCallDummy) {
		
		logger.trace("Enter removeHistoryCall");
		
		try{
			//eliminamos el HistoryCallDummy de la capa de persistencia
			persistenceService.removeHistoryCallDummy(historyCallDummy);
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit removeHistoryCall");

	}
	
	@Override
	public void removeAllHistoryCall() {
		logger.trace("Enter removeAllHistoryCall");
		
		try{
			//eliminamos todos los HistoryCallDummy de la capa de persistencia
			persistenceService.removeAllHistoryCallDummy();
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
	
		logger.trace("Exit removeAllHistoryCall");
	}

	@Override
	public Collection<HistoryCallDummy> getAllHistoryCall() {
		Collection<HistoryCallDummy> historyCallDummyCollection=null;
		
		logger.trace("Enter getAllHistoryCall");
		
		try{
			//obtenemos todos los HistoryCallDummy de la capa de persistencia
			historyCallDummyCollection =	persistenceService.getAllHistoryCallDummy();
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
	
		logger.trace("Exit getAllHistoryCall");
		return historyCallDummyCollection;
	}

	@Override
	public void createHistoryCount(HistoryCountDummy historyCountDummy) {
		
		logger.trace("Enter createHistoryCount");
		
		try{
			//creamos el HistoryCountDummy de la capa de persistencia
			persistenceService.addHistoryCountDummy(historyCountDummy);
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit createHistoryCount");

	}

	@Override
	public void removeHistoryCount(HistoryCountDummy historyCountDummy) {
		logger.trace("Enter removeHistoryCount");
		
		try{
			//eliminamos el HistoryCountDummy de la capa de persistencia
			persistenceService.removeHistoryCountDummy(historyCountDummy);
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit removeHistoryCount");
	}

	@Override
	public void removeAllHistoryCount() {
		logger.trace("Enter removeAllHistoryCount");
		
		try{
			//eliminamos todos los HistoryCountDummy de la capa de persistencia
			persistenceService.removeAllHistoryCountDummy();
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
	
		logger.trace("Exit removeAllHistoryCount");
		
	}
	
	@Override
	public Collection<HistoryCountDummy> getAllHistoryCount() {
		Collection<HistoryCountDummy> historyCountDummyCollection=null;
		
		logger.trace("Enter getAllHistoryCall");
		
		try{
			//obtenemos todos los HistoryCallDummy de la capa de persistencia
			historyCountDummyCollection =	persistenceService.getAllHistoryCountDummy();
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
	
		logger.trace("Exit getAllHistoryCall");
		return historyCountDummyCollection;
	}
}
