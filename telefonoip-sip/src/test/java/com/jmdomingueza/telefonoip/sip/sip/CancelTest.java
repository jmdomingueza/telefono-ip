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
 * Unit test for ByeTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class CancelTest {
	
	@Autowired 
	protected RequestFactory requestFactory;
	
	@Test
	public void cancelOk() throws CreateRequestException {
		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia;
		Long cSeq;
		Integer portVia,maxForwards;
		
		uri = "sip:bob@biloxi.com";
		callID = "2xTb9vxSit55XU7p8@atlanta.com";
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
		branchVia = "z9hG4bK74bf9";
		maxForwards = 70;
		
		req = requestFactory.createCancel(uri, callID, cSeq,  
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo, 
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards);
		
		
		String requestExpect ="CANCEL sip:bob@biloxi.com SIP/2.0\r\n"
				+ "Call-ID: 2xTb9vxSit55XU7p8@atlanta.com\r\n"
				+ "CSeq: 1 CANCEL\r\n"
				+ "From: \"Alice\" <sip:alice@atlanta.com>;tag=1928301774\r\n"
				+ "To: \"Bob\" <sip:bob@biloxi.com>\r\n"
				+ "Via: SIP/2.0/UDP client.atlanta.com:5060;branch=z9hG4bK74bf9\r\n"
				+ "Max-Forwards: 70\r\n"
				+ "Content-Length: 0\r\n"
				+ "\r\n";
		Assert.assertEquals(requestExpect.toString(), req.toString());
	}
	
	@Test(expected = CreateRequestException.class)
	public void cancelCreateRequestException() throws CreateRequestException {
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia;
		Long cSeq;
		Integer portVia,maxForwards;
		
		uri = "sip:bob@client.biloxi.com";
		callID = "2xTb9vxSit55XU7p8@atlanta.com";
		cSeq = 2L;
		userFrom = "alice";
		hostFrom = "atlanta.com";
		displayFrom = "Alice";
		tagFrom = "1928301774";
		userTo = "bob";
		hostTo = "biloxi.com";
		displayTo ="Bob";
		tagTo = "314159";
		hostVia = "client.atlanta.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "";
		maxForwards = 70;
		
		requestFactory.createCancel(uri, callID, cSeq,  
											  userFrom,hostFrom, displayFrom, tagFrom,
											  userTo, hostTo, displayTo, tagTo, 
											  hostVia, portVia, transportVia, branchVia,
											  maxForwards);
		
		
		
	}
}
