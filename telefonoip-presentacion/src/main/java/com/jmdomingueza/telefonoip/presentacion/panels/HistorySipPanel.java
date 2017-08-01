package com.jmdomingueza.telefonoip.presentacion.panels;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.common.messages.MessageListener;
import com.jmdomingueza.telefonoip.common.messages.MessageService;
import com.jmdomingueza.telefonoip.common.messages.ihm.IhmMessage;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallSip;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountSip;
import com.jmdomingueza.telefonoip.negocio.services.HistorySipService;
import com.jmdomingueza.telefonoip.presentacion.dialogs.QuestionDialog;
import com.jmdomingueza.telefonoip.presentacion.dialogs.ShowHistoryCallSipDialog;
import com.jmdomingueza.telefonoip.presentacion.dialogs.ShowHistoryCountSipDialog;
import com.jmdomingueza.telefonoip.presentacion.table.models.HistoryCallTableModel;
import com.jmdomingueza.telefonoip.presentacion.table.models.HistoryCountTableModel;

/**
 * Clase que implementa un panel donde aparece la lista de llamadas sip con su
 * informacion en una tabla. Aparecen tambien los botones para realizar las
 * acciones sobre esa llamadas(llamar, contestar,colgar,retener, recuperar,
 * transferir, conferencia y cancelar), estos botones se activaran dependiendo
 * del estado de las llamadas. Podremos seleccionar con que cuenta se pueden
 * realizar las llamadas.
 * 
 * @author jmdomingueza
 *
 */
public class HistorySipPanel extends HistoryPanel implements MessageListener<IhmMessage> {

	private static final long serialVersionUID = -2776061100050428544L;

	private static final Log logger = LogFactory.getLog(HistorySipPanel.class);


	/**
	 * Constructor de la clase
	 * 
	 * @param title
	 */
	public HistorySipPanel(String title) {
		super(title);

		logger.trace("Enter HistorySipPanel");

		MessageService.addMessageListener(MessageService.IHM_GROUP_NAME, this);

		logger.trace("Exit HistorySipPanel");
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
			selectedHistoryCall = null;
			selectedHistoryCount = null;
			updateButtons();
			return;
		}

		switch (message.getType()) {
		case IhmMessage.SELECTION_TYPE:
			if (message.getValue() instanceof HistoryCallSip) {
				// Si recibimos un mensaje de seleccion de unhistorico de llamada sip, indicamos
				// que ese historico de llamada pasa a ser la seleccionado y actualizamos los botones
				HistoryCallSip historyCallSip = (HistoryCallSip) message.getValue();
				selectedHistoryCall = historyCallSip;
				updateButtons();
			}
			
			if (message.getValue() instanceof HistoryCountSip) {
				// Si recibimos un mensaje de seleccion de un historico de cuenta sip, indicamos
				// que ese historico cuenta pasa a ser la seleccionado y actualizamos los botones
				HistoryCountSip historyCountSip = (HistoryCountSip) message.getValue();
				selectedHistoryCount = historyCountSip;
				updateButtons();
			}
			break;
		case IhmMessage.ADD_TYPE:
		case IhmMessage.UPDATE_TYPE:
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

		//Limpiamos el modelo del historico de llamadas
		((HistoryCallTableModel) historyCallModel).clean();
		// Limpiamos el modelo del historico de llamadas
		((HistoryCountTableModel) historyCountModel).clean();
				
		HistorySipService historySipService = CTX.ctx.getBean(HistorySipService.class);
		
		//Buscamos en la BBDD todos las historicos de llamadas sip
		Collection<HistoryCallSip> historyCallSipCollection = historySipService.getAllHistoryCall();
		// introducimos en el vector del ComboBox de cuentas solo las que estan registradas.
		for (HistoryCallSip historyCallSip : historyCallSipCollection) {
			((HistoryCallTableModel) historyCallModel).addRow(historyCallSip);
		}
		
		//Buscamos en la BBDD todos las historicos de cunenta sip
		Collection<HistoryCountSip> historyCountSipCollection = historySipService.getAllHistoryCount();
		// introducimos en el vector del ComboBox de cuentas solo las que estan
		// registradas.
		for (HistoryCountSip historyCountSip : historyCountSipCollection) {
			((HistoryCountTableModel) historyCountModel).addRow(historyCountSip);
		}

		logger.trace("Exit load");

	}

	@Override
	protected void updateButtons() {

		logger.trace("Enter updateButtons");
		
		removeHistoryCallButton.setEnabled(selectedHistoryCall!=null);
		removeHistoryCountButton.setEnabled(selectedHistoryCount!=null);
		
		showHistoryCallButton.setEnabled(selectedHistoryCall!=null);
		showHistoryCountButton.setEnabled(selectedHistoryCount!=null);
		
		removeAllHistoryCallButton.setEnabled(true);
		removeAllHistoryCountButton.setEnabled(true);
		
		logger.trace("Exit updateButtons");
	}

	@Override
	protected void removeHistoryCall() {
		
		logger.trace("Enter removeHistoryCall");
		int result = QuestionDialog.showDialog(null,"main.dialog.question.history.call.sip.deleted.title","main.dialog.question.history.call.sip.deleted");
		if(result == QuestionDialog.ACCEPT_OPTION){
			//Eliminamos el historico de llamada en la BBDD
			HistorySipService historySipService = CTX.ctx.getBean(HistorySipService.class);
			historySipService.removeHistoryCall((HistoryCallSip) selectedHistoryCall);
			//Eliminamos el historico de llamada en el modelo de la tabla de historicos de llamada
			((HistoryCallTableModel) historyCallModel).removeRow(selectedHistoryCall);
		}
		logger.trace("Exit removeHistoryCall");
	}

	@Override
	protected void showHistoryCall() {
		logger.trace("Enter showHistoryCall");
		
		ShowHistoryCallSipDialog.showDialog(null, "main.dialog.showhistorycallsip.title",selectedHistoryCall);
		
		logger.trace("Exit showHistoryCall");
	}

	@Override
	protected void removeAllHistoryCall() {
		
		logger.trace("Enter removeAllHistoryCall");
		
		int result = QuestionDialog.showDialog(null,"main.dialog.question.history.call.sip.deleted.all.title","main.dialog.question.history.call.sip.deleted.all");
		if(result == QuestionDialog.ACCEPT_OPTION){
			//Eliminamos todos los historicos de llamadas en la BBDD
			HistorySipService historySipService = CTX.ctx.getBean(HistorySipService.class);
			historySipService.removeAllHistoryCall();
			//Eliminamos todos los historicos de llamada en el modelo de la tabla de historicos de llamada
			((HistoryCallTableModel) historyCallModel).clean();
		}
		logger.trace("Exit removeAllHistoryCall");
	}
	
	@Override
	protected void removeHistoryCount() {
		
		logger.trace("Enter removeHistoryCount");
		
		int result = QuestionDialog.showDialog(null,"main.dialog.question.history.count.sip.deleted.title","main.dialog.question.history.count.sip.deleted");
		if(result == QuestionDialog.ACCEPT_OPTION){
			//Eliminamos el historico de cuenta en la BBDD
			HistorySipService historySipService = CTX.ctx.getBean(HistorySipService.class);
			historySipService.removeHistoryCount((HistoryCountSip) selectedHistoryCount);
			//Eliminamos el historico de cuenta en el modelo de la tabla de historicos de cuenta
			((HistoryCountTableModel) historyCountModel).removeRow(selectedHistoryCount);
		}
		logger.trace("Exit removeHistoryCount");
	}

	@Override
	protected void showHistoryCount() {
		logger.trace("Enter showHistoryCount");
		
		ShowHistoryCountSipDialog.showDialog(null, "main.dialog.showhistorycountsip.title",selectedHistoryCount);
		
		logger.trace("Exit showHistoryCount");
	}

	@Override
	protected void removeAllHistoryCount() {
		logger.trace("Enter removeAllHistoryCount");
		
		int result = QuestionDialog.showDialog(null,"main.dialog.question.history.count.sip.deleted.all.title","main.dialog.question.history.count.sip.deleted.all");
		if(result == QuestionDialog.ACCEPT_OPTION){
			//Eliminamos todos los historicos de cuenta en la BBDD
			HistorySipService historySipService = CTX.ctx.getBean(HistorySipService.class);
			historySipService.removeAllHistoryCount();
			//Eliminamos todos los historicos de cuenta en el modelo de la tabla de historicos de cuenta
			((HistoryCountTableModel) historyCountModel).clean();
		}
		
		logger.trace("Exit removeAllHistoryCount");
		
	}
}
