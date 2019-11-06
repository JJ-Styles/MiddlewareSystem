//the package the class belongs to
package Portal;

//imports used
import java.io.IOException;
import java.util.Scanner;

/**
 * PortalTest. <br>
 *
 * Date: 09/01/2019
 *
 * @author Andrew McGouran - T7068437
 * @author Camron Ali - S6301166
 * @author Matthew Marr - T7037477
 * @author Jason Flower - T7047098
 */
public class PortalTest
{

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        int port = 8888;
        Portal testPortal = new Portal(port);
        
        
        System.out.println("Type exit to stop the portal");
        loop : while(true)
        {
            final String option = getInput();
                
                switch(option) 
                {
                    case "exit":
                        break loop;
                    default:
                        System.err.println("Invalid command.");
                }
        }
        testPortal.stop();
    }
    
    private static String getInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
