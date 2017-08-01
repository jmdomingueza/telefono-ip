package com.jmdomingueza.telefonoip.rtp.beans;

import java.io.Serializable;

import javax.media.rtp.RTPManager;

import com.jmdomingueza.telefonoip.rtp.ReceiveThread;
import com.jmdomingueza.telefonoip.rtp.TransmitThread;

public interface SessionRtp  extends Serializable{

	public String getDestAddress();

	public void setDestAddress(String destAddress);

	public int getDestPort();

	public void setDestPort(int destPort);

	public int getLocalPort();

	public void setLocalPort(int localPort);

	public int getTtl();

	public void setTtl(int ttl);

	public RTPManager getRTPMgr();

	public void setRTPMgr(RTPManager rtpMgr);

	public TransmitThread getTransmitThread();

	public void setTransmitThread(TransmitThread transmit);

	public ReceiveThread getReceiveThread() ;

	public void setReceiveThread(ReceiveThread receive);
}
