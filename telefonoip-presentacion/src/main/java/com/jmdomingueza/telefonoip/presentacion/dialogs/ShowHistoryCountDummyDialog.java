package com.jmdomingueza.telefonoip.presentacion.dialogs;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.negocio.beans.HistoryCount;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountDummy;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.utils.DialogIUtils;

/**
 * Clase que implementa el dialogo que se muestra cuando queremos visualizar un 
 * historico de cuenta dummy. El dialogo contiene  todos los componentes
 * de un historico de cuenta dummy.
 * @author jmdomingueza
 *
 */
public class ShowHistoryCountDummyDialog extends ShowHistoryCountDialog {
	
	private static final long serialVersionUID = -4510834058365692418L;
	
	private static final Log logger = LogFactory.getLog(ShowHistoryCountDummyDialog.class);
	
	/**
	 * Etiqueta del usuario del historico de cuenta
	 */
	private JLabel userLabel;
	
	/**
	 * Campo de texto con el valor del usuario del historico de cuenta
	 */
	private JTextField userTextField;
	
	/**
	 * Etiqueta de la accion del historico de cuenta
	 */
	private JLabel actionLabel;
	
	/**
	 * Campo de texto con el valor de la acciob del historico de cuenta
	 */
	private JTextField actionTextField;
	
	/**
	 * Etiqueta del estado del historico de cuenta
	 */
	private JLabel stateLabel;
	
	/**
	 * Campo de texto con el valor del estado del historico de cuenta
	 */
	private JTextField stateTextField;
	
	/**
	 * Etiqueta de la fecha del historico de cuenta
	 */
	private JLabel dateLabel;
	
	/**
	 * Campo de texto con el valor de la fecha del historico de cuenta
	 */
	private JTextField dateTextField;
	
	/**
	 * Etiqueta del editable
	 */
	private JLabel editableLabel;
	
	/**
	 * Campo de texto con el valor del editable de la cuenta
	 */
	private JTextField editableTextField;
		
	/**
	 * Constructor de la clase
	 * @param owner
	 * @param title
	 * @param width
	 * @param height
	 * @param count
	 */
	public ShowHistoryCountDummyDialog(Window owner, String title, int width, int height,HistoryCount historyCount) {
		super(owner, title, width, height,historyCount);
		
		logger.trace("Enter ShowHistoryCountDummyDialog");
		
		this.init();
		
		logger.trace("Exit ShowHistoryCountDummyDialog");
	}
	
	/**
	 * Metodo estatico que crea el dialogo
	 * @param owner
	 * @param title
	 * @param count
	 * @return
	 */
	public static int showDialog(Window owner, String title,HistoryCount historyCount){
		JDialog dialog;
		
		logger.trace("Enter showDialog");
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog = new  ShowHistoryCountDummyDialog(owner,title,d.width/4,d.height/4,historyCount);
		
		DialogIUtils.centerDialog(owner, dialog);
		DialogIUtils.showDialog(dialog);
		
		logger.trace("Exit showDialog");
		return ((ShowHistoryCountDummyDialog)dialog).getSelectedOption();
		
	}
	
	@Override
	protected  JPanel createGeneralPanel() {
		
		logger.trace("Enter createGeneralPanel");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		userLabel = ComponentFactory.createLabel("main.dialog.showhistorycountdummy.label.user");
		userTextField = ComponentFactory.createTextField(historyCount.getUser(),20,false);
		
		actionLabel = ComponentFactory.createLabel("main.dialog.showhistorycountdummy.label.action");
		actionTextField = ComponentFactory.createTextField(historyCount.getAction().toString(),20,false);
		
		stateLabel = ComponentFactory.createLabel("main.dialog.showhistorycountdummy.label.state");
		stateTextField = ComponentFactory.createTextField(((HistoryCountDummy) historyCount).getState().toString(),20,false);
		
		dateLabel = ComponentFactory.createLabel("main.dialog.showhistorycountdummy.label.date");
		SimpleDateFormat dateFormat = new SimpleDateFormat(resourceService.getProperty("main.dialog.showhistorycountdummy.date.format"));
		String value;
		if(historyCount.getDate()==null)
			value = "";
		else
			value = dateFormat.format( historyCount.getDate());
		dateTextField = ComponentFactory.createTextField(value,20,false);
		
		editableLabel = ComponentFactory.createLabel("main.dialog.showhistorycountdummy.label.editable");
		editableTextField = ComponentFactory.createTextField(((HistoryCountDummy)historyCount).getEditable(),20,false);
		
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
			.addGroup(layoutGroup.createSequentialGroup()
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(userLabel)
					.addComponent(actionLabel)
					.addComponent(stateLabel)
					.addComponent(dateLabel)
					.addComponent(editableLabel)
				)
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(userTextField)
					.addComponent(actionTextField)
					.addComponent(stateTextField)
					.addComponent(dateTextField)
					.addComponent(editableTextField)
				)
			)	
		);
		//Layout Vertical
		layoutGroup.setVerticalGroup(layoutGroup.createSequentialGroup()
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(userTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(actionLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(actionTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(stateLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(stateTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(dateLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(dateTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(editableLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(editableTextField, GroupLayout.PREFERRED_SIZE, 30,GroupLayout.PREFERRED_SIZE)
			)	
		);

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		logger.trace("Exit createGeneralPanel");
		return panel;
	}
	
	@Override
	protected JPanel createExtraPanel() {
		
		logger.trace("Enter createExtraPanel");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();

		JLabel extraLabel = ComponentFactory.createLabel("main.dialog.showhistorycountdummy.label.extra");
		
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(
			layoutGroup.createSequentialGroup()
						.addComponent(extraLabel)	
		);
		//Layout Vertical
		layoutGroup.setVerticalGroup(
			layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(extraLabel)
		);

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		logger.trace("Exit createExtraPanel");
		return panel;
	}

	
}
