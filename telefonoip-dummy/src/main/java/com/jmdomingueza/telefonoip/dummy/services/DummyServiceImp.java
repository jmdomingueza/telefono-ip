package com.jmdomingueza.telefonoip.dummy.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.common.messages.MessageService;
import com.jmdomingueza.telefonoip.common.messages.dummy.DummyMessage;
import com.jmdomingueza.telefonoip.dummy.beans.Call;
import com.jmdomingueza.telefonoip.dummy.beans.Count;

@Service(value = "dummyService")
public class DummyServiceImp implements DummyService {

	private static final Log logger = LogFactory.getLog(DummyServiceImp.class);
	
	@Override
	public void registerCount(Count count) {
		logger.trace("Enter registerCount");
		
		count.setState(Count.State.registered);
		CountThread countThread = new CountThread(count);
		countThread.start();
		
		logger.trace("Exit registerCount");
	}

	@Override
	public void unregisterCount(Count count) {
		logger.trace("Enter unregisterCount");
		
		count.setState(Count.State.unregistered);
		CountThread countThread = new CountThread(count);
		countThread.start();
		
		logger.trace("Exit unregisterCount");
	}

	@Override
	public void talkingCall(Call call) {
		logger.trace("Enter talkingCall");
		
		call.setState(Call.State.talking);
		CallThread callThread = new CallThread(call);
		callThread.start();
		
		logger.trace("Exit talkingCall");
	}
	
	@Override
	public void heldCall(Call call) {
		logger.trace("Enter heldCall");
		
		call.setState(Call.State.held);
		CallThread callThread = new CallThread(call);
		callThread.start();
		
		logger.trace("Exit heldCall");
		
	}

	@Override
	public void terminatedCall(Call call) {
		logger.trace("Enter terminatedCall");
		
		call.setState(Call.State.terminated);
		CallThread callThread = new CallThread(call);
		callThread.start();
		
		logger.trace("Exit terminatedCall");
	}
	
	@Override
	public void canceledCall(Call call) {
		logger.trace("Enter canceledCall");
		
		call.setState(Call.State.canceled);
		CallThread callThread = new CallThread(call);
		callThread.start();
		
		logger.trace("Exit canceledCall");
		
	}
	
	@Override
	public void transferedCall(Call call) {
		logger.trace("Enter transferedCall");
		
		call.setState(Call.State.transfered);
		CallThread callThread = new CallThread(call);
		callThread.start();
		
		logger.trace("Exit transferedCall");
		
	}
	
	
	@Override
	public void conferenceCall(Call call) {
		logger.trace("Enter conferenceCall");
		
		call.setState(Call.State.conference);
		CallThread callThread = new CallThread(call);
		callThread.start();
		
		logger.trace("Exit conferenceCall");
		
	}
	
	/**
	 * Clase de un hilo que lo que hace es mandar un mensaje con
	 * la cuenta 5 segundo despues de inciar el hilo
	 * @author jmdomingueza
	 *
	 */
	private class CountThread extends Thread{

		private Count count;
		
		public CountThread(Count count) {
			this.count = count;
		}
		
		@Override
		public void run() {
			try {
				sleep(5000);
				MessageService
					.sendMessage(new DummyMessage(this,DummyMessage.REGISTER_TYPE, this.count));
			} catch (InterruptedException e) {}
			
		}
		
	}
	
	/**
	 * Clase de un hilo que lo que hace es mandar un mensaje con
	 * la llamada 5 segundo despues de inciar el hilo
	 * @author jmdomingueza
	 *
	 */
	private class CallThread extends Thread{

		private Call call;
		
		public CallThread(Call call) {
			this.call = call;
		}
		
		@Override
		public void run() {
			try {
				sleep(5000);
				MessageService
					.sendMessage(new DummyMessage(this,DummyMessage.CALL_TYPE, this.call));
			} catch (InterruptedException e) {}
			
		}
		
	}

	
	
	

	
}
