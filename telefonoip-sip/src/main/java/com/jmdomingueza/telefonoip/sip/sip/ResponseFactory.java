package com.jmdomingueza.telefonoip.sip.sip;

import javax.sdp.SessionDescription;
import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.header.ContactHeader;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.header.ProxyAuthenticateHeader;
import javax.sip.header.WWWAuthenticateHeader;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class ResponseFactory {

	/**
	 * Logger of the class
	 */
	private static Log logger = LogFactory.getLog(RequestFactory.class);

	/**
	 * AddressFactory for create directions SIP
	 */
	private static AddressFactory objAddressFactory;

	/**
	 * HeaderFactory  for  create heads SIP
	 */
	private  static HeaderFactory objHeaderFactory;

	/**
	 * MessageFactory for create message SIP
	 */
	private  static MessageFactory objMessageFactory;

	/**
	 * Path donde se encuentra las implementaciones de los objetos de la libreria jain-sip
	 */
	@Autowired
	private static String pathName;

	static{
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
	 * Creates a response BUSY_HERE of SIP.
	 * @param req Request received
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @return the newly created Response object.
	 * @throws CreateResponseException if the Response BUSY_HERE is malformed.
	 */
	public static Response createBusyHere(Request req,String userContact,String hostContact, Integer portContact)
			throws CreateResponseException {
		Response resp;
		ContactHeader contact;
		Integer method;

		logger.trace("exit  createBusyHere");
		try {
			method = Response.BUSY_HERE;
			resp = objMessageFactory.createResponse(method, req);

			contact = createContactHeader(userContact, hostContact, portContact);
			resp.addHeader(contact);
		}catch (CreateResponseException e) {
			logger.trace("exit  createBusyHere - CreateResponseException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createBusyHere -  - Exception");
			throw new CreateResponseException(e.getMessage(),e);
		}
		logger.debug("Respose: "+method);
		logger.debug("\r\n"+resp);

		logger.trace("exit  createBusyHere");
		return resp;
	}

	/**
	 * Creates a response PROXY_AUTHENTICATION_REQUIRED of SIP.
	 * @param req Request received
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @return the newly created Response object.
	 * @throws CreateResponseException if the Response PROXY_AUTHENTICATION_REQUIRED is malformed.
	 */
	public static Response createProxyAuthorizationRequired(Request req,String scheme,
            String realm, String qop,String nonce, String opaque,
            boolean stale,String algorithm)
			throws CreateResponseException {
		Response resp;
		Integer method;
		ProxyAuthenticateHeader proxyAuthenticateHeader;

		logger.trace("exit  createProxyAuthorizationRequired");
		try {
			method = Response.PROXY_AUTHENTICATION_REQUIRED;
			resp = objMessageFactory.createResponse(method, req);
			resp.setReasonPhrase("Proxy Authorization Required");

			proxyAuthenticateHeader = createProxyAuthenticateHeader(scheme, realm, qop, nonce, opaque, stale, algorithm);
			resp.addHeader(proxyAuthenticateHeader);
		}catch (CreateResponseException e) {
			logger.trace("exit  createProxyAuthorizationRequired - CreateResponseException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createProxyAuthorizationRequired -  - Exception");
			throw new CreateResponseException(e.getMessage(),e);
		}
		logger.debug("Respose: "+method);
		logger.debug("\r\n"+resp);

		logger.trace("exit  createProxyAuthorizationRequired");
		return resp;
	}

	/**
	 *
	 * @param req
	 * @param scheme
	 * @param realm
	 * @param qop
	 * @param nonce
	 * @param opaque
	 * @param stale
	 * @param algorithm
	 * @return
	 */
	public static Response createUnauthorized(Request req, String scheme, String realm, String qop, String nonce,
			String opaque, boolean stale, String algorithm) throws CreateResponseException {
		Response resp;
		Integer method;
		WWWAuthenticateHeader wwwAuthenticateHeader;

		logger.trace("exit  createUnauthorized");
		try {
			method = Response.UNAUTHORIZED;
			resp = objMessageFactory.createResponse(method, req);

			wwwAuthenticateHeader = createWWWAuthenticateHeader(scheme, realm, qop, nonce, opaque, stale, algorithm);
			resp.addHeader(wwwAuthenticateHeader);
		}catch (CreateResponseException e) {
			logger.trace("exit  createUnauthorized - CreateResponseException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createUnauthorized -  - Exception");
			throw new CreateResponseException(e.getMessage(),e);
		}
		logger.debug("Respose: "+method);
		logger.debug("\r\n"+resp);

		logger.trace("exit  createUnauthorized");
		return resp;
	}

	/**
	 * Creates a response RINGING of SIP.
	 * @param req Request received
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @param contentType the new string content type value.
	 * @param contentSubType the new string content sub-type value.
	 * @param objSessionDescription the new SessionDescription value of the sessionDescription.
	 * @return the newly created Response object.
	 * @throws CreateResponseException if the Response RINGING is malformed.
	 */
	public static Response createRinging(Request req, String userContact,String hostContact, Integer portContact,
								String contentType, String contentSubType,
								SessionDescription objSessionDescription)throws CreateResponseException {
		Response resp;
		ContactHeader contactHeader;
		ContentTypeHeader contentTypeHeader;
		Integer method;

		logger.trace("exit  createRinging");
		try {
			method = Response.RINGING;
			resp = objMessageFactory.createResponse(method, req);

			contactHeader = createContactHeader(userContact, hostContact, portContact);
			contentTypeHeader = createContentTypeHeader(contentType,contentSubType);
			resp.addHeader(contactHeader);
			resp.addHeader(contentTypeHeader);

			if(objSessionDescription!=null)
				resp.setContent(objSessionDescription, contentTypeHeader);
		}catch (CreateResponseException e) {
			logger.trace("exit  createRinging - CreateResponseException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createRinging -  - Exception");
			throw new CreateResponseException(e.getMessage(),e);
		}
		logger.debug("Respose: "+method);
		logger.debug("\r\n"+resp);
		logger.trace("exit  createRinging");
		return resp;
	}

	/**
	 * Creates a response TRYING of SIP.
	 * @param req Request received
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @return the newly created Response object.
	 * @throws CreateResponseException if the Response TRYING is malformed.
	 */
	public static Response createTrying(Request req,String userContact,String hostContact, Integer portContact)
			throws CreateResponseException{
		Response resp;
		ContactHeader contactHeader;
		Integer method;
		logger.trace("exit  createTrying");

		try {
			method = Response.TRYING;
			resp = objMessageFactory.createResponse(method, req);

			contactHeader = createContactHeader(userContact, hostContact, portContact);
			resp.addHeader(contactHeader);
		}catch (CreateResponseException e) {
			logger.trace("exit  createTrying - CreateResponseException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createTrying -  - Exception");
			throw new CreateResponseException(e.getMessage(),e);
		}
		logger.trace("Respose: "+method);
		logger.trace("\r\n"+resp);
		logger.trace("exit  createTrying");
		return resp;

	}

	/**
	 * Creates a response OK of SIP.
	 * @param req Request received
	 * @param userContact the new string value of the user, this value may be null.
	 * @param hostContact the new string value of the host.
	 * @param portContact the new string value of the port.
	 * @param contentType the new string content type value.
	 * @param contentSubType the new string content sub-type value.
	 * @param objSessionDescription the new SessionDescription value of the sessionDescription.
	 * @return the newly created Response object.
	 * @throws CreateResponseException if the Response OK is malformed.
	 */
	public static Response createOk(Request req, String userContact,String hostContact, Integer portContact,
								   String contentType, String contentSubType,
								   SessionDescription objSessionDescription) throws CreateResponseException {
		Response resp;
		ContactHeader contactHeader;
		ContentTypeHeader contentTypeHeader;
		Integer method;

		logger.trace("exit  createOk");
		try {
			method = Response.OK;
			resp = objMessageFactory.createResponse(method, req);

			contactHeader = createContactHeader(userContact,hostContact,portContact);
			contentTypeHeader = createContentTypeHeader(contentType,contentSubType);
			resp.addHeader(contactHeader);
			resp.addHeader(contentTypeHeader);

			if(objSessionDescription!=null)
				resp.setContent(objSessionDescription, contentTypeHeader);
		}catch (CreateResponseException e) {
			logger.trace("exit createOk - CreateResponseException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit createOk -  - Exception");
			throw new CreateResponseException(e.getMessage(),e);
		}
		logger.trace("Respose: "+method);
		logger.trace("\r\n"+resp);
		logger.trace("exit  createOk");
		return resp;
	}

	public static Response createNotAcceptableHere(Request req, String userContact,String hostContact, Integer portContact)
			throws CreateResponseException {
		Response resp;
		ContactHeader contact;
		Integer method;

		logger.trace("exit  createNotAcceptableHere");
		try {
			method = Response.NOT_ACCEPTABLE_HERE;
			resp = objMessageFactory.createResponse(method, req);

			contact = createContactHeader(userContact, hostContact, portContact);
			resp.addHeader(contact);
		}catch (CreateResponseException e) {
			logger.trace("exit  createNotAcceptableHere - CreateResponseException");
			throw e;
		}catch (Exception e) {
			logger.trace("exit  createNotAcceptableHere -  - Exception");
			throw new CreateResponseException(e.getMessage(),e);
		}
		logger.debug("Respose: "+method);
		logger.debug("\r\n"+resp);

		logger.trace("exit  createNotAcceptableHere");
		return resp;
	}

	/**
	 * Creates a new ContactHeader based on the newly supplied address value.
	 * @param user the new string value of the user, this value may be null.
	 * @param host the new string value of the host.
	 * @param port the new string value of the port.
	 * @return the newly created ContactHeader object.
	 * @throws CreateResponseException if the URI string is malformed.
	 */
	private static ContactHeader createContactHeader(String user,String host, Integer port) throws CreateResponseException{
		SipURI contactURI;
		Address contactAddress;
		ContactHeader objContactHeader;

		logger.trace("enter  createContactHeader");
		try{
		contactURI = objAddressFactory.createSipURI(user, host);
		contactURI.setPort(port);
		contactAddress = objAddressFactory.createAddress(contactURI);
		objContactHeader = objHeaderFactory.createContactHeader(contactAddress);
		} catch (Exception e) {
			logger.trace("exit  createContactHeader - Exception");
			throw new CreateResponseException(e.getMessage(),e);
		}
		logger.trace("exit  createContactHeader");
		return objContactHeader;
	}

	/**
	 * Creates a new ContentTypeHeader based on the newly supplied contentType and contentSubType values.
	 * @param contentType the new string content type value.
	 * @param contentSubType the new string content sub-type value.
	 * @return the newly created ContentTypeHeader object.
	 * @throws CreateResponseException which signals that an error has been reached unexpectedly while parsing the content type or content subtype value.
	 */
	private static ContentTypeHeader createContentTypeHeader(String contentType, String contentSubType) throws CreateResponseException {
		ContentTypeHeader objContentTypeHeader;
		logger.trace("enter  createContentTypeHeader");
		try{
			objContentTypeHeader = objHeaderFactory.createContentTypeHeader(contentType, contentSubType);
		} catch (Exception e) {
			logger.trace("exit  createContentTypeHeader - Exception");
			throw new CreateResponseException(e.getMessage(),e);
		}
		logger.trace("exit  createContentTypeHeader");
		return objContentTypeHeader;
	}

	/**
	 * Creates a new ProxyAuthorizationHeader based on the newly supplied scheme value.
	 * @param scheme the new string value of the scheme.
	 * @return the newly created ProxyAuthorizationHeader object.
	 * @throws CreateResponseException which signals that an error has been reached unexpectedly while parsing the content type or content subtype value.
	 */
	private static ProxyAuthenticateHeader createProxyAuthenticateHeader(String scheme,
            String realm, String qop,String nonce, String opaque,
            boolean stale,String algorithm)throws CreateResponseException {
		ProxyAuthenticateHeader ojbProxyAuthenticateHeader;
		logger.trace("enter  createProxyAuthenticateHeader");
		try{
			ojbProxyAuthenticateHeader = objHeaderFactory.createProxyAuthenticateHeader(scheme);
			ojbProxyAuthenticateHeader.setRealm(realm);
			ojbProxyAuthenticateHeader.setQop(qop);
			ojbProxyAuthenticateHeader.setNonce(nonce);
			ojbProxyAuthenticateHeader.setOpaque(opaque);
			ojbProxyAuthenticateHeader.setStale(stale);
			ojbProxyAuthenticateHeader.setAlgorithm(algorithm);
		} catch (Exception e) {
			logger.trace("exit  createProxyAuthenticateHeader - Exception");
			throw new CreateResponseException(e.getMessage(),e);
		}
		logger.trace("exit  createProxyAuthenticateHeader");
		return ojbProxyAuthenticateHeader;
	}

	/**
	 * Creates a new ProxyAuthorizationHeader based on the newly supplied scheme value.
	 * @param scheme the new string value of the scheme.
	 * @return the newly created ProxyAuthorizationHeader object.
	 * @throws CreateResponseException which signals that an error has been reached unexpectedly while parsing the content type or content subtype value.
	 */
	private static WWWAuthenticateHeader createWWWAuthenticateHeader(String scheme,
            String realm, String qop,String nonce, String opaque,
            boolean stale,String algorithm)throws CreateResponseException {
		WWWAuthenticateHeader ojbWWWAuthenticateHeader;
		logger.trace("enter  createWWWAuthenticateHeader");
		try{
			ojbWWWAuthenticateHeader = objHeaderFactory.createWWWAuthenticateHeader(scheme);
			ojbWWWAuthenticateHeader.setRealm(realm);
			ojbWWWAuthenticateHeader.setQop(qop);
			ojbWWWAuthenticateHeader.setNonce(nonce);
			ojbWWWAuthenticateHeader.setOpaque(opaque);
			ojbWWWAuthenticateHeader.setStale(stale);
			ojbWWWAuthenticateHeader.setAlgorithm(algorithm);
		} catch (Exception e) {
			logger.trace("exit  createWWWAuthenticateHeader - Exception");
			throw new CreateResponseException(e.getMessage(),e);
		}
		logger.trace("exit  createWWWAuthenticateHeader");
		return ojbWWWAuthenticateHeader;
	}


}
