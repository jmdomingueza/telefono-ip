package com.jmdomingueza.telefonoip.sip.sip;

import javax.sip.message.Request;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jmdomingueza.telefonoip.sip.sip.CreateRequestException;
import com.jmdomingueza.telefonoip.sip.sip.RequestFactory;

/**
 * Unit test for NotifyTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class NotifyTest {

	@Autowired 
	protected RequestFactory requestFactory;
	
	@Test
	public void notifyTrying() throws CreateRequestException {
		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,eventType,userContact,hostContact,subscriptionState,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,portContact;
		String text;

		uri = "sip:a@atlanta.example.com";
		callID = "898234234@agenta.atlanta.example.com";
		cSeq = 1993402L;
		userFrom = "b";
		hostFrom = "atlanta.example.com";
		displayFrom = null;
		tagFrom = "4992881234";
		userTo = "a";
		hostTo = "atlanta.example.com";
		displayTo = null;
		tagTo = "193402342";
		hostVia = "agentb.atlanta.example.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bK9922ef992-25";
		maxForwards = 70;
		eventType = "refer";
		userContact = "b";
		hostContact = "atlanta.example.com";
		portContact =5060;
		subscriptionState = "active;expires=(depends on Refer-To URI)";
		contentType = "message";
		contentSubType = "sipfrag;version=2.0";
		text = "SIP/2.0 100 Trying\r\n";

		req = requestFactory.createNotify(uri,
										  callID,
										  cSeq,
										  userFrom, hostFrom, displayFrom, tagFrom,
										  userTo, hostTo, displayTo, tagTo,
										  hostVia, portVia, transportVia, branchVia,
										  maxForwards,
										  eventType,
										  userContact, hostContact, portContact,
										  subscriptionState,
										  contentType, contentSubType,
										  text);

		String requestExpect = "NOTIFY sip:a@atlanta.example.com SIP/2.0\r\n"
				+"Call-ID: 898234234@agenta.atlanta.example.com\r\n"
				+"CSeq: 1993402 NOTIFY\r\n"
				+"From: <sip:b@atlanta.example.com>;tag=4992881234\r\n"
				+"To: <sip:a@atlanta.example.com>;tag=193402342\r\n"
				+"Via: SIP/2.0/UDP agentb.atlanta.example.com:5060;branch=z9hG4bK9922ef992-25\r\n"
				+"Max-Forwards: 70\r\n"
				+"Event: refer\r\n"
				+"Subscription-State: active;expires=(depends on Refer-To URI)\r\n"
				+"Contact: <sip:b@atlanta.example.com:5060>\r\n"
				+"Content-Type: message/sipfrag;version=2.0\r\n"
				+"Content-Length: 20\r\n"+"\r\n"
				+"SIP/2.0 100 Trying\r\n";

		Assert.assertEquals(requestExpect.toString(), req.toString());
	}


	@Test
	public void notifyOK() throws CreateRequestException {
		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,eventType,userContact,hostContact,subscriptionState,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,portContact;
		String text;

		uri = "sip:a@atlanta.example.com";
		callID = "898234234@agenta.atlanta.example.com";
		cSeq = 1993403L;
		userFrom = "b";
		hostFrom = "atlanta.example.com";
		displayFrom = null;
		tagFrom = "4992881234";
		userTo = "a";
		hostTo = "atlanta.example.com";
		displayTo = null;
		tagTo = "193402342";
		hostVia = "agentb.atlanta.example.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bK9323394234";
		maxForwards = 70;
		eventType = "refer";
		userContact = "b";
		hostContact = "atlanta.example.com";
		portContact =5060;
		subscriptionState = "terminated;reason=noresource";
		contentType = "message";
		contentSubType = "sipfrag;version=2.0";
		text = "SIP/2.0 200 OK";

		req = requestFactory.createNotify(uri,
										  callID,
										  cSeq,
										  userFrom, hostFrom, displayFrom, tagFrom,
										  userTo, hostTo, displayTo, tagTo,
										  hostVia, portVia, transportVia, branchVia,
										  maxForwards,
										  eventType,
										  userContact, hostContact, portContact,
										  subscriptionState,
										  contentType, contentSubType,
										  text);

		String requestExpect = "NOTIFY sip:a@atlanta.example.com SIP/2.0\r\n"
				+"Call-ID: 898234234@agenta.atlanta.example.com\r\n"
				+"CSeq: 1993403 NOTIFY\r\n"
				+"From: <sip:b@atlanta.example.com>;tag=4992881234\r\n"
				+"To: <sip:a@atlanta.example.com>;tag=193402342\r\n"
				+"Via: SIP/2.0/UDP agentb.atlanta.example.com:5060;branch=z9hG4bK9323394234\r\n"
				+"Max-Forwards: 70\r\n"
				+"Event: refer\r\n"
				+"Subscription-State: terminated;reason=noresource\r\n"
				+"Contact: <sip:b@atlanta.example.com:5060>\r\n"
				+"Content-Type: message/sipfrag;version=2.0\r\n"
				+"Content-Length: 14\r\n"+"\r\n"
				+"SIP/2.0 200 OK";

		Assert.assertEquals(requestExpect.toString(), req.toString());
	}

	@Test(expected = CreateRequestException.class)
	public void notifyCreateRequestException() throws CreateRequestException {
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,eventType,userContact,hostContact,subscriptionState,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,portContact;
		String text;

		uri = "sip:a@atlanta.example.com";
		callID = "898234234@agenta.atlanta.example.com";
		cSeq = 1993402L;
		userFrom = "b";
		hostFrom = "atlanta.example.com";
		displayFrom = null;
		tagFrom = "4992881234";
		userTo = "a";
		hostTo = "atlanta.example.com";
		displayTo = null;
		tagTo = "193402342";
		hostVia = "agentb.atlanta.example.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "";
		maxForwards = 70;
		eventType = "refer";
		userContact = "b";
		hostContact = "atlanta.example.com";
		portContact =5060;
		subscriptionState = "active;expires=(depends on Refer-To URI)";
		contentType = "message";
		contentSubType = "sipfrag;version=2.0";
		text = "SIP/2.0 100 Trying\r\n";

		requestFactory.createNotify(uri,
										  callID,
										  cSeq,
										  userFrom, hostFrom, displayFrom, tagFrom,
										  userTo, hostTo, displayTo, tagTo,
										  hostVia, portVia, transportVia, branchVia,
										  maxForwards,
										  eventType,
										  userContact, hostContact, portContact,
										  subscriptionState,
										  contentType, contentSubType,
										  text);


	}
}
