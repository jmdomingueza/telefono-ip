package audio;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jmdomingueza.telefonoip.rtp.audio.AudioUtil;
import com.jmdomingueza.telefonoip.rtp.audio.microphone.MiJavaSoundSourceStream;
import com.jmdomingueza.telefonoip.rtp.audio.speaker.MiJavaSoundOutput;
import com.jmdomingueza.telefonoip.rtp.beans.Audio;
import com.jmdomingueza.telefonoip.rtp.factories.BeanRtpFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class AudioTest {

	@Test
	public void testBasico() {
		SourceDataLine sourceDataLine;
		TargetDataLine targetDataLine;

		try{
			AudioFormat afmt = new AudioFormat(44100, 16, 2, true, false);
			Mixer.Info infoMicro = AudioUtil.getMixerInfo(BeanRtpFactory.createAudio("High Definition", Audio.TYPE_MICROPHONE));
			Mixer.Info infoSpeaker = AudioUtil.getMixerInfo(BeanRtpFactory.createAudio("High Definition", Audio.TYPE_SPEAKERS));

			sourceDataLine = ((SourceDataLine)AudioSystem.getSourceDataLine(afmt,infoSpeaker));
			//sourceDataLine.open(afmt);

			//sourceDataLine.close();

			//targetDataLine = AudioSystem.getTargetDataLine(afmt, infoMicro);
			targetDataLine = AudioSystem.getTargetDataLine(afmt);

			targetDataLine.open(afmt);
			targetDataLine.start();
			targetDataLine.stop();
			targetDataLine.close();
			targetDataLine.open(afmt);
			targetDataLine.open(afmt);

			System.out.println("pepe");
		}catch(Exception e){

		}
	}
}
