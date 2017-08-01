package com.jmdomingueza.telefonoip.negocio.services;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallSip;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountSip;

@Service(value = "historySipService")
public class HistorySipServiceImpl implements HistorySipService {

	/**
	 * Logger de la clase
	 */
	private static Log logger = LogFactory.getLog(HistorySipServiceImpl.class);

	/**
	 * Servicio que gestiona las operacion con la capa de persistencia
	 */
	@Autowired
	private PersistenceService persistenceService;
	
	
	@Override
	public void createHistoryCall(HistoryCallSip historyCallSip) {
		
		logger.trace("Enter createHistoryCall");
		
		try{
			//creamos el HistoryCallSip de la capa de persistencia
			persistenceService.addHistoryCallSip(historyCallSip);
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit createHistoryCall");

	}

	@Override
	public void removeHistoryCall(HistoryCallSip historyCallSip) {
		
		logger.trace("Enter removeHistoryCall");
		
		try{
			//eliminamos el HistoryCallSip de la capa de persistencia
			persistenceService.removeHistoryCallSip(historyCallSip);
			
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
			//eliminamos todos los HistoryCallSip de la capa de persistencia
			persistenceService.removeAllHistoryCallSip();
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
	
		logger.trace("Exit removeAllHistoryCall");
	}
	
	@Override
	public Collection<HistoryCallSip> getAllHistoryCall() {
		Collection<HistoryCallSip> historyCallSipCollection=null;
		
		logger.trace("Enter getAllHistoryCall");
		
		try{
			//obtenemos todos los HistoryCallSip de la capa de persistencia
			historyCallSipCollection =	persistenceService.getAllHistoryCallSip();
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
	
		logger.trace("Exit getAllHistoryCall");
		return historyCallSipCollection;
	}

	@Override
	public void createHistoryCount(HistoryCountSip historyCountSip) {
		
		logger.trace("Enter createHistoryCount");
		
		try{
			//creamos el HistoryCountSip de la capa de persistencia
			persistenceService.addHistoryCountSip(historyCountSip);
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
		
		logger.trace("Exit createHistoryCount");

	}

	@Override
	public void removeHistoryCount(HistoryCountSip historyCountSip) {
		logger.trace("Enter removeHistoryCount");
		
		try{
			//eliminamos el HistoryCountSip de la capa de persistencia
			persistenceService.removeHistoryCountSip(historyCountSip);
			
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
			//eliminamos todos los HistoryCountSip de la capa de persistencia
			persistenceService.removeAllHistoryCountSip();
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
	
		logger.trace("Exit removeAllHistoryCount");
		
	}
	
	@Override
	public Collection<HistoryCountSip> getAllHistoryCount() {
		Collection<HistoryCountSip> historyCountSipCollection=null;
		
		logger.trace("Enter getAllHistoryCall");
		
		try{
			//obtenemos todos los HistoryCallSip de la capa de persistencia
			historyCountSipCollection =	persistenceService.getAllHistoryCountSip();
			
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
		}
	
		logger.trace("Exit getAllHistoryCall");
		return historyCountSipCollection;
	}

}
