package com.jmdomingueza.telefonoip.rtp.beans;

import java.io.IOException;

import javax.media.Processor;
import javax.media.protocol.DataSource;
import javax.media.rtp.SendStream;

/**
 * Clase que relaciona el SendStream, el Processor y el DataSource de un 
 * TransmitThread
 * @author jmdomingueza
 *
 */
public class SendImpl implements Send{

	
	private static final long serialVersionUID = 3941161261197891597L;

	/**
	 * DataSource
	 */
	private DataSource dataSource;
	
	/**
	 * SendStream
	 */
	private SendStream stream;
	
	/**
	 * Processor
	 */
	private Processor processor;

	
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public SendStream getStream() {
		return stream;
	}

	@Override
	public void setStream(SendStream stream) {
		this.stream = stream;
	}

	@Override
	public Processor getProcessor() {
		return processor;
	}

	@Override
	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	@Override
	public void close() throws IOException
	{

		if(stream!=null){
			try {
				stream.stop();
			} catch (IOException ioe) {
				throw ioe;
			}
			stream.close();
		}
		
		if(processor!=null){
			processor.stop();
			processor.close();
		}
	}

}
