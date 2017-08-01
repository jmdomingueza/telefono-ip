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
 * Unit test for AckTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class AckTest {
	
	@Autowired 
	protected RequestFactory requestFactory;
	
	@Test
	public void AckOk() throws CreateRequestException {
		
		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia;
		Long cSeq;
		Integer portVia,maxForwards;
		
		uri = "sip:bob@biloxi.com";
		callID = "987asjd97y7atg";
		cSeq = 986759L;
		userFrom = "alice";
		hostFrom = "atlanta.com";
		displayFrom = "Alice";
		tagFrom = "88sja8x";
		userTo = "bob";
		hostTo = "biloxi.com";
		displayTo ="Bob";
		tagTo = "99sa0xk";
		hostVia = "pc33.atlanta.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bKkjshdyff";
		maxForwards = 70;
		
		req = requestFactory.createAck(uri, callID, cSeq,  
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo, 
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards);
		
		
		String requestExpect = "ACK sip:bob@biloxi.com SIP/2.0\r\n"
							  +"Call-ID: 987asjd97y7atg\r\n"
							  +"CSeq: 986759 ACK\r\n"
							  +"From: \"Alice\" <sip:alice@atlanta.com>;tag=88sja8x\r\n"
							  +"To: \"Bob\" <sip:bob@biloxi.com>;tag=99sa0xk\r\n" 
							  +"Via: SIP/2.0/UDP pc33.atlanta.com:5060;branch=z9hG4bKkjshdyff\r\n"
							  +"Max-Forwards: 70\r\n"	
							  +"Content-Length: 0\r\n"
							  +"\r\n";
		Assert.assertEquals(requestExpect.toString(), req.toString());
	}
	
	@Test(expected = CreateRequestException.class)
	public void AckCreateRequestException() throws CreateRequestException {
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia;
		Long cSeq;
		Integer portVia,maxForwards;
		
		uri = "sip:bob@biloxi.com";
		callID = "987asjd97y7atg";
		cSeq = 986759L;
		userFrom = "alice";
		hostFrom = "atlanta.com";
		displayFrom = "Alice";
		tagFrom = "88sja8x";
		userTo = "bob";
		hostTo = "biloxi.com";
		displayTo ="Bob";
		tagTo = "99sa0xk";
		hostVia = "pc33.atlanta.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "";
		maxForwards = 70;
		
		requestFactory.createAck(uri, callID, cSeq,  
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo, 
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards);
	}

}
