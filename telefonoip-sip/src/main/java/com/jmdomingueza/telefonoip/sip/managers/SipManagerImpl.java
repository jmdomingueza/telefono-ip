package com.jmdomingueza.telefonoip.sip.managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogTerminatedEvent;
import javax.sip.IOExceptionEvent;
import javax.sip.ListeningPoint;
import javax.sip.PeerUnavailableException;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.ServerTransaction;
import javax.sip.SipFactory;
import javax.sip.SipListener;
import javax.sip.SipProvider;
import javax.sip.SipStack;
import javax.sip.TimeoutEvent;
import javax.sip.Transaction;
import javax.sip.TransactionTerminatedEvent;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.sip.events.MyDialogTerminatedEvent;
import com.jmdomingueza.telefonoip.sip.events.MyRequestEvent;
import com.jmdomingueza.telefonoip.sip.events.MyResponseEvent;
import com.jmdomingueza.telefonoip.sip.events.MyTimeoutEvent;
import com.jmdomingueza.telefonoip.sip.events.MyTransactionTerminatedEvent;
import com.jmdomingueza.telefonoip.sip.services.exception.IsAlivedSipStackException;
import com.jmdomingueza.telefonoip.sip.services.SipMessageService;
import com.jmdomingueza.telefonoip.sip.services.exception.IsAlivedSipProviderException;
import com.jmdomingueza.telefonoip.sip.services.exception.NotCreatedSipProviderException;
import com.jmdomingueza.telefonoip.sip.services.exception.NotCreatedSipStackException;
import com.jmdomingueza.telefonoip.sip.services.exception.NotGetCallIdException;
import com.jmdomingueza.telefonoip.sip.services.exception.ParameterNotValidedException;

public class SipManagerImpl implements SipManager, SipListener {

	/**
	 * Logger de la clase
	 */
	private static Log logger = LogFactory.getLog(SipManagerImpl.class);
		
	private Map<String, Long> cSeqMap;

	/**
	 * sipStack que se crea en el SipManager
	 */
	private SipStack objSipStack;
	
	private SipMessageService sipServiceMessage;

	/**
	 * Path donde se encuentra las implementaciones de los objetos de la
	 * libreria jain-sip
	 */
	private String pathName;

	/**
	 * 
	 */
	private String nameStack;

	/**
	 * 
	 */
	private String networkLayer;
	
	private SipManagerImpl() throws Exception {
		// Otenemos los Bean del ctx
		sipServiceMessage = CTX.ctx.getBean(SipMessageService.class);
		pathName = (String) CTX.ctx.getBean("pathName");
		nameStack = (String) CTX.ctx.getBean("nameStack");
		networkLayer = (String) CTX.ctx.getBean("networkLayer");
		
		// Incicializamos los mapas
		cSeqMap = new HashMap<>();

	}

	public SipManagerImpl(String host) throws Exception {
		this();

		this.createSipStack(host);

	}

	public SipManagerImpl(String host, Integer port, String transport) throws Exception {
		this(host);

		this.createSipProvider(host, port, transport);
		this.createListeningPoint(host, port, transport);

	}

	@Override
	public void destroy() throws Exception {

		logger.trace("Enter  destroy");
		try {
			logger.debug("Destruimos el SipStack: "+ objSipStack);
			destroySipStack();

			logger.debug("Vaciamos el mapa de CSeq");
			cSeqMap.clear();

		} catch (Exception e) {
			logger.trace("Exit destroy - Exception");
			throw e;
		}
		logger.info("Se ha destruido el SipService correctamente.");

		logger.trace("Exit  destroy");
	}
	
	@Override
	public void createSipProvider(String host, Integer port, String transport) throws Exception {
		SipProvider objSipProvider = null;
		ListeningPoint objListeningPoint = null;

		logger.trace("Enter  createSipProvider");

		try {
			// Comprobamos que los parametros pasados son validos
			validParameters(host, port, transport);

			if (this.getSipProvider(port, transport) != null) {
				logger.error("El SipProvide host: " + host + " transport: " + transport + " port: " + port
						+ " ya esta creado, no puede crearse otro igual");
				throw new IsAlivedSipProviderException("El SipProvider con host: " + host + " transport: " + transport
						+ " port: " + port + " ya esta creado, no puede crearse otro igual");
			}

			// Obtenemos el Sipstack, si no existe devolvemos una excepcion
			if (objSipStack == null) {
				logger.error("La sipStack host: " + host + " no esta creada, hay que "
						+ "crearla antes de crear el ListeningPoint y el SipProvider");
				throw new NotCreatedSipStackException("sipStack host: " + host + " no creada");
			}

			logger.debug("SipStack: " + objSipStack + " con el host: " + host);

			// Se crea el ListeningPoint ya que un SipProvider por lo menos
			// tiene que tener creado un ListeningPoint
			objListeningPoint = objSipStack.createListeningPoint(objSipStack.getIPAddress(), port, transport);
			logger.debug("objSipStack.createListeningPoint" + " IPAddress:  " + objListeningPoint.getIPAddress()
					+ " Port: " + objListeningPoint.getPort() + " Transport: " + objListeningPoint.getTransport());

			// Se crea el SipProvider del ListeningPoint
			objSipProvider = objSipStack.createSipProvider(objListeningPoint);
			logger.debug(" objSipStack.createSipProvider " + objSipProvider);
			// Se dice que los eventos Sip los va recibir la clase "this"
			objSipProvider.addSipListener(this);
			logger.debug(" objSipProvider añadido al SipListener " + this);
		} catch (Exception e) {
			logger.trace("Exit createSipProvider - Exception");
			throw e;
		}
		logger.info("Se ha creado un SipProvider  y se le ha asignado un ListeningPoint correctamente. SipStack:"
				+ objSipStack + " ListeningPoint:" + objListeningPoint + " SipProvider:" + objSipProvider);
		logger.trace("Exit createSipProvider");

	}

	@Override
	public void destroySipProvider(String host, Integer port, String transport)
			throws Exception {
		SipProvider objSipProvider = null;

		logger.trace("Enter destroySipProvider");

		try {
			// Comprobamos que los parametros pasados son validos
			validParameters(host, port, transport);

			if (objSipStack == null) {
				logger.error("objSipStack is null por tanto el provider esta destruido");
				logger.trace("Exit  destroySipProvider - objSipStack == null");
				return;
			}

			objSipProvider = this.getSipProvider(port, transport);
			if (objSipProvider == null) {
				logger.error("objSipProvider is null por tanto el provider esta destruido");
				logger.trace("Exit  destroySipProvider - objSipProvider == null");
				return;
			}

			objSipProvider.removeSipListener(this);
			logger.debug("objSipProvider.removeSipListener " + this);

			ListeningPoint[] arrayListeningPoints = objSipProvider.getListeningPoints();
			for (int i = 0; i < arrayListeningPoints.length; i++) {
				if (arrayListeningPoints[i].getTransport().equalsIgnoreCase(transport)
						&& arrayListeningPoints[i].getPort() == port) {
					objSipProvider.removeListeningPoint(arrayListeningPoints[i]);
					logger.debug("sipProvider.removeListeningPoint " + " IPAddress:  "
							+ arrayListeningPoints[i].getIPAddress() + " Port: " + arrayListeningPoints[i].getPort()
							+ " Transport: " + arrayListeningPoints[i].getTransport());
				}
			}
			objSipStack.deleteSipProvider(objSipProvider);
			logger.debug("objSipStack.deleteSipProvider " + objSipProvider);
		}catch (Exception e) {
			logger.trace("Exit destroySipProvider - Exception");
			throw e;
		}
		logger.info("Se ha destruido un SipProvider y se le ha desasignado sus ListeningPoint correctamente. SipStack:"
				+ objSipStack + " SipProvider:" + objSipProvider);
		logger.trace("Exit destroySipProvider");

	}

	@Override
	public void createListeningPoint(String host, Integer port, String transport)
			throws Exception {
		ListeningPoint objListeningPoint;
		SipProvider objSipProvider;

		logger.trace("Enter  createListeningPoint");

		try {
			// Comprobamos que los parametros pasados son validos
			validParameters(host, port, transport);
			
			// Obtenemos el Sipstack, si no existe devolvemos una excepcion
			if (this.objSipStack == null) {
				logger.error("La SipStack host: " + host + " no esta creada, hay que "
						+ "crearla antes de crear el ListeningPoint");
				throw new NotCreatedSipStackException("SipStack host: " + host + " no creada");
			}

			logger.debug("SipStack: " + objSipStack + " con el host: " + host);

			// Obtenemos el SipProvider, si no existe devolvemos una excepcion
			if (this.getSipProvider(port, transport) == null) {
				logger.error("El SipProvider host: " + host + " transport: " + transport + " port: " + port
						+ " no esta creado, hay que " + "crearlo antes de crear el ListeningPoint");
				throw new NotCreatedSipProviderException(
						"SipProvider host: " + host + " transport: " + transport + " port: " + port + " no creado");
			}

			objSipProvider = this.getSipProvider(port, transport);
			logger.debug("Obtenido un SipProvider: " + objSipProvider + " con el host: " + host + " transport: "
					+ transport + " port: " + port);

			objListeningPoint = objSipStack.createListeningPoint(objSipStack.getIPAddress(), port, transport);
			logger.debug("objSipStack.createListeningPoint" + " IPAddress:  " + objListeningPoint.getIPAddress()
					+ " Port: " + objListeningPoint.getPort() + " Transport: " + objListeningPoint.getTransport());
			objSipProvider.addListeningPoint(objListeningPoint);

		} catch (Exception e) {
			logger.trace("Exit  createListeningPoint - Exception");
			throw e;
		}
		logger.info("Se ha creado un ListenerPoint correctamente. SipStack:" + objSipStack + " ListeningPoint:"
				+ objListeningPoint + " SipProvider:" + objSipProvider);
		logger.trace("Exit createListeningPoint");
	}

	@Override
	public void destroyListeningPoint(String host, Integer port, String transport)
			throws Exception {
		ListeningPoint objListeningPoint = null;

		logger.trace("Enter  destroyListeningPoint");

		try {
			// Comprobamos que los parametros pasados son validos
			validParameters(host, port, transport);

			if (objSipStack == null) {
				logger.error("objSipStack is null por tanto el provider esta destruido");
				logger.trace("Exit  destroyListeningPoint - objSipStack == null");
				return;
			}

			Iterator<?> itListeningPoints = objSipStack.getListeningPoints();
			for (; itListeningPoints.hasNext();) {
				objListeningPoint = (ListeningPoint) itListeningPoints.next();
				if (objListeningPoint.getTransport().equalsIgnoreCase(transport)
						&& objListeningPoint.getPort() == port) {
					itListeningPoints.remove();
					logger.debug("itListeningPoints.remove " + objListeningPoint);
					objSipStack.deleteListeningPoint(objListeningPoint);
					logger.debug("objSipStack.deleteListeningPoint " + " IPAddress:  "
							+ objListeningPoint.getIPAddress() + " Port: " + objListeningPoint.getPort()
							+ " Transport: " + objListeningPoint.getTransport());

				}
			}
		} catch (Exception e) {
			logger.trace("Exit destroyListeningPoint - Exception");
			throw e;
		}
		logger.info("Se ha destruido un Listening correctamente. SipStack:" + objSipStack + " ListeningPoint:"
				+ objListeningPoint);
		logger.trace("Exit destroyListeningPoint");

	}

	@Override
	public Transaction sendRequest(int port, String transport, Request request) throws Exception {
		SipProvider sipProvider;
		String host;
		ClientTransaction transaction;
		logger.trace("Enter sendRequest");

		try {
			host = getHost();
			// Obtenemos el SipProvider, si no existe devolvemos una excepcion
			if (this.getSipProvider(port, transport) == null) {
				logger.error("El SipProvider host: " + host + " transport: " + transport + " port: " + port
						+ " no esta creado, hay que " + "crearlo antes de querer enviar un mensaje");
				throw new NotCreatedSipProviderException(
						"SipProvider host: " + host + " transport: " + transport + " port: " + port + " no creado");
			}
			sipProvider = getSipProvider(port, transport);

			transaction = sipProvider.getNewClientTransaction(request);
			logger.debug("Creamos una nueva transaccion: " + transaction);
			transaction.sendRequest();
			logger.info("Send Request : " + request.getMethod() + " " + CallIdHeader.NAME + "- "
					+ ((CallIdHeader) request.getHeader(CallIdHeader.NAME)).getCallId() + " " + CSeqHeader.NAME + "- "
					+ ((CSeqHeader) request.getHeader(CSeqHeader.NAME)).getSeqNumber());
			logger.debug("\r\n" + request);
		} catch (Exception e) {
			logger.trace("Exit  sendRequest - Exception");
			throw e;
		}
		logger.trace("Exit sendRequest");
		return transaction;
	}

	
	@Override
	public Transaction sendResponse( int port, String transport, Request request, Response response)
			throws Exception {
		SipProvider sipProvider;
		String host;
		ServerTransaction transaction;

		logger.trace("Enter sendRequest");

		try {

			host = getHost();
			// Obtenemos el SipProvider, si no existe devolvemos una excepcion
			if (this.getSipProvider(port, transport) == null) {
				logger.error("El SipProvider host: " + host + " transport: " + transport + " port: " + port
						+ " no esta creado, hay que " + "crearlo antes de querer enviar un mensaje");
				throw new NotCreatedSipProviderException(
						"SipProvider host: " + host + " transport: " + transport + " port: " + port + " no creado");
			}
			sipProvider = getSipProvider(port, transport);

			transaction = sipProvider.getNewServerTransaction(request);
			logger.debug("Create new transaction: " + transaction);
			transaction.sendResponse(response);
			logger.info("Send Response : " + response.getReasonPhrase() + " " + CallIdHeader.NAME + "- "
					+ ((CallIdHeader) response.getHeader(CallIdHeader.NAME)).getCallId() + " " + CSeqHeader.NAME + "- "
					+ ((CSeqHeader) response.getHeader(CSeqHeader.NAME)).getSeqNumber());
			logger.debug("\r\n" + response);
		} catch (Exception e) {
			logger.trace("Exit  sendRequest - Exception");
			throw e;
		}

		logger.trace("Exit sendRequest");
		return transaction;
	}

	@Override
	public String getHost() throws Exception{
		if(objSipStack ==null){
			throw new NotCreatedSipStackException("SipStack == null");
		}
		return objSipStack.getIPAddress();
			
	}

		
	@Override
	public String getNewCallId(int port, String tranport) throws NotGetCallIdException {
		SipProvider sipProvider;

		sipProvider = this.getSipProvider(port, tranport);

		if (sipProvider == null) {
			logger.trace("Exit  getNewCallId - SipProvider == null");
			throw new NotGetCallIdException("SipProvider == null");
		}

		String callId = sipProvider.getNewCallId().getCallId();
		return callId;
	}
	
	@Override
	public Long getNewCSeq(String transport) {
		if (cSeqMap != null) {
			if (cSeqMap.containsKey(transport)) {
				Long cSeq = cSeqMap.get(transport);
				cSeq++;
				return cSeq;
			} else {
				Long cSeq = new Long(1);
				cSeqMap.put(transport, new Long(1));
				return cSeq;
			}
		}
		return null;

	}

	@Override
	public void processDialogTerminated(DialogTerminatedEvent evt) {
		Dialog dialog;

		logger.trace("Enter  processDialogTerminated");
		

		if (evt == null || evt.getDialog() == null) {
			logger.error("DialogTerminatedEvent no valido");
			logger.trace("Exit  processDialogTerminated - DialogTerminatedEvent no valido");
			return;
		}
		
		dialog = evt.getDialog();
		
		
		logger.info("Process DialogTerminated : " + CallIdHeader.NAME + "- "
				+  dialog.getCallId()
				+ " DialogId- "
				+  dialog.getDialogId());
		logger.debug("\r\n" + dialog);

		sipServiceMessage.sendDialogTerminated(new MyDialogTerminatedEvent(evt,this));
		
		logger.trace("Exit  processDialogTerminated");

	}

	@Override
	public void processIOException(IOExceptionEvent arg0) {
		logger.trace("Enter  processIOException");
		logger.info("Process IOEception : Host- " + arg0.getHost() + " " + "Port-" + arg0.getPort() + " "
				+ "Transport- " + arg0.getTransport() + " " + "Source - " + arg0.getSource());
		try {
			// this.destroyListeningPoint(arg0.getHost(), arg0.getPort(),
			// arg0.getTransport());
			// TODO: MessageService.sendMessage(new SipMessage(this,
			// SipMessage.PROVIDER_DESTROYED_TYPE));
		} catch (Exception e) {
			logger.error("Error destruyendo todo en processIOException()", e);
		}
		logger.trace("Exit  processIOException");

	}

	@Override
	public void processRequest(RequestEvent evt) {
		String reqMethod;
		Request req;

		logger.trace("Enter  processRequest");
		if (evt == null || evt.getServerTransaction() == null || evt.getRequest() == null
				|| evt.getRequest().getMethod() == null) {
			logger.error("ResquestEvent is invalid");
			logger.trace("Exit processRequest - ResquestEvent is invalid");
			return;
		}
		req = evt.getRequest();
		reqMethod = req.getMethod();

		logger.info("Process Request : " + reqMethod + " " + CallIdHeader.NAME + "- "
				+ ((CallIdHeader) req.getHeader(CallIdHeader.NAME)).getCallId() + " " + CSeqHeader.NAME + "- "
				+ ((CSeqHeader) req.getHeader(CSeqHeader.NAME)).getSeqNumber());
		logger.debug("\r\n" + req);

		sipServiceMessage.sendRequest(new MyRequestEvent(evt,this));

		logger.trace("Exit processRequest");
	}

	@Override
	public void processResponse(ResponseEvent evt) {
		Response resp;
		logger.trace("Enter  processResponse");
		if (evt == null || evt.getClientTransaction() == null || evt.getResponse() == null
				|| evt.getClientTransaction().getRequest() == null
				|| evt.getClientTransaction().getRequest().getMethod() == null) {
			logger.error("ResponseEvent is invalid");
			logger.trace("Exit  processResponse - ResponseEvent is invalid");
			return;
		}

		resp = evt.getResponse();
		logger.info("Process Response : " + resp.getReasonPhrase() + " " + CallIdHeader.NAME + "- "
				+ ((CallIdHeader) resp.getHeader(CallIdHeader.NAME)).getCallId() + " " + CSeqHeader.NAME + "- "
				+ ((CSeqHeader) resp.getHeader(CSeqHeader.NAME)).getSeqNumber());
		logger.debug("\r\n" + resp);

		sipServiceMessage.sendResponse(new MyResponseEvent(evt, this));

		logger.trace("Exit  processResponse");
	}

	@Override
	public void processTimeout(TimeoutEvent evt) {
		String reqMethod;

		logger.trace("Enter  processTimeout");
		if (evt == null || evt.getClientTransaction() == null || evt.getClientTransaction().getRequest() == null) {
			logger.error("TimeoutEvent no valido");
			logger.trace("Exit  processTimeout - TimeoutEvent no valido");
			return;
		}

		reqMethod = evt.getClientTransaction().getRequest().getMethod();

		logger.info("Process Timeout : " + reqMethod + " " + CallIdHeader.NAME + "- "
				+ ((CallIdHeader) evt.getClientTransaction().getRequest().getHeader(CallIdHeader.NAME)).getCallId()
				+ " " + CSeqHeader.NAME + "- "
				+ ((CSeqHeader) evt.getClientTransaction().getRequest().getHeader(CSeqHeader.NAME)).getSeqNumber());
		logger.debug("\r\n" + evt.getClientTransaction().getRequest());

		sipServiceMessage.sendTimeout(new MyTimeoutEvent(evt,this));

		logger.trace("Exit  processTimeout");

	}

	@Override
	public void processTransactionTerminated(TransactionTerminatedEvent evt) {
		String reqMethod;
		logger.trace("Enter  processTransactionTerminated");
		if (evt == null || (evt.getClientTransaction() == null && !evt.isServerTransaction())
				|| evt.getClientTransaction().getRequest() == null && evt.isServerTransaction()) {
			logger.error("TransactionTerminatedEvent no valido");
			logger.trace("Exit  processTransactionTerminated - TransactionTerminatedEvent no valido");
			return;
		}

		reqMethod = evt.getClientTransaction().getRequest().getMethod();

		logger.info("Process TransactionTerminated : " + reqMethod + " " + CallIdHeader.NAME + "- "
				+ ((CallIdHeader) evt.getClientTransaction().getRequest().getHeader(CallIdHeader.NAME)).getCallId()
				+ " " + CSeqHeader.NAME + "- "
				+ ((CSeqHeader) evt.getClientTransaction().getRequest().getHeader(CSeqHeader.NAME)).getSeqNumber());
		logger.debug("\r\n" + evt.getClientTransaction().getRequest());

		sipServiceMessage.sendTransactionTerminated(new MyTransactionTerminatedEvent(evt,this));
		

		logger.trace("Exit  processTransactionTerminated");

	}
	
	/**
	 * Metodo que se encarga de crear el SipStack.
	 * 
	 * @param host
	 *            Host donde se crea el SipStack
	 * @throws IsAlivedSipStackException
	 *             Se devuelve esta excepcion cuando ya existe el SipStack
	 * @throws ParameterNotValidedException
	 *             Se devuelve esta excepcion cuando los parametros pasados no
	 *             son validos
	 * @throws PeerUnavailableException
	 *             Se devuelve esta excepcion cuando el SipFactory no puede
	 *             crear el SipStack
	 * @throws SipUnknowException
	 *             Se devuelve esta excepcion cuando se da una excepcion que no
	 *             esta controlada
	 */
	private void createSipStack(String host) throws Exception {
		SipFactory objSipFactory;
		Properties objProperties;

		logger.trace("Enter createSipStack");
		objSipFactory = SipFactory.getInstance();
		try {

			// Comprobamos que los parametros pasados son validos
			if (host == null || host.isEmpty()) {
				logger.error("host == null o vacio, no puede serlo");
				logger.trace("Exit  createSipStack - host == null o vacio");
				throw new ParameterNotValidedException("El host no puede ser ni null ni vacio");
			}
			if (nameStack == null || nameStack.isEmpty()) {
				logger.error("nameStack == null o vacio, no puede serlo");
				logger.trace("Exit  createSipStack - nameStack == null o vacio");
				throw new ParameterNotValidedException("El nameStack no puede ser ni null ni vacio");
			}
			if (networkLayer == null || networkLayer.isEmpty()) {
				logger.error("networkLayer == null o vacio, no puede serlo");
				logger.trace("Exit  createSipStack - networkLayer == null o vacio");
				throw new ParameterNotValidedException("El networkLayer no puede ser ni null ni vacio");
			}

			// Si la Stak ya esta creada devolvemos una Excepcion porque ya esta
			// creada
			if (objSipStack != null) {
				logger.trace("Exit createSipStack - getSipStack(stackSip.getHostAddress())!=null");
				throw new IsAlivedSipStackException(
						"El SipStack con host: " + host + " ya esta creado, no puede crearse otro igual");
			}

			objSipFactory.setPathName(pathName);

			objProperties = new Properties();
			objProperties.setProperty("javax.sip.STACK_NAME", nameStack);
			objProperties.setProperty("javax.sip.IP_ADDRESS", host);
			objProperties.setProperty("gov.nist.javax.sip.NETWORK_LAYER", networkLayer);

			objSipStack = objSipFactory.createSipStack(objProperties);
			logger.debug("Creamos el SipStack " + objSipStack);
			logger.debug("Ponemos el SipStack del mapa " + objSipStack);
			objSipStack.start();
			logger.debug("Iniciamos el SipStack " + objSipStack);
			logger.info("Se ha creado un SipStack correctamente. SipStack:" + objSipStack);
			objSipFactory.resetFactory();
		} catch (Exception e) {
			if (objSipFactory != null)
				objSipFactory.resetFactory();
			logger.trace("Exit createSipStack - Exception");
			throw e;
		}
		logger.trace("Exit createSipStack");
	}

	/**
	 * Metodo que se encarga de destruir el SipStack.
	 * 
	 * @param host
	 *            Host donde se crea el SipStack
	 * @throws ParameterNotValidedException
	 *             Se devuelve esta excepcion cuando los parametros pasados no
	 *             son validos
	 * @throws SipUnknowException
	 *             Se devuelve esta excepcion cuando se da una excepcion que no
	 *             esta controlada
	 */
	private void destroySipStack() throws Exception {
		logger.trace("Enter  destroySipStack");

		try {
			if (objSipStack != null) {
				logger.debug("Borramos  todos los objSipStack.deleteListeningPoint");
				Iterator<?> itListeningPoints = objSipStack.getListeningPoints();
				for (; itListeningPoints.hasNext();) {
					ListeningPoint objListeningPoint = (ListeningPoint) itListeningPoints.next();
					itListeningPoints.remove();
					logger.debug("itListeningPoints.remove " + objListeningPoint);
					objSipStack.deleteListeningPoint(objListeningPoint);
					logger.debug("objSipStack.deleteListeningPoint " + " IPAddress:  "
							+ objListeningPoint.getIPAddress() + " Port: " + objListeningPoint.getPort()
							+ " Transport(): " + objListeningPoint.getTransport());
				}

				logger.debug("Borramos  todos los sipProvider.removeListeningPoint");
				Iterator<?> itSipProvider = objSipStack.getSipProviders();
				for (; itSipProvider.hasNext();) {
					SipProvider sipProvider = (SipProvider) itSipProvider.next();
					itSipProvider.remove();
					logger.debug("itSipProvider.remove " + sipProvider);
					sipProvider.removeSipListener(this);
					logger.debug("sipProvider.removeSipListener " + this);
					ListeningPoint[] arrayListeningPoints = sipProvider.getListeningPoints();
					for (int i = 0; i < arrayListeningPoints.length; i++) {
						sipProvider.removeListeningPoint(arrayListeningPoints[i]);
						logger.debug("sipProvider.removeListeningPoint " + " IPAddress:  "
								+ arrayListeningPoints[i].getIPAddress() + " Port: " + arrayListeningPoints[i].getPort()
								+ " Transport: " + arrayListeningPoints[i].getTransport());

					}
					objSipStack.deleteSipProvider(sipProvider);
					logger.debug("objSipStack.deleteSipProvider " + sipProvider);

				}
				objSipStack.stop();
				logger.debug("Paramos la stack " + objSipStack);
				objSipStack = null;
			}
		} catch (Exception e) {
			logger.trace("Exit destroySipStack - Exception");
			throw e;
		}

		logger.trace("Exit  destroySipStack");
	}

	

	/**
	 * Metodo que devuelve el SipProvider que corresponde con los parametros que
	 * pasamos. Si no existe el SipProvider devuelve null.
	 * 
	 * @param host
	 * @param port
	 * @param transport
	 * @return
	 */
	private SipProvider getSipProvider(int port, String transport) {
		if (objSipStack != null) {
			Iterator<?> itSipProvider = objSipStack.getSipProviders();
			for (; itSipProvider.hasNext();) {
				SipProvider objSipProvider = (SipProvider) itSipProvider.next();
				ListeningPoint[] arrayListeningPoints = objSipProvider.getListeningPoints();
				if (arrayListeningPoints[0].getPort() == port) {
					return objSipProvider;
				}

			}
		}
		return null;
	}

	private void validParameters(String host, Integer port, String transport) throws ParameterNotValidedException {
		
		logger.trace("Enter validParameters");
		
		// Comprobamos que los parametros pasados son validos
		if (host == null || host.isEmpty()) {
			logger.error("host == null o vacio, no puede serlo");
			logger.trace("Exit  validParameters - host == null o vacio");
			throw new ParameterNotValidedException("El host no puede ser ni null ni vacio");
		}
		if (transport == null || transport.isEmpty()) {
			logger.error("transport == null o vacio, no puede serlo");
			logger.trace("Exit  validParameters - transport == null o vacio");
			throw new ParameterNotValidedException("El transport no puede ser ni null ni vacio");
		}
		if (port == null || port <= 0) {
			logger.error("port <= 0, no puede serlo");
			logger.trace("Exit  validParameters - " + "port <= 0");
			throw new ParameterNotValidedException("El port no puede ser menor o igual a 0");
		}
		logger.trace("Exit validParameters");
	}
}
