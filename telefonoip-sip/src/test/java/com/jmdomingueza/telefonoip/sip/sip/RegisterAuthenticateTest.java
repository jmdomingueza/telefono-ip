package com.jmdomingueza.telefonoip.sip.sip;

import java.text.ParseException;

import javax.sip.header.ToHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jmdomingueza.telefonoip.sip.sip.CreateRequestException;
import com.jmdomingueza.telefonoip.sip.sip.CreateResponseException;
import com.jmdomingueza.telefonoip.sip.sip.RequestFactory;
import com.jmdomingueza.telefonoip.sip.sip.ResponseFactory;

/**
 * Unit test for RegisterAuthenticate.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class RegisterAuthenticateTest {

	@Autowired 
	protected RequestFactory requestFactory;
	
	@Test
	public void registerOk() throws CreateRequestException, CreateResponseException {

		Request req,reqAuthenticate;
		Response resp;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact, user, password,scheme;
		Long cSeq;
		Integer portVia,maxForwards,portContact,expires;
		String realm,  qop, nonce,  opaque,algorithm;
        boolean stale;
        String requestExpect,responseExpect,requestAuthenticateExpect;


		uri = "sip:atlanta.com";
		callID = "843817637684230@998sdasdh09";
		cSeq = 1826L;
		userFrom = "alice";
		hostFrom = "atlanta.com";
		displayFrom = "Alice";
		tagFrom = "1928301774";
		userTo = "alice";
		hostTo = "atlanta.com";
		displayTo ="Alice";
		tagTo = null;
		hostVia = "client.atlanta.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bKnashds7";
		maxForwards = 70;
		userContact="alice";
		hostContact="client.atlanta.com";
		portContact=5060;
		expires=7200;

		req = requestFactory.createRegister(uri, callID, cSeq, userFrom, hostFrom, displayFrom, tagFrom,
											userTo, hostTo, displayTo, tagTo,
											hostVia, portVia, transportVia, branchVia,
											maxForwards,
											userContact, hostContact, portContact,
											expires);

		requestExpect = "REGISTER sip:atlanta.com SIP/2.0\r\n"
				+ "Call-ID: 843817637684230@998sdasdh09\r\n"
				+ "CSeq: 1826 REGISTER\r\n"
				+ "From: \"Alice\" <sip:alice@atlanta.com>;tag=1928301774\r\n"
				+ "To: \"Alice\" <sip:alice@atlanta.com>\r\n"
				+ "Via: SIP/2.0/UDP client.atlanta.com:5060;branch=z9hG4bKnashds7\r\n"
				+ "Max-Forwards: 70\r\n"
				+ "Contact: <sip:alice@client.atlanta.com:5060>\r\n"
				+ "Expires: 7200\r\n"
				+ "Content-Length: 0\r\n" + "\r\n";
		Assert.assertEquals(requestExpect.toString(), req.toString());

		scheme = "Digest";
		realm = "atlanta.example.com";
		qop = "auth";
		nonce= "ea9c8e88df84f1cec4341ae6cbe5a359";
		opaque= "";
		stale = false;
		algorithm ="MD5";
		ToHeader to = (ToHeader) req.getHeader(ToHeader.NAME);
		ViaHeader via = (ViaHeader) req.getHeader(ViaHeader.NAME);
		try {
			to.setTag("1410948204");
			via.setBranch("z9hG4bKnashds7");
			via.setReceived("192.0.2.201");
		} catch (ParseException e) {

		}
		resp = ResponseFactory.createUnauthorized(req, scheme, realm, qop, nonce,  opaque,stale, algorithm);

		responseExpect ="SIP/2.0 401 Unauthorized\r\n"
				+"Call-ID: 843817637684230@998sdasdh09\r\n"
				+"CSeq: 1826 REGISTER\r\n"
				+ "From: \"Alice\" <sip:alice@atlanta.com>;tag=1928301774\r\n"
				+ "To: \"Alice\" <sip:alice@atlanta.com>;tag=1410948204\r\n"
				+"Via: SIP/2.0/UDP client.atlanta.com:5060;branch=z9hG4bKnashds7;received=192.0.2.201\r\n"
				+"WWW-Authenticate: Digest nonce=\"ea9c8e88df84f1cec4341ae6cbe5a359\",qop=\"auth\",realm=\"atlanta.example.com\",opaque=\"\",stale=false,algorithm=MD5\r\n"
				+"Content-Length: 0\r\n"+ "\r\n";

		Assert.assertEquals(responseExpect.toString(), resp.toString());

		user = "alice";
		password = "";

		reqAuthenticate = requestFactory.createRegisterAuthenticate(req, resp, hostVia, portVia, transportVia, branchVia, user, password);


		requestAuthenticateExpect = "REGISTER sip:atlanta.com SIP/2.0\r\n"
				+"Call-ID: 843817637684230@998sdasdh09\r\n"
				+"From: \"Alice\" <sip:alice@atlanta.com>;tag=1928301774\r\n"
				+"To: \"Alice\" <sip:alice@atlanta.com>;tag=1410948204\r\n"
				+"Max-Forwards: 70\r\n"
				+"Contact: <sip:alice@client.atlanta.com:5060>\r\n"
				+"Expires: 7200\r\n"
				+"Authorization: Digest response=\"07c53e37a1f928ee55f7e87b8f648ece\",username=\"alice\",qop=auth,nonce=\"ea9c8e88df84f1cec4341ae6cbe5a359\",realm=\"atlanta.example.com\",opaque=\"\",uri=\"sip:alice@atlanta.com\",algorithm=MD5\r\n"
				+"CSeq: 1827 REGISTER\r\n"
				+"Via: SIP/2.0/UDP client.atlanta.com:5060;branch=z9hG4bKnashds7\r\n"
				+"Content-Length: 0\r\n"+ "\r\n";


		 Assert.assertEquals(requestAuthenticateExpect.toString(), reqAuthenticate.toString());

	}

	@Test(expected = CreateRequestException.class)
	public void registerCreateRequestException() throws CreateRequestException, CreateResponseException {

		Request req;
		Response resp;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact, user, password,scheme;
		Long cSeq;
		Integer portVia,maxForwards,portContact,expires;
		String realm,  qop, nonce,  opaque,algorithm;
        boolean stale;
        String requestExpect,responseExpect;


		uri = "sip:atlanta.com";
		callID = "843817637684230@998sdasdh09";
		cSeq = 1826L;
		userFrom = "alice";
		hostFrom = "atlanta.com";
		displayFrom = "Alice";
		tagFrom = "1928301774";
		userTo = "alice";
		hostTo = "atlanta.com";
		displayTo ="Alice";
		tagTo = null;
		hostVia = "client.atlanta.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bKnashds7";
		maxForwards = 70;
		userContact="alice";
		hostContact="client.atlanta.com";
		portContact=5060;
		expires=7200;

		req = requestFactory.createRegister(uri, callID, cSeq, userFrom, hostFrom, displayFrom, tagFrom,
											userTo, hostTo, displayTo, tagTo,
											hostVia, portVia, transportVia, branchVia,
											maxForwards,
											userContact, hostContact, portContact,
											expires);

		requestExpect = "REGISTER sip:atlanta.com SIP/2.0\r\n"
				+ "Call-ID: 843817637684230@998sdasdh09\r\n"
				+ "CSeq: 1826 REGISTER\r\n"
				+ "From: \"Alice\" <sip:alice@atlanta.com>;tag=1928301774\r\n"
				+ "To: \"Alice\" <sip:alice@atlanta.com>\r\n"
				+ "Via: SIP/2.0/UDP client.atlanta.com:5060;branch=z9hG4bKnashds7\r\n"
				+ "Max-Forwards: 70\r\n"
				+ "Contact: <sip:alice@client.atlanta.com:5060>\r\n"
				+ "Expires: 7200\r\n"
				+ "Content-Length: 0\r\n" + "\r\n";
		Assert.assertEquals(requestExpect.toString(), req.toString());

		scheme = "Digest";
		realm = "atlanta.example.com";
		qop = "auth";
		nonce= "ea9c8e88df84f1cec4341ae6cbe5a359";
		opaque= "";
		stale = false;
		algorithm ="MD5";
		ToHeader to = (ToHeader) req.getHeader(ToHeader.NAME);
		ViaHeader via = (ViaHeader) req.getHeader(ViaHeader.NAME);
		try {
			to.setTag("1410948204");
			via.setBranch("z9hG4bKnashds7");
			via.setReceived("192.0.2.201");
		} catch (ParseException e) {

		}
		resp = ResponseFactory.createUnauthorized(req, scheme, realm, qop, nonce,  opaque,stale, algorithm);

		responseExpect ="SIP/2.0 401 Unauthorized\r\n"
				+"Call-ID: 843817637684230@998sdasdh09\r\n"
				+"CSeq: 1826 REGISTER\r\n"
				+ "From: \"Alice\" <sip:alice@atlanta.com>;tag=1928301774\r\n"
				+ "To: \"Alice\" <sip:alice@atlanta.com>;tag=1410948204\r\n"
				+"Via: SIP/2.0/UDP client.atlanta.com:5060;branch=z9hG4bKnashds7;received=192.0.2.201\r\n"
				+"WWW-Authenticate: Digest nonce=\"ea9c8e88df84f1cec4341ae6cbe5a359\",qop=\"auth\",realm=\"atlanta.example.com\",opaque=\"\",stale=false,algorithm=MD5\r\n"
				+"Content-Length: 0\r\n"+ "\r\n";

		Assert.assertEquals(responseExpect.toString(), resp.toString());

		user = "alice";
		password = "";
		branchVia = "";

		requestFactory.createRegisterAuthenticate(req, resp, hostVia, portVia, transportVia, branchVia, user, password);
	}
}
