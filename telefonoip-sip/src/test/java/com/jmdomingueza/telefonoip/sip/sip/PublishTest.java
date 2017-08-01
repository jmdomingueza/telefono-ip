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
 * Unit test for PublishTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class PublishTest {

	@Autowired 
	protected RequestFactory requestFactory;
	
	@Test
	public void publishWithoutContent() throws CreateRequestException {
		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,eventType,etag,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,expires;
		String text;

		uri = "sip:presentity@example.com";
		callID = "98798798@pua.example.com";
		cSeq = 1L;
		userFrom = "presentity";
		hostFrom = "example.com";
		displayFrom = null;
		tagFrom = "1234kljk";
		userTo = "presentity";
		hostTo = "example.com";
		displayTo = null;
		tagTo = null;
		hostVia = "pua.example.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bK771ash02";
		maxForwards = 70;
		etag = "dx200xyz";
		expires = 3600;
		eventType = "presence";
		contentType = null;
		contentSubType = null;
		text = null;

		req = requestFactory.createPublish(uri,
										   callID,
										   cSeq,
										   userFrom, hostFrom, displayFrom, tagFrom,
										   userTo, hostTo, displayTo, tagTo,
										   hostVia, portVia, transportVia, branchVia,
										   maxForwards,
										   etag,
										   expires,
										   eventType,
										   contentType, contentSubType,
										   text);

		String requestExpect = "PUBLISH sip:presentity@example.com SIP/2.0\r\n"
				+"Call-ID: 98798798@pua.example.com\r\n"
				+"CSeq: 1 PUBLISH\r\n"
				+"From: <sip:presentity@example.com>;tag=1234kljk\r\n"
				+"To: <sip:presentity@example.com>\r\n"
				+"Via: SIP/2.0/UDP pua.example.com:5060;branch=z9hG4bK771ash02\r\n"
				+"Max-Forwards: 70\r\n"
				+"SIP-If-Match: dx200xyz\r\n"
				+"Expires: 3600\r\n"
				+"Event: presence\r\n"
				+"Content-Length: 0\r\n"+"\r\n";

		Assert.assertEquals(requestExpect.toString(), req.toString());
	}


	@Test
	public void publishWithContent() throws CreateRequestException {
		Request req;
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,etag,eventType,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,expires;
		String text;

		uri = "sip:presentity@example.com";
		callID = "81818181@pua.example.com";
		cSeq = 1L;
		userFrom = "presentity";
		hostFrom = "example.com";
		displayFrom = null;
		tagFrom = "1234wxyz";
		userTo = "presentity";
		hostTo = "example.com";
		displayTo = null;
		tagTo = null;
		hostVia = "pua.example.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "z9hG4bK652hsge";
		maxForwards = 70;
		etag = null;
		expires = 3600;
		eventType = "presence";
		contentType = "application";
		contentSubType = "pidf+xml";
		text = "...";

		req = requestFactory.createPublish(uri,
				   						   callID,
				   						   cSeq,
				   						   userFrom, hostFrom, displayFrom, tagFrom,
				   						   userTo, hostTo, displayTo, tagTo,
				   						   hostVia, portVia, transportVia, branchVia,
				   						   maxForwards,
				   						   etag,
				   						   expires,
				   						   eventType,
				   						   contentType, contentSubType,
				   						   text);

		String requestExpect = "PUBLISH sip:presentity@example.com SIP/2.0\r\n"
				+"Call-ID: 81818181@pua.example.com\r\n"
				+"CSeq: 1 PUBLISH\r\n"
				+"From: <sip:presentity@example.com>;tag=1234wxyz\r\n"
				+"To: <sip:presentity@example.com>\r\n"
				+"Via: SIP/2.0/UDP pua.example.com:5060;branch=z9hG4bK652hsge\r\n"
				+"Max-Forwards: 70\r\n"
				+"Expires: 3600\r\n"
				+"Event: presence\r\n"
				+"Content-Type: application/pidf+xml\r\n"
				+"Content-Length: 3\r\n"+"\r\n"
				+"...";

		Assert.assertEquals(requestExpect.toString(), req.toString());
	}

	@Test(expected = CreateRequestException.class)
	public void notifyCreateRequestException() throws CreateRequestException {
		String uri,callID,userFrom,hostFrom,displayFrom,tagFrom,userTo,hostTo,displayTo,tagTo,hostVia,transportVia,branchVia,eventType,etag,contentType,contentSubType;
		Long cSeq;
		Integer portVia,maxForwards,expires;
		String text;

		uri = "sip:presentity@example.com";
		callID = "98798798@pua.example.com";
		cSeq = 1L;
		userFrom = "presentity";
		hostFrom = "example.com";
		displayFrom = null;
		tagFrom = "1234kljk";
		userTo = "presentity";
		hostTo = "example.com";
		displayTo = null;
		tagTo = null;
		hostVia = "pua.example.com";
		portVia = 5060;
		transportVia = "UDP";
		branchVia = "";
		maxForwards = 70;
		etag = "dx200xyz";
		expires = 3600;
		eventType = "presence";
		contentType = null;
		contentSubType = null;
		text = null;

		requestFactory.createPublish(uri,
										   callID,
										   cSeq,
										   userFrom, hostFrom, displayFrom, tagFrom,
										   userTo, hostTo, displayTo, tagTo,
										   hostVia, portVia, transportVia, branchVia,
										   maxForwards,
										   etag,
										   expires,
										   eventType,
										   contentType, contentSubType,
										   text);



	}
}
