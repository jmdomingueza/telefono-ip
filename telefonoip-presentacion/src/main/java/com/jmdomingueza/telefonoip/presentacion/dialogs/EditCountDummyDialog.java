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
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.negocio.beans.CountDummy;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.utils.DialogIUtils;

/**
 * Clase que implementa el dialogo que se muestra cuando queremos editar una 
 * cuenta dummy (crear o modificar). El dialogo contiene  todos los componentes
 * necesarios para editar una cuenta dummy.
 * @author jmdomingueza
 *
 */
public class EditCountDummyDialog extends EditCountDialog {
	
	private static final long serialVersionUID = -4510834058365692418L;
	
	private static final Log logger = LogFactory.getLog(EditCountDummyDialog.class);
	
	/**
	 * Etiqueta del usuario
	 */
	private JLabel userLabel;
	
	/**
	 * Campo de texto con el valor del usuario de la cuenta
	 */
	private JTextField userTextField;
	
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
	public EditCountDummyDialog(Window owner, String title, int width, int height,CountDummy count) {
		super(owner, title, width, height,count);
		
		logger.trace("Enter EditCountDummyDialog");
		
		this.count = count;
		this.init();
		
		logger.trace("Exit EditCountDummyDialog");
	}
	
	/**
	 * Metodo estatico que crea el dialogo
	 * @param owner
	 * @param title
	 * @param count
	 * @return
	 */
	public static int showDialog(Window owner, String title,CountDummy count){
		JDialog dialog;
		
		logger.trace("Enter showDialog");
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog = new  EditCountDummyDialog(owner,title,d.width/4,d.height/4,count);
		
		DialogIUtils.centerDialog(owner, dialog);
		DialogIUtils.showDialog(dialog);
		
		logger.trace("Exit showDialog");
		return ((EditCountDummyDialog)dialog).getSelectedOption();
		
	}
	
	@Override
	protected void accept() {
		CountDummy countDummy;
		
		logger.trace("Enter accept");
		
		countDummy = (CountDummy) count;
		if(countDummy!=null){
			countDummy.setUser(userTextField.getText());
			countDummy.setEditable(editableTextField.getText());
		}
		logger.trace("Exit accept");
	}
	
	@Override
	protected  JPanel createGeneralPanel() {
		
		logger.trace("Enter createGeneralPanel");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		userLabel = ComponentFactory.createLabel("main.dialog.editcountdummy.label.user");
		userTextField = ComponentFactory.createTextField("",20,true);
		editableLabel = ComponentFactory.createLabel("main.dialog.editcountdummy.label.editable");
		editableTextField = ComponentFactory.createTextField("",20,true);
		
		editable();
		
		update();
		
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(
			layoutGroup.createSequentialGroup()
					.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(userLabel)
						.addComponent(editableLabel)
					)
					.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(userTextField)
						.addComponent(editableTextField)
					)
				
		);
		//Layout Vertical
		layoutGroup.setVerticalGroup(
			layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(layoutGroup.createSequentialGroup()
						.addComponent(userLabel)
						.addComponent(editableLabel)
					)
					.addGroup(layoutGroup.createSequentialGroup()
						.addComponent(userTextField)
						.addComponent(editableTextField)
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

		JLabel extraLabel = ComponentFactory.createLabel("main.dialog.editcountdummy.label.extra");
		
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
	
	@Override
	protected  void editable() {
		CountDummy countDummy;
		
		logger.trace("Enter editable");
		
		countDummy = (CountDummy) count;
		if(countDummy ==null || countDummy.getUser()==null){
			userTextField.setEditable(true);
		}else{
			userTextField.setEditable(false);
		}
		editableTextField.setEditable(true);
		
		logger.trace("Exit editable");
	}
	
	@Override
	protected  void update(){
		CountDummy countDummy;
		
		logger.trace("Enter update");
		
		countDummy = (CountDummy) count;
		if(countDummy == null){
			this.userTextField.setText("");
			this.editableTextField.setText("");
			return;
		}
		
		this.userTextField.setText(countDummy.getUser()!=null?countDummy.getUser():"");
		this.editableTextField.setText(countDummy.getEditable()!=null?countDummy.getEditable():"");
		
		logger.trace("Exit update");
	}
	
}
