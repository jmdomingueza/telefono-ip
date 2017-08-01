package com.jmdomingueza.telefonoip.presentacion.dialogs;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.negocio.beans.CountSip;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.utils.DialogIUtils;

/**
 * Clase que implementa el dialogo que se muestra cuando queremos editar una
 * cuenta sip (crear o modificar). El dialogo contiene todos los componentes
 * necesarios para editar una cuenta sip.
 * 
 * @author jmdomingueza
 *
 */
public class EditCountSipDialog extends EditCountDialog {

	private static final long serialVersionUID = -4510834058365692418L;

	private static final Log logger = LogFactory.getLog(EditCountSipDialog.class);
	
	/**
	 * Etiqueta del usuario
	 */
	private JLabel userLabel;
	
	/**
	 * Campo de texto con el valor del usuario de la cuenta. Es editable 
	 * si creamos una cuenta
	 */
	private JTextField userTextField;
	
	/**
	 * Etiqueta del servidor
	 */
	private JLabel serverLabel;
	
	/**
	 * Campo de texto con el valor del servidor. Es editable 
	 * si creamos una cuenta
	 */
	private JTextField serverTextField;
	
	/**
	 * Etiqueta del password
	 */
	private JLabel passwordLabel;
	
	/**
	 * Campo de texto con el valor del password
	 */
	private JPasswordField passwordPasswordField;

	/**
	 * Constructor de la clase
	 * 
	 * @param owner
	 * @param title
	 * @param width
	 * @param height
	 * @param count
	 */
	public EditCountSipDialog(Window owner, String title, int width, int height, CountSip count) {
		super(owner, title, width, height,count);
		
		logger.trace("Enter EditCountSipDialog");
		
		this.count = count;
		this.init();
		
		logger.trace("Exit EditCountSipDialog");
	}

	/**
	 * Metodo estatico que crea el dialogo
	 * 
	 * @param owner
	 * @param title
	 * @param count
	 * @return
	 */
	public static int showDialog(Window owner, String title, CountSip count) {
		JDialog dialog;

		logger.trace("Enter showDialog");
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog = new EditCountSipDialog(owner, title, d.width / 4, d.height / 4, count);

		DialogIUtils.centerDialog(owner, dialog);
		DialogIUtils.showDialog(dialog);
		
		logger.trace("Exit showDialog");
		return ((EditCountSipDialog) dialog).getSelectedOption();

	}

	@Override
	protected void accept() {
		CountSip countSip;
		
		logger.trace("Enter accept");
		
		countSip = (CountSip) count;
		if (countSip != null) {
			countSip.setUser(userTextField.getText());
			countSip.setHostServer(serverTextField.getText());
			countSip.setPassword(String.copyValueOf(passwordPasswordField.getPassword()));
		}
		logger.trace("Exit accept");
	}

	@Override
	protected JPanel createGeneralPanel() {
		
		logger.trace("Enter createGeneralPanel");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();

		userLabel = ComponentFactory.createLabel("main.dialog.editcountsip.label.user");
		userTextField = ComponentFactory.createTextField("", 20, false);
		serverLabel = ComponentFactory.createLabel("main.dialog.editcountsip.label.server");
		serverTextField = ComponentFactory.createTextField("", 20, false);
		passwordLabel = ComponentFactory.createLabel("main.dialog.editcountsip.label.password");
		passwordPasswordField = ComponentFactory.createPasswordField("", 20, true);

		editable();

		update();

		// Layout Horizontal
		layoutGroup.setHorizontalGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
				.addGroup(layoutGroup.createSequentialGroup()
						.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(userLabel).addComponent(serverLabel).addComponent(passwordLabel))
						.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(userTextField).addComponent(serverTextField)
								.addComponent(passwordPasswordField))));
		// Layout Vertical
		layoutGroup.setVerticalGroup(layoutGroup.createSequentialGroup()
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(userTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(serverLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(serverTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordPasswordField, GroupLayout.PREFERRED_SIZE, 30,
								GroupLayout.PREFERRED_SIZE)));

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		logger.trace("Exit createGeneralPanel");
		return panel;
	}

	@Override
	protected JPanel createExtraPanel() {
		
		logger.trace("Enter createExtraPanel");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();

		JLabel extraLabel = ComponentFactory.createLabel("main.dialog.editcountsip.label.extra");

		// Layout Horizontal
		layoutGroup.setHorizontalGroup(layoutGroup.createSequentialGroup().addComponent(extraLabel));
		// Layout Vertical
		layoutGroup.setVerticalGroup(
				layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(extraLabel));

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		logger.trace("Exit createExtraPanel");
		return panel;
	}

	@Override
	protected void editable() {
		CountSip countSip;
		
		logger.trace("Enter editable");
		
		countSip = (CountSip) count;
		if (countSip == null || countSip.getUser() == null) {
			userTextField.setEditable(true);
		} else {
			userTextField.setEditable(false);
		}
		if (countSip == null || countSip.getHostServer() == null) {
			serverTextField.setEditable(true);
		} else {
			serverTextField.setEditable(false);
		}
		passwordPasswordField.setEditable(true);
		
		logger.trace("Exit editable");
	}

	@Override
	protected void update() {
		CountSip countSip;
		
		logger.trace("Enter update");
		
		countSip = (CountSip) count;
		if (countSip == null) {
			this.userTextField.setText("");
			this.serverTextField.setText("");
			this.passwordPasswordField.setText("");
			return;
		}

		this.userTextField.setText(countSip.getUser() != null ? countSip.getUser() : "");
		this.serverTextField.setText(countSip.getHostServer() != null ? countSip.getHostServer() : "");
		this.passwordPasswordField.setText(countSip.getPassword());
		
		logger.trace("Exit update");
	}

}
