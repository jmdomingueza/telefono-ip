package com.jmdomingueza.telefonoip.persistencia.factories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;

import com.jmdomingueza.telefonoip.persistencia.daos.CountDummyDao;
import com.jmdomingueza.telefonoip.persistencia.daos.CountDummyDaoImpl;
import com.jmdomingueza.telefonoip.persistencia.daos.CountSipDao;
import com.jmdomingueza.telefonoip.persistencia.daos.CountSipDaoImpl;
import com.jmdomingueza.telefonoip.persistencia.daos.HistoryCallDummyDao;
import com.jmdomingueza.telefonoip.persistencia.daos.HistoryCallDummyDaoImpl;
import com.jmdomingueza.telefonoip.persistencia.daos.HistoryCallSipDao;
import com.jmdomingueza.telefonoip.persistencia.daos.HistoryCallSipDaoImpl;
import com.jmdomingueza.telefonoip.persistencia.daos.HistoryCountDummyDao;
import com.jmdomingueza.telefonoip.persistencia.daos.HistoryCountDummyDaoImpl;
import com.jmdomingueza.telefonoip.persistencia.daos.HistoryCountSipDao;
import com.jmdomingueza.telefonoip.persistencia.daos.HistoryCountSipDaoImpl;

/** Clase que contiene las operaciones para crear los DAO 
 *  de persistencia.
 * 
 * @author jmdomingueza
 *
 */
public class DaoFactory {
	
	private final static Logger logger = Logger.getLogger(DaoFactory.class);
	
	/**
	 * Metodo que crea el DAO de la cuenta dummy
	 * @param emf Interfaz utiliza para interactuar con la fábrica
	 * gestor de la entidad para la unidad de persistencia
	 * @param em Interfaz utiliza para interactuar con el contexto 
	 * de persistencia.
	 * @param tx Interfaz utiliza para controlar las transacciones 
	 * en los administradores de la entidad local de recursos.
	 * @return Dao de la cuenta dummy
	 */
	public static CountDummyDao createCountDummyDao(EntityManagerFactory emf, EntityManager em, EntityTransaction tx){
		CountDummyDao countDummyDao;
		
		logger.trace("Enter createCountDummyDao");
		
		countDummyDao =  new CountDummyDaoImpl(emf,em,tx);
		
		logger.trace("Exit createCountDummyDao");
		return countDummyDao;
	}
	
	/**
	 * Metodo que crea el DAO de la cuenta sip
	 * @param emf Interfaz utiliza para interactuar con la fábrica
	 * gestor de la entidad para la unidad de persistencia
	 * @param em Interfaz utiliza para interactuar con el contexto 
	 * de persistencia.
	 * @param tx Interfaz utiliza para controlar las transacciones 
	 * en los administradores de la entidad local de recursos.
	 * @return Dao de la cuenta sip
	 */
	public static CountSipDao createCountSipDao(EntityManagerFactory emf, EntityManager em, EntityTransaction tx){
		CountSipDao countSipDao;
		logger.trace("Enter createCountSipDao");
		
		countSipDao = new CountSipDaoImpl(emf,em,tx);
		
		logger.trace("Exit createCountSipDao");
		return countSipDao;
	}
	
	/**
	 * Metodo que crea el DAO de la llamada dummy finalizada
	 * @param emf Interfaz utiliza para interactuar con la fábrica
	 * gestor de la entidad para la unidad de persistencia
	 * @param em Interfaz utiliza para interactuar con el contexto 
	 * de persistencia.
	 * @param tx Interfaz utiliza para controlar las transacciones 
	 * en los administradores de la entidad local de recursos.
	 * @return Dao de la llamada dummy finalizada
	 */
	public static HistoryCallDummyDao createHistoryCallDummyDao(EntityManagerFactory emf, EntityManager em, EntityTransaction tx){
		HistoryCallDummyDao historyCallDummyDao;
		
		logger.trace("Enter createHistoryCallDummyDao");
		
		historyCallDummyDao =  new HistoryCallDummyDaoImpl(emf,em,tx);
		
		logger.trace("Exit createHistoryCallDummyDao");
		return historyCallDummyDao;
	}
	
	/**
	 * Metodo que crea el DAO de la llamada sip finalizada
	 * @param emf Interfaz utiliza para interactuar con la fábrica
	 * gestor de la entidad para la unidad de persistencia
	 * @param em Interfaz utiliza para interactuar con el contexto 
	 * de persistencia.
	 * @param tx Interfaz utiliza para controlar las transacciones 
	 * en los administradores de la entidad local de recursos.
	 * @return Dao de la llamada sip finalizada
	 */
	public static HistoryCallSipDao createHistoryCallSipDao(EntityManagerFactory emf, EntityManager em, EntityTransaction tx){
		HistoryCallSipDao historyCallSipDao;
		logger.trace("Enter createHistoryCallSipDao");
		
		historyCallSipDao = new HistoryCallSipDaoImpl(emf,em,tx);
		
		logger.trace("Exit createHistoryCallSipDao");
		return historyCallSipDao;
	}
	
	/**
	 * Metodo que crea el DAO del historico de cuenta dummy
	 * @param emf Interfaz utiliza para interactuar con la fábrica
	 * gestor de la entidad para la unidad de persistencia
	 * @param em Interfaz utiliza para interactuar con el contexto 
	 * de persistencia.
	 * @param tx Interfaz utiliza para controlar las transacciones 
	 * en los administradores de la entidad local de recursos.
	 * @return Dao del historico de cuenta dummy
	 */
	public static HistoryCountDummyDao createHistoryCountDummyDao(EntityManagerFactory emf, EntityManager em, EntityTransaction tx){
		HistoryCountDummyDao historyCountDummyDao;
		
		logger.trace("Enter createHistoryCountDummyDao");
		
		historyCountDummyDao =  new HistoryCountDummyDaoImpl(emf,em,tx);
		
		logger.trace("Exit createHistoryCountDummyDao");
		return historyCountDummyDao;
	}
	
	/**
	 * Metodo que crea el DAO del historico de cuenta sip
	 * @param emf Interfaz utiliza para interactuar con la fábrica
	 * gestor de la entidad para la unidad de persistencia
	 * @param em Interfaz utiliza para interactuar con el contexto 
	 * de persistencia.
	 * @param tx Interfaz utiliza para controlar las transacciones 
	 * en los administradores de la entidad local de recursos.
	 * @return Dao del historico de cuenta sip
	 */
	public static HistoryCountSipDao createHistoryCountSipDao(EntityManagerFactory emf, EntityManager em, EntityTransaction tx){
		HistoryCountSipDao historyCountSipDao;
		logger.trace("Enter createHistoryCountSipDao");
		
		historyCountSipDao = new HistoryCountSipDaoImpl(emf,em,tx);
		
		logger.trace("Exit createHistoryCountSipDao");
		return historyCountSipDao;
	}
	
}
