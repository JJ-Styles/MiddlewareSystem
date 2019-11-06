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
public class ManyAgentsSending 
{
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException 
    {
        ArrayList<Agent> agentlist = new ArrayList<>();
        String ipv4Address = "152.105.67.167";
        int port = 8888; //getPort();
        
        InetAddress ip = InetAddress.getByName(ipv4Address);
        
        for(int i = 1270; i< 1905; i++)
        {
            agentlist.add(new Agent("Agent" + i, ip, port));
        }
        
        TimeUnit.SECONDS.sleep(5);
        
        for(int i = 0; i< 635; i++)
        {
            agentlist.get(i).sendMessage(new Client("ALL",new AgentDetails("Agent" + (1270 + i)),"test"));
        }
    }
}
