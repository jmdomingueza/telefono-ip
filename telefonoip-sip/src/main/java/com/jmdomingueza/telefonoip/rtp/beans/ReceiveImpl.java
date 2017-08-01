package com.jmdomingueza.telefonoip.rtp.beans;

import javax.media.Player;
import javax.media.Processor;
import javax.media.rtp.ReceiveStream;

/**
 * Clase que relaciona el ReceiveStream, el Processor y el Player de un 
 * NewReceiveStreamEvent
 * @author jmdomingueza
 *
 */
public class ReceiveImpl implements Receive{

	private static final long serialVersionUID = 2506177723775100076L;

	/**
	 * Player
	 */
	private Player player;
	
	/**
	 * ReceiveStream
	 */
	private ReceiveStream stream;
	
	/**
	 * Processor
	 */
	private Processor processor;

	
	/**
	 * Metodo que devuelve el Player
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Metodo que pone el player
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Metodo que devuelve el Stream
	 * @return
	 */
	public ReceiveStream getStream() {
		return stream;
	}

	/**
	 * Metodo que pone el Stream
	 * @param stream
	 */
	public void setStream(ReceiveStream stream) {
		this.stream = stream;
	}

	/**
	 * Metodo que devuelve le Processor
	 * @return
	 */
	public Processor getProcessor() {
		return processor;
	}

	/**
	 * Metodo que pone el Processor
	 * @param processor
	 */
	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	/**
	 * Metodo que cierra el player y el Processor
	 */
	public void close()
	{
		player.close();
		processor.close();
	}

}
