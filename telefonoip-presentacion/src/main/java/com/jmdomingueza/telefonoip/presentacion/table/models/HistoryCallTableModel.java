package com.jmdomingueza.telefonoip.presentacion.table.models;

import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCall;
import com.jmdomingueza.telefonoip.presentacion.services.ResourceService;

/**
 * Clase  que implementa el modelo de de la tabla con las llamadas.
 * 
 * @author jmdomingueza
 *
 */
public class HistoryCallTableModel extends GenericTableModel<HistoryCall> {

	private static final long serialVersionUID = -1638440653732719160L;

	private static final Log logger = LogFactory.getLog(HistoryCallTableModel.class);

	/**
	 * Servicio que tiene los metodos para acceder a los recursos
	 */
	private static ResourceService resourceService = (ResourceService) CTX.ctx.getBean("resourceService");

	/**
	 * Numero de la columna del Sentido
	 */
	public static final int DIRECTION_COLUMN = 0;

	/**
	 * Numero de la columna del estado
	 */
	public static final int STATE_COLUMN = 1;
	
	/**
	 * Numero de la columna de la cuenta
	 */
	public static final int COUNT_COLUMN = 2;

	/**
	 * Numero de la columna del numero
	 */
	public static final int NUMBER_COLUMN = 3;

	/**
	 * Numero de la columna del de la descripcion
	 */
	public static final int DESCRIPTION_COLUMN = 4;

	/**
	 * Numero de la columna del de la fecha
	 */
	public static final int DATE_COLUMN = 5;

	/**
	 * Texto de la cabecera del sentido
	 */
	private static final String DIRECTION_TEXT = resourceService.getProperty("model.table.historycall.direction");

	/**
	 * Texto de la cabecera del estado
	 */
	private static final String STATE_TEXT = resourceService.getProperty("model.table.historycall.state");

	/**
	 * Texto de la cabecera de la cuenta
	 */
	private static final String COUNT_TEXT = resourceService.getProperty("model.table.historycall.count");

	/**
	 * Texto de la cabecera del numero
	 */
	private static final String NUMBER_TEXT = resourceService.getProperty("model.table.historycall.number");

	/**
	 * Texto de la cabecera de la descripcion
	 */
	private static final String DESCRIPTION_TEXT = resourceService.getProperty("model.table.historycall.description");

	/**
	 * Texto de la cabecera de la fecha
	 */
	private static final String DATE_TEXT = resourceService.getProperty("model.table.historycall.date");

	/**
	 * Array con todo los textos de las cabeceras
	 */
	public static final String[] COLUMNS = new String[] { DIRECTION_TEXT, STATE_TEXT,COUNT_TEXT, NUMBER_TEXT, DESCRIPTION_TEXT,DATE_TEXT};

	/**
	 * Constructor de la clase
	 */
	public HistoryCallTableModel() {

		super(COLUMNS);

		logger.trace("Enter CallTableModel");

		logger.trace("Exit CallTableModel");
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
		logger.trace("Enter getColumnClass");
		
		Class<?> clazz = String.class;

		switch (columnIndex) {
		case STATE_COLUMN:
			clazz = HistoryCall.State.class;
			break;
		case DIRECTION_COLUMN:
			clazz = HistoryCall.Direction.class;
			break;
		}

		logger.trace("Exit getColumnClass");
		return clazz;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		logger.trace("Enter getValueAt");
		
		HistoryCall row = getRow(rowIndex);

		Object value = "";

		switch (columnIndex) {
		case DIRECTION_COLUMN:
			value = row.getDirection();
			break;
		case STATE_COLUMN:
			value = row.getState();
			break;
		case COUNT_COLUMN:
			value = row.getCount();
			break;
		case NUMBER_COLUMN:
			value = row.getNumber();
			break;
		case DESCRIPTION_COLUMN:
			value = row.getDescription();
			break;
		case DATE_COLUMN:
			SimpleDateFormat dateFormat = new SimpleDateFormat(resourceService.getProperty("model.table.historycall.date.format"));
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
