package com.jmdomingueza.telefonoip.presentacion.table.renders;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;

/**
 * Clase que implementa una TableCellRenderer que corresponde con las celdas de
 * la fila de la cabecera de la tabla
 * 
 * @author jmdomingueza
 *
 */
public class HeaderTableRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 442985817690279973L;

	private static final Log logger = LogFactory.getLog(HeaderTableRenderer.class);

	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");
	
	/**
	 * Color del Background de la etiqueta para las cabeceras
	 */
	private static final Color HEADER_COLOR = resourceService.getColor("main.render.header");
	
	/**
	 * Constructor de la clase
	 */
	public HeaderTableRenderer() {
		super();
		
		logger.trace("Enter HeaderTableRenderer");
		
		setHorizontalAlignment(CENTER);
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		setBackground(HEADER_COLOR);
		setOpaque(true);
		
		logger.trace("Exit HeaderTableRenderer");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		logger.trace("Enter getTableCellRendererComponent");
		
		setText(value!=null?value.toString():"");
		
		logger.trace("Exit getTableCellRendererComponent");
		return this;
	}

}
