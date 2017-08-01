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
 * Unit test for OptionsTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class OptionsTest {

	@Autowired 
	protected RequestFactory requestFactory;
	
	@Test
	public void OptionsOk() throws CreateRequestException {
		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,portContact;

		uri = "sip:carol@chicago.com";
		callID = "a84b4c76e66710";
		cSeq = 63104L;
		userFrom = "alice";
		hostFrom = "atlanta.com";
		displayFrom = "Alice";
		tagFrom = "1928301774";
		userTo = "carol";
		hostTo = "chicago.com";
		displayTo = null;
		tagTo = null;
		hostVia = "pc33.atlanta.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bKhjhs8ass877";
		maxForwards = 70;
		userContact = "alice";
		hostContact = "pc33.atlanta.com";
		portContact =5060;
		contentType = "application";
		contentSubType = "sdp";

		req = requestFactory.createOptions(uri,
										   callID,
										   cSeq,
										   userFrom, hostFrom, displayFrom, tagFrom,
										   userTo, hostTo, displayTo, tagTo,
										   hostVia, portVia, transportVia, branchVia,
										   maxForwards,
										   userContact, hostContact, portContact,
										   contentType, contentSubType);

		String requestExpect = "OPTIONS sip:carol@chicago.com SIP/2.0\r\n"
				+"Call-ID: a84b4c76e66710\r\n"
				+"CSeq: 63104 OPTIONS\r\n"
				+"From: \"Alice\" <sip:alice@atlanta.com>;tag=1928301774\r\n"
				+"To: <sip:carol@chicago.com>\r\n"
				+"Via: SIP/2.0/UDP pc33.atlanta.com:5060;branch=z9hG4bKhjhs8ass877\r\n"
				+"Max-Forwards: 70\r\n"
				+"Contact: <sip:alice@pc33.atlanta.com:5060>\r\n"
				+"Accept: application/sdp\r\n"
				+"Content-Length: 0\r\n"+"\r\n";
		Assert.assertEquals(requestExpect.toString(), req.toString());
	}

	@Test(expected = CreateRequestException.class)
	public void optionsCreateRequestException() throws CreateRequestException {
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,userContact,hostContact,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,portContact;

		uri = "sip:carol@chicago.com";
		callID = " a84b4c76e66710";
		cSeq = 63104L;
		userFrom = "alice";
		hostFrom = "atlanta.com";
		displayFrom = "Alice";
		tagFrom = "1928301774";
		userTo = "carol";
		hostTo = "chicago.com";
		displayTo = null;
		tagTo = null;
		hostVia = "pc33.atlanta.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "";
		maxForwards = 70;
		userContact = "alice";
		hostContact = "pc33.atlanta.com";
		portContact =5060;
		contentType = "application";
		contentSubType = "sdp";

		requestFactory.createOptions(uri,
										   callID,
										   cSeq,
										   userFrom, hostFrom, displayFrom, tagFrom,
										   userTo, hostTo, displayTo, tagTo,
										   hostVia, portVia, transportVia, branchVia,
										   maxForwards,
										   userContact, hostContact, portContact,
										   contentType, contentSubType);
	}
}
