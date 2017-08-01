package com.jmdomingueza.telefonoip.rtp.audio.speaker;

import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Panel;

import javax.media.Buffer;
import javax.media.Codec;
import javax.media.Control;
import javax.media.Format;
import javax.media.Owned;
import javax.media.ResourceUnavailableException;
import javax.media.format.AudioFormat;

import com.jmdomingueza.telefonoip.rtp.beans.Audio;
import com.sun.media.ExclusiveUse;
import com.sun.media.JMFSecurityManager;
import com.sun.media.SimpleGraphBuilder;
import com.sun.media.controls.GainControlAdapter;
import com.sun.media.renderer.audio.AudioRenderer;
import com.sun.media.renderer.audio.device.AudioOutput;

public class MiJavaSoundRenderer extends AudioRenderer implements ExclusiveUse{

	  static String NAME = "JavaSound Renderer";
	  Codec ulawDecoder;
	  Format ulawOutputFormat;
	  Format ulawFormat;
	  Format linearFormat;
	  static int METERHEIGHT = 4;
	  static boolean available = false;
	  Format[] supportedFormats;

	  Buffer decodeBuffer = null;

	  private Audio audio;

	  public MiJavaSoundRenderer(Audio audio)
	  {
	    if (!available) {
	      throw new UnsatisfiedLinkError("No JavaSound library");
	    }
	    this.ulawFormat = new AudioFormat("ULAW");

	    this.linearFormat = new AudioFormat("LINEAR");

	    this.supportedFormats = new Format[2];
	    this.supportedFormats[0] = this.linearFormat;
	    this.supportedFormats[1] = this.ulawFormat;

	    this.gainControl = new GCA(this);
	    this.peakVolumeMeter = new PeakVolumeMeter(this);

	    this.audio =  audio;
	  }

	  public String getName() {
	    return NAME;
	  }

	  @Override
	  public Format setInputFormat(Format format) {
		    for (int i = 0; i < this.supportedFormats.length; i++) {
		      if (this.supportedFormats[i].matches(format)) {
		        this.inputFormat = ((AudioFormat)format);
		        return format;
		      }
		    }
		    return null;
		  }

	  public void open() throws ResourceUnavailableException
	  {
	    if ((this.device == null) && (this.inputFormat != null)) {
	      if (!initDevice(this.inputFormat))
	        throw new ResourceUnavailableException("Cannot intialize audio device for playback");
	      this.device.pause();
	    }
	  }

	  public boolean isExclusive()
	  {
	    return false;
	  }

	  protected boolean initDevice(AudioFormat in)
	  {
	    Format newInput = in;

	    if (this.ulawDecoder != null) {
	      this.ulawDecoder.close();
	      this.ulawDecoder = null;
	    }

	    Format[] outs = new Format[1];
	    if (this.ulawFormat.matches(in))
	    {
	      this.ulawDecoder = SimpleGraphBuilder.findCodec(in, this.linearFormat, null, outs);
	      if (this.ulawDecoder != null)
	        this.ulawOutputFormat = (newInput = outs[0]);
	      else {
	        return false;
	      }
	    }
	    this.devFormat = in;

	    return super.initDevice((AudioFormat)newInput);
	  }

	  protected AudioOutput createDevice(AudioFormat format) {
	    return new MiJavaSoundOutput(this.audio);
	  }

	  public int processData(Buffer buffer)
	  {
	    if (!checkInput(buffer)) {
	      return 1;
	    }

	    if (this.ulawDecoder == null) {
	      try {
	        ((PeakVolumeMeter)this.peakVolumeMeter).processData(buffer);
	      } catch (Throwable t) {
	        t.printStackTrace();
	      }
	      return super.doProcessData(buffer);
	    }

	    if (this.decodeBuffer == null) {
	      this.decodeBuffer = new Buffer();
	      this.decodeBuffer.setFormat(this.ulawOutputFormat);
	    }

	    this.decodeBuffer.setLength(0);
	    this.decodeBuffer.setOffset(0);
	    this.decodeBuffer.setFlags(buffer.getFlags());
	    this.decodeBuffer.setTimeStamp(buffer.getTimeStamp());
	    this.decodeBuffer.setSequenceNumber(buffer.getSequenceNumber());

	    int rc = this.ulawDecoder.process(buffer, this.decodeBuffer);

	    if (rc == 0) {
	      try {
	        ((PeakVolumeMeter)this.peakVolumeMeter).processData(this.decodeBuffer);
	      } catch (Throwable t) {
	        System.err.println(t);
	      }
	      return super.doProcessData(this.decodeBuffer);
	    }

	    return 1;
	  }

	  public Object[] getControls() {
	    Control[] c = { this.gainControl, this.bufferControl, this.peakVolumeMeter };

	    return c;
	  }

	  static
	  {
	    String javaVersion = null;
	    String subver = null;
	    try
	    {
	      javaVersion = System.getProperty("java.version");
	      int len;
	      if (javaVersion.length() < 3)
	        len = javaVersion.length();
	      else
	        len = 3;
	      subver = javaVersion.substring(0, len);
	    } catch (Throwable t) {
	      javaVersion = null;
	      subver = null;
	    }

	    if ((subver == null) || (subver.compareTo("1.3") < 0))
	      try {
	        JMFSecurityManager.loadLibrary("jmutil");
	        JMFSecurityManager.loadLibrary("jsound");
	        available = true;
	      }
	      catch (Throwable t) {
	      }
	    else available = true;
	  }

	  class PeakVolumeMeter
	    implements Control, Owned
	  {
	    int averagePeak = 0;
	    int lastPeak = 0;

	    Panel component = null;
	    Checkbox cbEnabled = null;
	    Canvas canvas = null;
	    AudioRenderer renderer;
	    long lastResetTime;
	    Graphics cGraphics = null;

	    public PeakVolumeMeter(AudioRenderer r) {
	      this.renderer = r;
	      this.lastResetTime = System.currentTimeMillis();
	    }

	    public Object getOwner() {
	      return this.renderer;
	    }

	    public Component getControlComponent() {
	      if (this.component == null) {
	        this.canvas = null;

	        this.cbEnabled = new Checkbox("Peak Volume Meter", false);
	        this.component = new Panel();
	        this.component.add(this.cbEnabled);
	        this.component.add(this.canvas);
	        this.canvas.setBackground(Color.black);
	      }
	      return this.component;
	    }

	    public void processData(Buffer buf) {
	      AudioFormat af = (AudioFormat)buf.getFormat();
	      int index = 0;
	      int peak = 0;
	      int inc = 2;
	      if (this.component == null)
	        return;
	      if (!this.cbEnabled.getState())
	        return;
	      byte[] data = (byte[])buf.getData();

	      if (buf.isDiscard())
	        return;
	      if (buf.getLength() <= 0)
	        return;
	      if (af.getEndian() == 0) {
	        index = 1;
	      }
	      boolean signed = af.getSigned() == 1;
	      if (af.getSampleSizeInBits() == 8)
	        inc = 1;
	      if (signed) {
	        for (int i = index; i < buf.getLength(); i += inc * 5) {
	          int d = data[i];
	          if (d < 0)
	            d = -d;
	          if (d > peak)
	            peak = d;
	        }
	        peak = peak * 100 / 127;
	      } else {
	        for (int i = index; i < buf.getLength(); i += inc * 5) {
	          if ((data[i] & 0xFF) > peak)
	            peak = data[i] & 0xFF;
	        }
	        peak = peak * 100 / 255;
	      }
	      this.averagePeak = ((peak + this.averagePeak) / 2);
	      long currentTime = System.currentTimeMillis();
	      if (currentTime > this.lastResetTime + 100L) {
	        this.lastResetTime = currentTime;
	        updatePeak(this.averagePeak);
	        this.averagePeak = peak;
	      }
	    }

	    private void updatePeak(int newPeak) {
	      if (this.canvas == null)
	        return;
	      if (this.cGraphics == null) {
	        this.cGraphics = this.canvas.getGraphics();
	      }
	      if (this.cGraphics == null)
	        return;
	      if (newPeak > 99)
	        newPeak = 99;
	      this.cGraphics.setColor(Color.green);
	      if (newPeak < 80) {
	        this.cGraphics.drawLine(1, 1, newPeak + 1, 1);
	        this.cGraphics.drawLine(1, 2, newPeak + 1, 2);
	      } else {
	        this.cGraphics.drawLine(1, 1, 81, 1);
	        this.cGraphics.drawLine(1, 2, 81, 2);
	        this.cGraphics.setColor(Color.yellow);
	        if (newPeak < 90) {
	          this.cGraphics.drawLine(81, 1, newPeak + 1, 1);
	          this.cGraphics.drawLine(81, 2, newPeak + 1, 2);
	        } else {
	          this.cGraphics.drawLine(81, 1, 91, 1);
	          this.cGraphics.drawLine(81, 2, 91, 2);
	          this.cGraphics.setColor(Color.red);
	          this.cGraphics.drawLine(91, 1, newPeak + 1, 1);
	          this.cGraphics.drawLine(91, 2, newPeak + 1, 2);
	        }
	      }
	      this.cGraphics.setColor(Color.black);
	      this.cGraphics.drawLine(newPeak + 2, 1, 102, 1);
	      this.cGraphics.drawLine(newPeak + 2, 2, 102, 2);
	      this.lastPeak = newPeak;
	    }
	  }

	  class GCA extends GainControlAdapter
	  {
		  MiJavaSoundRenderer renderer;

	    protected GCA(MiJavaSoundRenderer r)
	    {
	      super();
	      this.renderer = r;
	    }

	    public void setMute(boolean mute) {
	      if ((this.renderer != null) && (this.renderer.device != null))
	        this.renderer.device.setMute(mute);
	      super.setMute(mute);
	    }

	    public float setLevel(float g) {
	      float level = super.setLevel(g);
	      if ((this.renderer != null) && (this.renderer.device != null))
	        this.renderer.device.setGain(getDB());
	      return level;
	    }
	  }
}
