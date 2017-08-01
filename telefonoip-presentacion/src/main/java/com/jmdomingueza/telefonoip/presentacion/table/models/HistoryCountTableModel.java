package com.jmdomingueza.telefonoip.presentacion.table.models;

import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCount;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountSip;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;

/**
 * Clase  que implementa el modelo de de la tabla con las llamadas.
 * 
 * @author jmdomingueza
 *
 */
public class HistoryCountTableModel extends GenericTableModel<HistoryCount> {

	private static final long serialVersionUID = -1638440653732719160L;

	private static final Log logger = LogFactory.getLog(HistoryCountTableModel.class);

	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");

	/**
	 * Numero de la columna del usuario del hitorico de cuenta
	 */
	public static final int USER_COLUMN = 0;
	
	/**
	 * Numero de la columna de la accion del historico de cuenta
	 */
	public static final int ACTION_COLUMN = 1;
	
	/**
	 * Numero de la columna de el estado del historico de cuenta
	 */
	public static final int STATE_COLUMN = 2;
	
	/**
	 * Numero de la columna de la fecha del historico de cuenta
	 */
	public static final int DATE_COLUMN = 3;

	/**
	 * Texto de la cabecera del usuario del historico de cuenta
	 */
	private static final String USER_TEXT = resourceService.getProperty("model.table.historycount.user");

	/**
	 * Texto de la cabecera de la accion del historico de cuenta
	 */
	private static final String ACTION_TEXT = resourceService.getProperty("model.table.historycount.action");

	/**
	 * Texto de la cabecera del estado del historico de cuenta
	 */
	private static final String STATE_TEXT = resourceService.getProperty("model.table.historycount.state");

	/**
	 * Texto de la cabecera de la fecha del historico de cuenta
	 */
	private static final String DATE_TEXT = resourceService.getProperty("model.table.historycount.date");


	/**
	 * Array con todo los textos de las cabeceras
	 */
	public static final String[] COLUMNS = new String[] { USER_TEXT, ACTION_TEXT,STATE_TEXT,DATE_TEXT };

	/**
	 * Constructor de la clase
	 */
	public HistoryCountTableModel() {

		super(COLUMNS);

		logger.trace("Enter HistoryCountTableModel");

		logger.trace("Exit HistoryCountTableModel");
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
		logger.trace("Enter getColumnClass");
		
		Class<?> clazz = String.class;

		switch (columnIndex) {
		case ACTION_COLUMN:
		clazz = HistoryCount.Action.class;
		break;
	}
		logger.trace("Exit getColumnClass");
		return clazz;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		logger.trace("Enter getValueAt");
		
		HistoryCount row = getRow(rowIndex);

		Object value = "";

		switch (columnIndex) {
		case USER_COLUMN:
			value = row.toString();
			break;
		case ACTION_COLUMN:
			value = row.getAction();
			break;
		case STATE_COLUMN:
			if(row instanceof HistoryCountDummy){
				value = ((HistoryCountDummy) row).getState();
			}else if(row instanceof HistoryCountSip){
				value = ((HistoryCountSip) row).getState();
			}
			break;
		case DATE_COLUMN:
			SimpleDateFormat dateFormat = new SimpleDateFormat(resourceService.getProperty("model.table.historycount.date.format"));
			if(row.getDate()==null)
				value = "";
			else
				value = dateFormat.format( row.getDate());
			break;
		}
		
		logger.trace("Exit getValueAt");
		return (value != null) ? value : "";
	}

}
