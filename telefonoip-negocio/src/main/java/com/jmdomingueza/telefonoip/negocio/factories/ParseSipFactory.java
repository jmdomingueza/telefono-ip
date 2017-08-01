package com.jmdomingueza.telefonoip.negocio.factories;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.negocio.beans.Audio;
import com.jmdomingueza.telefonoip.negocio.beans.CallSip;
import com.jmdomingueza.telefonoip.negocio.beans.CountSip;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallSip;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountSip;
import com.jmdomingueza.telefonoip.persistencia.entities.CountSipData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCallSipData;
import com.jmdomingueza.telefonoip.persistencia.entities.HistoryCountSipData;
import com.jmdomingueza.telefonoip.persistencia.factories.EntityFactory;
import com.jmdomingueza.telefonoip.rtp.factories.BeanRtpFactory;
import com.jmdomingueza.telefonoip.sip.beans.Call;
import com.jmdomingueza.telefonoip.sip.beans.Count;
import com.jmdomingueza.telefonoip.sip.factories.BeanSipFactory;

/**
 * Clase que contiene las operaciones parsear los Objetos sip entre las
 * diferentes capas. persitencia-negocio, sip-negocio.
 * 
 * @author jmdomingueza
 *
 */
public class ParseSipFactory {

	private static final Log logger = LogFactory.getLog(ParseSipFactory.class);

	/**
	 * Metodo que crea un audio de la capa de sip a partir de un audio de la
	 * capa de negocio
	 * 
	 * @param countSip
	 *            Audio de la capa negocio
	 * @return
	 */
	public static com.jmdomingueza.telefonoip.rtp.beans.Audio parseAToAS(Audio audio) {
		com.jmdomingueza.telefonoip.rtp.beans.Audio audioRTP;

		logger.trace("Enter parseAToAS");

		if (audio != null) {
			audioRTP = BeanRtpFactory.createAudio(audio.getNameInfo(), audio.getType());
		} else {
			audioRTP = null;
		}
		logger.trace("Exit parseAToAS");
		return audioRTP;
	}

	/**
	 * Metodo que crea un audio de la capa de negocio a partir de un audio de la
	 * capa de sip
	 * 
	 * @param countSip
	 *            Audio de la capa negocio
	 * @return
	 */
	public static Audio parseASToA(com.jmdomingueza.telefonoip.rtp.beans.Audio audioRTP) {
		Audio audio;

		logger.trace("Enter parseASToA");

		if (audioRTP != null) {
			audio = BeanFactory.createAudio(audioRTP.getNameInfo(), audioRTP.getType());
		} else {
			audio = null;
		}
		logger.trace("Exit parseASToA");
		return audio;
	}

	/**
	 * Metodo que crea una cuenta de la capa de sip a partir de una cuenta sip
	 * de la capa de negocio
	 * 
	 * @param countSip
	 *            Cuenta sip de la capa negocio
	 * @return
	 */
	public static Count parseCoSToCo(CountSip countSip) {
		Count count;

		logger.trace("Enter parseCoSToCo");

		if (countSip != null) {
			count = BeanSipFactory.createCount(countSip.getUser(), countSip.getHostServer(), countSip.getPassword(),
					parseStateCoSToCo(countSip.getState()), countSip.getDisplay(), countSip.getAudioAvalibleList());
		} else {
			count = null;
		}
		logger.trace("Exit parseCoSToCo");
		return count;
	}

	/**
	 * Metodo que crea una cuenta sip de la capa de negocio a partir de una
	 * cuenta de la capa de sip
	 * 
	 * @param count
	 *            Cuenta de la capa sip
	 * @return
	 */
	public static CountSip parseCoToCoS(Count count) {
		CountSip countSip;

		logger.trace("Enter parseCoToCoS");
		if (count != null) {
			countSip = BeanFactory.createCountSip(count.getUser(), count.getHostServer(), count.getPassword(),
					parseStateCoToCoS(count.getState()), count.getDisplay(), count.getAudioAvalibleList());
		} else {
			countSip = null;
		}
		logger.trace("Exit parseCoToCoS");
		return countSip;
	}

	/**
	 * Metodo que crea una cuenta sip de la capa de persistencia a partir de una
	 * cuenta sip de la capa de negocio
	 * 
	 * @param countSip
	 *            Cuenta sip de la capa negocio
	 * @return
	 */
	public static CountSipData parseCoSToCoSD(CountSip countSip) {
		CountSipData countSipData;

		logger.trace("Enter parseCoSToCoSD");

		if (countSip != null) {
			countSipData = EntityFactory.createCountSipData(countSip.getUser(), countSip.getHostServer(),
					countSip.getPassword(), parseStateCoSToCoSD(countSip.getState()));
		} else {
			countSipData = null;
		}
		logger.trace("Exit parseCoSToCoSD");
		return countSipData;
	}

	/**
	 * Metodo que crea una cuenta sip de la capa de negocio a partir de una
	 * cuenta sip de la capa de persistencia
	 * 
	 * @param count
	 *            Cuenta de la capa sip
	 * @return
	 */
	public static CountSip parseCoSDToCoS(CountSipData countSipData) {
		CountSip countSip;

		logger.trace("Enter parseCoSDToCoS");

		if (countSipData != null) {
			countSip = BeanFactory.createCountSip(countSipData.getUser(), countSipData.getHostServer(),
					countSipData.getPassword(), parseStateCoSDToCoS(countSipData.getState()), null, null);
		} else {
			countSip = null;
		}
		logger.trace("Exit parseCoSDToCoS");
		return countSip;
	}

	/**
	 * Metodo que convierte el estado de una cuenta sip devuelta en la capa de
	 * negocio en el estado de un cuenta de la capa de sip.
	 * 
	 * @param stateSip
	 *            Estado de una cuenta sip devuelta en la capa de negocio
	 * @return
	 */
	public static Count.State parseStateCoSToCo(CountSip.State stateSip) {
		Count.State state;
		logger.trace("Enter parseStateCoSToCo");

		switch (stateSip) {
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
		case unauthorized:
			state = Count.State.unauthorized;
			break;
		default:
			state = Count.State.error;
			break;
		}
		logger.trace("Exit parseStateCoSToCo");
		return state;
	}

	/**
	 * Metodo que convierte el estado de una cuenta devuelta en la capa sip en
	 * el estado de una cuenta sip de la capa de negocio.
	 * 
	 * @param state
	 *            Estado de una cuenta devuelta en la capa sip
	 * @return
	 */
	public static CountSip.State parseStateCoToCoS(Count.State state) {
		CountSip.State stateSip;
		logger.trace("Enter parseStateCoToCoS");

		switch (state) {
		case registering:
			stateSip = CountSip.State.registering;
			break;
		case unregistering:
			stateSip = CountSip.State.unregistering;
			break;
		case registered:
			stateSip = CountSip.State.registered;
			break;
		case unregistered:
			stateSip = CountSip.State.unregistered;
			break;
		case unauthorized:
			stateSip = CountSip.State.unauthorized;
			break;
		default:
			stateSip = CountSip.State.error;
			break;
		}
		logger.trace("Exit parseStateCoToCoS");
		return stateSip;
	}

	/**
	 * Metodo que convierte el estado de una cuenta sip devuelta en la capa de
	 * negocio en el estado de un cuenta sip de la capa de persistencia.
	 * 
	 * @param stateSip
	 *            Estado de una cuenta sip devuelta en la capa de negocio
	 * @return
	 */
	public static CountSipData.State parseStateCoSToCoSD(CountSip.State stateSip) {
		CountSipData.State state;
		logger.trace("Enter parseStateCoSToCoSD");

		switch (stateSip) {
		case registering:
			state = CountSipData.State.registering;
			break;
		case unregistering:
			state = CountSipData.State.unregistering;
			break;
		case registered:
			state = CountSipData.State.registered;
			break;
		case unregistered:
			state = CountSipData.State.unregistered;
			break;
		case unauthorized:
			state = CountSipData.State.unauthorized;
			break;
		default:
			state = CountSipData.State.error;
			break;
		}
		logger.trace("Exit parseStateCoSToCoSD");
		return state;
	}

	/**
	 * Metodo que convierte el estado de una cuenta sip devuelta en la capa de
	 * persistencia en el estado de una cuenta sip de la capa de negocio.
	 * 
	 * @param state
	 *            Estado de una cuenta sip devuelta en la capa de persistencia
	 * @return
	 */
	public static CountSip.State parseStateCoSDToCoS(CountSipData.State state) {
		CountSip.State stateSip;
		logger.trace("Enter parseStateCoSDToCoS");

		switch (state) {
		case registering:
			stateSip = CountSip.State.registering;
			break;
		case unregistering:
			stateSip = CountSip.State.unregistering;
			break;
		case registered:
			stateSip = CountSip.State.registered;
			break;
		case unregistered:
			stateSip = CountSip.State.unregistered;
			break;
		case unauthorized:
			stateSip = CountSip.State.unregistered;
			break;
		default:
			stateSip = CountSip.State.error;
			break;
		}
		logger.trace("Exit parseStateCoSDToCoS");
		return stateSip;
	}

	/**
	 * Metodo que crea una llamada de la capa de sip a partir de una llamada sip
	 * de la capa de negocio
	 * 
	 * @param callSip
	 *            Llamada de la capa negocio
	 * @return
	 */
	public static Call parseCaSToCa(CallSip callSip) {
		Call call;

		logger.trace("Enter parseCaSToCa");
		if (callSip != null) {
			call = BeanSipFactory.createCall(parseCoSToCo((CountSip) callSip.getCount()), callSip.getNumber(),
					callSip.getTagFrom(), callSip.getTagTo(), callSip.getBranchVia(), callSip.getContentType(),
					callSip.getContentSubType(), callSip.getSessionDescriptionMode(),
					parseStateCaSToCa(callSip.getState()));
		} else {
			call = null;
		}

		logger.trace("Exit parseCaSToCa");
		return call;
	}

	/**
	 * Metodo que crea una llamada sip de la capa de negocio a partir de una
	 * llamada de la capa de sip
	 * 
	 * @param count
	 *            Llamada de la capa sip
	 * @return
	 */
	public static CallSip parseCaToCaS(Call call) {
		CallSip callSip;

		logger.trace("Enter parseCaToCaS");
		if (call != null) {
			// TODO: hay que hacer bien esto
			callSip = BeanFactory.createCallSip(0, parseCoToCoS(call.getCount()), call.getNumber(),
					parseStateCaToCaS(call.getState()), parseDirectionCaToCaS(call.getDirection()), "",
					call.getTagFrom(), call.getTagTo(), call.getBranchVia(), call.getContentType(),
					call.getContentSubType(), call.getSessionDescriptionMode());
		} else {
			callSip = null;
		}
		logger.trace("Exit parseCaToCaS");
		return callSip;
	}

	/**
	 * Metodo que convierte el estado de una llamada sip devuelta en la capa de
	 * negocio en el estado de una llamada de la capa de sip.
	 * 
	 * @param stateSip
	 *            Estado de una llamada sip devuelta en la capa de negocio
	 * @return
	 */
	public static Call.State parseStateCaSToCa(CallSip.State stateSip) {
		Call.State state;
		logger.trace("Enter parseStateCaSToCa");

		switch (stateSip) {
		case ringing:
			state = Call.State.ringing;
			break;
		case talking:
			state = Call.State.talking;
			break;
		case terminated:
			state = Call.State.terminated;
			break;
		default:
			state = Call.State.error;
			break;
		}
		logger.trace("Exit parseStateCaSToCa");
		return state;
	}

	/**
	 * Metodo que convierte el estado de una llamada devuelta en la capa sip en
	 * el estado de una llamada sip de la capa de negocio.
	 * 
	 * @param state
	 *            Estado de una llamada devuelta en la capa sip
	 * @return
	 */
	public static CallSip.State parseStateCaToCaS(Call.State state) {
		CallSip.State stateSip;
		logger.trace("Enter parseStateCaToCaS");

		switch (state) {
		case ringing:
			stateSip = CallSip.State.ringing;
			break;
		case talking:
			stateSip = CallSip.State.talking;
			break;
		case terminated:
			stateSip = CallSip.State.terminated;
			break;
		default:
			stateSip = CallSip.State.error;
			break;
		}
		logger.trace("Exit parseStateCaToCaS");
		return stateSip;
	}

	/**
	 * Metodo que convierte el sentido de una llamada sip devuelta en la capa de
	 * negocio en el sentido de una llamada dummmy de la capa de persistencia.
	 * 
	 * @param state
	 *            Sentido de una llamada sip devuelta en la capa negocio
	 * @return
	 */
	public static Call.Direction parseDirectionCaToCaS(CallSip.Direction directionSip) {
		Call.Direction direction;

		logger.trace("Enter parseDirectionCaToCaS");

		switch (directionSip) {
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
		logger.trace("Exit parseDirectionCaToCaS");
		return direction;
	}

	/**
	 * Metodo que convierte el sentido de una llamada sip devuelta en la capa de
	 * persistencia en el sentodo de una llamada sip de la capa de negocio.
	 * 
	 * @param state
	 *            Sentido de una llamada devuelta en la capa de persistencia
	 * @return
	 */
	public static CallSip.Direction parseDirectionCaToCaS(Call.Direction direction) {
		CallSip.Direction directionSip;

		logger.trace("Enter parseDirectionCaToCaS");

		switch (direction) {
		case in:
			directionSip = CallSip.Direction.in;
			break;
		case out:
			directionSip = CallSip.Direction.out;
			break;
		default:
			directionSip = CallSip.Direction.out;
			break;
		}
		logger.trace("Exit parseDirectionCaToCaS");
		return directionSip;
	}

	/**
	 * Metodo que crea un historico de llamada sip de la capa de persistencia a
	 * partir de un historico de llamada sip de la capa de negocio
	 * 
	 * @param historyCallSip
	 *            historico de llamada sip de la capa negocio
	 * @return
	 */
	public static HistoryCallSipData parseHCaSToHCaSD(HistoryCallSip historyCallSip) {
		HistoryCallSipData historyCallSipData;

		logger.trace("Enter parseHCaSToHCaSD");

		if (historyCallSip != null) {
			historyCallSipData = EntityFactory.createHistoryCallSipData(historyCallSip.getIdLlamada(),
					historyCallSip.getCount(), historyCallSip.getNumber(),
					parseStateHCaSToHCaSD(historyCallSip.getState()),
					parseDirectionHCaSToHCaSD(historyCallSip.getDirection()), historyCallSip.getDescription(),
					historyCallSip.getDate());
			historyCallSipData.setId(historyCallSip.getId());
		} else {
			historyCallSipData = null;
		}
		logger.trace("Exit parseHCaSToHCaSD");
		return historyCallSipData;
	}

	/**
	 * Metodo que crea un historico de llamada sip de la capa de negocio a
	 * partir de un historico de llamada sip de la capa de persistencia
	 * 
	 * @param historyCall
	 *            Llamada de la capa sip
	 * @return
	 */
	public static HistoryCallSip parseHCaSDToHCaS(HistoryCallSipData historyCallSipData) {
		HistoryCallSip historyCallSip;

		logger.trace("Enter parseHCaSDToHCaS");

		if (historyCallSipData != null) {
			historyCallSip = BeanFactory.createHistoryCallSip(historyCallSipData.getIdLlamada(),
					historyCallSipData.getCount(), historyCallSipData.getNumber(),
					parseStateHCaSDToHCaS(historyCallSipData.getState()),
					parseDirectionHCaSDToHCaS(historyCallSipData.getDirection()), historyCallSipData.getDescription(),
					historyCallSipData.getDate());
			historyCallSip.setId(historyCallSipData.getId());
		} else {
			historyCallSip = null;
		}
		logger.trace("Exit parseHCaSDToHCaS");
		return historyCallSip;
	}

	/**
	 * Metodo que convierte el estado de un historico de llamada sip devuelta en
	 * la capa de negocio en el estado de un historico de llamada sip de la capa
	 * de persistencia.
	 * 
	 * @param stateSip
	 *            Estado de un historico de llamada sip devuelta en la capa de
	 *            negocio
	 * @return
	 */
	public static HistoryCallSipData.State parseStateHCaSToHCaSD(HistoryCallSip.State stateSip) {
		HistoryCallSipData.State state;

		logger.trace("Enter parseStateHCaSToHCaSD");

		switch (stateSip) {
		case terminated:
			state = HistoryCallSipData.State.terminated;
			break;
		case canceled:
			state = HistoryCallSipData.State.canceled;
			break;
		case transfered:
			state = HistoryCallSipData.State.transfered;
			break;
		case error:
			state = HistoryCallSipData.State.error;
			break;
		case lost:
			state = HistoryCallSipData.State.lost;
			break;
		default:
			state = HistoryCallSipData.State.error;
			break;
		}
		logger.trace("Exit parseStateHCaSToHCaSD");
		return state;
	}

	/**
	 * Metodo que convierte el estado de un historico de llamada sip devuelta en
	 * la capa de persistencia en el estado de un historico de llamada sip de la
	 * capa de negocio.
	 * 
	 * @param state
	 *            Estado de un historico de llamada devuelta en la capa sip
	 * @return
	 */
	public static HistoryCallSip.State parseStateHCaSDToHCaS(HistoryCallSipData.State state) {
		HistoryCallSip.State stateSip;

		logger.trace("Enter parseStateHCaSDToHCaS");

		switch (state) {
		case terminated:
			stateSip = HistoryCallSip.State.terminated;
			break;
		case canceled:
			stateSip = HistoryCallSip.State.canceled;
			break;
		case transfered:
			stateSip = HistoryCallSip.State.transfered;
			break;
		case error:
			stateSip = HistoryCallSip.State.error;
			break;
		case lost:
			stateSip = HistoryCallSip.State.lost;
			break;
		default:
			stateSip = HistoryCallSip.State.error;
			break;
		}
		logger.trace("Exit parseStateHCaSDToHCaS");
		return stateSip;
	}

	/**
	 * Metodo que convierte el sentido de un historico de llamada sip devuelta
	 * en la capa de negocio en el sentido de un historico de llamada dummmy de
	 * la capa de persistencia.
	 * 
	 * @param state
	 *            Sentido de un historico de llamada sip devuelta en la capa
	 *            negocio
	 * @return
	 */
	public static HistoryCallSipData.Direction parseDirectionHCaSToHCaSD(HistoryCallSip.Direction directionSip) {
		HistoryCallSipData.Direction direction;

		logger.trace("Enter parseDirectionHCaSToHCaSD");

		switch (directionSip) {
		case in:
			direction = HistoryCallSipData.Direction.in;
			break;
		case out:
			direction = HistoryCallSipData.Direction.out;
			break;
		default:
			direction = HistoryCallSipData.Direction.out;
			break;
		}
		logger.trace("Exit parseDirectionHCaSToHCaSD");
		return direction;
	}

	/**
	 * Metodo que convierte el sentido de un historico de llamada sip devuelta
	 * en la capa de persistencia en el sentodo de un historico de llamada sip
	 * de la capa de negocio.
	 * 
	 * @param state
	 *            Sentido de un historico de llamada devuelta en la capa de
	 *            persistencia
	 * @return
	 */
	public static HistoryCallSip.Direction parseDirectionHCaSDToHCaS(HistoryCallSipData.Direction direction) {
		HistoryCallSip.Direction directionSip;

		logger.trace("Enter parseDirectionHCaSDToHCaS");

		switch (direction) {
		case in:
			directionSip = HistoryCallSip.Direction.in;
			break;
		case out:
			directionSip = HistoryCallSip.Direction.out;
			break;
		default:
			directionSip = HistoryCallSip.Direction.out;
			break;
		}
		logger.trace("Exit parseDirectionHCaSDToHCaS");
		return directionSip;
	}

	/**
	 * Metodo que convierte el estado de una llamada sip devuelta en la capa de
	 * negocio en el estado de un historico de llamada sip de la capa de
	 * negocio.
	 * 
	 * @param stateSip
	 *            Estado de una llamada sip devuelta en la capa de negocio
	 * @return
	 */
	public static HistoryCallSip.State parseStateCaSToHCaS(CallSip.State stateSip) {
		HistoryCallSip.State state;

		logger.trace("Enter parseStateCaSToHCaS");

		switch (stateSip) {
		case terminated:
			state = HistoryCallSip.State.terminated;
			break;
		case canceled:
			state = HistoryCallSip.State.canceled;
			break;
		case transfered:
			state = HistoryCallSip.State.transfered;
			break;
		case error:
			state = HistoryCallSip.State.error;
			break;
		case lost:
			state = HistoryCallSip.State.lost;
			break;
		default:
			state = HistoryCallSip.State.error;
			break;
		}
		logger.trace("Exit parseStateCaSToHCaS");
		return state;
	}

	/**
	 * Metodo que convierte el sentido de una llamada sip devuelta en la capa de
	 * negocio en el sentido de un historico de llamada dummmy de la capa de
	 * negocio.
	 * 
	 * @param state
	 *            Sentido de una llamada sip devuelta en la capa negocio
	 * @return
	 */
	public static HistoryCallSip.Direction parseDirectionCaSToHCaS(CallSip.Direction directionSip) {
		HistoryCallSip.Direction direction;

		logger.trace("Enter parseDirectionCaSToHCaS");

		switch (directionSip) {
		case in:
			direction = HistoryCallSip.Direction.in;
			break;
		case out:
			direction = HistoryCallSip.Direction.out;
			break;
		default:
			direction = HistoryCallSip.Direction.out;
			break;
		}
		logger.trace("Exit parseDirectionCaSToHCaS");
		return direction;
	}

	/**
	 * Metodo que crea un historico de cuenta sip de la capa de persistencia a
	 * partir de un historico de cuenta sip de la capa de negocio
	 * 
	 * @param historyCountSip
	 *            Historico de cuenta sip de la capa negocio
	 * @return
	 */
	public static HistoryCountSipData parseHCoSToHCoSD(HistoryCountSip historyCountSip) {
		HistoryCountSipData historyCountSipData;

		logger.trace("Enter parseHCoSToHCoSD");

		if (historyCountSip != null) {
			historyCountSipData = EntityFactory.createHistoryCountSipData(historyCountSip.getUser(),
				historyCountSip.getHostServer(), parseActionHCoSToHCoSD(historyCountSip.getAction()),
				parseStateHCoSToHCoSD(historyCountSip.getState()), historyCountSip.getDate(),
				historyCountSip.getPassword());
		}else{
			historyCountSipData = null;
		}

		logger.trace("Exit parseHCoSToHCoSD");
		return historyCountSipData;
	}

	/**
	 * Metodo que crea un historico de cuenta sip de la capa de negocio a partir
	 * de un historico de cuenta sip de la capa de persistencia
	 * 
	 * @param historyCount
	 *            Historico de cuenta de la capa sip
	 * @return
	 */
	public static HistoryCountSip parseHCoSDToHCoS(HistoryCountSipData historyCountSipData) {
		HistoryCountSip historyCountSip;

		logger.trace("Enter parseHCoSDToHCoS");

		if (historyCountSipData != null) {
			historyCountSip = BeanFactory.createHistoryCountSip(historyCountSipData.getUser(),
				parseActionHCoSDToHCoS(historyCountSipData.getAction()), historyCountSipData.getDate(),
				historyCountSipData.getHostServer(), parseStateHCoSDToHCoS(historyCountSipData.getState()),
				historyCountSipData.getPassword());
		}else{
			historyCountSip = null;
		}
		
		logger.trace("Exit parseHCoSDToHCoS");
		return historyCountSip;
	}

	/**
	 * Metodo que convierte la accion de un historico de cuenta sip devuelta en
	 * la capa de negocio en la accion de un cuenta sip de la capa de
	 * persistencia.
	 * 
	 * @param actionSip
	 *            Accion de un historico de cuenta sip devuelta en la capa de
	 *            negocio
	 * @return
	 */
	public static HistoryCountSipData.Action parseActionHCoSToHCoSD(HistoryCountSip.Action actionSip) {
		HistoryCountSipData.Action action;

		logger.trace("Enter parseActionHCoSDToHCoS");

		switch (actionSip) {
		case created:
			action = HistoryCountSipData.Action.created;
			break;
		case modified:
			action = HistoryCountSipData.Action.modified;
			break;
		case removed:
			action = HistoryCountSipData.Action.removed;
			break;
		default:
			action = HistoryCountSipData.Action.error;
			break;
		}
		logger.trace("Exit parseActionHCoSDToHCoS");
		return action;
	}

	/**
	 * Metodo que convierte la accion de un historico de cuenta sip devuelta en
	 * la capa persistencia en la accion de un historico de cuenta sip de la
	 * capa de negocio.
	 * 
	 * @param action
	 *            Accion de un historico de cuenta sip devuelta en la capa
	 *            persistencia
	 * @return
	 */
	public static HistoryCountSip.Action parseActionHCoSDToHCoS(HistoryCountSipData.Action action) {
		HistoryCountSip.Action actionSip;

		logger.trace("Enter parseActionHCoSToHCoSD");

		switch (action) {
		case created:
			actionSip = HistoryCountSip.Action.created;
			break;
		case modified:
			actionSip = HistoryCountSip.Action.modified;
			break;
		case removed:
			actionSip = HistoryCountSip.Action.removed;
			break;
		default:
			actionSip = HistoryCountSip.Action.error;
			break;
		}
		logger.trace("Exit parseActionHCoSToHCoSD");
		return actionSip;
	}

	/**
	 * Metodo que convierte el estado de un historico de cuenta sip devuelta en
	 * la capa de negocio en el estado de un cuenta sip de la capa de
	 * persistencia.
	 * 
	 * @param stateSip
	 *            Estado de un historico de cuenta sip devuelta en la capa de
	 *            negocio
	 * @return
	 */
	public static HistoryCountSipData.State parseStateHCoSToHCoSD(HistoryCountSip.State stateSip) {
		HistoryCountSipData.State state;
		logger.trace("Enter parseStateHCoSToHCoSD");

		switch (stateSip) {
		case registering:
			state = HistoryCountSipData.State.registering;
			break;
		case unregistering:
			state = HistoryCountSipData.State.unregistering;
			break;
		case registered:
			state = HistoryCountSipData.State.registered;
			break;
		case unregistered:
			state = HistoryCountSipData.State.unregistered;
			break;
		case unauthorized:
			state = HistoryCountSipData.State.unauthorized;
			break;
		default:
			state = HistoryCountSipData.State.error;
			break;
		}
		logger.trace("Exit parseStateHCoSToHCoSD");
		return state;
	}

	/**
	 * Metodo que convierte el estado de un historico de cuenta sip devuelta en
	 * la capa de persistencia en el estado de un historico de cuenta sip de la
	 * capa de negocio.
	 * 
	 * @param state
	 *            Estado de un historico de cuenta sip devuelta en la capa de
	 *            persistencia
	 * @return
	 */
	public static HistoryCountSip.State parseStateHCoSDToHCoS(HistoryCountSipData.State state) {
		HistoryCountSip.State stateSip;
		logger.trace("Enter parseStateHCoSDToHCoS");

		switch (state) {
		case registering:
			stateSip = HistoryCountSip.State.registering;
			break;
		case unregistering:
			stateSip = HistoryCountSip.State.unregistering;
			break;
		case registered:
			stateSip = HistoryCountSip.State.registered;
			break;
		case unregistered:
			stateSip = HistoryCountSip.State.unregistered;
			break;
		case unauthorized:
			stateSip = HistoryCountSip.State.unauthorized;
			break;
		default:
			stateSip = HistoryCountSip.State.error;
			break;
		}
		logger.trace("Exit parseStateHCoSDToHCoS");
		return stateSip;
	}

	/**
	 * Metodo que convierte el estado de una cuenta sip devuelta en
	 * la capa de negocio en el estado de un historico de cuenta sip de la
	 * capa de negocio.
	 * 
	 * @param state
	 *            Estado de un historico de cuenta sip devuelta en la capa de
	 *            persistencia
	 * @return
	 */
	public static HistoryCountSip.State parseStateCoSToHCoS(CountSip.State state) {
		HistoryCountSip.State stateSip;
		logger.trace("Enter parseStateCoSToHCoS");

		switch (state) {
		case registering:
			stateSip = HistoryCountSip.State.registering;
			break;
		case unregistering:
			stateSip = HistoryCountSip.State.unregistering;
			break;
		case registered:
			stateSip = HistoryCountSip.State.registered;
			break;
		case unregistered:
			stateSip = HistoryCountSip.State.unregistered;
			break;
		case unauthorized:
			stateSip = HistoryCountSip.State.unauthorized;
			break;
		default:
			stateSip = HistoryCountSip.State.error;
			break;
		}
		logger.trace("Exit parseStateCoSToHCoS");
		return stateSip;
	}
}
