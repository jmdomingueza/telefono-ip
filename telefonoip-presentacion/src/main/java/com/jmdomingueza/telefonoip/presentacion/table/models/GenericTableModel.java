package com.jmdomingueza.telefonoip.presentacion.table.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Clase abstracta que implementa el modelo de una tabla.
 * 
 * @author jmdomingueza
 *
 */
public abstract class GenericTableModel<T> extends AbstractTableModel {

	private static final long serialVersionUID = 8155564455479254339L;

	private static final Log logger = LogFactory.getLog(CallTableModel.class);

	/**
	 * Lista con los datos de la tabla
	 */
	protected List<T> data = new ArrayList<T>(1500);

	/**
	 * Array con todo los textos de las cabeceras de la tabla
	 */
	private String[] columns;

	/**
	 * Constructor de la clase
	 * @param columns
	 */
	public GenericTableModel(String[] columns) {
		super();
		
		logger.trace("Enter GenericTableModel");
		
		this.columns = columns;
		
		logger.trace("Exit GenericTableModel");
	}
	

	@Override
	public int getColumnCount() {
		logger.trace("Enter getColumnCount");
		logger.trace("Exit getColumnCount");
		return columns.length;
	}

	@Override
	public String getColumnName(int column) {
		logger.trace("Enter getColumnName");
		logger.trace("Exit getColumnName");
		return columns[column];
	}

	@Override
	public int findColumn(String columnName) {
		
		logger.trace("Enter findColumn");
		
		for (int i = 0; i < columns.length; i++) {
			if (columns[i].equals(columnName)) {
				logger.trace("Exit findColumn - columns[i].equals(columnName)");
				return i;
			}
		}
		logger.trace("Exit findColumn");
		return -1;
	}

	@Override
	public int getRowCount() {
		logger.trace("Enter getRowCount");
		logger.trace("Exit getRowCount");
		return data.size();
	}

	/**
	 * Metodo que devuelve el array con todo los textos 
	 * de las cabeceras de la tabla
	 * @return
	 */
	public String[] getColumns() {
		logger.trace("Enter getColumns");
		logger.trace("Exit getColumns");
		return columns;
	}
	
	/**
	 * Metodo que devuelve el indice de la fila del <T> que pasamos
	 * @param obj
	 * @return
	 */
	public int getRowIndex(T obj) {
		logger.trace("Enter getRowIndex");
		logger.trace("Exit getRowIndex");
		return data.indexOf(obj);
	}

	/**
	 * Metodo de que devuelve el <T> de la fila que pasamos
	 * @param row
	 * @return
	 */
	public T getRow(int row) {
		
		logger.trace("Enter getRow");
		
		if (row >= getRowCount() || row < 0) {
			logger.trace("Exit getRow - row >= getRowCount() || row < 0");
			return null;
		}

		logger.trace("Exit getRow");
		return data.get(row);
	}

	/**
	 * Metodo que devuelve una coleccion de <T> con todo los datos
	 * que hay en la tabla
	 * @param objs
	 */
	public void addAll(Collection<T> objs) {
		
		logger.trace("Enter addAll");
		
		data.addAll(objs);
		fireTableDataChanged();
		
		logger.trace("Exit addAll");
	}

	/**
	 * Metodo que añade una fila con el <T> que pasamos
	 * @param obj
	 */
	public void addRow(T obj) {
		
		logger.trace("Enter addRow");
		
		data.add(obj);
		int row = data.indexOf(obj);
		fireTableRowsInserted(row, row);
		
		logger.trace("Exit addRow");
	}

	/**
	 * Metodo que añade una fila con el <T> al pincipio
	 * @param obj
	 */
	public void addRowBegin(T obj) {
		
		logger.trace("Enter addRowBegin");
		
		data.add(0, obj);
		fireTableRowsInserted(0, 0);
		
		logger.trace("Exit addRowBegin");
	}

	/**
	 * Metodo que elimina la fila donde esta el <T>
	 * @param obj
	 */
	public void removeRow(T obj) {
		
		logger.trace("Enter removeRow - T obj");
		
		int row = data.indexOf(obj);
		if (row >= 0) {
			data.remove(row);
			fireTableRowsDeleted(row, row);
		}
		
		logger.trace("Exit removeRow - T obj");
	}

	/**
	 * Metodo que elimina la fila que pasamos
	 * @param nRow
	 */
	public void removeRow(int nRow) {
		
		logger.trace("Enter removeRow - int nRow");
		
		if (nRow >= getRowCount()){
			logger.trace("Exit removeRow - nRow >= getRowCount()");
			return;
		}

		data.remove(nRow);
		fireTableRowsDeleted(nRow, nRow);
		
		logger.trace("Exit removeRow - int nRow");
	}

	/**
	 * Metodo que devuelve true si el <T> esta en la tabla
	 * @param obj
	 * @return
	 */
	public boolean contains(T obj) {
		logger.trace("Enter contains");
		logger.trace("Exit contains");
		return data.contains(obj);
	}

	/**
	 * Metodo que elimina todos los datos
	 * de la tabla.
	 */
	public void clean() {
		
		logger.trace("Enter clean");
		
		data.clear();
		fireTableDataChanged();
		
		logger.trace("Exit clean");
	}

}
