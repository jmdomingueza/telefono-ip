package com.jmdomingueza.telefonoip.negocio.services;

import com.jmdomingueza.telefonoip.negocio.beans.Audio;

/**
 * Interfaz que define las diferentes operaciones de configuracion generales.
 * @author jmdomingueza
 *
 */
public interface ConfigurationService {

	/**
	 * Metodo que configura como altavoz predefinido el que pasamos
	 * en el audio, envia esa configuracion a las diferentes tecnologias
	 * ip (sip, dummy).
	 * @param audio
	 */
	public void configureSpeaker(Audio audio);
	
	/**
	 * Metodo que configura como microfono predefinido el que pasamos
	 * en el audio, envia esa configuracion a las diferentes tecnologias
	 * ip (sip, dummy).
	 * @param audio
	 */
	public void configureMicrophone(Audio audio);	
}
