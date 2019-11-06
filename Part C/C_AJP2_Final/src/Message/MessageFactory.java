//package this class belongs to
package Message;

//imports used
import Agent.AgentDetails;
import java.io.Serializable;

/**
 * MessageFactory. <br>
 *
 * Date: 09/01/2019
 *
 * @author Andrew McGouran - T7068437
 * @author Camron Ali - S6301166
 * @author Matthew Marr - T7037477
 * @author Jason Flower - T7047098
 */
public class MessageFactory
{

    /**
     *
     * CreateMessage - creates a message based on the users selection. <br>
     *
     * @param messageType type of message the client wants to use
     * @param body the content thats being sent
     * @param sender the client thats sending the message
     * @param destination the client that will receive the message
     * @return selected message
     */
    public Message createMessage(String messageType, Object body, AgentDetails sender, String destination)
    {
        switch (messageType.toUpperCase())
        {
            case "CLIENT":
                return new Client(destination, sender, (String) body);
            case "ERROR":
                return new Error(destination, sender, (String) body);
            case "DIAGNOSTIC":
                return new Diagnostic(destination, sender, body);
            case "SERIALIZABLE":
                return new SerializableMessage(destination, sender, (Serializable) body);
            default:
                return null;
        }
    }
}
