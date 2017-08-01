package com.jmdomingueza.telefonoip.presentacion.panels;

import java.awt.ComponentOrientation;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;

/**
 * Clase que implementa el panel de gestion de llamadas.
 * @author jmdomingueza
 *
 */
public class CallManagerPanel extends DefaultPanel {
	
	private static final long serialVersionUID = 7749668494418887366L;
	
	private static final Log logger = LogFactory.getLog(CallManagerPanel.class);

	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");
	
	/**
	 * Constructor de la clase
	 * @param title
	 */
	public CallManagerPanel(String title) {
		super(title);
		
		logger.trace("Enter CallManagerPanel");
		logger.trace("Exit CallManagerPanel");
	}

	@Override
	protected JComponent createMainPane() {
		
		logger.trace("Enter createMainPane");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		CallDummyPanel callDummyPanel = new CallDummyPanel(null);
		callDummyPanel.init();
		CallSipPanel callSipPanel =new CallSipPanel(null);
		callSipPanel.init();
		tabbedPane.addTab(resourceService.getProperty("main.panel.callmanager.tab.dummy.text"),callDummyPanel);
		tabbedPane.addTab(resourceService.getProperty("main.panel.callmanager.tab.sip.text"),callSipPanel);
		
				
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(
			layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
				.addComponent(tabbedPane)
		);
		//Layout Vertical
		layoutGroup.setVerticalGroup(
			layoutGroup.createSequentialGroup()
				.addComponent(tabbedPane)
		);

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		logger.trace("Exit createMainPane");
		return panel;
	}

}
