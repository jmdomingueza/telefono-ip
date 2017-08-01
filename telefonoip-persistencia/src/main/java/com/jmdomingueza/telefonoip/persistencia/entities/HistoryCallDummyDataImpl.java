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
 * HistoryCallDummyData que corresponde con la tabla HISTORICO_LLAMADA_DUMMY.
 * 
 * @author jmdomingueza
 *
 */
@Entity
@Table(name="HISTORICO_LLAMADA_DUMMY")
public class HistoryCallDummyDataImpl  implements HistoryCallDummyData{

	private static final long serialVersionUID = 5035642697282814431L;

	private final static Logger logger = Logger.getLogger(HistoryCallDummyDataImpl.class);
	
	/**
	 * Identifidor autogenerado del historico de llamada dummy, clave priamria.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	/**
	 * Identifidor de la llamada dummy que corresponde con el historico de llamada dummy.
	 */
	@Column(name = "IDLLAMADA")
	private int idLlamada;
	
	/**
	 * Cuenta de la llamada dummy que corresponde con el historico de llamada dummy.
	 */
	@Column(name = "CUENTA")
	private String count;
	
	/**
	 * Numero de destino o origen de la llamada dummy que corresponde
	 * con el historico de llamada dummy.
	 */
	@Column(name = "NUMERO")
	private String number;
	
	/**
	 * Estado del historico de llamada dummy
	 */
	@Column(name = "ESTADO")
	private State state;
	
	/**
	 * Sentido de la llamada dummy que corresponde con el historico de llamada dummy.
	 */
	@Column(name = "SENTIDO")
	private Direction direction;
	
	/**
	 * Descripcion de la llamada dummy que corresponde con el historico de llamada dummy.
	 */
	@Column(name = "DESCRIPCION")
	private String description;
	
	/**
	 * Fecha(dia y hora) del historico de llamada dummy 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA")
	private  Date date;
	
	/**
	 * Constructor sin parametros
	 */
	public HistoryCallDummyDataImpl() {
		logger.trace("Enter HistoryCallDummyDataImpl - sin parametros");
		logger.trace("Exit HistoryCallDummyDataImpl - sin parametros");
	}
	
	/**
	 * Construtor con todos los parametros editables
	 * @param idLlamada Identifidor de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param count Cuenta de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param number Numero de destino o origen de la llamada dummy que corresponde
	 * 				 con el historico de llamada dummy.
	 * @param state Estado del historico de llamada dummy
	 * @param direction Sentido de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param description  Descripcion de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param date Fecha(dia y hora) del historico de llamada dummy.
	 */
	public HistoryCallDummyDataImpl(int idLlamada, String count, String number, State state, Direction direction,
			String description, Date date) {
		
		logger.trace("Enter HistoryCallDummyDataImpl - todos los parametros editables");
		
		this.idLlamada = idLlamada;
		this.count = count;
		this.number = number;
		this.state = state;
		this.direction = direction;
		this.description = description;
		this.date = date;
		
		logger.trace("Exit HistoryCallDummyDataImpl - todos los parametros editables");
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
	public int getIdLlamada() {
		logger.trace("Enter getIdLlamada");
		logger.trace("Exit getIdLlamada");
		return idLlamada;
	}

	@Override
	public void setIdLlamada(int idLlamada) {
		logger.trace("Enter setIdLlamada");
		this.idLlamada = idLlamada;
		logger.trace("Exit setIdLlamada");
	}
	
	@Override
	public String getCount() {
		logger.trace("Enter getCount");
		logger.trace("Exit getCount");
		return count;
	}

	@Override
	public void setCount(String count) {
		logger.trace("Enter setCount");
		this.count = count;
		logger.trace("Exit setCount");
	}

	@Override
	public String getNumber() {
		logger.trace("Enter getNumber");
		logger.trace("Exit getNumber");
		return number;
	}

	@Override
	public void setNumber(String number) {
		logger.trace("Enter setNumber");
		this.number = number;
		logger.trace("Exit setNumber");
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
	public Direction getDirection() {
		logger.trace("Enter getDirection");
		logger.trace("Exit getDirection");
		return direction;
	}

	@Override
	public void setDirection(Direction direction) {
		logger.trace("Enter setDirection");
		this.direction = direction;
		logger.trace("Exit setDirection");
	}
	
	@Override
	public String getDescription(){
		logger.trace("Enter getDescription");
		logger.trace("Exit getDescription");
		return this.description;
	}
	
	@Override
	public void setDescription(String description){
		logger.trace("Enter setDescription");
		this.description = description;
		logger.trace("Exit setDescription");
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
		if (!(obj instanceof HistoryCallDummyDataImpl)) {
			logger.trace("Exit equals - !(obj instanceof HistoryCallDummyDataImpl) ");
			return false;
		}
		HistoryCallDummyDataImpl other = (HistoryCallDummyDataImpl) obj;
		if (id == null) {
			if (other.id != null) {
				logger.trace("Exit equals - other.id != null");
				return false;
			}
		} else if (!id.equals(other.id)) {
			logger.trace("Exit equals - !id.equals(other.id)");
			return false;
		}
		
		logger.trace("Exit equals");
		return true;
	}

	@Override
	public String toString() {
		logger.trace("Enter toString");
		logger.trace("Exit toString");
		return "HistoryCallDummyDataImpl [id=" + id + ", idLlamada=" + idLlamada + ", count=" + count + ", number="
				+ number + ", state=" + state + ", direction=" + direction + ", description=" + description + ", date="
				+ date + "]";
	}
}
