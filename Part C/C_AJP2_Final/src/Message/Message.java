//package this abstract class belongs to
package Message;

//imports used
import Agent.AgentDetails;
import java.io.Serializable;

/**
 * Message abstract class. <br>
 *
 * Date: 09/01/2019
 *
 * @author Andrew McGouran - T7068437
 * @author Camron Ali - S6301166
 * @author Matthew Marr - T7037477
 * @author Jason Flower - T7047098
 */
public abstract class Message implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final String destination;
    private final AgentDetails sender;

    /**
     * Default constructor that takes one parameter. <br>
     * @param destination the destination of the message
     */
    public Message(String destination)
    {
        this.destination = destination;
        this.sender = null;
    }
    
    /**
     * Constructor that takes two parameter. <br>
     * @param destination the destination of the message
     * @param senderDetails details of the client sending the message
     */
    public Message(String destination, AgentDetails senderDetails)
    {
        this.destination = destination;
        this.sender = senderDetails;
    }

    /**
     * 
     * @return the destination of the message
     */
    public final String getDestination()
    {
        return destination;
    }


    /**
     *
     * @return the sender of the message
     */
    public String getSender() 
    {
        return sender != null ? sender.getAgentName() : null;
    }
}
