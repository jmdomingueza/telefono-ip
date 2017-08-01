package com.jmdomingueza.telefonoip.rtp.factories;

import java.util.Vector;

import javax.media.Player;
import javax.media.Processor;
import javax.media.rtp.ReceiveStream;
import javax.sdp.Media;
import javax.sdp.MediaDescription;
import javax.sdp.SdpException;
import javax.sdp.SessionDescription;
import javax.sip.message.Request;
import javax.sip.message.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.rtp.beans.Audio;
import com.jmdomingueza.telefonoip.rtp.beans.AudioImpl;
import com.jmdomingueza.telefonoip.rtp.beans.Receive;
import com.jmdomingueza.telefonoip.rtp.beans.ReceiveImpl;
import com.jmdomingueza.telefonoip.rtp.beans.Send;
import com.jmdomingueza.telefonoip.rtp.beans.SendImpl;
import com.jmdomingueza.telefonoip.rtp.beans.SessionRtp;
import com.jmdomingueza.telefonoip.rtp.beans.SessionRtpImpl;
import com.jmdomingueza.telefonoip.sip.sdp.CreateSessionDescriptionException;
import com.jmdomingueza.telefonoip.sip.sdp.SessionDescriptionFactory;

public class BeanRtpFactory {

	/**
	 * Logger de la clase
	 */
	private static final Log logger = LogFactory.getLog(BeanRtpFactory.class);

	public static Audio createAudio(String nameInfo,String type){
		Audio audio;
		logger.trace("Enter createAudio");
		
		audio = new AudioImpl();
		audio.setNameInfo(nameInfo);
		audio.setType(type);
		logger.debug("Bean de Audio creado: "+audio);
		
		logger.trace("Exit createAudio");
		return audio;
	}
	
	public static Receive createReceive(Processor pr, ReceiveStream strm,Player pl){
		Receive receive;
		logger.trace("Enter createReceive");
		
		receive = new ReceiveImpl();
		receive.setProcessor(pr);
		receive.setStream(strm);
		receive.setPlayer(pl);
		logger.debug("Bean de Receive creado: "+receive);
		
		logger.trace("Exit createReceive");
		return receive;
	}
	
	public static Send createSend(){
		Send send;
		logger.trace("Enter createSend");
		
		send = new SendImpl();
		logger.debug("Bean de Send creado: "+send);
		
		logger.trace("Exit createSend");
		return send;
	}
	
	public static SessionRtp createSessionRtp(Request req, Response resp) throws CreateSessionDescriptionException, SdpException{
		SessionRtp sessionRtp;
		logger.trace("Enter createSessionRtp");
		
		String strSessionDescriptionResp = new String((byte[])resp.getContent());
		SessionDescription sessionDesciptionResp = SessionDescriptionFactory.createSessionDescription(strSessionDescriptionResp);
		//Fijar el valor del puerto
		//extraigo la información MediaDescriptions del SessionDescription
		Vector<?> vMediaDescResp = sessionDesciptionResp.getMediaDescriptions(false);
		Media mediaDescriptionResp = ((MediaDescription)vMediaDescResp.firstElement()).getMedia();
		Integer destPort= mediaDescriptionResp.getMediaPort();
		//Fijar el valor de la IP de conexión existente en la oferta/respuesta SDP.
		//extraigo la información de la conexion del SessionDescription		
		String destAddress = sessionDesciptionResp.getConnection().getAddress();			
			
		String strSessionDescriptionReq = new String((byte[])req.getContent());
		SessionDescription sessionDesciptionReq = SessionDescriptionFactory.createSessionDescription(strSessionDescriptionReq);
		//Fijar el valor del puerto
		//extraigo la información MediaDescriptions del SessionDescription
		Vector<?> vMediaDescReq = sessionDesciptionReq.getMediaDescriptions(false);
		Media mediaDescriptionReq = ((MediaDescription)vMediaDescReq.firstElement()).getMedia();
		Integer localPort= mediaDescriptionReq.getMediaPort();
		
		sessionRtp = new SessionRtpImpl();
		sessionRtp.setLocalPort(localPort);
		sessionRtp.setDestAddress(destAddress);
		sessionRtp.setDestPort(destPort);
		logger.debug("Bean de SessionRtp creado: "+sessionRtp);
		
		logger.trace("Exit createSessionRtp");
		return sessionRtp;
	}
	
	
}
