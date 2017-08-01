package com.jmdomingueza.telefonoip.negocio.beans;

import java.io.Serializable;

/***
 * Interfaz que define las operaciones del bean Audio.
 * @author jmdomingueza
 *
 */
public interface Audio extends Serializable {

	/**
	 * Tipo de Tarjeta de sonido Microphone
	 */
	public static final String TYPE_MICROPHONE = "Microphone";
	
	/**
	 * Tipo de Tarjeta de sonido Micrófono
	 */
	public static final String TYPE_MICROFONO = "Micrófono";
	
	/**
	 * Tipo de Tarjeta de sonido Speakers
	 */
	public static final String TYPE_SPEAKERS ="Speakers";
	
	/**
	 * Tipo de Tarjeta de sonido Altavoces
	 */
	public static final String TYPE_ALTAVOCES ="Altavoces";
	
	/**
	 * Metodo que devuelve el nombre de la tarjeta de sonido 
	 * devuelto por AudioSystem.getMixerInfo
	 */
	public String getNameInfo();

	/**
	 * Metodo que pone el nombre de la tarjeta de sonido 
	 * devuelto por AudioSystem.getMixerInfo
	 */
	public void setNameInfo(String nameInfo);

	/**
	 * Metodo que devuelve el tipo de tarjeta de sonido 
	 * (Microfono o Altavoz) 
	 */
	public String getType();

	/**
	 * Metodo que pone el tipo de tarjeta de sonido 
	 * (Microfono o Altavoz)
	 */
	public void setType(String type);

}
