//the package the class belongs to
package Portal;

//imports used
import Message.Message;
import Message.Error;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Portal. <br>
 *
 * Date: 09/01/2019
 *
 * @author Andrew McGouran - T7068437
 * @author Camron Ali - S6301166
 * @author Matthew Marr - T7037477
 * @author Jason Flower - T7047098
 */
public final class Portal {

    private final int PortalPort;
    private final HashMap<String, MetaAgent> connections = new HashMap<>();
    private final BlockingQueue<Message> messagesToSend = new LinkedBlockingQueue<>();
    private final Thread messageQueueThread;
    private final RunningLoop runningLoop;
    private final AgentListener connectionListener = new Listener();
    private boolean running;
    private AgentList<String> userList = new AgentList<>();

    /**
     * Default constructor thats takes one parameters. <br>
     * 
     * @param PortalPort the port number this portal will be running on
     * @throws IOException
     */
    public Portal(int PortalPort) throws IOException {
        System.out.println("...Starting Portal");
        this.PortalPort = PortalPort;

        running = true;

        MessageQueueLoop messageQueueLoop = new MessageQueueLoop();
        messageQueueThread = new Thread(messageQueueLoop, "Message queue Loop");
        messageQueueThread.start();

        runningLoop = new RunningLoop();
        Thread connectionThread = new Thread(runningLoop, "Running Loop");
        connectionThread.start();
        System.out.println("Portal has Started...");
    }

//    public void sendMessage(String destination, Serializable body)
//    {
//        Message message = new Message(destination, body);
//        messagesToSend.add(message);
//    }
    private void processMessage(Message message) {
        if (message.getDestination().equals("ALL")) {
            for (MetaAgent agentConnection : connections.values()) {
                agentConnection.addMessage(message);
            }
        } else {
            MetaAgent connection = connections.get(message.getDestination());

            if (connection == null || message.getClass().getSimpleName().toLowerCase().equals("error")) {
                if (message.getClass().getSimpleName().toLowerCase().equals("error")) {
                    Error errorMessage = (Error) message;

                    ErrorMessage(errorMessage);
                } else {
                    System.out.println("Message for unknown agent '" + message.getDestination() + " of type: " + message.getClass().getSimpleName());
                }
            } else {
                connection.addMessage(message);
                System.out.println("message added to meta-agent[" + connection.getClientName() + "]");
            }
        }
    }

    private void ErrorMessage(Error m) {
        System.out.println(m.getBody() + " has Occured (Error 404)");
    }

    /**
     * Stops the portal from running, closing all connections
     */
    public void stop() {
        if (running) {
            System.out.println("...Stopping Portal");
            running = false;
            runningLoop.stop();
            try {
                messageQueueThread.join();
            } catch (InterruptedException e) {
                //couldn't join connection thread?
            }
            for (MetaAgent connection : new HashSet<>(connections.values())) {
                connection.stop();
            }

        }
    }

    private void updateClients() {
        //connections.values().forEach((agentConnection)
                //-> {
            try {
                //agentConnection.setClients(userList);
            } catch (Exception ex) {
                Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
            }
        //});
    }

//    private void fillUserList()
//    {
//        userList.clear();
//        for (Iterator it = connections.keySet().iterator(); it.hasNext();)
//        {
//            Object key = it.next();
//            userList.add((String)key);
//        }
//    }
    private class RunningLoop implements Runnable {

        private final ServerSocket socket;

        public RunningLoop() throws IOException {
            socket = new ServerSocket(PortalPort);
        }

        @Override
        public void run() {
            System.out.println("Connection thread has started...");

            try {
                while (running) {
                    Socket newSocket = socket.accept();
                    System.out.println("New connection on " + newSocket.getPort());

                    MetaAgent connection = new MetaAgent(newSocket, userList);
                    try {
                        String clientName = connection.run(connectionListener);
                        if (clientName != null) {
                            connections.put(clientName, connection);
                            //userList.add(clientName);
                            //userList.printAgentList();
                            //updateClients();
                        } else {
                            // connection.stop();
                            newSocket.close();
                        }
                    } catch (Exception e) {
                        System.out.println("Connection could not be started");
                        connection.stop();
                    }
                }
                socket.close();
            } catch (IOException e) {
                if (running) {
                    stop();
                    System.out.println("...Connection thread has stopped");
                }
            }
        }

        public void stop() {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Connection socket could not be closed");
            }
        }

    }

    private class MessageQueueLoop implements Runnable {

        @Override
        public void run() {
            try {
                while (running) {
                    Message message = messagesToSend.poll(1000, TimeUnit.MILLISECONDS);
                    if (message != null) {
                        processMessage(message);
                    }
                }
            } catch (InterruptedException e) {
                if (running) {
                    //Message queue loop interrupted
                    stop();
                }
            }
        }
    }

    private class Listener implements AgentListener {

        @Override
        public void messageReceived(Message message) {
            messagesToSend.add(message);
        }

        @Override
        public void connectionDropped(String agentName, int port) {
            System.out.println("connection to " + agentName + " " + port + " dropped");

            if (agentName != null) {
                if (connections.containsKey(agentName)) {
                    connections.remove(agentName);
                    userList.remove(agentName);
                    updateClients();
                }
            }
        }
    }
}
