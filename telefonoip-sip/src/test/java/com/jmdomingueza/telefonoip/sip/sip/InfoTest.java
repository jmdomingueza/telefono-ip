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
 * Unit test for InfoTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class InfoTest {

	@Autowired 
	protected RequestFactory requestFactory;
	
	@Test
	public void InfoOk() throws CreateRequestException {
		
		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia, info_Package, contentType, contentSubType, encoding,  text;
		Long cSeq;
		Integer portVia,maxForwards;
		
		uri = "sip:alice@pc33.example.com";
		callID = "a84b4c76e66710@pc33.example.com";
		cSeq = 314333L;
		userFrom = "alice";
		hostFrom = "atlanta.com";
		displayFrom = "Alice";
		tagFrom = "1928301774";
		userTo = "bob";
		hostTo = "biloxi.com";
		displayTo ="Bob";
		tagTo = "a6c85cf";
		hostVia = "192.0.2.2";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bKnabcdef";
		maxForwards = 70;
		info_Package = "foo";
		contentType = "application";
		contentSubType = "foo";
		encoding = "Info-Package";
		text = "I am a foo message type ";
				
		req = requestFactory.createInfo(uri, callID, cSeq,  
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo, 
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards,info_Package,contentType,contentSubType,
											  encoding,text);
		
		
		String requestExpect = "INFO sip:alice@pc33.example.com SIP/2.0\r\n"
							  +"Call-ID: a84b4c76e66710@pc33.example.com\r\n"
							  +"CSeq: 314333 INFO\r\n"
							  +"From: \"Alice\" <sip:alice@atlanta.com>;tag=1928301774\r\n"
							  +"To: \"Bob\" <sip:bob@biloxi.com>;tag=a6c85cf\r\n" 
							  +"Via: SIP/2.0/UDP 192.0.2.2:5060;branch=z9hG4bKnabcdef\r\n"
							  +"Max-Forwards: 70\r\n"	
							  +"Info-Package: foo\r\n"
							  +"Content-Disposition: Info-Package\r\n"
							  +"Content-Type: application/foo\r\n"
							  +"Content-Length: 24\r\n"
							  +"\r\n"
							  +"I am a foo message type ";
		
		Assert.assertEquals(requestExpect.toString(), req.toString());
	}
	
	@Test(expected = CreateRequestException.class)
	public void InfoCreateRequestException() throws CreateRequestException {
		
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia, info_Package, contentType, contentSubType, encoding,  text;
		Long cSeq;
		Integer portVia,maxForwards;
		
		uri = "sip:alice@pc33.example.com";
		callID = "a84b4c76e66710@pc33.example.com";
		cSeq = 314333L;
		userFrom = "alice";
		hostFrom = "atlanta.com";
		displayFrom = "Alice";
		tagFrom = "1928301774";
		userTo = "bob";
		hostTo = "biloxi.com";
		displayTo ="Bob";
		tagTo = "a6c85cf";
		hostVia = "192.0.2.2";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "";
		maxForwards = 70;
		info_Package = "foo";
		contentType = "application";
		contentSubType = "foo";
		encoding = "Info-Package";
		text = "I am a foo message type ";
				
		requestFactory.createInfo(uri, callID, cSeq,  
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo, 
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards,info_Package,contentType,contentSubType,
											  encoding,text);
		
	}
}
