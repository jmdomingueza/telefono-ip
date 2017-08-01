package com.jmdomingueza.telefonoip.sip.services;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Vector;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Codec;
import javax.media.Control;
import javax.media.Controller;
import javax.media.ControllerClosedEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoProcessorException;
import javax.media.Owned;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.media.control.QualityControl;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.media.protocol.PushBufferDataSource;
import javax.media.protocol.PushBufferStream;
import javax.media.rtp.RTPManager;
import javax.media.rtp.SendStream;
import javax.media.rtp.SessionAddress;
import javax.swing.JFrame;

import com.jmdomingueza.telefonoip.rtp.audio.microphone.MiDataSource;
import com.jmdomingueza.telefonoip.rtp.audio.microphone.MiJavaSoundSourceStream;
import com.sun.media.util.Arch;

import net.sf.fmj.media.codec.audio.alaw.DePacketizer;
import net.sf.fmj.media.codec.audio.alaw.Decoder;
import net.sf.fmj.media.codec.audio.alaw.Encoder;
import net.sf.fmj.media.codec.audio.alaw.Packetizer;

public class TransmitTest extends JFrame
{
	String camDevice = "vfw:Microsoft WDM Image Capture (Win32):0";
	String audioDevice = "JavaSound audio capture";
	CaptureDeviceInfo videoCapturDeviceInfo, audioCaptureDeviceInfo;
	MediaLocator videoDeviceLocator, audioDeviceLocator, outputFileLocator;
	DataSource videoDS, audioDS, mixDS, rtpReadyDS;
	Component visualComp, nonVisComp;
	Player myPlayer;
	Processor myProcessor;
	ProcessorModel processorModel;
	DataSink mySink;
	RTPManager[] rtpMgrs;
	int portBase = 22222;
	//String ipAddress = "172.31.120.63";
	String ipAddress = "192.168.1.133";
	SendStream sendStream;

	static Encoder encoder = new Encoder();
	static Decoder decoder = new Decoder();
	static Packetizer packetizer = new Packetizer();
	static DePacketizer dePacketizer = new DePacketizer();


	public TransmitTest()
	{
		this.initJMF();
		this.setupProcessor();
		//this.setupPlayer();
		//this.setupPlayerUI();
		this.setupTransmitter();
		this.myProcessor.start();
		try
		{
			this.sendStream.start();
		}
		catch (Exception oops)
		{
			this.msg(oops.toString());
		}
	}

	private void setupPlayerUI()
	{
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				try
				{
					sendStream.stop();
				}
				catch (IOException ioe)
				{
					msg("Cannot stop sendStream: " + ioe.toString());
				}
				sendStream.close();

				myProcessor.stop();
				myProcessor.close();

				// myPlayer.stop();
				// myPlayer.close();
				System.exit(0);
			}
		});
		if ((this.visualComp = this.myPlayer.getVisualComponent()) != null)
			this.getContentPane().add(this.visualComp, BorderLayout.CENTER);
		else
			msg("no visual visual component");

		if ((this.nonVisComp = this.myPlayer.getControlPanelComponent()) != null)
			this.getContentPane().add(this.nonVisComp, BorderLayout.SOUTH);
		else
			msg("no control panel component");
		this.pack();
		this.setSize(new Dimension(400, 400));
		this.setVisible(true);
	}

	private void setupPlayer()
	{
		try
		{
			this.myPlayer = Manager.createRealizedPlayer(this.mixDS);
			this.myPlayer.start();
		}
		catch (Exception oops)
		{
			this.msg(oops.toString());
		}
	}

	private void setupTransmitter()
	{
		SessionAddress localAddr, destAddr;
		int port;
		InetAddress destIPAddr;

		PushBufferDataSource pbds = (PushBufferDataSource) this.myProcessor.getDataOutput();
		PushBufferStream pbss[] = pbds.getStreams();

		this.rtpMgrs = new RTPManager[pbss.length];

		for (int i = 0; i < pbss.length; i++)
		{
			try
			{
				this.rtpMgrs[i] = RTPManager.newInstance();

				port = portBase + 2 * i;

				localAddr = new SessionAddress(InetAddress.getLocalHost(), port);
				destAddr = new SessionAddress(InetAddress.getByName(this.ipAddress), port);

				this.rtpMgrs[i].initialize(localAddr);
				this.rtpMgrs[i].addTarget(destAddr);
				this.rtpMgrs[i].addFormat(new AudioFormat(AudioFormat.ALAW, -1.0, 8, 1, -1, AudioFormat.SIGNED, 8, -1.0, Format.byteArray), 8);

				this.msg("Created RTP session: from [" + InetAddress.getLocalHost().getHostAddress() + "] to [" + ipAddress + "]:/" + port);

				sendStream = rtpMgrs[i].createSendStream(this.myProcessor.getDataOutput(), i);
				sendStream.start();
				this.msg("stream started.");
			}
			catch (Exception oops)
			{
				this.msg(oops.toString());
			}
		}
	}

	private void setupProcessor()
	{
		try
		{
			this.myProcessor = Manager.createProcessor(this.audioDS);
			boolean result = waitForState(this.myProcessor, Processor.Configured);
			if (result == false) this.err("Couldn't configure processor");

			TrackControl[] tracks = this.myProcessor.getTrackControls();
			if (tracks == null || tracks.length < 1) this.err("Couldn't find tracks in processor");
		
			ContentDescriptor cd = new ContentDescriptor(ContentDescriptor.MIXED);
			this.myProcessor.setContentDescriptor(cd);

			Format supported[];
			Format chosen;
			boolean videoOk = false, audioOk = false;

			Codec[] codec = new Codec[2];
			codec[0] = encoder;
			codec[1] = packetizer;
			
			for (int i = 0; i < tracks.length; i++) {
				this.msg(tracks[i]+" Format:"+tracks[i].getFormat());
			}
			
			for (int i = 0; i < tracks.length; i++) {
				tracks[i].setFormat(new AudioFormat(AudioFormat.ALAW, -1.0, 8, 1, -1, AudioFormat.SIGNED, 8, -1.0, Format.byteArray));
				tracks[i].setCodecChain(codec);
			}
			
			for (int i = 0; i < tracks.length; i++) {
				this.msg(tracks[i]+" Format:"+tracks[i].getFormat());
			}


			// Program the etracks.
			for (int i = 0; i < tracks.length; i++)
			{
				Format format = tracks[i].getFormat();
				if (tracks[i].isEnabled())
				{
					supported = tracks[i].getSupportedFormats();
					if (supported.length > 0)
					{
						if (supported[0] instanceof VideoFormat)
						{
							chosen = checkForVideoSizes(tracks[i].getFormat(), supported[0]);
							videoOk = true;
						}// fi
						else
						{
							chosen = new AudioFormat(AudioFormat.ALAW, -1.0, 8, 1, -1, AudioFormat.SIGNED, 8, -1.0, Format.byteArray);//supported[0];
							audioOk = true;
						}// esle

						tracks[i].setFormat(chosen);
						this.msg("Track[" + i + "] is set to transmet as:");
						this.msg("  " + chosen);
					}// fi (supported.length > 0)
					else
						// not supported tracks will be disabled
						tracks[i].setEnabled(false);
				} // fi (tracks[i].isEnabled())
				else
					tracks[i].setEnabled(false);
			}// rof // program the tracks

			if (!(audioOk)) this.err("Couldn't set any of the tracks");

			result = this.waitForState(this.myProcessor, Controller.Realized);
			if (result == false) this.err("Couldn't realize processor");

			// set JPEG quality to 0.5
			this.setJPEGQuality(this.myProcessor, 0.5f);

			this.myProcessor.start();

			this.rtpReadyDS = this.myProcessor.getDataOutput();
		}
		catch (NoProcessorException npe)
		{
			this.msg("Couldn't create processor: " + npe.toString());
		}
		catch (IOException ioe)
		{
			this.msg("IOException creating processor: " + ioe.toString());
		}
		catch (Exception oops)
		{
			this.msg("Processor: " + oops.toString());
		}
	}

	private void initJMF()
	{
		try
		{
			VideoFormat videoFormat = new VideoFormat(VideoFormat.YUV);
			Vector<CaptureDeviceInfo> vectorVideo = CaptureDeviceManager.getDeviceList(videoFormat);
			for(CaptureDeviceInfo cdf : vectorVideo){
				System.out.println(cdf);
			}
			AudioFormat audioFormat = new AudioFormat(AudioFormat.LINEAR);
			Vector<CaptureDeviceInfo> vectorAudio = CaptureDeviceManager.getDeviceList(audioFormat);
			for(CaptureDeviceInfo cdf : vectorAudio){
				System.out.println(cdf);
			}
			
			int DefSigned = 1;
			int DefEndian = Arch.isBigEndian() ? 1 : 0;
			Format[] supported = new Format[] { new javax.media.format.AudioFormat("LINEAR", 8000.0D, 16, 2, DefEndian, DefSigned) };
			
		
			this.audioCaptureDeviceInfo = new CaptureDeviceInfo("JavaSound audio capture", new MediaLocator(" javasound://8000/16/1/little/signed"), supported);

			//this.videoCapturDeviceInfo = CaptureDeviceManager.getDevice(this.camDevice);
			//this.audioCaptureDeviceInfo = CaptureDeviceManager.getDevice(this.audioDevice);

			//this.videoDeviceLocator = this.videoCapturDeviceInfo.getLocator();
			this.audioDeviceLocator = this.audioCaptureDeviceInfo.getLocator();

			//this.videoDS = Manager.createDataSource(this.videoDeviceLocator);
			this.videoDS = null;
			//this.audioDS = new MiDataSource("USB PnP Sound",MiJavaSoundSourceStream.TYPE_MICROFONO);
			//this.audioDS.setLocator(this.audioCaptureDeviceInfo.getLocator());
			//this.audioDS.connect();
			this.audioDS = Manager.createDataSource(this.audioDeviceLocator);
			DataSource[] s = new DataSource[1];
			s[0] = this.audioDS;

			this.mixDS = Manager.createMergingDataSource(s);
			
			/*
			 * // FileTypeDescriptor outputType = new //
			 * FileTypeDescriptor(FileTypeDescriptor.MSVIDEO); //
			 * FileTypeDescriptor outputType = new //
			 * FileTypeDescriptor(FileTypeDescriptor.RAW_RTP); Format[]
			 * outputFormat = new Format[2]; ContentDescriptor outputDescriptor =
			 * new ContentDescriptor(ContentDescriptor.RAW_RTP); //
			 * outputFormat[0] = new VideoFormat(VideoFormat.YUV);
			 * outputFormat[0] = new VideoFormat(VideoFormat.MPEG_RTP); //
			 * outputFormat[1] = new AudioFormat(AudioFormat.LINEAR, 11025.0, 8, //
			 * 1); outputFormat[1] = new AudioFormat(AudioFormat.MPEG_RTP,
			 * 11025.0, 8, 1); // this.myPlayer =
			 * Manager.createRealizedPlayer(this.mixDS); //
			 * this.myPlayer.start(); this.processorModel = new
			 * ProcessorModel(this.mixDS, outputFormat, outputDescriptor);
			 */
		}
		catch (Exception oops)
		{
			this.msg("initJMF: " + oops.toString());
		}
	}

	private synchronized boolean waitForState(Processor p, int state)
	{
		p.addControllerListener(new StateListener());
		this.failed = false;

		if (state == Processor.Configured)
			p.configure();
		else if (state == Processor.Realized) p.realize();

		// wait
		while (p.getState() < state && !failed)
		{
			synchronized (getStateLock())
			{
				try
				{
					getStateLock().wait();
				}
				catch (InterruptedException ie)
				{
					return false;
				}
			}
		}

		if (this.failed)
			return false;
		else
			return true;
	}

	private Format checkForVideoSizes(Format original, Format supported)
	{
		int width, height;
		Dimension size = ((VideoFormat) original).getSize();

		Format jpegFmt = new Format(VideoFormat.JPEG_RTP);
		Format h263Fmt = new Format(VideoFormat.H263_RTP);

		if (supported.matches(jpegFmt))
		{
			// for JPEG make sure the width and height are divisible by 8
			width = (size.width % 8 == 0 ? size.width : (int) (size.width / 8) * 8);
			height = (size.height % 8 == 0 ? size.height : (int) (size.height / 8) * 8);
		}
		else if (supported.matches(h263Fmt))
		{
			// For H.263, we only support some specific sizes.
			if (size.width < 128)
			{
				width = 128;
				height = 96;
			}
			else if (size.width < 176)
			{
				width = 176;
				height = 144;
			}
			else
			{
				width = 352;
				height = 288;
			}
		}
		else
			return supported;

		return (new VideoFormat(null, new Dimension(width, height), Format.NOT_SPECIFIED, null, Format.NOT_SPECIFIED)).intersects(supported);
	}

	private void setJPEGQuality(Player p, float val)
	{
		Control cs[] = p.getControls();
		QualityControl qc = null;
		VideoFormat jpegFmt = new VideoFormat(VideoFormat.JPEG);

		// loop through the controls to find the Quality control for the JPEG
		// encoder
		for (int i = 0; i < cs.length; i++)
		{
			if (cs[i] instanceof QualityControl && cs[i] instanceof Owned)
			{
				Object owner = ((Owned) cs[i]).getOwner();

				// check to see if the owner is a codec
				// then check for the output format
				if (owner instanceof Codec)
				{
					Format fmts[] = ((Codec) owner).getSupportedOutputFormats(null);
					for (int j = 0; j < fmts.length; j++)
					{
						if (fmts[j].matches(jpegFmt))
						{
							qc = (QualityControl) cs[i];
							qc.setQuality(val);
							this.msg("- Setting quality to " + val + " on " + qc);
							break;
						}
					}
				}
				if (qc != null) break;
			}
		}
	}

	public void msg(String m)
	{
		System.out.println(m);
	}

	public void err(String m)
	{
		System.out.println(m);
		System.exit(-1);
	}

	private Integer stateLock = new Integer(0);

	public Integer getStateLock()
	{
		return stateLock;
	}

	private boolean failed = false;

	public void setFailed()
	{
		this.failed = true;
	}

	class StateListener implements ControllerListener
	{
		public void controllerUpdate(ControllerEvent ce)
		{
			if (ce instanceof ControllerClosedEvent) setFailed();

			if (ce instanceof ControllerEvent)
			{
				synchronized (getStateLock())
				{
					getStateLock().notifyAll();
				}
			}
		}
	}

	public static void main(String[] args)
	{
		//instalación de plugins en caso de que sea necesario
		try {

			PlugInManager.addPlugIn(encoder.getClass().getName(),
					encoder.getSupportedInputFormats(),
					encoder.getSupportedOutputFormats(null),
					PlugInManager.CODEC);

			PlugInManager.addPlugIn(decoder.getClass().getName(),
					decoder.getSupportedInputFormats(),
					decoder.getSupportedOutputFormats(null),
					PlugInManager.CODEC);

			PlugInManager.addPlugIn(packetizer.getClass().getName(),
	                packetizer.getSupportedInputFormats(),
	                packetizer.getSupportedOutputFormats(null),
	                PlugInManager.CODEC);
			PlugInManager.addPlugIn(dePacketizer.getClass().getName(),
	                dePacketizer.getSupportedInputFormats(),
	                dePacketizer.getSupportedOutputFormats(null),
	                PlugInManager.CODEC);

			PlugInManager.commit();
		} catch (Exception e) {
			System.err.println("Error al guardar los codec incluidos.");
		}
		TransmitTest tt = new TransmitTest();
	}

}
