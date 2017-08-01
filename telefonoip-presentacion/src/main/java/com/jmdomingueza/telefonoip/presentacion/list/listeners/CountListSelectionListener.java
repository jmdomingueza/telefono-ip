package com.jmdomingueza.telefonoip.presentacion.list.listeners;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jmdomingueza.telefonoip.common.messages.MessageService;
import com.jmdomingueza.telefonoip.common.messages.ihm.SelectionMessage;
import com.jmdomingueza.telefonoip.negocio.beans.Count;
import com.jmdomingueza.telefonoip.presentacion.list.models.CountListModel;

public class CountListSelectionListener implements ListSelectionListener {

	private CountListModel model;
	private ListSelectionModel selection;

	public CountListSelectionListener(CountListModel model,
			ListSelectionModel selection) {
		this.model = model;
		this.selection = selection;
	}

	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			if (model.getSize() > 0 && selection.isSelectionEmpty()) {
				selection.setValueIsAdjusting(true);
				selection.setSelectionInterval(-1, -1);
				selection.setValueIsAdjusting(false);
				MessageService
				.sendMessage(new SelectionMessage(this, null));
			} else {
				int row = selection.getMinSelectionIndex();
				if (row > -1) {
					Count count = model.get(row);

					selection.setValueIsAdjusting(true);
					selection.setValueIsAdjusting(false);

					MessageService
							.sendMessage(new SelectionMessage(this, count));
				}else{
					MessageService
					.sendMessage(new SelectionMessage(this, null));
				}
			}
		}
	}

}
