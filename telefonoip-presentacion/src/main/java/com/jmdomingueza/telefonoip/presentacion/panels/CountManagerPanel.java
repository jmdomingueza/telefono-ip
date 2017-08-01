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
 * Clase que implementa el panel de gestion de usuarios.
 * @author jmdomingueza
 *
 */
public class CountManagerPanel extends DefaultPanel {
	
	private static final long serialVersionUID = 7749668494418887366L;
	
	private static final Log logger = LogFactory.getLog(CountManagerPanel.class);

	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");
	
	/**
	 * Constructor de la clase
	 * @param title
	 */
	public CountManagerPanel(String title) {
		super(title);
		
		logger.trace("Enter CountManagerPanel");
		logger.trace("Exit CountManagerPanel");
	}

	@Override
	protected JComponent createMainPane() {
		
		logger.trace("Enter createMainPane");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		CountDummyPanel countDummyPanel = new CountDummyPanel(null);
		countDummyPanel.init();
		CountSipPanel countSipPanel =new CountSipPanel(null);
		countSipPanel.init();
		tabbedPane.addTab(resourceService.getProperty("main.panel.countmanager.tab.dummy.text"),countDummyPanel);
		tabbedPane.addTab(resourceService.getProperty("main.panel.countmanager.tab.sip.text"),countSipPanel);
		
				
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
