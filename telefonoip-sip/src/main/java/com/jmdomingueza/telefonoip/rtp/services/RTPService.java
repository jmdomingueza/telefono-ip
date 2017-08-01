package com.jmdomingueza.telefonoip.rtp.services;

import com.jmdomingueza.telefonoip.rtp.beans.Audio;
import com.jmdomingueza.telefonoip.rtp.beans.SessionRtp;
import com.jmdomingueza.telefonoip.rtp.services.exceptions.CreateSessionException;
import com.jmdomingueza.telefonoip.rtp.services.exceptions.DestroySessionException;

public interface RTPService {


	public void createSession(SessionRtp session) throws CreateSessionException;

	public void destroySession(SessionRtp session) throws DestroySessionException;
	
	public void configureSpeaker(Audio audio);
	
	public void configureMicrophone(Audio audio);
	
}
