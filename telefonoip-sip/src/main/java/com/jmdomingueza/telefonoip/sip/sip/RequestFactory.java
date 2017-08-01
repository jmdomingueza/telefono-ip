package com.jmdomingueza.telefonoip.sip.sip;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sdp.SessionDescription;
import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.address.URI;
import javax.sip.header.AcceptHeader;
import javax.sip.header.AuthorizationHeader;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContactHeader;
import javax.sip.header.ContentDispositionHeader;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.EventHeader;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.HeaderFactory;
import javax.sip.header.MaxForwardsHeader;
import javax.sip.header.ProxyAuthenticateHeader;
import javax.sip.header.ProxyAuthorizationHeader;
import javax.sip.header.ReferToHeader;
import javax.sip.header.SIPIfMatchHeader;
import javax.sip.header.SubscriptionStateHeader;
import javax.sip.header.ToHeader;
import javax.sip.header.UserAgentHeader;
import javax.sip.header.ViaHeader;
import javax.sip.header.WWWAuthenticateHeader;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class Factory that create the object Request of different form.
 * @author jmdomingueza
 *
 */
@Component
public class RequestFactory {

	/**
	 * Logger of the class
	 */
	private static final Log logger = LogFactory.getLog(RequestFactory.class);

	/**
	 * AddressFactory for create directions SIP
	 */
	private AddressFactory objAddressFactory;

	/**
	 * HeaderFactory  for  create heads SIP
	 */
	private HeaderFactory objHeaderFactory;

	/**
	 * MessageFactory for create message SIP
	 */
	private MessageFactory objMessageFactory;
	
	/**
	 * Path donde se encuentra las implementaciones de los objetos de la
	 * libreria jain-sip
	 */
	@Autowired
	private String pathName;

	
	@PostConstruct
	public void init(){
		SipFactory objSipFactory;

		objSipFactory = SipFactory.getInstance();
		objSipFactory.setPathName(pathName);
		try {
			objHeaderFactory = objSipFactory.createHeaderFactory();
			objAddressFactory = objSipFactory.createAddressFactory();
			objMessageFactory = objSipFactory.createMessageFactory();
			objSipFactory.resetFactory();
		} catch (PeerUnavailableException e) {
			if(objSipFactory!=null)
				objSipFactory.resetFactory();
			logger.error("Error init RequestFactory: "+e.getMessage());
		}
	}
	
	
	/**
	 * Creates a request ACK of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request ACK is malformed.
	 */
	public Request createAck(String uri,String callID,Long cSeq,
				 String userFrom,String hostFrom,String displayFrom,
				 String tagFrom,String userTo,String hostTo,String displayTo,
				 String tagTo,String hostVia,Integer portVia,String transportVia,
				 String branchVia,Integer maxForwards) throws CreateRequestException {
		Request req;
		String method;

		logger.trace("enter  createAck");
		try {
			method = Request.ACK;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createAck - req == null");
				throw new CreateRequestException("Error to create the request "+method+": req == null");
			}
		}catch (CreateRequestException e) {
			logger.trace("exit  createAck - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createAck - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit  createAck");
		return req;
	}

	/**
	 * Creates a request BYE of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request BYE is malformed.
	 */
	public Request createBye(String uri,String callID,Long cSeq,
			 String userFrom,String hostFrom,String displayFrom,
			 String tagFrom,String userTo,String hostTo,String displayTo,
			 String tagTo,String hostVia,Integer portVia,String transportVia,
			 String branchVia,Integer maxForwards) throws CreateRequestException {
		Request req;
		String method;

		logger.trace("enter  createBye");
		try {
			method = Request.BYE;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createBye - req == null");
				throw new CreateRequestException("Error to create the request "+method+": req == null");
			}
		}catch (CreateRequestException e) {
			logger.trace("exit  createBye - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createBye - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit  createBye");
		return req;
	}

	/**
	 * Creates a request CANCEL of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request CANCEL is malformed.
	 */
	public Request createCancel(String uri,String callID,Long cSeq,
			 String userFrom,String hostFrom,String displayFrom,
			 String tagFrom,String userTo,String hostTo,String displayTo,
			 String tagTo,String hostVia,Integer portVia,String transportVia,
			 String branchVia,Integer maxForwards) throws CreateRequestException {

		Request req;
		String method;

		logger.trace("enter  createCancel");
		try {
			method = Request.CANCEL;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createCancel - req == null");
				throw new CreateRequestException("Error to create the request "+method+": req == null");
			}
		}catch (CreateRequestException e) {
			logger.trace("exit  createCancel - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createCancel - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit  createCancel");
		return req;
	}

	/**
	 * Creates a request NOTIFY of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @param contentType  the new string content type value.
	 * @param contentSubType the new string content sub-type value.
	 * @param text the new string value of the text.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request NOTIFY is malformed.
	 */
	public Request createInfo(String uri,String callID,Long cSeq,
			 String userFrom,String hostFrom,String displayFrom,
			 String tagFrom,String userTo,String hostTo,String displayTo,
			 String tagTo,String hostVia,Integer portVia,String transportVia,
			 String branchVia,Integer maxForwards,
			 String info_Package, String contentType, String contentSubType,
			 String encoding, String text) throws CreateRequestException {

		Request req;
		String method;
		ContentTypeHeader contentTypeHeader;
		Header info_PackageHeader;
		ContentDispositionHeader contentDispositionHeader;

		logger.trace("enter  createInfo");
		try {
			method = Request.INFO;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createInfo - req == null");
				throw new CreateRequestException("Error to create the request "+method+": req == null");
			}

			info_PackageHeader = createInfoPackageHeader(info_Package);
			contentTypeHeader = createContentTypeHeader(contentType,contentSubType);
			contentDispositionHeader = createContentDispositionHeader(encoding);

			req.addHeader(info_PackageHeader);
			req.addHeader(contentTypeHeader);
			req.addHeader(contentDispositionHeader);

			//text Description
			if(text!=null)	{
				req.setContent(text, contentTypeHeader);
			}
		}catch (CreateRequestException e) {
			logger.trace("exit  createInfo - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createInfo - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit  createInfo");
		return req;
	}

	/**
	 * Creates a request INVITE of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @param product the new List of values of the product.
	 * @param contentType  the new string content type value.
	 * @param contentSubType the new string content sub-type value.
	 * @param objSessionDescription the new SessionDescription value of the sessionDescription.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request INVITE is malformed.
	 */
	public Request createInvite(String uri,String callID,Long cSeq,
			   				 String userFrom,String hostFrom,String displayFrom,
			   				 String tagFrom,String userTo,String hostTo,String displayTo,
			   				 String tagTo,String hostVia,Integer portVia,String transportVia,
			   				 String branchVia,Integer maxForwards,
			   				 String userContact,String hostContact, Integer portContact,
			   				 String product,String contentType, String contentSubType,
			   				SessionDescription objSessionDescription) throws CreateRequestException {
		ContactHeader contactHeader;
		UserAgentHeader userAgentHeader;
		ContentTypeHeader contentTypeHeader;
		Request req;
		String method;

		logger.trace("enter  createInvite");
		try {
			method = Request.INVITE;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createInvite - req == null");
				throw new CreateRequestException("Error to create the request "+method+": req == null");
			}
			contactHeader = createContactHeader(userContact,hostContact,portContact);
			userAgentHeader = createUserAgentHeader(product);
			contentTypeHeader = createContentTypeHeader(contentType,contentSubType);

			req.addHeader(contactHeader);
			req.addHeader(userAgentHeader);
			req.addHeader(contentTypeHeader);

			//Session Description
			if(objSessionDescription!=null)	{
				req.setContent(objSessionDescription, contentTypeHeader);
			}
		}catch (CreateRequestException e) {
			logger.trace("exit  createInvite - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createInvite - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit  createInvite");
		return req;
	}

	/**
	 * Authentication creates an INVITE request by Recquired
	 * The urging server to authenticate before making a call. The method
	 * Expand the original request it by entering the necessary parameters for
	 * Client authentication .
	 * @param req Original Request that should be expanded response calculated field
	 * 			  depending on the value of the nonce contained in the Response field .
	 * @param resp 407 response received for the server. Contain the nonce field employee
	 * 			  the method for calculating the response field
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param user the new string value of the user.
	 * @param password the new string value of the password.
	 * @return Request of the invite with authentication.
	 * @throws CreateRequestException Exception when there is a error in of INVITE
	 */
	public Request createInviteAuthenticate(Request req, Response resp,
											String hostVia,Integer portVia,String transportVia,
											String branchVia ,String user, String password) throws CreateRequestException{

		List<ViaHeader> viasHeader;
		Request reqAuthenticate;
		ProxyAuthenticateHeader objAuthenticateHeader;
		ProxyAuthorizationHeader objProxyAuthorizationHeader;
		CSeqHeader objCSeqHeader;

		logger.trace("enter  createInviteAuthenticate");
		reqAuthenticate=(Request)req.clone();
		//Recupero la cabecera WWW-Authenticate de la respuesta
		 objAuthenticateHeader = (ProxyAuthenticateHeader) resp.getHeader(ProxyAuthenticateHeader.NAME);
		//Recupero la cabecera CSeq de la respuesta
		objCSeqHeader= (CSeqHeader) resp.getHeader(CSeqHeader.NAME);

		try {
			objProxyAuthorizationHeader = objHeaderFactory.createProxyAuthorizationHeader(objAuthenticateHeader.getScheme());

			//Contrucción de los campos de Authorization
			String strAlgorithm = objAuthenticateHeader.getAlgorithm();
			String strRealm = objAuthenticateHeader.getRealm();
			String strOpaque = objAuthenticateHeader.getOpaque();
			String strNonce = objAuthenticateHeader.getNonce();
			String strQop = objAuthenticateHeader.getQop();
			String strMethod = objCSeqHeader.getMethod();

			ToHeader objToHeader = (ToHeader) reqAuthenticate.getHeader(ToHeader.NAME);
			Address remoteUAAddress = objToHeader.getAddress();
			//objToHeader.setTag("");
			if(strAlgorithm!= null) objProxyAuthorizationHeader.setAlgorithm(strAlgorithm);
			if(strRealm!= null) objProxyAuthorizationHeader.setRealm(strRealm);
			if(strOpaque!= null) objProxyAuthorizationHeader.setOpaque(strOpaque);
			if(strNonce!= null) objProxyAuthorizationHeader.setNonce(strNonce);
			objProxyAuthorizationHeader.setUsername(user);
			if(remoteUAAddress.getURI()!= null) objProxyAuthorizationHeader.setURI(remoteUAAddress.getURI());
			if(strQop!= null) objProxyAuthorizationHeader.setQop(strQop);
			//calculo del campo Response de AuthenticateHeader
			objCSeqHeader.setSeqNumber(objCSeqHeader.getSeqNumber() + 1);
			reqAuthenticate.setHeader(objCSeqHeader);

			viasHeader = createViaHeader(hostVia,portVia,transportVia,branchVia);
			reqAuthenticate.setHeader(viasHeader.get(0));

			String response = MessageDigestAlgorithm.calculateResponse(strAlgorithm,
													user,
													strRealm,
													password,
													strNonce,
													null,
													null,
													strMethod,
													remoteUAAddress.getURI().toString(),
													reqAuthenticate.toString(),
													strQop);

			objProxyAuthorizationHeader.setResponse(response);
			reqAuthenticate.addHeader(objProxyAuthorizationHeader);

		}catch (CreateRequestException e) {
			logger.trace("exit  createInviteAuthenticate - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createInviteAuthenticate -  - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}

		logger.trace("Request: "+reqAuthenticate.getMethod());
		logger.trace("\r\n"+reqAuthenticate);

		logger.trace("exit  createInviteAuthenticate");
		return reqAuthenticate;
	}

	/**
	 * Creates a request MESSAGE of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @param contentType  the new string content type value.
	 * @param contentSubType the new string content sub-type value.
	 * @param text the new string value of the text.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request MESSAGE is malformed.
	 */
	public Request createMessage(String uri,String callID,Long cSeq,
			 String userFrom,String hostFrom,String displayFrom,
			 String tagFrom,String userTo,String hostTo,String displayTo,
			 String tagTo,String hostVia,Integer portVia,String transportVia,
			 String branchVia,Integer maxForwards,
			 String userContact,String hostContact, Integer portContact,
			 String contentType, String contentSubType, String text) throws CreateRequestException {

		Request req;
		String method;
		ContactHeader contactHeader;
		ContentTypeHeader contentTypeHeader;

		logger.trace("enter  createMessage");
		try {
			method = Request.MESSAGE;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createMessage - req == null");
				throw new CreateRequestException("Error to create the request "+method+": req == null");
			}

			contactHeader = createContactHeader(userContact,hostContact,portContact);
			contentTypeHeader = createContentTypeHeader(contentType,contentSubType);

			req.addHeader(contactHeader);
			req.addHeader(contentTypeHeader);

			//text Description
			if(text!=null)	{
				req.setContent(text, contentTypeHeader);
			}
		}catch (CreateRequestException e) {
			logger.trace("exit  createMessage - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createMessage - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit  createMessage");
		return req;
	}

	/**
	 * Creates a request NOTIFY of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @param contentType  the new string content type value.
	 * @param contentSubType the new string content sub-type value.
	 * @param text the new string value of the text.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request NOTIFY is malformed.
	 */
	public Request createNotify(String uri,String callID,Long cSeq,
			 String userFrom,String hostFrom,String displayFrom,
			 String tagFrom,String userTo,String hostTo,String displayTo,
			 String tagTo,String hostVia,Integer portVia,String transportVia,
			 String branchVia,Integer maxForwards,String eventType,
			 String userContact,String hostContact, Integer portContact,
			 String subscriptionState,
			 String contentType, String contentSubType, String text) throws CreateRequestException {

		Request req;
		String method;
		ContactHeader contactHeader;
		ContentTypeHeader contentTypeHeader;
		SubscriptionStateHeader subscriptionStateHeader;
		EventHeader eventHeader;

		logger.trace("enter  createNotify");
		try {
			method = Request.NOTIFY;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createNotify - req == null");
				throw new CreateRequestException("Error to create the request "+method+": req == null");
			}

			eventHeader = createEventHeader(eventType);
			contactHeader = createContactHeader(userContact,hostContact,portContact);
			subscriptionStateHeader = createSubscriptionStateHeader(subscriptionState);
			contentTypeHeader = createContentTypeHeader(contentType,contentSubType);

			req.addHeader(eventHeader);
			req.addHeader(subscriptionStateHeader);
			req.addHeader(contactHeader);
			req.addHeader(contentTypeHeader);

			//text Description
			if(text!=null)	{
				req.setContent(text, contentTypeHeader);
			}
		}catch (CreateRequestException e) {
			logger.trace("exit  createNotify - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createNotify - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit  createNotify");
		return req;
	}


	/**
	 * Creates a request OPTIONS of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @param contentType  the new string content type value.
	 * @param contentSubType the new string content sub-type value.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request OPTIONS is malformed.
	 */
	public Request createOptions(String uri,String callID,Long cSeq,
			 String userFrom,String hostFrom,String displayFrom,
			 String tagFrom,String userTo,String hostTo,String displayTo,
			 String tagTo,String hostVia,Integer portVia,String transportVia,
			 String branchVia,Integer maxForwards,
			 String userContact,String hostContact, Integer portContact,
			 String contentType, String contentSubType) throws CreateRequestException {
		Request req;
		String method;
		ContactHeader contactHeader;
		AcceptHeader acceptHeader;

		logger.trace("enter  createOptions");
		try {
			method = Request.OPTIONS;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createOptions - req == null");
				throw new CreateRequestException("Error to create the request "+method+": req == null");
			}

			contactHeader = createContactHeader(userContact,hostContact,portContact);
			acceptHeader = createAcceptHeader(contentType, contentSubType);

			req.addHeader(contactHeader);
			req.addHeader(acceptHeader);

		}catch (CreateRequestException e) {
			logger.trace("exit  createOptions - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createOptions - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit  createOptions");
		return req;
	}

	/**
	 * Creates a request PRACK of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request PRACK is malformed.
	 */
	public Request createPrack(String uri,String callID,Long cSeq,
				 String userFrom,String hostFrom,String displayFrom,
				 String tagFrom,String userTo,String hostTo,String displayTo,
				 String tagTo,String hostVia,Integer portVia,String transportVia,
				 String branchVia,Integer maxForwards) throws CreateRequestException {
		Request req;
		String method;

		logger.trace("enter  createPrack");
		try {
			method = Request.PRACK;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createPrack - req == null");
				throw new CreateRequestException("Error to create the request "+method+": req == null");
			}
		}catch (CreateRequestException e) {
			logger.trace("exit  createPrack - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createPrack - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit  createPrack");
		return req;
	}

	/**
	 *  Creates a request PUBLISH of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @param expires the new integer value of the time expires.
	 * @param eventType the new integer value of the time eventType.
	 * @param contentType  the new string content type value.
	 * @param contentSubType the new string content sub-type value.
	 * @param text the new string value of the text.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request PUBLISH is malformed.
	 */
	public Request createPublish(String uri,String callID,Long cSeq,
			 String userFrom,String hostFrom,String displayFrom,
			 String tagFrom,String userTo,String hostTo,String displayTo,
			 String tagTo,String hostVia,Integer portVia,String transportVia,
			 String branchVia,Integer maxForwards,String etag,
			 Integer expires, String eventType, String contentType, String contentSubType,
			 String text) throws CreateRequestException {
		Request req;
		String method;
		ExpiresHeader expiresHeader;
		EventHeader eventHeader;
		ContentTypeHeader contentTypeHeader;
		SIPIfMatchHeader sipIfMatchHeader;

		logger.trace("enter  createPublish");
		try {
			method = Request.PUBLISH;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createPublish - req == null");
				throw new CreateRequestException("Error to create the request "+method+": req == null");
			}

			if(etag!=null){
				sipIfMatchHeader = createSIPIfMatchHeader(etag);
				req.addHeader(sipIfMatchHeader);
			}
			expiresHeader = createExpireHeader(expires);
			eventHeader = createEventHeader(eventType);

			req.addHeader(expiresHeader);
			req.addHeader(eventHeader);

			if(contentType!=null && contentSubType!=null){
				contentTypeHeader = createContentTypeHeader(contentType,contentSubType);
				req.addHeader(contentTypeHeader);
				//text Description
				if(text!=null)	{
					req.setContent(text, contentTypeHeader);
				}
			}



		}catch (CreateRequestException e) {
			logger.trace("exit  createPublish - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createPublish - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit  createPublish");
		return req;
	}


	/**
	 *  Creates a request REGISTER of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @param expires the new integer value of the time expires.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request REGISTER is malformed.
	 */
	public Request createRegister(String uri,String callID,Long cSeq,
				 String userFrom,String hostFrom,String displayFrom,
				 String tagFrom,String userTo,String hostTo,String displayTo,
				 String tagTo,String hostVia,Integer portVia,String transportVia,
				 String branchVia,Integer maxForwards,
				 String userContact,String hostContact, Integer portContact,
				 Integer expires)
			throws CreateRequestException {

		ContactHeader contactHeader;
		ExpiresHeader expiresHeader;
		Request req;
		String method;

		logger.trace("enter createRegister");
		try {
			method = Request.REGISTER;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createRegister - req == null");
			throw new CreateRequestException("Error to create the request "+method+": req == null");
			}
			contactHeader = createContactHeader(userContact, hostContact, portContact);
			expiresHeader = createExpireHeader(expires);

			req.addHeader(contactHeader);
			req.addHeader(expiresHeader);

		}catch (CreateRequestException e) {
			logger.trace("exit  createRegister - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createRegister - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit createRegister");
		return req;
	}

	/**
	 * It generates a request for a 401 response to a registration request to the server.
	 * Generate a new request with the response calculated field based on the nonce
	 * server response and forwards the request to the server .
	 * @param req REGISTER request initially sent to the server generate a 401.
	 * @param resp Server response to the request ; field contains the nonce .
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param user the new string value of the user.
	 * @param password the new string value of the password.
	 * @return Authentication of the REGISTER request .
	 * @throws CreateRequestException if the Request REGISTER is malformed.
	 */
	public Request createRegisterAuthenticate(Request req, Response resp, String hostVia,Integer portVia,String transportVia,
			String branchVia ,String user, String password) throws CreateRequestException {
		List<ViaHeader> viasHeader;
		Request myRequest;
		WWWAuthenticateHeader objAuthenticateHeader;
		CSeqHeader objCSeqHeader;
		AuthorizationHeader objAuthorizationHeader;

		logger.trace("enter  createRegisterAuthenticate");
		myRequest = (Request)req.clone();
		//Recupero la cabecera WWW-Authenticate de la respuesta
		objAuthenticateHeader = (WWWAuthenticateHeader) resp.getHeader(WWWAuthenticateHeader.NAME);
		//Recupero la cabecera CSeq de la respuesta
		objCSeqHeader= (CSeqHeader) resp.getHeader(CSeqHeader.NAME);

		try {
			objAuthorizationHeader = objHeaderFactory.createAuthorizationHeader(objAuthenticateHeader.getScheme());

			//Contrucción de los campos de Authorization
			String strAlgorithm = objAuthenticateHeader.getAlgorithm();
			String strRealm = objAuthenticateHeader.getRealm();
			String strOpaque = objAuthenticateHeader.getOpaque();
			String strNonce = objAuthenticateHeader.getNonce();
			String strQop = objAuthenticateHeader.getQop();
			String strMethod = objCSeqHeader.getMethod();

			ToHeader objToHeader = (ToHeader) myRequest.getHeader(ToHeader.NAME);
			Address remoteUAAddress = objToHeader.getAddress();
			if(strAlgorithm!= null) objAuthorizationHeader.setAlgorithm(strAlgorithm);
			if(strRealm!= null) objAuthorizationHeader.setRealm(strRealm);
			if(strOpaque!= null) objAuthorizationHeader.setOpaque(strOpaque);
			if(strNonce!= null) objAuthorizationHeader.setNonce(strNonce);
			objAuthorizationHeader.setUsername(user);
			if(remoteUAAddress.getURI()!= null) objAuthorizationHeader.setURI(remoteUAAddress.getURI());
			if(strQop!= null) objAuthorizationHeader.setQop(strQop);
			//calculo del campo Response de AuthenticateHeader
			String response = MessageDigestAlgorithm.calculateResponse(strAlgorithm,
													user,
													strRealm,
													password,
													strNonce,
													null,
													null,
													strMethod,
													remoteUAAddress.getURI().toString(),
													myRequest.toString(),
													strQop);

			objAuthorizationHeader.setResponse(response);
			myRequest.addHeader(objAuthorizationHeader);
			objCSeqHeader.setSeqNumber(objCSeqHeader.getSeqNumber() + 1);
			myRequest.setHeader(objCSeqHeader);

			viasHeader = createViaHeader(hostVia,portVia,transportVia,branchVia);
			myRequest.setHeader(viasHeader.get(0));

		}catch (CreateRequestException e) {
			logger.trace("exit  createRegisterAuthenticate - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createRegisterAuthenticate - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+myRequest.getMethod());
		logger.trace("\r\n"+myRequest);

		logger.trace("exit  createRegisterAuthenticate");
		return myRequest;

	}


	/**
	 *  Creates a request REFER of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @param userReferTo the new string value of the user, this value may be null.
	 * @param hostReferTo the new string value of the host.
	 * @param portReferTo the new string value of the port.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request REFER is malformed.
	 */
	public Request createRefer(String uri,String callID,Long cSeq,
			 String userFrom,String hostFrom,String displayFrom,
			 String tagFrom,String userTo,String hostTo,String displayTo,
			 String tagTo,String hostVia,Integer portVia,String transportVia,
			 String branchVia,Integer maxForwards,
			 String userContact,String hostContact, Integer portContact,
			 String userReferTo,String hostReferTo, Integer portReferTo) throws CreateRequestException {

		ContactHeader contactHeader;
		ReferToHeader referToHeader;
		Request req;
		String method;

		logger.trace("enter createRefer");
		try {
			method = Request.REFER;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createRefer - req == null");
			throw new CreateRequestException("Error to create the request "+method+": req == null");
			}
			referToHeader = createReferToHeader(userReferTo, hostReferTo, portReferTo);
			contactHeader = createContactHeader(userContact, hostContact, portContact);

			req.addHeader(referToHeader);
			req.addHeader(contactHeader);

		}catch (CreateRequestException e) {
			logger.trace("exit  createRefer - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createRefer - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit createRefer");
		return req;
	}

	/**
	 *  Creates a request SUBSCRIBE of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @param expires the new integer value of the time expires.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request SUBSCRIBE is malformed.
	 */
	public Request createSubscribe(String uri,String callID,Long cSeq,
			 String userFrom,String hostFrom,String displayFrom,
			 String tagFrom,String userTo,String hostTo,String displayTo,
			 String tagTo,String hostVia,Integer portVia,String transportVia,
			 String branchVia,Integer maxForwards,
			 String userContact,String hostContact, Integer portContact,
			 Integer expires )throws CreateRequestException {

		ContactHeader contactHeader;
		ExpiresHeader expiresHeader;
		Request req;
		String method;

		logger.trace("enter createSubscribe");
		try {
			method = Request.SUBSCRIBE;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createSubscribe - req == null");
			throw new CreateRequestException("Error to create the request "+method+": req == null");
			}
			contactHeader = createContactHeader(userContact, hostContact, portContact);
			expiresHeader = createExpireHeader(expires);

			req.addHeader(contactHeader);
			req.addHeader(expiresHeader);

		}catch (CreateRequestException e) {
			logger.trace("exit  createSubscribe - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createSubscribe - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit createSubscribe");
		return req;
	}

	/**
	 * Creates a request UPDATE of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @param recvInfo the new string value of the recvInfo.
	 * @param contentType  the new string content type value.
	 * @param contentSubType the new string content sub-type value.
	 * @param objSessionDescription the new SessionDescription value of the sessionDescription.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request UPDATE is malformed.
	 */
	public Request createUpdate(String uri,String callID,Long cSeq,
				 String userFrom,String hostFrom,String displayFrom,
				 String tagFrom,String userTo,String hostTo,String displayTo,
				 String tagTo,String hostVia,Integer portVia,String transportVia,
				 String branchVia,Integer maxForwards,
				 String userContact,String hostContact, Integer portContact,
				 String recvInfo,String contentType, String contentSubType,
				SessionDescription objSessionDescription) throws CreateRequestException {

		ContactHeader contactHeader;
		Header recvInfoHeader;
		ContentTypeHeader contentTypeHeader;
		Request req;
		String method;

		logger.trace("enter  createUpdate");
		try {
			method = Request.UPDATE;
			req = createRequest(uri, callID, cSeq, method, userFrom, hostFrom, displayFrom, tagFrom, userTo, hostTo, displayTo, tagTo, hostVia, portVia, transportVia, branchVia, maxForwards);
			if(req == null){
				logger.trace("exit  createUpdate - req == null");
				throw new CreateRequestException("Error to create the request "+method+": req == null");
			}
			contactHeader = createContactHeader(userContact,hostContact,portContact);
			recvInfoHeader = createRecvInfoHeader(recvInfo);
			contentTypeHeader = createContentTypeHeader(contentType,contentSubType);

			req.addHeader(recvInfoHeader);
			req.addHeader(contactHeader);
			req.addHeader(contentTypeHeader);

			//Session Description
			if(objSessionDescription!=null)	{
				req.setContent(objSessionDescription, contentTypeHeader);
			}
		}catch (CreateRequestException e) {
			logger.trace("exit  createUpdate - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createInvite - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("Request: "+method);
		logger.trace("\r\n"+req);

		logger.trace("exit  createUpdate");
		return req;
	}

	/**
	 *  Creates a request  of SIP.
	 * @param uri the new string value of the URI.
	 * @param callID the new string value of the call-id.
	 * @param cSeq the new long value of the sequence number.
	 * @param method new string value of the method.
	 * @param userFrom the new string value of the user, this value may be null.
	 * @param hostFrom the new string value of the host.
	 * @param displayFrom the new string value of the display name.
	 * @param tagFrom the new string value of the tag.
	 * @param userTo the new string value of the user, this value may be null.
	 * @param hostTo the new string value of the host.
	 * @param displayTo the new string value of the display name.
	 * @param tagTo the new string value of the tag.
	 * @param hostVia the new string value of the host.
	 * @param portVia the new string value of the port.
	 * @param transportVia the new string value of the transport.
	 * @param branchVia the new string value of the branch.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @return the newly created Request object.
	 * @throws CreateRequestException if the Request is malformed.
	 */
	private Request createRequest(String uri,String callID,Long cSeq,String method,
			   String userFrom,String hostFrom,String displayFrom,
			   String tagFrom,String userTo,String hostTo,String displayTo,
			   String tagTo,String hostVia,Integer portVia,String transportVia,
			   String branchVia,Integer maxForwards) throws CreateRequestException {
		URI uriHeader;
		CallIdHeader callIdHeader;
		CSeqHeader cSeqHeader;
		FromHeader fromHeader;
		ToHeader toHeader;
		List<ViaHeader> viasHeader;
		MaxForwardsHeader maxForwardsHeader;
		Request req;

		logger.trace("enter  createRequest");

		try {
			uriHeader = createURI(uri);
			callIdHeader = createCallIdHeader(callID);
			cSeqHeader = createCSeqHeader(cSeq, method);
			fromHeader = createFromHeader(userFrom,hostFrom,displayFrom,tagFrom);
			toHeader = createToHeader(userTo,hostTo,displayTo,tagTo);
			viasHeader = createViaHeader(hostVia,portVia,transportVia,branchVia);
			maxForwardsHeader = createMaxForwardsHeader(maxForwards);

			req = objMessageFactory.createRequest(uriHeader, method, callIdHeader, cSeqHeader, fromHeader, toHeader, viasHeader, maxForwardsHeader);
		} catch (CreateRequestException e) {
			logger.trace("exit  createRequest - CreateRequestException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createRequest - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}

		logger.trace("exit  createRequest");
		return req;
	}

	/**
	 * Creates a URI based on given URI string. The URI string is parsed in order to create the new URI instance.
	 * Depending on the scheme the returned may or may not be a SipURI or TelURL cast as a URI.
	 * @param uri the new string value of the URI.
	 * @return  the newly created URI object.
	 * @throws CreateRequestException if the URI string is malformed.
	 */
	private URI createURI(String uri) throws CreateRequestException {
		URI objURI;
		logger.trace("enter  createURI");
		try{
			objURI = objAddressFactory.createURI(uri);
		} catch (Exception e) {
			logger.trace("exit  createURI - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createURI");
		return objURI;
	}

	/**
	 * Creates a new CallIdHeader based on the newly supplied callId value.
	 * @param callId the new string value of the call-id.
	 * @return the newly created CallIdHeader object.
	 * @throws CreateRequestException which signals that an error has been reached unexpectedly while parsing the callId value.
	 */
	private  CallIdHeader createCallIdHeader(String callID) throws CreateRequestException {
		CallIdHeader objCallIdHeader;
		logger.trace("enter  createCallIdHeader");
		try{
			objCallIdHeader = objHeaderFactory.createCallIdHeader(callID);
		} catch (Exception e) {
			logger.trace("exit  createCallIdHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createCallIdHeader");
		return objCallIdHeader;
	}

	/**
	 * Creates a new CallIdHeader based on the newly supplied callId value.
	 * @param sequenceNumber  the new long value of the sequence number.
	 * @param methodthe new string value of the method.
	 * @return the newly created CSeqHeader object.
	 * @throws CreateRequestException  if supplied sequence number is less than zero o which signals that an error has been reached
	 * unexpectedly while parsing the method value.
	 */
	private CSeqHeader createCSeqHeader(Long cSeq, String method) throws CreateRequestException {
		CSeqHeader objCSeqHeader;
		logger.trace("enter  createCSeqHeader");
		try{
			objCSeqHeader = objHeaderFactory.createCSeqHeader(cSeq, method);
		} catch (Exception e) {
			logger.trace("exit  createCSeqHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createCSeqHeader");
		return objCSeqHeader;
	}

	/**
	 * Creates a new FromHeader based on the newly supplied address and tag values.
	 * @param user the new string value of the user, this value may be null.
	 * @param host the new string value of the host.
	 * @param display the new string value of the display name.
	 * @param tag the new string value of the tag.
	 * @return the newly created FromHeader object.
	 * @throws CreateRequestException which signals that an error has been reached unexpectedly while parsing the tag value.
	 */
	private FromHeader createFromHeader(String userFrom, String hostFrom, String displayFrom,String tagFrom) throws CreateRequestException{
		SipURI fromURI;
		Address fromAddress;
		FromHeader objFromHeader;

		logger.trace("enter  createFromHeader");
		try{
			fromURI = objAddressFactory.createSipURI(userFrom, hostFrom);
			fromAddress = objAddressFactory.createAddress(fromURI);
			//If display equals null don´t insert
			if(displayFrom!=null){
				fromAddress.setDisplayName(displayFrom);
			}
			objFromHeader = objHeaderFactory.createFromHeader(fromAddress, tagFrom);
		} catch (Exception e) {
			logger.trace("exit  createFromHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createFromHeader");
		return objFromHeader;
	}

	/**
	 * Creates a new ToHeader based on the newly supplied address and tag values.
	 * @param user the new string value of the user, this value may be null.
	 * @param host the new string value of the host.
	 * @param display the new string value of the display name.
	 * @param tag the new string value of the tag.
	 * @return the newly created ToHeader object.
	 * @throws CreateRequestException which signals that an error has been reached unexpectedly while parsing the tag value.
	 */
	private ToHeader createToHeader(String userTo, String hostTo, String displayTo,String tagTo) throws CreateRequestException{
		SipURI toURI;
		Address toAddress;
		ToHeader objtoHeader;

		logger.trace("enter  createToHeader");
		try{
			toURI = objAddressFactory.createSipURI(userTo,hostTo);
			toAddress = objAddressFactory.createAddress(toURI);
			if(displayTo!=null){
				toAddress.setDisplayName(displayTo);
			}
			objtoHeader = objHeaderFactory.createToHeader(toAddress, tagTo);
		} catch (Exception e) {
			logger.trace("exit  createToHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createToHeader");
		return objtoHeader;
	}

	/**
	 * Creates a new ViaHeader based on the newly supplied uri and branch values.
	 * @param host  the new string value of the host.
	 * @param port  the new integer value of the port.
	 * @param transport the new string value of the transport.
	 * @param branch the new string value of the branch.
	 * @return the newly created ViaHeader object.
	 * @throws CreateRequestException which signals that an error has been reached unexpectedly while parsing the host, transport or branch value.
	 * @throws CreateRequestException if the supplied port is invalid.
	 */
	private List<ViaHeader> createViaHeader(String hostVia,Integer portVia,String transportVia, String branchVia) throws CreateRequestException{
		List<ViaHeader> viaHeaders = new ArrayList<>();
		ViaHeader objViaHeader;

		logger.trace("enter  createViaHeader");
		try{
			objViaHeader = objHeaderFactory.createViaHeader(hostVia, portVia,transportVia, branchVia);
			viaHeaders.add(objViaHeader);
		} catch (Exception e) {
			logger.trace("exit  createViaHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createViaHeader");
		return viaHeaders;
	}

	/**
	 * Creates a new MaxForwardsHeader based on the newly supplied maxForwards value.
	 * @param maxForwards the new integer value of the maxForwards.
	 * @return the newly created MaxForwardsHeader object.
	 * @throws CreateRequestException if supplied maxForwards is less than zero or greater than 255.
	 */
	private MaxForwardsHeader createMaxForwardsHeader(Integer maxForwards) throws CreateRequestException {
		MaxForwardsHeader objMaxForwardsHeader;
		logger.trace("enter  createMaxForwardsHeader");
		try{
			objMaxForwardsHeader = objHeaderFactory.createMaxForwardsHeader(maxForwards);
		} catch (Exception e) {
			logger.trace("exit  createMaxForwardsHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createMaxForwardsHeader");
		return objMaxForwardsHeader;
	}

	/**
	 * Creates a new ContactHeader based on the newly supplied address value.
	 * @param user the new string value of the user, this value may be null.
	 * @param host the new string value of the host.
	 * @param port the new string value of the port.
	 * @return the newly created ContactHeader object.
	 * @throws CreateRequestException if the URI string is malformed.
	 */
	private ContactHeader createContactHeader(String userContact,String hostContact, Integer portContact) throws CreateRequestException{
		SipURI contactURI;
		Address contactAddress;
		ContactHeader objContactHeader;

		logger.trace("enter  createContactHeader");
		try{
		contactURI = objAddressFactory.createSipURI(userContact, hostContact);
		contactURI.setPort(portContact);
		contactAddress = objAddressFactory.createAddress(contactURI);
		objContactHeader = objHeaderFactory.createContactHeader(contactAddress);
		} catch (Exception e) {
			logger.trace("exit  createContactHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createContactHeader");
		return objContactHeader;
	}

	/**
	 * Creates a new UserAgentHeader based on the newly supplied List of product values.
	 * @param product the new List of values of the product.
	 * @return the newly created UserAgentHeader object.
	 * @throws CreateRequestException which signals that an error has been reached unexpectedly while parsing the List of product values.
	 */
	private UserAgentHeader createUserAgentHeader(String product) throws CreateRequestException{
		List<String> userAgents;
		UserAgentHeader objUserAgentHeader;

		logger.trace("enter  createUserAgentHeader");
		try{
		userAgents = new ArrayList<>();
		userAgents.add(product);
		objUserAgentHeader = objHeaderFactory.createUserAgentHeader(userAgents);
		} catch (Exception e) {
			logger.trace("exit  createUserAgentHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createUserAgentHeader");
		return objUserAgentHeader;
	}

	/**
	 * Creates a new ContentTypeHeader based on the newly supplied contentType and contentSubType values.
	 * @param contentType the new string content type value.
	 * @param contentSubType the new string content sub-type value.
	 * @return the newly created ContentTypeHeader object.
	 * @throws CreateRequestException which signals that an error has been reached unexpectedly while parsing the content type or content subtype value.
	 */
	private ContentTypeHeader createContentTypeHeader(String contentType, String contentSubType) throws CreateRequestException {
		ContentTypeHeader objContentTypeHeader;
		logger.trace("enter  createContentTypeHeader");
		try{
			objContentTypeHeader = objHeaderFactory.createContentTypeHeader(contentType, contentSubType);
		} catch (Exception e) {
			logger.trace("exit  createContentTypeHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createContentTypeHeader");
		return objContentTypeHeader;
	}

	/**
	 * Creates a new ExpiresHeader based on the newly supplied address value.
	 * @param expires the new integer value of the time expires.
	 * @throws CreateRequestException if the integer is invalid argument.
	 */
	private ExpiresHeader createExpireHeader(Integer expires) throws CreateRequestException{
		ExpiresHeader objExpiresHeader;
		logger.trace("enter  createExpireHeader");
		try{
			objExpiresHeader = objHeaderFactory.createExpiresHeader(expires);
		} catch (Exception e) {
			logger.trace("exit  createExpireHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createExpireHeader");
		return objExpiresHeader;
	}

	/**
	 * Creates a new AcceptHeader based on the newly supplied contentType and contentSubType values.
	 * @param contentType the new string content type value.
	 * @param contentSubType the new string content sub-type value.
	 * @return the newly created AcceptHeader object.
	 * @throws CreateRequestException which signals that an error has been reached unexpectedly while parsing the content type or content subtype value.
	 */
	private AcceptHeader createAcceptHeader(String contentType, String contentSubType) throws CreateRequestException{
		AcceptHeader objAcceptHeader;
		logger.trace("enter  createAcceptHeader");
		try{
			objAcceptHeader = objHeaderFactory.createAcceptHeader(contentType, contentSubType);
		} catch (Exception e) {
			logger.trace("exit  createAcceptHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createAcceptHeader");
		return objAcceptHeader;
	}

	/**
	 * Creates a new ReferToHeader based on the newly supplied address value.
	 * @param user the new string value of the user, this value may be null.
	 * @param host the new string value of the host.
	 * @param port the new string value of the port.
	 * @return the newly created ReferToHeader object.
	 * @throws CreateRequestException if the URI string is malformed.
	 */
	private ReferToHeader createReferToHeader(String user, String host,Integer port) throws CreateRequestException {
		ReferToHeader objReferToHeader;
		Address referToAddress;
		SipURI referToURI;

		logger.trace("enter  createReferToHeader");
		try{
		referToURI = objAddressFactory.createSipURI(user, host);
		if(port !=null)
			referToURI.setPort(port);
		referToAddress = objAddressFactory.createAddress(referToURI);
		objReferToHeader =  objHeaderFactory.createReferToHeader(referToAddress);
		} catch (ParseException e) {
			logger.trace("exit  createReferToHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createReferToHeader");
		return objReferToHeader;
	}

	/**
	 * Creates a new InfoPackage based on the newly supplied address value.
	 * @param user the new string value of the user, this value may be null.
	 * @param host the new string value of the host.
	 * @param port the new string value of the port.
	 * @return the newly created InfoPackage object.
	 * @throws CreateRequestException if the URI string is malformed.
	 */
	private Header createInfoPackageHeader(String info_Package) throws CreateRequestException {
		Header objInfoPackageHeader;

		logger.trace("enter  createInfoPackageHeader");
		try{
			objInfoPackageHeader =  objHeaderFactory.createHeader("Info-Package", info_Package);
		} catch (ParseException e) {
			logger.trace("exit  createInfoPackageHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createInfoPackageHeader");
		return objInfoPackageHeader;
	}

	/**
	 * Creates a new InfoPackage based on the newly supplied address value.
	 * @param user the new string value of the encoding, this value may be null.
	 * @return the newly created InfoPackage object.
	 * @throws CreateRequestException if the URI string is malformed.
	 */
	private ContentDispositionHeader createContentDispositionHeader(String encoding) throws CreateRequestException {
		ContentDispositionHeader objContentDispositioHeader;

		logger.trace("enter  createContentDispositionHeader");
		try{
			objContentDispositioHeader =  objHeaderFactory.createContentDispositionHeader(encoding);
		} catch (ParseException e) {
			logger.trace("exit  createContentDispositionHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createContentDispositionHeader");
		return objContentDispositioHeader;
	}

	/**
	 * Creates a new RecvInfo based on the newly supplied address value.
	 * @param recvInfo the new string value of the recvInfo, this value may be null.
	 * @throws CreateRequestException if the URI string is malformed.
	 */
	private  Header createRecvInfoHeader(String recvInfo) throws CreateRequestException {
		Header objRecvInfoHeader;

		logger.trace("enter  createRecvInfoHeader");
		try{
			objRecvInfoHeader =  objHeaderFactory.createHeader("RecvInfo", recvInfo);
		} catch (ParseException e) {
			logger.trace("exit  createRecvInfoHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createRecvInfoHeader");
		return objRecvInfoHeader;
	}

	/**
	 * Creates a new InfoPackage based on the newly supplied address value.
	 * @param user the new string value of the user, this value may be null.
	 * @param host the new string value of the host.
	 * @param port the new string value of the port.
	 * @return the newly created InfoPackage object.
	 * @throws CreateRequestException if the URI string is malformed.
	 */
	private EventHeader createEventHeader(String eventType) throws CreateRequestException {
		EventHeader objEventHeader;

		logger.trace("enter  createEventHeader");
		try{
			objEventHeader =  objHeaderFactory.createEventHeader(eventType);
		}catch (ParseException e) {
			logger.trace("exit  createEventHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createEventHeader");
		return objEventHeader;
	}

	/**
	 * Creates a new SubscriptionStateHeader based on the newly supplied subscriptionState value.
	 * @param subscriptionState the new string value of the subscriptionState.
	 * @return the newly created SubscriptionStateHeader object.
	 * @throws CreateRequestException which signals that an error has been reached unexpectedly while parsing the subscriptionState value.
	 */
	private SubscriptionStateHeader createSubscriptionStateHeader(String subscriptionState)throws CreateRequestException {
		SubscriptionStateHeader objSubscriptionStateHeader;

		logger.trace("enter  createSubscriptionStateHeader");
		try{
			objSubscriptionStateHeader =  objHeaderFactory.createSubscriptionStateHeader(subscriptionState);
		}catch (ParseException e) {
			logger.trace("exit  createSubscriptionStateHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createSubscriptionStateHeader");
		return objSubscriptionStateHeader;
	}

	/**
	 * Creates a new SIP-ETag header with the supplied tag value
	 * @param etag the new tag token
	 * @return the newly created SIP-ETag header
	 * @throws CreateRequestException when an error occurs during parsing of the etag parameter
	 */
	private SIPIfMatchHeader createSIPIfMatchHeader(String etag) throws CreateRequestException{
		SIPIfMatchHeader objSIPIfMatchHeader;

		logger.trace("enter  createSIPIfMatchHeader");
		try{
			objSIPIfMatchHeader =  objHeaderFactory.createSIPIfMatchHeader(etag);
		}catch (ParseException e) {
			logger.trace("exit  createSIPIfMatchHeader - Exception");
			throw new CreateRequestException(e.getMessage(),e);
		}
		logger.trace("exit  createSIPIfMatchHeader");
		return objSIPIfMatchHeader;
	}
}
