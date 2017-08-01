package com.jmdomingueza.telefonoip.rtp.beans;

import org.apache.log4j.Logger;

/**
 * Clase que implementa los atributos y operaciones del bean Audio.
 * @author jmdomingueza
 *
 */
public class AudioImpl implements Audio {

	private static final long serialVersionUID = 1538510903293348497L;
	
	private final static Logger logger = Logger.getLogger(AudioImpl.class);
	
	/**
	 * Nombre de la tarjeta de sonido devuelto por AudioSystem.getMixerInfo
	 */
	private String nameInfo;
	
	/**
	 * Tipo de tarjeta de sonido (Microfono o Altavoz)
	 */
	private String type;

	@Override
	public String getNameInfo() {
		logger.trace("Enter getNameInfo");
		logger.trace("Exit getNameInfo");
		return nameInfo;
	}

	@Override
	public void setNameInfo(String nameInfo) {
		logger.trace("Enter setNameInfo");
		this.nameInfo = nameInfo;
		logger.trace("Exit setNameInfo");
	}

	@Override
	public String getType() {
		logger.trace("Enter getType");
		logger.trace("Exit getType");
		return type;
	}

	@Override
	public void setType(String type) {
		logger.trace("Enter setType");
		this.type = type;
		logger.trace("Exit setType");
	}

	@Override
	public int hashCode() {
		
		logger.trace("Enter hashCode");
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nameInfo == null) ? 0 : nameInfo.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		
		logger.trace("Exit hashCode");
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		logger.trace("Enter equals");
		
		if (this == obj){
			logger.trace("Exit equals - this == obj");
			return true;
		}
			
		if (obj == null){
			logger.trace("Exit equals - obj == null");
			return false;
		}
		if (getClass() != obj.getClass()){
			logger.trace("Exit equals - getClass() != obj.getClass()");
			return false;
		}
		AudioImpl other = (AudioImpl) obj;
		if (nameInfo == null) {
			if (other.nameInfo != null){
				logger.trace("Exit equals - other.nameInfo != null");
				return false;
			}
		} else if (!nameInfo.equals(other.nameInfo)){
			logger.trace("Exit equals - !nameInfo.equals(other.nameInfo)");
			return false;
		}if (type == null) {
			if (other.type != null){
				logger.trace("Exit equals - other.type != null");
				return false;
			}
		} else if (!type.equals(other.type)){
			logger.trace("Exit equals - !type.equals(other.type)");
			return false;
		}
		
		logger.trace("Exit equalS");
		return true;
	}

	@Override
	public String toString() {
		logger.trace("Enter toString");
		logger.trace("Exit toString");
		return "AudioBean [nameInfo=" + nameInfo + ", type=" + type + "]";
	}

}
