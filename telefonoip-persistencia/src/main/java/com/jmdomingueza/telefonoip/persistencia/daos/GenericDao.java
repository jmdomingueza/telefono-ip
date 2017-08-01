package com.jmdomingueza.telefonoip.persistencia.daos;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;

/**
 * Clase abstracta que implemneta las operaciones generica de un Dao 
 * @author jmdomingueza
 *
 * @param <T>
 */
public abstract class GenericDao<T> {

	private final static Logger logger = Logger.getLogger(GenericDao.class);

	protected EntityManagerFactory emf = null;
	protected  EntityManager em = null;
	protected EntityTransaction tx = null;

	/**
	 * Construtor de la clase
	 * @param emf
	 * @param em
	 * @param tx
	 */
	public GenericDao(EntityManagerFactory emf, EntityManager em, EntityTransaction tx) {
		
		logger.trace("Enter GenericDao");
		
		this.emf = emf;
		this.em = em;
		this.tx = tx;
		
		logger.trace("Exit GenericDao");
	}
	
}
