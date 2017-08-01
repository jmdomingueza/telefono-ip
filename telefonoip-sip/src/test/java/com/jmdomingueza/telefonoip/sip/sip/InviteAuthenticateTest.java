package com.jmdomingueza.telefonoip.sip.sip;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.sdp.SessionDescription;
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

import com.jmdomingueza.telefonoip.sip.sdp.CreateSessionDescriptionException;
import com.jmdomingueza.telefonoip.sip.sdp.SessionDescriptionFactory;
import com.jmdomingueza.telefonoip.sip.sip.CreateRequestException;
import com.jmdomingueza.telefonoip.sip.sip.CreateResponseException;
import com.jmdomingueza.telefonoip.sip.sip.RequestFactory;
import com.jmdomingueza.telefonoip.sip.sip.ResponseFactory;
import com.jmdomingueza.telefonoip.sip.utils.SDPConstants;

/**
 * Unit test for InviteAuthenticateTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class InviteAuthenticateTest {

	@Autowired 
	protected RequestFactory requestFactory;
	
	@Test
	public void inviteWihoutSessionDescription() throws CreateRequestException, CreateResponseException, CreateSessionDescriptionException {
		Request req,reqAuthenticate;
		Response resp;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact,product,contentType,contentSubType, user, password,scheme;
		Long cSeq;
		Integer portVia,maxForwards,portContact;
		SessionDescription objSessionDescription;
		Long sessionId,sessionVersion;
		String ipSessionDescription;
		Integer portSessionDescription, mode;
		List<Integer> audioCodecs;
		String realm,  qop, nonce,  opaque,algorithm;
        boolean stale;
        String requestExpect,SDExpect,responseExpect,requestAuthenticateExpect,SDAuthenticateExpect;

		uri = "sip:bob@biloxi.example.com";
		callID = "2xTb9vxSit55XU7p8@atlanta.example.com";
		cSeq = 1L;
		userFrom = "alice";
		hostFrom = "atlanta.example.com";
		displayFrom = "Alice";
		tagFrom = "9fxced76sl";
		userTo = "bob";
		hostTo = "biloxi.example.com";
		displayTo ="Bob";
		tagTo = null;
		hostVia = "client.atlanta.example.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bK74b03";
		maxForwards = 70;
		userContact = "alice";
		hostContact = "client.atlanta.example.com";
		portContact =5060;
		product = "Telephone IP";
		contentType = "application";
		contentSubType = "sdp";

		sessionId = 11111L;
		sessionVersion =11111L;
		ipSessionDescription = "client.atlanta.com";
		audioCodecs = new ArrayList<Integer>();
		audioCodecs.add(SDPConstants.PCMA);
		audioCodecs.add(SDPConstants.TE98);
		audioCodecs.add(SDPConstants.TE101);
		portSessionDescription = 12000;
		mode = SDPConstants.NONE;
		objSessionDescription = SessionDescriptionFactory.createSessionDescription(sessionId, sessionVersion, ipSessionDescription, audioCodecs, portSessionDescription, mode);

		req = requestFactory.createInvite(uri, callID, cSeq,
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo,
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards,
											  userContact, hostContact, portContact,
											  product,
											  contentType, contentSubType,
											  objSessionDescription);


		requestExpect = "INVITE sip:bob@biloxi.example.com SIP/2.0\r\n"
				 +"Call-ID: 2xTb9vxSit55XU7p8@atlanta.example.com\r\n"
				 +"CSeq: 1 INVITE\r\n"
				 +"From: \"Alice\" <sip:alice@atlanta.example.com>;tag=9fxced76sl\r\n"
				 +"To: \"Bob\" <sip:bob@biloxi.example.com>\r\n"
				 +"Via: SIP/2.0/UDP client.atlanta.example.com:5060;branch=z9hG4bK74b03\r\n"
				 +"Max-Forwards: 70\r\n"
				 +"Contact: <sip:alice@client.atlanta.example.com:5060>\r\n"
				 + "User-Agent: Telephone IP\r\n"
				 +"Content-Type: application/sdp\r\n"
				 +"Content-Length: 261\r\n" + "\r\n";

		SDExpect = "v=0\r\n"
				+ "o=- 11111 11111 IN IP4 client.atlanta.com\r\n"
				+ "s=SIPTester\r\n"
				+ "c=IN IP4 client.atlanta.com\r\n"
				+ "t=0 0\r\n"
				+ "m=audio 12000 RTP/AVP 8 98 101\r\n"
				+ "a=rtpmap:8 PCMA/8000\r\n"
				+ "a=rtpmap:98 telephone-event/8000\r\n"
				+ "a=FMTP:98 0-15\r\n"
				+ "a=rtpmap:101 telephone-event/8000\r\n"
				+ "a=FMTP:101 0-15\r\n"
				+ "a=none\r\n";

		requestExpect+= SDExpect;
		Assert.assertEquals(requestExpect.toString(), req.toString());

		scheme = "Digest";
		realm = "atlanta.example.com";
		qop = "auth";
		nonce= "wf84f1cczx41ae6cbeaea9ce88d359";
		opaque= "";
		stale = false;
		algorithm ="MD5";
		ToHeader to = (ToHeader) req.getHeader(ToHeader.NAME);
		ViaHeader via = (ViaHeader) req.getHeader(ViaHeader.NAME);
		try {
			to.setTag("876321");
			via.setReceived("192.0.2.101");
		} catch (ParseException e) {

		}
		resp = ResponseFactory.createProxyAuthorizationRequired(req, scheme, realm, qop, nonce,  opaque,stale, algorithm);

		responseExpect ="SIP/2.0 407 Proxy Authorization Required\r\n"
				+"Call-ID: 2xTb9vxSit55XU7p8@atlanta.example.com\r\n"
	    		+"CSeq: 1 INVITE\r\n"
	    		+"From: \"Alice\" <sip:alice@atlanta.example.com>;tag=9fxced76sl\r\n"
	    		+"To: \"Bob\" <sip:bob@biloxi.example.com>;tag=876321\r\n"
	    		+"Via: SIP/2.0/UDP client.atlanta.example.com:5060;branch=z9hG4bK74b03;received=192.0.2.101\r\n"
	    		+"Proxy-Authenticate: Digest nonce=\"wf84f1cczx41ae6cbeaea9ce88d359\",qop=\"auth\",realm=\"atlanta.example.com\",opaque=\"\",stale=false,algorithm=MD5\r\n"
		    	+"Content-Length: 0\r\n"+ "\r\n";

		Assert.assertEquals(responseExpect.toString(), resp.toString());

		user = "alice";
		password = "";

		reqAuthenticate = requestFactory.createInviteAuthenticate(req, resp, hostVia, portVia, transportVia, branchVia, user, password);


		requestAuthenticateExpect = "INVITE sip:bob@biloxi.example.com SIP/2.0\r\n"
				+"Call-ID: 2xTb9vxSit55XU7p8@atlanta.example.com\r\n"
				+"From: \"Alice\" <sip:alice@atlanta.example.com>;tag=9fxced76sl\r\n"
			    +"To: \"Bob\" <sip:bob@biloxi.example.com>;tag=876321\r\n"
				+"Max-Forwards: 70\r\n"
				+"Contact: <sip:alice@client.atlanta.example.com:5060>\r\n"
			    +"User-Agent: Telephone IP\r\n"
			    +"Content-Type: application/sdp\r\n"
				+"CSeq: 2 INVITE\r\n"
				+"Via: SIP/2.0/UDP client.atlanta.example.com:5060;branch=z9hG4bK74b03\r\n"
				+"Proxy-Authorization: Digest response=\"8f06573ffce40335a873e3c0caf1ae21\",username=\"alice\",qop=auth,nonce=\"wf84f1cczx41ae6cbeaea9ce88d359\",realm=\"atlanta.example.com\",opaque=\"\",uri=\"sip:bob@biloxi.example.com\",algorithm=MD5\r\n"
				+"Content-Length: 261\r\n"+ "\r\n";

		 SDAuthenticateExpect = "v=0\r\n"
				+ "o=- 11111 11111 IN IP4 client.atlanta.com\r\n"
				+ "s=SIPTester\r\n"
				+ "c=IN IP4 client.atlanta.com\r\n"
				+ "t=0 0\r\n"
				+ "m=audio 12000 RTP/AVP 8 98 101\r\n"
				+ "a=rtpmap:8 PCMA/8000\r\n"
				+ "a=rtpmap:98 telephone-event/8000\r\n"
				+ "a=FMTP:98 0-15\r\n"
				+ "a=rtpmap:101 telephone-event/8000\r\n"
				+ "a=FMTP:101 0-15\r\n"
				+ "a=none\r\n";
		 requestAuthenticateExpect+= SDAuthenticateExpect;
		 Assert.assertEquals(requestAuthenticateExpect.toString(), reqAuthenticate.toString());

	}

	@Test(expected = CreateRequestException.class)
	public void inviteCreateRequestException() throws CreateRequestException, CreateSessionDescriptionException, CreateResponseException {
		Request req;
		Response resp;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact,product,contentType,contentSubType, user, password,scheme;
		Long cSeq;
		Integer portVia,maxForwards,portContact;
		SessionDescription objSessionDescription;
		Long sessionId,sessionVersion;
		String ipSessionDescription;
		Integer portSessionDescription, mode;
		List<Integer> audioCodecs;
		String realm,  qop, nonce,  opaque,algorithm;
        boolean stale;
        String requestExpect,SDExpect,responseExpect;

		uri = "sip:bob@biloxi.example.com";
		callID = "2xTb9vxSit55XU7p8@atlanta.example.com";
		cSeq = 1L;
		userFrom = "alice";
		hostFrom = "atlanta.example.com";
		displayFrom = "Alice";
		tagFrom = "9fxced76sl";
		userTo = "bob";
		hostTo = "biloxi.example.com";
		displayTo ="Bob";
		tagTo = null;
		hostVia = "client.atlanta.example.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bK74b03";
		maxForwards = 70;
		userContact = "alice";
		hostContact = "client.atlanta.example.com";
		portContact =5060;
		product = "Telephone IP";
		contentType = "application";
		contentSubType = "sdp";

		sessionId = 11111L;
		sessionVersion =11111L;
		ipSessionDescription = "client.atlanta.com";
		audioCodecs = new ArrayList<Integer>();
		audioCodecs.add(SDPConstants.PCMA);
		audioCodecs.add(SDPConstants.TE98);
		audioCodecs.add(SDPConstants.TE101);
		portSessionDescription = 12000;
		mode = SDPConstants.NONE;
		objSessionDescription = SessionDescriptionFactory.createSessionDescription(sessionId, sessionVersion, ipSessionDescription, audioCodecs, portSessionDescription, mode);

		req = requestFactory.createInvite(uri, callID, cSeq,
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo,
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards,
											  userContact, hostContact, portContact,
											  product,
											  contentType, contentSubType,
											  objSessionDescription);


		requestExpect = "INVITE sip:bob@biloxi.example.com SIP/2.0\r\n"
				 +"Call-ID: 2xTb9vxSit55XU7p8@atlanta.example.com\r\n"
				 +"CSeq: 1 INVITE\r\n"
				 +"From: \"Alice\" <sip:alice@atlanta.example.com>;tag=9fxced76sl\r\n"
				 +"To: \"Bob\" <sip:bob@biloxi.example.com>\r\n"
				 +"Via: SIP/2.0/UDP client.atlanta.example.com:5060;branch=z9hG4bK74b03\r\n"
				 +"Max-Forwards: 70\r\n"
				 +"Contact: <sip:alice@client.atlanta.example.com:5060>\r\n"
				 + "User-Agent: Telephone IP\r\n"
				 +"Content-Type: application/sdp\r\n"
				 +"Content-Length: 261\r\n" + "\r\n";

		SDExpect = "v=0\r\n"
				+ "o=- 11111 11111 IN IP4 client.atlanta.com\r\n"
				+ "s=SIPTester\r\n"
				+ "c=IN IP4 client.atlanta.com\r\n"
				+ "t=0 0\r\n"
				+ "m=audio 12000 RTP/AVP 8 98 101\r\n"
				+ "a=rtpmap:8 PCMA/8000\r\n"
				+ "a=rtpmap:98 telephone-event/8000\r\n"
				+ "a=FMTP:98 0-15\r\n"
				+ "a=rtpmap:101 telephone-event/8000\r\n"
				+ "a=FMTP:101 0-15\r\n"
				+ "a=none\r\n";

		requestExpect+= SDExpect;
		Assert.assertEquals(requestExpect.toString(), req.toString());

		scheme = "Digest";
		realm = "atlanta.example.com";
		qop = "auth";
		nonce= "wf84f1cczx41ae6cbeaea9ce88d359";
		opaque= "";
		stale = false;
		algorithm ="MD5";
		ToHeader to = (ToHeader) req.getHeader(ToHeader.NAME);
		ViaHeader via = (ViaHeader) req.getHeader(ViaHeader.NAME);
		try {
			to.setTag("876321");
			via.setReceived("192.0.2.101");
		} catch (ParseException e) {

		}
		resp = ResponseFactory.createProxyAuthorizationRequired(req, scheme, realm, qop, nonce,  opaque,stale, algorithm);

		responseExpect ="SIP/2.0 407 Proxy Authorization Required\r\n"
				+"Call-ID: 2xTb9vxSit55XU7p8@atlanta.example.com\r\n"
	    		+"CSeq: 1 INVITE\r\n"
	    		+"From: \"Alice\" <sip:alice@atlanta.example.com>;tag=9fxced76sl\r\n"
	    		+"To: \"Bob\" <sip:bob@biloxi.example.com>;tag=876321\r\n"
	    		+"Via: SIP/2.0/UDP client.atlanta.example.com:5060;branch=z9hG4bK74b03;received=192.0.2.101\r\n"
	    		+"Proxy-Authenticate: Digest nonce=\"wf84f1cczx41ae6cbeaea9ce88d359\",qop=\"auth\",realm=\"atlanta.example.com\",opaque=\"\",stale=false,algorithm=MD5\r\n"
		    	+"Content-Length: 0\r\n"+ "\r\n";

		Assert.assertEquals(responseExpect.toString(), resp.toString());

		user = "alice";
		password = "";
		branchVia = "";
		requestFactory.createInviteAuthenticate(req, resp, hostVia, portVia, transportVia, branchVia, user, password);


	}
}
