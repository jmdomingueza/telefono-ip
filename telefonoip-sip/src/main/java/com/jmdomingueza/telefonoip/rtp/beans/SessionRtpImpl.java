package com.jmdomingueza.telefonoip.rtp.beans;

import javax.media.rtp.RTPManager;

import com.jmdomingueza.telefonoip.rtp.ReceiveThread;
import com.jmdomingueza.telefonoip.rtp.TransmitThread;

/**
 * Clase Bean con los datos de una session
 * @author jmdomingueza
 *
 */
public class SessionRtpImpl implements SessionRtp{
	
	private static final long serialVersionUID = -8379364588052339951L;

	/**
	 * Direccion destino de la session
	 */
	private String destAddress;
	
	/**
	 * Puerto destino de la session
	 */
	private int destPort;
	
	/**
	 * Puerto local de la session
	 */
	private int localPort;
	
	/**
	 * 
	 */
	private int ttl;
	
	/**
	 * Gestor de RTP
	 */
	private RTPManager rtpMgr;
	
	/**
	 * Hilo de transmitir
	 */
	private TransmitThread transmit;
	
	/**
	 * Hilo de recibir
	 */
	private ReceiveThread receive;

	@Override
	public String getDestAddress() {
		return destAddress;
	}

	@Override
	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}

	@Override
	public int getDestPort() {
		return destPort;
	}

	@Override
	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}

	@Override
	public int getLocalPort() {
		return localPort;
	}

	@Override
	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}

	@Override
	public int getTtl() {
		return ttl;
	}

	@Override
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	@Override
	public RTPManager getRTPMgr() {
		return rtpMgr;
	}

	@Override
	public void setRTPMgr(RTPManager rtpMgr) {
		this.rtpMgr = rtpMgr;
	}

	@Override
	public TransmitThread getTransmitThread() {
		return transmit;
	}

	@Override
	public void setTransmitThread(TransmitThread transmit) {
		this.transmit = transmit;
	}
	
	@Override
	public ReceiveThread getReceiveThread() {
		return receive;
	}

	@Override
	public void setReceiveThread(ReceiveThread receive) {
		this.receive = receive;
	}

	@Override
	public String toString() {
		return "SessionBean [destAddress=" + destAddress + ", destPort=" + destPort + ", localPort=" + localPort
				+ ", ttl=" + ttl + ", rtpMgr=" + rtpMgr + ", transmit=" + transmit + ", receive=" + receive + "]";
	}

	
}
