package com.jmdomingueza.telefonoip.presentacion.panels;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.presentacion.factories.ComponentFactory;


/**
 * Clase que implementa un panel Generico
 * @author jmdomingueza
 *
 */
public abstract class DefaultPanel extends JPanel {

	private static final long serialVersionUID = 93970078009815907L;

	private static final Log logger = LogFactory.getLog(DefaultPanel.class);
	
	/**
	 * Titulo del panel
	 */
	protected String title;

	/**
	 * Layout del Panel
	 */
	protected GroupLayout layout;

	/**
	 * Constutor de la clase que se crea sin titulo
	 */
	public DefaultPanel() {
		this(null);
		logger.trace("Enter DefaultPanel");
		logger.trace("Exit DefaultPanel");
	}
	/**
	 * Constructor de la clase que crea un panel con titulo
	 * @param title Titulo que aparecera en en el principio del panel
	 */
	public DefaultPanel(String title) {
		super();
		
		logger.trace("Enter DefaultPanel");
		//Titulo del Panel
		this.title = title;
		//Borde alrededor del panel
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
				BorderFactory.createEmptyBorder(2, 2, 2, 2));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), border));
		//Layout sobre el que se destribuyen los Components
		layout = new GroupLayout(this);
		this.setLayout(layout);
		
		logger.trace("Exit DefaultPanel");
	}

	/**
	 * Metodo que se llama para crea las partes del panel. Titulo,Principal y botones
	 */
	public void init() {

		logger.trace("Enter init");
		
		JComponent title = createTitle();

		JComponent genericMainPane = createMainPane();
		
		
		//Layout Horizontal
		ParallelGroup pgroup = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		if(title!=null)
			pgroup.addComponent(title);
		if(genericMainPane!=null)
			pgroup.addComponent(genericMainPane);
		layout.setHorizontalGroup(pgroup);

		//Layout Vertical
		SequentialGroup sgroup = layout.createSequentialGroup();
		if(title!=null)
			sgroup.addComponent(title);
		if(genericMainPane!=null)
			sgroup.addComponent(genericMainPane);
		layout.setVerticalGroup(sgroup);

		this.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
		
		logger.trace("Exit init");
	}

	/**
	 * Metodo que libera todos los Componentes del panel
	 */
	public void save() {
		
		logger.trace("Enter save");
		
		this.removeAll();
		this.layout = null;
		this.title = null;
		
		logger.trace("Exit save");
	}

	/**
	 * Metodo que Crea el JComponent que va en e centro del Panel
	 * @return
	 */
	protected abstract JComponent createMainPane();
	
	/**
	 * Metodo que crea un panel que contiene el titulo del Panel.
	 * @return Panel con el titulo
	 */
	protected JPanel createTitle() {
		
		logger.trace("Enter createTitle");

		JPanel panel = ComponentFactory.createPanel();
		GroupLayout layoutGroup = (GroupLayout) panel.getLayout();
		JPanel panelLabel = ComponentFactory.createPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel label;
		if(title==null){
			return null;
		}else if (title.equals("")){
			label = ComponentFactory.createLabel();
		}else{
			label =  ComponentFactory.createLabel(title);
		}

		panelLabel.add(label);
	
		//Layout Horizontal
		layoutGroup.setHorizontalGroup(
				layoutGroup.createSequentialGroup()
					.addComponent(panelLabel));
		//Layout Vertical
		layoutGroup.setVerticalGroup(
				layoutGroup.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(panelLabel,GroupLayout.PREFERRED_SIZE,20,GroupLayout.PREFERRED_SIZE));

		panel.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		logger.trace("Exit createTitle");
		return panel;
	}



}