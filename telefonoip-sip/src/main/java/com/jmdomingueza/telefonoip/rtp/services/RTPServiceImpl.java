package com.jmdomingueza.telefonoip.rtp.services;

import java.net.InetAddress;

import javax.media.Format;
import javax.media.control.BufferControl;
import javax.media.format.AudioFormat;
import javax.media.rtp.RTPManager;
import javax.media.rtp.SessionAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.rtp.ReceiveThread;
import com.jmdomingueza.telefonoip.rtp.TransmitThread;
import com.jmdomingueza.telefonoip.rtp.beans.Audio;
import com.jmdomingueza.telefonoip.rtp.beans.AudioImpl;
import com.jmdomingueza.telefonoip.rtp.beans.SessionRtp;
import com.jmdomingueza.telefonoip.rtp.beans.SessionRtpImpl;
import com.jmdomingueza.telefonoip.rtp.services.exceptions.CreateSessionException;
import com.jmdomingueza.telefonoip.rtp.services.exceptions.DestroySessionException;

@Service(value = "rtpService")
public class RTPServiceImpl implements RTPService{
	/**
	 * Logger of the class
	 */
	private static Log logger = LogFactory.getLog(RTPServiceImpl.class);
	
	/**
	 * Audio del altavoz 
	 */
	private Audio audioSpeaker;
	
	/**
	 * Audio del microfono
	 */
	private Audio audioMicrophone;

	/**
	 * Construtor de la clase
	 */
	public RTPServiceImpl() {
		logger.trace("Enter RTPServiceImpl");
		logger.trace("Exit RTPServiceImpl");
	}

	@Override
	public void createSession(SessionRtp session) throws CreateSessionException{
		InetAddress ipAddr;
		SessionAddress localSessionAddr;
		SessionAddress destSessionAddr;
		RTPManager rtpMgr;
		
		logger.trace("Enter createSession");
		
		try {
			
			
			rtpMgr = RTPManager.newInstance();
			if (rtpMgr == null)
				throw new CreateSessionException("RTPSessionMgr is null");

			localSessionAddr = new SessionAddress(InetAddress.getLocalHost(), session.getLocalPort());
			
			ipAddr = InetAddress.getByName(session.getDestAddress());
			destSessionAddr = new SessionAddress(ipAddr, session.getDestPort());

			rtpMgr.initialize(localSessionAddr);
			rtpMgr.addTarget(destSessionAddr);
			rtpMgr.addFormat(new AudioFormat(AudioFormat.ALAW, -1.0, 8, 1, -1, AudioFormat.SIGNED, 8, -1.0, Format.byteArray), 8);
			
			// You can try out some other buffer size to see
			// if you can get better smoothness.
			BufferControl bc = (BufferControl) rtpMgr.getControl("javax.media.control.BufferControl");
			if (bc != null) bc.setBufferLength(350);	
			
			session.setRTPMgr(rtpMgr);
			logger.info("Created RTP session: from [" + InetAddress.getLocalHost().getHostAddress() + "]:/"
					+ session.getLocalPort()+" to [" + session.getDestAddress() + "]:/" + session.getDestPort());
			
			session.setTransmitThread(new TransmitThread(session,audioMicrophone));
			session.getTransmitThread().start();
			
			session.setReceiveThread(new ReceiveThread(session,audioSpeaker));
			session.getReceiveThread().start();
		} catch (Exception e) {
			logger.trace("Exit createSession - Exception");
			throw new CreateSessionException(e);
		}
		logger.trace("Exit createSession");
	}

	@Override
	public void destroySession(SessionRtp session) throws DestroySessionException{
		RTPManager rtpMgr;
		
		logger.trace("Enter destroySession");
		
		rtpMgr = session.getRTPMgr();
		if(rtpMgr == null)
			throw new DestroySessionException("RTPSessionMgr is null");
		try{
			session.getTransmitThread().interrupt();
			session.getReceiveThread().interrupt();
			
			rtpMgr.removeTargets("Closing session from RTPServiceImpl");
			rtpMgr.dispose();
		}catch(Exception e){
			logger.trace("Exit destroySession - Exception");
			throw new DestroySessionException(e);
			
		}
		logger.trace("Exit destroySession");
	}
	
	@Override
	public void configureSpeaker(Audio audio) {
		
		logger.trace("Enter configureSpeaker");
		
		this.audioSpeaker = audio;
		
		logger.trace("Exit configureSpeaker");
	}

	@Override
	public void configureMicrophone(Audio audio) {
		
		logger.trace("Enter configureMicrophone");
		
		this.audioSpeaker = audio;
		
		logger.trace("Exit configureMicrophone");
	}

//	public static void main(String[] args) {
//
//		try {
//			CaptureDeviceInfo device = CaptureDeviceManager.getDevice("JavaSound audio capture");
//
//			if (device == null) {
//				System.out.println("CaptureDeviceInfo returned is null");
//				return;
//			}
//
//			DataSource ds = new MiDataSource("USB PnP Sound",MiJavaSoundSourceStream.TYPE_MICROFONO);
//			ds.setLocator(device.getLocator());
//			ds.connect();
//
//			Player player = new MiMediaPlayer("High Definition",MiJavaSoundOutput.TYPE_SPEAKERS);
//			System.out.println("Iniciando la grabacion...");
//			player.setSource(ds);
//			player.start();
//			System.out.println("Grabando durante 10s...");
//			Thread.sleep(10000);
//
//			player.close();
//			System.out.println("Parando la grabacion...");
//			
//			RTPService service = new RTPServiceImpl();
//			SessionRtpImpl session = new SessionRtpImpl();
//			session.setDestAddress("192.168.1.133");
//			session.setDestPort(22222);
//			session.setLocalPort(22222);
//			session.setTtl(1);
//			
//			Audio audioSpeaker= new AudioImpl();
//			audioSpeaker.setNameInfo("USB PnP Sound");
//			audioSpeaker.setType(Audio.TYPE_ALTAVOCES);
//			
//			Audio audioMicrophone= new AudioImpl();
//			audioMicrophone.setNameInfo("USB PnP Sound");
//			audioMicrophone.setType(Audio.TYPE_MICROFONO);
//			
//			
//			System.out.println("Iniciando la sesion...");
//			service.createSession(session,audioSpeaker,audioMicrophone);
//			System.out.println("ejecutando la sesion durante 100s...");
//			Thread.sleep(100000);
//			
//			service.destroySession(session);
//			System.out.println("Parando la sesion...");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	

}
