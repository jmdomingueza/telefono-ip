package com.jmdomingueza.telefonoip.rtp.audio.microphone;

import java.awt.Component;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.Control;
import javax.media.Format;
import javax.media.MediaLocator;
import javax.media.Owned;
import javax.media.control.BufferControl;
import javax.media.control.FormatControl;
import javax.media.protocol.BufferTransferHandler;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PushBufferStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

import com.jmdomingueza.telefonoip.rtp.audio.AudioUtil;
import com.jmdomingueza.telefonoip.rtp.audio.speaker.MiJavaSoundOutput;
import com.jmdomingueza.telefonoip.rtp.beans.Audio;
import com.sun.media.CircularBuffer;
import com.sun.media.JMFSecurity;
import com.sun.media.JMFSecurityManager;
import com.sun.media.Log;
import com.sun.media.protocol.BasicSourceStream;
import com.sun.media.protocol.javasound.jdk12CreateThreadAction;
import com.sun.media.ui.AudioFormatChooser;
import com.sun.media.util.Arch;
import com.sun.media.util.jdk12;

public class MiJavaSoundSourceStream extends BasicSourceStream implements PushBufferStream {

	private Audio audio;

	MiDataSource dsource;
	TargetDataLine dataLine = null;
	javax.media.format.AudioFormat format;
	javax.media.format.AudioFormat devFormat;
	boolean reconnect = false;
	int bufSize;
	BufferTransferHandler transferHandler;
	boolean started = false;
	AudioFormatChooser afc;
	BufferControl bc;
	CircularBuffer cb = new CircularBuffer(1);
	MiPushThread pushThread = null;

	static int DefRate = 44100;
	static int DefBits = 16;
	static int DefChannels = 2;
	static int DefSigned = 1;
	static int DefEndian = Arch.isBigEndian() ? 1 : 0;

	static int OtherEndian = Arch.isBigEndian() ? 1 : 0;
	static Format[] supported;
	protected static CaptureDeviceInfo[] deviceList;
	private static JMFSecurity jmfSecurity = null;
	private static boolean securityPrivelege = false;
	private Method[] mSecurity = new Method[1];
	private Class[] clSecurity = new Class[1];
	private Object[][] argsSecurity = new Object[1][0];
	static int DefaultMinBufferSize;
	static int DefaultMaxBufferSize;
	long bufLenReq = 125L;

	public MiJavaSoundSourceStream(MiDataSource ds, Audio audio) {
		super(new ContentDescriptor("raw"), -1L);
		this.dsource = ds;

		this.bc = new BC(this);

		this.controls = new Control[2];
		this.controls[0] = new FC(this);
		this.controls[1] = this.bc;
		this.audio = audio;
	}

	public static Format parseLocator(MediaLocator ml) {
		String rateStr = null;
		String bitsStr = null;
		String channelsStr = null;
		String endianStr = null;
		String signedStr = null;

		String remainder = ml.getRemainder();
		if ((remainder != null) && (remainder.length() > 0)) {
			while ((remainder.length() > 1) && (remainder.charAt(0) == '/')) {
				remainder = remainder.substring(1);
			}
			int off = remainder.indexOf('/');
			if (off == -1) {
				if (!remainder.equals(""))
					rateStr = remainder;
			} else {
				rateStr = remainder.substring(0, off);
				remainder = remainder.substring(off + 1);

				off = remainder.indexOf('/');
				if (off == -1) {
					if (!remainder.equals(""))
						bitsStr = remainder;
				} else {
					bitsStr = remainder.substring(0, off);
					remainder = remainder.substring(off + 1);

					off = remainder.indexOf('/');
					if (off == -1) {
						if (!remainder.equals(""))
							channelsStr = remainder;
					} else {
						channelsStr = remainder.substring(0, off);
						remainder = remainder.substring(off + 1);

						off = remainder.indexOf('/');
						if (off == -1) {
							if (!remainder.equals(""))
								endianStr = remainder;
						} else {
							endianStr = remainder.substring(0, off);
							if (!remainder.equals("")) {
								signedStr = remainder.substring(off + 1);
							}
						}
					}
				}
			}
		}

		int rate = DefRate;
		if (rateStr != null) {
			try {
				Integer integer = Integer.valueOf(rateStr);
				if (integer != null)
					rate = integer.intValue();
			} catch (Throwable t) {
			}
			if ((rate <= 0) || (rate > 96000)) {
				Log.warning("JavaSound capture: unsupported sample rate: " + rate);
				rate = DefRate;
				Log.warning("        defaults to: " + rate);
			}

		}

		int bits = DefBits;
		if (bitsStr != null) {
			try {
				Integer integer = Integer.valueOf(bitsStr);
				if (integer != null)
					bits = integer.intValue();
			} catch (Throwable t) {
			}
			if ((bits != 8) && (bits != 16)) {
				Log.warning("JavaSound capture: unsupported sample size: " + bits);
				bits = DefBits;
				Log.warning("        defaults to: " + bits);
			}

		}

		int channels = DefChannels;
		if (channelsStr != null) {
			try {
				Integer integer = Integer.valueOf(channelsStr);
				if (integer != null)
					channels = integer.intValue();
			} catch (Throwable t) {
			}
			if ((channels != 1) && (channels != 2)) {
				Log.warning("JavaSound capture: unsupported # of channels: " + channels);
				channels = DefChannels;
				Log.warning("        defaults to: " + channels);
			}

		}

		int endian = DefEndian;
		if (endianStr != null) {
			if (endianStr.equalsIgnoreCase("big")) {
				endian = 1;
			} else if (endianStr.equalsIgnoreCase("little")) {
				endian = 0;
			} else {
				Log.warning("JavaSound capture: unsupported endianess: " + endianStr);
				Log.warning("        defaults to: big endian");
			}

		}

		int signed = DefSigned;
		if (signedStr != null) {
			if (signedStr.equalsIgnoreCase("signed")) {
				signed = 1;
			} else if (signedStr.equalsIgnoreCase("unsigned")) {
				signed = 0;
			} else {
				Log.warning("JavaSound capture: unsupported signedness: " + signedStr);
				Log.warning("        defaults to: signed");
			}

		}

		javax.media.format.AudioFormat fmt = new javax.media.format.AudioFormat("LINEAR", rate, bits, channels, endian,
				signed);

		return fmt;
	}

	public Format setFormat(Format fmt) {
		if (this.started) {
			Log.warning("Cannot change audio capture format after started.");
			return this.format;
		}

		if (fmt == null) {
			return this.format;
		}
		Format f = null;
		for (int i = 0; i < supported.length; i++) {
			if ((fmt.matches(supported[i])) && ((f = fmt.intersects(supported[i])) != null)) {
				break;
			}
		}

		if (f == null)
			return this.format;
		try {
			if (this.devFormat != null) {
				if ((!this.devFormat.matches(f)) && (!MiJavaSoundOutput.isOpen())) {
					this.format = ((javax.media.format.AudioFormat) f);
					disconnect();
					connect();
				}
			} else {
				this.format = ((javax.media.format.AudioFormat) f);
				connect();
			}
		} catch (IOException e) {
			return null;
		}

		if (this.afc != null) {
			this.afc.setCurrentFormat(this.format);
		}
		return this.format;
	}

	public boolean isConnected() {
		return this.devFormat != null;
	}

	public void connect() throws IOException {
		if (isConnected()) {
			return;
		}
		if (MiJavaSoundOutput.isOpen()) {
			Log.warning("JavaSound is already opened for rendering.  Will capture at the default format.");
			this.format = null;
		}

		openDev();

		if (this.pushThread == null) {
			if (jmfSecurity != null) {
				String permission = null;
				try {
					if (jmfSecurity.getName().startsWith("jmf-security")) {
						permission = "thread";
						jmfSecurity.requestPermission(this.mSecurity, this.clSecurity, this.argsSecurity, 16);

						this.mSecurity[0].invoke(this.clSecurity[0], this.argsSecurity[0]);

						permission = "thread group";
						jmfSecurity.requestPermission(this.mSecurity, this.clSecurity, this.argsSecurity, 32);

						this.mSecurity[0].invoke(this.clSecurity[0], this.argsSecurity[0]);
					} else if (jmfSecurity.getName().startsWith("internet")) {

					}

				} catch (Throwable e) {
					securityPrivelege = false;
				}

			}

			if ((jmfSecurity != null) && (jmfSecurity.getName().startsWith("jdk12"))) {
				try {
					Constructor cons = jdk12CreateThreadAction.cons;

					this.pushThread = ((MiPushThread) jdk12.doPrivM.invoke(jdk12.ac,
							new Object[] { cons.newInstance(new Object[] { MiPushThread.class }) }));
				} catch (Exception e) {
				}

			} else {
				this.pushThread = new MiPushThread();
			}

			this.pushThread.setSourceStream(this);
		}

		if (this.reconnect)
			Log.comment("Capture buffer size: " + this.bufSize);
		this.devFormat = this.format;
		this.reconnect = false;
	}

	void openDev() throws IOException {
		javax.sound.sampled.AudioFormat afmt = null;
		Mixer.Info info;
		if (this.format != null) {
			afmt = MiJavaSoundOutput.convertFormat(this.format);
			int chnls = this.format.getChannels() == -1 ? 1 : this.format.getChannels();

			int size = this.format.getSampleSizeInBits() == -1 ? 16 : this.format.getSampleSizeInBits();

			int frameSize = size * chnls / 8;

			if (frameSize == 0) {
				frameSize = 1;
			}
			this.bufSize = (int) (this.format.getSampleRate() * frameSize * this.bc.getBufferLength() / 1000.0D);

			info = AudioUtil.getMixerInfo(audio);
		} else {
			info = AudioUtil.getMixerInfo(audio);
		}

		if (!AudioUtil.isLineSupported(audio)) {
			Log.error("Audio not supported: " + info + "\n");
			throw new IOException("Cannot open audio device for input.");
		}

		try {
			this.dataLine = AudioSystem.getTargetDataLine(afmt, info);
			if (this.format != null) {
				this.dataLine.open(afmt, this.bufSize);
			} else {
				this.dataLine.open(afmt);
				this.format = MiJavaSoundOutput.convertFormat(this.dataLine.getFormat());
			}

			this.bufSize = this.dataLine.getBufferSize();
		} catch (Exception e) {
			Log.error("Cannot open audio device for input: " + e);
			throw new IOException(e.getMessage());
		}
	}

	public void disconnect() {
		if (this.dataLine == null) {
			return;
		}

		this.dataLine.stop();
		this.dataLine.close();

		this.dataLine = null;
		this.devFormat = null;
		if (this.pushThread != null) {
			this.pushThread.kill();
			this.pushThread = null;
		}
	}

	public void start()
	    throws IOException
	  {
		Format fmt;
	    if (this.dataLine == null)
	      throw new IOException("A JavaSound input channel cannot be opened.");
	    if (this.started) {
	      return;
	    }

	    if (this.afc != null)
	    {
	      if (((fmt = this.afc.getFormat()) != null) && (!fmt.matches(this.format)) &&
	        (setFormat(fmt) == null));
	      this.afc.setEnabled(false);
	    }

	    if (this.reconnect) {
	      disconnect();
	    }

	    if (!isConnected()) {
	      connect();
	    }

	    synchronized (this.cb) {
	      while (this.cb.canRead()) {
	        this.cb.read();
	        this.cb.readReport();
	      }
	      this.cb.notifyAll();
	    }

	    this.pushThread.start();
	    this.dataLine.flush();
	    this.dataLine.start();
	    this.started = true;
	  }

	public void stop() throws IOException {
		if (!this.started) {
			return;
		}
		this.pushThread.pause();
		if (this.dataLine != null)
			this.dataLine.stop();
		this.started = false;
		if ((this.afc != null) && (!MiJavaSoundOutput.isOpen()))
			this.afc.setEnabled(true);
	}

	public Format getFormat() {
		return this.format;
	}

	public Object[] getControls() {
		return this.controls;
	}

	public static Format[] getSupportedFormats() {
		return supported;
	}

	public static CaptureDeviceInfo[] listCaptureDeviceInfo() {
		return deviceList;
	}

	public void setTransferHandler(BufferTransferHandler th) {
		this.transferHandler = th;
	}

	public boolean willReadBlock() {
		return !this.started;
	}

	public void read(Buffer in)
	  {
	    Buffer buffer;
	    synchronized (this.cb) {
	      while (!this.cb.canRead())
	        try {
	          this.cb.wait();
	        } catch (Exception e) {
	        }
	      buffer = this.cb.read();
	    }

	    Object data = in.getData();
	    in.copy(buffer);
	    buffer.setData(data);

	    synchronized (this.cb) {
	      this.cb.readReport();
	      this.cb.notify();
	    }
	  }

	static {
		try {
			jmfSecurity = JMFSecurityManager.getJMFSecurity();
			securityPrivelege = true;
		} catch (SecurityException e) {
		}
		supported = new Format[] { new javax.media.format.AudioFormat("LINEAR", 44100.0D, 16, 2, DefEndian, DefSigned),
				new javax.media.format.AudioFormat("LINEAR", 44100.0D, 16, 1, DefEndian, DefSigned),
				new javax.media.format.AudioFormat("LINEAR", 22050.0D, 16, 2, DefEndian, DefSigned),
				new javax.media.format.AudioFormat("LINEAR", 22050.0D, 16, 1, DefEndian, DefSigned),
				new javax.media.format.AudioFormat("LINEAR", 11025.0D, 16, 2, DefEndian, DefSigned),
				new javax.media.format.AudioFormat("LINEAR", 11025.0D, 16, 1, DefEndian, DefSigned),
				new javax.media.format.AudioFormat("LINEAR", 8000.0D, 16, 2, DefEndian, DefSigned),
				new javax.media.format.AudioFormat("LINEAR", 8000.0D, 16, 1, DefEndian, DefSigned) };

		deviceList = new CaptureDeviceInfo[] {
				new CaptureDeviceInfo("JavaSound audio capture", new MediaLocator("javasound://44100"), supported) };

		DefaultMinBufferSize = 16;
		DefaultMaxBufferSize = 4000;
	}

	class BC implements BufferControl, Owned {
		MiJavaSoundSourceStream jsss;

		BC(MiJavaSoundSourceStream js) {
			this.jsss = js;
		}

		public long getBufferLength() {
			return MiJavaSoundSourceStream.this.bufLenReq;
		}

		public long setBufferLength(long time) {
			if (time < MiJavaSoundSourceStream.DefaultMinBufferSize)
				MiJavaSoundSourceStream.this.bufLenReq = MiJavaSoundSourceStream.DefaultMinBufferSize;
			else if (time > MiJavaSoundSourceStream.DefaultMaxBufferSize)
				MiJavaSoundSourceStream.this.bufLenReq = MiJavaSoundSourceStream.DefaultMaxBufferSize;
			else
				MiJavaSoundSourceStream.this.bufLenReq = time;
			Log.comment("Capture buffer length set: " + MiJavaSoundSourceStream.this.bufLenReq);
			MiJavaSoundSourceStream.this.reconnect = true;
			return MiJavaSoundSourceStream.this.bufLenReq;
		}

		public long getMinimumThreshold() {
			return 0L;
		}

		public long setMinimumThreshold(long time) {
			return 0L;
		}

		public void setEnabledThreshold(boolean b) {
		}

		public boolean getEnabledThreshold() {
			return false;
		}

		public Component getControlComponent() {
			return null;
		}

		public Object getOwner() {
			return MiJavaSoundSourceStream.this.dsource;
		}
	}

	class FC implements FormatControl, Owned {
		MiJavaSoundSourceStream jsss;

		public FC(MiJavaSoundSourceStream jsss) {
			this.jsss = jsss;
		}

		public Object getOwner() {
			return MiJavaSoundSourceStream.this.dsource;
		}

		public Format getFormat() {
			return MiJavaSoundSourceStream.this.format;
		}

		public Format setFormat(Format fmt) {
			return this.jsss.setFormat(fmt);
		}

		public Format[] getSupportedFormats() {
			return MiJavaSoundSourceStream.supported;
		}

		public boolean isEnabled() {
			return true;
		}

		public void setEnabled(boolean enabled) {
		}

		public Component getControlComponent() {
			if (MiJavaSoundSourceStream.this.afc == null) {
				MiJavaSoundSourceStream.this.afc = new AudioFormatChooser(MiJavaSoundSourceStream.supported,
						MiJavaSoundSourceStream.this.format);
				MiJavaSoundSourceStream.this.afc.setName("JavaSound");
				if ((MiJavaSoundSourceStream.this.started) || (MiJavaSoundSourceStream.this.dataLine == null)
						|| (MiJavaSoundOutput.isOpen()))
					MiJavaSoundSourceStream.this.afc.setEnabled(false);
			}
			return MiJavaSoundSourceStream.this.afc;
		}
	}
}