package com.jmdomingueza.telefonoip.presentacion.table.listeners;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.jmdomingueza.telefonoip.common.messages.MessageService;
import com.jmdomingueza.telefonoip.common.messages.ihm.SelectionMessage;
import com.jmdomingueza.telefonoip.common.messages.ihm.UpdateMessage;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCount;
import com.jmdomingueza.telefonoip.presentacion.table.models.HistoryCountTableModel;

public class HistoryCountTableSelectionListener implements ListSelectionListener, TableModelListener {

	private TableModel model;
	private ListSelectionModel selection;

	public HistoryCountTableSelectionListener(TableModel model,
			ListSelectionModel selection) {
		this.model = model;
		this.selection = selection;
	}

	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			if (model.getRowCount() > 0 && selection.isSelectionEmpty()) {
				selection.setValueIsAdjusting(true);
				selection.setSelectionInterval(0, 0);
				MessageService
						.sendMessage(new SelectionMessage(this, null));
				selection.setValueIsAdjusting(false);
			} else {
				int row = selection.getMinSelectionIndex();
				if (row > -1) {
					HistoryCount historyCount = ((HistoryCountTableModel)model).getRow(row);

					selection.setValueIsAdjusting(true);
					selection.setValueIsAdjusting(false);

					MessageService
							.sendMessage(new SelectionMessage(this, historyCount));
				}else{
					MessageService
						.sendMessage(new SelectionMessage(this, null));
				}
			}
		}
	}

	public void tableChanged(TableModelEvent e) {

		if (e.getType() == TableModelEvent.UPDATE) {
			// se actualiza una fila
			int row = e.getFirstRow();
			if (row > -1 ) {
				HistoryCount historyCount = ((HistoryCountTableModel)model).getRow(row);

				if( selection.isSelectedIndex(row) ){
					MessageService
						.sendMessage(new SelectionMessage(this, historyCount));
				}

				// Para que quien quiera se entere de que esta llamada ha cambiado de estado
				MessageService
					.sendMessage(new UpdateMessage(this, historyCount));
			}


		} else if (e.getType() == TableModelEvent.INSERT) {
			// se añade una fila
			int row = e.getFirstRow();
			if (row > -1 && model.getRowCount() == 1) {
				selection.setValueIsAdjusting(true);
				selection.setSelectionInterval(0, 0);
				selection.setValueIsAdjusting(false);
			}
		} else if (e.getType() == TableModelEvent.DELETE) {
			// Si estammos en una multiparty añadimos la llmada borrada

			// se elimina una fila
			if (selection.isSelectionEmpty()) {
				if (model.getRowCount() > 0) {
					selection.setValueIsAdjusting(true);
					selection.setSelectionInterval(0, 0);
					selection.setValueIsAdjusting(false);
				}else {
					// las tablas estan vacías, provoco una actualización de los botones
					MessageService
						.sendMessage(new SelectionMessage(this, null));
				}
			}

		}
	}

}