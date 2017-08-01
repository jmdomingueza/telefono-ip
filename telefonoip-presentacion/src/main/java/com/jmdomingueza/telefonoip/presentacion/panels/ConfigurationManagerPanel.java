package com.jmdomingueza.telefonoip.presentacion.panels;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Vector;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.negocio.beans.Audio;
import com.jmdomingueza.telefonoip.negocio.factories.BeanFactory;
import com.jmdomingueza.telefonoip.negocio.services.ConfigurationService;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;

/**
 * Clase que implementa un panel donde aparece la configuracion de las tarjetas de
 * sonido para el microfono y el altavoz
 * @author jmdomingueza
 *
 */
public class ConfigurationManagerPanel extends DefaultPanel{
	
	private static final long serialVersionUID = -1530408955639589460L;

	private static final Log logger = LogFactory.getLog(ConfigurationManagerPanel.class);
	
	/**
	 * Etiqueta del titulo del sonido
	 */
	private JLabel titleAudioLabel;
	
	/**
	 * Etiqueta del microfono
	 */
	private JLabel microLabel;
	
	/**
	 * ComboBox los microfonos de las tarjetas de sonido que pueden ser 
	 * selecionadas
	 */
	protected JComboBox<String> microComboBox;
	
	/**
	 * Etiqueta del Altavoz
	 */
	private JLabel speakerLabel;
	
	/**
	 * ComboBox los altavoces de las tarjetas de sonido que pueden ser 
	 * selecionadas
	 */
	protected JComboBox<String> speakerComboBox;
	
	/**
	 * Constructor de la clase
	 * @param title
	 */
	public ConfigurationManagerPanel(String title) {
		super(title);
		
		logger.trace("Enter ConfigurationManagerPanel");
		
		logger.trace("Exit ConfigurationManagerPanel");
	}
	
	@Override
	protected JComponent createMainPane() {
		
		logger.trace("Enter createMainPane");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		JPanel audioPanel = createAudioPanel();
			
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
			.addComponent(audioPanel)
		);
		//Layout Vertical
		layoutGroup.setVerticalGroup(layoutGroup.createSequentialGroup()
			.addComponent(audioPanel)
		);

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		logger.trace("Exit createMainPane");
		return panel;
	}

	
	private JPanel createAudioPanel() {
		logger.trace("Enter createAudioPanel");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		titleAudioLabel = ComponentFactory.createLabel("main.panel.configurationmanager.label.titleaudio");
		
		microLabel = ComponentFactory.createLabel("main.panel.configurationmanager.label.micro");
		
		Vector<String> microVector = createMicroVector();
		microComboBox = ComponentFactory.createComboBox(microVector);
		microComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigurationService Service = CTX.ctx.getBean(ConfigurationService.class);
				Service.configureMicrophone(BeanFactory.createAudio((String) ((JComboBox<?>)e.getSource()).getSelectedItem(), Audio.TYPE_MICROFONO));
			}
		});
		
		speakerLabel = ComponentFactory.createLabel("main.panel.configurationmanager.label.speaker");
		
		Vector<String> speakerVector = createSpeakerVector();
		speakerComboBox = ComponentFactory.createComboBox(speakerVector);
		speakerComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigurationService Service = CTX.ctx.getBean(ConfigurationService.class);
				Service.configureSpeaker(BeanFactory.createAudio((String) ((JComboBox<?>)e.getSource()).getSelectedItem(), Audio.TYPE_ALTAVOCES));
			}
		});
				
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
			.addComponent(titleAudioLabel)
			.addGroup(layoutGroup.createSequentialGroup()
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(microLabel)
					.addComponent(speakerLabel)
				)
				.addGap(10)
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(microComboBox)
					.addComponent(speakerComboBox)
				)
			)	
		);
		//Layout Vertical
		layoutGroup.setVerticalGroup(layoutGroup.createSequentialGroup()
			.addComponent(titleAudioLabel)
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(microLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				
				.addComponent(microComboBox, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
			)
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(speakerLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(speakerComboBox, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
			)
		);
		
		logger.trace("Exit createAudioPanel");
		return panel;
	}

	private Vector<String> createMicroVector() {
		Vector<String> vector;
		
		logger.trace("Enter createMicroVector");
		
		vector = new Vector<>();
		Mixer.Info[] arrayMixerInfo = AudioSystem.getMixerInfo();
		for (int i = 0; i < arrayMixerInfo.length; i++) {
			if ((arrayMixerInfo[i].getName().contains(Audio.TYPE_MICROFONO) && !arrayMixerInfo[i].getName().contains("Port")) || 
					(arrayMixerInfo[i].getName().contains(Audio.TYPE_MICROPHONE) && !arrayMixerInfo[i].getName().contains("Port"))) {
				logger.debug("Mixer.Info: " + arrayMixerInfo[i]);
				vector.add(arrayMixerInfo[i].getName());
			}
		}
		
		logger.trace("Exit createMicroVector");
		return vector;
	}
	
	private Vector<String> createSpeakerVector() {
		Vector<String> vector;
		
		logger.trace("Enter createSpeakerVector");
		
		vector = new Vector<>();
		Mixer.Info[] arrayMixerInfo = AudioSystem.getMixerInfo();
		for (int i = 0; i < arrayMixerInfo.length; i++) {
			if ((arrayMixerInfo[i].getName().contains(Audio.TYPE_ALTAVOCES) && !arrayMixerInfo[i].getName().contains("Port"))|| 
					(arrayMixerInfo[i].getName().contains(Audio.TYPE_SPEAKERS) && !arrayMixerInfo[i].getName().contains("Port"))) {
				logger.debug("Mixer.Info: " + arrayMixerInfo[i]);
				vector.add(arrayMixerInfo[i].getName());
			}
		}
		
		logger.trace("Exit createSpeakerVector");
		return vector;
	}

}
