package com.jmdomingueza.telefonoip.rtp.audio.microphone;

import javax.media.Buffer;
import javax.media.SystemTimeBase;
import javax.media.protocol.BufferTransferHandler;

import com.sun.media.CircularBuffer;
import com.sun.media.util.LoopThread;

public class MiPushThread extends LoopThread {

	private MiJavaSoundSourceStream sourceStream;
	private SystemTimeBase systemTimeBase = new SystemTimeBase();
	private long seqNo = 0L;

	public MiPushThread() {
		setName("JavaSound PushThread");
	}

	void setSourceStream(MiJavaSoundSourceStream ss) {
		this.sourceStream = ss;
	}

	protected boolean process()
	  {
	    CircularBuffer cb = this.sourceStream.cb;
	    BufferTransferHandler transferHandler = this.sourceStream.transferHandler;
	    Buffer buffer;
	    synchronized (cb) {
	      while (!cb.canWrite())
	        try {
	          cb.wait();
	        } catch (java.lang.Exception e) {
	        }
	      buffer = cb.getEmptyBuffer();
	    }
	    byte[] data;
	    if ((buffer.getData() instanceof byte[]))
	      data = (byte[])buffer.getData();
	    else {
	      data = null;
	    }
	    if ((data == null) || (data.length < this.sourceStream.bufSize)) {
	      data = new byte[this.sourceStream.bufSize];
	      buffer.setData(data);
	    }

	    int len = this.sourceStream.dataLine.read(data, 0, this.sourceStream.bufSize);

	    buffer.setOffset(0);
	    buffer.setLength(len);
	    buffer.setFormat(this.sourceStream.format);
	    buffer.setFlags(0x80 | 0x8000);
	    buffer.setSequenceNumber(this.seqNo++);

	    buffer.setTimeStamp(this.systemTimeBase.getNanoseconds());

	    synchronized (cb) {
	      cb.writeReport();
	      cb.notify();

	      if (transferHandler != null) {
	        transferHandler.transferData(this.sourceStream);
	      }
	    }
	    return true;
	  }
}