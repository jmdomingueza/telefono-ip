package com.jmdomingueza.telefonoip.sip.config;


import javax.sip.ListeningPoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.jmdomingueza.telefonoip.sip.utils.QoSConstants;

/**
 * Clase  que implementa los metodos para obtener los Objetos generados apartir de los 
 * atributos del sip.properties
 * @author jmdomingueza
 *
 */
@Configuration(value="sipProperties")
@PropertySource(value="classpath:sip.properties")
public class SipProperties{

	/**
	 * Logger de la clase
	 */
	private static Log logger = LogFactory.getLog(SipProperties.class);
	
	@Autowired
	private Environment env;


	/**
	 * Path donde se encuentra las implementaciones de los objetos de la libreria jain-sip
	 */
	private static final String SIP_PATH_NAME_PROPERTY = "sip.path.name";

	/**
	 * Nombre que se le pone a la pila
	 */
	private static final String SIP_STACK_NAME_PROPERTY = "sip.stack.name";
		
	/**
	 * Direccion host de la pila
	 */
	private static final String SIP_STACK_HOST_ADDRESS_PROPERTY = "sip.stack.hostAdress";
	
	/**
	 * Ruta del QOS Layer
	 */
	private static final String SIP_STACK_NETWORK_LAYER_PROPERTY = "sip.stack.networkLayer";

	/**
	 * Protocolo de transporte que se va a usar
	 */
	private static final String SIP_PROVIDER_TRANSPORT_PROPERTY = "sip.provider.transport";
	/**
	 * Puerto de señalizacion de los mensajes SIP
	 */
	private static final String SIP_PROVIDER_PORT_SIP_PROPERTY = "sip.provider.port.sip";
	
	/**
	 * Puerto de audio de los mensajes RTP
	 */
	private static final String SIP_PROVIDER_PORT_RTP_PROPERTY = "sip.provider.port.rtp";

	/**
	 * QOS/TOS usado para la señalizacion SIP
	 */
	private static final String SIP_PROVIDER_TOS_SIP_PROPERTY = "sip.provider.tos.sip";
	
	/**
	 * QOS/TOS usado para el audio RTP
	 */
	private static final String SIP_PROVIDER_TOS_RTP_PROPERTY = "sip.provider.tos.rtp";

	/**
	 * Tiempo que dura un  registro en el server/pbx (seg)
	 */
	private static final String SIP_REGISTER_EXPIRES_PROPERTY = "sip.register.expires";
	
	/**
	 * Tiempo cada el cual se manda un REGISTER a la pbx para refresacar el registro (ms).
	 */
	private static final String SIP_REGISTER_TIMER_KEEPALIVE_PROPERTY = "sip.register.timer.keepalive";

	/**
	 * Valor que va en la cabecera UserAgent
	 */
	private static final String SIP_USER_AGENT_PROPERTY = "sip.user.agent";
	
	/**
	 * Numero maximo de intentos de una Request
	 */
	private static final String SIP_MAXFORWARD_PROPERTY = "sip.maxforward";

	

	/*
	 * Valores por defecto
	 */
	private static final String SIP_PATH_NAME_DEFAULT = "gov.nist";
	
	private static final String SIP_STACK_NAME_DEFAULT = "SIPTester";
	private static final String SIP_STACK_NETWORK_LAYER_DEFAULT = "com.jmdomingueza.telefonoip.sip.utils.QOSLayer";
	private static final String SIP_STACK_HOST_ADDRESS_DEFAULT="127.0.0.1";
	
	private static final String SIP_PROVIDER_TRANSPORT_DEFAULT = ListeningPoint.UDP;
	private static final Integer SIP_PROVIDER_PORT_SIP_DEFAULT = ListeningPoint.PORT_5060;
	private static final Integer SIP_PROVIDER_PORT_RTP_DEFAULT = 29000;
	private static final Integer SIP_PROVIDER_TOS_SIP_DEFAULT = QoSConstants.BEST_EFFORT;
	private static final Integer SIP_PROVIDER_TOS_RTP_DEFAULT = QoSConstants.BEST_EFFORT;
	
	private static final Integer SIP_REGISTER_EXPIRES_DEFAULT = 60;
	private static final Long SIP_REGISTER_TIMER_KEEPALIVE_DEFAULT = 25000L;
	
	private static final Integer SIP_MAXFORWARD_DEFAULT = 70;
	
	private static final String SIP_USER_AGENT_DEFAULT = "Telephone IP";
	
	/**
	 * Metodo que devuelve PathName que se genera a partir de los atributos de 
	 * sip.properties.
	 * @return
	 */
	@Bean(name="pathName")
	public  String  pathName(){
		String path; 
		
		path = env.getProperty(SIP_PATH_NAME_PROPERTY)!=null?
				env.getProperty(SIP_PATH_NAME_PROPERTY):SIP_PATH_NAME_DEFAULT;
				
		return path;
		
	}
	
	/**
	 * Metodo que devuelve el StakSip que se genera a partir de los atributos de 
	 * sip.properties.
	 * 
	 * @return
	 */
	@Bean(name="nameStack")
	public String stackSip(){
		String nameStack;
		
		nameStack = env.getProperty(SIP_STACK_NAME_PROPERTY)!=null?
				env.getProperty(SIP_STACK_NAME_PROPERTY):SIP_STACK_NAME_DEFAULT;
		
		
		return nameStack;
	}
	
	/**
	 * Metodo que devuelve el ProviderSip que se genera a partir de los atributos de 
	 * sip.properties.
	 * 
	 * @return
	 */
	@Bean(name="networkLayer")
	public String providerSip(){
		String networkLayer;
		
		networkLayer = env.getProperty(SIP_STACK_NETWORK_LAYER_PROPERTY)!=null?
				env.getProperty(SIP_STACK_NETWORK_LAYER_PROPERTY):SIP_STACK_NETWORK_LAYER_DEFAULT;	
		
		return networkLayer;
	}
	
	/**
	 * Metodo que devuelve registerExpires que se genera a partir de los atributos de 
	 * sip.properties.
	 * @return
	 */
	@Bean(name="registerExpires")
	public Integer registerExpires(){
		Integer timeExpires;
		try{		
			timeExpires= env.getProperty(SIP_REGISTER_EXPIRES_PROPERTY)!=null?
				Integer.getInteger(env.getProperty(SIP_REGISTER_EXPIRES_PROPERTY)):SIP_REGISTER_EXPIRES_DEFAULT;	
		}catch (Exception e) {
			logger.error("Excepccion obteniendo el registerExpires, ponemos el de por defecto: "+SIP_PROVIDER_PORT_SIP_DEFAULT);
			timeExpires= SIP_REGISTER_EXPIRES_DEFAULT;
		}	
		return timeExpires;
	}

	/**
	 * Metodo que devuelve registerExpires que se genera a partir de los atributos de 
	 * sip.properties.
	 * @return
	 */
	@Bean(name="registerTimer")
	public Long registerTimer(){
		Long timer;
		try{		
			timer= env.getProperty(SIP_REGISTER_TIMER_KEEPALIVE_PROPERTY)!=null?
				Integer.getInteger(env.getProperty(SIP_REGISTER_TIMER_KEEPALIVE_PROPERTY)):SIP_REGISTER_TIMER_KEEPALIVE_DEFAULT;	
		}catch (Exception e) {
			logger.error("Excepccion obteniendo el registerExpires, ponemos el de por defecto: "+SIP_REGISTER_TIMER_KEEPALIVE_DEFAULT);
			timer= SIP_REGISTER_TIMER_KEEPALIVE_DEFAULT;
		}	
		return timer;
	}
	
	/**
	 * Metodo que devuelve registerExpires que se genera a partir de los atributos de 
	 * sip.properties.
	 * @return
	 */
	@Bean(name="maxForwards")
	public Integer maxForwards(){
		Integer maxForwards;
		try{		
			maxForwards= env.getProperty(SIP_MAXFORWARD_PROPERTY)!=null?
				Integer.getInteger(env.getProperty(SIP_MAXFORWARD_PROPERTY)):SIP_MAXFORWARD_DEFAULT;	
		}catch (Exception e) {
			logger.error("Excepccion obteniendo el maxforward, ponemos el de por defecto: "+SIP_MAXFORWARD_DEFAULT);
			maxForwards= SIP_MAXFORWARD_DEFAULT;
		}	
		return maxForwards;
	}
	
	/**
	 * Metodo que devuelve Product que se genera a partir de los atributos de 
	 * sip.properties.
	 * @return
	 */
	@Bean(name="product")
	public String  product(){
		String product; 
		
		product = env.getProperty(SIP_USER_AGENT_PROPERTY)!=null?
				env.getProperty(SIP_USER_AGENT_PROPERTY):SIP_USER_AGENT_DEFAULT;
				
		return product;
		
	}
	
	/**
	 * Metodo que devuelve portRTP que se genera a partir de los atributos de 
	 * sip.properties.
	 * @return
	 */
	@Bean(name="portRTP")
	public  Integer  portRTP(){
		Integer portRTP; 
		try{	
		portRTP = env.getProperty(SIP_PROVIDER_PORT_RTP_PROPERTY)!=null?
				Integer.getInteger(env.getProperty(SIP_PROVIDER_PORT_RTP_PROPERTY)):SIP_PROVIDER_PORT_RTP_DEFAULT;
		}catch (Exception e) {
			logger.error("Excepccion obteniendo el portRTP, ponemos el de por defecto: "+SIP_PROVIDER_PORT_RTP_DEFAULT);
			portRTP= SIP_PROVIDER_PORT_RTP_DEFAULT;
		}	
		return portRTP;
		
	}
	
}
