//Package name the class belongs to
package Message;

//imports used
import Agent.AgentDetails;

/**
 * Error. <br>
 *
 * Date: 09/01/2019
 *
 * @author Andrew McGouran - T7068437
 * @author Camron Ali - S6301166
 * @author Matthew Marr - T7037477
 * @author Jason Flower - T7047098
 */
public class Error extends Message
{

    private final String body;

    /**
     * Default constructor thats takes three parameters. <br>
     *
     * @param destination the client that will receive the message
     * @param sender the client who's sending the message
     * @param body the content of the message thats being sent
     */
    public Error(String destination, AgentDetails sender, String body)
    {
        super(destination, sender);
        this.body = body;
    }

    /**
     * GetBody. <br>
     *
     * @return the content of the message as a String
     */
    public String getBody()
    {
        return body;
    }

}
