package com.jmdomingueza.telefonoip.persistencia.daos;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.jmdomingueza.telefonoip.persistencia.entities.CountDummyData;
import com.jmdomingueza.telefonoip.persistencia.entities.CountDummyDataImpl;
import com.jmdomingueza.telefonoip.persistencia.exception.NotDataonDatabaseException;
import com.jmdomingueza.telefonoip.persistencia.factories.EntityFactory;

/**
 * Clase que implementa las operaciones que se pueden realizar con la tabla
 * CUENTA_DUMMY.
 * @author jmdomingueza
 *
 */
public class CountDummyDaoImpl extends GenericDao<CountDummyData> implements CountDummyDao{

	private final static Logger logger = Logger.getLogger(CountDummyDaoImpl.class);

	/**
	 * Construtor de la clase
	 * @param emf Interfaz utiliza para interactuar con la fábrica
	 * gestor de la entidad para la unidad de persistencia
	 * @param em Interfaz utiliza para interactuar con el contexto 
	 * de persistencia.
	 * @param tx Interfaz utiliza para controlar las transacciones 
	 * en los administradores de la entidad local de recursos.
	 */
	public CountDummyDaoImpl(EntityManagerFactory emf, EntityManager em, EntityTransaction tx) {		
		super(emf, em, tx);
		logger.trace("Enter CountDummyDaoImpl");
		
		logger.debug("Creada una instancia de CountDummyDao: "+this);
		
		logger.trace("Exit CountDummyDaoImpl");
	}

	@Override
	public  synchronized void save(String user,String editable, CountDummyData.State state){
		long timeInit, timefinish;
		CountDummyData countDummy;

		logger.trace("Enter save");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();
			
			countDummy = EntityFactory.createCountDummyData(user,editable, state);
			
			em.persist(countDummy);
			
			tx.commit();
			logger.debug("Insertada en la BBDD la cuenta dummy : "+countDummy);
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
	public synchronized void update(String user,String editable, CountDummyData.State state) {
		long timeInit, timefinish;
		CountDummyData countDummy;
		
		logger.trace("Enter update");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();

			countDummy = em.find(CountDummyDataImpl.class, user);
			
			if(countDummy==null){
				throw new NotDataonDatabaseException("Not countDummy "+user+" on Database");
			}
			
			countDummy.setEditable(editable);
			countDummy.setState(state);
			
			em.persist(countDummy);
			
			tx.commit();
			logger.debug("Actualizada en la BBDD la cuenta dummy : "+countDummy);
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
	public synchronized void remove(String user){
		long timeInit, timefinish;
		CountDummyData countDummy;
		logger.trace("Enter remove");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();

			countDummy = em.find(CountDummyDataImpl.class, user);
			if(countDummy==null){
				throw new NotDataonDatabaseException("Not countDummy "+user+" on Database");
			}
			
			em.remove(countDummy);
			
			tx.commit();
			logger.debug("Borrada en la BBDD la cuenta dummy : "+countDummy);
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
	public synchronized Collection<CountDummyData> findAll(){
		long timeInit, timefinish;
		Collection<CountDummyData> countDummyCollection = null;
		
		logger.trace("Enter findAll");
		
		timeInit = System.currentTimeMillis();
		try {
			TypedQuery<CountDummyData> select =
					em.createQuery("select cd from CountDummyDataImpl cd",CountDummyData.class);
			countDummyCollection = select.getResultList();
			logger.debug("Buscadas todas las cuentas dummy - size("+countDummyCollection.size()+")");
		} catch (Exception e) {
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit findAll - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit findAll");
		
		return countDummyCollection;
	}
	
	@Override
	public synchronized CountDummyData findbyId(String user){
		long timeInit, timefinish;
		CountDummyData countDummy = null;
		
		logger.trace("Enter findbyId");
		
		timeInit = System.currentTimeMillis();
		try {
			countDummy = em.find(CountDummyDataImpl.class, user);
			logger.debug("Buscada la cuenta dummy - id("+user+"): "+countDummy);
		} catch (Exception e) {
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit findbyId - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit findbyId");
		return countDummy;
	}

}
