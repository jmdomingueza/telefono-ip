package com.jmdomingueza.telefonoip.presentacion.services;

import java.awt.Color;
import java.awt.Image;
import java.util.Locale;

import javax.swing.Icon;

/**
 * Interfaz que define los metodos de ResourceService
 * El Servicio ResourceService
 * se encarga de obtener los recursos(textos, iconos,imagenes,etc) que tenemos
 * en resouces. Dependiendo del idioma que este cargado carga un resource o
 * otro.
 * @author jmdomingueza
 *
 */
public interface ResourceService {

	/**
	 * Metodo que carga el Idioma que pasamos como parametro y partir de
	 * entonces Los resources son cargados de este idioma
	 * 
	 * @param locale
	 */
	public void loadLocale(Locale locale);
	
	/**
	 * Metodo que devuelve el Boolean de la propiedad
	 * Si no existe la propiedad devuelve false
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key);
	
	/**
	 * Metodo que devuelve el Icon de la propiedad
	 * Si no existe la propiedad devuelve null
	 * @param key
	 * @return
	 */
	public Icon getIcon(String key);
	
	/**
	 * Metodo que devuelve el Image de la propiedad
	 * Si no existe la propiedad devuelve null
	 * @param categoryName
	 * @param key
	 * @return
	 */
	public Image getImage(String categoryName, String key);
	
	/**
	 * Metodo que devuelve el int de la propiedad
	 * Si no existe la propiedad devuelve 0
	 * @param key
	 * @return
	 */
	public int getInt(String key);
	
	/**
	 * Metodo que devuelve el Color de la propiedad
	 * Si no existe la propiedad devuelve null
	 * @param key
	 * @return
	 */
	public Color getColor(String key);
	
	/**
	 * Metodo que devuelve el String
	 * 
	 * @param key
	 * @param objs
	 * @return
	 */
	public String getString(String key, Object... objs);
	
	/**
	 * Metodo que devuelve la propiedad que corresponde con la clave
	 * @param key
	 * @return
	 */
	public String getProperty(String key);
}
