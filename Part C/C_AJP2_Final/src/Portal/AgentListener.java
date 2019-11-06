//the package the class belongs to
package Portal;

//imports used
import Message.Message;

/**
 * AgentListener. <br>
 *
 * Date: 09/01/2019
 *
 * @author Andrew McGouran - T7068437
 * @author Camron Ali - S6301166
 * @author Matthew Marr - T7037477
 * @author Jason Flower - T7047098
 */
public interface AgentListener
{

    /**
     *
     * @param message the message received by the agent
     */
    void messageReceived(Message message);

    /**
     *
     * @param clientName the name of the agent dropped
     * @param port the port that the agent is accessing from
     */
    void connectionDropped(String clientName, int port);
}
