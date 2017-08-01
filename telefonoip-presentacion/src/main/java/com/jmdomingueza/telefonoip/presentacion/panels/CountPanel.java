package com.jmdomingueza.telefonoip.presentacion.panels;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.negocio.beans.Count;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.list.models.CountListModel;

/**
 * Clase abstracta que implementa un panel donde aparece la lista de cuentas con su
 * informacion y la posiblidad de crear,modificarn, eliminar, registrar o desregistar
 * @author jmdomingueza
 *
 */
public abstract class CountPanel extends DefaultPanel{
	
	private static final long serialVersionUID = -1530408955639589460L;

	private static final Log logger = LogFactory.getLog(CountPanel.class);

	/**
	 * Lista con las cuentas
	 */
	protected JList<Count> countJList; 
	
	/**
	 * Modelo de la lista de cuentas 
	 */
	protected CountListModel listModel;
	
	/**
	 * Panel con la informacion de la cuenta
	 */
	protected JPanel countInfoPanel;
	
	/**
	 * Boton de crear
	 */
	protected JButton createButton;
	
	/**
	 * Boton de modificar
	 */
	protected JButton modifyButton;
	
	/**
	 * Boton de eliminar
	 */
	protected JButton removeButton;
	
	/**
	 * Boton de registrar
	 */
	protected JButton registerButton; 
	
	/**
	 * Boton de desregistrar
	 */
	protected JButton unregisterButton;
	
	/**
	 * Constructor de la clase
	 * @param title
	 */
	public CountPanel(String title) {
		super(title);
		
		logger.trace("Enter CountPanel");
		
		logger.trace("Exit CountPanel");
	}
	
	@Override
	protected JComponent createMainPane() {
		
		logger.trace("Enter createMainPane");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		
		listModel = new CountListModel();
		
		load();
		
		JScrollPane countJListPanel = createList(listModel);
		
		
		createButton = createCreateButton();
		modifyButton = createModifyButton();
		removeButton = createDeleteButton();
		
		countInfoPanel = createInfoPanel();
				
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(
			layoutGroup.createSequentialGroup()
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(countJListPanel,GroupLayout.PREFERRED_SIZE, 150,GroupLayout.PREFERRED_SIZE)
					.addGroup(layoutGroup.createSequentialGroup()
						.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(createButton,GroupLayout.PREFERRED_SIZE, 150,GroupLayout.PREFERRED_SIZE)
							.addComponent(modifyButton,GroupLayout.PREFERRED_SIZE, 150,GroupLayout.PREFERRED_SIZE)
							.addComponent(removeButton,GroupLayout.PREFERRED_SIZE, 150,GroupLayout.PREFERRED_SIZE)
						)
					)
				)
				.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(countInfoPanel)
					)
		);
		//Layout Vertical
		layoutGroup.setVerticalGroup(
			layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layoutGroup.createSequentialGroup()
					.addComponent(countJListPanel)	
					.addGroup(layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layoutGroup.createSequentialGroup()
							.addComponent(createButton)
							.addComponent(modifyButton)
							.addComponent(removeButton)
						)
					)
				)
				.addGroup(layoutGroup.createSequentialGroup()
					.addComponent(countInfoPanel)
				)
		);

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		logger.trace("Exit createMainPane");
		return panel;
	}
	
	/**
	 * Metodo que crea un panel con la informacion de cuenta que se quiere mostrar
	 * @return
	 */
	protected abstract JPanel createInfoPanel();
	
	/**
	 * Metodo que crea un ScrollPane y dentro la lista de cuentas.
	 * @param model
	 * @return
	 */
	protected JScrollPane createList(CountListModel model) {
		
		logger.trace("Enter createList");
		
		countJList = (JList<Count>) ComponentFactory.createCountJList(model);
		
		JScrollPane pane = new JScrollPane(countJList);
		
		logger.trace("Exit createList");
		return pane;
	}

	/**
	 * Metodo que crea el boton de crear
	 * @return
	 */
	protected JButton createCreateButton() {
		
		logger.trace("Enter createCreateButton");
		
		JButton button = ComponentFactory.createButton("main.panel.count.button.create");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				create();
			}
		});
		
		logger.trace("Exit createCreateButton");
		return button;
	}
	
	
	/**
	 * Metodo que crea el boton de modificar
	 * @return
	 */
	protected JButton createModifyButton() {
		
		logger.trace("Enter createModifyButton");
		
		JButton button = ComponentFactory.createButton("main.panel.count.button.modify");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countJList.getSelectedIndex() > -1) {
					Count count = countJList.getSelectedValue();
					modify(count);
				}
			}
		});
		
		logger.trace("Exit createModifyButton");
		return button;
	}
	
	/**
	 * Metodo que crea el boton de eliminar
	 * @return
	 */
	protected JButton createDeleteButton() {
		
		logger.trace("Enter createDeleteButton");
		
		JButton button = ComponentFactory.createButton("main.panel.count.button.remove");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countJList.getSelectedIndex() > -1) {
					Count count = countJList.getSelectedValue();
					delete(count);
				}
			}
		});
		
		logger.trace("Exit createDeleteButton");
		return button;
	}
	
	/**
	 * Metodo que crea el boton de registrar
	 * @return
	 */
	protected JButton createRegisterButton() {
		
		logger.trace("Enter createRegisterButton");
		
		JButton button = ComponentFactory.createButton("main.panel.count.button.register");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countJList.getSelectedIndex() > -1) {
					Count count = countJList.getSelectedValue();
					register(count);
				}
			}
		});
		
		logger.trace("Exit createRegisterButton");
		return button;
	}
	
	/**
	 * Metodo que crea el boton de desregistrar
	 * @return
	 */
	protected JButton createUnregisterButton() {
		
		logger.trace("Enter createUnregisterButton");
		
		JButton button = ComponentFactory.createButton("main.panel.count.button.unregister");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countJList.getSelectedIndex() > -1) {
					Count count = countJList.getSelectedValue();
					unregister(count);
				}
			}
		});
		
		logger.trace("Exit createUnregisterButton");
		return button;
	}
	
	/**
	 * Metodo que realiza la carga de cuentas
	 */
	protected abstract void load();
	
	/**
	 * Metodo que actuliza todos los componentes del IHM con los valores
	 * de la cuenta seleccionada en la lista de cuentas.
	 */
	protected abstract void updateIHM();
	
	/**
	 * Meotodo que crea una cuenta
	 */
	protected abstract void create();
	
	/**
	 * Meotodo que modifica una cuenta
	 */
	protected abstract void modify(Count count);
	
	/**
	 * Meotodo que elimina una cuenta
	 */
	protected abstract void delete(Count count);
	
	/**
	 * Meotodo que registra una cuenta
	 */
	protected abstract void register(Count count);
	
	/**
	 * Meotodo que desregistra una cuenta
	 */
	protected abstract void unregister(Count count) ;
	
	
}
