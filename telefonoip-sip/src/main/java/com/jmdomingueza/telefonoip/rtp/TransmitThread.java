package com.jmdomingueza.telefonoip.rtp;

import java.io.IOException;

import javax.media.Codec;
import javax.media.Controller;
import javax.media.ControllerClosedEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoProcessorException;
import javax.media.PlugInManager;
import javax.media.Processor;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.media.protocol.PushBufferDataSource;
import javax.media.protocol.PushBufferStream;
import javax.media.rtp.SendStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.rtp.audio.AudioUtil;
import com.jmdomingueza.telefonoip.rtp.audio.microphone.MiDataSource;
import com.jmdomingueza.telefonoip.rtp.beans.Audio;
import com.jmdomingueza.telefonoip.rtp.beans.Send;
import com.jmdomingueza.telefonoip.rtp.beans.SessionRtp;
import com.jmdomingueza.telefonoip.rtp.factories.BeanRtpFactory;

import net.sf.fmj.media.codec.audio.alaw.DePacketizer;
import net.sf.fmj.media.codec.audio.alaw.Decoder;
import net.sf.fmj.media.codec.audio.alaw.Encoder;
import net.sf.fmj.media.codec.audio.alaw.Packetizer;

public class TransmitThread extends Thread {

	private static Log logger = LogFactory.getLog(TransmitThread.class);
	
	private SessionRtp session;
	private Audio audio;
	
	private boolean lived = false;

	private Send send;

	private static Encoder encoder = new Encoder();
	private static Decoder decoder = new Decoder();
	private static Packetizer packetizer = new Packetizer();
	private static DePacketizer dePacketizer = new DePacketizer();

	public TransmitThread(SessionRtp session,Audio audio) throws RTPException{
		// instalación de plugins en caso de que sea necesario
		try {

			PlugInManager.addPlugIn(encoder.getClass().getName(), encoder.getSupportedInputFormats(),
					encoder.getSupportedOutputFormats(null), PlugInManager.CODEC);

			PlugInManager.addPlugIn(decoder.getClass().getName(), decoder.getSupportedInputFormats(),
					decoder.getSupportedOutputFormats(null), PlugInManager.CODEC);

			PlugInManager.addPlugIn(packetizer.getClass().getName(), packetizer.getSupportedInputFormats(),
					packetizer.getSupportedOutputFormats(null), PlugInManager.CODEC);
			PlugInManager.addPlugIn(dePacketizer.getClass().getName(), dePacketizer.getSupportedInputFormats(),
					dePacketizer.getSupportedOutputFormats(null), PlugInManager.CODEC);

			PlugInManager.commit();
		} catch (Exception e) {
			throw new RTPException("Error al guardar los codec incluidos.");
		}

		this.session = session;
		this.audio = audio;
		this.send = BeanRtpFactory.createSend();
		if(!AudioUtil.isLineSupported(audio))
			throw new RTPException("Audio : "+audio+" - no supported");
	}

	@Override
	public void run() {
		this.lived = true;
		this.initJMF();
		this.setupProcessor();
		this.setupTransmitter();
		
		
		while (lived ==true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
		}
	}

	@Override
	public void interrupt() {
		try {
			send.close();
		} catch (IOException ioe) {
			logger.error("Cannot stop sendStream: " + ioe.toString());
		}
		lived= false;
		super.interrupt();
	}

	private void setupTransmitter() {
		SendStream sendStream;
		Processor myProcessor;
		
		myProcessor = send.getProcessor();
		PushBufferDataSource pbds = (PushBufferDataSource) myProcessor.getDataOutput();
		PushBufferStream pbss[] = pbds.getStreams();

		if(pbss.length>1){
			logger.error("No puede ser mayor de uno");
			return;
		}
		
		for (int i = 0; i < pbss.length; i++) {
			try {
				sendStream = session.getRTPMgr().createSendStream(myProcessor.getDataOutput(), i);
				sendStream.start();
				send.setStream(sendStream);
				logger.debug("stream started.");
			} catch (Exception oops) {
				logger.error(oops.toString());
			}
		}
	}

	private void setupProcessor() {
		Processor myProcessor;
		DataSource audioDS;
		try {
			
			
			audioDS = send.getDataSource();
			
			myProcessor = Manager.createProcessor(audioDS);
			boolean result = waitForState(myProcessor, Processor.Configured);
			if (result == false)
				logger.error("Couldn't configure processor");

			TrackControl[] tracks = myProcessor.getTrackControls();
			if (tracks == null || tracks.length < 1)
				logger.error("Couldn't find tracks in processor");

			ContentDescriptor cd = new ContentDescriptor(ContentDescriptor.MIXED);
			myProcessor.setContentDescriptor(cd);

			Format supported[];
			Format chosen;
			boolean  audioOk = false;

			Codec[] codec = new Codec[2];
			codec[0] = encoder;
			codec[1] = packetizer;

			for (int i = 0; i < tracks.length; i++) {
				logger.debug(tracks[i] + " Format:" + tracks[i].getFormat());
			}

			for (int i = 0; i < tracks.length; i++) {
				tracks[i].setFormat(new AudioFormat(AudioFormat.ALAW, -1.0, 8, 1, -1, AudioFormat.SIGNED, 8, -1.0,
						Format.byteArray));
				tracks[i].setCodecChain(codec);
			}

			for (int i = 0; i < tracks.length; i++) {
				logger.debug(tracks[i] + " Format:" + tracks[i].getFormat());
			}

			// Program the etracks.
			for (int i = 0; i < tracks.length; i++) {
				if (tracks[i].isEnabled()) {
					supported = tracks[i].getSupportedFormats();
					if (supported.length > 0) {
						chosen = new AudioFormat(AudioFormat.ALAW, -1.0, 8, 1, -1, AudioFormat.SIGNED, 8, -1.0,
									Format.byteArray);
						audioOk = true;

						tracks[i].setFormat(chosen);
						logger.debug("Track[" + i + "] is set to transmet as:");
						logger.debug("  " + chosen);
					}
					else
						// not supported tracks will be disabled
						tracks[i].setEnabled(false);
				} // fi (tracks[i].isEnabled())
				else
					tracks[i].setEnabled(false);
			} // rof // program the tracks

			if (!(audioOk))
				logger.error("Couldn't set any of the tracks");

			result = this.waitForState(myProcessor, Controller.Realized);
			if (result == false)
				logger.error("Couldn't realize processor");

			myProcessor.start();
			send.setProcessor(myProcessor);
		} catch (NoProcessorException npe) {
			logger.error("Couldn't create processor: " + npe.toString());
		} catch (IOException ioe) {
			logger.error("IOException creating processor: " + ioe.toString());
		} catch (Exception oops) {
			logger.error("Processor: " + oops.toString());
		}
	}

	private void initJMF() {
		DataSource audioDS;
		try {

			audioDS = new MiDataSource(audio);
			audioDS.setLocator(new MediaLocator(" javasound://8000/16/1/little/signed"));
			audioDS.connect();
			send.setDataSource(audioDS);
			
		} catch (Exception oops) {
			logger.error("initJMF: " + oops.toString());
		}
	}

	private synchronized boolean waitForState(Processor p, int state) {
		p.addControllerListener(new StateListener());
		this.failed = false;

		if (state == Processor.Configured)
			p.configure();
		else if (state == Processor.Realized)
			p.realize();

		// wait
		while (p.getState() < state && !failed) {
			synchronized (getStateLock()) {
				try {
					getStateLock().wait();
				} catch (InterruptedException ie) {
					return false;
				}
			}
		}

		if (this.failed)
			return false;
		else
			return true;
	}

	private Integer stateLock = new Integer(0);

	public Integer getStateLock() {
		return stateLock;
	}

	private boolean failed = false;

	public void setFailed() {
		this.failed = true;
	}

	class StateListener implements ControllerListener {
		public void controllerUpdate(ControllerEvent ce) {
			if (ce instanceof ControllerClosedEvent)
				setFailed();

			if (ce instanceof ControllerEvent) {
				synchronized (getStateLock()) {
					getStateLock().notifyAll();
				}
			}
		}
	}

}
