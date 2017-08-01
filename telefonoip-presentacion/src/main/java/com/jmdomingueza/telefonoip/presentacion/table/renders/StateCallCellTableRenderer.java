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
import com.jmdomingueza.telefonoip.negocio.beans.Call;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;

/**
 * Clase que implementa una TableCellRenderer que corresponde con las celdas de
 * la columna de tipo Call.State de una llamada
 * 
 * @author jmdomingueza
 *
 */
public class StateCallCellTableRenderer extends JLabel implements TableCellRenderer {
	
	private static final long serialVersionUID = -5698402689082339362L;

	private static final Log logger = LogFactory.getLog(StateCallCellTableRenderer.class);

	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");
	
	/**
	 * Color del Background de la etiqueta para el estado de llamando
	 */
	private static final Color DIALING_COLOR = resourceService.getColor("main.render.state.dialing");
	
	/**
	 * Color del Background de la etiqueta para el estado de sonando
	 */
	private static final Color RINGING_COLOR = resourceService.getColor("main.render.state.ringing");
	
	/**
	 * Color del Background de la etiqueta para el estado de hablando y conferencia
	 */
	private static final Color TALKING_COLOR = resourceService.getColor("main.render.state.talking");
	
	/**
	 * Color del Background de la etiqueta para el estado de retenida
	 */
	private static final Color HELD_COLOR = resourceService.getColor("main.render.state.held");
	
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
	public StateCallCellTableRenderer() {
		super();
		
		logger.trace("Enter StateCallCellTableRenderer");
		
		setHorizontalAlignment(CENTER);
		setOpaque(true);
		
		logger.trace("Exit StateCallCellTableRenderer");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		logger.trace("Enter getTableCellRendererComponent");
		
		Call.State state = (Call.State) value;
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
	private static Color getColor(Call.State state) {
		
		logger.trace("Enter getColor");
		
		switch (state) {
		case dialing:
			logger.trace("Exit getColor - dialing");
			return DIALING_COLOR;
		case ringing:
			logger.trace("Exit  getColor - ringing");
			return RINGING_COLOR;
		case talking:
			logger.trace("Exit  getColor - talking");
			return TALKING_COLOR;
		case transfered:
			logger.trace("Exit  getColor - transfered");
			return TERMINATED_COLOR;
		case conference: 
			logger.trace("Exit  getColor - conference");
			return TALKING_COLOR;
		case held:
			logger.trace("Exit  getColor - held");
			return HELD_COLOR;
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
