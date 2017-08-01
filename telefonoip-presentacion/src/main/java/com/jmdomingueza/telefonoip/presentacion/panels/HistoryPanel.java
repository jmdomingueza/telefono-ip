package com.jmdomingueza.telefonoip.presentacion.panels;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.negocio.beans.HistoryCall;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCount;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.table.listeners.HistoryCallTableSelectionListener;
import com.jmdomingueza.telefonoip.presentacion.table.listeners.HistoryCountTableSelectionListener;
import com.jmdomingueza.telefonoip.presentacion.table.models.HistoryCallTableModel;
import com.jmdomingueza.telefonoip.presentacion.table.models.HistoryCountTableModel;
import com.jmdomingueza.telefonoip.presentacion.table.renders.DirectionHistoryCallCellTableRenderer;
import com.jmdomingueza.telefonoip.presentacion.table.renders.StateHistoryCallCellTableRenderer;
import com.jmdomingueza.telefonoip.presentacion.table.renders.StringCellTableRenderer;

/**
 * Clase abstracta que implementa un panel donde aparece el historico de llamadas con su
 * informacion, el historico de cuentas con toda su informacion, tambien contiene los 
 * botones de eliminar, eliminar todos y ver tanto para el historico de cuentas como para
 * el historico de llamadas.
 * @author jmdomingueza
 *
 */
public abstract class HistoryPanel extends DefaultPanel{
	
	private static final long serialVersionUID = -1530408955639589460L;

	private static final Log logger = LogFactory.getLog(HistoryPanel.class);
	
	public static final int[] COLUMNS_HISTORY_CALL_WIDTHS = new int[] { 90, 90,90, 90, 150,150};
	
	public static final TableCellRenderer[] COLUMNS_HISTORY_CALL_RENDERS = new TableCellRenderer[] { new DirectionHistoryCallCellTableRenderer(), new StateHistoryCallCellTableRenderer(),new StringCellTableRenderer(), new StringCellTableRenderer(), new StringCellTableRenderer(),new StringCellTableRenderer() };
	
	public static final int[] COLUMNS_HISTORY_COUNT_WIDTHS = new int[] {210, 210, 210,210};
	
	public static final TableCellRenderer[] COLUMNS_HISTORY_COUNT_RENDERS = new TableCellRenderer[] { new StringCellTableRenderer(),  new StringCellTableRenderer(),new StringCellTableRenderer(),new StringCellTableRenderer()};
	
	protected TableModel historyCallModel;
	
	protected TableModel historyCountModel;
	
	protected JTable historyCallTable;
	
	protected JTable historyCountTable;
	
	protected JButton removeHistoryCallButton;
	protected JButton removeAllHistoryCallButton;
	protected JButton showHistoryCallButton;

	protected JButton removeHistoryCountButton;
	protected JButton removeAllHistoryCountButton;
	protected JButton showHistoryCountButton;
	
	/**
	 * Historico de llamada selecionado
	 */
	protected HistoryCall selectedHistoryCall;

	/**
	 * Historico de cuenta selecionado
	 */
	protected HistoryCount selectedHistoryCount;
	
	/**
	 * Constructor de la clase
	 * @param title
	 */
	public HistoryPanel(String title) {
		super(title);
		
		logger.trace("Enter HistoryPanel");
		
		logger.trace("Exit HistoryPanel");
	}
	
	@Override
	protected JComponent createMainPane() {
		TableColumnModel historyCallColumnModel,historyCountColumnModel;
		ListSelectionModel historyCallListSelectionModel,historyCountistSelectionModel;
		
		logger.trace("Enter createMainPane");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		
		historyCallModel =  new HistoryCallTableModel();
		historyCallColumnModel =	ComponentFactory.createTableColumnModel(historyCallModel, COLUMNS_HISTORY_CALL_WIDTHS,COLUMNS_HISTORY_CALL_RENDERS);

		historyCallListSelectionModel = new DefaultListSelectionModel();
		historyCallListSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		historyCallListSelectionModel.addListSelectionListener(new HistoryCallTableSelectionListener(historyCallModel,historyCallListSelectionModel));
		
		historyCallTable = ComponentFactory.createTable(historyCallModel, historyCallColumnModel,historyCallListSelectionModel, 20);
		JScrollPane historyCallScrollPane = new JScrollPane(historyCallTable);
		
		removeHistoryCallButton = createRemoveHitoryCallButton();
		removeAllHistoryCallButton = createRemoveAllHitoryCallButton();
		showHistoryCallButton = createShowHitoryCallButton();
		
		historyCountModel =  new HistoryCountTableModel();
		historyCountColumnModel =	ComponentFactory.createTableColumnModel(historyCountModel, COLUMNS_HISTORY_COUNT_WIDTHS,COLUMNS_HISTORY_COUNT_RENDERS);

		historyCountistSelectionModel = new DefaultListSelectionModel();
		historyCountistSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		historyCountistSelectionModel.addListSelectionListener(new HistoryCountTableSelectionListener(historyCountModel,historyCountistSelectionModel));
		
		historyCountTable = ComponentFactory.createTable(historyCountModel, historyCountColumnModel,historyCountistSelectionModel, 20);
		JScrollPane historyCountScrollPane = new JScrollPane(historyCountTable);
		
		removeHistoryCountButton = createRemoveHitoryCountButton();
		removeAllHistoryCountButton = createRemoveAllHitoryCountButton();
		showHistoryCountButton = createShowHitoryCountButton();
		
		load();
		
		updateButtons();
				
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(
			layoutGroup.createSequentialGroup()
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(historyCallScrollPane)
					.addGroup(layoutGroup.createSequentialGroup()
						.addComponent(removeHistoryCallButton)
						.addComponent(removeAllHistoryCallButton)
						.addComponent(showHistoryCallButton)
					)
					.addComponent(historyCountScrollPane)
					.addGroup(layoutGroup.createSequentialGroup()
						.addComponent(removeHistoryCountButton)
						.addComponent(removeAllHistoryCountButton)
						.addComponent(showHistoryCountButton)
					)
				)
		);
		//Layout Vertical
		layoutGroup.setVerticalGroup(
			layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layoutGroup.createSequentialGroup()
					.addComponent(historyCallScrollPane)
					.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(removeHistoryCallButton)
						.addComponent(removeAllHistoryCallButton)
						.addComponent(showHistoryCallButton)
					)
					.addComponent(historyCountScrollPane)
					.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(removeHistoryCountButton)
						.addComponent(removeAllHistoryCountButton)
						.addComponent(showHistoryCountButton)
					)
				)
		);

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		logger.trace("Exit createMainPane");
		return panel;
	}
	
	/**
	 * Metodo que crea el boton de borrar un historico de llamada
	 * @return
	 */
	protected JButton createRemoveHitoryCallButton() {
		JButton button;
		logger.trace("Enter createRemoveHitoryCallButton");
		
		button = ComponentFactory.createButton("main.panel.history.button.removehistorycall");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeHistoryCall();
			}
		});
		
		logger.trace("Exit createRemoveHitoryCallButton");
		return button;
	}
	
	/**
	 * Metodo que crea el boton de ver un historico de llamada
	 * @return
	 */
	private JButton createShowHitoryCallButton() {
		JButton button;
		logger.trace("Enter createShowHitoryCallButton");
		
		button = ComponentFactory.createButton("main.panel.history.button.showhistorycall");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHistoryCall();
			}
		});
		
		logger.trace("Exit createShowHitoryCallButton");
		return button;
	}

	/**
	 * Metodo que crea el boton de eliminar todos los historicos de llamada
	 * @return
	 */
	private JButton createRemoveAllHitoryCallButton() {
		JButton button;
		logger.trace("Enter createRemoveAllHitoryCallButton");
		
		button = ComponentFactory.createButton("main.panel.history.button.removeallhistorycall");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAllHistoryCall();
			}
		});
		
		logger.trace("Exit createRemoveAllHitoryCallButton");
		return button;
	}

	/**
	 * Metodo que crea el boton de de borrar un historico de cuenta
	 * @return
	 */
	protected JButton createRemoveHitoryCountButton() {
		JButton button;
		logger.trace("Enter createRemoveHitoryCountButton");
		
		button = ComponentFactory.createButton("main.panel.history.button.removehistorycount");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeHistoryCount();
			}
		});
		
		logger.trace("Exit createRemoveHitoryCountButton");
		return button;
	}
	
	/**
	 * Metodo que crea el boton de ver un historico de cuenta
	 * @return
	 */
	private JButton createShowHitoryCountButton() {
		JButton button;
		logger.trace("Enter createShowHitoryCountButton");
		
		button = ComponentFactory.createButton("main.panel.history.button.showhistorycount");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHistoryCount();
			}
		});
		logger.trace("Exit createShowHitoryCountButton");
		return button;
	}

	/**
	 * Metodo que crea el boton de eliminar todos los historicos de cuenta
	 * @return
	 */
	private JButton createRemoveAllHitoryCountButton() {
		JButton button;
		logger.trace("Enter createRemoveAllHitoryCountButton");
		button = ComponentFactory.createButton("main.panel.history.button.removeallhistorycount");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAllHistoryCount();
			}
		});
		logger.trace("Exit createRemoveAllHitoryCountButton");
		return button;
	}

	/**
	 *  Metodo que realiza la carga de las cuentas
	 */
	protected abstract void load() ;

	/**
	 * Metodo que Habilita o desabilita los botones dependiendo
	 * de el estado de las llamadas de la tabla.
	 */
	protected  abstract void  updateButtons();
	
	/**
	 * Metodo que borra un historico de llamada
	 */
	protected  abstract void removeHistoryCall() ;
	
	/**
	 * Metodo que visualiza un historico de llamada
	 */
	protected abstract void showHistoryCall();
	
	/**
	 * Metodo que borra todos los historicos de llamada
	 */
	protected abstract void removeAllHistoryCall();
	/**
	 * Metodo que borra un historico de cuenta
	 */
	protected  abstract void removeHistoryCount() ;
	
	/**
	 * Metodo que visualiza un historico de cuenta
	 */
	protected abstract void showHistoryCount();
	
	/**
	 * Metodo que borra todos los historicos de cuenta
	 */
	protected abstract void removeAllHistoryCount();
}
