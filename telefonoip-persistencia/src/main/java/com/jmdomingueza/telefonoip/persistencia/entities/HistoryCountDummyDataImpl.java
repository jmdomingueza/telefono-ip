package com.jmdomingueza.telefonoip.persistencia.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

/**
 * Clase  que implementa los atributos y operaciones de la entidad 
 * HistoryCountDummyData que corresponde con la tabla HISTORICO_CUENTA_DUMMY.
 * 
 * @author jmdomingueza
 *
 */
@Entity
@Table(name="HISTORICO_CUENTA_DUMMY")
public class HistoryCountDummyDataImpl implements HistoryCountDummyData {

	private static final long serialVersionUID = 3403680987464516638L;

	private final static Logger logger = Logger.getLogger(HistoryCountDummyDataImpl.class);
	
	/**
	 * Identifidor autogenerado del historico de cuenta dummy, clave priamria.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	/**
	 * Usuario del historico de cuenta dummy.
	 */
	@Column(name = "USUARIO")
	private String user;
	
	/**
	 * Accion del historico de cuenta dummy. 
	 */
	@Column(name = "ACCION")
	private Action action;
	
	/**
	 * Estado del historico de cuenta dummy. 
	 */
	@Column(name = "ESTADO")
	private State state;
	
	/**
	 * Fecha(dia y hora) del historico de cuenta dummy. 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA")
	private  Date date;
	
	/**
	 * Atributo editable.
	 */
	@Column(name = "EDITABLE")
	private String editable;
	
	
	/**
	 * Constructor sin parametros
	 */
	public HistoryCountDummyDataImpl() {
		logger.trace("Enter HistoryCountDummyDataImpl - sin parametros");
		logger.trace("Exit HistoryCountDummyDataImpl - sin parametros");
	}
	
	/**
	 * Construtor con todos los parametros editables
	 * @param user Usuario del historico de cuenta dummy.
	 * @param action Accion del historico de cuenta dummy. 
	 * @param state Estado del historico de cuenta dummy.
	 * @param date Fecha(dia y hora) del historico de cuenta dummy.
	 * @param editable Atributo editable.
	 */
	public HistoryCountDummyDataImpl(String user, Action action, State state, Date date,String editable) {
		logger.trace("Enter HistoryCountDummyDataImpl - todos los parametros editables");
		
		this.user = user;
		this.action = action;
		this.state = state;
		this.date = date;
		this.editable = editable;
		
		logger.trace("Exit HistoryCountDummyDataImpl - todos los parametros editables");
	}

	
	@Override
	public Long getId() {
		logger.trace("Enter getId");
		logger.trace("Exit getId");
		return id;
	}

	@Override
	public void setId(Long id) {
		logger.trace("Enter setId");
		this.id = id;
		logger.trace("Exit setId");
	}
	
	@Override
	public String getUser() {
		logger.trace("Enter getUser");
		logger.trace("Exit getUser");
		return user;
	}

	@Override
	public void setUser(String user) {
		logger.trace("Enter setUser");
		this.user = user;
		logger.trace("Exit setUser");
	}
	
	@Override
	public Action getAction() {
		logger.trace("Enter getAction");
		logger.trace("Exit getAction");
		return action;
	}

	@Override
	public void setAction(Action action) {
		logger.trace("Enter setAction");
		this.action = action;
		logger.trace("Exit setAction");
	}
	
	
	@Override
	public State getState() {
		logger.trace("Enter getState");
		logger.trace("Exit getState");
		return state;
	}

	@Override
	public void setState(State state) {
		logger.trace("Enter setState");
		this.state = state;
		logger.trace("Exit setState");
	}

	@Override
	public Date getDate() {
		logger.trace("Enter getDate");
		logger.trace("Exit getDate");
		return date;
	}

	@Override
	public void setDate(Date date) {
		logger.trace("Enter setDate");
		this.date = date;
		logger.trace("Exit setDate");
	}
	
	@Override
	public String getEditable() {
		logger.trace("Enter getEditable");
		logger.trace("Exit getEditable");
		return editable;
	}

	@Override
	public void setEditable(String editable) {
		logger.trace("Enter setEditable");
		this.editable = editable;
		logger.trace("Exit setEditable");
	}

	@Override
	public int hashCode() {
		
		logger.trace("Enter hashCode");
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		
		logger.trace("Exit hashCode");
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		logger.trace("Enter equals");
		
		if (this == obj) {
			logger.trace("Exit equals - this == obj");
			return true;
		}
		if (obj == null) {
			logger.trace("Exit equals - obj == null");
			return false;
		}
		if (!(obj instanceof HistoryCountDummyDataImpl)) {
			logger.trace("Exit equals - !(obj instanceof HistoryCountDummyDataImpl)");
			return false;
		}
		HistoryCountDummyDataImpl other = (HistoryCountDummyDataImpl) obj;
		if (id == null) {
			if (other.id != null) {
				logger.trace("Exit equals - other.id != null");
				return false;
			}
		} else if (!id.equals(other.id)) {
			logger.trace("Exit equals - !id.equals(other.id");
			return false;
		}
		
		logger.trace("Exit equals");
		return true;
	}

	@Override
	public String toString() {
		logger.trace("Enter toString");
		logger.trace("Exit toString");
		return "HistoryCountDummyDataImpl [id=" + id + ", user=" + user + ", state=" + state + ", date=" + date
				+ ", editable=" + editable + "]";
	}
}
