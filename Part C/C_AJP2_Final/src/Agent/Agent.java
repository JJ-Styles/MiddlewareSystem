//the package the class belongs to
package Agent;

//imports used
import Message.Client;
import Message.Diagnostic;
import Message.Message;
import Message.MessageFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Agent. <br>
 *
 * Date: 09/01/2019
 *
 * @author Andrew McGouran - T7068437
 * @author Cameron Ali - S6301166
 * @author Matthew Marr - T7037477
 * @author Jason Flower - T7047098
 */
public final class Agent
{

    private final ObjectInputStream inStream;
    private final ObjectOutputStream outStream;
    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    private final Socket socket;
    private final Thread inThread;
    private final Thread outThread;
    private boolean running;
    private final MessageFactory mf = new MessageFactory();

    /**
     *  Default constructor thats takes three parameters. <br>
     *  Creates and starts the threads used for sending and receiving messages
     * 
     * @param clientName the name of the agent
     * @param serverIP the IP address of the server the agent is connecting to
     * @param serverPort the port number of the server the agent is connecting to
     * @throws IOException
     */
    public Agent(String clientName, InetAddress serverIP, int serverPort) throws IOException
    {
        System.out.println("Starting Agent...");

        socket = new Socket(serverIP, serverPort);
        outStream = new ObjectOutputStream(socket.getOutputStream());
        inStream = new ObjectInputStream(socket.getInputStream());

        running = true;
        
        sendMessage(mf.createMessage("serializable", new AgentDetails(clientName), null, "Portal"));

        inThread = new Thread(new InLoop(), "In Loop");
        outThread = new Thread(new OutLoop(), "Out Loop");
        inThread.start();
        outThread.start();
        System.out.println("...Agent Started");
    }

    /**
     *  Sends a message
     * @param m is the message that will be sent
     */
    public final void sendMessage(Message m)
    {
        messages.add(m);
    }

    /**
     *  Processes a message
     * @param body is the message that will be processed
     */
    private void processMessage(Message body)
    {
        if (body.getClass().getSimpleName().toLowerCase().equals("client"))
        {
            Client clientMessage = (Client) body;
            
            if (body.getDestination() != null)
            {
                if(body.getSender() != null)
                    System.out.println(clientMessage.getBody() + " from " + clientMessage.getSender());
                else
                    System.out.println(clientMessage.getBody());
            }
            else
            {
                System.out.println(clientMessage.getBody());
            }
        }
        else if (body.getClass().getSimpleName().toLowerCase().equals("diagnostic"))
        {
            Diagnostic diagnosticMessage = (Diagnostic) body;
            
            System.out.println("Please Send diagnostic Engineer");
        }
    }

    /**
     *  Stops the agent from working
     */
    public void stop()
    {
        if (running)
        {
            System.out.println("Stopping Agent");
            running = false;

            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                System.out.println("could not be closed");
            }

            try
            {
                if (Thread.currentThread() != inThread)
                {
                    inThread.join();
                }
            }
            catch (InterruptedException e)
            {
                //input thread problem...
            }

            try
            {
                if (Thread.currentThread() != outThread)
                {
                    outThread.join();
                }
            }
            catch (InterruptedException e)
            {
                //output thread problem
            }

            System.out.println("Agent stopped");
        }
    }
      
    /**
     *  Handles messages being received by the agent
     */
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
                        processMessage(message);
                    }
                    catch (ClassNotFoundException e)
                    {
                        System.out.println("Could not read message");
                    }
                }
            }
            catch (IOException e)
            {
                if (running)
                {
                    System.out.println("In stream closed");
                    stop();
                }
            }
            System.out.println("Input loop is stopped");
        }
    }

    
    /**
     *  Handles messages being sent from the agent
     */
    private class OutLoop implements Runnable
    {

        @Override
        public void run()
        {
            System.out.println("Output loop is running");
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
            catch (IOException e)
            {
                if (running)
                {
                    System.out.println("Output stream closed");
                    stop();
                }
            }
            catch (InterruptedException e)
            {
                if (running)
                {
                    System.out.println("Out loop interrupted");
                    stop();
                }
            }
            System.out.println("Output loop is stopped");
        }
    }

    /**
     *
     * @return whether the agent is alive or not
     */
    public boolean isRunning()
    {
        return running;
    }

}
