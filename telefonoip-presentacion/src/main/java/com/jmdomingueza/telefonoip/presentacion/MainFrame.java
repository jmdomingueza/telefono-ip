package com.jmdomingueza.telefonoip.presentacion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.presentacion.panels.MainPanel;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;


public class MainFrame  extends JFrame{

	private static final long serialVersionUID = 4473685876324890180L;
	
	private static final Log logger = LogFactory.getLog(MainFrame.class);
	
	private MainPanel panel;
	
	private ResourceService resourceService;

	/**
	 * Constructor de la clase
	 * @throws HeadlessException
	 */
	public MainFrame() throws HeadlessException {
		super();
		logger.trace("Enter MainFrame");

		CTX.ctx = new ClassPathXmlApplicationContext("application-context.xml");
        
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				logger.trace("Enter windowClosing");
				panel.save();
				System.exit(0);
				logger.trace("Exit windowClosing");
			}
		});

        this.getContentPane().add(createPanel(), BorderLayout.CENTER);
        
        this.setResizable(false);
        Dimension screenDimesion = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenDimesion.width/2, screenDimesion.height/2);
        this.setLocation(screenDimesion.width/4, screenDimesion.height/4);
        
       
        resourceService = CTX.ctx.getBean(ResourceService.class);
        resourceService.loadLocale(Locale.getDefault());
        logger.trace("Exit MainFrame");
	}

	/**
	 * Metodo que crea el panel que hay en el MainFrame
	 * @return
	 */
	private Component createPanel() {
		logger.trace("Enter createPanel");
		panel = new MainPanel("main.panel.main.title");
		panel.init();
		logger.trace("Exit createPanel");
		return panel;
	}
	
	/**
	 * Clase main que inicia la aplicacion
	 * @param args
	 */
	public static void main(String[] args) {
		logger.trace("Enter main");
		
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		
		logger.trace("Exit main");
	}
	
	

}
