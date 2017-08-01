package com.jmdomingueza.telefonoip.presentacion.dialogs;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.utils.DialogIUtils;

/**
 * Clase que implementa un dialogo de pregunta
 * @author jmdomingueza
 *
 */
public class QuestionDialog extends DefaultDialog {
	
	private static final long serialVersionUID = -4510834058365692418L;
	
	private static final Log logger = LogFactory.getLog(QuestionDialog.class);
	
	/**
	 * Propety donde se encuentra el texto de la pregunta
	 */
	private String textLabel;
	
	
	/**
	 * Constructor de la clase.
	 * @param owner
	 * @param title 
	 * @param width
	 * @param height
	 * @param textLabel Propety donde se encuentra el texto de la pregunta
	 */
	public QuestionDialog(Window owner, String title, int width, int height,String textLabel) {
		super(owner, title, width, height);
		
		logger.trace("Enter QuestionDialog");
		
		this.textLabel = textLabel;
		this.init();
		
		logger.trace("Exit QuestionDialog");
	}
	
	/**
	 * Metodo estatico que crea el dialogo
	 * 
	 * @param owner
	 * @param title
	 * @param textLabel
	 * @return
	 */
	public static int showDialog(Window owner, String title, String textLabel){
		JDialog dialog;
		
		logger.trace("Enter showDialog");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog = new  QuestionDialog(owner,title,d.width/4,d.height/4,textLabel);
		
		DialogIUtils.centerDialog(owner, dialog);
		DialogIUtils.showDialog(dialog);
		
		logger.trace("Exit showDialog");
		return ((QuestionDialog)dialog).getSelectedOption();
		
	}
	
	@Override
	protected JComponent createMainPane() {
		
		logger.trace("Enter createMainPane");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		JLabel label = ComponentFactory.createLabel(textLabel);
		
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(
			layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
				.addComponent(label)
		);
		//Layout Vertical
		layoutGroup.setVerticalGroup(
			layoutGroup.createSequentialGroup()
				.addComponent(label)	
		);

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
		panelButtons.add(createAcceptButton());
		panelButtons.add(createCancelButton());
		
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(
			layoutGroup.createSequentialGroup()
				.addComponent(panelButtons));
		//Layout Vertical
		layoutGroup.setVerticalGroup(
			layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(panelButtons));

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
		
		logger.trace("Exit createButtons");
		return panel;
	}
}
