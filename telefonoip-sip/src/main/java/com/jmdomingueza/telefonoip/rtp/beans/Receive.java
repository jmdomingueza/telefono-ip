package com.jmdomingueza.telefonoip.rtp.beans;

import java.io.Serializable;

import javax.media.Player;
import javax.media.Processor;
import javax.media.rtp.ReceiveStream;

/**
 * Interfaz que relaciona el ReceiveStream, el Processor y el Player de un 
 * NewReceiveStreamEvent
 * @author jmdomingueza
 *
 */
public interface Receive extends Serializable {
	
	/**
	 * Metodo que devuelve el Player
	 * @return
	 */
	public Player getPlayer();

	/**
	 * Metodo que pone el player
	 * @param player
	 */
	public void setPlayer(Player player);

	/**
	 * Metodo que devuelve el Stream
	 * @return
	 */
	public ReceiveStream getStream();

	/**
	 * Metodo que pone el Stream
	 * @param stream
	 */
	public void setStream(ReceiveStream stream);

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
	 */
	public void close();

}
