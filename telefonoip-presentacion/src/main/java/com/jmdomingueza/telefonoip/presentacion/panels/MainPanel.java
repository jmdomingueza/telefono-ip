package com.jmdomingueza.telefonoip.presentacion.panels;

import java.awt.ComponentOrientation;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;

/**
 * 
 *  @author jmdomingueza
 *
 */
public class MainPanel extends DefaultPanel{
	
	private static final long serialVersionUID = -1530408955639589460L;

	private static final Log logger = LogFactory.getLog(MainPanel.class);
	
	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");
	
	/**
	 * Constructor de la clase
	 * @param title
	 */
	public MainPanel(String title) {
		super(title);
		
		logger.trace("Enter MainPanel");
		
		logger.trace("Exit MainPanel");
	}
	
	@Override
	protected JComponent createMainPane() {
		
		logger.trace("Enter createMainPane");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		CountManagerPanel countManagerPanel =new CountManagerPanel("main.panel.countmanager.title");
		countManagerPanel.init();
		CallManagerPanel callManagerPanel = new CallManagerPanel("main.panel.callmanager.title");
		callManagerPanel.init();
		HistoryManagerPanel historyManagerPanel = new HistoryManagerPanel("main.panel.historymanager.title");
		historyManagerPanel.init();
		ConfigurationManagerPanel configurationManagerPanel = new ConfigurationManagerPanel("main.panel.configurationmanager.title");
		configurationManagerPanel.init();
		tabbedPane.addTab(resourceService.getProperty("main.panel.main.tab.count.text"),countManagerPanel);
		tabbedPane.addTab(resourceService.getProperty("main.panel.main.tab.call.text"),callManagerPanel);
		tabbedPane.addTab(resourceService.getProperty("main.panel.main.tab.history.text"),historyManagerPanel);
		tabbedPane.addTab(resourceService.getProperty("main.panel.main.tab.configuration.text"),configurationManagerPanel);
		tabbedPane.addChangeListener(new ChangeListener() {	
			@Override
			public void stateChanged(ChangeEvent e) {
				if(tabbedPane.getSelectedComponent() instanceof HistoryManagerPanel){
					historyManagerPanel.load();
				}
				
			}
		});
				
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
