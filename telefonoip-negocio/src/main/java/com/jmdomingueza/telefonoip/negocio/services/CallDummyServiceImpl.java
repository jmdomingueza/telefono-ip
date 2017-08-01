package com.jmdomingueza.telefonoip.negocio.services;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.common.messages.MessageListener;
import com.jmdomingueza.telefonoip.common.messages.MessageService;
import com.jmdomingueza.telefonoip.common.messages.dummy.DummyMessage;
import com.jmdomingueza.telefonoip.common.messages.ihm.UpdateMessage;
import com.jmdomingueza.telefonoip.dummy.beans.Call;
import com.jmdomingueza.telefonoip.dummy.services.DummyService;
import com.jmdomingueza.telefonoip.negocio.beans.CallDummy;
import com.jmdomingueza.telefonoip.negocio.beans.CountDummy;
import com.jmdomingueza.telefonoip.negocio.beans.Call.State;
import com.jmdomingueza.telefonoip.negocio.factories.BeanFactory;
import com.jmdomingueza.telefonoip.negocio.factories.ParseDummyFactory;

@Service(value = "callDummyDummyService")
public class CallDummyServiceImpl implements CallDummyService,MessageListener<DummyMessage> {
	
	/**
	 * Logger de la clase
	 */
	private static Log logger = LogFactory.getLog(CallDummyServiceImpl.class);

	@Autowired
	private DummyService dummyService;
	
	/**
	 * Servicio que gestiona las operacion con la capa de persistencia
	 */
	@Autowired
	private PersistenceService persistenceService;
	
	/**
	 * Hilo que cada minuto crea una llamada ringing, si no es contestada al
	 * cabo de un minuto la pone a lost y crea una nueva
	 */
	private CallRingingThread thread;


	/**
	 * Construtor de la clase
	 */
	public CallDummyServiceImpl() {
		
		logger.trace("Enter CountDummyServiceImpl");
		
		thread = new CallRingingThread();
		thread.start();

		MessageService.addMessageListener(MessageService.DUMMY_GROUP_NAME, this);
		
		logger.trace("Exit CountDummyServiceImpl");
	}
	
	public void destroy(){
		
		logger.trace("Enter destroy");
		
		if (thread != null) {
			thread.interrupt();
			thread = null;
		}
		MessageService.removeMessageListener(MessageService.DUMMY_GROUP_NAME, this);
		
		logger.trace("Exit destroy");
	}
	
	@Override
	public void dial(CountDummy count, String number) {
		Call callCapaDummy;

		logger.trace("Enter dial");

		// Creamos la llamada saliente dialing
		CallDummy callDummy = BeanFactory.createCallDummy(createNewId(), count, number, CallDummy.State.dialing,
				CallDummy.Direction.out, "");
		try {

			// Realizamos la llamada del CallDummy de la capa de dummy
			callCapaDummy = ParseDummyFactory.parseCaDToCa(callDummy);
			dummyService.talkingCall(callCapaDummy);

			// Notificar para arriba dialing
			MessageService.sendMessage(new UpdateMessage(this, callDummy));
		} catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callDummy.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callDummy));
		}

		logger.trace("Exit dial");
	}

	@Override
	public void response(CallDummy callDummy) {
		Call callCapaDummy;
		
		logger.trace("Enter response");
		try{
			//contestamos la llamada del CallDummy de la capa de dummy
			callCapaDummy = ParseDummyFactory.parseCaDToCa(callDummy);
			dummyService.talkingCall(callCapaDummy);
		
			// Notificar para arriba responding
			callDummy.setState(State.responding);
			MessageService
				.sendMessage(new UpdateMessage(this, callDummy));

		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callDummy.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callDummy));
		}
		
		logger.trace("Exit response");
		
	}

	@Override
	public void drop(CallDummy callDummy) {
		Call callDummyCapaDummy;
		
		logger.trace("Enter drop");
		try{
			//terminamos la llamada del CallDummy de la capa de dummy
			callDummyCapaDummy = ParseDummyFactory.parseCaDToCa(callDummy);
			dummyService.terminatedCall(callDummyCapaDummy);
			
			// Notificar para arriba terminating
			callDummy.setState(State.terminating);
			MessageService
				.sendMessage(new UpdateMessage(this, callDummy));
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callDummy.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callDummy));
		}
		
		logger.trace("Exit drop");
		
		
	}

	@Override
	public void cancel(CallDummy callDummy) {
		Call callCapaDummy;
		
		logger.trace("Enter cancel");
		try{
			//cancelamos la llamada del CallDummy de la capa de dummy
			callCapaDummy = ParseDummyFactory.parseCaDToCa(callDummy);
			dummyService.canceledCall(callCapaDummy);
		
			// Notificar para arriba canceling
			callDummy.setState(State.canceling);
			MessageService
				.sendMessage(new UpdateMessage(this, callDummy));

		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callDummy.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callDummy));
		}
		
		logger.trace("Exit cancel");
	}

	@Override
	public void transfer(CallDummy callDummyTranfer, CallDummy callDummyTranfered) {
		Call callTransferCapaDummy,callTransferedCapaDummy;
		
		logger.trace("Enter transfer");
		try{
			//transferimos las llamadas del CallDummy de la capa de dummy
			callTransferCapaDummy = ParseDummyFactory.parseCaDToCa(callDummyTranfer);
			dummyService.terminatedCall(callTransferCapaDummy);
		
			callTransferedCapaDummy = ParseDummyFactory.parseCaDToCa(callDummyTranfered);
			dummyService.transferedCall(callTransferedCapaDummy);
			
			// Notificar para arriba transfering
			callDummyTranfer.setState(State.transfering);
			MessageService
				.sendMessage(new UpdateMessage(this, callDummyTranfer));

			// Notificar para arriba transfering
			callDummyTranfered.setState(State.transfering);
			MessageService
				.sendMessage(new UpdateMessage(this, callDummyTranfered));

		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callDummyTranfer.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callDummyTranfer));
			callDummyTranfered.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callDummyTranfered));
		}
		
		logger.trace("Exit transfer");	
		
	}

	@Override
	public void conference(CallDummy callDummyConference1, CallDummy callDummyConference2) {
		Call callConference1CapaDummy,callConference2CapaDummy;
		
		logger.trace("Enter conference");
		try{
			//conferenciamos las llamadas del CallDummy de la capa de dummy
			callConference1CapaDummy = ParseDummyFactory.parseCaDToCa(callDummyConference1);
			dummyService.conferenceCall(callConference1CapaDummy);
		
			callConference2CapaDummy = ParseDummyFactory.parseCaDToCa(callDummyConference2);
			dummyService.conferenceCall(callConference2CapaDummy);
			
			// Notificar para arriba confering
			callDummyConference1.setState(State.confering);
			MessageService
				.sendMessage(new UpdateMessage(this, callDummyConference1));
			// Notificar para arriba confering
			callDummyConference2.setState(State.confering);
			MessageService
				.sendMessage(new UpdateMessage(this, callDummyConference2));
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callDummyConference1.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callDummyConference1));
			callDummyConference2.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callDummyConference2));
		}
		
		logger.trace("Exit conference");	
		
	}

	@Override
	public void held(CallDummy callDummy) {
		Call callCapaDummy;
		
		logger.trace("Enter held");
		try{
			//realizamos la llamada del CallDummy de la capa de dummy
			callCapaDummy = ParseDummyFactory.parseCaDToCa(callDummy);
			dummyService.heldCall(callCapaDummy);
		
			// Notificar para arriba helding
			callDummy.setState(State.helding);
			MessageService
				.sendMessage(new UpdateMessage(this, callDummy));
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callDummy.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callDummy));
		}
		
		logger.trace("Exit held");	
		
	}

	@Override
	public void retieve(CallDummy callDummy) {
		Call callCapaDummy;
		
		logger.trace("Enter retieve");
		try{
			//realizamos la llamada del CallDummy de la capa de dummy
			callCapaDummy = ParseDummyFactory.parseCaDToCa(callDummy);
			dummyService.talkingCall(callCapaDummy);
		
			// Notificar para arriba retrieving
			callDummy.setState(State.retrieving);
			MessageService
				.sendMessage(new UpdateMessage(this, callDummy));
		}catch (Exception e) {
			// TODO: Gestionar la excepcion
			e.printStackTrace();
			// Notificar para arriba el error
			callDummy.setState(State.error);
			MessageService.sendMessage(new UpdateMessage(this, callDummy));
		}
		
		logger.trace("Exit retieve");	
		
	}
	
	@Override
	public void receiveMessage(DummyMessage message) {
		
		logger.trace("Enter receiveMessage");
		
		switch (message.getType()) {
		case DummyMessage.CALL_TYPE:
			Call call = (Call) message.getValue();
			CallDummy callDummy = ParseDummyFactory.parseCaToCaD(call);
			//Notificamos a las otras capas el cambio.
			MessageService
				.sendMessage(new UpdateMessage(this, callDummy));
			break;
		case DummyMessage.REGISTER_TYPE:
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
	
	/**
	 * Clase de un hilo que cada minuto crea una llamada ringing, si no es
	 * contestada al cabo de un minuto la pone a lost y crea una nueva
	 * 
	 * @author jmdomingueza
	 *
	 */
	private class CallRingingThread extends Thread implements MessageListener<DummyMessage>{

		
		private CallDummy call;
		/**
		 * Constructor de la clase
		 */
		public CallRingingThread() {
			super("CallRingingThread");
			logger.trace("Enter CallRingingThread");
			logger.trace("Exit CallRingingThread");
		}

		@Override
		public void run() {
			
			logger.trace("Enter run");
			
			try {
				// Se duerme 10s para arrancar
				sleep(10000);
				// while infinito hasta que se interrumpe el hilo
				while (true) {
					//Creamos una llamada entrante y ringing y la pintamos en la tabla
					call = createCall();
					// Dormimos 1 min
					sleep(60000);
					if (call !=null && call.getState() == CallDummy.State.ringing) {
						call.setState(CallDummy.State.lost);
						//Notificamos a la actulizacion.
						MessageService
							.sendMessage(new UpdateMessage(this, call));
					}
				}
			} catch (InterruptedException e) {
			}
			logger.trace("Exit run");
		}
		
		@Override
		public void receiveMessage(DummyMessage message) {
			logger.trace("Enter receiveMessage");
			
			switch (message.getType()) {
			case DummyMessage.CALL_TYPE:
				Call call = (Call) message.getValue();
				CallDummy callDummy = ParseDummyFactory.parseCaToCaD(call);
				// Cuando se recibe un cambio de estado se guarda en la llamada
				// para saber que ya no es ringing
				if(callDummy == this.call){
					this.call = callDummy;
				}
				break;
			default:
				logger.warn("Se recibe un tipo que no es tratado: "+message.getType());
				break;
			}
		}

		/**
		 * Metodo crea una llamada entrante con la cuenta que esta seleccionada en comboBox de cuentas,
		 * con un numero aleatorio (0-1000) y  en estado ringing.
		 * @return
		 */
		private CallDummy createCall() {
			
			logger.trace("Enter createCall");
			
			CountDummy count = getFirtCountRegister();
			
			if(count==null){
				logger.trace("Exit createCall - count==null");
				return null;
			}

			CallDummy call = BeanFactory.createCallDummy(createNewId(), count, createNumberRandom(), CallDummy.State.ringing,
					CallDummy.Direction.in, "Texto");
			
			//Notificamos a la actulizacion.
			MessageService
				.sendMessage(new UpdateMessage(this, call));
			
			logger.trace("Exit createCall");
			return call;
		}
		
		/**
		 * Metodo que crea un Numero aleatorio
		 * @return
		 */
		private String createNumberRandom() {
			int randomNum;
			String randomNumS;
			
			logger.trace("Enter createNumberRandom");
			
			randomNum = ThreadLocalRandom.current().nextInt(0, 10000 + 1);
			randomNumS = "" + Math.abs(randomNum);
			
			logger.trace("Exit createNumberRandom");
			return randomNumS;
		}
		
		private CountDummy getFirtCountRegister(){
			Collection<CountDummy> countDummyCollection;
			
			logger.trace("Enter getFirtCountRegister");
			try{
				//obtenemos todos los CountDummy de la capa de persistencia
				countDummyCollection = persistenceService.getAllCountDummy();
				for(CountDummy count : countDummyCollection){
					if(count.getState()==CountDummy.State.registered){
						return count;
					}
				}
			}catch (Exception e) {
				// TODO: Gestionar la excepcion
				e.printStackTrace();
				return null;
			}
			
			logger.trace("Exit getFirtCountRegister");
			return null;
		}

		
	}

}
