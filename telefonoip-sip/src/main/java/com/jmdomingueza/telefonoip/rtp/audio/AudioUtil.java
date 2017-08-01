package com.jmdomingueza.telefonoip.rtp.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.rtp.beans.Audio;

/**
 * Clase que implementa varios metodos estaticos para usar la libreria
 * AudioSystem
 * 
 * @author jmdomingueza
 *
 */
public class AudioUtil {

	/**
	 * Logger de las clases
	 */
	private static Log logger = LogFactory.getLog(AudioUtil.class);

	/**
	 * Metodo que devuelve si el AudioBean tiene una linea que lo soporte
	 * 
	 * @param audio
	 * @return
	 */
	public static boolean isLineSupported(Audio audio) {
		String type;

		logger.trace("Enter isLineSupported");

		type = audio.getType();
		Mixer.Info info = getMixerInfo(audio);
		if (info != null) {
			Mixer mixer = AudioSystem.getMixer(getMixerInfo(audio));
			if (mixer != null) {
				if (type != null && !type.equals("")
						&& (type.equals(Audio.TYPE_SPEAKERS) || type.equals(Audio.TYPE_ALTAVOCES))) {
					Line.Info[] arrayLineInfo = mixer.getSourceLineInfo();
					for (int i = 0; i < arrayLineInfo.length; i++) {
						if (arrayLineInfo[i].getLineClass().equals(SourceDataLine.class)) {
							logger.trace("Exit isLineSupported TYPE_SPEAKERS || TYPE_ALTAVOCES");
							return AudioSystem.isLineSupported(arrayLineInfo[i]);
						}
					}
				} else if (type != null && !type.equals("")
						&& (type.equals(Audio.TYPE_MICROPHONE) || type.equals(Audio.TYPE_MICROFONO))) {
					Line.Info[] arrayLineInfo = mixer.getTargetLineInfo();
					for (int i = 0; i < arrayLineInfo.length; i++) {
						if (arrayLineInfo[i].getLineClass().equals(TargetDataLine.class)) {
							logger.trace("Enter isLineSupported TYPE_MICROPHONE || TYPE_MICROFONO");
							return AudioSystem.isLineSupported(arrayLineInfo[i]);
						}
					}
				}
			}
		}
		logger.trace("Exit isLineSupported false");
		return false;
	}

	/**
	 * Metodo que devuelve el Mixer.Info de un AudioBean. Si no existe se
	 * devuelve null
	 * 
	 * @param audio
	 * @return
	 */
	public static Mixer.Info getMixerInfo(Audio audio) {
		String nameInfo, type;

		logger.trace("Enter getMixerInfo");

		nameInfo = audio.getNameInfo();
		type = audio.getType();
		Mixer.Info[] arrayMixerInfo = AudioSystem.getMixerInfo();
		logger.debug("Mixer.Info: name:" + nameInfo);
		logger.debug("Mixer.Info: type:" + type);
		for (int i = 0; i < arrayMixerInfo.length; i++) {
			if (nameInfo != null && !nameInfo.equals("") && arrayMixerInfo[i].getName().contains(nameInfo)
					&& arrayMixerInfo[i].getName().contains(type)) {
				logger.debug("Mixer.Info: " + arrayMixerInfo[i]);
				logger.trace("Exit getMixerInfo arrayMixerInfo[i]");
				return arrayMixerInfo[i];
			}
		}
		logger.debug("Mixer.Info: null");

		logger.trace("Exit getMixerInfo null");
		return null;
	}

}
