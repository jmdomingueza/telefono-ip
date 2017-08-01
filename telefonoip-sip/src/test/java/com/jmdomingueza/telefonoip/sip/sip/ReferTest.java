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
 * Unit test for ReferTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class ReferTest {

	@Autowired 
	protected RequestFactory requestFactory;
	
	@Test
	public void referOk() throws CreateRequestException {

		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact,userReferTo,hostReferTo;
		Long cSeq;
		Integer portVia,maxForwards,portContact,portReferTo;
		
		uri = "sip:bob@biloxi.com";
		callID = "898234234@alice.example.com";
		cSeq = 93809823L;
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
		branchVia = "z9hG4bK2293940223";
		maxForwards = 70;
		userContact="alice";
		hostContact="client.atlanta.com";
		portContact=5060;
		userReferTo="charles";
		hostReferTo="atlanta.com";
		portReferTo =null;
		
		req = requestFactory.createRefer(uri, callID, cSeq, userFrom, hostFrom, displayFrom, tagFrom,
										userTo, hostTo, displayTo, tagTo, 
										hostVia, portVia, transportVia, branchVia, 
										maxForwards, 
										userContact, hostContact, portContact, 
										userReferTo, hostReferTo, portReferTo);
		

		String requestExpect = "REFER sip:bob@biloxi.com SIP/2.0\r\n"
				+ "Call-ID: 898234234@alice.example.com\r\n"
				+ "CSeq: 93809823 REFER\r\n"
				+ "From: \"Alice\" <sip:alice@atlanta.com>;tag=1928301774\r\n"
				+ "To: \"Bob\" <sip:bob@biloxi.com>\r\n"
				+ "Via: SIP/2.0/UDP client.atlanta.com:5060;branch=z9hG4bK2293940223\r\n"
				+ "Max-Forwards: 70\r\n"
				+ "Refer-To: <sip:charles@atlanta.com>\r\n"
				+ "Contact: <sip:alice@client.atlanta.com:5060>\r\n"
				+ "Content-Length: 0\r\n" + "\r\n";
		Assert.assertEquals(requestExpect.toString(), req.toString());
	}
	@Test(expected = CreateRequestException.class)
	public void referCreateRequestException() throws CreateRequestException {
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact,userReferTo,hostReferTo;
		Long cSeq;
		Integer portVia,maxForwards,portContact,portReferTo;
		
		uri = "sip:bob@biloxi.com";
		callID = "898234234@alice.example.com";
		cSeq = 93809823L;
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
		branchVia = "";
		maxForwards = 70;
		userContact="alice";
		hostContact="client.atlanta.com";
		portContact=5060;
		userReferTo="charles";
		hostReferTo="atlanta.com";
		portReferTo =null;
		
		requestFactory.createRefer(uri, callID, cSeq, userFrom, hostFrom, displayFrom, tagFrom,
										userTo, hostTo, displayTo, tagTo, 
										hostVia, portVia, transportVia, branchVia, 
										maxForwards, 
										userContact, hostContact, portContact, 
										userReferTo, hostReferTo, portReferTo);
		
	}
}
