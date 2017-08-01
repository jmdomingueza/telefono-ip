package com.jmdomingueza.telefonoip.sip.sdp;

import java.util.List;
import java.util.Vector;

import javax.sdp.Attribute;
import javax.sdp.Connection;
import javax.sdp.MediaDescription;
import javax.sdp.Origin;
import javax.sdp.SdpFactory;
import javax.sdp.SessionDescription;
import javax.sdp.SessionName;
import javax.sdp.Time;
import javax.sdp.Version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.sip.utils.SDPConstants;

/**
 * Class Factory that create the object SessionDescription of different form.
 * @author jmdomingueza
 *
 */
public class SessionDescriptionFactory {

	/**
	 * Logger of the class
	 */
	private static Log logger = LogFactory.getLog(SessionDescriptionFactory.class);

	/**
	 * Create a object of type SessionDescription.
	 * @param strSessionDescription the new string value of the SessionDescription.
	 * @return he newly created SessionDescription object.
	 * @throws CreateSessionDescriptionException if the SessionDescription is malformed.
	 */
	public static SessionDescription createSessionDescription(String strSessionDescription)throws CreateSessionDescriptionException{
		SdpFactory objSdpFactory;
		SessionDescription sessionDescription;
		logger.trace("enter createSessionDescription");
		try{
			objSdpFactory = SdpFactory.getInstance();
			sessionDescription = objSdpFactory.createSessionDescription(strSessionDescription);
		} catch (Exception e) {
			logger.trace("exit  createSessionDescription - Exception");
			throw new CreateSessionDescriptionException(e.getMessage(), e);
		}
		logger.trace("exit  createSessionDescription");
		return sessionDescription;
	}
	/**
	 * Create a object of type SessionDescription.
	 * @param sessionId the new long value of the sessionId.
	 * @param sessionVersion the new long value of the sessionVersion.
	 * @param ip the new string value of the ip.
	 * @param audioCodecs the new list object of the audioCodecs.
	 * @param port the new int value of the port.
	 * @param mode the new int value of the mode.
	 * @return
	 * @throws CreateSessionDescriptionException
	 */
	public static SessionDescription createSessionDescription(Long sessionId, Long sessionVersion,
			 String ipSessionDescription,List<Integer> audioCodecs,
			Integer portSessionDescription, Integer mode)
			throws CreateSessionDescriptionException {
		SessionDescription objSessionDescription;
		SdpFactory objSdpFactory;
		
		logger.trace("enter  createSessionDescription");
		try {
			objSdpFactory = SdpFactory.getInstance();
			
			objSessionDescription = objSdpFactory.createSessionDescription();

			Version objVersion = objSdpFactory.createVersion(0);

			Origin objOrigin = objSdpFactory.createOrigin("-",
					sessionId, sessionVersion,
					SDPConstants.IN, SDPConstants.IP4, ipSessionDescription);

			SessionName objSessionName = objSdpFactory
					.createSessionName(SDPConstants.SDP_SESSION_NAME);

			Connection objConnection = objSdpFactory.createConnection(
					SDPConstants.IN, SDPConstants.IP4,ipSessionDescription);
	
			Time objTime = objSdpFactory.createTime();
			Vector<Time> objVectorTiempo = new Vector<Time>();
			objVectorTiempo.add(objTime);

			MediaDescription objAudioDescription = null;
			int[] audioCodecsInt = new int[audioCodecs.size()];
			for (int i = 0; i < audioCodecs.size(); i++)
				audioCodecsInt[i] = audioCodecs.get(i).intValue();
			objAudioDescription = objSdpFactory.createMediaDescription(
					SDPConstants.MEDIA_AUDIO, portSessionDescription, 1,
					SDPConstants.RTP_AVP, audioCodecsInt);
			
			Vector<Attribute> vAtributos = new Vector<Attribute>();
			Attribute atrib;
			for (int i = 0; i < audioCodecs.size(); i++) {
					atrib = objSdpFactory.createAttribute(SDPConstants.RTPMAP,
						audioCodecs.get(i)
								+ " "
								+ SDPConstants.avpTypeNames[audioCodecs.get(i)]
								+ "/"
								+ SDPConstants.avpClockRates[audioCodecs.get(i)]);
					vAtributos.add(atrib);
					if (audioCodecs.get(i).equals(SDPConstants.TE98)
							|| audioCodecs.get(i).equals(SDPConstants.TE101)) {
						atrib = objSdpFactory.createAttribute(SDPConstants.FMTP,
								audioCodecs.get(i)
										+ " "
										+ SDPConstants.avpTypefmtp[audioCodecs.get(i)]);
						vAtributos.add(atrib);
					}
			}
			Attribute atributoMode = objSdpFactory.createAttribute(
					SDPConstants.modes[mode], null);
			vAtributos.add(atributoMode);
			Vector<MediaDescription> vMediaDesc = new Vector<MediaDescription>();
			objAudioDescription.setAttributes(vAtributos);
			vMediaDesc.add(objAudioDescription);
			
			objSessionDescription.setVersion(objVersion);
			objSessionDescription.setOrigin(objOrigin);
			objSessionDescription.setSessionName(objSessionName);
			objSessionDescription.setConnection(objConnection);
			objSessionDescription.setTimeDescriptions(objVectorTiempo);
			objSessionDescription.setMediaDescriptions(vMediaDesc);
		} catch (Exception e) {
			logger.trace("exit  createSessionDescription - Exception");
			throw new CreateSessionDescriptionException(e.getMessage(), e);
		}

		logger.trace("exit  createSessionDescription");
		return objSessionDescription;
	}
}
