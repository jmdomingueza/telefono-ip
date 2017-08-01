package com.jmdomingueza.telefonoip.sip.utils;

import gov.nist.core.net.DefaultNetworkLayer;
import gov.nist.core.net.NetworkLayer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class QOSLayer implements NetworkLayer {
	/**
	 * Logger of the class
	 */
	private static Log logger = LogFactory.getLog(QOSLayer.class);


	public DatagramSocket createDatagramSocket() throws SocketException {
		logger.trace("enter  createDatagramSocket");
		logger.trace("exit  createDatagramSocket");
		return DefaultNetworkLayer.SINGLETON.createDatagramSocket();
	}

	public DatagramSocket createDatagramSocket(int arg0, InetAddress arg1)
			throws SocketException {
		logger.trace("enter  createDatagramSocket");
		logger.trace("exit  createDatagramSocket");
		return DefaultNetworkLayer.SINGLETON.createDatagramSocket(arg0,arg1);
	}

	public SSLServerSocket createSSLServerSocket(int arg0, int arg1,
			InetAddress arg2) throws IOException {
		logger.trace("enter  createSSLServerSocket");
		logger.trace("exit  createSSLServerSocket");
		return DefaultNetworkLayer.SINGLETON.createSSLServerSocket(arg0, arg1, arg2);
	}

	public SSLSocket createSSLSocket(InetAddress arg0, int arg1)
			throws IOException {
		logger.trace("enter  createSSLSocket");
		logger.trace("exit  createSSLSocket");
		return DefaultNetworkLayer.SINGLETON.createSSLSocket(arg0, arg1);
	}

	public SSLSocket createSSLSocket(InetAddress arg0, int arg1,
			InetAddress arg2) throws IOException {
		logger.trace("enter  createSSLSocket");
		logger.trace("exit  createSSLSocket");
		return DefaultNetworkLayer.SINGLETON.createSSLSocket(arg0, arg1,arg2);
	}

	public ServerSocket createServerSocket(int arg0, int arg1, InetAddress arg2)
			throws IOException {
		logger.trace("enter  createServerSocket");
		logger.trace("exit  createServerSocket");
		return DefaultNetworkLayer.SINGLETON.createServerSocket(arg0, arg1,arg2);
	}

	public Socket createSocket(InetAddress arg0, int arg1) throws IOException {
		logger.trace("enter  createSocket");
		logger.trace("exit  createSocket");
		return DefaultNetworkLayer.SINGLETON.createSocket(arg0, arg1);
	}

	public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2)
			throws IOException {
		logger.trace("enter  createSocket");
		logger.trace("exit  createSocket");
		return DefaultNetworkLayer.SINGLETON.createSocket(arg0, arg1,arg2);
	}


}
