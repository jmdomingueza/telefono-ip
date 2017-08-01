package com.jmdomingueza.telefonoip.negocio.services;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.common.messages.MessageListener;
import com.jmdomingueza.telefonoip.common.messages.MessageService;
import com.jmdomingueza.telefonoip.common.messages.ihm.UpdateMessage;
import com.jmdomingueza.telefonoip.common.messages.sip.SipMessage;
import com.jmdomingueza.telefonoip.negocio.beans.CallSip;
import com.jmdomingueza.telefonoip.negocio.beans.CountSip;
import com.jmdomingueza.telefonoip.negocio.beans.Call.State;
import com.jmdomingueza.telefonoip.negocio.factories.BeanFactory;
import com.jmdomingueza.telefonoip.negocio.factories.ParseSipFactory;
import com.jmdomingueza.telefonoip.sip.beans.Call;
import com.jmdomingueza.telefonoip.sip.services.SipService;
import com.jmdomingueza.telefonoip.sip.services.SipServiceImpl;

@Service(value = "callSipService")
public class CallSipServiceImpl  implements CallSipService,MessageListener<SipMessage>{

	/**
	 * Logger de la clase
	 */
	private static Log logger = LogFactory.getLog(CallSipServiceImpl.class);
	
	@Autowired
	private SipService sipService;
	
	public CallSipServiceImpl() {
		MessageService.addMessageListener(MessageService.SIP_GROUP_NAME, this);
	}

	public void init(){
		logger.trace("Enter init");
		
		logger.trace("Exit init");
	}
	
	public void destroy(){
		logger.trace("Enter destroy");
		
		MessageService.removeMessageListener(MessageService.SIP_GROUP_NAME, this);
		logger.trace("Exit destroy");
	}
	
	@Override
	public void dial(CountSip count,String number){
		com.jmdomingueza.telefonoip.sip.beans.Call callCapaSip;
		
		logger.trace("Enter dial");
		
		// Creamos la llamada saliente dialing
		CallSip callSip = BeanFactory.createCallSip(createNewId(), count, number, CallSip.State.dialing,
				CallSip.Direction.out, "", null, null, null, null, null, null);
		try {

			// Realizamos la llamada del CallSip de la capa de sip
			callCapaSip = ParseSipFactory.parseCaSToCa(callSip);
			sipService.invite(callCapaSip);

			// Notificar para arriba dialing
			MessageService.sendMessage(new UpdateMessage(this, callSip));
		} catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callSip.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callSip));
		}

		logger.trace("Exit dial");
	}
	
	@Override
	public void response(CallSip callSip) {
		Call callCapaSip;
		
		logger.trace("Enter response");
		try{
			//contestamos la llamada del CallSip de la capa de sip
			callCapaSip = ParseSipFactory.parseCaSToCa(callSip);
			sipService.invite(callCapaSip);
		
			// Notificar para arriba responding
			callSip.setState(State.responding);
			MessageService
				.sendMessage(new UpdateMessage(this, callSip));

		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callSip.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callSip));
		}
		
		logger.trace("Exit response");
		
	}
	
	@Override
	public void drop(CallSip callSip){
		Call callCapaSip;
		
		logger.trace("Enter drop");
		
		try {
			//terminamos la llamada del CallSip de la capa de sip
			callCapaSip = ParseSipFactory.parseCaSToCa(callSip);
			sipService.bye(callCapaSip);
			
			// Notificar para arriba terminating
			callSip.setState(State.terminating);
			MessageService
				.sendMessage(new UpdateMessage(this, callSip));
		} catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callSip.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callSip));
		}
		logger.trace("Exit drop");
	}
	
	@Override
	public void cancel(CallSip callSip) {
		Call callCapaSip;
		
		logger.trace("Enter cancel");
		try{
			//cancelamos la llamada del CallSip de la capa de sip
			callCapaSip = ParseSipFactory.parseCaSToCa(callSip);
			//callService.cancel(callCapaSip, sipManager, ListeningPoint.PORT_5060, ListeningPoint.UDP);

		
			// Notificar para arriba canceling
			callSip.setState(State.canceling);
			MessageService
				.sendMessage(new UpdateMessage(this, callSip));

		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callSip.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callSip));
		}
		
		logger.trace("Exit cancel");
	}

	@Override
	public void transfer(CallSip callSipTranfer, CallSip callSipTranfered) {
		Call callTransferCapaSip,callTransferedCapaSip;
		
		logger.trace("Enter transfer");
		try{
			//transferimos las llamadas del CallSip de la capa de sip
			callTransferCapaSip = ParseSipFactory.parseCaSToCa(callSipTranfer);
			callTransferedCapaSip = ParseSipFactory.parseCaSToCa(callSipTranfered);
			//callService.transfer(callTransferCapaSip,callTransferedCapaSip, sipManager,  ListeningPoint.PORT_5060, ListeningPoint.UDP);
			
			// Notificar para arriba transfering
			callSipTranfer.setState(State.transfering);
			MessageService
				.sendMessage(new UpdateMessage(this, callSipTranfer));

			// Notificar para arriba transfering
			callSipTranfered.setState(State.transfering);
			MessageService
				.sendMessage(new UpdateMessage(this, callSipTranfered));

		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callSipTranfer.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callSipTranfer));
			callSipTranfered.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callSipTranfered));
		}
		
		logger.trace("Exit transfer");	
		
	}

	@Override
	public void conference(CallSip callSipConference1, CallSip callSipConference2) {
		Call callConference1CapaSip,callConference2CapaSip;
		
		logger.trace("Enter conference");
		try{
			//conferenciamos las llamadas del CallSip de la capa de sip
			callConference1CapaSip = ParseSipFactory.parseCaSToCa(callSipConference1);
			callConference2CapaSip = ParseSipFactory.parseCaSToCa(callSipConference2);
			//callService.conference(callConference1CapaSip,callConference2CapaSip, sipManager,  ListeningPoint.PORT_5060, ListeningPoint.UDP);
			
			// Notificar para arriba confering
			callSipConference1.setState(State.confering);
			MessageService
				.sendMessage(new UpdateMessage(this, callSipConference1));
			// Notificar para arriba confering
			callSipConference2.setState(State.confering);
			MessageService
				.sendMessage(new UpdateMessage(this, callSipConference2));
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callSipConference1.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callSipConference1));
			callSipConference2.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callSipConference2));
		}
		
		logger.trace("Exit conference");	
		
	}

	@Override
	public void held(CallSip callSip) {
		Call callCapaSip;
		
		logger.trace("Enter held");
		try{
			//realizamos la llamada del CallSip de la capa de sip
			callCapaSip = ParseSipFactory.parseCaSToCa(callSip);
			sipService.invite(callCapaSip);

			// Notificar para arriba helding
			callSip.setState(State.helding);
			MessageService
				.sendMessage(new UpdateMessage(this, callSip));
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callSip.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callSip));
		}
		
		logger.trace("Exit held");	
		
	}

	@Override
	public void retieve(CallSip callSip) {
		Call callCapaSip;
		
		logger.trace("Enter retieve");
		try{
			//realizamos la llamada del CallSip de la capa de sip
			callCapaSip = ParseSipFactory.parseCaSToCa(callSip);
			sipService.invite(callCapaSip);

		
			// Notificar para arriba retrieving
			callSip.setState(State.retrieving);
			MessageService
				.sendMessage(new UpdateMessage(this, callSip));
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callSip.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callSip));
		}
		
		logger.trace("Exit retieve");	
		
	}

	@Override
	public void receiveMessage(SipMessage message) {
		Call call;
		
		logger.trace("Enter receiveMessage");
		
		switch (message.getType()) {
		case SipMessage.CALL_TYPE:
			if( message.getSource() instanceof SipServiceImpl){
				call = (com.jmdomingueza.telefonoip.sip.beans.Call) message.getValue();
				//TODO: Hay que implementar la gestion del Objeto
				
			}
			break;
		case SipMessage.REGISTER_TYPE:
			// No se hace nada con estos tipo en esta clase
			break;
		default:
			logger.warn("Se recibe un tipo que no es tratado: "+message.getType());
			break;
		}
		logger.trace("Exit receiveMessage");
		
	}
	
	/**
	 * Metodo que crea un Id aleatorio
	 * @return
	 */
	private int createNewId() {
		
		logger.trace("Enter createNewId");
		
		int randomId = ThreadLocalRandom.current().nextInt();
		
		logger.trace("Exit createNewId");
		return randomId;
	}
}
