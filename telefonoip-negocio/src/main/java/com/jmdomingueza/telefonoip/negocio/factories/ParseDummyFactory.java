package com.jmdomingueza.telefonoip.negocio.factories;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.dummy.beans.Call;
import com.jmdomingueza.telefonoip.dummy.beans.Count;
import com.jmdomingueza.telefonoip.dummy.factories.BeanDummyFactory;
import com.jmdomingueza.telefonoip.negocio.beans.CallDummy;
import com.jmdomingueza.telefonoip.negocio.beans.CountDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountDummy;
import com.jmdomingueza.telefonoip.persistencia.entities.CountDummyData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallDummyData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountDummyData;
import com.jmdomingueza.telefonoip.persistencia.factories.EntityFactory;

/** 
 * Clase que contiene las operaciones parsear los Objetos dummy entre las diferentes 
 * capas. persitencia-negocio, sip-negocio.
 * 
 * @author jmdomingueza
 *
 */
public class ParseDummyFactory {

	private static final Log logger = LogFactory.getLog(ParseDummyFactory.class);
	
	/**
	 * Metodo que crea un historico de cuenta de la capa de dummy a partir de un historico de cuenta dummy
	 * de la capa de negocio
	 * @param countDummy Cuenta dummy de la capa negocio
	 * @return
	 */
	public static Count parseCoDToCo(CountDummy countDummy) {
		Count count;
		
		logger.trace("Enter parseCoDToCo"); 
		
		if(countDummy!=null){
			count = BeanDummyFactory.createCount(countDummy.getUser(),
												 parseStateCoDToCo(countDummy.getState()));
		}else{
			count = null;
		}
		logger.trace("Exit parseCoDToCo");
		return count;
	}
	
	/**
	 * Metodo que crea un historico de cuenta dummy de la capa de negocio a partir de un historico de cuenta
	 * de la capa de dummy
	 * @param count Cuenta de la capa dummy
	 * @return
	 */
	public static CountDummy parseCoToCoD(Count count) {
		CountDummy countDummy;
		
		logger.trace("Enter parseCoToCoD");
		
		if(count!=null){
			countDummy = BeanFactory.createCountDummy(count.getUser(),
													  null,
													  parseStateCoToCoD(count.getState()));
		}else{
			countDummy = null;
		}
		logger.trace("Exit parseCoToCoD");
		return countDummy;
	}
	
	/**
	 * Metodo que crea un historico de cuenta dummy de la capa de persistencia a partir de un historico de cuenta
	 * dummy de la capa de negocio
	 * @param countDummy Cuenta dummy de la capa de negocio
	 * @return
	 */
	public static CountDummyData parseCoDToCoDD(CountDummy countDummy) {
		CountDummyData countDummyData;
		
		logger.trace("Enter parseCoDToCoDD");
		
		if(countDummy!=null){
			countDummyData = EntityFactory.createCountDummyData(countDummy.getUser(),
																countDummy.getEditable(),
													  			parseStateCoDToCoDD(countDummy.getState()));
		}else{
			countDummyData= null;
		}
	
		logger.trace("Exit parseCoDToCoDD");
		return countDummyData;
	}

	/**
	 * Metodo que crea un historico de cuenta dummy de la capa de negocio a partir de un historico de cuenta
	 * dummy de la capa de persistencia
	 * @param countDummyData Cuenta dummy de la capa de persistencia
	 * @return
	 */
	public static CountDummy parseCoDDToCoD(CountDummyData countDummyData) {
		CountDummy countDummy;
		
		logger.trace("Enter parseCoDDToCoD");
		
		if(countDummyData!=null){
			countDummy = BeanFactory.createCountDummy(countDummyData.getUser(),
													  countDummyData.getEditable(),
													  parseStateCoDDToCoD(countDummyData.getState()));
		}else{
			countDummy= null;
		}
	
		logger.trace("Exit parseCoDDToCoD");
		return countDummy;
	}

	

	

	/**
	 * Metodo que convierte el estado de un historico de cuenta dummy devuelta en la capa de negocio
	 * en el estado de un cuenta de la capa de dummy.
	 * @param stateDummy Estado de un historico de cuenta dummy devuelta en la capa de negocio
	 * @return
	 */
	public static Count.State parseStateCoDToCo(CountDummy.State stateDummy) {
		Count.State state;
		
		logger.trace("Enter parseStateCoDToCo");
		
		switch (stateDummy) {
		case registering:
			state = Count.State.registering;
			break;
		case unregistering:
			state = Count.State.unregistering;
			break;
		case registered:
			state = Count.State.registered;
			break;
		case unregistered:
			state = Count.State.unregistered;
			break;
		default:
			state = Count.State.unregistered;
			break;
		}
		logger.trace("Exit parseStateCoDToCo");
		return state;
	}

	/**
	 * Metodo que convierte el estado de un historico de cuenta devuelta en la capa dummy 
	 * en el estado de un historico de cuenta dummy de la capa de negocio.
	 * @param state  Estado de un historico de cuenta devuelta en la capa dummy
	 * @return
	 */
	public static CountDummy.State parseStateCoToCoD(Count.State state) {
		CountDummy.State stateDummy;
		
		logger.trace("Enter parseStateCoToCoD");
		
		switch (state) {
		case registering:
			stateDummy = CountDummy.State.registering;
			break;
		case unregistering:
			stateDummy = CountDummy.State.unregistering;
			break;
		case registered:
			stateDummy = CountDummy.State.registered;
			break;
		case unregistered:
			stateDummy = CountDummy.State.unregistered;
			break;
		default:
			stateDummy = CountDummy.State.unregistered;
			break;
		}
		logger.trace("Exit parseStateCoToCoD");
		return stateDummy;
	}

	/**
	 * Metodo que convierte el estado de un historico de cuenta dummy devuelta en la capa de negocio
	 * en el estado de un cuenta dummy de la capa de persistencia.
	 * @param stateDummy Estado de un historico de cuenta dummy devuelta en la capa de negocio
	 * @return
	 */
	public static CountDummyData.State parseStateCoDToCoDD(CountDummy.State stateDummy) {
		CountDummyData.State state;
		
		logger.trace("Enter parseStateCoDToCoDD");
		
		switch (stateDummy) {
		case registering:
			state = CountDummyData.State.registering;
			break;
		case unregistering:
			state = CountDummyData.State.unregistering;
			break;
		case registered:
			state = CountDummyData.State.registered;
			break;
		case unregistered:
			state = CountDummyData.State.unregistered;
			break;
		default:
			state = CountDummyData.State.unregistered;
			break;
		}
		logger.trace("Exit parseStateCoDToCoDD");
		return state;
	}
	/**
	 * Metodo que convierte el estado de un historico de cuenta dummy devuelta en la capa persistencia 
	 * en el estado de un historico de cuenta dummy de la capa de negocio.
	 * @param state Estado de un historico de cuenta dummy devuelta en la capa persistencia
	 * @return
	 */
	public static CountDummy.State parseStateCoDDToCoD(CountDummyData.State state) {
		CountDummy.State stateDummy;
		
		logger.trace("Enter parseStateCoDDToCoD");
		
		switch (state) {
		case registering:
			stateDummy = CountDummy.State.registering;
			break;
		case unregistering:
			stateDummy = CountDummy.State.unregistering;
			break;
		case registered:
			stateDummy = CountDummy.State.registered;
			break;
		case unregistered:
			stateDummy = CountDummy.State.unregistered;
			break;
		default:
			stateDummy = CountDummy.State.unregistered;
			break;
		}
		logger.trace("Exit parseStateCoDDToCoD");
		return stateDummy;
	}

	/**
	 * Metodo que crea una llamada de la capa de dummy a partir de una llamada dummy
	 * de la capa de negocio
	 * @param callDummy Llamada dummy de la capa negocio
	 * @return
	 */
	public static Call parseCaDToCa(CallDummy callDummy) {
		Call call;
		
		logger.trace("Enter parseCaDToCa"); 
		
		if(callDummy!=null){
			call = BeanDummyFactory.createCall(callDummy.getId(),
											   parseCoDToCo((CountDummy) callDummy.getCount()),
											   callDummy.getNumber(),
											   parseStateCaDToCa(callDummy.getState()),
											   parseDirectionCaDToCa(callDummy.getDirection()),
											   callDummy.getDescription());
		}else{
			call = null;
		}
		logger.trace("Exit parseCaDToCa");
		return call;
	}
	
	/**
	 * Metodo que crea una llamada dummy de la capa de negocio a partir de una llamada
	 * de la capa de dummy
	 * @param call Llamada de la capa dummy
	 * @return
	 */
	public static CallDummy parseCaToCaD(Call call) {
		CallDummy callDummy;
		
		logger.trace("Enter parseCaToCaD"); 
		
		if(call!=null){
			callDummy = BeanFactory.createCallDummy(call.getId(),
											   parseCoToCoD((Count) call.getCount()),
											   call.getNumber(),
											   parseStateCaToCaD(call.getState()),
											   parseDirectionCaToCaD(call.getDirection()),
											   call.getDescription());
		}else{
			callDummy = null;
		}
		logger.trace("Exit parseCaToCaD");
		return callDummy;
	}

	/**
	 * Metodo que convierte el estado de una llamada dummy devuelta en la capa de negocio
	 * en el estado de una llamada de la capa de dummy.
	 * @param stateDummy Estado de una llamada dummy devuelta en la capa de negocio
	 * @return
	 */
	public  static Call.State parseStateCaDToCa(CallDummy.State stateDummy) {
		Call.State state;
		
		logger.trace("Enter parseStateCaDToCa");
		
		switch (stateDummy) {
		case dialing:
			state = Call.State.dialing;
			break;
		case ringing:
			state = Call.State.ringing;
			break;
		case responding:
			state = Call.State.responding;
			break;
		case talking:
			state = Call.State.talking;
			break;
		case terminating:
			state = Call.State.terminating;
			break;
		case terminated:
			state = Call.State.terminated;
			break;
		case canceling:
			state = Call.State.canceling;
			break;
		case canceled:
			state = Call.State.canceled;
			break;
		case helding:
			state = Call.State.helding;
			break;
		case held:
			state = Call.State.held;
			break;
		case retrieving:
			state = Call.State.retrieving;
			break;
		case confering:
			state = Call.State.confering;
			break;
		case conference:
			state = Call.State.conference;
			break;
		case transfering:
			state = Call.State.transfering;
			break;
		case transfered:
			state = Call.State.transfered;
			break;
		case error:
			state = Call.State.error;
			break;
		case lost:
			state = Call.State.lost;
			break;
		default: 
			state = Call.State.error;
			break;
		}
		logger.trace("Exit parseStateCaDToCa");
		return state;
	}
	
	/**
	 * Metodo que convierte el estado de una llamada devuelta en la capa dummy 
	 * en el estado de una llamada dummy de la capa de negocio.
	 * @param state  Estado de una llamada devuelta en la capa dummy
	 * @return
	 */
	public  static CallDummy.State parseStateCaToCaD(Call.State state) {
		CallDummy.State stateDummy;
		
		logger.trace("Enter parseStateCaToCaD");
		
		switch (state) {
		case dialing:
			stateDummy = CallDummy.State.dialing;
			break;
		case ringing:
			stateDummy = CallDummy.State.ringing;
			break;
		case responding:
			stateDummy = CallDummy.State.responding;
			break;
		case talking:
			stateDummy = CallDummy.State.talking;
			break;
		case terminating:
			stateDummy = CallDummy.State.terminating;
			break;
		case terminated:
			stateDummy = CallDummy.State.terminated;
			break;
		case canceling:
			stateDummy = CallDummy.State.canceling;
			break;
		case canceled:
			stateDummy = CallDummy.State.canceled;
			break;
		case helding:
			stateDummy = CallDummy.State.helding;
			break;
		case held:
			stateDummy = CallDummy.State.held;
			break;
		case retrieving:
			stateDummy = CallDummy.State.retrieving;
			break;
		case confering:
			stateDummy = CallDummy.State.confering;
			break;
		case conference:
			stateDummy = CallDummy.State.conference;
			break;
		case transfering:
			stateDummy = CallDummy.State.transfering;
			break;
		case transfered:
			stateDummy = CallDummy.State.transfered;
			break;
		case error:
			stateDummy = CallDummy.State.error;
			break;
		case lost:
			stateDummy = CallDummy.State.lost;
			break;
		default: 
			stateDummy = CallDummy.State.error;
			break;
		}
		logger.trace("Exit parseStateCaToCaD");
		return stateDummy;
	}

	/**
	 * Metodo que convierte el sentido de una llamada dummy devuelta en la capa de 
	 * negocio en el sentido de una llamada de la capa de dummy.
	 * @param state  Sentido de una llamada dummy devuelta en la capa negocio
	 * @return
	 */
	public static Call.Direction parseDirectionCaDToCa(CallDummy.Direction directionDummy) {
		Call.Direction direction;
		
		logger.trace("Enter parseDirectionCaDToCa");
		
		switch (directionDummy) {
		case in:
			direction = Call.Direction.in;
			break;
		case out:
			direction = Call.Direction.out;
			break;
		default:
			direction = Call.Direction.out;
			break;
		}
		logger.trace("Exit parseDirectionCaDToCa");
		return direction;
	}

	/**
	 * Metodo que convierte el sentido de una llamada devuelta en la capa dummy 
	 * en el sentido de una llamada dummy de la capa de negocio.
	 * @param state  Sentido de una llamada devuelta en la capa dummy
	 * @return
	 */
	public static CallDummy.Direction parseDirectionCaToCaD(Call.Direction direction) {
		CallDummy.Direction directionDummy;
		
		logger.trace("Enter parseDirectionCaToCaD");
		
		switch (direction) {
		case in:
			directionDummy = CallDummy.Direction.in;
			break;
		case out:
			directionDummy = CallDummy.Direction.out;
			break;
		default:
			directionDummy = CallDummy.Direction.out;
			break;
		}
		logger.trace("Exit parseDirectionCaToCaD");
		return directionDummy;
	}
	
	/**
	 * Metodo que crea un historico de llamada dummy de la capa de persistencia a partir de un historico de llamada dummy
	 * de la capa de negocio
	 * @param historyCallDummy historico de llamada dummy de la capa negocio
	 * @return
	 */
	public static HistoryCallDummyData parseHCaDToHCaDD(HistoryCallDummy historyCallDummy) {
		HistoryCallDummyData historyCallDummyData;
		
		logger.trace("Enter parseHCaDToHCaDD"); 
		
		if(historyCallDummy!=null){
			historyCallDummyData = EntityFactory.createHistoryCallDummyData(historyCallDummy.getIdLlamada(),
											   historyCallDummy.getCount(),
											   historyCallDummy.getNumber(),
											   parseStateHCaDToHCaDD(historyCallDummy.getState()),
											   parseDirectionHCaDToHCaDD(historyCallDummy.getDirection()),
											   historyCallDummy.getDescription(),
											   historyCallDummy.getDate());
			historyCallDummyData.setId(historyCallDummy.getId());
		}else{
			historyCallDummyData = null;
		}
		logger.trace("Exit parseHCaDToHCaDD");
		return historyCallDummyData;
	}
	

	/**
	 * Metodo que crea un historico de llamada dummy de la capa de negocio a partir de un historico de llamada
	 * dummy de la capa de persistencia
	 * @param historyCall Llamada de la capa dummy
	 * @return
	 */
	public static HistoryCallDummy parseHCaDDToHCaD(HistoryCallDummyData historyCallDummyData) {
		HistoryCallDummy historyCallDummy;
		
		logger.trace("Enter parseHCaDDToHCaD"); 
		
		if(historyCallDummyData!=null){
			historyCallDummy = BeanFactory.createHistoryCallDummy(historyCallDummyData.getIdLlamada(),
											   historyCallDummyData.getCount(),
											   historyCallDummyData.getNumber(),
											   parseStateHCaDDToHCaD(historyCallDummyData.getState()),
											   parseDirectionHCaDDToHCaD(historyCallDummyData.getDirection()),
											   historyCallDummyData.getDescription(),
											   historyCallDummyData.getDate());
			historyCallDummy.setId(historyCallDummyData.getId());
		}else{
			historyCallDummy = null;
		}
		logger.trace("Exit parseHCaDDToHCaD");
		return historyCallDummy;
	}
	

	/**
	 * Metodo que convierte el estado de un historico de llamada dummy devuelta en la capa de negocio
	 * en el estado de un historico de llamada dummy de la capa de persistencia.
	 * @param stateDummy Estado de un historico de llamada dummy devuelta en la capa de negocio
	 * @return
	 */
	public  static HistoryCallDummyData.State parseStateHCaDToHCaDD(HistoryCallDummy.State stateDummy) {
		HistoryCallDummyData.State state;
		
		logger.trace("Enter parseStateHCaDToHCaDD");
		
		switch (stateDummy){
		case terminated:
			state = HistoryCallDummyData.State.terminated;
			break;
		case canceled:
			state = HistoryCallDummyData.State.canceled;
			break;
		case transfered:
			state = HistoryCallDummyData.State.transfered;
			break;
		case error:
			state = HistoryCallDummyData.State.error;
			break;
		case lost:
			state = HistoryCallDummyData.State.lost;
			break;
		default: 
			state = HistoryCallDummyData.State.error;
			break;
		}
		logger.trace("Exit parseStateHCaDToHCaDD");
		return state;
	}
	
	/**
	 * Metodo que convierte el estado de un historico de llamada dummy devuelta en la capa de persistencia 
	 * en el estado de un historico de llamada dummy de la capa de negocio.
	 * @param state  Estado de un historico de llamada devuelta en la capa dummy
	 * @return
	 */
	public  static HistoryCallDummy.State parseStateHCaDDToHCaD(HistoryCallDummyData.State state) {
		HistoryCallDummy.State stateDummy;
		
		logger.trace("Enter parseStateHCaDDToHCaD");
		
		switch (state) {
		case terminated:
			stateDummy = HistoryCallDummy.State.terminated;
			break;
		case canceled:
			stateDummy = HistoryCallDummy.State.canceled;
			break;
		case transfered:
			stateDummy = HistoryCallDummy.State.transfered;
			break;
		case error:
			stateDummy = HistoryCallDummy.State.error;
			break;
		case lost:
			stateDummy = HistoryCallDummy.State.lost;
			break;
		default: 
			stateDummy = HistoryCallDummy.State.error;
			break;
		}
		logger.trace("Exit parseStateHCaDDToHCaD");
		return stateDummy;
	}

	/**
	 * Metodo que convierte el sentido de un historico de llamada dummy devuelta en la capa de 
	 * negocio en el sentido de un historico de llamada dummmy de la capa de persistencia.
	 * @param state  Sentido de un historico de llamada dummy devuelta en la capa negocio
	 * @return
	 */
	public static HistoryCallDummyData.Direction parseDirectionHCaDToHCaDD(HistoryCallDummy.Direction directionDummy) {
		HistoryCallDummyData.Direction direction;
		
		logger.trace("Enter parseDirectionHCaDToHCaDD");
		
		switch (directionDummy) {
		case in:
			direction = HistoryCallDummyData.Direction.in;
			break;
		case out:
			direction = HistoryCallDummyData.Direction.out;
			break;
		default:
			direction = HistoryCallDummyData.Direction.out;
			break;
		}
		logger.trace("Exit parseDirectionHCaDToHCaDD");
		return direction;
	}

	/**
	 * Metodo que convierte el sentido de un historico de llamada dummy devuelta en la capa de persistencia 
	 * en el sentodo de un historico de llamada dummy de la capa de negocio.
	 * @param state  Sentido de un historico de llamada devuelta en la capa de persistencia
	 * @return
	 */
	public static HistoryCallDummy.Direction parseDirectionHCaDDToHCaD(HistoryCallDummyData.Direction direction) {
		HistoryCallDummy.Direction directionDummy;
		
		logger.trace("Enter parseDirectionHCaDDToHCaD");
		
		switch (direction) {
		case in:
			directionDummy = HistoryCallDummy.Direction.in;
			break;
		case out:
			directionDummy = HistoryCallDummy.Direction.out;
			break;
		default:
			directionDummy = HistoryCallDummy.Direction.out;
			break;
		}
		logger.trace("Exit parseDirectionHCaDDToHCaD");
		return directionDummy;
	}	
	
	/**
	 * Metodo que convierte el estado de una llamada dummy devuelta en la capa de negocio
	 * en el estado de un historico de llamada dummy de la capa de negocio.
	 * @param stateDummy Estado de una llamada dummy devuelta en la capa de negocio
	 * @return
	 */
	public  static HistoryCallDummy.State parseStateCaDToHCaD(CallDummy.State stateDummy) {
		HistoryCallDummy.State state;
		
		logger.trace("Enter parseStateCaDToHCaD");
		
		switch (stateDummy){
		case terminated:
			state = HistoryCallDummy.State.terminated;
			break;
		case canceled:
			state = HistoryCallDummy.State.canceled;
			break;
		case transfered:
			state = HistoryCallDummy.State.transfered;
			break;
		case error:
			state = HistoryCallDummy.State.error;
			break;
		case lost:
			state = HistoryCallDummy.State.lost;
			break;
		default: 
			state = HistoryCallDummy.State.error;
			break;
		}
		logger.trace("Exit parseStateCaDToHCaD");
		return state;
	}
	
	/**
	 * Metodo que convierte el sentido de una llamada dummy devuelta en la capa de 
	 * negocio en el sentido de un historico de llamada dummmy de la capa de negocio.
	 * @param state  Sentido de una llamada dummy devuelta en la capa negocio
	 * @return
	 */
	public static HistoryCallDummy.Direction parseDirectionCaDToHCaD(CallDummy.Direction directionDummy) {
		HistoryCallDummy.Direction direction;
		
		logger.trace("Enter parseDirectionCaDToHCaD");
		
		switch (directionDummy) {
		case in:
			direction = HistoryCallDummy.Direction.in;
			break;
		case out:
			direction = HistoryCallDummy.Direction.out;
			break;
		default:
			direction = HistoryCallDummy.Direction.out;
			break;
		}
		logger.trace("Exit parseDirectionCaDToHCaD");
		return direction;
	}
	
	/**
	 * Metodo que crea un historico de cuenta dummy de la capa de persistencia a partir de un historico de cuenta
	 * dummy de la capa de negocio
	 * @param countDummy Historico de cuenta dummy de la capa de negocio
	 * @return
	 */
	public static HistoryCountDummyData parseHCoDToHCoDD(HistoryCountDummy historyCountDummy) {
		HistoryCountDummyData historyCountDummyData;
		
		logger.trace("Enter parseHCoDToHCoDD");
		
		if(historyCountDummy!=null){
			historyCountDummyData = EntityFactory.createHistoryCountDummyData(historyCountDummy.getUser(),
															parseActionHCoDToHCoDD(historyCountDummy.getAction()),
															parseStateHCoDToHCoDD(historyCountDummy.getState()),
															historyCountDummy.getDate(),
															historyCountDummy.getEditable());
			historyCountDummyData.setId(historyCountDummy.getId());
		}else{
			historyCountDummyData= null;
		}
	
		logger.trace("Exit parseHCoDToHCoDD");
		return historyCountDummyData;
	}

	/**
	 * Metodo que crea un historico de cuenta dummy de la capa de negocio a partir de un historico de cuenta
	 * dummy de la capa de persistencia
	 * @param historyCountDummyData Historico de cuenta dummy de la capa de persistencia
	 * @return
	 */
	public static HistoryCountDummy parseHCoDDToHCoD(HistoryCountDummyData historyCountDummyData) {
		HistoryCountDummy historyCountDummy;
		
		logger.trace("Enter parseHCoDDToHCoD");
		
		if(historyCountDummyData!=null){
			historyCountDummy = BeanFactory.createHistoryCountDummy(historyCountDummyData.getUser(),
												parseActionHCoDDToHCoD(historyCountDummyData.getAction()),
					  							historyCountDummyData.getDate(),
					  							parseStateHCoDDToHCoD(historyCountDummyData.getState()),
					  							historyCountDummyData.getEditable());
			historyCountDummy.setId(historyCountDummyData.getId());
		}else{
			historyCountDummy= null;
		}
	
		logger.trace("Exit parseHCoDDToHCoD");
		return historyCountDummy;
	}
	
	/**
	 * Metodo que convierte la accion de un historico de cuenta dummy devuelta en la capa de negocio
	 * en la accion de un cuenta dummy de la capa de persistencia.
	 * @param actionDummy Accion de un historico de cuenta dummy devuelta en la capa de negocio
	 * @return
	 */
	public static HistoryCountDummyData.Action parseActionHCoDToHCoDD(HistoryCountDummy.Action actionDummy) {
		HistoryCountDummyData.Action action;
		
		logger.trace("Enter parseActionHCoDToHCoDD");
		
		switch (actionDummy) {
		case created:
			action = HistoryCountDummyData.Action.created;
			break;
		case modified:
			action = HistoryCountDummyData.Action.modified;
			break;
		case removed:
			action = HistoryCountDummyData.Action.removed;
			break;
		default:
			action = HistoryCountDummyData.Action.error;
			break;
		}
		logger.trace("Exit parseActionHCoDToHCoDD");
		return action;
	}
	
	/**
	 * Metodo que convierte la accion de un historico de cuenta dummy devuelta en la capa persistencia 
	 * en la accion de un historico de cuenta dummy de la capa de negocio.
	 * @param action Accion de un historico de cuenta dummy devuelta en la capa persistencia
	 * @return
	 */
	public static HistoryCountDummy.Action parseActionHCoDDToHCoD(HistoryCountDummyData.Action action) {
		HistoryCountDummy.Action actionDummy;
		
		logger.trace("Enter parseActionHCoDToHCoDD");
		
		switch (action) {
		case created:
			actionDummy = HistoryCountDummy.Action.created;
			break;
		case modified:
			actionDummy = HistoryCountDummy.Action.modified;
			break;
		case removed:
			actionDummy = HistoryCountDummy.Action.removed;
			break;
		default:
			actionDummy = HistoryCountDummy.Action.error;
			break;
		}
		logger.trace("Exit parseActionHCoDToHCoDD");
		return actionDummy;
	}
	
	/**
	 * Metodo que convierte el estado de un historico de cuenta dummy devuelta en la capa de negocio
	 * en el estado de un cuenta dummy de la capa de persistencia.
	 * @param stateDummy Estado de un historico de cuenta dummy devuelta en la capa de negocio
	 * @return
	 */
	public static HistoryCountDummyData.State parseStateHCoDToHCoDD(HistoryCountDummy.State stateDummy) {
		HistoryCountDummyData.State state;
		
		logger.trace("Enter parseStateHCoDToHCoDD");
		
		switch (stateDummy) {
		case registering:
			state = HistoryCountDummyData.State.registering;
			break;
		case unregistering:
			state = HistoryCountDummyData.State.unregistering;
			break;
		case registered:
			state = HistoryCountDummyData.State.registered;
			break;
		default:
			state = HistoryCountDummyData.State.unregistered;
			break;
		}
		logger.trace("Exit parseStateHCoDToHCoDD");
		return state;
	}
	
	/**
	 * Metodo que convierte el estado de un historico de cuenta dummy devuelta en la capa persistencia 
	 * en el estado de un historico de cuenta dummy de la capa de negocio.
	 * @param state Estado de un historico de cuenta dummy devuelta en la capa persistencia
	 * @return
	 */
	public static HistoryCountDummy.State parseStateHCoDDToHCoD(HistoryCountDummyData.State state) {
		HistoryCountDummy.State stateDummy;
		
		logger.trace("Enter parseStateHCoDDToHCoD");
		
		switch (state) {
		case registering:
			stateDummy = HistoryCountDummy.State.registering;
			break;
		case unregistering:
			stateDummy = HistoryCountDummy.State.unregistering;
			break;
		case registered:
			stateDummy = HistoryCountDummy.State.registered;
			break;
		default:
			stateDummy = HistoryCountDummy.State.unregistered;
			break;
		}
		logger.trace("Exit parseStateHCoDDToHCoD");
		return stateDummy;
	}

	/**
	 * Metodo que convierte el estado de una cuenta dummy devuelta en la capa de negocio 
	 * en el estado de un historico de cuenta dummy de la capa de negocio.
	 * @param state Estado de una cuenta dummy devuelta en la capa de negocio
	 * @return
	 */
	public static HistoryCountDummy.State parseStateCoDToHCoD(CountDummy.State state) {
		HistoryCountDummy.State stateDummy;
		
		logger.trace("Enter parseStateCoDToHCoD");
		
		switch (state) {
		case registering:
			stateDummy = HistoryCountDummy.State.registering;
			break;
		case unregistering:
			stateDummy = HistoryCountDummy.State.unregistering;
			break;
		case registered:
			stateDummy = HistoryCountDummy.State.registered;
			break;
		default:
			stateDummy = HistoryCountDummy.State.unregistered;
			break;
		}
		logger.trace("Exit parseStateCoDToHCoD");
		return stateDummy;
	}
}
