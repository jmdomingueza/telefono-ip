package com.jmdomingueza.telefonoip.sip.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.TimeoutEvent;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.sip.events.MyDialogTerminatedEvent;
import com.jmdomingueza.telefonoip.sip.events.MyRequestEvent;
import com.jmdomingueza.telefonoip.sip.events.MyResponseEvent;
import com.jmdomingueza.telefonoip.sip.events.MyTimeoutEvent;
import com.jmdomingueza.telefonoip.sip.events.MyTransactionTerminatedEvent;

/**
 * Servicio que gestiona el añadido y eliminado de SipMessageListener en el GroupManagaer
 * y envio de {@link RequestEvent}, {@link ResponseEvent},{@link TimeoutEvent} a los
 * SipMessageListener.
 * 
 * @author jmdomingueza
 *
 */
@Service(value = "sipMessageService")
public class SipMessageServiceImpl implements SipMessageService {

	/**
	 * Logger de la clase
	 */
	private static Logger log = Logger.getLogger(SipMessageServiceImpl.class);

	/**
	 * Mapa con con todos los grupos definidos
	 */
	private static GroupManager group = new GroupManager();

	/**
	 * Cola con todos los mensajes RequestEvent por enviar
	 */
	private static BlockingQueue<MyRequestEvent> queueRequestEvent = new LinkedBlockingQueue<>();

	/**
	 * Cola con todos los mensajes ResponseEvent por enviar
	 */
	private static BlockingQueue<MyResponseEvent> queueResponseEvent = new LinkedBlockingQueue<>();

	/**
	 * Cola con todos los mensajes TimeoutEvent por enviar
	 */
	private static BlockingQueue<MyTimeoutEvent> queueTimeoutEvent = new LinkedBlockingQueue<>();

	/**
	 * Cola con todos los mensajes DialogTerminatedEvent por enviar
	 */
	private static BlockingQueue<MyDialogTerminatedEvent> queueDialogTerminatedEvent = new LinkedBlockingQueue<>();

	/**
	 * Cola con todos los mensajes TransactionTerminatedEvent por enviar
	 */
	private static BlockingQueue<MyTransactionTerminatedEvent> queueTransactionTerminatedEvent = new LinkedBlockingQueue<>();

	/**
	 * Hilo que se encarga de enviar los mensajes RequestEvent
	 */
	private static Thread dispatcherRequestEventThread = new Thread(new DispacherRequestEvent(),
			"RequestEvent-Dispatcher");

	/**
	 * Hilo que se encarga de enviar los mensajes ResponseEvent
	 */
	private static Thread dispatcherResponseEventThread = new Thread(new DispacherResponseEvent(),
			"ResponseEvent-Dispatcher");

	/**
	 * Hilo que se encarga de enviar los mensajes TimeoutEvent
	 */
	private static Thread dispatcherTimeoutEventThread = new Thread(new DispacherTimeoutEvent(),
			"TimeoutEvent-Dispatcher");
	
	/**
	 * Hilo que se encarga de enviar los mensajes DialogTerminatedEvent
	 */
	private static Thread dispatcherDialogTerminatedEventThread = new Thread(new DispacherDialogTerminatedEvent(),
			"DialogTerminatedEvent-Dispatcher");
	
	/**
	 * Hilo que se encarga de enviar los mensajes TransactionTerminatedEvent
	 */
	private static Thread dispatcherTransactionTerminatedEventThread = new Thread(new DispacherTransactionTerminatedEvent(),
			"TransactionTerminatedEvent-Dispatcher");


	static {
		dispatcherRequestEventThread.start();
		dispatcherResponseEventThread.start();
		dispatcherTimeoutEventThread.start();
		dispatcherDialogTerminatedEventThread.start();
		dispatcherTransactionTerminatedEventThread.start();
	}
	
	
	/**
	 * 
	 */
	public SipMessageServiceImpl() {
		super();
	}

	@Override
	public void addSipMessageListener(SipMessageListener listener) {
		if (group != null) {
			group.addSipMessageListener(listener);
		}
	}

	@Override
	public void addSipMessageListener(SipMessageListener listener, boolean topPriority) {
		if (group != null) {
			group.addSipMessageListener(listener, topPriority);
		}
	}

	@Override
	public void removeSipMessageListener(SipMessageListener listener) {
		if (group != null) {
			group.removeSipMessageListener(listener);
		}
	}

	@Override
	public void sendRequest(MyRequestEvent evt) {
		queueRequestEvent.add(evt);
	}

	@Override
	public void sendResponse(MyResponseEvent evt) {
		queueResponseEvent.add(evt);
	}

	@Override
	public void sendTimeout(MyTimeoutEvent evt) {
		queueTimeoutEvent.add(evt);
	}

	@Override
	public void sendDialogTerminated(MyDialogTerminatedEvent evt) {
		queueDialogTerminatedEvent.add(evt);
		
	}

	@Override
	public void sendTransactionTerminated(MyTransactionTerminatedEvent evt) {
		queueTransactionTerminatedEvent.add(evt);
		
	}
	/**
	 * Clase que gestiona los grupos
	 * 
	 * @author jmdomingueza
	 *
	 * @param <T>
	 */
	private static class GroupManager {

		/**
		 * Lista con los SipMessageListener de un grupo
		 */
		private List<SipMessageListener> listeners = Collections.synchronizedList(new ArrayList<SipMessageListener>());

		/**
		 * Añade un SipMessageListener a la lista
		 *
		 * @param listener
		 */
		public void addSipMessageListener(SipMessageListener listener) {
			addSipMessageListener(listener, false);
		}

		/**
		 * Añade un SipMessageListener a la lista con prioridad. Si topPriority
		 * es true pone el SipMessageListener al principio de la lista
		 *
		 * @param listener
		 * @param topPriority
		 */
		public void addSipMessageListener(SipMessageListener listener, boolean topPriority) {
			if (topPriority)
				listeners.add(0, listener);
			else
				listeners.add(listener);
		}

		/**
		 * Borra un SipMessageListener de la lista
		 *
		 * @param listener
		 *            DOCUMENT ME!
		 */
		public void removeSipMessageListener(SipMessageListener listener) {
			listeners.remove(listener);
		}

		/**
		 * Envia un mensaje a todos los RequestEvent de la lista a los
		 * SipMessageListener
		 *
		 * @param message
		 */
		public void sendRequestEvent(MyRequestEvent evt) {
			List<SipMessageListener> listenersCopy = new ArrayList<SipMessageListener>(listeners);

			for (SipMessageListener listener : listenersCopy) {
				listener.processRequest(evt.getRequestEvent(),evt.getSipManager());
			}
		}

		/**
		 * Envia un mensaje a todos los ResponseEvent de la lista a los
		 * SipMessageListener
		 *
		 * @param message
		 */
		public void sendResponseEvent(MyResponseEvent evt) {
			List<SipMessageListener> listenersCopy = new ArrayList<SipMessageListener>(listeners);

			for (SipMessageListener listener : listenersCopy) {
				listener.processResponse(evt.getResponseEvent(),evt.getSipManager());
			}
		}

		/**
		 * Envia un mensaje a todos los TimeoutEvent de la lista a los
		 * SipMessageListener
		 *
		 * @param message
		 */
		public void sendTimeoutEvent(MyTimeoutEvent evt) {
			List<SipMessageListener> listenersCopy = new ArrayList<SipMessageListener>(listeners);

			for (SipMessageListener listener : listenersCopy) {
				listener.processTimeout(evt.getTimeoutEvent(),evt.getSipManager());
			}
		}
		
		/**
		 * Envia un mensaje a todos los DialogTerminatedEvent de la lista a los
		 * SipMessageListener
		 *
		 * @param message
		 */
		public void sendDialogTerminatedEvent(MyDialogTerminatedEvent evt) {
			List<SipMessageListener> listenersCopy = new ArrayList<SipMessageListener>(listeners);

			for (SipMessageListener listener : listenersCopy) {
				listener.processDialogTerminated(evt.getDialogTerminatedEvent(),evt.getSipManager());
			}
		}
		
		/**
		 * Envia un mensaje a todos los TransactionTerminatedEvent de la lista a los
		 * SipMessageListener
		 *
		 * @param message
		 */
		public void sendTransactionTerminatedEvent(MyTransactionTerminatedEvent evt) {
			List<SipMessageListener> listenersCopy = new ArrayList<SipMessageListener>(listeners);

			for (SipMessageListener listener : listenersCopy) {
				listener.processTransactionTerminatedEvent(evt.getTransactionTerminatedEvent(),evt.getSipManager());
			}
		}
	}

	/**
	 * Hilo que se encarga de enviar los RequestEvent
	 */
	private static class DispacherRequestEvent implements Runnable {

		@Override
		public void run() {
			Thread thread = Thread.currentThread();
			while (!thread.isInterrupted()) {
				try {
					MyRequestEvent evt = queueRequestEvent.take();
					if (group != null) {
						group.sendRequestEvent(evt);
					}
				} catch (InterruptedException e) {
					log.warn("error", e);
					thread.interrupt();
				}
			}
		}
	}

	/**
	 * Hilo que se encarga de enviar los ResponseEvent
	 */
	private static class DispacherResponseEvent implements Runnable {

		@Override
		public void run() {
			Thread thread = Thread.currentThread();
			while (!thread.isInterrupted()) {
				try {
					MyResponseEvent evt = queueResponseEvent.take();
					if (group != null) {
						group.sendResponseEvent(evt);
					}
				} catch (InterruptedException e) {
					log.warn("error", e);
					thread.interrupt();
				}
			}
		}
	}

	/**
	 * Hilo que se encarga de enviar los TimeoutEvent
	 */
	private static class DispacherTimeoutEvent implements Runnable {

		@Override
		public void run() {
			Thread thread = Thread.currentThread();
			while (!thread.isInterrupted()) {
				try {
					MyTimeoutEvent evt = queueTimeoutEvent.take();
					if (group != null) {
						group.sendTimeoutEvent(evt);
					}
				} catch (InterruptedException e) {
					log.warn("error", e);
					thread.interrupt();
				}
			}
		}
	}
	
	/**
	 * Hilo que se encarga de enviar los DialogTerminatedEvent
	 */
	private static class DispacherDialogTerminatedEvent implements Runnable {

		@Override
		public void run() {
			Thread thread = Thread.currentThread();
			while (!thread.isInterrupted()) {
				try {
					MyDialogTerminatedEvent evt = queueDialogTerminatedEvent.take();
					if (group != null) {
						group.sendDialogTerminatedEvent(evt);
					}
				} catch (InterruptedException e) {
					log.warn("error", e);
					thread.interrupt();
				}
			}
		}
	}

	
	/**
	 * Hilo que se encarga de enviar los TransactionTerminatedEvent
	 */
	private static class DispacherTransactionTerminatedEvent implements Runnable {

		@Override
		public void run() {
			Thread thread = Thread.currentThread();
			while (!thread.isInterrupted()) {
				try {
					MyTransactionTerminatedEvent evt = queueTransactionTerminatedEvent.take();
					if (group != null) {
						group.sendTransactionTerminatedEvent(evt);
					}
				} catch (InterruptedException e) {
					log.warn("error", e);
					thread.interrupt();
				}
			}
		}
	}
	
}
