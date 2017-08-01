package com.jmdomingueza.telefonoip.presentacion.panels;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.negocio.beans.Count;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.table.listeners.CallTableSelectionListener;
import com.jmdomingueza.telefonoip.presentacion.table.models.CallTableModel;
import com.jmdomingueza.telefonoip.presentacion.table.renders.DirectionCallCellTableRenderer;
import com.jmdomingueza.telefonoip.presentacion.table.renders.StateCallCellTableRenderer;
import com.jmdomingueza.telefonoip.presentacion.table.renders.StringCellTableRenderer;

/**
 * Clase abstracta que implementa un panel donde aparece la lista de llamadas con su
 * informacion y la posiblidad de responder,colgar, transferir, conferencia o llamar
 * @author jmdomingueza
 *
 */
public abstract class CallPanel extends DefaultPanel{
	
	private static final long serialVersionUID = -1530408955639589460L;

	private static final Log logger = LogFactory.getLog(CallPanel.class);
	
	public static final int[] COLUMNS_CALL_WIDTHS = new int[] { 110, 110,110, 90, 210};
	
	public static final TableCellRenderer[] COLUMNS_CALL_RENDERS = new TableCellRenderer[] { new DirectionCallCellTableRenderer(), new StateCallCellTableRenderer(),new StringCellTableRenderer(), new StringCellTableRenderer(), new StringCellTableRenderer() };
	
	protected TableModel model;
	
	protected JTable table;
	
	/**
	 * Boton de Llamar
	 */
	protected JButton dialButton;
	
	/**
	 * Boton de Responder
	 */
	protected JButton responseButton;
	
	/**
	 * Boton de Colgar
	 */
	protected JButton hangupButton;
	
	/**
	 * Boton de Cancelar
	 */
	protected JButton cancelButton;
	
	/**
	 * Boton de Transferir
	 */
	protected JButton transferButton; 
	
	/**
	 * Boton de Conferencia
	 */
	protected JButton conferenceButton;
	
	/**
	 * Boton de Retener
	 */
	protected JButton heldButton;
	
	/**
	 * Boton de Recuperar
	 */
	protected JButton retrieveButton;
	
	/**
	 * Vector con las cuentas registradas desde donde se va hacer la llamada
	 */
	protected Vector<Count> vector;
	
	/**
	 * ComboBox con las cuentas registradas desde donde se va hacer la llamada
	 */
	protected JComboBox<Count> countComboBox;
	
	/**
	 * Campo de texto con el numero al que realizar la llamada
	 */
	protected JTextField callTextField;
	
	/**
	 * Constructor de la clase
	 * @param title
	 */
	public CallPanel(String title) {
		super(title);
		
		logger.trace("Enter CallPanel");
		
		logger.trace("Exit CallPanel");
	}
	
	@Override
	protected JComponent createMainPane() {
		TableColumnModel columnModel;
		
		logger.trace("Enter createMainPane");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		
		model =  new CallTableModel();
		columnModel =	ComponentFactory.createTableColumnModel(model, COLUMNS_CALL_WIDTHS,COLUMNS_CALL_RENDERS);

		ListSelectionModel listSelectionModel = new DefaultListSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectionModel.addListSelectionListener(new CallTableSelectionListener(model,listSelectionModel));
		
		table = ComponentFactory.createTable(model, columnModel,listSelectionModel, 20);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		dialButton = createDialButton();
		responseButton = createResponseButton();
		hangupButton = createHangupButton();
		cancelButton = createCancelButton();
		transferButton = createTransferButton();
		conferenceButton = createConferenceButton();
		heldButton = createHeldButton();
		retrieveButton = createRetrieveButton();
		
		callTextField = ComponentFactory.createTextField("", 20, true);
		
		vector = new Vector<>();
	
		
		load();
		
		countComboBox = createCountComboBox(vector);
		
		updateButtons();
				
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(
			layoutGroup.createSequentialGroup()
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(scrollPane)
					.addGroup(layoutGroup.createSequentialGroup()
						.addComponent(responseButton)
						.addComponent(hangupButton)
						.addComponent(cancelButton)
						.addComponent(transferButton)
						.addComponent(conferenceButton)
						.addComponent(heldButton)
						.addComponent(retrieveButton)
					)
					.addGroup(layoutGroup.createSequentialGroup()
						.addComponent(countComboBox)
						.addComponent(callTextField)	
						.addComponent(dialButton)	
					)
				)
		);
		//Layout Vertical
		layoutGroup.setVerticalGroup(
			layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layoutGroup.createSequentialGroup()
					.addComponent(scrollPane)	
					.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(responseButton)
						.addComponent(hangupButton)
						.addComponent(cancelButton)
						.addComponent(transferButton)
						.addComponent(conferenceButton)
						.addComponent(heldButton)
						.addComponent(retrieveButton)
					)
					.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(countComboBox)
							.addComponent(callTextField)	
							.addComponent(dialButton)
						)
				)
		);

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		logger.trace("Exit createMainPane");
		return panel;
	}

	/**
	 * Metodo que crea el boton de Llamar
	 * @return
	 */
	protected JButton createDialButton() {
		
		logger.trace("Enter createDialButton");
		
		JButton button = ComponentFactory.createButton("main.panel.call.button.dial");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dial();
			}
		});
		
		logger.trace("Exit createDialButton");
		return button;
	}
	
	/**
	 * Metodo que crea el boton de Responder
	 * @return
	 */
	protected JButton createResponseButton() {
		
		logger.trace("Enter createResponseButton");
		
		JButton button = ComponentFactory.createButton("main.panel.call.button.response");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				response();
			}
		});
		
		logger.trace("Exit createResponseButton");
		return button;
	}
	
	/**
	 * Metodo que crea el boton de Colgar
	 * @return
	 */
	protected JButton createHangupButton() {
		
		logger.trace("Enter createHangupButton");
		
		JButton button = ComponentFactory.createButton("main.panel.call.button.hangup");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hangup();
			}
		});
		
		logger.trace("Exit createHangupButton");
		return button;
	}
	
	/**
	 * Metodo que crea el boton de Cancelar
	 * @return
	 */
	protected JButton createCancelButton() {
		logger.trace("Enter createCancelButton");
		
		JButton button = ComponentFactory.createButton("main.panel.call.button.cancel");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		
		logger.trace("Exit createCancelButton");
		return button;
	}
	
	/**
	 * Metodo que crea el boton de Transferir
	 * @return
	 */
	protected JButton createTransferButton() {
		
		logger.trace("Enter createTransferButton");
		
		JButton button = ComponentFactory.createButton("main.panel.call.button.transfer");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transfer();
			}
		});
		
		logger.trace("Exit createTransferButton");
		return button;
	}
	
	/**
	 * Metodo que crea el boton de Conferencia
	 * @return
	 */
	protected JButton createConferenceButton() {
		
		logger.trace("Enter createConferenceButton");
		
		JButton button = ComponentFactory.createButton("main.panel.call.button.conference");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conference();
			}
		});
		
		logger.trace("Exit createConferenceButton");
		return button;
	}
	
	/**
	 * Metodo que crea el boton de Retenida
	 * @return
	 */
	protected JButton createHeldButton() {
		
		logger.trace("Enter createHeldButton");
		
		JButton button = ComponentFactory.createButton("main.panel.call.button.held");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				held();
			}
		});
		
		logger.trace("Exit createHeldButton");
		return button;
	}
	
	/**
	 * Metodo que crea el boton de Recuperar
	 * @return
	 */
	protected JButton createRetrieveButton() {
		
		logger.trace("Enter createRetrieveButton");
		
		JButton button = ComponentFactory.createButton("main.panel.call.button.retrieve");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retrieve();
			}
		});
		
		logger.trace("Exit createRetrieveButton");
		return button;
	}
	
	/**
	 * Metodo crea el combobox con las cuentas 
	 * @return
	 */
	protected JComboBox<Count> createCountComboBox(Vector<Count> vector) {
		
		logger.trace("Enter createCountComboBox");
		
		JComboBox<Count> comboBox = ComponentFactory.createCountComboBox(vector);
		
		logger.trace("Exit createCountComboBox");
		return comboBox;
	}

	/**
	 *  Metodo que realiza la carga de las cuentas
	 */
	protected abstract void load() ;
		
	/**
	 * Metodo que realiza la llamada
	 */
	protected abstract void dial();
	
	/**
	 * Metodo que responde la llamada
	 */
	protected abstract void response();
	
	/**
	 * Metodo que cuelga la llamada
	 */
	protected abstract void hangup();

	/**
	 * Metodo que cancela la llamada
	 */
	protected abstract void cancel() ;
	
	/**
	 * Metodo que transfiere la llamada
	 */
	protected abstract void transfer();
	
	/**
	 * Metodo que pone en conferencia la llamada
	 */
	protected abstract void conference();
	
	/**
	 * Metodo que retiene la llamada
	 */
	protected abstract void held();
	
	/**
	 * Metodo que recupera la llamada
	 */
	protected abstract void retrieve();

	/**
	 * Metodo que Habilita o desabilita los botones dependiendo
	 * de el estado de las llamadas de la tabla.
	 */
	protected  abstract void  updateButtons();
}
