package com.jmdomingueza.telefonoip.sip.utils;

import javax.sdp.Connection;
import javax.sdp.SdpConstants;

public class SDPConstants {

	/**
	 * Connection
	 */
	public final static String  IN = Connection.IN;
	public final static String  IP4 = Connection.IP4;
	public final static String  IP6 = Connection.IP6;
	
	public final static String  RTPMAP = SdpConstants.RTPMAP;
	public final static String  RTP_AVP = SdpConstants.RTP_AVP;
	
	public final static int  PCMA = SdpConstants.PCMA;
	public final static int  TE98 = 98;
	public final static int  TE101 = 101;
	
	public static final String[] avpTypeNames = { "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "PCMA", "UNASSIGNED",
												  "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", 
												  "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", 
												  "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
												  "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
												  "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
												  "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
												  "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
												  "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
												  "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "telephone-event", "UNASSIGNED",
												  "UNASSIGNED", "telephone-event" };
	public static final int[] avpClockRates = { -1, -1, -1, -1, -1, -1, -1, -1, 8000, -1,
												   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
												   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
												   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
												   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
												   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
												   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
												   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
												   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
												   -1, -1, -1, -1, -1, -1, -1, -1, 8000, -1,
												   -1, 8000};
	public final static String  FMTP = SdpConstants.FMTP;
	public final static String[] avpTypefmtp = { "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
									  		 "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", 
									  		 "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", 
									  		 "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
									  		 "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
									  		 "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
									  		 "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
									  		 "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
									  		 "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED",
									  		 "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "UNASSIGNED", "0-15", "UNASSIGNED",
									  		 "UNASSIGNED", "0-15"};

	public final static String MEDIA_AUDIO = "audio";
	
	/**
	 * Modos de envío/recepción SDP
	 */
	public final static int NONE = 0;
	public final static int SENDONLY = 1;
	public final static int RECVONLY = 2;
	public final static int SENDRECV = 3;
	public final static int INACTIVE = 4;
	
	public final static String[] modes = { "none", "sendonly", "recvonly", "sendrecv", "inactive"};
	
	/**
	 * Nombre de sesion SDP
	 */
	public final static String SDP_SESSION_NAME = "SIPTester";
}
