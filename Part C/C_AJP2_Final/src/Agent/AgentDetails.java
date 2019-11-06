//the package the class belongs to
package Agent;

//imports used
import java.io.Serializable;

/**
 * AgentDetails. <br>
 *
 * Date: 09/01/2019
 *
 * @author Andrew McGouran - T7068437
 * @author Camron Ali - S6301166
 * @author Matthew Marr - T7037477
 * @author Jason Flower - T7047098
 */
public class AgentDetails implements Serializable
{
    private final String agentName;

    /**
     * Default constructor thats takes 1 parameters. <br>
     * 
     * @param agentName the name of the agent
     */
    public AgentDetails(String agentName)
    {
        this.agentName = agentName;
    }

    /**
     * 
     * @return the agentsName as a String
     */
    public String getAgentName()
    {
        return agentName;
    }
}
