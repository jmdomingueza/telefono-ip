package com.jmdomingueza.telefonoip.rtp;

/*
 * @(#)ReceiveTest.java 1.3 01/03/13 Copyright (c) 1999-2001 Sun Microsystems,
 * Inc. All Rights Reserved. Sun grants you ("Licensee") a non-exclusive,
 * royalty free, license to use, modify and redistribute this software in source
 * and binary code form, provided that i) this copyright notice and license
 * appear on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun. This software is provided
 * "AS IS," without a warranty of any kind. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
 * CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS
 * OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE
 * SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear facility.
 * Licensee represents and warrants that it will not use or redistribute the
 * Software for such purposes.
 */

import java.util.Vector;

import javax.media.Codec;
import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerErrorEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.NotConfiguredError;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;
import javax.media.UnsupportedPlugInException;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;
import javax.media.protocol.DataSource;
import javax.media.rtp.Participant;
import javax.media.rtp.RTPControl;
import javax.media.rtp.RTPManager;
import javax.media.rtp.ReceiveStream;
import javax.media.rtp.ReceiveStreamListener;
import javax.media.rtp.SessionListener;
import javax.media.rtp.event.ByeEvent;
import javax.media.rtp.event.NewParticipantEvent;
import javax.media.rtp.event.NewReceiveStreamEvent;
import javax.media.rtp.event.ReceiveStreamEvent;
import javax.media.rtp.event.RemotePayloadChangeEvent;
import javax.media.rtp.event.SessionEvent;
import javax.media.rtp.event.StreamMappedEvent;

import com.jmdomingueza.telefonoip.rtp.audio.AudioUtil;
import com.jmdomingueza.telefonoip.rtp.audio.speaker.MiMediaPlayer;
import com.jmdomingueza.telefonoip.rtp.beans.Audio;
import com.jmdomingueza.telefonoip.rtp.beans.Receive;
import com.jmdomingueza.telefonoip.rtp.beans.ReceiveImpl;
import com.jmdomingueza.telefonoip.rtp.beans.SessionRtp;
import com.jmdomingueza.telefonoip.rtp.factories.BeanRtpFactory;

import net.sf.fmj.media.codec.audio.alaw.DePacketizer;
import net.sf.fmj.media.codec.audio.alaw.Decoder;
import net.sf.fmj.media.codec.audio.alaw.Encoder;
import net.sf.fmj.media.codec.audio.alaw.Packetizer;

/**
 * ReceiveTest to receive RTP transmission using the new RTP API.
 */
public class ReceiveThread extends Thread implements ReceiveStreamListener, SessionListener, ControllerListener {
	private SessionRtp session;
	private Audio audio;

	private Vector<Receive> receiveStreams = null;

	boolean lived = false;

	static Encoder encoder = new Encoder();
	static Decoder decoder = new Decoder();
	static Packetizer packetizer = new Packetizer();
	static DePacketizer dePacketizer = new DePacketizer();

	/**
	 * Constructor de la clase
	 * 
	 * @param addr
	 * @param port
	 * @param ttl
	 */
	public ReceiveThread(SessionRtp session,Audio audio) throws RTPException{
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
		if(!AudioUtil.isLineSupported(audio))
			throw new RTPException("Audio : "+audio+" - no supported");
		
		
		this.receiveStreams = new Vector<>();
	}

	protected void initialize() {
		session.getRTPMgr().addSessionListener(this);
		session.getRTPMgr().addReceiveStreamListener(this);
	}

	@Override
	public void run() {
		this.lived = true;
		initialize();

		while (lived==true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
		}
	}

	@Override
	public void interrupt() {

		if(receiveStreams!=null){
			for (int i = 0; i < receiveStreams.size(); i++) {
				try {
					((Receive) receiveStreams.elementAt(i)).close();
				} catch (Exception e) {
				}
			}
			receiveStreams.removeAllElements();
		}
		if(session != null){
			RTPManager rtpMgr = session.getRTPMgr();
			if (rtpMgr != null) {
				rtpMgr.removeReceiveStreamListener(this);
				rtpMgr.removeSessionListener(this);

			}
		}
		lived = false;
		super.interrupt();
	}

	@Override
	public synchronized void update(SessionEvent evt) {
		if (evt instanceof NewParticipantEvent) {
			Participant p = ((NewParticipantEvent) evt).getParticipant();
			System.err.println("  - A new participant had just joined: " + p.getCNAME());
		}
	}

	@Override
	public synchronized void update(ReceiveStreamEvent evt) {
		Participant participant = evt.getParticipant(); // could be null.
		ReceiveStream stream = evt.getReceiveStream(); // could be null.

		if (evt instanceof RemotePayloadChangeEvent) {

			System.err.println("  - Received an RTP PayloadChangeEvent.");
			System.err.println("Sorry, cannot handle payload change.");
			System.exit(0);

		}

		else if (evt instanceof NewReceiveStreamEvent) {

			try {
				DataSource ds = stream.getDataSource();

				// Find out the formats.
				RTPControl ctl = (RTPControl) ds.getControl("javax.media.rtp.RTPControl");
				if (ctl != null) {
					System.err.println("  - Recevied new RTP stream: " + ctl.getFormat());
				} else
					System.err.println("  - Recevied new RTP stream");

				if (participant == null)
					System.err.println("      The sender of this stream had yet to be identified.");
				else {
					System.err.println("      The stream comes from: " + participant.getCNAME());
				}

				// create a player by passing datasource to the Media Manager
				Processor pr = Manager.createProcessor(ds);
				if (pr == null)
					return;

				pr.addControllerListener(this);

				pr.configure();
				Receive rs = BeanRtpFactory.createReceive(pr, stream, null);
				receiveStreams.addElement(rs);

			} catch (Exception e) {
				System.err.println("NewReceiveStreamEvent exception " + e.getMessage());
				return;
			}

		}

		else if (evt instanceof StreamMappedEvent) {

			if (stream != null && stream.getDataSource() != null) {
				DataSource ds = stream.getDataSource();
				// Find out the formats.
				RTPControl ctl = (RTPControl) ds.getControl("javax.media.rtp.RTPControl");
				System.err.println("  - The previously unidentified stream ");
				if (ctl != null)
					System.err.println("      " + ctl.getFormat());
				System.err.println("      had now been identified as sent by: " + participant.getCNAME());
			}
		}

		else if (evt instanceof ByeEvent) {

			System.err.println("  - Got \"bye\" from: " + participant.getCNAME());
			Receive rs = find(stream);
			if (rs != null) {
				rs.close();
				receiveStreams.removeElement(rs);
			}
		}

	}

	@Override
	public synchronized void controllerUpdate(ControllerEvent ce) {

		if (ce.getSourceController() == null)
			return;

		// Get this when the internal players are realized.
		if (ce instanceof ConfigureCompleteEvent) {
			if (ce.getSourceController() instanceof Processor) {
				Processor p = (Processor) ce.getSourceController();
				TrackControl[] trackIn = p.getTrackControls();

				for (int i = 0; i < trackIn.length; i++) {
					((TrackControl) trackIn[i]).setEnabled(true);
				}
				Codec[] codec = new Codec[2];
				codec[0] = dePacketizer;
				codec[1] = decoder;
				try {
					for (int i = 0; i < trackIn.length; i++) {
						((TrackControl) trackIn[i]).setCodecChain(codec);
					}
				} catch (UnsupportedPlugInException e) {
					System.err.println(e);
				} catch (NotConfiguredError e) {
					System.err.println(e);
				}

				for (int i = 0; i < trackIn.length; i++) {
					((TrackControl) trackIn[i]).setFormat(new AudioFormat(AudioFormat.LINEAR, 8000, 16, 1, AudioFormat.LITTLE_ENDIAN, 1));
				}
				p.realize();
			}
		} // Get this when the internal players are realized.
		else if (ce instanceof RealizeCompleteEvent) {
			if (ce.getSourceController() instanceof Processor) {
				try {
					Processor pr = (Processor) ce.getSourceController();

					Receive rs = find(pr);
					Player pl = new MiMediaPlayer(audio);
					pl.setSource(pr.getDataOutput());
					rs.setPlayer(pl);
					pl.addControllerListener(this);

					pl.realize();
					pr.start();
				} catch (Exception e) {
					System.err.println("RealizeCompleteEvent exception " + e.getMessage());
					return;
				}
			} else if (ce.getSourceController() instanceof Player) {
				Player pl = (Player) ce.getSourceController();
				pl.start();
			}
		}

		if (ce instanceof ControllerErrorEvent) {
			if (ce.getSourceController() instanceof Processor) {
				Processor pr = (Processor) ce.getSourceController();
				pr.removeControllerListener(this);
				Receive rs = find(pr);
				if (rs != null) {
					rs.close();
					receiveStreams.removeElement(rs);
				}
				System.err.println("ReceiveTest internal error: " + ce);
			} else if (ce.getSourceController() instanceof Player) {
				Player pl = (Player) ce.getSourceController();
				pl.removeControllerListener(this);
				Receive rs = find(pl);
				if (rs != null) {
					rs.close();
					receiveStreams.removeElement(rs);
				}
				System.err.println("ReceiveTest internal error: " + ce);
			}
		}

	}

	private Receive find(Player pl) {
		for (int i = 0; i < receiveStreams.size(); i++) {
			Receive rs = (ReceiveImpl) receiveStreams.elementAt(i);
			if (rs.getPlayer() == pl)
				return rs;
		}
		return null;
	}

	private Receive find(Processor pr) {
		for (int i = 0; i < receiveStreams.size(); i++) {
			Receive rs = (ReceiveImpl) receiveStreams.elementAt(i);
			if (rs.getProcessor() == pr)
				return rs;
		}
		return null;
	}

	private Receive find(ReceiveStream strm) {
		for (int i = 0; i < receiveStreams.size(); i++) {
			Receive rs = (ReceiveImpl) receiveStreams.elementAt(i);
			if (rs.getStream() == strm)
				return rs;
		}
		return null;
	}

}// end of ReceiveTest
