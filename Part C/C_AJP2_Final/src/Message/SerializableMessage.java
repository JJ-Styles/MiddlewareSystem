//package the class belongs to.
package Message;

//imports used
import Agent.AgentDetails;
import java.io.Serializable;

/**
 * SerializableMessage. <br>
 *
 * Date: 09/01/2019
 *
 * @author Andrew McGouran - T7068437
 * @author Camron Ali - S6301166
 * @author Matthew Marr - T7037477
 * @author Jason Flower - T7047098
 */
public class SerializableMessage extends Message
{

    private final Serializable body;

    /**
     * Default constructor thats takes three parameters. <br>
     *
     * @param destination the client that will receive the message
     * @param sender the client who's sending the message
     * @param body the content thats being sent, serialised in order to send
     * through networks
     */
    public SerializableMessage(String destination, AgentDetails sender, Serializable body)
    {
        super(destination, sender);
        this.body = body;
    }

    /**
     * GetBody. <br>
     *
     * @return the content of the message as a serialisable object
     */
    public Serializable getBody()
    {
        return body;
    }

}
