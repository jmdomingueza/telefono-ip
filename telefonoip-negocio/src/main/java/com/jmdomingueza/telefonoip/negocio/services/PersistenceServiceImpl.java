package com.jmdomingueza.telefonoip.negocio.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.negocio.beans.CountDummy;
import com.jmdomingueza.telefonoip.negocio.beans.CountSip;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallSip;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountSip;
import com.jmdomingueza.telefonoip.negocio.factories.ParseDummyFactory;
import com.jmdomingueza.telefonoip.negocio.factories.ParseSipFactory;
import com.jmdomingueza.telefonoip.persistencia.daos.CountDummyDao;
import com.jmdomingueza.telefonoip.persistencia.daos.CountSipDao;
import com.jmdomingueza.telefonoip.persistencia.daos.HistoryCallDummyDao;
import com.jmdomingueza.telefonoip.persistencia.daos.HistoryCallSipDao;
import com.jmdomingueza.telefonoip.persistencia.daos.HistoryCountDummyDao;
import com.jmdomingueza.telefonoip.persistencia.daos.HistoryCountSipDao;
import com.jmdomingueza.telefonoip.persistencia.entities.CountDummyData;
import com.jmdomingueza.telefonoip.persistencia.entities.CountSipData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallDummyData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallSipData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountDummyData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountSipData;
import com.jmdomingueza.telefonoip.persistencia.factories.DaoFactory;

/**
 * Clase que implementa el servicio que gestiona las operaciones que 
 * se pueden hacer con la capa de persistencia.
 * 
 * @author jmdomingueza
 *
 */
@Service(value = "persistenceService")
public class PersistenceServiceImpl implements PersistenceService{

	private final static Logger logger = Logger.getLogger(PersistenceServiceImpl.class);

	/**
	 * Entidad que gestiona la factoria de Base de Datos
	 */
	private EntityManagerFactory emf = null;

	/**
	 * Entidad que gestiona la Base de Datos
	 */
	private EntityManager em = null;

	/**
	 * Entidad con el transacion
	 */
	private EntityTransaction tx = null;
	
	/**
	 * Dao del CountDummy
	 */
	private CountDummyDao countDummyDao;
	
	/**
	 * Dao del CountSip
	 */
	private CountSipDao countSipDao;
	
	/**
	 * Dao del HistoryCallDummy
	 */
	private HistoryCallDummyDao historyCallDummyDao;
	
	/**
	 * Dao del HistoryCallSip
	 */
	private HistoryCallSipDao historyCallSipDao;
	
	/**
	 * Dao del HistoryCountDummy
	 */
	private HistoryCountDummyDao historyCountDummyDao;
	
	/**
	 * Dao del HistoryCountSip
	 */
	private HistoryCountSipDao historyCountSipDao;
	
	/**
	 * Constructor de la clase
	 */
	public PersistenceServiceImpl(){
		
		logger.trace("Enter PersistenceServiceImpl");
		
		logger.info("Creamos el objeto del servicio PersistenceService");
		
		// Creamos la factoria del EntityManager
		logger.debug("Creamos la factoria del EntityManager: telefonoip-persistencia");
		emf = Persistence.createEntityManagerFactory("telefonoip-persistencia");
		// Creamos el EntityManager
		logger.debug("Creamos el EntityManager");
		em = emf.createEntityManager();
		// Obtenemos una Transaccion
		logger.debug("Obtenemos una Transaccion");
		tx = em.getTransaction();
				
		// Creamos los DAO de persistencia
		logger.debug("Creamos los DAO de persistencia");
		countDummyDao = DaoFactory.createCountDummyDao(emf, em, tx);
		countSipDao = DaoFactory.createCountSipDao(emf, em, tx);
		historyCallDummyDao = DaoFactory.createHistoryCallDummyDao(emf, em, tx);
		historyCallSipDao = DaoFactory.createHistoryCallSipDao(emf, em, tx);
		historyCountDummyDao = DaoFactory.createHistoryCountDummyDao(emf, em, tx);
		historyCountSipDao = DaoFactory.createHistoryCountSipDao(emf, em, tx);
		
		logger.trace("Exit PersistenceServiceImpl");
	}
	
	@Override
	public synchronized void addCountDummy(CountDummy countDummy) {
		
		logger.trace("Enter addCountDummy");
		
		//Creamos un CountDummy en la capa persistencia
		logger.debug("Creamos "+countDummy+" en la capa persistencia");
		countDummyDao.save(countDummy.getUser(),countDummy.getEditable(),ParseDummyFactory.parseStateCoDToCoDD(countDummy.getState()));
		
		logger.trace("Exit addCountDummy");
	}
	
	@Override
	public synchronized void updateCountDummy(CountDummy countDummy) {
		logger.trace("Enter updateCountDummy");
		
		//Actulizamos un CountDummy en la capa persistencia
		logger.debug("Actulizamos "+countDummy+" en la capa persistencia");
		countDummyDao.update(countDummy.getUser(),countDummy.getEditable(),ParseDummyFactory.parseStateCoDToCoDD(countDummy.getState()));
		
		logger.trace("Exit updateCountDummy");
	}

	@Override
	public synchronized void removeCountDummy(CountDummy countDummy) {
		logger.trace("Enter removeCountDummy");
		
		//Borramos un CountDummy en la capa persistencia
		logger.debug("Borramos "+countDummy+" en la capa persistencia");	
		countDummyDao.remove(countDummy.getUser());
		
		logger.trace("Exit removeCountDummy");
	}

	@Override
	public synchronized CountDummy getCountDummy(String user) {
		CountDummy countDummy;
		CountDummyData countDummyData;
		
		logger.trace("Enter getCountDummy");
		
		//Obtenemos un CountDummyData de la capa persistencia
		logger.debug("Obtenemos "+user+" en la capa persistencia");	
		countDummyData = countDummyDao.findbyId(user);
		
		//Parseamos el CountDummyData a CountDummy de la capa negocio
		logger.debug("Parseamos "+countDummyData+" a CountSip de la capa negocio");
		countDummy = ParseDummyFactory.parseCoDDToCoD(countDummyData);
		
		logger.trace("Exit getCountDummy");
		return countDummy;
	}

	@Override
	public synchronized Collection<CountDummy> getAllCountDummy() {
		Collection<CountDummy> countDummyCollection;

		logger.trace("Enter getAllCountDummy");
		
		//Obtenemos todos los CountDummyData de la capa persistencia
		countDummyCollection =  new ArrayList<>();
		for(CountDummyData countDummyData :countDummyDao.findAll() ){
			//Parseamos el CountDummyData a CountDummy de la capa negocio
			logger.debug("Parseamos "+countDummyData+" a CountSip de la capa negocio");
			CountDummy countDummy = ParseDummyFactory.parseCoDDToCoD(countDummyData);
			//Insertamos el CountDummy en la coleccion
			logger.debug("Insertamos "+countDummy+" en la coleccion");
			countDummyCollection.add(countDummy);
		}
		
		logger.trace("Exit getAllCountDummy");
		return countDummyCollection;
	}

	@Override
	public synchronized void addCountSip(CountSip countSip) {
		
		logger.trace("Enter addCountSip");
		
		//Creamos un CountSip en la capa persistencia
		logger.debug("Creamos "+countSip+" en la capa persistencia");		
		countSipDao.save(countSip.getUser(),
						 countSip.getHostServer(),
						 countSip.getPassword(),
						 ParseSipFactory.parseStateCoSToCoSD(countSip.getState()));
		
		logger.trace("Exit addCountSip");
	}

	@Override
	public synchronized void updateCountSip(CountSip countSip) {
		
		logger.trace("Enter updateCountSip");
		
		//Actualizamos un CountSip en la capa persistencia
		logger.debug("Actualizamos "+countSip+" en la capa persistencia");			
		countSipDao.update(countSip.getUser(),
				   		   countSip.getHostServer(),
						   countSip.getPassword(),
						   ParseSipFactory.parseStateCoSToCoSD(countSip.getState()));
		
		logger.trace("Exit updateCountSip");
	}

	@Override
	public synchronized void removeCountSip(CountSip countSip) {
		logger.trace("Enter removeCountSip");
		
		//Borramos un CountSip en la capa persistencia
		logger.debug("Borramos "+countSip+" en la capa persistencia");			
		countSipDao.remove(countSip.getUser(),countSip.getHostServer());
		
		logger.trace("Exit removeCountSip");
		
	}

	@Override
	public synchronized CountSip getCountSip(String user, String hostServer) {
		CountSip countSip;
		CountSipData countSipData;
		
		logger.trace("Enter getCountSip");
		
		//Obtenemos un CountSipData de la capa persistencia
		logger.debug("Obtenemos "+user+"@"+hostServer+" en la capa persistencia");	
		countSipData = countSipDao.findbyId(user,hostServer);
				
		//Parseamos el CountSipData a CountSip de la capa negocio
		logger.debug("Parseamos "+countSipData+" a CountSip de la capa negocio");
		countSip = ParseSipFactory.parseCoSDToCoS(countSipData);
		
		logger.trace("Exit getCountSip");
		return countSip;
	}

	@Override
	public synchronized Collection<CountSip> getAllCountSip() {
		Collection<CountSip> countSipCollection;

		logger.trace("Enter getAllCountSip");
		
		//Obtenemos todos los CountSipData de la capa persistencia
		countSipCollection =  new ArrayList<>();
		for(CountSipData countSipData :countSipDao.findAll() ){
			//Parseamos el CountSipData a CountSip de la capa negocio
			logger.debug("Parseamos "+countSipData+" a CountSip de la capa negocio");
			CountSip countSip = ParseSipFactory.parseCoSDToCoS(countSipData);
			//Insertamos el CountSip en la coleccion
			logger.debug("Insertamos "+countSip+" en la coleccion");
			countSipCollection.add(countSip);
		}		
		logger.trace("Exit getAllCountSip");
		return countSipCollection;
	}

	@Override
	public synchronized void addHistoryCallDummy(HistoryCallDummy historyCallDummy) {
		logger.trace("Enter addHistoryCallDummy");
		
		//Creamos un HistoryCallDummy en la capa persistencia
		logger.debug("Creamos "+historyCallDummy+" en la capa persistencia");	
		historyCallDummyDao.save(historyCallDummy.getIdLlamada(), historyCallDummy.getCount(),historyCallDummy.getNumber(),
				ParseDummyFactory.parseStateHCaDToHCaDD(historyCallDummy.getState()),ParseDummyFactory.parseDirectionHCaDToHCaDD(historyCallDummy.getDirection()),
				historyCallDummy.getDescription(),historyCallDummy.getDate());
		
		logger.trace("Exit addHistoryCallDummy");
	}

	@Override
	public synchronized void removeHistoryCallDummy(HistoryCallDummy historyCallDummy) {
		
		logger.trace("Enter removeHistoryCallDummy");
		
		//Borramos un HistoryCallDummy de la capa persistencia
		logger.debug("Borramos "+historyCallDummy+" en la capa persistencia");		
		historyCallDummyDao.remove(historyCallDummy.getId());
		
		logger.trace("Exit removeHistoryCallDummy");
	}
	
	@Override
	public synchronized void removeAllHistoryCallDummy() {
		logger.trace("Enter removeAllHistoryCallDummy");
		
		//Borramos todos los  HistoryCallDummy de la capa persistencia
		logger.debug("Borramos todos los historicos de llamada dummy en la capa persistencia");			
		historyCallDummyDao.removeAll();
		
		logger.trace("Exit removeAllHistoryCallDummy");
		
	}

	@Override
	public synchronized Collection<HistoryCallDummy> getAllHistoryCallDummy() {
		Collection<HistoryCallDummy> historyCallDummyCollection;

		logger.trace("Enter getAllHistoryCallDummy");
		
		//Obtenemos todos los HistoryCallDummyData de la capa persistencia
		historyCallDummyCollection =  new ArrayList<>();
		for(HistoryCallDummyData historyCallDummyData :historyCallDummyDao.findAll() ){
			//Parseamos el HistoryCallDummyData a HistoryCallDummy de la capa negocio
			logger.debug("Parseamos "+historyCallDummyData+" a HistoryCallSip de la capa negocio");
			HistoryCallDummy historyCallDummy = ParseDummyFactory.parseHCaDDToHCaD(historyCallDummyData);
			//Insertamos el HistoryCallDummy en la coleccion
			logger.debug("Insertamos "+historyCallDummy+" en la coleccion");
			historyCallDummyCollection.add(historyCallDummy);
		}
		
		logger.trace("Exit getAllHistoryCallDummy");
		return historyCallDummyCollection;
	}

	@Override
	public synchronized void addHistoryCallSip(HistoryCallSip historyCallSip) {
		logger.trace("Enter addHistoryCallSip");

		//Creamos un HistoryCallSip en la capa persistencia
		logger.debug("Creamos "+historyCallSip+" en la capa persistencia");	
		historyCallSipDao.save(historyCallSip.getIdLlamada(),historyCallSip.getCount(),historyCallSip.getNumber(),
				ParseSipFactory.parseStateHCaSToHCaSD(historyCallSip.getState()),ParseSipFactory.parseDirectionHCaSToHCaSD(historyCallSip.getDirection()),
				historyCallSip.getDescription(),historyCallSip.getDate());
		
		logger.trace("Exit addHistoryCallSip");
	}

	@Override
	public synchronized void removeHistoryCallSip(HistoryCallSip historyCallSip) {
		logger.trace("Enter removeHistoryCallSip");

		//Borramos un HistoryCallSip de la capa persistencia
		logger.debug("Borramos "+historyCallSip+" en la capa persistencia");		
		historyCallSipDao.remove(historyCallSip.getId());
		
		logger.trace("Exit removeHistoryCallSip");
	}

	@Override
	public synchronized void removeAllHistoryCallSip() {
		logger.trace("Enter removeAllHistoryCallSip");

		//Borramos todos los  HistoryCallSip de la capa persistencia
		logger.debug("Borramos todos los historicos de llamada sip en la capa persistencia");
		historyCallSipDao.removeAll();
		
		logger.trace("Exit removeAllHistoryCallSip");
		
	}
	
	@Override
	public synchronized Collection<HistoryCallSip> getAllHistoryCallSip() {
		Collection<HistoryCallSip> historyCallSipCollection;

		logger.trace("Enter getAllHistoryCallSip");
		
		//Obtenemos todos los HistoryCallSipData de la capa persistencia
		historyCallSipCollection =  new ArrayList<>();
		for(HistoryCallSipData historyCallSipData :historyCallSipDao.findAll() ){
			//Parseamos el HistoryCallSipData a HistoryCallSip de la capa negocio
			logger.debug("Parseamos "+historyCallSipData+" a HistoryCallSip de la capa negocio");
			HistoryCallSip historyCallSip = ParseSipFactory.parseHCaSDToHCaS(historyCallSipData);
			//Insertamos el HistoryCallSip en la coleccion
			logger.debug("Insertamos "+historyCallSip+" en la coleccion");
			historyCallSipCollection.add(historyCallSip);
		}
		
		logger.trace("Exit getAllHistoryCallSip");
		return historyCallSipCollection;
	}

	@Override
	public synchronized void addHistoryCountDummy(HistoryCountDummy historyCountDummy) {
		logger.trace("Enter addHistoryCountDummy");

		//Creamos un HistoryCountDummy en la capa persistencia
		logger.debug("Creamos "+historyCountDummy+" en la capa persistencia");	
		historyCountDummyDao.save(historyCountDummy.getUser(),ParseDummyFactory.parseActionHCoDToHCoDD(historyCountDummy.getAction()),ParseDummyFactory.parseStateHCoDToHCoDD(historyCountDummy.getState()),
				historyCountDummy.getDate(),historyCountDummy.getEditable());
		
		logger.trace("Exit addHistoryCountDummy");
		
	}

	@Override
	public synchronized void removeHistoryCountDummy(HistoryCountDummy historyCountDummy) {
		logger.trace("Enter removeHistoryCountDummy");
	
		//Borramos un HistoryCountDummy de la capa persistencia
		logger.debug("Borramos "+historyCountDummy+" de la capa persistencia");	
		historyCountDummyDao.remove(historyCountDummy.getId());
		
		logger.trace("Exit removeHistoryCountDummy");
		
	}
	
	@Override
	public synchronized void removeAllHistoryCountDummy() {
		logger.trace("Enter removeAllHistoryCountDummy");
		
		//Borramos todos los  HistoryCountDummy de la capa persistencia
		logger.debug("Borramos todos los historicos de cuenta dummy en la capa persistencia");
		historyCountDummyDao.removeAll();
		
		logger.trace("Exit removeAllHistoryCountDummy");
		
	}

	@Override
	public synchronized Collection<HistoryCountDummy> getAllHistoryCountDummy() {
		Collection<HistoryCountDummy> historyCountDummyCollection;

		logger.trace("Enter getAllHistoryCountDummy");
		
		//Obtenemos todos los HistoryCountDummyData de la capa persistencia
		historyCountDummyCollection =  new ArrayList<>();
		for(HistoryCountDummyData historyCountDummyData :historyCountDummyDao.findAll() ){
			//Parseamos el HistoryCountDummyData a HistoryCountDummy de la capa negocio
			logger.debug("Parseamos "+historyCountDummyData+" a HistoryCountDummy de la capa negocio");
			HistoryCountDummy historyCountDummy = ParseDummyFactory.parseHCoDDToHCoD(historyCountDummyData);
			//Insertamos el HistoryCountDummy en la coleccion
			logger.debug("Insertamos "+historyCountDummy+" en la coleccion");
			historyCountDummyCollection.add(historyCountDummy);
		}
		
		logger.trace("Exit getAllHistoryCountDummy");
		return historyCountDummyCollection;
	}

	@Override
	public synchronized void addHistoryCountSip(HistoryCountSip historyCountSip) {
		logger.trace("Enter addHistoryCountSip");

		//Creamos un HistoryCountSip en la capa persistencia
		logger.debug("Creamos "+historyCountSip+" en la capa persistencia");	
		historyCountSipDao.save(historyCountSip.getUser(),
				historyCountSip.getHostServer(),
				ParseSipFactory.parseActionHCoSToHCoSD(historyCountSip.getAction()),
				ParseSipFactory.parseStateHCoSToHCoSD(historyCountSip.getState()),
				historyCountSip.getDate(),
				historyCountSip.getPassword());
		
		logger.trace("Exit addHistoryCountSip");
		
	}

	@Override
	public synchronized void removeHistoryCountSip(HistoryCountSip historyCountSip) {
		logger.trace("Enter removeHistoryCountSip");
	
		//Borramos un HistoryCountSip de la capa persistencia
		logger.debug("Borramos "+historyCountSip+" de la capa persistencia");	
		historyCountSipDao.remove(historyCountSip.getId());
		
		logger.trace("Exit removeHistoryCountSip");
		
	}
	
	@Override
	public synchronized void removeAllHistoryCountSip() {
		logger.trace("Enter removeAllHistoryCountSip");
		
		//Borramos todos los  HistoryCountSip de la capa persistencia
		logger.debug("Borramos todos los historicos de cuenta sip en la capa persistencia");
		historyCountSipDao.removeAll();
		
		logger.trace("Exit removeAllHistoryCountSip");
		
	}


	@Override
	public synchronized Collection<HistoryCountSip> getAllHistoryCountSip() {
		Collection<HistoryCountSip> historyCountSipCollection;

		logger.trace("Enter getAllHistoryCountSip");
		
		//Obtenemos todos los HistoryCountSipData de la capa persistencia
		historyCountSipCollection =  new ArrayList<>();
		for(HistoryCountSipData historyCountSipData :historyCountSipDao.findAll() ){
			//Parseamos el HistoryCountSipData a HistoryCountSip de la capa negocio
			logger.debug("Parseamos "+historyCountSipData+" a HistoryCountSip de la capa negocio");
			HistoryCountSip historyCountSip = ParseSipFactory.parseHCoSDToHCoS(historyCountSipData);
			//Insertamos el HistoryCountSip en la coleccion
			logger.debug("Insertamos "+historyCountSip+" en la coleccion");
			historyCountSipCollection.add(historyCountSip);
		}
		
		logger.trace("Exit getAllHistoryCountSip");
		return historyCountSipCollection;
	}
}
