package com.jmdomingueza.telefonoip.sip.sip;

import java.util.ArrayList;
import java.util.List;

import javax.sdp.SessionDescription;
import javax.sip.message.Request;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jmdomingueza.telefonoip.sip.sdp.CreateSessionDescriptionException;
import com.jmdomingueza.telefonoip.sip.sdp.SessionDescriptionFactory;
import com.jmdomingueza.telefonoip.sip.sip.CreateRequestException;
import com.jmdomingueza.telefonoip.sip.sip.RequestFactory;
import com.jmdomingueza.telefonoip.sip.utils.SDPConstants;

/**
 * Unit test for InviteTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class InviteTest {

	@Autowired 
	protected RequestFactory requestFactory;
	
	@Test
	public void inviteWihoutSessionDescription() throws CreateRequestException {
		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact,product,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,portContact;
		
		uri = "sip:bob@biloxi.com";
		callID = "a84b4c76e66710@client.atlanta.com";
		cSeq = 1L;
		userFrom = "alice";
		hostFrom = "atlanta.com";
		displayFrom = "Alice";
		tagFrom = "1928301774";
		userTo = "bob";
		hostTo = "biloxi.com";
		displayTo ="Bob";
		tagTo = null;
		hostVia = "client.atlanta.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bK776asdhds";
		maxForwards = 70;
		userContact = "alice";
		hostContact = "client.atlanta.com";
		portContact =5060;
		product = "Telephone IP";
		contentType = "application";
		contentSubType = "sdp";
		
		req = requestFactory.createInvite(uri, callID, cSeq,  
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo, 
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards, 
											  userContact, hostContact, portContact, 
											  product, 
											  contentType, contentSubType, 
											  null);

		String requestExpect = "INVITE sip:bob@biloxi.com SIP/2.0\r\n"
				+ "Call-ID: a84b4c76e66710@client.atlanta.com\r\n"
				+ "CSeq: 1 INVITE\r\n"
				+ "From: \"Alice\" <sip:alice@atlanta.com>;tag=1928301774\r\n"
				+ "To: \"Bob\" <sip:bob@biloxi.com>\r\n"
				+ "Via: SIP/2.0/UDP client.atlanta.com:5060;branch=z9hG4bK776asdhds\r\n"
				+ "Max-Forwards: 70\r\n"
				+ "Contact: <sip:alice@client.atlanta.com:5060>\r\n"
				+ "User-Agent: Telephone IP\r\n"
				+ "Content-Type: application/sdp\r\n"
				+ "Content-Length: 0\r\n" + "\r\n";
		Assert.assertEquals(requestExpect.toString(), req.toString());
	}
	
	@Test
	public void inviteWithSessionDescription() throws CreateRequestException, CreateSessionDescriptionException {
		
		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact,product,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,portContact;
		SessionDescription objSessionDescription;
		Long sessionId,sessionVersion;
		String ipSessionDescription;
		Integer portSessionDescription, mode;
		List<Integer> audioCodecs;
		
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
		
		uri = "sip:bob@biloxi.com";
		callID = "a84b4c76e66710@client.atlanta.com";
		cSeq = 1L;
		userFrom = "alice";
		hostFrom = "atlanta.com";
		displayFrom = "Alice";
		tagFrom = "1928301774";
		userTo = "bob";
		hostTo = "biloxi.com";
		displayTo ="Bob";
		tagTo = null;
		hostVia = "client.atlanta.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bK776xxasdhds";
		maxForwards = 70;
		userContact = "alice";
		hostContact = "client.atlanta.com";
		portContact =5060;
		product = "Telephone IP";
		contentType = "application";
		contentSubType = "sdp";
		
		req = requestFactory.createInvite(uri, callID, cSeq,  
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo, 
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards, 
											  userContact, hostContact, portContact, 
											  product, 
											  contentType, contentSubType, 
											  objSessionDescription);


		String requestExpect = "INVITE sip:bob@biloxi.com SIP/2.0\r\n"
				+ "Call-ID: a84b4c76e66710@client.atlanta.com\r\n"
				+ "CSeq: 1 INVITE\r\n"
				+ "From: \"Alice\" <sip:alice@atlanta.com>;tag=1928301774\r\n"
				+ "To: \"Bob\" <sip:bob@biloxi.com>\r\n"
				+ "Via: SIP/2.0/UDP client.atlanta.com:5060;branch=z9hG4bK776xxasdhds\r\n"
				+ "Max-Forwards: 70\r\n"
				+ "Contact: <sip:alice@client.atlanta.com:5060>\r\n"
				+ "User-Agent: Telephone IP\r\n"
				+ "Content-Type: application/sdp\r\n"
				+ "Content-Length: 261\r\n" + "\r\n";
		
		String SDExpect = "v=0\r\n"
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
	}
	
	@Test(expected = CreateRequestException.class)
	public void inviteCreateRequestException() throws CreateRequestException {
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact,product,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,portContact;
		
		uri = "sip:bob@biloxi.com";
		callID = "a84b4c76e66710@client.atlanta.com";
		cSeq = 1L;
		userFrom = "alice";
		hostFrom = "atlanta.com";
		displayFrom = "Alice";
		tagFrom = "1928301774";
		userTo = "bob";
		hostTo = "biloxi.com";
		displayTo ="Bob";
		tagTo = "";
		hostVia = "client.atlanta.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = null;
		maxForwards = 70;
		userContact = "alice";
		hostContact = "client.atlanta.com";
		portContact =5060;
		product = "Telephone IP";
		contentType = "application";
		contentSubType = "sdp";
		
		requestFactory.createInvite(uri, callID, cSeq,  
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo, 
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards, 
											  userContact, hostContact, portContact, 
											  product, 
											  contentType, contentSubType, 
											  null);
	}
}
