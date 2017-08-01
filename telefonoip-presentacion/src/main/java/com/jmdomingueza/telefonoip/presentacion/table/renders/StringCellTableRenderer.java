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
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;

/**
 * Clase que implementa una TableCellRenderer que corresponde con las celdas de
 * la columna de tipo String de una llamada
 * 
 * @author jmdomingueza
 *
 */
public class StringCellTableRenderer extends JLabel implements TableCellRenderer {
	
	private static final long serialVersionUID = -7586935677617379966L;

	private static final Log logger = LogFactory.getLog(StringCellTableRenderer.class);

	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");
	
	/**
	 * Color del Background de la etiqueta para las filas pares
	 */
	private static final Color ODD_COLOR = resourceService.getColor("main.render.string.odd");
	
	/**
	 * Color del Background de la etiqueta para las llamadas salientes
	 */
	private static final Color EVEN_COLOR = resourceService.getColor("main.render.string.even");
	
	/**
	 * Contructor de la clase
	 */
	public StringCellTableRenderer() {
		super();
		
		logger.trace("Enter StringCellTableRenderer");
		
		setOpaque(true);
		setHorizontalAlignment(CENTER);
		
		logger.trace("Exit StringCellTableRenderer");
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		logger.trace("Enter getTableCellRendererComponent");
		
		setText((value != null) ? value.toString(): "");
		
		if(row % 2 == 0){
			setBorder(BorderFactory.createLineBorder(ODD_COLOR,1));
		}else{
			setBorder(BorderFactory.createLineBorder(EVEN_COLOR,1));
		}
		
		if (isSelected) {
			setBackground(UIManager.getColor("Table.selectionBackground"));
		} else {
			if(row % 2 == 0){
				setBackground(ODD_COLOR);
			}else{
				setBackground(EVEN_COLOR);
			}
		}
		
		logger.trace("Exit getTableCellRendererComponent");
		return this;
	}

}
