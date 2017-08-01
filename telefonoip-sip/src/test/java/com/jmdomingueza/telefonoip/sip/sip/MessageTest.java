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
 * Unit test for MessageTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class MessageTest {

	@Autowired 
	protected RequestFactory requestFactory;
	
	@Test
	public void message_Ok() throws CreateRequestException {
		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,portContact;
		String text;
		
		uri = "sip:user2@domain.com";
		callID = "asd88asd77a@1.2.3.4";
		cSeq = 1L;
		userFrom = "user1";
		hostFrom = "domain.com";
		displayFrom = null;
		tagFrom = "49583";
		userTo = "user2";
		hostTo = "domain.com";
		displayTo =null;
		tagTo = null;
		hostVia = "user1pc.domain.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bK776sgdkse";
		maxForwards = 70;
		userContact = "user1pc";
		hostContact = "domain.com";
		portContact =5060;
		contentType = "text";
		contentSubType = "plain";
		text = "Watson, come here.";
		
		req = requestFactory.createMessage(uri, callID, cSeq,  
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo, 
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards, 
											  userContact, hostContact, portContact,  
											  contentType, contentSubType,
											  text);

		String requestExpect = "MESSAGE sip:user2@domain.com SIP/2.0\r\n"
				+"Call-ID: asd88asd77a@1.2.3.4\r\n"
				+"CSeq: 1 MESSAGE\r\n"
				+"From: <sip:user1@domain.com>;tag=49583\r\n"
				+"To: <sip:user2@domain.com>\r\n"
				+"Via: SIP/2.0/UDP user1pc.domain.com:5060;branch=z9hG4bK776sgdkse\r\n"
				+"Max-Forwards: 70\r\n"
				+"Contact: <sip:user1pc@domain.com:5060>\r\n"
				+"Content-Type: text/plain\r\n"
				+"Content-Length: 18\r\n"+"\r\n"
				+"Watson, come here.";
		Assert.assertEquals(requestExpect.toString(), req.toString());
	}
	
	@Test(expected = CreateRequestException.class)
	public void message_CreateRequestException() throws CreateRequestException {
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,portContact;
		String text;
		
		uri = "sip:user2@domain.com";
		callID = "asd88asd77a@1.2.3.4";
		cSeq = 1L;
		userFrom = "user1";
		hostFrom = "domain.com";
		displayFrom = null;
		tagFrom = "49583";
		userTo = "user2";
		hostTo = "domain.com";
		displayTo =null;
		tagTo = null;
		hostVia = "user1pc.domain.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bK776sgdkse";
		maxForwards = 70;
		userContact = "user1pc";
		hostContact = "domain.com";
		portContact =5060;
		contentType = "text";
		contentSubType = "plain";
		text = "Watson, come here.";
		
		requestFactory.createMessage(uri, callID, cSeq,  
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo, 
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards, 
											  userContact, hostContact, portContact,  
											  contentType, contentSubType,
											  text);

	}
}
