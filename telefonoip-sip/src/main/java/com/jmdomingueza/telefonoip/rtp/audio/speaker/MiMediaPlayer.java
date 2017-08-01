package com.jmdomingueza.telefonoip.rtp.audio.speaker;

import com.jmdomingueza.telefonoip.rtp.beans.Audio;
import com.sun.media.MediaPlayer;

public class MiMediaPlayer extends MediaPlayer {

	public MiMediaPlayer(Audio audio) {
		//Implicitamente llama al contructor de MediaPlayer. Tenemos que parar el PlaybackEngine
		if(engine!=null)
			this.engine.close();
		this.engine = new MiPlaybackEngine(this, audio);
	}
}
