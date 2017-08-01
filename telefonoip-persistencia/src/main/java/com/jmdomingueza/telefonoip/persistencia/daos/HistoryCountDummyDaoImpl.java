package com.jmdomingueza.telefonoip.persistencia.daos;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountDummyData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountDummyDataImpl;
import com.jmdomingueza.telefonoip.persistencia.exception.NotDataonDatabaseException;
import com.jmdomingueza.telefonoip.persistencia.factories.EntityFactory;

/**
 * Clase que implementa las operaciones que se pueden realizar con la tabla
 * HISTORICO_CUENTA_DUMMY.
 * @author jmdomingueza
 *
 */
public class HistoryCountDummyDaoImpl extends GenericDao<HistoryCountDummyData> implements HistoryCountDummyDao{

	private final static Logger logger = Logger.getLogger(HistoryCountDummyDaoImpl.class);

	/**
	 * Construtor de la clase
	 * @param emf Interfaz utiliza para interactuar con la fábrica
	 * gestor de la entidad para la unidad de persistencia
	 * @param em Interfaz utiliza para interactuar con el contexto 
	 * de persistencia.
	 * @param tx Interfaz utiliza para controlar las transacciones 
	 * en los administradores de la entidad local de recursos.
	 */
	public HistoryCountDummyDaoImpl(EntityManagerFactory emf, EntityManager em, EntityTransaction tx) {		
		super(emf, em, tx);
		logger.trace("Enter HistoryCountDummyDaoImpl");
		
		logger.debug("Creada una instancia de HistoryCountDummyDao: "+this);
		
		logger.trace("Exit HistoryCountDummyDaoImpl");
	}

	@Override
	public synchronized void save(String user, HistoryCountDummyData.Action action, HistoryCountDummyData.State state, Date date,String editable){
		long timeInit, timefinish;
		HistoryCountDummyData historyCountDummy;

		logger.trace("Enter save");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();
			
			historyCountDummy = EntityFactory.createHistoryCountDummyData(user,action, state, date,editable);
			
			em.persist(historyCountDummy);
			
			tx.commit();
			logger.debug("Insertado en la BBDD el historico de cuenta dummy: "+historyCountDummy);
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
	public synchronized void update(Long  id,String user, HistoryCountDummyData.Action action, HistoryCountDummyData.State state, Date date,String editable) {
		long timeInit, timefinish;
		HistoryCountDummyData historyCountDummy;
		
		logger.trace("Enter update");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();

			historyCountDummy = em.find(HistoryCountDummyDataImpl.class, id);
			
			if(historyCountDummy==null){
				throw new NotDataonDatabaseException("Not historyCountDummy "+id+" on Database");
			}
			
			historyCountDummy.setUser(user);
			historyCountDummy.setAction(action);
			historyCountDummy.setState(state);
			historyCountDummy.setDate(date);
			historyCountDummy.setEditable(editable);
			
			
			em.persist(historyCountDummy);
			
			tx.commit();
			logger.debug("Actualizado en la BBDD el historico de cuenta dummy: "+historyCountDummy);
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
	public synchronized void remove(Long  id){
		long timeInit, timefinish;
		HistoryCountDummyData historyCountDummy;
		logger.trace("Enter remove");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();

			historyCountDummy = em.find(HistoryCountDummyDataImpl.class, id);
			if(historyCountDummy==null){
				throw new NotDataonDatabaseException("Not historyCountDummy "+id+" on Database");
			}
			
			em.remove(historyCountDummy);
			
			tx.commit();
			logger.debug("Borrado en la BBDD el historico de cuenta dummy: "+historyCountDummy);
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
			
			em.createQuery("delete from HistoryCountDummyDataImpl").executeUpdate();
			
			tx.commit();
			logger.debug("Borrados todos los historicos de cuenta dummy - size(0)");
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
	public synchronized Collection<HistoryCountDummyData> findAll(){
		long timeInit, timefinish;
		Collection<HistoryCountDummyData> historyCountDummyCollection = null;
		
		logger.trace("Enter findAll");
		
		timeInit = System.currentTimeMillis();
		try {
			TypedQuery<HistoryCountDummyData> select =
					em.createQuery("select hcd from HistoryCountDummyDataImpl hcd",HistoryCountDummyData.class);
			historyCountDummyCollection = select.getResultList();
			logger.debug("Buscados todos los historicos de cuentas dummy - size("+historyCountDummyCollection.size()+")");
		} catch (Exception e) {
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit findAll - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit findAll");
		
		return historyCountDummyCollection;
	}
	
	@Override
	public synchronized HistoryCountDummyData findbyId(Long  id){
		long timeInit, timefinish;
		HistoryCountDummyData historyCountDummy = null;
		
		logger.trace("Enter findbyId");
		
		timeInit = System.currentTimeMillis();
		try {
			historyCountDummy = em.find(HistoryCountDummyDataImpl.class, id);
			logger.debug("Buscado el historico de cuenta dummy - id("+id+"): "+historyCountDummy);
		} catch (Exception e) {
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit findbyId - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit findbyId");
		return historyCountDummy;
	}

}
