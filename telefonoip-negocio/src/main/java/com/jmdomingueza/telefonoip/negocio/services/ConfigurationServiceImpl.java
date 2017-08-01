package com.jmdomingueza.telefonoip.negocio.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.negocio.beans.Audio;
import com.jmdomingueza.telefonoip.negocio.factories.ParseSipFactory;
import com.jmdomingueza.telefonoip.rtp.services.RTPService;

/**
 * Clase  que implementa las diferentes operaciones de configuracion generales.
 * @author jmdomingueza
 *
 */
@Service(value = "configurationService")
public class ConfigurationServiceImpl implements ConfigurationService{

	private static Log logger = LogFactory.getLog(ConfigurationServiceImpl.class);

	@Autowired
	private RTPService rtpService;
	
	@Override
	public void configureSpeaker(Audio audio){
		
		logger.trace("Enter configureSpeaker");
		
		//Enviamos la configuracion a la capa sip para que sepa porque
		// tarjeta debe sonar el audio enviado via rtp
		rtpService.configureSpeaker(ParseSipFactory.parseAToAS(audio));
		
		logger.trace("Exit configureSpeaker");
	}
	
	@Override
	public void configureMicrophone(Audio audio){
		
		logger.trace("Enter configureMicrophone");
		
		//Enviamos la configuracion a la capa sip para que sepa porque
		// tarjeta debe coger el audio que sera enviado via rtp
		rtpService.configureMicrophone(ParseSipFactory.parseAToAS(audio));
		
		logger.trace("Exit configureMicrophone");
	}
}
