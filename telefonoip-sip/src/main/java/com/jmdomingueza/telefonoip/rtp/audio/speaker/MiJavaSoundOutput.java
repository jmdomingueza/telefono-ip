package com.jmdomingueza.telefonoip.rtp.audio.speaker;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

import com.jmdomingueza.telefonoip.rtp.audio.AudioUtil;
import com.jmdomingueza.telefonoip.rtp.beans.Audio;
import com.sun.media.Log;
import com.sun.media.renderer.audio.device.AudioOutput;

public class MiJavaSoundOutput implements AudioOutput
{
	  static Mixer mixer = null;
	  static Object initSync = new Object();
	  protected SourceDataLine dataLine;
	  protected FloatControl gc;
	  protected FloatControl rc;
	  protected BooleanControl mc;
	  protected boolean paused = true;
	  protected int bufSize;
	  protected javax.media.format.AudioFormat format;
	  long lastPos = 0L;
	  long originPos = 0L;
	  long totalCount = 0L;

	  private Audio audio;

	  public MiJavaSoundOutput(Audio audio){
		  super();
		  this.audio = audio;
	  }

	  public boolean initialize(javax.media.format.AudioFormat format, int bufSize)
	  {
	    synchronized (initSync)
	    {
	      javax.sound.sampled.AudioFormat afmt = convertFormat(format);

	      Mixer.Info info = AudioUtil.getMixerInfo(this.audio);
	      try
	      {
	        if (!AudioUtil.isLineSupported(this.audio)) {
	          Log.warning("DataLine not supported: " + format);
	          return false;
	        }

	        this.dataLine = ((SourceDataLine)AudioSystem.getSourceDataLine(afmt,info));
	        this.dataLine.open(afmt, bufSize);
	      }
	      catch (Exception e) {
	        Log.warning("Cannot open audio device: " + e);
	        return false;
	      }

	      this.format = format;
	      this.bufSize = bufSize;

	      if (this.dataLine == null) {
	        Log.warning("JavaSound unsupported format: " + format);
	        return false;
	      }
	      try
	      {
	        this.gc = ((FloatControl)this.dataLine.getControl(FloatControl.Type.MASTER_GAIN));
	        this.mc = ((BooleanControl)this.dataLine.getControl(BooleanControl.Type.MUTE));
	      } catch (Exception e) {
	        Log.warning("JavaSound: No gain control");
	      }
	      try
	      {
	        this.rc = ((FloatControl)this.dataLine.getControl(FloatControl.Type.SAMPLE_RATE));
	      } catch (Exception e) {
	        Log.warning("JavaSound: No rate control");
	      }

	      return true;
	    }
	  }

	  public void dispose()
	  {
	    this.dataLine.close();
	  }

	  public void finalize() throws Throwable
	  {
	    super.finalize();
	    dispose();
	  }

	  public void pause()
	  {
	    if (this.dataLine != null)
	      this.dataLine.stop();
	    this.paused = true;
	  }

	  public void resume()
	  {
	    if (this.dataLine != null)
	      this.dataLine.start();
	    this.paused = false;
	  }

	  public void drain()
	  {
	    this.dataLine.drain();
	  }

	  public void flush()
	  {
	    this.dataLine.flush();
	  }

	  public javax.media.format.AudioFormat getFormat()
	  {
	    return this.format;
	  }

	  public long getMediaNanoseconds()
	  {
	    if ((this.dataLine == null) || (this.format == null)) {
	      return 0L;
	    }
	    long pos = this.dataLine.getFramePosition();

	    if (pos < this.lastPos)
	    {
	      this.totalCount += this.lastPos - this.originPos;
	      this.originPos = pos;
	    }

	    this.lastPos = pos;

	    return (long)((this.totalCount + pos - this.originPos) * 1000L / this.format.getSampleRate() * 1000000.0D);
	  }

	  public void setGain(double g) {
	    if (this.gc != null)
	      this.gc.setValue((float)g);
	  }

	  public double getGain()
	  {
	    return this.gc != null ? this.gc.getValue() : 0.0D;
	  }

	  public void setMute(boolean m)
	  {
	    if (this.mc != null)
	      this.mc.setValue(m);
	  }

	  public boolean getMute()
	  {
	    return this.mc != null ? this.mc.getValue() : false;
	  }

	  public float setRate(float r)
	  {
	    if (this.rc == null) {
	      return 1.0F;
	    }
	    float rate = (float)(r * this.format.getSampleRate());

	    if ((rate > this.rc.getMaximum()) || (rate < this.rc.getMinimum())) {
	      return getRate();
	    }
	    this.rc.setValue(rate);

	    return r;
	  }

	  public float getRate()
	  {
	    if (this.rc == null)
	      return 1.0F;
	    return (float)(this.rc.getValue() / this.format.getSampleRate());
	  }

	  public int bufferAvailable()
	  {
	    return this.dataLine.available();
	  }

	  public int write(byte[] data, int off, int len)
	  {
	    return this.dataLine.write(data, off, len);
	  }

	  public static boolean isOpen()
	  {
		Mixer mixer = AudioSystem.getMixer(null);
	    Line[] lines = mixer.getSourceLines();

	    return (lines != null) && (lines.length > 0);
	  }

	  public static javax.media.format.AudioFormat convertFormat(javax.sound.sampled.AudioFormat fmt)
	  {
	    AudioFormat.Encoding type = fmt.getEncoding();
	    String encoding;
	    if ((type == AudioFormat.Encoding.PCM_SIGNED) || (type == AudioFormat.Encoding.PCM_UNSIGNED))
	    {
	      encoding = "LINEAR";
	    } else if (type == AudioFormat.Encoding.ALAW)
	      encoding = "alaw";
	    else if (type == AudioFormat.Encoding.ULAW)
	      encoding = "ULAW";
	    else {
	      encoding = null;
	    }
	    return new javax.media.format.AudioFormat(encoding, fmt.getSampleRate(), fmt.getSampleSizeInBits(), fmt.getChannels(), fmt.isBigEndian() ? 1 : 0, type == AudioFormat.Encoding.PCM_SIGNED ? 1 : 0);
	  }

	  public static javax.sound.sampled.AudioFormat convertFormat(javax.media.format.AudioFormat fmt)
	  {
	    return new javax.sound.sampled.AudioFormat(fmt.getSampleRate() == -1.0D ? 8000.0F : (float)fmt.getSampleRate(), fmt.getSampleSizeInBits() == -1 ? 16 : fmt.getSampleSizeInBits(), fmt.getChannels() == -1 ? 1 : fmt.getChannels(), fmt.getSigned() == 1, fmt.getEndian() == 1);
	  }
}