package com.jmdomingueza.telefonoip.presentacion.factories;

import java.awt.LayoutManager;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.negocio.beans.Count;
import com.jmdomingueza.telefonoip.negocio.beans.CountDummy;
import com.jmdomingueza.telefonoip.negocio.beans.CountSip;
import com.jmdomingueza.telefonoip.presentacion.labels.DummyStateLabel;
import com.jmdomingueza.telefonoip.presentacion.labels.SipStateLabel;
import com.jmdomingueza.telefonoip.presentacion.list.listeners.CountListSelectionListener;
import com.jmdomingueza.telefonoip.presentacion.list.models.CountListModel;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;
import com.jmdomingueza.telefonoip.presentacion.table.renders.HeaderTableRenderer;

/**
 * Clase que implementa la generacion de todos los componentes de Swing
 * 
 * @author jmdomingueza
 *
 */
public class ComponentFactory {

	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");

	private static final Log logger = LogFactory.getLog(ComponentFactory.class);

	/**
	 * Metodo que crea una etiqueta sin texto
	 * 
	 * @return Etiqueta
	 */
	public static JLabel createLabel() {
		logger.trace("Enter createLabel - sin parametros");
		logger.trace("Exit createLabel - sin parametros");
		return new JLabel();
	}

	/**
	 * Metodo que crea una etiqueta con el texto que pasamos
	 * 
	 * @param text
	 *            Texto de la etiqueta
	 * @return Etiqueta
	 */
	public static JLabel createLabel(String key) {

		logger.trace("Enter createLabel - key");

		String text = resourceService.getProperty(key + ".text");
		Icon icon = resourceService.getIcon(key + ".icon");

		if (icon != null) {
			logger.trace("Exit createLabel - key, icon !=null");
			return new JLabel(text, icon, JLabel.CENTER);
		} else {
			logger.trace("Exit createLabel - key");
			return new JLabel(text, JLabel.CENTER);
		}
	}

	/**
	 * Metodo que crea una etiqueta con el estado de una cuenta dummy que
	 * pasamos
	 * 
	 * @param state
	 *            Estado de una cuenta dummy
	 * @return Etiqueta
	 */
	public static DummyStateLabel createDummyStateLabel(CountDummy.State state) {
		logger.trace("Enter createDummyStateLabel");
		logger.trace("Exit createDummyStateLabel");
		return new DummyStateLabel(state);
	}

	/**
	 * Metodo que crea una etiqueta con el estado de una cuenta sip que pasamos
	 * 
	 * @param state
	 *            Estado de una cuenta sip
	 * @return Etiqueta
	 */
	public static SipStateLabel createSipStateLabel(CountSip.State state) {
		logger.trace("Enter createSipStateLabel");
		logger.trace("Exit createSipStateLabel");
		return new SipStateLabel(state);
	}

	/**
	 * Metodo que crea un boton con el texto que pasamos
	 * 
	 * @param text
	 *            Texto del boton
	 * @return Boton
	 */
	public static JButton createButton(String key) {

		logger.trace("Enter createButton");

		String text = resourceService.getProperty(key + ".text");
		Icon icon = resourceService.getIcon(key + ".icon");

		if (icon != null) {
			logger.trace("Exit createButton - icon!=null");
			return new JButton(text, icon);
		} else {
			logger.trace("Exit createButton");
			return new JButton(text);
		}
	}

	/**
	 * Metodo que crea un campo de texto con el texto que pasamos y la logitud
	 * 
	 * @param text
	 *            Texto del campo de texto
	 * @param length
	 *            Logitud del campo de texto
	 * @return Campo de Texto
	 */
	public static JTextField createTextField(String text, int length, boolean editable) {
		JTextField textField;

		logger.trace("Enter createTextField");

		textField = new JTextField(text, length);
		textField.setEditable(editable);

		logger.trace("Exit createTextField");
		return textField;
	}

	/**
	 * Metodo que crea un campo de texto de password con el texto que pasamos y
	 * la logitud
	 * 
	 * @param text
	 *            Texto del campo de texto
	 * @param length
	 *            Logitud del campo de texto
	 * @return Campo de Password
	 */
	public static JPasswordField createPasswordField(String text, int length, boolean editable) {
		JPasswordField passwordField;

		logger.trace("Enter createPasswordField");

		passwordField = new JPasswordField(text, length);
		passwordField.setEditable(editable);

		logger.trace("Exit createPasswordField");
		return passwordField;
	}

	/**
	 * Metodo que crea un Panel con el Layout GroupLayout y Borde
	 * CompoundBorder(2,2,2,2)
	 * 
	 * @return Panel
	 */
	public static JPanel createPanel() {

		logger.trace("Enter createPanel");

		JPanel panel = new JPanel();
		GroupLayout layoutGroup = new GroupLayout(panel);

		panel.setLayout(layoutGroup);
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
				BorderFactory.createEmptyBorder(2, 2, 2, 2));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), border));

		logger.trace("Exit createPanel");
		return panel;
	}

	/**
	 * Metodo que crea un Panel con el Layout con el Layout que pasamos como
	 * parametro
	 * 
	 * @return Panel
	 */
	public static JPanel createPanel(LayoutManager layout) {

		logger.trace("Enter createPanel - layout");

		JPanel panel = new JPanel(layout);

		logger.trace("Exit createPanel - layout");
		return panel;
	}

	/**
	 * Metodo que crea un ComboBox con el vector de String que pasamos
	 * 
	 * @param vector
	 *            Vector de String
	 * @return ComboBox
	 */
	public static JComboBox<String> createComboBox(Vector<String> vector) {

		logger.trace("Enter createComboBox");

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(vector);
		JComboBox<String> comboBox = new JComboBox<>(model);

		logger.trace("Exit createComboBox");
		return comboBox;
	}

	
	/**
	 * Metodo que crea un ComboBox con el vector de String que pasamos
	 * 
	 * @param vector
	 *            Vector de String
	 * @return ComboBox
	 */
	public static JComboBox<Count> createCountComboBox(Vector<Count> vector) {

		logger.trace("Enter createComboBox");

		DefaultComboBoxModel<Count> model = new DefaultComboBoxModel<>(vector);
		JComboBox<Count> comboBox = new JComboBox<>(model);

		logger.trace("Exit createComboBox");
		return comboBox;
	}

	/**
	 * Metodo que crea un RadioButton con el texto de pasamos
	 * 
	 * @param text
	 *            Texto
	 * @return RAdioButoon
	 */
	public static JRadioButton createRadioButton(String text) {

		logger.trace("Enter createRadioButton");

		JRadioButton radio = new JRadioButton(text);

		logger.trace("Exit createRadioButton");
		return radio;
	}

	/**
	 * Metodo que crea un Grupo de RAdioButton con el vector de RadioButton que
	 * pasamos
	 * 
	 * @param radioVector
	 * @return
	 */
	public static ButtonGroup createButtonGroup(Vector<JRadioButton> radioVector) {

		logger.trace("Enter createButtonGroup");

		ButtonGroup group = new ButtonGroup();
		for (JRadioButton radio : radioVector) {
			group.add(radio);
		}

		logger.trace("Exit createButtonGroup");
		return group;
	}

	/**
	 * Metodo que crea una Lista de cuentas
	 * 
	 * @return
	 */
	public static JList<Count> createCountJList(CountListModel listModel) {

		logger.trace("Enter createCountJList");

		JList<Count> jList = new JList<>(listModel);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) jList.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jList.addListSelectionListener(new CountListSelectionListener(listModel,jList.getSelectionModel()));
		jList.setBorder(BorderFactory.createEtchedBorder());

		logger.trace("Exit createCountJList");
		return jList;
	}

	public static JTable createTable(TableModel model, TableColumnModel columnModel,
			 ListSelectionModel listSelectionModel,int rowHeight) {

		JTable table = new JTable();

		table.setRowHeight(rowHeight);
		table.getTableHeader().setDefaultRenderer(new HeaderTableRenderer());

		if (listSelectionModel != null) {
			table.setSelectionModel(listSelectionModel);
		} else {
			table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		
		if (columnModel != null) {
			table.setAutoCreateColumnsFromModel(false);
			table.setColumnModel(columnModel);
		}
		table.setModel(model);

		return table;
	}
	
	public static TableColumnModel createTableColumnModel(TableModel model, int[] columnWidths, TableCellRenderer[] cellRenderers) {
		TableColumnModel columnModel = new DefaultTableColumnModel();
		columnModel.setSelectionModel(new DefaultListSelectionModel());

		for (int i = 0; i < model.getColumnCount(); i++) {
			columnModel.addColumn(createTableColumn(i, model.getColumnName(i), columnWidths[i],cellRenderers[i]));
		}

		return columnModel;
	}
	
	public static TableColumn createTableColumn(int i, String name, int width, TableCellRenderer cellRenderer) {
		TableColumn column = new TableColumn(i, width);
		column.setIdentifier(name);
		column.setHeaderValue(name);
		column.setMaxWidth(width);
		column.setCellRenderer(cellRenderer);
		return column;
	}
	

}
