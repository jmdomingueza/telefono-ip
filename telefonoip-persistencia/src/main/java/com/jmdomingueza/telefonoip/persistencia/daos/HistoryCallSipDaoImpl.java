package com.jmdomingueza.telefonoip.persistencia.daos;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallSipData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallSipDataImpl;
import com.jmdomingueza.telefonoip.persistencia.exception.NotDataonDatabaseException;
import com.jmdomingueza.telefonoip.persistencia.factories.EntityFactory;

/**
 * Clase que implementa las operaciones que se pueden realizar con la tabla
 * HISTORICO_LLAMADA_SIP.
 * @author jmdomingueza
 *
 */
public class HistoryCallSipDaoImpl extends GenericDao<HistoryCallSipData> implements HistoryCallSipDao{

	private final static Logger logger = Logger.getLogger(HistoryCallSipDaoImpl.class);

	/**
	 * Construtor de la clase
	 * @param emf Interfaz utiliza para interactuar con la fábrica
	 * gestor de la entidad para la unidad de persistencia
	 * @param em Interfaz utiliza para interactuar con el contexto 
	 * de persistencia.
	 * @param tx Interfaz utiliza para controlar las transacciones 
	 * en los administradores de la entidad local de recursos.
	 */
	public HistoryCallSipDaoImpl(EntityManagerFactory emf, EntityManager em, EntityTransaction tx) {		
		super(emf, em, tx);
		logger.trace("Enter HistoryCallSipDaoImpl");
		
		logger.debug("Creada una instancia de HistoryCallSipDao: "+this);
		
		logger.trace("Exit HistoryCallSipDaoImpl");
	}

	@Override
	public synchronized void save(int idLlamada, String count, String number, HistoryCallSipData.State state, HistoryCallSipData.Direction direction,
			String description, Date date){
		long timeInit, timefinish;
		HistoryCallSipData historyCallSip;

		logger.trace("Enter save");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();
			
			historyCallSip = EntityFactory.createHistoryCallSipData(idLlamada, count, number, state, direction, description, date);
			
			em.persist(historyCallSip);
			
			tx.commit();
			logger.debug("Insertado en la BBDD el historico de llamada sip: "+historyCallSip);
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
	public synchronized void update(Long id, int idLlamada, String count, String number, HistoryCallSipData.State state, HistoryCallSipData.Direction direction,
			String description, Date date) {
		long timeInit, timefinish;
		HistoryCallSipData historyCallSip;
		
		logger.trace("Enter update");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();

			historyCallSip = em.find(HistoryCallSipDataImpl.class, id);
			
			if(historyCallSip==null){
				throw new NotDataonDatabaseException("Not historyCallSip "+id+" on Database");
			}
			
			historyCallSip.setIdLlamada(idLlamada);
			historyCallSip.setCount(count);
			historyCallSip.setNumber(number);
			historyCallSip.setDirection(direction);
			historyCallSip.setState(state);
			historyCallSip.setDescription(description);
			historyCallSip.setDate(date);
			
			em.persist(historyCallSip);
			
			tx.commit();
			logger.debug("Actualizado en la BBDD el historico de llamada sip: "+historyCallSip);
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
		HistoryCallSipData historyCallSip;
		logger.trace("Enter remove");
		
		timeInit = System.currentTimeMillis();
		try {
			tx.begin();

			historyCallSip = em.find(HistoryCallSipDataImpl.class, id);
			
			if(historyCallSip==null){
				throw new NotDataonDatabaseException("Not historyCallSip "+id+" on Database");
			}
			
			em.remove(historyCallSip);
			
			tx.commit();
			logger.debug("Borrado en la BBDD el historico de llamada sip: "+historyCallSip);
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
			
			em.createQuery("delete from HistoryCallSipDataImpl").executeUpdate();
			
			tx.commit();
			logger.debug("Borrados todos los historicos de llamada sip - size(0)");
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
	public synchronized Collection<HistoryCallSipData> findAll(){
		long timeInit, timefinish;
		Collection<HistoryCallSipData> historyCallSipCollection = null;
		
		logger.trace("Enter findAll");
		
		timeInit = System.currentTimeMillis();
		try {
			TypedQuery<HistoryCallSipData> select =
					em.createQuery("select hcd from HistoryCallSipDataImpl hcd",HistoryCallSipData.class);
			historyCallSipCollection = select.getResultList();
			logger.debug("Buscados todos los historicos de llamada sip - size("+historyCallSipCollection.size()+")");
		} catch (Exception e) {
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit findAll - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit findAll");
		
		return historyCallSipCollection;
	}
	
	@Override
	public synchronized HistoryCallSipData findbyId(Long id){
		long timeInit, timefinish;
		HistoryCallSipData historyCallSip=null;
		
		logger.trace("Enter findbyId");
		
		timeInit = System.currentTimeMillis();
		try {
			historyCallSip = em.find(HistoryCallSipDataImpl.class, id);
			logger.debug("Buscado el historico de llamada sip - id("+id+"): "+historyCallSip);
		} catch (Exception e) {
			timefinish = System.currentTimeMillis();
			logger.debug("Tiempo de ejecucion con excepcion: "+(timefinish-timeInit));
			logger.trace("Exit findbyId - Exception");
			throw e;
		}
		timefinish = System.currentTimeMillis();
		logger.debug("Tiempo de ejecucion: "+(timefinish-timeInit));
		logger.trace("Exit findbyId");
		return historyCallSip;
	}

}
