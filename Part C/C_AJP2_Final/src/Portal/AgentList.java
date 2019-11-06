//the package the class belongs to
package Portal;

//imports used
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * AgentList. <br>
 *
 * Date: 09/01/2019
 *
 * @author Andrew McGouran - T7068437
 * @author Camron Ali - S6301166
 * @author Matthew Marr - T7037477
 * @author Jason Flower - T7047098
 * @param <T> Object inputted type
 */
public class AgentList<T> extends ArrayList
{    

    /**
     *  Prints a list of Agents
     */
    public void printAgentList()
    {
        System.out.print("Connected users: ");
        
        Object a = getLastItem();
        this.forEach((item) ->
        {
            System.out.print(item + (item.equals(a) ? " ": ", "));
        });
        System.out.println();
    }
    
    /**
     * 
     * @param listIn the list to clone
     * @return a data clone of the list inputted
     */
    private ArrayList deepCopy(ArrayList listIn)
    {
        ArrayList clone = new ArrayList();
        
        for (int i = 0; i < listIn.size(); i++) 
        {
            clone.add(listIn.get(i));
        }
        
        return clone;
    }
    
    /**
     *
     * @param name the agent that is receiving the list
     * @return a String containing all the agents
     */
    public String getFilteredList(String name)
    {
        AgentList filtered = (AgentList) deepCopy(this);
        
        filtered.remove(name);
        String str = "Connected users: ";
        
        for (Object item : filtered)
        {
            if (!item.equals(filtered.getLastItem()))
            {
               str += item + ", ";
            }
            else
            {
                str += (String)item + " ";
            }
        }
        return str;
    }
    
    private Object getLastItem()
    {
        return this.get(this.size() - 1);
    }
}
