package com.jmdomingueza.telefonoip.presentacion.panels;

import java.awt.ComponentOrientation;
import java.util.Collection;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.common.messages.MessageListener;
import com.jmdomingueza.telefonoip.common.messages.MessageService;
import com.jmdomingueza.telefonoip.common.messages.ihm.IhmMessage;
import com.jmdomingueza.telefonoip.negocio.beans.Count;
import com.jmdomingueza.telefonoip.negocio.beans.CountDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountDummy;
import com.jmdomingueza.telefonoip.negocio.factories.BeanFactory;
import com.jmdomingueza.telefonoip.negocio.services.CountDummyService;
import com.jmdomingueza.telefonoip.negocio.services.HistoryDummyService;
import com.jmdomingueza.telefonoip.presentacion.dialogs.QuestionDialog;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.labels.DummyStateLabel;
import com.jmdomingueza.telefonoip.presentacion.dialogs.EditCountDummyDialog;

/**
 * Clase que implementa un panel donde aparece la lista de cuentas dummy con su
 * informacion y la posiblidad de crear,modificarn, eliminar, registrar o desregistar
 * @author jmdomingueza
 *
 */
public class CountDummyPanel extends CountPanel implements MessageListener<IhmMessage>{
	
	private static final long serialVersionUID = 7352625129149387267L;
	
	private static final Log logger = LogFactory.getLog(CountDummyPanel.class);

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
	 * Etiqueta del estado de la cuenta
	 */
	private DummyStateLabel stateLabel;

	/**
	 * Contructor de la clase
	 * @param title
	 */
	public CountDummyPanel(String title) {
		super(title);
		
		logger.trace("Enter CountDummyPanel");
		
		MessageService.addMessageListener(MessageService.IHM_GROUP_NAME, this);
		
		logger.trace("Exit CountDummyPanel");
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
			logger.trace("Exit receiveMessage - message.getValue()==null");
			return;
		}
		
		switch (message.getType()) {
		case IhmMessage.ADD_TYPE:
			if( message.getValue() instanceof CountDummy){
				CountDummy countDummy = (CountDummy) message.getValue();
				
				add(countDummy);
				
				//creamos el historyCountDummy con el estado created
				HistoryCountDummy historyCountDummy = BeanFactory.createHistoryCountDummy(countDummy,HistoryCountDummy.Action.created);
				HistoryDummyService historyDummyService = CTX.ctx.getBean(HistoryDummyService.class);
				historyDummyService.createHistoryCount(historyCountDummy);
			}
			break;
		case IhmMessage.UPDATE_TYPE:
			if( message.getValue() instanceof CountDummy){
				CountDummy countDummy = (CountDummy) message.getValue();
				
				update(countDummy);
				
				//creamos el historyCountDummy con el estado modified
				HistoryCountDummy historyCountDummy = BeanFactory.createHistoryCountDummy(countDummy,HistoryCountDummy.Action.modified);
				HistoryDummyService historyDummyService = CTX.ctx.getBean(HistoryDummyService.class);
				historyDummyService.createHistoryCount(historyCountDummy);	
			}
			break;
		case IhmMessage.REMOVE_TYPE:
			if( message.getValue() instanceof CountDummy){
				CountDummy countDummy = (CountDummy) message.getValue();
				
				remove(countDummy);
				
				//creamos el historyCountDummy con el estado created
				HistoryCountDummy historyCountDummy = BeanFactory.createHistoryCountDummy(countDummy, HistoryCountDummy.Action.removed);
				HistoryDummyService historyDummyService = CTX.ctx.getBean(HistoryDummyService.class);
				historyDummyService.createHistoryCount(historyCountDummy);
			}
			break;
		case IhmMessage.SELECTION_TYPE:
			if( message.getValue() instanceof CountDummy){
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
		
		userLabel = ComponentFactory.createLabel("main.panel.countdummy.label.user");
		userTextField = ComponentFactory.createTextField("", 20,false);
		editableLabel = ComponentFactory.createLabel("main.panel.countdummy.label.editable");
		editableTextField = ComponentFactory.createTextField("", 20,false);
		stateLabel = ComponentFactory.createDummyStateLabel(CountDummy.State.unregistered);
		
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
						.addComponent(editableLabel)	
					)
					.addGap(10)
					.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(userTextField)
						.addComponent(editableTextField)
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
						.addComponent(editableLabel,GroupLayout.PREFERRED_SIZE, 30,GroupLayout.PREFERRED_SIZE)
						.addComponent(editableTextField,GroupLayout.PREFERRED_SIZE, 30,GroupLayout.PREFERRED_SIZE)
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
		
		CountDummyService countDummyService = CTX.ctx.getBean(CountDummyService.class);
		Collection<CountDummy>  countDummyCollection = countDummyService.getAllCountDummyPersistence();
		
		for(CountDummy countDummy : countDummyCollection){
			listModel.addElement(countDummy);
		}
		logger.trace("Exit load");
	}
	
	@Override
	protected void create() {
		
		logger.trace("Enter create");
		
		CountDummy countDummy;
		
		countDummy = BeanFactory.createCountDummy();
		int result =  EditCountDummyDialog.showDialog(null, "main.dialog.editcountdummy.title",countDummy);
		if(result == EditCountDummyDialog.ACCEPT_OPTION){
			CountDummyService countDummyService = CTX.ctx.getBean(CountDummyService.class);
			countDummyService.createCountDummy(countDummy);
			
		}
		logger.trace("Exit create");
	}
	
	@Override
	protected void modify(Count count) {
		
		logger.trace("Enter modify");
		
		CountDummy countDummy;
		
		countDummy = (CountDummy) count;
		int result =  EditCountDummyDialog.showDialog(null, "main.dialog.editcountdummy.title",countDummy);
		if(result == EditCountDummyDialog.ACCEPT_OPTION){
			CountDummyService countDummyService = CTX.ctx.getBean(CountDummyService.class);
			countDummyService.modifyCountDummy(countDummy);
		}
		logger.trace("Exit modify");
	}
	
	@Override
	protected void delete(Count count) {
		
		logger.trace("Enter delete");
		
		CountDummy countDummy;
		
		countDummy = (CountDummy) count;
		int result = QuestionDialog.showDialog(null,"main.dialog.question.count.dummy.deleted.title","main.dialog.question.count.dummy.deleted");
		if(result == QuestionDialog.ACCEPT_OPTION){
			CountDummyService countDummyService = CTX.ctx.getBean(CountDummyService.class);
			countDummyService.removeCountDummy(countDummy);
		}
		logger.trace("Exit delete");
	}
	
	@Override
	protected void register(Count count) {
		
		logger.trace("Enter register");
		
		CountDummy countDummy;
		
		countDummy = (CountDummy) count;
		CountDummyService countDummyService = CTX.ctx.getBean(CountDummyService.class);
		countDummyService.registerCountDummy(countDummy);
		
		logger.trace("Exit register");
	}
	
	@Override
	protected void unregister(Count count) {
		
		logger.trace("Enter unregister");
		
		CountDummy countDummy;
		
		countDummy = (CountDummy) count;
		CountDummyService countDummyService = CTX.ctx.getBean(CountDummyService.class);
		countDummyService.unregisterCountDummy(countDummy);
		
		logger.trace("Exit unregister");
	}
	
	@Override
	protected void updateIHM(){
		CountDummy selectedCount;
		
		logger.trace("Enter update");
		
		selectedCount = (CountDummy) countJList.getSelectedValue();
		
		if(selectedCount == null){
			this.userTextField.setText("");
			this.editableTextField.setText("");
			this.stateLabel.setText(CountDummy.State.unregistered);
			this.registerButton.setEnabled(false);
			this.unregisterButton.setEnabled(false);
			logger.trace("Exit update - countDummy == null");
			return;
		}
		
		this.userTextField.setText(selectedCount.getUser()!=null?selectedCount.getUser():"");
		this.editableTextField.setText(selectedCount.getEditable()!=null?selectedCount.getEditable():"");
		this.stateLabel.setText(selectedCount.getState()!=null?selectedCount.getState():CountDummy.State.unregistered);
		
		this.registerButton.setEnabled(selectedCount.getState()==CountDummy.State.unregistered);
		this.unregisterButton.setEnabled(selectedCount.getState()==CountDummy.State.registered);

		
		logger.trace("Exit update");
	}
	
	
	private void add(CountDummy count){
		
		logger.trace("Enter add");
		
		listModel.addElement(count);
		
		logger.trace("Exit add");
	}
	
	private void update(CountDummy count) {
		logger.trace("Enter update");
		
		listModel.removeElement(count);
		listModel.addElement(count);
		
		logger.trace("Exit update");
		
	}
	
	private void remove(CountDummy count){
		
		logger.trace("Enter remove");
		
		listModel.removeElement(count);
		
		logger.trace("Exit remove");
	}
	

	
}
