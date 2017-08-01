package com.jmdomingueza.telefonoip.presentacion.utils;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Clase con metodo utiles para los dialogos
 * @author jmdomingueza
 *
 */
public class DialogIUtils {

	private static final Log logger = LogFactory.getLog(DialogIUtils.class);
	
	/**
	 * Metodo que centra el dialogo en owner
	 * @param owner
	 * @param dialog
	 */
	public static void centerDialog(Window owner, Window dialog ) {
		
		logger.trace("Enter centerDialog - owner,  dialog");
		
		centerDialog( owner, dialog, true);
		
		logger.trace("Exit centerDialog  - owner,  dialog");
	}
	
	/**
	 * Metodo que centra el dialogo en owner
	 * @param owner
	 * @param dialog
	 * @param doPack
	 */
	public static void centerDialog(Window owner, Window dialog, boolean doPack ) {
		
		logger.trace("Enter centerDialog - owner, dialog, doPack");
		
		if( doPack )
			dialog.pack();
		if(owner!=null){
			Point p = owner.getLocation();
			Dimension d = owner.getSize();
			Dimension s = dialog.getSize();
			dialog.setLocation(p.x + (d.width / 2) - (s.width / 2), 
					p.y + (d.height / 2) - (s.height / 2));
		}else{
			  Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			  dialog.setLocation(d.width/4, d.height/4);
		}
		logger.trace("Exit centerDialog - owner, dialog, doPack");
	}
	
	/**
	 * Metodo que centra el dialogo en owner
	 * @param owner
	 * @param dialog
	 */
	public static void locateDialog(Window owner, Window dialog) {
		
		logger.trace("Enter locateDialog");
		
		dialog.pack();
		
		if(owner!=null){
			Point p = owner.getLocation();
			Dimension d = owner.getSize();
			Dimension s = dialog.getSize();
			
			if (owner instanceof Dialog) {
				// si se trata de un dialog, este tendra a su vez un owner
				Point sp = owner.getOwner().getLocation();
				Dimension sd = owner.getOwner().getSize();
			
				// calculo las dimensiones de ambos dialogos
				int w = d.width + s.width;
				int h = Math.max(d.height, s.height);
			
				// centro el owner respecto a su superower
				// teniendo en cuenta las dimensiones totales
				owner.setLocation(sp.x + (sd.width / 2) - (w / 2), 
					sp.y + (sd.height / 2) - (h / 2));
				p = owner.getLocation();
				// y ahora coloco el otro dialog al lado
				dialog.setLocation(p.x + d.width, p.y);
			} else {
				owner.setLocation(p.x - (s.width / 2), 
					p.y + (d.height / 2) - (s.height / 2));
				p = owner.getLocation();
				dialog.setLocation(p.x + d.width, p.y);
			}
		}else{
			Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
			dialog.setLocation(sd.width/2, sd.height/2);
		}
		
		logger.trace("Exit locateDialog");
	}
	
	/** 
	 * Metodo que muestra un dialogo
	 * @param dialog
	 */
	public static void showDialog(Window dialog) {
		
		logger.trace("Enter showDialog - dialog");
		
		showDialog(dialog, true);
		
		logger.trace("Exit showDialog - dialog");
	}
	

	/**
	 * Metodo que muestra un dialogo
	 * @param dialog
	 * @param show
	 */
	public static void showDialog(final Window dialog, final boolean show) {
		
		logger.trace("Enter showDialog - dialog,  show");
		
		if (EventQueue.isDispatchThread()) {
			dialog.setVisible(show);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					dialog.setVisible(show);
				}
			});
		}
		logger.trace("Exit showDialog - dialog,  show");
	}

}
