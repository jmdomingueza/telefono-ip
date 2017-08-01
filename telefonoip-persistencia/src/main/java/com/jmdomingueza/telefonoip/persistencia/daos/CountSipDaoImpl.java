package com.jmdomingueza.telefonoip.persistencia.daos;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.jmdomingueza.telefonoip.persistencia.entities.CountSipData;
import com.jmdomingueza.telefonoip.persistencia.entities.CountSipDataImpl;
import com.jmdomingueza.telefonoip.persistencia.entities.CountSipDataPKImpl;
import com.jmdomingueza.telefonoip.persistencia.exception.NotDataonDatabaseException;
import com.jmdomingueza.telefonoip.persistencia.factories.EntityFactory;

/**
 * Clase que implementa las operaciones que se pueden realizar con la tabla
 * CUENTA_SIP.
 * @author jmdomingueza
 *
 */
public class CountSipDaoImpl extends GenericDao<CountSipData> implements CountSipDao{

	private final static Logger logger = Logger.getLogger(CountSipDaoImpl.class);

	/**
	 * Construtor de la clase
	 * @param emf Interfaz utiliza para interactuar con la fábrica
	 * gestor de la entidad para la unidad de persistencia
	 * @param em Interfaz utiliza para interactuar con el contexto 
	 * de persistencia.
	 * @param tx Interfaz utiliza para controlar las transacciones 
	 * en los administradores de la entidad local de recursos.
	 */
	public CountSipDaoImpl(EntityManagerFactory emf, EntityManager em, EntityTransaction tx) {
		super(emf, em, tx);
		
		logger.trace("Enter CountSipDaoImpl");
		
		logger.debug("Creada una instancia de CountSipDao: "+this);
		
		logger.trace("Exit CountSipDaoImpl");
	}

	@Override
	public synchronized void save(String user, String hostServer,String password,CountSipData.State state){
		long timeInit, timefinish;
		CountSipData countSip;

		logger.trace("Enter save");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();
			
			countSip = EntityFactory.createCountSipData(user,hostServer,password,state);
			
			em.persist(countSip);
			
			tx.commit();
			logger.debug("Insertada en la BBDD la cuenta sip : "+countSip);
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit save - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit save");
	}
	
	@Override
	public synchronized void update(String user,String hostServer,String password,CountSipData.State state) {
		long timeInit, timefinish;
		CountSipData countSip;
		
		logger.trace("Enter update");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();
			
			CountSipDataPKImpl pk = new CountSipDataPKImpl(user,hostServer);

			countSip = em.find(CountSipDataImpl.class, pk);
			
			if(countSip==null){
				throw new NotDataonDatabaseException("Not countSip "+user+"@"+hostServer+" on Database");
			}
			
			countSip.setUser(user);
			countSip.setPassword(password);
			countSip.setHostServer(hostServer);
			countSip.setState(state);
			
			em.persist(countSip);
			
			tx.commit();
			logger.debug("Actualizada en la BBDD la cuenta sip : "+countSip);
		}catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit update - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit update");
	}
	
	@Override
	public synchronized void remove(String user, String hostServer){
		long timeInit, timefinish;
		CountSipData countSip;
		logger.trace("Enter remove");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();

			CountSipDataPKImpl pk = new CountSipDataPKImpl(user,hostServer);
			
			countSip = em.find(CountSipDataImpl.class, pk);
			if(countSip==null){
				throw new NotDataonDatabaseException("Not countSip "+user+"@"+hostServer+" on Database");
			}
			
			em.remove(countSip);
			
			tx.commit();
			logger.debug("Borrada en la BBDD la cuenta sip : "+countSip);
		} catch (Exception e) {
			if(tx !=null)
				tx.rollback();
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit remove - Exception");
			throw e;
		}
		
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit remove");
		return;
	}
	
	@Override
	public synchronized Collection<CountSipData> findAll(){
		long timeInit, timefinish;
		Collection<CountSipData> countSipCollection = null;
		
		logger.trace("Enter findAll");
		
		timeInit = System.currentTimeMillis();
		try {
			TypedQuery<CountSipData> select =
					em.createQuery("select cd from CountSipDataImpl cd",CountSipData.class);
			countSipCollection = select.getResultList();
			logger.debug("Buscadas todas las cuentas sip - size("+countSipCollection.size()+")");
		} catch (Exception e) {
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit findAll - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit findAll");
		
		return countSipCollection;
	}
	
	@Override
	public synchronized CountSipData findbyId(String user, String hostServer){
		long timeInit, timefinish;
		CountSipData countSip = null;
		
		logger.trace("Enter findbyId");
		
		timeInit = System.currentTimeMillis();
		try {
			CountSipDataPKImpl pk = new CountSipDataPKImpl(user,hostServer);
			countSip = em.find(CountSipDataImpl.class, pk);
			logger.debug("Buscada la cuenta sip - id("+pk+"): "+countSip);
		} catch (Exception e) {
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit findbyId - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit findbyId");
		return countSip;
	}

}
