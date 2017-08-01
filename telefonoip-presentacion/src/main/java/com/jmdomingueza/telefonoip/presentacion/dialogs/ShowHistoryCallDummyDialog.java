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

import com.jmdomingueza.telefonoip.negocio.beans.HistoryCall;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.utils.DialogIUtils;

/**
 * Clase que implementa el dialogo que se muestra cuando queremos visualizar un 
 * historico de llamada dummy. El dialogo contiene  todos los componentes
 * de un historico de llamada dummy.
 * @author jmdomingueza
 *
 */
public class ShowHistoryCallDummyDialog extends ShowHistoryCallDialog {
	
	private static final long serialVersionUID = -4510834058365692418L;
	
	private static final Log logger = LogFactory.getLog(ShowHistoryCallDummyDialog.class);
	
	/**
	 * Etiqueta del usuario del historico de llamada
	 */
	private JLabel idLabel;
	
	/**
	 * Campo de texto con el valor del identificador del historico de llamada
	 */
	private JTextField idTextField;
	
	/**
	 * Etiqueta de la cuenta desde la que se hace el historico de llamada
	 */
	private JLabel countLabel;
	
	/**
	 * Campo de texto con el valor de la cuenta desde la que se hace el historico de llamada
	 */
	private JTextField countTextField;
	
	/**
	 * Etiqueta del numero de destino o origen del historico de llamada,
	 * depende del sentido del historico de llamada.
	 */
	private JLabel numberLabel;
	
	/**
	 * Campo de texto con el valor del numero de destino o origen del historico de llamada,
	 * depende del sentido del historico de llamada.
	 */
	private JTextField numberTextField;
	
	/**
	 * Etiqueta del estado del historico de llamada
	 */
	private JLabel stateLabel;
	
	/**
	 * Campo de texto con el valor del estado del historico de llamada
	 */
	private JTextField stateTextField;
	
	/**
	 * Etiqueta del sentido del historico de llamada
	 */
	private JLabel directionLabel;
	
	/**
	 * Campo de texto con el valor del sentido del historico de llamada
	 */
	private JTextField directionTextField;

	/**
	 * Etiqueta de la descripcion del historico de llamada
	 */
	private JLabel descriptionLabel;
	
	/**
	 * Campo de texto con el valor de la descripcion del historico de llamada
	 */
	private JTextField descriptionTextField;

	/**
	 * Etiqueta de la fecha del historico de llamada
	 */
	private JLabel dateLabel;
	
	/**
	 * Campo de texto con el valor de la fecha del historico de llamada
	 */
	private JTextField dateTextField;
	
	/**
	 * Constructor de la clase
	 * @param owner
	 * @param title
	 * @param width
	 * @param height
	 * @param call
	 */
	public ShowHistoryCallDummyDialog(Window owner, String title, int width, int height,HistoryCall historyCall) {
		super(owner, title, width, height,historyCall);
		
		logger.trace("Enter ShowHistoryCallDummyDialog");
		
		this.init();
		
		logger.trace("Exit ShowHistoryCallDummyDialog");
	}
	
	/**
	 * Metodo estatico que crea el dialogo
	 * @param owner
	 * @param title
	 * @param call
	 * @return
	 */
	public static int showDialog(Window owner, String title,HistoryCall historyCall){
		JDialog dialog;
		
		logger.trace("Enter showDialog");
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog = new  ShowHistoryCallDummyDialog(owner,title,d.width/4,d.height/4,historyCall);
		
		DialogIUtils.centerDialog(owner, dialog);
		DialogIUtils.showDialog(dialog);
		
		logger.trace("Exit showDialog");
		return ((ShowHistoryCallDummyDialog)dialog).getSelectedOption();
		
	}
	
	@Override
	protected  JPanel createGeneralPanel() {
		
		logger.trace("Enter createGeneralPanel");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		idLabel = ComponentFactory.createLabel("main.dialog.showhistorycalldummy.label.id");
		idTextField = ComponentFactory.createTextField(historyCall.getId()+"",20,false);
		
		countLabel = ComponentFactory.createLabel("main.dialog.showhistorycalldummy.label.count");
		countTextField = ComponentFactory.createTextField(historyCall.getCount(),20,false);
		
		numberLabel = ComponentFactory.createLabel("main.dialog.showhistorycalldummy.label.number");
		numberTextField = ComponentFactory.createTextField(historyCall.getNumber(),20,false);
		
		stateLabel = ComponentFactory.createLabel("main.dialog.showhistorycalldummy.label.state");
		stateTextField = ComponentFactory.createTextField(historyCall.getState().toString(),20,false);
		
		directionLabel = ComponentFactory.createLabel("main.dialog.showhistorycalldummy.label.direction");
		directionTextField = ComponentFactory.createTextField(historyCall.getDirection().toString(),20,false);
		
		descriptionLabel = ComponentFactory.createLabel("main.dialog.showhistorycalldummy.label.description");
		descriptionTextField = ComponentFactory.createTextField(historyCall.getDescription(),20,false);
		
		dateLabel = ComponentFactory.createLabel("main.dialog.showhistorycalldummy.label.date");
		SimpleDateFormat dateFormat = new SimpleDateFormat(resourceService.getProperty("main.dialog.showhistorycalldummy.date.format"));
		String value;
		if(historyCall.getDate()==null)
			value = "";
		else
			value = dateFormat.format( historyCall.getDate());
		dateTextField = ComponentFactory.createTextField(value,20,false);
		
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
			.addGroup(layoutGroup.createSequentialGroup()
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(idLabel)
					.addComponent(countLabel)
					.addComponent(numberLabel)
					.addComponent(stateLabel)
					.addComponent(directionLabel)
					.addComponent(descriptionLabel)
					.addComponent(dateLabel)
				)
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(idTextField)
					.addComponent(countTextField)
					.addComponent(numberTextField)
					.addComponent(stateTextField)
					.addComponent(directionTextField)
					.addComponent(descriptionTextField)
					.addComponent(dateTextField)
				)
			)	
		);
		//Layout Vertical
		layoutGroup.setVerticalGroup(layoutGroup.createSequentialGroup()
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(idLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(idTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(countLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(countTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(numberLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(numberTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(stateLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(stateTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(directionLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(directionTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(descriptionLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(descriptionTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
			.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(dateLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(dateTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
			)
		);	

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		logger.trace("Exit createGeneralPanel");
		return panel;
	}
	
}
