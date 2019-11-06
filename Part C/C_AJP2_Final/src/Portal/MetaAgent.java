//the package the class belongs to
package Portal;

//imports used
import Agent.AgentDetails;
import Message.Client;
import Message.Message;
import Message.SerializableMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * MetaAgent. <br>
 *
 * Date: 09/01/2019
 *
 * @author Andrew McGouran - T7068437
 * @author Camron Ali - S6301166
 * @author Matthew Marr - T7037477
 * @author Jason Flower - T7047098
 */
public class MetaAgent
{

    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    private final Socket socket;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    private String clientName;
    private boolean running;
    private AgentListener connectionListener;
    private ArrayList<String> clients;

    /**
     * Default constructor thats takes two parameters. <br>
     * 
     * @param socket the socket the agent is connecting through
     * @param clients a list of agents connected
     */
    public MetaAgent(Socket socket,ArrayList<String> clients)
    {
        this.socket = socket;
        this.clients = clients;
    }

    /**
     *
     * @param connectionListener monitors the connection
     * @return the agents details and starts the threads if its valid
     * @throws Exception
     */
    public final String run(AgentListener connectionListener) throws Exception
    {
        assert !running;

        inStream = new ObjectInputStream(socket.getInputStream());
        outStream = new ObjectOutputStream(socket.getOutputStream());

        SerializableMessage message = (SerializableMessage) inStream.readObject();

        if (!message.getDestination().equals("Portal"))
        {
            throw new Exception("First message was not the agent details");
        }

        AgentDetails clientDetails = (AgentDetails) message.getBody();

        if (!clients.contains(clientDetails.getAgentName()))
        {
            this.connectionListener = connectionListener;
            running = true;

            clientName = clientDetails.getAgentName();

            new Thread(new InLoop(), "In Loop").start();
            new Thread(new OutLoop(), "Out Loop").start();

            System.out.println("meta-agent for [" + clientName + "] created successfully");
            outStream.writeObject(new Client("",null, "Welcome to the client application " + clientName));
            return clientName;
        }
        else
        {
            System.out.println("meta-agent unsucessfully created [name already exists]");
            outStream.writeObject(new Client("",null, "Sorry a client already exists with that name"));
            return null;
        }
    }

    /**
     *  Handles the messages in and out of the portal by adding them to a blocking queue
     * @param message the message that has been received or that is being delivered
     */
    public final void addMessage(Message message)
    {
        messages.add(message);
    }

    /**
     *  Stops the meta agent and closes the connection
     */
    public final void stop()
    {
        running = false;

        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            //error closing socket?
        }

        connectionListener.connectionDropped(clientName, socket.getPort());
    }

    private class InLoop implements Runnable
    {

        @Override
        public void run()
        {
            try
            {
                while (running)
                {
                    try
                    {
                        Message message = (Message) inStream.readObject();
                        connectionListener.messageReceived(message);
                    }
                    catch (ClassNotFoundException e)
                    {
                        //could not read an object
                    }
                }
            }
            catch (IOException e)
            {
                stop();
            }
        }
    }

    private class OutLoop implements Runnable
    {

        @Override
        public void run()
        {
            try
            {
                while (running)
                {
                    Message message = messages.poll(1000, TimeUnit.MILLISECONDS);
                    if (message != null)
                    {
                        outStream.reset();
                        outStream.writeObject(message);
                    }
                }
            }
            catch (IOException | InterruptedException e)
            {
                stop();
            }
        }
    }

    /**
     *
     * @return the agents name
     */
    public String getClientName()
    {
        return clientName;
    }

    /**
     *  Outputs a connected agent list without the agent that requires the list
     * @param clients List of all agents connected
     * @throws IOException
     */
    public void setClients(AgentList<String> clients) throws IOException
    {
        this.clients = clients;
        
        String filteredList = clients.getFilteredList(clientName);
        if (!filteredList.equals("Connected users: "))
        {
            //Message clientList = new Message("", filteredList);
            //outStream.writeObject(clientList);
        }
    }
    
}
