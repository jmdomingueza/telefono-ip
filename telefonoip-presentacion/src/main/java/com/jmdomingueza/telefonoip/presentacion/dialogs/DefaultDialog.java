package com.jmdomingueza.telefonoip.presentacion.dialogs;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;


/**
 * Clase abstracta que crea el esqueleto de lo que es un dialogo con un
 * boton de aceptar, otro de cerrar y otro de cancelar
 * @author jmdomingueza
 *
 */
public abstract class DefaultDialog extends JDialog {

	private static final long serialVersionUID = 4133032038764429L;
	
	private static final Log logger = LogFactory.getLog(DefaultDialog.class);

	/**
	 * Opcion cancelar
	 */
	public static final int CANCEL_OPTION = 1;

	/**
	 * Opcion Aceptar
	 */
	public static final int ACCEPT_OPTION = 2;

	/**
	 * Opcion selecionada
	 */
	protected int selectedOption;
	
	/**
	 * Servicio que gestiona los recursos
	 */
	private ResourceService resourceService = CTX.ctx.getBean(ResourceService.class);

	/**
	 * Constuctor de la clase
	 * @param owner
	 * @param title
	 * @param width
	 * @param height
	 */
	public DefaultDialog(Window owner,String title, int width, int height) {
		super(owner);
		
		logger.trace("Enter DefaultDialog");
		
		//Ponemos el titulo del Dialogo
		this.setTitle(resourceService.getProperty(title+".text"));
		this.setSize(new Dimension(width, height));
		this.setModal(true);
		
		 logger.trace("Exit DefaultDialog");
	}

	/**
	 * Metodo que devuelve la opcion que se ha seleccionado
	 * @return
	 */
	public int getSelectedOption() {
		logger.trace("Enter getSelectedOption");
		 logger.trace("Exit getSelectedOption");
		return selectedOption;
	}

	/**
	 * Metodo que crea los paneles principal en el centro y el
	 * de los botones abajo
	 */
	protected void init() {
		
		logger.trace("Enter init");
		
		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layout = (GroupLayout) panel.getLayout();
		
		JComponent genericMainPane = createMainPane();
		
		JComponent genericButtonsPane = createButtons();

		//Layout Horizontal
		ParallelGroup pgroup = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		if(genericMainPane!=null)
			pgroup.addComponent(genericMainPane);
		if(genericButtonsPane!=null)
			pgroup.addComponent(genericButtonsPane);
		layout.setHorizontalGroup(pgroup);

		//Layout Vertical
		SequentialGroup sgroup = layout.createSequentialGroup();
		if(genericMainPane!=null)
			sgroup.addComponent(genericMainPane);
		if(genericButtonsPane!=null)
			sgroup.addComponent(genericButtonsPane);
		layout.setVerticalGroup(sgroup);
		
		this.setContentPane(panel);

		this.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
		
		 logger.trace("Exit init");
	}

	/**
	 * Metodo que se llama para hacer las tareas de finalizacion del panel
	 */
	protected void save() {
		logger.trace("Enter save");
		 logger.trace("Exit save");
	}

	/**
	 * Metodo que crea el panel pincipal y los elementos que van dentro de él
	 * @return
	 */
	protected abstract JComponent createMainPane();

	/**
	 * Metodo que crea el panel de los botones y los elementos que van
	 * dentro de él
	 * @return
	 */
	protected abstract JComponent createButtons();

	/**
	 * Metodo que crea el boton de aceptar
	 * @return
	 */
	protected JButton createAcceptButton() {
		
		logger.trace("Enter createAcceptButton");
		
		JButton accept = ComponentFactory.createButton("main.dialog.button.accept");
		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accept();
				closeDialog(ACCEPT_OPTION);
			}
		});
		logger.trace("Exit createAcceptButton");
		return accept;
	}
	
	/**
	 * Metodo que crea el boton de cerrar
	 * @return
	 */
	protected JButton createCloseButton() {
		
		logger.trace("Enter createCloseButton");
		
		JButton accept = ComponentFactory.createButton("main.dialog.button.close");
		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				closeDialog(ACCEPT_OPTION);
			}
		});
		logger.trace("Exit createCloseButton");
		return accept;
	}

	/**
	 *Metodo que crea el boton de cancelar
	 * @return
	 */
	protected JButton createCancelButton() {
		
		logger.trace("Enter createCancelButton");
		
		JButton cancel = ComponentFactory.createButton("main.dialog.button.cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
				closeDialog(CANCEL_OPTION);
			}
		});
		logger.trace("Exit createCancelButton");
		return cancel;
	}
	
	/**
	 * Metodo que cierra el dialogo y libera el espacio
	 * @param option
	 */
	protected void closeDialog(int option) {
		
		logger.trace("Enter closeDialog");
		
		this.selectedOption = option;
		this.save();
		this.setVisible(false);
		this.dispose();
		logger.trace("Exit closeDialog");
	}

	/**
	 * Metodo que se llama cuando se seleciona el boton de aceptar
	 */
	protected void accept() {
		logger.trace("Enter accept");
		logger.trace("Exit accept");
	}


	/**
	 * Metodo que se llama cuando se seleciona el boton de cancelar
	 */
	protected void cancel() {
		logger.trace("Enter cancel");
		logger.trace("Exit cancel");
	}

	/**
	 * Metodo que se llama cuando se seleciona el boton de cerrar
	 */
	protected void close() {
		logger.trace("Enter close");
		logger.trace("Exit close");
	}
	

	
	


}