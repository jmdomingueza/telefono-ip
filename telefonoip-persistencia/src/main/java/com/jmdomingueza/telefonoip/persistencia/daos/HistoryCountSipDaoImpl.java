package com.jmdomingueza.telefonoip.persistencia.daos;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountSipData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountSipDataImpl;
import com.jmdomingueza.telefonoip.persistencia.exception.NotDataonDatabaseException;
import com.jmdomingueza.telefonoip.persistencia.factories.EntityFactory;

/**
 * Clase que implementa las operaciones que se pueden realizar con la tabla
 * HISTORICO_HISTORICO_CUENTA_SIP.
 * @author jmdomingueza
 *
 */
public class HistoryCountSipDaoImpl extends GenericDao<HistoryCountSipData> implements HistoryCountSipDao{

	private final static Logger logger = Logger.getLogger(HistoryCountSipDaoImpl.class);

	/**
	 * Construtor de la clase
	 * @param emf Interfaz utiliza para interactuar con la fábrica
	 * gestor de la entidad para la unidad de persistencia
	 * @param em Interfaz utiliza para interactuar con el contexto 
	 * de persistencia.
	 * @param tx Interfaz utiliza para controlar las transacciones 
	 * en los administradores de la entidad local de recursos.
	 */
	public HistoryCountSipDaoImpl(EntityManagerFactory emf, EntityManager em, EntityTransaction tx) {
		super(emf, em, tx);
		
		logger.trace("Enter HistoryCountSipDaoImpl");
		
		logger.debug("Creada una instancia de HistoryCountSipDao: "+this);
		
		logger.trace("Exit HistoryCountSipDaoImpl");
	}

	@Override
	public synchronized void save(String user, String hostServer, HistoryCountSipData.Action action, HistoryCountSipData.State state, Date date, String password){
		long timeInit, timefinish;
		HistoryCountSipData historyCountSip;

		logger.trace("Enter save");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();
			
			historyCountSip = EntityFactory.createHistoryCountSipData(user,hostServer,action,state, date,password);
			
			em.persist(historyCountSip);
			
			tx.commit();
			logger.debug("Insertado en la BBDD el historico de cuenta sip: "+historyCountSip);
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
	public synchronized void update(Long id, String user, String hostServer,HistoryCountSipData.Action action, HistoryCountSipData.State state, Date date, String password) {
		long timeInit, timefinish;
		HistoryCountSipData historyCountSip;
		
		logger.trace("Enter update");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();
			
			
			historyCountSip = em.find(HistoryCountSipDataImpl.class, id);
			
			if(historyCountSip==null){
				throw new NotDataonDatabaseException("Not historyCountDummy "+id+" on Database");
			}
			
			historyCountSip.setUser(user);
			historyCountSip.setHostServer(hostServer);
			historyCountSip.setAction(action);
			historyCountSip.setState(state);
			historyCountSip.setDate(date);
			historyCountSip.setPassword(password);
			
			em.persist(historyCountSip);
			
			tx.commit();
			logger.debug("Actualizado en la BBDD el historico de cuenta sip: "+historyCountSip);
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
		HistoryCountSipData historyCountSip;
		logger.trace("Enter remove");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();

			historyCountSip = em.find(HistoryCountSipDataImpl.class, id);
			if(historyCountSip==null){
				throw new NotDataonDatabaseException("Not historyCountDummy "+id+" on Database");
			}
			
			em.remove(historyCountSip);
			
			tx.commit();
			logger.debug("Borrado en la BBDD la cuenta sip: "+historyCountSip);
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
			
			em.createQuery("delete from HistoryCountSipDataImpl").executeUpdate();
			
			tx.commit();
			logger.debug("Borrados todos los historicos de cuenta sip - size(0)");
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
	public synchronized Collection<HistoryCountSipData> findAll(){
		long timeInit, timefinish;
		Collection<HistoryCountSipData> historyCountSipCollection = null;
		
		logger.trace("Enter findAll");
		
		timeInit = System.currentTimeMillis();
		try {
			TypedQuery<HistoryCountSipData> select =
					em.createQuery("select hcd from HistoryCountSipDataImpl hcd",HistoryCountSipData.class);
			historyCountSipCollection = select.getResultList();
			logger.debug("Buscados todos los historicos de cuenta sip - size("+historyCountSipCollection.size()+")");
		} catch (Exception e) {
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit findAll - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit findAll");
		
		return historyCountSipCollection;
	}
	
	@Override
	public synchronized HistoryCountSipData findbyId(Long id){
		long timeInit, timefinish;
		HistoryCountSipData historyCountSip = null;
		
		logger.trace("Enter findbyId");
		
		timeInit = System.currentTimeMillis();
		try {
			historyCountSip = em.find(HistoryCountSipDataImpl.class, id);
			logger.debug("Buscado el historico de cuenta sip - id("+id+"): "+historyCountSip);
		} catch (Exception e) {
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit findbyId - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit findbyId");
		return historyCountSip;
	}

}
