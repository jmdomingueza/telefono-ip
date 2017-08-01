package com.jmdomingueza.telefonoip.negocio.factories;

import java.net.Inet4Address;
import java.util.Date;
import java.util.List;

import javax.sip.ListeningPoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmdomingueza.telefonoip.negocio.beans.Audio;
import com.jmdomingueza.telefonoip.negocio.beans.AudioImpl;
import com.jmdomingueza.telefonoip.negocio.beans.Call;
import com.jmdomingueza.telefonoip.negocio.beans.CallDummy;
import com.jmdomingueza.telefonoip.negocio.beans.CallDummyImpl;
import com.jmdomingueza.telefonoip.negocio.beans.CallSip;
import com.jmdomingueza.telefonoip.negocio.beans.CallSipImpl;
import com.jmdomingueza.telefonoip.negocio.beans.CountDummy;
import com.jmdomingueza.telefonoip.negocio.beans.CountDummyImpl;
import com.jmdomingueza.telefonoip.negocio.beans.CountSip;
import com.jmdomingueza.telefonoip.negocio.beans.CountSipImpl;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallDummyImpl;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallSip;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCallSipImpl;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountDummy;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountDummyImpl;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountSip;
import com.jmdomingueza.telefonoip.negocio.beans.HistoryCountSipImpl;

/** 
 * Clase que contiene las operaciones para crear los beans 
 * de la capa de negocio.
 * 
 * @author jmdomingueza
 *
 */
public class BeanFactory {

	private static final Log logger = LogFactory.getLog(BeanFactory.class);

	/**
	 * Metodo que crea el bean de Audio con todos los parametros
	 * @param nameInfo Nombre de la tarjeta de sonido devuelto por AudioSystem.getMixerInfo
	 * @param type Tipo de tarjeta de sonido (Microfono o Altavoz)
	 * @return Audio creada
	 */
	public static Audio createAudio(String nameInfo, String type) {
		Audio audio;
		logger.trace("Enter createAudio - con parametros");
		
		audio = new AudioImpl();
		audio.setNameInfo(nameInfo);
		audio.setType(type);
		
		logger.trace("Exit createAudio - con todos parametros");
		return audio;
	}
	/**
	 * Metodo que crea el bean de CountDummy sin parametros
	 * 
	 * @return Cuenta dummy creada
	 */
	public static CountDummy createCountDummy(){
		CountDummy countDummy;
		
		logger.trace("Enter createCountDummyData - sin parametros");
		
		countDummy = new CountDummyImpl();
		countDummy.setState(CountDummy.State.unregistered);
		
		logger.trace("Exit createCountDummyData - sin parametros");
		return countDummy;
	}
	

	/**
	 * Metodo que crea el bean de CountDummy con todos los parametros
	 * @param user Nombre del usuario de la cuenta dummy.
	 * @param editable Atributo editablede la cuenta dummy.
	 * @param state Estado de la cuenta dummy.
	 * 
	 * @return Cuenta dummy creada
	 */
	public static CountDummy createCountDummy(String user, String editable, CountDummy.State state){
		CountDummy countDummy;
		
		logger.trace("Enter createCountDummyData - con todos parametros");
		
		countDummy = new CountDummyImpl();
		countDummy.setUser(user);
		countDummy.setEditable(editable);
		countDummy.setState(state);
		
		logger.trace("Exit createCountDummyData - con todos parametros");
		return countDummy;
	}
	
	
	/**
	 * Metodo que crea el bean CountSip sin parametros
	 * @return Cuenta sip creada 
	 */
	public static CountSip createCountSip() {
		CountSip countSip;
		
		logger.trace("Enter createCountSip - sin parametros");
		
		countSip = new CountSipImpl();
		try{
			countSip.setLocalHost(Inet4Address.getLocalHost().getHostAddress());
		}catch (Exception e) {
			logger.warn("Se ha producido una excepcion a la hora de  obtener el host local, se pone la de porDefecto");
			countSip.setLocalHost("127.0.0.1");
			
		}
		countSip.setLocalPort(ListeningPoint.PORT_5060);
		countSip.setProtocol(ListeningPoint.UDP);
		countSip.setState(CountSip.State.unregistered);
		
		logger.trace("Exit createCountSip - sin parametros");
		return countSip;
	}
	
	/**
	 * Metodo que crea el bean de CountSip con todos los parametros
	 * @param user Nombre del usuario de la cuenta sip.
	 * @param hostServer  Host del servidor donde esta registrada la cuenta sip.
	 * @param password Password de la cuenta sip.
	 * @param state Estado de la cuenta sip.
	 * @param display Valor del  display que tiene la cuenta sip.
	 * @param audioAvalibleList Lista de enteros que corresponde con los 
	 * audios permitidos por la cuenta sip.

	 * @return Cuenta sip creada 
	 */
	public static CountSip createCountSip(String user,String hostServer, String password, CountSip.State state,
			String display, List<Integer> audioAvalibleList) {
		CountSip countSip;
		
		logger.trace("Enter createCountSip");
		
		countSip = new CountSipImpl();
		countSip.setUser(user);
		try{
			countSip.setLocalHost(Inet4Address.getLocalHost().getHostAddress());
		}catch (Exception e) {
			logger.warn("Se ha producido una excepcion a la hora de  obtener el host local, se pone la de porDefecto");
			countSip.setLocalHost("127.0.0.1");
			
		}
		countSip.setLocalPort(ListeningPoint.PORT_5060);
		countSip.setProtocol(ListeningPoint.UDP);
		countSip.setHostServer(hostServer);
		countSip.setPassword(password);
		countSip.setState(state);
		countSip.setDisplay(display);
		countSip.setAudioAvalibleList(audioAvalibleList);
		
		logger.trace("Exit createCountSip");
		return countSip;
	}
	
	/**
	 * Metodo que crea el bean de CallDummy con todos los parametros
	 * @param id Identificador de la llamada dummy.
	 * @param count Cuenta desde la que se hace la llamada dummy.
	 * @param number Numero de destino o origen de la llamada dummy, depende del sentido de la llamada dummy.
	 * @param state Estado de la llamada dummy.
	 * @param direction Sentido de la llamada dummy.
	 * @param description Descripcion de la llamada dummy.
	 * 
	 * @return Llamada dummy creada 
	 */
	public static CallDummy createCallDummy(int id, CountDummy count, String number, CallDummy.State state,
			CallDummy.Direction direction, String description) {
		CallDummy call;
		
		logger.trace("Enter createCallDummy");
		
		call = new CallDummyImpl();
		call.setId(id);
		call.setCount(count);
		call.setNumber(number);
		call.setState(state);
		call.setDirection(direction);
		call.setDescription(description);
		
		logger.trace("Exit createCallDummy");
		return call;
	}
	
	/**
	 * Metodo que crea el bean de CallSip con todos los parametros
	 * @param id Identificador de la llamada sip.
	 * @param count Cuenta desde la que se hace la llamada sip.
	 * @param number Numero de destino o origen de la llamada sip, depende del sentido de la llamada sip.
	 * @param state Estado de la llamada sip.
	 * @param direction Sentido de la llamada sip.
	 * @param description Descripcion de la llamada sip.
	 * @param tagFrom Etiqueta del usuario que realiza la llamada sip.
	 * @param tagTo Etiqueta del usuario que realiza la llamada sip.
	 * @param branchVia Rama de la via de la llamada sip.
	 * @param contentType  Tipo del contenido de la llamada sip.
	 * @param contentSubType Subtipo del contenido de la llamada sip.
	 * @param mode Modo del SessionDescription de la llamada sip.
	 * 
	 * @return Llamada sip creada 
	 */
	public static CallSip createCallSip(int id, CountSip count, String number, CallDummy.State state,
			CallDummy.Direction direction, String description, String tagFrom, String tagTo,
			String branchVia, String contentType, String contentSubType, Integer mode) {
		CallSip call;
		
		logger.trace("Enter createCallSip");
		
		call = new CallSipImpl();
		call.setId(id);
		call.setCount(count);
		call.setNumber(number);
		call.setState(state);
		call.setDirection(direction);
		call.setDescription(description);
		call.setTagFrom(tagFrom);
		call.setTagTo(tagTo);
		call.setBranchVia(branchVia);
		call.setContentType(contentType);
		call.setContentSubType(contentSubType);
		call.setSessionDescriptionMode(mode);
		
		logger.trace("Exit createCallSip");
		return call;
	}
	
	/**
	 * Metodo que crea el bean de HistoryCallDummy con todos los parametros
	 * @param idLlamada Identifidor de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param count Cuenta de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param number Numero de destino o origen de la llamada dummy que corresponde
	 * 				 con el historico de llamada dummy.
	 * @param state Estado del historico de llamada dummy
	 * @param direction Sentido de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param description  Descripcion de la llamada dummy que corresponde con el historico de llamada dummy.
	 * @param date Fecha(dia y hora) del historico de llamada dummy.
	 *
	 * @return Historico de llamada dummy creado
	 */
	public static HistoryCallDummy createHistoryCallDummy(int idLlamada, String count, String number, HistoryCallDummy.State state,
			HistoryCallDummy.Direction direction, String description, Date date) {
		HistoryCallDummy historyCall;
		
		logger.trace("Enter createHistoryCallDummy");
		
		historyCall = new HistoryCallDummyImpl();
		historyCall.setIdLlamada(idLlamada);
		historyCall.setCount(count);
		historyCall.setNumber(number);
		historyCall.setState(state);
		historyCall.setDirection(direction);
		historyCall.setDescription(description);
		historyCall.setDate(date);
		
		logger.trace("Exit createHistoryCallDummy");
		return historyCall;
	}
	
	public static HistoryCallDummy createHistoryCallDummy(Call call) {
		HistoryCallDummy historyCall;
		
		logger.trace("Enter createHistoryCallDummy");
		
		historyCall = new HistoryCallDummyImpl();
		historyCall.setIdLlamada(call.getId());
		historyCall.setCount(call.getCount()!=null?call.getCount().toString():"");
		historyCall.setNumber(call.getNumber());
		historyCall.setState(ParseDummyFactory.parseStateCaDToHCaD(call.getState()));
		historyCall.setDirection(ParseDummyFactory.parseDirectionCaDToHCaD(call.getDirection()));
		historyCall.setDescription(call.getDescription());
		historyCall.setDate(new Date());
		
		logger.trace("Exit createHistoryCallDummy");
		return historyCall;
	}
	
	/**
	 * Metodo que crea el bean de HistoryCallSip con todos los parametros
	 * @param idLlamada Identifidor de la llamada sip que corresponde con el historico de llamada sip.
	 * @param count Cuenta de la llamada sip que corresponde con el historico de llamada sip.
	 * @param number Numero de destino o origen de la llamada dummy que corresponde
	 * 				 con el historico de llamada sip.
	 * @param state Estado del historico de llamada sip
	 * @param direction Sentido de la llamada sip que corresponde con el historico de llamada sip.
	 * @param description  Descripcion de la llamada sip que corresponde con el historico de llamada sip.
	 * @param date Fecha(dia y hora) del historico de llamada sip.
	 *
	 * @return Historico de llamada sip creado
	 */
	public static HistoryCallSip createHistoryCallSip(int idLlamada, String count, String number, HistoryCallDummy.State state,
			HistoryCallDummy.Direction direction, String description, Date date) {
		HistoryCallSip historyCall;
		
		logger.trace("Enter createHistoryCallSip");
		
		historyCall = new HistoryCallSipImpl();
		historyCall.setIdLlamada(idLlamada);
		historyCall.setCount(count);
		historyCall.setNumber(number);
		historyCall.setState(state);
		historyCall.setDirection(direction);
		historyCall.setDescription(description);
		historyCall.setDate(date);
		
		logger.trace("Exit createHistoryCallSip");
		return historyCall;
	}
	
	/**
	 * 
	 * @param call
	 * @return
	 */
	public static HistoryCallSip createHistoryCallSip(Call call) {
		HistoryCallSip historyCall;
		
		logger.trace("Enter createHistoryCallSip");
		
		historyCall = new HistoryCallSipImpl();
		historyCall.setIdLlamada(call.getId());
		historyCall.setCount(call.getCount()!=null?call.getCount().toString():"");
		historyCall.setNumber(call.getNumber());
		historyCall.setState(ParseSipFactory.parseStateCaSToHCaS(call.getState()));
		historyCall.setDirection(ParseSipFactory.parseDirectionCaSToHCaS(call.getDirection()));
		historyCall.setDescription(call.getDescription());
		historyCall.setDate(new Date());
		
		logger.trace("Exit createHistoryCallSip");
		return historyCall;
	}
	
	/**
	 * Metodo que crea el bean de HistoryCountDummy con todos los parametros
	 * @param user Nombre del usuario del historico de cuenta dummy.
	 * @param action Accion del historico de cuenta dummy.
	 * @param date Fecha(dia y hora) del historico de cuenta dummy.
	 * @param state Estado del historico de cuenta dummy.
	 * @param editable Atributo editable del historico de cuenta dummy.
	 * 
	 * @return Historico de cuenta dummy creado
	 */
	public static HistoryCountDummy createHistoryCountDummy(String user,HistoryCountDummy.Action action, Date date, 
			HistoryCountDummy.State state,  String editable){
		HistoryCountDummy historyCountDummy;
		
		logger.trace("Enter createHistoryCountDummyData - con todos parametros");
		
		historyCountDummy = new HistoryCountDummyImpl();
		//atributos de HistoryCount
		historyCountDummy.setUser(user);
		historyCountDummy.setAction(action);
		historyCountDummy.setDate(date);
		//atributos de HistoryCountDummy
		historyCountDummy.setState(state);
		historyCountDummy.setEditable(editable);
		
		logger.trace("Exit createHistoryCountDummyData - con todos parametros");
		return historyCountDummy;
	}
	
	/**
	 * Metodo que crea el bean de HistoryCountDummy con todos los parametros
	 * @param countDummy Cuenta dummy con la que se crea el historico de cuenta dummy.
	 * @param action Accion del historico de cuenta dummy.
	 * 
	 * @return Historico de cuenta dummy creado
	 */
	public static HistoryCountDummy createHistoryCountDummy(CountDummy countDummy,HistoryCountDummy.Action action){
		HistoryCountDummy historyCountDummy;
		
		logger.trace("Enter createHistoryCountDummyData - con todos parametros");
		
		historyCountDummy = new HistoryCountDummyImpl();
		//atributos de HistoryCount
		historyCountDummy.setUser(countDummy.getUser());
		historyCountDummy.setAction(action);
		historyCountDummy.setDate(new Date());
		//atributos de HistoryCountDummy
		historyCountDummy.setState(ParseDummyFactory.parseStateCoDToHCoD(countDummy.getState()));
		historyCountDummy.setEditable(countDummy.getEditable());
		
		logger.trace("Exit createHistoryCountDummyData - con todos parametros");
		return historyCountDummy;
	}
	
	/**
	 * Metodo que crea el bean de HistoryCountSip con todos los parametros
	 * @param user Nombre del usuario del historico de cuenta sip.
	 * @param action Accion del historico de cuenta sip.
	 * @param date Fecha(dia y hora) del historico de cuenta sip.
	 * @param hostServer  Host del servidor donde esta registrado el historico de cuenta sip
	 * @param state Estado del historico de cuenta sip.
	 * @param password Password del historico de cuenta sip.
	 * 
	 * @return Historico de cuenta sip creado
	 */
	public static HistoryCountSip createHistoryCountSip(String user, HistoryCountSip.Action action, Date date,
			String hostServer,HistoryCountSip.State state,String password) {
		HistoryCountSip historyCountSip;
		
		logger.trace("Enter createHistoryCountSip");
		
		historyCountSip = new HistoryCountSipImpl();
		//atributos de HistoryCount
		historyCountSip.setUser(user);
		historyCountSip.setAction(action);
		historyCountSip.setDate(date);
		//atributos de HistoryCountSip
		historyCountSip.setHostServer(hostServer);
		historyCountSip.setState(state);
		historyCountSip.setPassword(password);
	
		logger.trace("Exit createHistoryCountSip");
		return historyCountSip;
	}
	
	/**
	 * Metodo que crea el bean de HistoryCountSip con todos los parametros
	 * @param countSip Cuenta sip con la que se crea el historico de cuenta sip.
	 * @param action Accion del historico de cuenta sip.
	 * 
	 * @return Historico de cuenta sip creado
	 */
	public static HistoryCountSip createHistoryCountSip(CountSip countSip, HistoryCountSip.Action action) {
		HistoryCountSip historyCountSip;
		
		logger.trace("Enter createHistoryCountSip");
		
		historyCountSip = new HistoryCountSipImpl();
		//atributos de HistoryCount
		historyCountSip.setUser(countSip.getUser());
		historyCountSip.setAction(action);
		historyCountSip.setDate(new Date());
		//atributos de HistoryCountSip
		historyCountSip.setHostServer(countSip.getHostServer());
		historyCountSip.setState(ParseSipFactory.parseStateCoSToHCoS(countSip.getState()));
		historyCountSip.setPassword(countSip.getPassword());
	
		logger.trace("Exit createHistoryCountSip");
		return historyCountSip;
	}

	
}
