package com.jmdomingueza.telefonoip.sip.services;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sdp.SessionDescription;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogTerminatedEvent;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.TimeoutEvent;
import javax.sip.Transaction;
import javax.sip.TransactionTerminatedEvent;
import javax.sip.header.CSeqHeader;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.common.messages.MessageService;
import com.jmdomingueza.telefonoip.common.messages.sip.SipMessage;
import com.jmdomingueza.telefonoip.rtp.beans.SessionRtp;
import com.jmdomingueza.telefonoip.rtp.factories.BeanRtpFactory;
import com.jmdomingueza.telefonoip.rtp.services.RTPService;
import com.jmdomingueza.telefonoip.sip.beans.Call;
import com.jmdomingueza.telefonoip.sip.beans.Count;
import com.jmdomingueza.telefonoip.sip.managers.SipManager;
import com.jmdomingueza.telefonoip.sip.managers.SipManagerFactory;
import com.jmdomingueza.telefonoip.sip.sdp.SessionDescriptionFactory;
import com.jmdomingueza.telefonoip.sip.services.exception.PortRTPNotAvalibleException;
import com.jmdomingueza.telefonoip.sip.sip.RequestFactory;

@Service(value = "sipService")
public class SipServiceImpl implements SipMessageListener,SipService{

	/**
	 * Logger de la clase
	 */
	private static Log logger = LogFactory.getLog(SipServiceImpl.class);
	
	private Request ackRequest;
	
	@Autowired 
	protected RequestFactory requestFactory;
	
	@Autowired 
	private SipMessageService sipMessageService;
	
	@Autowired 
	private Integer portRTP;
	
	/**
	 * Mapa con los Thread de los usuarios que se estan gestionando
	 */
	private Map<Count, RegisterThread> registerThreadMap;

	/**
	 * Mapa con los usuarios que se estan en una transaccion
	 */
	private Map<Transaction, Count> countTransactionMap;
	
	@Autowired
	private Integer registerExpires;

	@Autowired
	private Integer maxForwards;
	
	@Autowired
	private Long registerTimer;

	@Autowired
	private String product;
	
	@Autowired
	private RTPService rtpService;

	/**
	 * Mapa con las llamadas Sipque se estan en una transaccion
	 */
	private Map<Transaction, Call> callTransactionMap;
	
	/**
	 * Mapa con las llamadas Sipque se estan en un Dialogo
	 */
	private Map<Dialog, Call> callDialogMap;
	
	private Collection<SipManager> sipManagerCollection;
	
	
	
	public SipServiceImpl() {
		super();
		
		logger.trace("Enter  SipServiceImpl");
		
		logger.info("Creamos el objeto del servicio SipService");
		
		registerThreadMap = new HashMap<>();
		countTransactionMap = new HashMap<>();
		
		callTransactionMap = new HashMap<>();
		callDialogMap = new HashMap<>();
		
		sipManagerCollection = new ArrayList<>();
		
		logger.trace("Exit  SipServiceImpl");
	}

	@PostConstruct
	public void init(){
		
		logger.trace("Enter  init");
		
		sipMessageService.addSipMessageListener(this);
		logger.debug("Añadimos este SipMessageListener al SipMessageService para que "
				+ "reciba eventos de RequestEvent, ResponseEvent, TimeoutEvent");
		
		logger.trace("Exit  init");
	}
	
	@Override
	public void destroy() {
		Set<Count> keys;

		logger.trace("Enter  destroy");

		try {
			sipMessageService.removeSipMessageListener(this);
			logger.debug("Eliminamos este SipMessageListener del SipMessageService para que "
					+ "ya no reciba eventos de RequestEvent, ResponseEvent, TimeoutEvent");
			
			
			
			keys = registerThreadMap.keySet();
			for (Count key : keys) {
				RegisterThread thread = registerThreadMap.get(key);
				if(!thread.isInterrupted())
					thread.interrupt();
			}
			registerThreadMap.clear();
		} catch (Exception e) {
			logger.error("Excepcion destruyendo el RegisterService");
			registerThreadMap = new HashMap<>();
		}

		logger.trace("Exit  destroy");
	}
	
	@Override
	public void register(Count count) throws Exception{
		
		logger.trace("Enter  register");
		try {
			//Si la cuenta la tiene un hilo de registro creado devolvemos
			//una Excepcion
			if(registerThreadMap.containsKey(count)){
				logger.debug("El mapa no contiene "+count+" se devuelve una excepcion");
				throw new Exception("Ya esta la cuenta registrada");
			}
			
			//Creamos RegisterThread de Count
			logger.debug("Creamos el hilo del registro de "+count);
			RegisterThread thread = new RegisterThread(count);
			
			//Guardamos RegisterThread y Count en registerThreadMap
			logger.debug("Guardamos "+thread+" y "+count+" en el mapa");
			registerThreadMap.put(count, thread);
			logger.debug("Creamos "+thread+", lo guardamos en el mapa y lo iniciamos");
			
			//Iniciamos RegisterThread
			logger.debug("Iniciamos "+thread);
			thread.start();
		} catch (Exception e) {
			logger.trace("Exit  register - Exception");
			throw e;
		}

		logger.trace("Exit register");
	}
	
	@Override
	public void unregister(Count count) throws Exception{

		logger.trace("Enter  unregister");
		try {
			//Obtenemos RegisterThread de registerThreadMap con Count
			logger.debug("Obtenemos el hilo del registro del mapa con "+count);
			RegisterThread thread = registerThreadMap.get(count);
			
			//Si no esta Count registerThreadMap se devuelve UregisterException
			if(thread==null){
				logger.debug(count+"no esta en el mapa de registros se devuelve una excepcion");
				throw new Exception(count+" no esta en el mapa de registros");
			}
			
			//Interrumpimos  RegisterThread
			logger.debug("Interrumpimos "+thread);
			thread.interrupt();
			
			// Borramos Count y RegisterThread del registerThreadMap
			logger.debug("Borramos "+count+" y "+thread+" del mapa");
			 registerThreadMap.remove(count);
		}catch (Exception e) {
			logger.trace("Exit  unregister - Exception");
			throw e;
		}
		logger.trace("Exit unregister");
	}
	
	@Override
	public void invite(Call call) throws Exception {
		Request request; 
		logger.trace("Enter invite");

		try {
			//Creamos el request del Invite de Call
			logger.debug("Creamos el request del Invite de "+call);
			request = createInvite(call);
			//Enviamos  el Invite de Call
			logger.debug("Enviamos el request del Invite de "+call);
			sendRequestForCall(call, request);
		} catch (Exception e) {
			logger.trace("Exit  invite - Exception");
			throw e;
		}
		logger.trace("Exit invite");

	}
	
	@Override
	public void bye(Call call) throws Exception {
		Request request;
	
		logger.trace("Enter bye");

		try {
			//Creamos el request del Bye de Call
			logger.debug("Creamos el request del Bye de "+call);
			request = createBye(call);
			//Enviamos  el request del Bye de Call
			logger.debug("Enviamos el request del Bye de "+call);
			sendRequestForCall(call, request);
		} catch (Exception e) {
			logger.trace("Exit  bye - Exception");
			throw e;
		}
		logger.trace("Exit bye");

	}
	
	@Override
	public void processRequest(RequestEvent evt,SipManager sipManager) {
		String reqMethod;
		
		logger.trace("Enter  processRequest");

		reqMethod = evt.getRequest().getMethod();
		
		switch (reqMethod) {
		case Request.REGISTER:
			
			logger.error("No se tratan peticiones de REGISTER");
			break;
		case Request.INVITE:
			
			//TODO: Tratar cuando se recibe un INVITE
			break;
		case Request.BYE:
			
			//TODO: Tratar cuando se recibe un BYE
			break;
		default:
			logger.warn("RequestEvent no tratado");
			break;
		}
		
		logger.trace("Exit  processRequest");

	}

	@Override
	public void processResponse(ResponseEvent evt,SipManager sipManager) {
		Response resp;
		Request req;
		String reqMethod;
		Transaction transaction;
		SessionRtp session;

		int statusCode;

		logger.trace("Enter  processResponse");

		reqMethod = evt.getClientTransaction().getRequest().getMethod();
		req =  evt.getClientTransaction().getRequest();
		resp = evt.getResponse();
		transaction = evt.getClientTransaction();
		statusCode = resp.getStatusCode();

		switch (reqMethod) {
		case Request.REGISTER:
			try {
				switch (statusCode) {
				case (Response.TRYING):
					break;
				case (Response.PROXY_AUTHENTICATION_REQUIRED):
					sendAuthenticateRegister(evt,sipManager);
					break;
				case (Response.OK):
					Count countOK = countTransactionMap.get(transaction);
					if (countOK != null) {
						// Si el tiempo de expirar es 0 es que es un desregistro
						if (((ExpiresHeader) req.getHeader(ExpiresHeader.NAME)).getExpires() == 0) {
							countOK.setState(Count.State.unregistered);
						} else {
							countOK.setState(Count.State.registered);
						}
						SipMessage message = new SipMessage(SipServiceImpl.this,SipMessage.REGISTER_TYPE, countOK);
						logger.debug("Notificamos "+message+" a los subscriptores");
						MessageService.sendMessage(message);
					}
					break;
				case (Response.UNAUTHORIZED):
					Count countUnau = countTransactionMap.get(transaction);
					if (countUnau != null) {
						countUnau.setState(Count.State.unauthorized);
						SipMessage message = new SipMessage(SipServiceImpl.this,SipMessage.REGISTER_TYPE, countUnau);
						logger.debug("Notificamos "+message+" a los subscriptores");
						MessageService.sendMessage(message);
					}
					break;
				default:
					logger.error("Error the codResponse not is correct " + statusCode);
					Count countDef = countTransactionMap.get(transaction);
					if (countDef != null) {
						SipMessage message = new SipMessage(SipServiceImpl.this,SipMessage.REGISTER_TYPE, countDef);
						logger.debug("Notificamos "+message+" a los subscriptores");
						MessageService.sendMessage(message);
					}
					break;
				}
			} catch (Exception e) {
				logger.error("Exception - " + e);
				Count countEx = countTransactionMap.get(transaction);
				if (countEx != null) {
					countEx.setState(Count.State.error);
					SipMessage message = new SipMessage(SipServiceImpl.this,SipMessage.REGISTER_TYPE, countEx);
					logger.debug("Notificamos "+message+" a los subscriptores");
					MessageService.sendMessage(message);
				}
			}
			break;
		case Request.INVITE:
			try {
				switch (statusCode) {
				case (Response.RINGING):
					session = BeanRtpFactory.createSessionRtp(req,resp);
					rtpService.createSession(session);
					// tenemos que notificar el cambio
					Call callSipRinging = callTransactionMap.get(transaction);
					callSipRinging.setState(Call.State.ringing);
					SipMessage message = new SipMessage(SipServiceImpl.this,SipMessage.CALL_TYPE, callSipRinging);
					logger.debug("Notificamos "+message+" a los subscriptores");
					MessageService.sendMessage(message);
					break;
				case (Response.TRYING):
					break;
				case (Response.OK):
					// Se envia un ACK para que empiece la tranmision del RTP
					sendAck(evt);
				
					session = BeanRtpFactory.createSessionRtp(req,resp);
					rtpService.createSession(session);
					// tenemos que notificar el cambio
					Call callSipOk = callTransactionMap.get(transaction);
					callSipOk.setState(Call.State.talking);
					SipMessage messageOk = new SipMessage(SipServiceImpl.this,SipMessage.CALL_TYPE, callSipOk);
					logger.debug("Notificamos "+messageOk+" a los subscriptores");
					MessageService.sendMessage(messageOk);
					break;
				case (Response.SESSION_PROGRESS):
					session = BeanRtpFactory.createSessionRtp(req,resp);
					rtpService.createSession(session);
					break;
				default:
					logger.error("Error the codResponse not is correct " + statusCode);
					Call callSipDef = callTransactionMap.get(transaction);
					callSipDef.setState(Call.State.error);
					SipMessage messageDef = new SipMessage(SipServiceImpl.this,SipMessage.CALL_TYPE, callSipDef);
					logger.debug("Notificamos "+messageDef+" a los subscriptores");
					MessageService.sendMessage(messageDef);
					break;
				}

			} catch (Exception e) {
				Call callSipEx = callTransactionMap.get(transaction);
				callSipEx.setState(Call.State.error);
				SipMessage messageEx = new SipMessage(SipServiceImpl.this,SipMessage.CALL_TYPE, callSipEx);
				logger.debug("Notificamos "+messageEx+" a los subscriptores");
				MessageService.sendMessage(messageEx);
				
			}
			break;
		case Request.BYE:
			try {
				switch (statusCode) {
				case (Response.TRYING):
					break;
				case (Response.OK):
					// Se envia un ACK para que termine la llamada
					sendAck(evt);
				
					session = BeanRtpFactory.createSessionRtp(req,resp);
					rtpService.destroySession(session);
					// tenemos que notificar el cambio
					Call callSipOk = callTransactionMap.get(transaction);
					callSipOk.setState(Call.State.terminated);
					SipMessage messageOk = new SipMessage(SipServiceImpl.this,SipMessage.CALL_TYPE, callSipOk);
					logger.debug("Notificamos "+messageOk+" a los subscriptores");
					MessageService.sendMessage(messageOk);
					break;
				default:
					logger.error("Error the codResponse not is correct " + statusCode);
					Call callSipDef = callTransactionMap.get(transaction);
					callSipDef.setState(Call.State.error);
					SipMessage messageDef = new SipMessage(SipServiceImpl.this,SipMessage.CALL_TYPE, callSipDef);
					logger.debug("Notificamos "+messageDef+" a los subscriptores");
					MessageService.sendMessage(messageDef);
					break;
				}
			} catch (Exception e) {
				Call callSipEx = callTransactionMap.get(transaction);
				callSipEx.setState(Call.State.error);
				SipMessage messageEx = new SipMessage(SipServiceImpl.this,SipMessage.CALL_TYPE, callSipEx);
				logger.debug("Notificamos "+messageEx+" a los subscriptores");
				MessageService.sendMessage(messageEx);
			}
			break;

		default:
			logger.warn("processResponse no tratado");
			break;
		}

		logger.trace("Exit  processResponse");

	}

	@Override
	public void processTimeout(TimeoutEvent evt,SipManager sipManager) {
		String reqMethod;
		Transaction transaction;
		
		logger.trace("Enter  processTimeout");
		
		reqMethod = evt.getClientTransaction().getRequest().getMethod();
		transaction = evt.getClientTransaction();
		
		switch (reqMethod) {
		case Request.REGISTER:
			try {
				Count count = countTransactionMap.get(transaction);
				if (count != null) {
					count.setState(Count.State.error);
					SipMessage message = new SipMessage(SipServiceImpl.this,SipMessage.REGISTER_TYPE, count);
					logger.debug("Notificamos "+message+" a los subscriptores");
					MessageService.sendMessage(message);
				}

			} catch (Exception e) {
				logger.error("Exception - " + e);
			}
			break;
		case Request.INVITE:
		case Request.BYE:
			try {
				Call callSip = callTransactionMap.get(transaction.getBranchId());
				if (callSip != null) {
					callSip.setState(Call.State.error);
					SipMessage message = new SipMessage(SipServiceImpl.this,SipMessage.CALL_TYPE, callSip);
					logger.debug("Notificamos "+message+" a los subscriptores");
					MessageService.sendMessage(message);
				}

			} catch (Exception e) {
				logger.trace("Exit  processTimeout - Exception");
				logger.error("Exception - " + e);
			}
			break;
		default:
			logger.warn("TimeoutEvent no tratado");
			break;
		}

		
		logger.trace("Exit  processTimeout");
	}

	@Override
	public void processDialogTerminated(DialogTerminatedEvent evt, SipManager sipManager) {
		Dialog dialog;
		Call callSip;
		
		logger.trace("Enter  processDialogTerminated");
		dialog = evt.getDialog();
		if(dialog!=null && callDialogMap.containsKey(dialog)){
			callSip = callDialogMap.remove(dialog);
			logger.debug("Eliminamos el Call: "+callSip+" del Mapa  que corresponde con "
					+ "el dialogo: "+dialog);
		}
		
		logger.trace("Exit processDialogTerminated");
	}

	@Override
	public void processTransactionTerminatedEvent(TransactionTerminatedEvent evt, SipManager sipManager) {
		Transaction transaction;
		Count count;
		Call callSip;
		
		transaction = evt.getClientTransaction();
		logger.trace("Enter  processTransactionTerminatedEvent");
		transaction = evt.getClientTransaction();
		if(transaction!=null && countTransactionMap.containsKey(transaction)){
			count = countTransactionMap.remove(transaction);
			logger.debug("Eliminamos el Count: "+count+" del Mapa que corresponde con "
					+ "la transaccion: "+transaction);
		}
		if(transaction!=null && callTransactionMap.containsKey(transaction)){
			callSip = callTransactionMap.remove(transaction);
			logger.debug("Eliminamos el Call: "+callSip+" del Mapa que corresponde con "
					+ "la transaccion: "+transaction);
		}
		
		transaction = evt.getServerTransaction();
		if(transaction!=null && countTransactionMap.containsKey(transaction)){
			count = countTransactionMap.remove(transaction);
			logger.debug("Eliminamos el Count: "+count+" del Mapa que corresponde con "
					+ "la transaccion: "+transaction);
		}
		if(transaction!=null && callTransactionMap.containsKey(transaction)){
			callSip = callTransactionMap.remove(transaction);
			logger.debug("Eliminamos el Call: "+callSip+" del Mapa que corresponde con "
					+ "la transaccion: "+transaction);
		}
		
		logger.trace("Exit processTransactionTerminatedEvent");
	}
	
	private Request createRegister(Count count,Integer expires)
			throws Exception {
		Request request;
		String uri, callId, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia,
				transportVia, branchVia, userContact, hostContact;
		Long cSeq;
		Integer portVia, portContact;

		logger.trace("Enter createRegister");
		try {
			SipManager sipManager = getSipManager(count);
			
			uri = "sip:" + count.getUser() + "@" + count.getHostServer();
			callId = sipManager.getNewCallId(count.getLocalPort(), count.getProtocol());
			cSeq = sipManager.getNewCSeq(count.getProtocol());
			userFrom = count.getUser();
			hostFrom = count.getHostServer();
			displayFrom = count.getDisplay();
			tagFrom = createTagRandom();
			userTo = count.getUser();
			hostTo = count.getHostServer();
			displayTo = count.getDisplay();
			tagTo = null;
			hostVia = sipManager.getHost();
			portVia = count.getLocalPort();
			transportVia = count.getProtocol();
			branchVia = null;

			userContact = count.getUser();
			hostContact = sipManager.getHost();
			portContact = count.getLocalPort();

			// Creamos el request de Register de Count
			logger.debug("Creamos el request de Invite de Count: "+count);	
			request = requestFactory.createRegister(uri, callId, cSeq, userFrom, hostFrom, displayFrom, tagFrom,
					userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards,
					userContact, hostContact, portContact, expires);
		} catch (Exception e) {
			logger.trace("Exit  createRequestRegister - Exception",e);
			throw e;
		}
		
		logger.trace("Exit createRegister");
		return request;
		
	}
	
	private Request createInvite(Call call)throws Exception {
		Request request;
		String uri, callId, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia,
				transportVia, branchVia, userContact, hostContact, product, contentType, contentSubType;
		Long cSeq;
		Integer portVia, maxForwards, portContact;
		SessionDescription objSessionDescription;
		Long sessionId, sessionVersion;
		String ipSessionDescription;
		Integer portSessionDescription, mode;
		List<Integer> audioCodecs;

		logger.trace("Enter createInvite");

		try {

			SipManager sipManager = getSipManager(call.getCount());
			
			uri = "sip:" + call.getNumber() + "@" + call.getCount().getHostServer();
			callId = sipManager.getNewCallId(call.getCount().getLocalPort(),call.getCount().getProtocol());
			cSeq = sipManager.getNewCSeq(call.getCount().getProtocol());
			userFrom =  call.getCount().getUser();
			hostFrom =  call.getCount().getHostServer();
			displayFrom =  call.getCount().getDisplay();
			tagFrom = createTagRandom();
			userTo = call.getNumber();
			hostTo = call.getCount().getHostServer();
			displayTo = "";
			tagTo = null;
			hostVia = sipManager.getHost();
			portVia = call.getCount().getLocalPort();
			transportVia = call.getCount().getProtocol();
			branchVia = null;
			maxForwards = this.maxForwards;

			userContact =  call.getCount().getUser();
			hostContact = sipManager.getHost();
			portContact = call.getCount().getLocalPort();

			product = this.product;

			contentType = call.getContentType();
			contentSubType = call.getContentSubType();

			sessionId = 0L;
			sessionVersion = 0L;
			ipSessionDescription = sipManager.getHost();
			audioCodecs = call.getCount().getAudioAvalibleList();
			portSessionDescription = calculatePortRTP();
			mode = call.getSessionDescriptionMode();
			objSessionDescription = SessionDescriptionFactory.createSessionDescription(sessionId, sessionVersion,
					ipSessionDescription, audioCodecs, portSessionDescription, mode);

			// Creamos el request de Invite de Call
			logger.debug("Creamos el request de Invite de Call: "+call);		
			request = requestFactory.createInvite(uri, callId, cSeq, userFrom, hostFrom, displayFrom, tagFrom, userTo,
					hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards, userContact,
					hostContact, portContact, product, contentType, contentSubType, objSessionDescription);

		} catch (Exception e) {
			logger.trace("Exit  createInvite - Exception");
			throw e;
		}
		logger.trace("Exit createInvite");
		return request;
		
	}
	
	private Request createBye(Call call)throws Exception {
		Request request;
		String uri, callId, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia,
				transportVia, branchVia;
		Long cSeq;
		Integer portVia, maxForwards;

		logger.trace("Enter createBye");

		try {

			SipManager sipManager = getSipManager(call.getCount());
			
			uri = "sip:" + call.getNumber() + "@" + call.getCount().getHostServer();
			callId = sipManager.getNewCallId(call.getCount().getLocalPort(), call.getCount().getProtocol());
			cSeq = sipManager.getNewCSeq(call.getCount().getProtocol());
			userFrom = call.getCount().getUser();
			hostFrom = call.getCount().getHostServer();
			displayFrom = call.getCount().getDisplay();
			tagFrom = null;
			userTo = call.getNumber();
			hostTo = call.getCount().getHostServer();
			displayTo = "";
			tagTo = null;
			hostVia = sipManager.getHost();
			portVia = call.getCount().getLocalPort();
			transportVia = call.getCount().getProtocol();
			branchVia = null;
			maxForwards = this.maxForwards;

			// Creamos el request de Bye de Call
			logger.debug("Creamos el request de Bye de Call: "+call);
			request = requestFactory.createBye(uri, callId, cSeq, userFrom, hostFrom, displayFrom, tagFrom, userTo,
					hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);

		} catch (Exception e) {
			logger.trace("Exit  createBye - Exception");
			throw e;
		}
		logger.trace("Exit createBye");
		return request;
	}
	
	private void sendRequestForCount(Count count,Request request) throws Exception{
		
		logger.trace("Enter  sendRequestForCount");
		try{
			SipManager sipManager = getSipManager(count);
			
			//Enviamos el request generado por Count  por Sip Manager
			logger.debug("Enviamos el request generado por el Count: "+count+"  por el SipManager: "+sipManager);
			Transaction transaction = sipManager.sendRequest(count.getLocalPort(), count.getProtocol(), request);
			
			//Guardamos Count en countTransactionMap
			logger.debug("Guardamos la cuenta en el mapa de transacciones, key: "+transaction+" Count: "+count);
			countTransactionMap.put(transaction, count);
		}catch (Exception e) {
			logger.trace("Exit  sendRegister - Exception");
			throw e;
		}
		
		logger.trace("Exit sendRequestForCount");
	}
	
	private void sendRequestForCall(Call call,Request request) throws Exception{
		
		logger.trace("Enter  sendRequestForCall");
		try{
			SipManager sipManager = getSipManager(call.getCount());
	
			//Enviamos el request generado por Call  por Sip Manager
			logger.debug("Enviamos el request generado por el Call: "+call+"  por el SipManager: "+sipManager);
			Transaction transaction = sipManager.sendRequest(call.getCount().getLocalPort(), call.getCount().getProtocol(), request);
			
			//Guardamos Call en callTransactionMap
			logger.debug("Guardamos la llamada en el mapa de transacciones, key: "+transaction+" Call: "+call);
			callTransactionMap.put(transaction, call);
			
			if(transaction.getDialog()!=null){
				//Guardamos Call en callDialogMap
				logger.debug("Guardamos la llamada en el mapa de dialogos, key: "+transaction.getDialog()+" Call: "+call);
				callDialogMap.put(transaction.getDialog(), call);
				
			}
		}catch (Exception e) {
			logger.trace("Exit  sendRequestForCall - Exception");
			throw e;
		}
		
		logger.trace("Exit sendRequestForCall");
	}
	
	/**
	 * Metodo que envia el mensaje de Autentificacion para el registro cuando la
	 * centralita lo requiera.
	 * 
	 * @param Evento
	 *            de respuesta
	 * @throws SendRegisterException
	 */
	private void sendAuthenticateRegister(ResponseEvent arg0,SipManager sipManager) throws Exception {
		Response resp;
		Request req, reqAuth;
		Transaction transaction;
		String hostVia, transportVia, branchVia, user, password;
		Integer portVia;

		logger.trace("Enter  authenticateRegister");
		try {
			req = arg0.getClientTransaction().getRequest();
			resp = arg0.getResponse();
			transaction = arg0.getClientTransaction();
			
			hostVia = ((ViaHeader) req.getHeader(ViaHeader.NAME)).getHost();
			portVia = ((ViaHeader) req.getHeader(ViaHeader.NAME)).getPort();
			transportVia = ((ViaHeader) req.getHeader(ViaHeader.NAME)).getTransport();
			branchVia = "";

			// Creamos el request del AuthenticateRegister de Count
			Count count = countTransactionMap.get(transaction);
			user = count.getUser();
			password = count.getPassword();
			logger.debug("Creamos el request del AuthenticateRegister de " + count);
			reqAuth = requestFactory.createRegisterAuthenticate(req, resp, hostVia, portVia, transportVia, branchVia,
					user, password);

			// Enviamos el AuthenticateRegister de Count
			logger.debug("Enviamos el request del AuthenticateRegister de Count: "+count+"  por el SipManager: "+sipManager);
			sipManager.sendRequest(((ViaHeader) req.getHeader(ViaHeader.NAME)).getPort(),
					((ViaHeader) req.getHeader(ViaHeader.NAME)).getTransport(), reqAuth);
		} catch (Exception e) {
			logger.trace("Exit  authenticateRegister - Exception");
			throw e;
		}
		logger.trace("Exit  authenticateRegister");
	}
	
	private void sendAck(ResponseEvent arg0) throws Exception {

		logger.trace("Enter sendAck");
		try{
			Response response = (Response) arg0.getResponse();
			ClientTransaction tid = arg0.getClientTransaction();
			CSeqHeader cseq = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
			Dialog dialog = arg0.getDialog();

			if (tid == null) {
				if (ackRequest != null && dialog != null) {
					//Re-enviamos un Ack del Dialog
					logger.debug("Re-enviamos un Ack del dialogo: "+dialog);
					dialog.sendAck(ackRequest);
				}
				return;
			}

			if (response.getStatusCode() == Response.OK) {
				// Creamos un Ack del Dialog
				logger.debug("Creamos un Ack del dialogo: " + dialog);
				ackRequest = dialog.createAck(cseq.getSeqNumber());
				
				// Enviamos un Ack del Dialog
				logger.debug("Enviamos un Ack del dialogo: " + dialog);
				dialog.sendAck(ackRequest);
			}

		} catch (Exception e) {
			logger.trace("Exit sendAck - Exception");
			throw e;
		}

		logger.trace("Exit sendAck");
		
		
	}

	/**
	 * Metodo que calcula el primer Puerto RTP libre a partir del puerto RTP
	 * defindo en los properties
	 * @return
	 * @throws PortRTPNotAvalibleException
	 */
	private Integer calculatePortRTP() throws PortRTPNotAvalibleException{
		boolean isFreePort;
		int port;
		
		logger.trace("Enter  calculatePortRTP");
		try{
			isFreePort = false;
			// iniciamos la busqueda en el puerto de definido en e properties.
			port = portRTP;
			while(!isFreePort) {
				try{
					DatagramSocket datagramSocket = new DatagramSocket(portRTP, InetAddress.getLocalHost());
					datagramSocket.close();
					datagramSocket=null;
					isFreePort = true;
				}catch (SocketException e) {
					port = port+4;
				}
				
			}
		} catch (Exception e) {
			logger.trace("Exit  calculatePortRTP - Exception");
			throw new PortRTPNotAvalibleException("No se puede calcular el puerto RTP",e);
		}

		logger.trace("Exit  calculatePortRTP");
		return port;
	}
	
	/**
	 * Metodo que crea una etiqueta con un Long Random para que 
	 * sea insertado como tag en las cabeceras SIP From y To
	 * @return
	 */
	private String createTagRandom() {
		
		logger.trace("Enter createTagRandom");
		logger.trace("Exit createTagRandom");
		return new Long((int) (Math
	              .random() * Integer.MAX_VALUE)).toString();
		
	}
	
	/**
	 * Metodo que busca el SipManager en la coleccion de SipManager,
	 * sino existe se crea uno y se devuelve.
	 * @param host
	 * @return
	 * @throws Exception
	 */
	private synchronized SipManager getSipManager(Count count)throws Exception{
		SipManager sipManager;
		logger.trace("Enter getSipManager");
		
		String host = count.getLocalHost();
		
		//Buscamos el SipManager del LocalHost
		for(SipManager sipManagerColle : sipManagerCollection){
			if(sipManagerColle.getHost().equals(host)){
				//Devolvemos el SipManager del LocalHost
				logger.debug("Devolvemos "+sipManagerColle+" del LocalHost: "+host);
				logger.trace("Exit getSipManager");
				return sipManagerColle;
			}
		}
		// Como no existe un SipManager Creamos uno.
		logger.debug("Creamos un SipManager");
		sipManager = SipManagerFactory.createSipManager(host,count.getLocalPort(),count.getProtocol());
		sipManagerCollection.add(sipManager);
		logger.debug("Devolvemos "+sipManager+" del LocalHost: "+host);
		logger.trace("Exit getSipManager");
		return sipManager;
	}

	
	/**
	 * Clase que lanza el hilo del Registro. Se mandara un mensaje REGITER cada registerTimer con un 
	 * ExpireregisterExpires  , si al enviar un Registro ocurre una excepcion que no se InterruptedException
	 * se notifica el error para los subcriptores pero se sigue haciendo peticiones, si recibimos un 
	 * InterruptedException entonces hacemos un Register con Expire a 0 para desregistrar la cuenta y
	 * luego paramos el hilo.
	 * 
	 * @author jmdomingueza
	 *
	 */
	private class RegisterThread extends Thread {

		/** 
		 * Cuenta sobre la que se lanza el hilo del Registro
		 */
		private Count count;
		
		/**
		 * Constructor de la clase 
		 * @param count
		 */
		public RegisterThread(Count count) {
			super("RegisterThread - " + count);

			logger.trace("Enter RegisterThread");
			
			this.count = count;
			
			logger.trace("Exit RegisterThread");
		}

		@Override
		public void run() {
			Request request;
			
			logger.trace("Enter run");
			
			logger.debug("Iniciamos el hilo: " + this.getName());
			while (true) {
				try {
					// Creamos el request del Register de Count
					logger.debug("Creamos el request del Register de " + count + " con el tiempo " + registerExpires);
					request = createRegister(count, registerExpires);
					// Enviamos el Register de Count
					logger.debug("Enviamos el request del Register de " + count);
					sendRequestForCount(count, request);
					// Paramos el hilo un tiempo
					logger.debug("Paramos " + this + " el tiempo " + registerTimer);
					Thread.sleep(registerTimer);
				} catch (InterruptedException e) {
					// Si se manda un REGISTER con expires a 0 es como
					// desregistrar
					try {
						// Creamos el request del Register de Count
						logger.debug("Creamos el request del Register de " + count + " con el tiempo " + 0);
						request = createRegister(count, 0);
						// Enviamos el Register de Count
						logger.debug("Enviamos el request del Register de " + count);
						sendRequestForCount(count, request);
						logger.debug("Paramos el hilo: " + this.getName());
					} catch (Exception e1) {
						e.printStackTrace();
						// Notificar para arriba el error
						count.setState(Count.State.error);
						SipMessage message = new SipMessage(SipServiceImpl.this, SipMessage.REGISTER_TYPE, count);
						logger.debug("Notificamos " + message + " a los subscriptores");
						MessageService.sendMessage(message);
						logger.debug("Paramos el hilo: " + this.getName() + " por excepcion en el desregistro");
					}
					break;
				} catch (Exception e) {
					e.printStackTrace();
					// Notificar para arriba el error
					count.setState(Count.State.error);
					SipMessage message = new SipMessage(SipServiceImpl.this, SipMessage.REGISTER_TYPE, count);
					logger.debug("Notificamos " + message + " a los subscriptores");
					MessageService.sendMessage(message);
					// No paramos el hilo porque la unica excepcion que para el hilo es InterruptedException
					logger.debug("No paramos el hilo: " + this.getName() + " por excepcion");
				}
			}
			logger.trace("Exit run");
			return;
		}

		@Override
		public void interrupt() {
			logger.trace("Enter interrupt");
			
			logger.debug("Interrumpimos el hilo: " + this.getName());
			super.interrupt();
			
			logger.trace("Exit interrupt");
		}
		
		

	}

}
