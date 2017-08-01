package com.jmdomingueza.telefonoip.persistencia.daos;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallDummyData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallDummyDataImpl;
import com.jmdomingueza.telefonoip.persistencia.exception.NotDataonDatabaseException;
import com.jmdomingueza.telefonoip.persistencia.factories.EntityFactory;

/**
 * Clase que implementa las operaciones que se pueden realizar con la tabla
 * HISTORICO_LLAMADA_DUMMY.
 * @author jmdomingueza
 *
 */
public class HistoryCallDummyDaoImpl extends GenericDao<HistoryCallDummyData> implements HistoryCallDummyDao{

	private final static Logger logger = Logger.getLogger(HistoryCallDummyDaoImpl.class);

	/**
	 * Construtor de la clase
	 * @param emf Interfaz utiliza para interactuar con la fábrica
	 * gestor de la entidad para la unidad de persistencia
	 * @param em Interfaz utiliza para interactuar con el contexto 
	 * de persistencia.
	 * @param tx Interfaz utiliza para controlar las transacciones 
	 * en los administradores de la entidad local de recursos.
	 */
	public HistoryCallDummyDaoImpl(EntityManagerFactory emf, EntityManager em, EntityTransaction tx) {		
		super(emf, em, tx);
		logger.trace("Enter HistoryCallDummyDaoImpl");
		
		logger.debug("Creada una instancia de HistoryCallDummyDao: "+this);
		
		logger.trace("Exit HistoryCallDummyDaoImpl");
	}

	@Override
	public synchronized void save(int idLlamada, String count, String number, HistoryCallDummyData.State state, HistoryCallDummyData.Direction direction,
			String description, Date date){
		long timeInit, timefinish;
		HistoryCallDummyData historyCallDummy;

		logger.trace("Enter save");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();
			
			historyCallDummy = EntityFactory.createHistoryCallDummyData(idLlamada, count, number, state, direction, description,date);
			
			em.persist(historyCallDummy);
			
			tx.commit();
			logger.debug("Insertado en la BBDD el historico de llamada dummy: "+historyCallDummy);
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
	public synchronized void update(Long id, int idLlamada, String count, String number, HistoryCallDummyData.State state, HistoryCallDummyData.Direction direction,
			String description, Date date) {
		long timeInit, timefinish;
		HistoryCallDummyData historyCallDummy;
		
		logger.trace("Enter update");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();

			historyCallDummy = em.find(HistoryCallDummyDataImpl.class, id);
			
			if(historyCallDummy==null){
				throw new NotDataonDatabaseException("Not historyCallDummy "+id+" on Database");
			}
			historyCallDummy.setIdLlamada(idLlamada);
			historyCallDummy.setCount(count);
			historyCallDummy.setNumber(number);
			historyCallDummy.setDirection(direction);
			historyCallDummy.setState(state);
			historyCallDummy.setDescription(description);
			historyCallDummy.setDate(date);
			
			em.persist(historyCallDummy);
			
			tx.commit();
			logger.debug("Actualizado en la BBDD el historico de llamada dummy: "+historyCallDummy);
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
	public synchronized void remove(Long id){
		long timeInit, timefinish;
		HistoryCallDummyData historyCallDummy;
		logger.trace("Enter remove");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();

			historyCallDummy = em.find(HistoryCallDummyDataImpl.class, id);
			
			if(historyCallDummy==null){
				throw new NotDataonDatabaseException("Not historyCallDummy "+id+" on Database");
			}
			
			em.remove(historyCallDummy);
			
			tx.commit();
			logger.debug("Borrado en la BBDD el hsitorico de llamada dummy: "+historyCallDummy);
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
	public synchronized void removeAll() {
		long timeInit, timefinish;
		
		logger.trace("Enter removeAll");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();
			
			em.createQuery("delete from HistoryCallDummyDataImpl").executeUpdate();
			
			tx.commit();
			logger.debug("Borrados todos los historicos de llamada dummy - size(0)");
		} catch (Exception e) {
			if(tx !=null)
				tx.rollback();
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit removeAll - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit removeAll");
	}
	
	@Override
	public synchronized Collection<HistoryCallDummyData> findAll(){
		long timeInit, timefinish;
		Collection<HistoryCallDummyData> historyCallDummyCollection = null;
		
		logger.trace("Enter findAll");
		
		timeInit = System.currentTimeMillis();
		try {
			TypedQuery<HistoryCallDummyData> select =
					em.createQuery("select hcd from HistoryCallDummyDataImpl hcd",HistoryCallDummyData.class);
			historyCallDummyCollection = select.getResultList();
			logger.debug("Buscados todos los historicos de llamada dummy - size("+historyCallDummyCollection.size()+")");
		} catch (Exception e) {
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit findAll - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit findAll");
		
		return historyCallDummyCollection;
	}
	
	@Override
	public synchronized HistoryCallDummyData findbyId(Long id){
		long timeInit, timefinish;
		HistoryCallDummyData historyCallDummy=null;
		
		logger.trace("Enter findbyId");
		
		timeInit = System.currentTimeMillis();
		try {
			historyCallDummy = em.find(HistoryCallDummyDataImpl.class, id);
			logger.debug("Buscado el historico de llamada dummy - id("+id+"): "+historyCallDummy);
		} catch (Exception e) {
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit findbyId - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit findbyId");
		return historyCallDummy;
	}

	

}
