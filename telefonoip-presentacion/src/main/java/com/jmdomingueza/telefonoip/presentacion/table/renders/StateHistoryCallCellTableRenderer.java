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
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;

/**
 * Clase que implementa una TableCellRenderer que corresponde con las celdas de
 * la columna de tipo HistoryCall.State de una llamada
 * 
 * @author jmdomingueza
 *
 */
public class StateHistoryCallCellTableRenderer extends JLabel implements TableCellRenderer {
	
	private static final long serialVersionUID = -5698402689082339362L;

	private static final Log logger = LogFactory.getLog(StateHistoryCallCellTableRenderer.class);

	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");
	
	/**
	 * Color del Background de la etiqueta para el estado de terminada y transferid
	 */
	private static final Color TERMINATED_COLOR = resourceService.getColor("main.render.state.terminated");
	
	/**
	 * Color del Background de la etiqueta para el estado de cancelada
	 */
	private static final Color CANCELED_COLOR = resourceService.getColor("main.render.state.canceled");
	
	/**
	 * Color del Background de la etiqueta para el estado de perdida
	 */
	private static final Color LOST_COLOR = resourceService.getColor("main.render.state.lost");
	
	/**
	 * Color del Background de la etiqueta para el estado de error
	 */
	private static final Color ERROR_COLOR = resourceService.getColor("main.render.state.error");
	
	/**
	 * Contructor de la clase
	 */
	public StateHistoryCallCellTableRenderer() {
		super();
		
		logger.trace("Enter StateHistoryCallCellTableRenderer");
		
		setHorizontalAlignment(CENTER);
		setOpaque(true);
		
		logger.trace("Exit StateHistoryCallCellTableRenderer");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		logger.trace("Enter getTableCellRendererComponent");
		
		HistoryCall.State state = (HistoryCall.State) value;
		setText(state.toString());
		setBorder(BorderFactory.createLineBorder(getColor(state),1));
		
		if (isSelected) {
			setBackground(UIManager.getColor("Table.selectionBackground"));

		} else {
			setBackground( getColor(state) );
		}
		
		logger.trace("Exit getTableCellRendererComponent");
		return this;
	}

	/**
	 * Metodo que devuelve el color que corresponde con el color del 
	 * estado que pasamos.
	 * @param direction
	 * @return
	 */
	private static Color getColor(HistoryCall.State state) {
		
		logger.trace("Enter getColor");
		
		switch (state) {
		case transfered:
			logger.trace("Exit  getColor - transfered");
			return TERMINATED_COLOR;
		case terminated:
			logger.trace("Exit  getColor - terminated");
			return TERMINATED_COLOR;
		case canceled:
			logger.trace("Exit  getColor - canceled");
			return CANCELED_COLOR;
		case lost:
			logger.trace("Exit  getColor - lost");
			return LOST_COLOR;
		case error:
			logger.trace("Exit  getColor - error");
			return ERROR_COLOR;
		default:
			logger.trace("Exit  getColor - default");
			return Color.WHITE;
		}
	}

}
