package com.jmdomingueza.telefonoip.presentacion.table.renders;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCall;
//import com.jmdomingueza.telefonoip.negocio.beans.HistoryCall;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;

/**
 * Clase que implementa una TableCellRenderer que corresponde con las celdas de
 * la columna de tipo HistoryCall.Direction de una llamada
 * 
 * @author jmdomingueza
 *
 */
public class DirectionHistoryCallCellTableRenderer extends JLabel implements TableCellRenderer {
	
	private static final long serialVersionUID = -4693324413824665224L;

	private static final Log logger = LogFactory.getLog(DirectionHistoryCallCellTableRenderer.class);

	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");
	
	/**
	 * Color del Background de la etiqueta para las llamadas salientes
	 */
	private static final Color OUT_COLOR = resourceService.getColor("main.render.direction.out");
	

	/**
	 * Color del Background de la etiqueta para las llamadas entrantes
	 */
	private static final Color IN_COLOR = resourceService.getColor("main.render.direction.in");

	/**
	 * Contructor de la clase
	 */
	public DirectionHistoryCallCellTableRenderer() {
		super();
		
		logger.trace("Enter DirectionHistoryCallCellTableRenderer");
		setHorizontalAlignment(CENTER);
		setOpaque(true);
		
		logger.trace("Exit DirectionHistoryCallCellTableRenderer");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		logger.trace("Enter getTableCellRendererComponent");
		
		HistoryCall.Direction direction = (HistoryCall.Direction) value;
		
		setText(direction.toString());
		setBorder(BorderFactory.createLineBorder(getColor(direction),1));
		
		if (isSelected) {
			setBackground(UIManager.getColor("Table.selectionBackground"));
		} else {
			setBackground( getColor(direction) );
			
		}
		
		logger.trace("Exit getTableCellRendererComponent");
		return this;
	}

	/**
	 * Metodo que devuelve el color que corresponde con el color de la 
	 * direccion que pasamos.
	 * @param direction
	 * @return
	 */
	private static Color getColor(HistoryCall.Direction direction ) {
		
		logger.trace("Enter getColor");
		
		switch (direction) {
		case in:
			logger.trace("Exit getColor - in");
			return IN_COLOR;

		case out:
		default :
			logger.trace("Exit getColor - out");
			return OUT_COLOR;
		}
	}

}
