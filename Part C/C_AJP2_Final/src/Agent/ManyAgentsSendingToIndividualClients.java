/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agent;

import Message.Client;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author t7047098
 */
public class ManyAgentsSendingToIndividualClients 
{
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException 
    {
        ArrayList<Agent> agentlist = new ArrayList<>();
        String ipv4Address = "152.105.67.119";
        int port = 8888; //getPort();
        
        InetAddress ip = InetAddress.getByName(ipv4Address);
        
        for(int i = 0; i< 500; i++)
        {
            agentlist.add(new Agent("Agent" + i, ip, port));
        }
        
        TimeUnit.SECONDS.sleep(5);
        
        for(int i = 0; i< 500; i++)
        {
            for (int j = 0; j < 500; j++) 
            {
                agentlist.get(i).sendMessage(new Client("Agent" + j ,new AgentDetails("Agent" + i),"test"));
            }
        }
        
        TimeUnit.SECONDS.sleep(5);
        
        for (int i = 0; i < 500; i++) 
        {
           agentlist.get(i).stop();
        }
        
        TimeUnit.SECONDS.sleep(5);
        
        System.out.println("Test Finished");
        
        
    }
}
