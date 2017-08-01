package com.jmdomingueza.telefonoip.sip.sip;

import java.util.ArrayList;
import java.util.List;

import javax.sdp.SessionDescription;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jmdomingueza.telefonoip.sip.sdp.CreateSessionDescriptionException;
import com.jmdomingueza.telefonoip.sip.sdp.SessionDescriptionFactory;
import com.jmdomingueza.telefonoip.sip.utils.SDPConstants;

/**
 * Unit test for SessionDescriptionTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class SessionDescriptionTest {

	@Test
	public void sessionDescriptionNone_atr() throws CreateSessionDescriptionException {
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
		

		String requestExpect = "v=0\r\n"
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
		Assert.assertEquals(requestExpect.toString(), objSessionDescription.toString());
	}
	
	@Test
	public void sessionDescriptionRecvonly_atr() throws CreateSessionDescriptionException {
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
		mode = SDPConstants.RECVONLY;
		objSessionDescription = SessionDescriptionFactory.createSessionDescription(sessionId, sessionVersion, ipSessionDescription, audioCodecs, portSessionDescription, mode);
		
		String requestExpect = "v=0\r\n"
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
				+ "a=recvonly\r\n";
		Assert.assertEquals(requestExpect.toString(), objSessionDescription.toString());
	}
	
	@Test
	public void sessionDescriptionRendonly_atr() throws CreateSessionDescriptionException {
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
		mode = SDPConstants.SENDONLY;
		objSessionDescription = SessionDescriptionFactory.createSessionDescription(sessionId, sessionVersion, ipSessionDescription, audioCodecs, portSessionDescription, mode);
		

		String requestExpect = "v=0\r\n"
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
				+ "a=sendonly\r\n";
		Assert.assertEquals(requestExpect.toString(), objSessionDescription.toString());
	}
	
	@Test
	public void sessionDescriptionRendrecv_atr() throws CreateSessionDescriptionException {
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
		mode = SDPConstants.SENDRECV;
		objSessionDescription = SessionDescriptionFactory.createSessionDescription(sessionId, sessionVersion, ipSessionDescription, audioCodecs, portSessionDescription, mode);
		
		String requestExpect = "v=0\r\n"
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
				+ "a=sendrecv\r\n";
		Assert.assertEquals(requestExpect.toString(), objSessionDescription.toString());
	}
	
	@Test
	public void sessionDescriptionInactive_atr() throws CreateSessionDescriptionException {
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
		mode = SDPConstants.INACTIVE;
		objSessionDescription = SessionDescriptionFactory.createSessionDescription(sessionId, sessionVersion, ipSessionDescription, audioCodecs, portSessionDescription, mode);
		
		String requestExpect = "v=0\r\n"
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
				+ "a=inactive\r\n";
		Assert.assertEquals(requestExpect.toString(), objSessionDescription.toString());
	}
	
	
	@Test
	public void sessionDescriptionNone_str() throws CreateSessionDescriptionException {
		SessionDescription objSessionDescription;
		String sessionDescriptionstr;
		
		
		sessionDescriptionstr = "v=0\r\n"
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
		objSessionDescription = SessionDescriptionFactory.createSessionDescription(sessionDescriptionstr);
		

		String requestExpect = "v=0\r\n"
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
		Assert.assertEquals(requestExpect.toString(), objSessionDescription.toString());
	}
	
	@Test
	public void sessionDesciptionRecvonly_str() throws CreateSessionDescriptionException {
		SessionDescription objSessionDescription;
		String sessionDescriptionstr;
		
		
		sessionDescriptionstr = "v=0\r\n"
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
				+ "a=recvonly\r\n";
		objSessionDescription = SessionDescriptionFactory.createSessionDescription(sessionDescriptionstr);
		
		String requestExpect = "v=0\r\n"
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
				+ "a=recvonly\r\n";
		Assert.assertEquals(requestExpect.toString(), objSessionDescription.toString());
	}
	
	@Test
	public void sessionDescriptionSendonly_str() throws CreateSessionDescriptionException {
		SessionDescription objSessionDescription;
		String sessionDescriptionstr;
		
		
		sessionDescriptionstr = "v=0\r\n"
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
				+ "a=sendonly\r\n";
		objSessionDescription = SessionDescriptionFactory.createSessionDescription(sessionDescriptionstr);
		

		String requestExpect = "v=0\r\n"
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
				+ "a=sendonly\r\n";
		Assert.assertEquals(requestExpect.toString(), objSessionDescription.toString());
	}
	
	@Test
	public void sessionDescriptionRendrecv_str() throws CreateSessionDescriptionException {
		SessionDescription objSessionDescription;
		String sessionDescriptionstr;
		
		
		sessionDescriptionstr = "v=0\r\n"
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
				+ "a=sendrecv\r\n";
		objSessionDescription = SessionDescriptionFactory.createSessionDescription(sessionDescriptionstr);
		
		String requestExpect = "v=0\r\n"
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
				+ "a=sendrecv\r\n";
		Assert.assertEquals(requestExpect.toString(), objSessionDescription.toString());
	}
	
	@Test
	public void sessionDescriptionInactive_str() throws CreateSessionDescriptionException {
		SessionDescription objSessionDescription;
		String sessionDescriptionstr;
		
		
		sessionDescriptionstr = "v=0\r\n"
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
				+ "a=inactive\r\n";
		objSessionDescription = SessionDescriptionFactory.createSessionDescription(sessionDescriptionstr);
		
		
		String requestExpect = "v=0\r\n"
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
				+ "a=inactive\r\n";
		Assert.assertEquals(requestExpect.toString(), objSessionDescription.toString());
	}
	@Test(expected = CreateSessionDescriptionException.class)
	public void sessionDescriptionCreateSessionDescriptionException() throws CreateSessionDescriptionException {
		Long sessionId,sessionVersion;
		String ipSessionDescription;
		Integer portSessionDescription, mode;
		List<Integer> audioCodecs;
		
		sessionId = 11111L;
		sessionVersion =11111L;
		ipSessionDescription = null;
		audioCodecs = new ArrayList<Integer>();
		audioCodecs.add(SDPConstants.PCMA);
		audioCodecs.add(SDPConstants.TE98);
		audioCodecs.add(SDPConstants.TE101);
		portSessionDescription = 12000;
		mode = SDPConstants.RECVONLY;
		
		SessionDescriptionFactory.createSessionDescription(sessionId, sessionVersion, ipSessionDescription, audioCodecs, portSessionDescription, mode);
		
	}
	
	
}
