package com.jmdomingueza.telefonoip.presentacion.panels;

import java.awt.ComponentOrientation;
import java.util.Collection;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.common.messages.MessageListener;
import com.jmdomingueza.telefonoip.common.messages.MessageService;
import com.jmdomingueza.telefonoip.common.messages.ihm.IhmMessage;
import com.jmdomingueza.telefonoip.negocio.beans.Count;
import com.jmdomingueza.telefonoip.negocio.beans.CountSip;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountSip;
import com.jmdomingueza.telefonoip.negocio.factories.BeanFactory;
import com.jmdomingueza.telefonoip.negocio.services.CountSipService;
import com.jmdomingueza.telefonoip.negocio.services.HistorySipService;
import com.jmdomingueza.telefonoip.presentacion.dialogs.QuestionDialog;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.labels.SipStateLabel;
import com.jmdomingueza.telefonoip.presentacion.dialogs.EditCountSipDialog;

/**
 * Clase que implementa un panel donde aparece la lista de cuentas sip con su
 * informacion y la posiblidad de crear,modificarn, eliminar, registrar o desregistar
 * @author jmdomingueza
 *
 */
public class CountSipPanel extends CountPanel implements MessageListener<IhmMessage>{

	private static final long serialVersionUID = 7352625129149387267L;
	
	private static final Log logger = LogFactory.getLog(CountSipPanel.class);
	
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
	 * Etiqueta del estado de la cuenta
	 */
	private SipStateLabel stateLabel;

	/**
	 * Contructor de la clase
	 * @param title
	 */
	public CountSipPanel(String title) {
		super(title);
		
		logger.trace("Enter CountSipPanel");
		
		MessageService.addMessageListener(MessageService.IHM_GROUP_NAME, this);
		
		logger.trace("Exit CountSipPanel");
	}
	
	@Override
	public void save(){
		
		logger.trace("Enter save");
		
		super.save();
		MessageService.removeMessageListener(MessageService.IHM_GROUP_NAME, this);
		
		logger.trace("Exit save");
	}
	
	@Override
	public void receiveMessage(IhmMessage message) {
		logger.trace("Enter receiveMessage");
		
		if(message.getValue()==null){
			updateIHM();
			return;
		}
		
		switch (message.getType()) {
		case IhmMessage.ADD_TYPE:
			if( message.getValue() instanceof CountSip){
				CountSip countSip = (CountSip) message.getValue();
				
				add(countSip);
				
				//creamos el historyCountSip con el estado created
				HistoryCountSip historyCountSip = BeanFactory.createHistoryCountSip(countSip, HistoryCountSip.Action.created);
				HistorySipService historySipService = CTX.ctx.getBean(HistorySipService.class);
				historySipService.createHistoryCount(historyCountSip);
			}
			break;
		case IhmMessage.UPDATE_TYPE:
			if( message.getValue() instanceof CountSip){
				CountSip countSip = (CountSip) message.getValue();
				
				update(countSip);
				
				//creamos el historyCountSip con el estado modified
				HistoryCountSip historyCountSip = BeanFactory.createHistoryCountSip(countSip, HistoryCountSip.Action.modified);
				HistorySipService historySipService = CTX.ctx.getBean(HistorySipService.class);
				historySipService.createHistoryCount(historyCountSip);
			}
			break;
		case IhmMessage.REMOVE_TYPE:
			if( message.getValue() instanceof CountSip){
				CountSip countSip = (CountSip) message.getValue();
				
				remove(countSip);
				
				//creamos el historyCountSip con el estado created
				HistoryCountSip historyCountSip = BeanFactory.createHistoryCountSip(countSip, HistoryCountSip.Action.removed);
				HistorySipService historySipService = CTX.ctx.getBean(HistorySipService.class);
				historySipService.createHistoryCount(historyCountSip);
			}
			break;
		case IhmMessage.SELECTION_TYPE:
			if( message.getValue() instanceof CountSip){
				updateIHM();
			}
			break;
		default:
			logger.warn("Se recibe un tipo que no es tratado: "+message.getType());
			break;
		}
		logger.trace("Exit receiveMessage");
		
	}
	
	@Override
	protected JPanel createInfoPanel() {
		
		logger.trace("Enter createInfoPanel");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		userLabel = ComponentFactory.createLabel("main.panel.countsip.label.user");
		userTextField = ComponentFactory.createTextField("", 20,false);
		serverLabel = ComponentFactory.createLabel("main.panel.countsip.label.server");
		serverTextField = ComponentFactory.createTextField("", 20,false);
		passwordLabel = ComponentFactory.createLabel("main.panel.countsip.label.password");
		passwordPasswordField = ComponentFactory.createPasswordField("", 20,false);
		stateLabel = ComponentFactory.createSipStateLabel(CountSip.State.unregistered);
		
		registerButton = createRegisterButton();
		
		unregisterButton = createUnregisterButton();
		
		//Layout Horizontal
				layoutGroup.setHorizontalGroup(
					layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
						.addGroup(layoutGroup.createSequentialGroup()
								.addGap(100)
								.addComponent(stateLabel)
						)
						.addGroup(layoutGroup.createSequentialGroup()
							.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(userLabel)
								.addComponent(serverLabel)	
								.addComponent(passwordLabel)	
							)
							.addGap(10)
							.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(userTextField)
								.addComponent(serverTextField)
								.addComponent(passwordPasswordField)	
							)
						)
						.addGroup(layoutGroup.createSequentialGroup()
							.addComponent(registerButton,GroupLayout.PREFERRED_SIZE, 150,GroupLayout.PREFERRED_SIZE)
							.addComponent(unregisterButton,GroupLayout.PREFERRED_SIZE, 150,GroupLayout.PREFERRED_SIZE)
						)
				);
				//Layout Vertical
				layoutGroup.setVerticalGroup(
					layoutGroup.createSequentialGroup()
					.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(stateLabel)
						)
						.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(userLabel,GroupLayout.PREFERRED_SIZE, 30,GroupLayout.PREFERRED_SIZE)
							.addComponent(userTextField,GroupLayout.PREFERRED_SIZE, 30,GroupLayout.PREFERRED_SIZE)
						)
						.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(serverLabel,GroupLayout.PREFERRED_SIZE, 30,GroupLayout.PREFERRED_SIZE)
								.addComponent(serverTextField,GroupLayout.PREFERRED_SIZE, 30,GroupLayout.PREFERRED_SIZE)
						)
						.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(passwordLabel,GroupLayout.PREFERRED_SIZE, 30,GroupLayout.PREFERRED_SIZE)
								.addComponent(passwordPasswordField,GroupLayout.PREFERRED_SIZE, 30,GroupLayout.PREFERRED_SIZE)
						)
						.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(registerButton)
							.addComponent(unregisterButton)
						)
				);

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		logger.trace("Exit createInfoPanel");
		return panel;
	}

	@Override
	protected void load() {
		
		logger.trace("Enter load");
		
		listModel.clear();
		
		CountSipService countSipService = CTX.ctx.getBean(CountSipService.class);
		Collection<CountSip>  countSipCollection = countSipService.getAllCountSipPersistence();
		
		for(CountSip countSip : countSipCollection){
			listModel.addElement(countSip);
			// Si la cuenta esta registrada o con error al intentarlo 
			// se intenta rwgistrarla
			if(countSip.getState() != CountSip.State.unregistered){
				countSipService.registerCountSip(countSip);
				
			}
		}
		
		logger.trace("Exit load");
	}
	
	@Override
	protected void create() {
		
		logger.trace("Enter create");
		
		CountSip countSip;
		
		countSip = BeanFactory.createCountSip();
		int result =  EditCountSipDialog.showDialog(null, "main.dialog.edit.count.sip.title",countSip);
		if(result == EditCountSipDialog.ACCEPT_OPTION){
			CountSipService countSipService = CTX.ctx.getBean(CountSipService.class);
			countSipService.createCountSip(countSip);
		}
		
		logger.trace("Exit create");
	}
	
	@Override
	protected void modify(Count count) {
		
		logger.trace("Enter modify");
		
		CountSip countSip;
		
		countSip = (CountSip) count;
		int result =  EditCountSipDialog.showDialog(null, "main.dialog.edit.count.sip.title",countSip);
		if(result == EditCountSipDialog.ACCEPT_OPTION){
			CountSipService countSipService = CTX.ctx.getBean(CountSipService.class);
			countSipService.modifyCountSip(countSip);
		}
		
		logger.trace("Exit modify");
	}
	
	@Override
	protected void delete(Count count) {
		
		logger.trace("Enter delete");
		
		CountSip countSip;
		
		countSip = (CountSip) count;
		int result = QuestionDialog.showDialog(null,"main.dialog.question.count.sip.deleted.title","main.dialog.question.count.sip.deleted");
		if(result == QuestionDialog.ACCEPT_OPTION){
			CountSipService countSipService = CTX.ctx.getBean(CountSipService.class);
			countSipService.removeCountSip(countSip);
		}
		
		logger.trace("Exit delete");
	}
	
	@Override
	protected void register(Count count) {
		
		logger.trace("Enter register");
		
		CountSip countSip;
		
		countSip = (CountSip) count;
		CountSipService countSipService = CTX.ctx.getBean(CountSipService.class);
		countSipService.registerCountSip(countSip);
		
		logger.trace("Exit register");
	}
	
	@Override
	protected void unregister(Count count) {
		
		logger.trace("Enter unregister");
		
		CountSip countSip;
		
		countSip = (CountSip) count;
		CountSipService countSipService = CTX.ctx.getBean(CountSipService.class);
		countSipService.unregisterCountSip(countSip);
		
		logger.trace("Exit unregister");
	}
	
	@Override
	protected void updateIHM(){
		CountSip selectedCount;
		
		logger.trace("Enter update");
		
		selectedCount = (CountSip) countJList.getSelectedValue();
		
		if(selectedCount == null){
			this.userTextField.setText("");
			this.serverTextField.setText("");
			this.passwordPasswordField.setText("");
			this.stateLabel.setText(CountSip.State.unregistered);
			this.registerButton.setEnabled(false);
			this.unregisterButton.setEnabled(false);
			logger.trace("Exit update - countSip == null");
			return;
		}
		
		this.userTextField.setText(selectedCount.getUser()!=null?selectedCount.getUser():"");
		this.serverTextField.setText(selectedCount.getHostServer()!=null?selectedCount.getHostServer():"");
		this.passwordPasswordField.setText(selectedCount.getPassword()!=null?selectedCount.getPassword():"");
		this.stateLabel.setText(selectedCount.getState()!=null?selectedCount.getState():CountSip.State.unregistered);
		
		this.registerButton.setEnabled(selectedCount.getState()==CountSip.State.unregistered);
		this.unregisterButton.setEnabled(selectedCount.getState()==CountSip.State.registered);

		logger.trace("Exit update");
	}
	
	private void add(CountSip count){
		
		logger.trace("Enter add");
		
		listModel.addElement(count);
		
		logger.trace("Exit add");
	}
	
	private void update(CountSip count) {
		logger.trace("Enter update");
		
		listModel.removeElement(count);
		listModel.addElement(count);
		
		logger.trace("Exit update");
		
	}
	
	private void remove(CountSip count){
		
		logger.trace("Enter remove");
		
		listModel.removeElement(count);
		
		logger.trace("Exit remove");
	}	
	
}
