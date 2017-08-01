package com.jmdomingueza.telefonoip.presentacion.dialogs;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCall;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;

/**
 * Clase abstracta que implementa el dialogo que se muestra cuando queremos
 * editar una llamada (crear o modificar). El dialogo contiene todos los
 * componentes necesarios para editar una llamada.
 * 
 * @author jmdomingueza
 *
 */
public abstract class ShowHistoryCallDialog extends DefaultDialog {

	private static final long serialVersionUID = -4510834058365692418L;
	
	private static final Log logger = LogFactory.getLog(ShowHistoryCallDialog.class);
	
	/**
	 * Historico de llamada
	 */
	protected HistoryCall historyCall;

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
	 * @param call
	 */
	public ShowHistoryCallDialog(Window owner, String title, int width, int height, HistoryCall historyCall) {
		super(owner, title, width, height);
		
		logger.trace("Enter ShowHistoryCallDialog");
		
		this.historyCall = historyCall;
		this.init();
		logger.trace("Exit ShowHistoryCallDialog");
	}

	@Override
	protected JComponent createMainPane() {
		
		logger.trace("Enter createMainPane");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();

		JPanel generalDummyPanel = createGeneralPanel();
		
		// Layout Horizontal
		layoutGroup.setHorizontalGroup(layoutGroup.createSequentialGroup().addComponent(generalDummyPanel));
		// Layout Vertical
		layoutGroup.setVerticalGroup(
				layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(generalDummyPanel));
		
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

}
