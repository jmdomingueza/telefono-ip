package com.jmdomingueza.telefonoip.presentacion.dialogs;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCount;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;

/**
 * Clase abstracta que implementa el dialogo que se muestra cuando queremos
 * editar una cuenta (crear o modificar). El dialogo contiene todos los
 * componentes necesarios para editar una cuenta.
 * 
 * @author jmdomingueza
 *
 */
public abstract class ShowHistoryCountDialog extends DefaultDialog {

	private static final long serialVersionUID = -4510834058365692418L;
	
	private static final Log logger = LogFactory.getLog(ShowHistoryCountDialog.class);
	
	/**
	 * Historico de cuenta
	 */
	protected HistoryCount historyCount;

	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	protected static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");

	/**
	 * Constructor de la clase
	 * 
	 * @param owner
	 * @param title
	 * @param width
	 * @param height
	 * @param count
	 */
	public ShowHistoryCountDialog(Window owner, String title, int width, int height, HistoryCount historyCount) {
		super(owner, title, width, height);
		
		logger.trace("Enter ShowHistoryCountDialog");
		
		this.historyCount = historyCount;
		this.init();
		logger.trace("Exit ShowHistoryCountDialog");
	}

	@Override
	protected JComponent createMainPane() {
		
		logger.trace("Enter createMainPane");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();

		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel generalDummyPanel = createGeneralPanel();
		JPanel extraDummyPanel = createExtraPanel();
		tabbedPane.addTab(resourceService.getProperty("main.dialog.showhistorycount.tab.general.text"),
				generalDummyPanel);
		tabbedPane.addTab(resourceService.getProperty("main.dialog.showhistorycount.tab.extra.text"), extraDummyPanel);

		// Layout Horizontal
		layoutGroup.setHorizontalGroup(layoutGroup.createSequentialGroup().addComponent(tabbedPane));
		// Layout Vertical
		layoutGroup.setVerticalGroup(
				layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(tabbedPane));
		
		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
		
		logger.trace("Exit createMainPane");
		return panel;
	}

	@Override
	protected JComponent createButtons() {
		
		logger.trace("Enter createButtons");

		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();

		JPanel panelButtons = ComponentFactory.createPanel(new FlowLayout(FlowLayout.CENTER));
		panelButtons.add(createCloseButton());

		// Layout Horizontal
		layoutGroup.setHorizontalGroup(layoutGroup.createSequentialGroup().addComponent(panelButtons));
		// Layout Vertical
		layoutGroup.setVerticalGroup(
				layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(panelButtons));

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
		
		logger.trace("Exit createButtons");
		return panel;
	}

	/**
	 * Metodo que crea el panel con los componentes generales
	 * @return
	 */
	protected abstract JPanel createGeneralPanel();

	/**
	 * Metodo que crea el panel con los componentes extra
	 * @return
	 */
	protected abstract JPanel createExtraPanel();

}
