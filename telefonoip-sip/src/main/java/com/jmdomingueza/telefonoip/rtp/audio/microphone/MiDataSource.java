package com.jmdomingueza.telefonoip.rtp.audio.microphone;

import com.jmdomingueza.telefonoip.rtp.beans.Audio;
import com.jmdomingueza.telefonoip.rtp.beans.AudioImpl;
import com.sun.media.JMFSecurityManager;
import com.sun.media.protocol.BasicPushBufferDataSource;

import java.io.IOException;

import javax.media.CaptureDeviceInfo;
import javax.media.Duration;
import javax.media.Time;
import javax.media.control.FormatControl;
import javax.media.protocol.CaptureDevice;
import javax.media.protocol.PushBufferStream;
import javax.media.protocol.RateConfiguration;
import javax.media.protocol.RateConfigureable;
import javax.media.protocol.RateRange;
import javax.media.protocol.SourceStream;

public class MiDataSource extends BasicPushBufferDataSource implements CaptureDevice, RateConfigureable {
	PushBufferStream[] streams = new PushBufferStream[0];
	MiJavaSoundSourceStream sourceStream = null;

	static String ContentType = "raw";

	public MiDataSource(Audio audio) {
		JMFSecurityManager.checkCapture();
		this.contentType = ContentType;
		this.duration = Duration.DURATION_UNBOUNDED;
		this.sourceStream = new MiJavaSoundSourceStream(this, audio);
		this.streams = new PushBufferStream[1];
		this.streams[0] = this.sourceStream;
	}

	public static CaptureDeviceInfo[] listCaptureDeviceInfo() {
		return MiJavaSoundSourceStream.listCaptureDeviceInfo();
	}

	public CaptureDeviceInfo getCaptureDeviceInfo() {
		return MiJavaSoundSourceStream.listCaptureDeviceInfo()[0];
	}

	public FormatControl[] getFormatControls() {
		FormatControl[] fc = new FormatControl[1];
		fc[0] = ((FormatControl) this.sourceStream.getControl("javax.media.control.FormatControl"));
		return fc;
	}

	public PushBufferStream[] getStreams() {
		if (this.streams == null) {
			System.err.println("DataSource needs to be connected before calling getStreams");
		}
		return this.streams;
	}

	public void connect() throws IOException {
		if (this.sourceStream.isConnected()) {
			return;
		}
		if (getLocator() != null)
			this.sourceStream.setFormat(MiJavaSoundSourceStream.parseLocator(getLocator()));
		this.sourceStream.connect();
	}

	public void disconnect() {
		this.sourceStream.disconnect();
	}

	public void start() throws IOException {
		this.sourceStream.start();
	}

	public void stop() throws IOException {
		this.sourceStream.stop();
	}

	public String getContentType() {
		return this.contentType;
	}

	public Time getDuration() {
		return this.duration;
	}

	boolean getStarted() {
		return this.started;
	}

	public Object[] getControls() {
		Object[] o = this.sourceStream.getControls();
		return o;
	}

	public Object getControl(String name) {
		return this.sourceStream.getControl(name);
	}

	public RateConfiguration[] getRateConfigurations() {
		RateConfiguration[] config = { new OneRateConfig() };
		return config;
	}

	public RateConfiguration setRateConfiguration(RateConfiguration config) {
		return config;
	}

	class OneRateConfig implements RateConfiguration {
		OneRateConfig() {
		}

		public RateRange getRate() {
			return new RateRange(1.0F, 1.0F, 1.0F, true);
		}

		public SourceStream[] getStreams() {
			SourceStream[] ss = { MiDataSource.this.sourceStream };
			return ss;
		}
	}
}