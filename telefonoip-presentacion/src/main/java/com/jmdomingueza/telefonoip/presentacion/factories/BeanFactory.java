package com.jmdomingueza.telefonoip.presentacion.factories;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.presentacion.beans.CategoryResource;
import com.jmdomingueza.telefonoip.presentacion.beans.CategoryResourceImpl;

/** 
 * Clase que contiene las operaciones para crear los beans 
 *  de presentacion.
 * 
 * @author jmdomingueza
 *
 */
public class BeanFactory {

	private static final Log logger = LogFactory.getLog(BeanFactory.class);
	
	/**
	 * Metodo que crea una categoria a partir pasando el nombre
	 * de la carpeta y el locale del idioma que buscar
	 * @param resourceRoot  Nombre de la carpeta donde estan los recursos
	 * @param locale Idioma sobre el que se crea la carpeta donde estan los
	 *            recursos
	 * @return
	 */
	public static CategoryResource createCategoryResource(String resourceRoot, Locale locale) {
		CategoryResource categoryResource;
		logger.trace("Enter createCategory");
		
		categoryResource = new CategoryResourceImpl(resourceRoot, locale);
		
		logger.trace("Exit createCategory");
		return categoryResource;
	}

}
