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
 * Clase que implementa el panel de gestion de historico.
 * @author jmdomingueza
 *
 */
public class HistoryManagerPanel extends DefaultPanel {
	
	private static final long serialVersionUID = 7749668494418887366L;
	
	private static final Log logger = LogFactory.getLog(HistoryManagerPanel.class);

	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");
	
	private HistoryDummyPanel historyDummyPanel;
	
	private HistorySipPanel historySipPanel;
	/**
	 * Constructor de la clase
	 * @param title
	 */
	public HistoryManagerPanel(String title) {
		super(title);
		
		logger.trace("Enter HistoryManagerPanel");
		logger.trace("Exit HistoryManagerPanel");
	}

	@Override
	protected JComponent createMainPane() {
		
		logger.trace("Enter createMainPane");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		historyDummyPanel = new HistoryDummyPanel(null);
		historyDummyPanel.init();
		historySipPanel =new HistorySipPanel(null);
		historySipPanel.init();
		tabbedPane.addTab(resourceService.getProperty("main.panel.historymanager.tab.dummy.text"),historyDummyPanel);
		tabbedPane.addTab(resourceService.getProperty("main.panel.historymanager.tab.sip.text"),historySipPanel);
		
				
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
	
	public void load(){
		historyDummyPanel.load();
		historyDummyPanel.updateButtons();
		historySipPanel.load();
		historySipPanel.updateButtons();
	}

}
