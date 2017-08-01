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
 * Unit test for SubscribeTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class SubscribeTest {

	@Autowired 
	protected RequestFactory requestFactory;
	

	@Test
	public void subscribeOk() throws CreateRequestException {

		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact;
		Long cSeq;
		Integer portVia,maxForwards,portContact,expires;

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

		req = requestFactory.createSubscribe(uri,
											 callID,
											 cSeq,
											 userFrom, hostFrom, displayFrom, tagFrom,
											 userTo, hostTo, displayTo, tagTo,
											 hostVia, portVia, transportVia, branchVia,
											 maxForwards,
											 userContact, hostContact, portContact,
											 expires);

		String requestExpect = "SUBSCRIBE sip:atlanta.com SIP/2.0\r\n"
				+ "Call-ID: 843817637684230@998sdasdh09\r\n"
				+ "CSeq: 1826 SUBSCRIBE\r\n"
				+ "From: \"Alice\" <sip:alice@atlanta.com>;tag=1928301774\r\n"
				+ "To: \"Alice\" <sip:alice@atlanta.com>\r\n"
				+ "Via: SIP/2.0/UDP client.atlanta.com:5060;branch=z9hG4bKnashds7\r\n"
				+ "Max-Forwards: 70\r\n"
				+ "Contact: <sip:alice@client.atlanta.com:5060>\r\n"
				+ "Expires: 7200\r\n"
				+ "Content-Length: 0\r\n" + "\r\n";
		Assert.assertEquals(requestExpect.toString(), req.toString());
	}


	@Test(expected = CreateRequestException.class)
	public void subscribeCreateRequestException() throws CreateRequestException {

		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact;
		Long cSeq;
		Integer portVia,maxForwards,portContact,expires;

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
		branchVia = "";
		maxForwards = 70;
		userContact="alice";
		hostContact="client.atlanta.com";
		portContact=5060;
		expires=7200;

		requestFactory.createSubscribe(uri,
											 callID,
											 cSeq,
											 userFrom, hostFrom, displayFrom, tagFrom,
											 userTo, hostTo, displayTo, tagTo,
											 hostVia, portVia, transportVia, branchVia,
											 maxForwards,
											 userContact, hostContact, portContact,
											 expires);

	}

}
