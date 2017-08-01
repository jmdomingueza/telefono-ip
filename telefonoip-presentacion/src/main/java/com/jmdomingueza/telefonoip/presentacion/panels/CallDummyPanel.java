package com.jmdomingueza.telefonoip.presentacion.panels;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.common.messages.MessageListener;
import com.jmdomingueza.telefonoip.common.messages.MessageService;
import com.jmdomingueza.telefonoip.common.messages.ihm.IhmMessage;
import com.jmdomingueza.telefonoip.negocio.beans.Call;
import com.jmdomingueza.telefonoip.negocio.beans.Call.State;
import com.jmdomingueza.telefonoip.negocio.beans.CallDummy;
import com.jmdomingueza.telefonoip.negocio.beans.CountDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallDummy;
import com.jmdomingueza.telefonoip.negocio.factories.BeanFactory;
import com.jmdomingueza.telefonoip.negocio.services.CallDummyService;
import com.jmdomingueza.telefonoip.negocio.services.CountDummyService;
import com.jmdomingueza.telefonoip.negocio.services.HistoryDummyService;
import com.jmdomingueza.telefonoip.presentacion.table.models.CallTableModel;

/**
 * Clase que implementa un panel donde aparece la lista de llamadas dummy con su
 * informacion en una tabla. Aparecen tambien los botones para realizar las
 * acciones sobre esa llamadas(llamar, contestar,colgar,retener, recuperar,
 * transferir, conferencia y cancelar), estos botones se activaran dependiendo
 * del estado de las llamadas. Podremos seleccionar con que cuenta se pueden
 * realizar las llamadas.
 * 
 * @author jmdomingueza
 *
 */
public class CallDummyPanel extends CallPanel implements MessageListener<IhmMessage> {

	private static final long serialVersionUID = -2776061100050428544L;

	private static final Log logger = LogFactory.getLog(CallDummyPanel.class);
	
	/**
	 * Constructor de la clase
	 * 
	 * @param title
	 */
	public CallDummyPanel(String title) {
		super(title);

		logger.trace("Enter CallDummyPanel");

		MessageService.addMessageListener(MessageService.IHM_GROUP_NAME, this);

		logger.trace("Exit CallDummyPanel");
	}

	@Override
	public void save() {

		logger.trace("Enter save");

		super.save();

		MessageService.removeMessageListener(MessageService.IHM_GROUP_NAME, this);

		logger.trace("Exit save");
	}

	@Override
	public void receiveMessage(IhmMessage message) {
		logger.trace("Enter receiveMessage");

		if (message.getValue() == null) {
			updateButtons();
			return;
		}

		switch (message.getType()) {
		case IhmMessage.UPDATE_TYPE:
			if (message.getValue() instanceof CallDummy) {
				// Si recibimos un mensaje de actualizacion de una llamada dummy, actualizamos
				// el IHM con esa llamada.
				CallDummy callDummy = (CallDummy) message.getValue();
				update(callDummy);
			}
			if (message.getValue() instanceof CountDummy) {
				// Si recibimos un mensaje de actualizacion de una cuenta dummy, volvemos
				// a cargar las cuentas dummy.
				load();
			}
			break;
		case IhmMessage.SELECTION_TYPE:
			if (message.getValue() instanceof CallDummy) {
				// Si recibimos un mensaje de seleccion de una llamada dummy, actualizamos
				// los bootones
				updateButtons();
			}
			break;
		case IhmMessage.ADD_TYPE:
		case IhmMessage.REMOVE_TYPE:
			// No se hace nada con estos tipo en esta clase
			break;
		default:
			logger.warn("Se recibe un tipo que no es tratado: " + message.getType());
			break;
		}
		logger.trace("Exit receiveMessage");

	}

	@Override
	protected void load() {
		logger.trace("Enter load");

		//Limpiamos el vector del ComboBox con las cuentas
		if(countComboBox!=null)
			countComboBox.setSelectedItem(null);
			
		vector.clear();
		//Buscamos en la BBDD todas las cuentas Dummy
		CountDummyService countDummyService = CTX.ctx.getBean(CountDummyService.class);
		Collection<CountDummy> countDummyCollection = countDummyService.getAllCountDummyPersistence();
		
		// introducimos en el vector del ComboBox de cuentas solo las que estan registradas.
		if(countDummyCollection!=null){
			for (CountDummy countDummy : countDummyCollection) {
				if (countDummy.getState().equals(CountDummy.State.registered)) {
					vector.addElement(countDummy);
				}
			}
		}

		logger.trace("Exit load");

	}

	/**
	 * Metodo que actualiza todos los componentes del IHM con los valores.
	 */
	protected void update(Call call) {

		logger.trace("Enter update");
		
		// Actulizamos el estado de la llamada de la tabla de llamadas
		updateState(call);
		// Habilitamos y desabilitamos los botones si procede
		updateButtons();

		logger.trace("Exit update");
	}
	
	@Override
	protected void updateButtons() {
		Call selectedCall;
		
		logger.trace("Enter updateButtons");
		
		selectedCall = ((CallTableModel) model).getRow(table.getSelectedRow());
		if (selectedCall != null) {
			// Si no hay ninguna talking ni dialing
			dialButton.setEnabled(!isOneActiveCall() && countComboBox.getSelectedItem()!=null);

			// Si hay la llamada esta en ringing, es la seleccionada y no hay
			// ninguna talking ni dialing
			responseButton.setEnabled(selectedCall.getState() == State.ringing && !isOneActiveCall());
			// Si hay la llamada esta en talking, held o conference
			hangupButton.setEnabled((selectedCall.getState() == State.talking || selectedCall.getState() == State.held
					|| selectedCall.getState() == State.conference));

			// Si hay la llamada esta en dialing o ringing y es la seleccionada
			cancelButton
					.setEnabled((selectedCall.getState() == State.dialing || selectedCall.getState() == State.ringing));

			// Si hay la llamada esta en talking o conference y es la
			// seleccionada
			heldButton.setEnabled(
					(selectedCall.getState() == State.talking || selectedCall.getState() == State.conference));

			// Si hay la llamada esta en held, es la seleccionada y no hay
			// ninguna talking ni dialing
			retrieveButton.setEnabled((selectedCall.getState() == State.held) && !isOneActiveCall());

			// Si hay la llamada esta en held, es la seleccionada y no hay
			// ninguna talking ni dialing
			transferButton.setEnabled((selectedCall.getState() == State.held) && getTalkingCall()!=null);

			// Si hay la llamada esta en held, es la seleccionada y no hay
			// ninguna talking ni dialing
			conferenceButton.setEnabled((selectedCall.getState() == State.held) && getTalkingCall()!=null);
		} else {
			// Si no hay ninguna llamada seleccionada el boton de llamar  no se habilita
			// si existe una llamada activa o activandose. Ademas el resto de botones se 
			// deshabilitan
			dialButton.setEnabled(!isOneActiveCall() && countComboBox.getSelectedItem()!=null);
			responseButton.setEnabled(false);
			hangupButton.setEnabled(false);
			cancelButton.setEnabled(false);
			heldButton.setEnabled(false);
			retrieveButton.setEnabled(false);
			transferButton.setEnabled(false);
			conferenceButton.setEnabled(false);
		}
		
		logger.trace("Exit updateButtons");
	}

	@Override
	protected void dial() {
		CountDummy count;
		String number;
		logger.trace("Enter dial");

		count = (CountDummy) countComboBox.getSelectedItem();
		number = callTextField.getText();
	
		// Realizamos la llamada
		CallDummyService callDummyService = CTX.ctx.getBean(CallDummyService.class);
		callDummyService.dial(count, number);

		logger.trace("Exit dial");
	}

	@Override
	protected void response() {

		logger.trace("Enter response");

		// Obtenemos la llamada seleccionada de la tabla
		Call call = ((CallTableModel) model).getRow(table.getSelectedRow());
		
		// Respondemos la llamada
		CallDummyService callDummyService = CTX.ctx.getBean(CallDummyService.class);
		callDummyService.response((CallDummy) call);

		logger.trace("Exit response");
	}

	@Override
	protected void hangup() {

		logger.trace("Enter hangup");

		// Obtenemos la llamada seleccionada de la tabla
		Call call = ((CallTableModel) model).getRow(table.getSelectedRow());

		// Colgamos la llamada
		CallDummyService callDummyService = CTX.ctx.getBean(CallDummyService.class);
		callDummyService.drop((CallDummy) call);

		logger.trace("Exit hangup");
	}

	@Override
	protected void cancel() {

		logger.trace("Enter cancel");

		// Obtenemos la llamada seleccionada de la tabla
		Call call = ((CallTableModel) model).getRow(table.getSelectedRow());

		// Colgamos la llamada
		CallDummyService callDummyService = CTX.ctx.getBean(CallDummyService.class);
		callDummyService.cancel((CallDummy) call);

		logger.trace("Exit cancel");
	}

	@Override
	protected void transfer() {

		logger.trace("Enter transfer");

		// Obtenemos la llamada seleccionada de la tabla
		Call call = ((CallTableModel) model).getRow(table.getSelectedRow());

		Call activeCall = getTalkingCall();

		if (activeCall == null)
			return; // TODO Error

		// Transferimos la llamada
		CallDummyService callDummyService = CTX.ctx.getBean(CallDummyService.class);
		callDummyService.transfer((CallDummy) activeCall, (CallDummy) call);

		logger.trace("Exit transfer");
	}

	@Override
	protected void conference() {

		logger.trace("Enter conference");

		// Obtenemos la llamada seleccionada de la tabla
		Call call = ((CallTableModel) model).getRow(table.getSelectedRow());

		Call activeCall = getTalkingCall();

		if (activeCall == null)
			return; // TODO Error

		// Colgamos la llamada
		CallDummyService callDummyService = CTX.ctx.getBean(CallDummyService.class);
		callDummyService.conference((CallDummy) activeCall, (CallDummy) call);

		logger.trace("Exit conference");
	}

	@Override
	protected void held() {

		logger.trace("Enter held");

		// Obtenemos la llamada seleccionada de la tabla
		Call call = ((CallTableModel) model).getRow(table.getSelectedRow());

		// Ponemos en espera la llamada
		CallDummyService callDummyService = CTX.ctx.getBean(CallDummyService.class);
		callDummyService.held((CallDummy) call);

		logger.trace("Exit held");
	}

	@Override
	protected void retrieve() {

		logger.trace("Enter retrieve");

		// Obtenemos la llamada seleccionada de la tabla
		Call call = ((CallTableModel) model).getRow(table.getSelectedRow());

		// recuperamos la llamada
		CallDummyService callDummyService = CTX.ctx.getBean(CallDummyService.class);
		callDummyService.retieve((CallDummy) call);

		logger.trace("Exit retrieve");
	}

	/**
	 * Metodo que actualiza el estado de una llamada en la tabla. Si es terminated, cancelled
	 * @param call
	 * @return
	 */
	private boolean updateState(Call call) {
		
		logger.trace("Enter updateState");
		
		// Comprobamos que la llamada esta en la tabla.Si es -1 es que no esta
		int row = ((CallTableModel) model).getRowIndex(call);
		if (row > -1) {
			//Obtenemos la llamada de la tabla
			Call callTable = ((CallTableModel) model).getRow(row);
			callTable.setState(call.getState());
			
			// Si la llamada esta terminando o poniendose en espera, se buscan si hay alguna llamada
			// en conferecia y se pone talking
			if (call.getState() == Call.State.terminating || call.getState() == Call.State.helding) {
				putTalkingCall();
			}
			
			//Si la llamada es uno de los estados que se indica se borra de la tabla y creamos el historico de cuenta
			if (call.getState() == Call.State.terminated || call.getState() == Call.State.canceled
					|| call.getState() == Call.State.transfered || call.getState() == Call.State.lost) {
				// Borramos la llamada del modelo de a tabla
				((CallTableModel) model).removeRow(call);
				
				// Creamos el historico de llamada 
				HistoryDummyService historyDummyService = CTX.ctx.getBean(HistoryDummyService.class);
				HistoryCallDummy historyCallDummy = BeanFactory.createHistoryCallDummy(call);
				historyDummyService.createHistoryCall(historyCallDummy);

				logger.trace("Exit updateState - removeRow");
				return true;
			}
			//Pintamos el nuevo estado en la tabla
			((CallTableModel) model).fireTableCellUpdated(row, CallTableModel.STATE_COLUMN);
			
			logger.trace("Exit updateState - fireTableCellUpdated");
			return true;
		}else{
			// Si la llamada esta ringing o dialing la insertamos en la tabla
			if(call.getState() == Call.State.ringing || call.getState() == Call.State.dialing ){
				// Insertamos la llamada en la tabla
				((CallTableModel) model).addRow(call);
			}
		}
		logger.trace("Exit updateState - row > -1");
		return false;
	}

	

	/**
	 * Metodo que devuelve la llamada que esta en estado talking (solo puede existir una).
	 * Si no hya ninguna devuelve null
	 * @return
	 */
	private Call getTalkingCall() {
		
		logger.trace("Enter getTalkingCall");
		
		// Se recorre todos las filas de la tabla a traves de su modelo
		for (int i = 0; i < ((CallTableModel) model).getRowCount(); i++) {
			Call call = ((CallTableModel) model).getRow(i);
			//Si la llamas es talking se devuelve
			if (call.getState() == State.talking) {		 
				logger.trace("Exit getTalkingCall - call");
				return call;
			}
		}
		
		logger.trace("Exit getTalkingCall - null");
		return null;
	}


	/**
	 * Metodo que busca si hay alguna llamada en conferecia 
	 * y si la hay se pone talking
	 */
	private void putTalkingCall() {
		
		logger.trace("Enter putTalkingCall");
		
		// Se recorre todos las filas de la tabla a traves de su modelo
		for (int i = 0; i < ((CallTableModel) model).getRowCount(); i++) {
			Call call = ((CallTableModel) model).getRow(i);
			// Si la llamada esta en conference se pone talking
			if (call.getState() == State.conference) {
				call.setState(State.talking);
				((CallTableModel) model).fireTableCellUpdated(i, CallTableModel.STATE_COLUMN);
			}
		}
		
		logger.trace("Exit putTalkingCall");
	}

	/**
	 * Metodo que devuelve si cierto si tenemos alguna llamada activa o se esta activando.
	 * Una llamada activa es: talking, conference
	 * Una llamada que se esta activando es: dialing, responding, confering o retrieving
	 * @return
	 */
	private boolean isOneActiveCall() {
		
		logger.trace("Enter isOneActiveCall");
		
		// Se recorre todos las filas de la tabla a traves de su modelo
		for (int i = 0; i < ((CallTableModel) model).getRowCount(); i++) {
			Call call = ((CallTableModel) model).getRow(i);
			// Si la llamada esta activa o activandose se devuelve cierto.
			if (call.getState() == State.dialing || call.getState() == State.talking
					|| call.getState() == State.responding || call.getState() == State.confering
					|| call.getState() == State.conference || call.getState() == State.retrieving) {
				logger.trace("Exit isOneActiveCall - true");
				return true;
			}
		}
		
		logger.trace("Exit isOneActiveCall - false");
		return false;
	}

	

}
