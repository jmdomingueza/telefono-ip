package com.jmdomingueza.telefonoip.sip.managers;

import java.util.TooManyListenersException;

import javax.sip.InvalidArgumentException;
import javax.sip.ObjectInUseException;
import javax.sip.Transaction;
import javax.sip.TransportNotSupportedException;
import javax.sip.message.Request;
import javax.sip.message.Response;

import com.jmdomingueza.telefonoip.sip.services.exception.IsAlivedSipProviderException;
import com.jmdomingueza.telefonoip.sip.services.exception.NotCreatedSipProviderException;
import com.jmdomingueza.telefonoip.sip.services.exception.NotCreatedSipStackException;
import com.jmdomingueza.telefonoip.sip.services.exception.NotGetCallIdException;
import com.jmdomingueza.telefonoip.sip.services.exception.ParameterNotValidedException;

public interface SipManager {

	/**
	 * Metodo que destruye todo. Deja el Servicio como recien creado.
	 * 
	 * @throws ObjectInUseException
	 *             Excepcion que salta cuando el Objeto ya esta en uso
	 * @throws Exception
	 *             Excepcion que salta cuando nos da una excepcion no controlada
	 */
	public void destroy() throws Exception;

	/**
	 * Metodo que se encarga de crear el SipProvider. Para crear un SpProvider
	 * hay que crear tambien un ListenigPoint, por tanto tambien lo creamos.
	 * 
	 * @param host
	 *            Host donde se ha creado el SipStack y donde se va a crear el
	 *            SipProvider
	 * @param port
	 *            Puerto del ListeningPoint
	 * @param transport
	 *            Protocolo de transporte del ListeningPoint
	 * @throws ParameterNotValidedException
	 *             Se devuelve esta excepcion cuando los parametros pasados no
	 *             son validos
	 * @throws NotCreatedSipStackException
	 *             Se devuelve esta excepcion cuando no esta creada la SipStack
	 * @throws IsAlivedSipProviderException
	 *             Se devuelve esta excepcion cuando ya existe un SipProvider en
	 *             el puerto que pasamos
	 * @throws TransportNotSupportedException
	 *             Se devuelve esta excepcion cuando el transporte que pasamos
	 *             no esta soportado
	 * @throws InvalidArgumentException
	 *             Se devuelve esta excepcion cuando algo es invalido
	 * @throws ObjectInUseException
	 *             Se devuelve esta excepcion cuando el Objeto ya esta en uso
	 * @throws TooManyListenersException
	 *             Se devuelve esta excepcion cuando cuando se intenta asignar
	 *             al SipProvider mas de un SipListener
	 * @throws Exception
	 *             Se devuelve esta excepcion cuando se da una excepcion que no
	 *             esta controlada
	 */
	public void createSipProvider(String host, Integer port, String transport) throws Exception;

	/**
	 * Metodo que se encarga de destruir el SipProvider. Tambien desasigna los
	 * ListingPoints que tenia asignados a él.
	 * 
	 * @param host
	 *            Host donde se ha creado el SipProvider
	 * @param port
	 *            Puerto donde se ha creado el SipProvider
	 * @param transport
	 *            Protocolo de transporte donde se ha creado el SipProvider
	 * @throws ParameterNotValidedException
	 *             Se devuelve esta excepcion cuando los parametros pasados no
	 *             son validos
	 * @throws ObjectInUseException
	 *             Se devuelve esta excepcion cuando el objeto esta en uso
	 * @throws Exception
	 *             Se devuelve esta excepcion cuando se da una excepcion que no
	 *             esta controlada
	 */
	public void destroySipProvider(String host, Integer port, String transport) throws Exception;

	/**
	 * Metodo que crea un ListeningPoint a partir de un SipStack. Si existe el
	 * SipProvider se lo asigna.
	 * 
	 * @param host
	 *            Host donde se ha creado el SipStack y donde se va a crear el
	 *            ListeningPoint
	 * @param port
	 *            Puerto del ListeningPoint
	 * @param transport
	 *            Protocolo de transporte del ListeningPoint
	 * @throws ParameterNotValidedException
	 *             Se devuelve esta excepcion cuando los parametros pasados no
	 *             son validos
	 * @throws NotCreatedSipStackException
	 *             Se devuelve esta excepcion cuando no esta creada la SipStack
	 * @throws NotCreatedSipProviderException
	 *             Se devuelve esta excepcion cuando no esta creado el
	 *             SipProvider
	 * @throws TransportNotSupportedException
	 *             Se devuelve esta excepcion cuando el transporte que pasamos
	 *             no esta soportado
	 * @throws InvalidArgumentException
	 *             Se devuelve esta excepcion cuando algo es invalido
	 * @throws ObjectInUseException
	 *             Se devuelve esta excepcion cuando el objeto esta en uso
	 * @throws Exception
	 *             Se devuelve esta excepcion cuando se da una excepcion que no
	 *             esta controlada
	 */
	public void createListeningPoint(String host, Integer port, String transport) throws Exception;

	/**
	 * Metodo que se encarga de destruir el ListeninPoint
	 * 
	 * @param host
	 *            Host donde se ha creado el ListeningPoint
	 * @param port
	 *            Puerto del ListeningPoint
	 * @param transport
	 *            Protocolo de transporte del ListeningPoint
	 * @throws ParameterNotValidedException
	 *             Se devuelve esta excepcion cuando los parametros pasados no
	 *             son validos
	 * @throws ObjectInUseException
	 *             Se devuelve esta excepcion cuando el objeto esta en uso
	 * @throws Exception
	 *             Se devuelve esta excepcion cuando se da una excepcion que no
	 *             esta controlada
	 */
	public void destroyListeningPoint(String host, Integer port, String transport) throws Exception;

	public Transaction sendRequest( int port, String transport, Request request) throws Exception;

	public String getNewCallId(int port, String tranport) throws NotGetCallIdException;

	public Long getNewCSeq(String transport);
	
	public Transaction sendResponse(int port, String transport, Request request, Response response)
			throws Exception;

	public String getHost() throws Exception;

	

}
