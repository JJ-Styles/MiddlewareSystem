//package the class belongs to
package Message;

//imports used
import Agent.AgentDetails;

/**
 * Diagnostic. <br>
 *
 * Date: 09/01/2019
 *
 * @author Andrew McGouran - T7068437
 * @author Camron Ali - S6301166
 * @author Matthew Marr - T7037477
 * @author Jason Flower - T7047098
 */
public class Diagnostic extends Message
{

    private final Object body;

    /**
     * Default constructor thats takes three parameters. <br>
     *
     * @param destination the client that will receive the message
     * @param sender the client who's sending the message
     * @param body the content of the message thats being sent
     */
    public Diagnostic(String destination, AgentDetails sender, Object body)
    {
        super(destination, sender);
        this.body = body;
    }

    /**
     * GetBody. <br>
     *
     * @return the content of the message as an object
     */
    public Object getBody()
    {
        return body;
    }
}
