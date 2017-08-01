package com.jmdomingueza.telefonoip.rtp.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.media.Processor;
import javax.media.protocol.DataSource;
import javax.media.rtp.SendStream;

/**
 * Intefaz que relaciona el SendStream, el Processor y el DataSource de un 
 * TransmitThread
 * @author jmdomingueza
 *
 */
public interface Send  extends Serializable{

	/**
	 * Metodo que devuelve el DataSource
	 * @return
	 */
	public DataSource getDataSource();

	/**
	 * Metodo que pone el DataSource
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource);

	/**
	 * Metodo que devuelve el Stream
	 * @return
	 */
	public SendStream getStream();

	/**
	 * Metodo que pone el Stream
	 * @param stream
	 */
	public void setStream(SendStream stream);

	/**
	 * Metodo que devuelve le Processor
	 * @return
	 */
	public Processor getProcessor();

	/**
	 * Metodo que pone el Processor
	 * @param processor
	 */
	public void setProcessor(Processor processor);

	/**
	 * Metodo que cierra el player y el Processor
	 * @throws IOException 
	 */
	public void close() throws IOException;

}
