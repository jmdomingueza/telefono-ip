package com.jmdomingueza.telefonoip.sip.utils;

/**
 * Contiene los valores predeterminados para la configuraci�n
 * del QoS con DSCP.
 * @author jgmelo
 *
 */
public class QoSConstants {
	
	//Strings con los posibles valores predefinidos
	
	/**
	 * Best Effort (clase 0)
	 */
	public final static String STR_BEST_EFFORT = "Best Effort";
	
	/**
	 * Controlled Load (clase 3)
	 */
	public final static String STR_CONTROLLED_LOAD = "Controlled Load";
	
	/**
	 * Guaranteed (clase 5)
	 */
	public final static String STR_GUARANTEED = "Guaranteed";
	
	/**
	 * Network Control (clase 6)
	 */
	public final static String STR_NETWORK_CONTROL = "Network Control";
	
	/**
	 * Qualitative (clase 0)
	 */
	public final static String STR_QUALITATIVE = "Qualitative";
	
	//Valores
	
	/**
	 * Best Effort (clase 0)
	 */
	public final static int BEST_EFFORT = 0;
	
	/**
	 * Controlled Load (clase 3)
	 */
	public final static int CONTROLLED_LOAD = 24;
	
	/**
	 * Guaranteed (clase 5)
	 */
	public final static int GUARANTEED = 40;
	
	/**
	 * Network Control (clase 6)
	 */
	public final static int NETWORK_CONTROL = 48;
	
	/**
	 * Qualitative (clase 0)
	 */
	public final static int QUALITATIVE = 0;
}
