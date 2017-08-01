package com.jmdomingueza.telefonoip.common.messages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.jmdomingueza.telefonoip.common.messages.dummy.DummyMessage;
import com.jmdomingueza.telefonoip.common.messages.ihm.IhmMessage;
import com.jmdomingueza.telefonoip.common.messages.sip.SipMessage;


/**
 * Servicio que gestiona la creacion de grupos de Message, creacion y eliminacion 
 * de MessageListener y envio de Message a los MessageListener.
 * @author jmdomingueza
 *
 */
public class MessageService {

	/**
	 * Logger de la clase
	 */
	private static Logger log = Logger.getLogger(MessageService.class);

    /**
     * Grupo para mensajes del Provider
     */
    public static final String PROVIDER_GROUP_NAME = "provider_group";
     
    /**
     * Grupo para mensajes del Sip
     */
    public static final String SIP_GROUP_NAME = "sip_group";

    /**
     * Grupo para mensajes del Dummy
     */
    public static final String DUMMY_GROUP_NAME = "dummy_group";


    /**
     * Grupo para mensajes del IHM
     */
    public static final String IHM_GROUP_NAME = "ihm_group";


    /** 
     * Mapa con con todos los grupos definidos
     */
    private static Map<String, GroupManager<Message>> groups = new HashMap<>();

    /**
     * Cola con todos los mensajes por enviar
     */
    private static BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();

    /**
     * Hilo que se encarga de enviar los mensajes
     */
    private static Thread dispatcherThread = new Thread(new Dispacher(), "Message-Dispatcher");

    static {
    	addGroup(PROVIDER_GROUP_NAME, new GroupManager<ProviderMessage>());
    	addGroup(SIP_GROUP_NAME, new GroupManager<SipMessage>());
    	addGroup(DUMMY_GROUP_NAME, new GroupManager<DummyMessage>());
    	addGroup(IHM_GROUP_NAME, new GroupManager<IhmMessage>());
        dispatcherThread.start();
    }

    /**
     * Añade un grupo al Servicio
     *
     * @param groupName DOCUMENT ME!
     */
	public static void addGroup(String groupName, GroupManager<? extends Message> groupManager) {
        groups.put(groupName,  (GroupManager<Message>) groupManager);
    }

    /**
     * Añade un MessageListener al GroupManager al que pertenece para cunado se envie
     * un mensaje de es grupo llegue a ese MessageListener
     *
     * @param groupName Nombre del grupo
     * @param listener MessageListener que añade
     */
    public static void addMessageListener(String groupName,
    									  MessageListener<? extends Message> listener) {
    	addMessageListener( groupName, listener, false );
}

    /**
     * Añade un MessageListener al GroupManager al que pertenece para cunado se envie
     * un mensaje de es grupo llegue a ese MessageListener.
     *
     * @param groupName Nombre del grupo
     * @param listener MessageListener que añade
     * @param topPriority Si el MessageListener es de alta prioridad
     */
	public static void addMessageListener(String groupName,
                                          MessageListener<? extends Message> listener,
                                          boolean topPriority) {
        GroupManager<Message> group = groups.get(groupName);

        if (group != null) {
            group.addMessageListener((MessageListener<Message>) listener, topPriority );
        }
    }

    /**
     * Elimina el MessageListener del GroupManager para que ya no reciba mensajes 
     * del grupo
     *
     * @param groupName Nombre del grupo
     * @param listener MessageListener que se elimina
     */
	public static void removeMessageListener(String          groupName,
                                             MessageListener<? extends Message> listener) {
        GroupManager<Message> group = groups.get(groupName);

        if (group != null) {
            group.removeMessageListener((MessageListener<Message>) listener);
        }
    }

    /**
     * Envia un mensaje a la cola para que despues sea enviado en un hilo
     * @param message Mensaje que se envia
     */
    public static void sendMessage(Message message) {
        queue.add(message);
    }

    /**
     * CLase que gestiona los grupos 
     * @author jmdomingueza
     *
     * @param <T>
     */
    public static class GroupManager<T extends Message> {
       
    	/**
    	 * Lista con los MessageListener de un grupo
    	 */
        private List<MessageListener<T>> listeners = Collections.synchronizedList(new ArrayList<MessageListener<T>>());

        /**
         * Añade un MessageListener a la lista
         *
         * @param listener
         */
        public void addMessageListener(MessageListener<T> listener) {
            addMessageListener(listener, false);
        }

        /**
         * Añade un MessageListener a la lista con prioridad. Si topPriority es true
         * pone el MessageListener al principio de la lista
         *
         * @param listener
         * @param topPriority
         */
        public void addMessageListener(MessageListener<T> listener, boolean topPriority) {
            if( topPriority )
            	listeners.add(0, listener);
            else
            	listeners.add(listener);
        }

        /**
         * Borra un MessageListener de la lista
         *
         * @param listener DOCUMENT ME!
         */
        public void removeMessageListener(MessageListener<T> listener) {
            listeners.remove(listener);
        }

        /**
         * Envia un mensaje a todos los MessageListener de la lista
         *
         * @param message 
         */
        public void sendMessage(T message) {
        	List<MessageListener<T>> listenersCopy = new ArrayList<MessageListener<T>>(listeners);

            for (MessageListener<T> listener : listenersCopy) {
                listener.receiveMessage(message);
            }
        }
    }

    /**
     * Hilo que se encarga de enviar los Message
     */
    public static class Dispacher implements Runnable {
    	
    	@Override
		public void run() {
			Thread thread = Thread.currentThread();
			while(!thread.isInterrupted()) {
				try {
					Message message = queue.take();

					GroupManager<Message> group = groups.get(message.getGroup());
					if(message.isDispatchThread() && !SwingUtilities.isEventDispatchThread()){
						SwingUtilities.invokeLater(new Runnable() {
						    public void run() {
						    	if (group != null) {
									group.sendMessage(message);
								}
						    }
						});
					}else{
						if (group != null) {
							group.sendMessage(message);
						}
					}
						   
					
				} catch (InterruptedException e) {
					log.warn("error", e);
					thread.interrupt();
				}
			}
		}
    }
}
