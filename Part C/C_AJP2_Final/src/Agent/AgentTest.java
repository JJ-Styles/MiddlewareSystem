//
//package Agent;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.util.Scanner;
//import java.util.regex.Pattern;
//
//public class AgentTest
//{
//    private static final Pattern IPV4PATTERN = Pattern.compile(
//        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
//    
//    private static final Pattern PORTPATTERN = Pattern.compile("(\\d{4})");
//    
//    private static AgentDetails sender;
//    
//    public static void main(String[] args) throws IOException
//    {
//        System.out.println("Enter AgentName:");
//        String ClientName = getInput();
//        String ipv4Address = "152.105.67.125";
//        int port = 8888; //getPort();
//        
//        InetAddress ip = InetAddress.getByName(ipv4Address);
//        
//        Agent agent = new Agent(ClientName, ip, port);
//        
//        sender = new AgentDetails(ClientName);
//        
//        loop : do
//        {
//            System.out.println("> ");
//            System.out.println("Options:");
//            System.out.println("1. Send message to someone");
//            System.out.println("0. exit");
//            System.out.println("> ");
//            final String option = getInput();
//
//            switch (option)
//            {
//                case "1":
//                    sendMessage(agent, ClientName);
//                    break;
//                case "0":
//                    break loop;
//                default:
//                    System.err.println("Invalid option.");
//            }
//        }
//        while(agent.isRunning());
//        
//        agent.stop();
//        
//    }
//    
//    private static String getIp()
//    {
//        System.out.println("What is the IP address of the peer to connect to?");
//        String ipAddress = getInput();
//        while(validateIp(ipAddress) != true)
//        {
//            System.out.println("Please enter a valid ip address");
//            ipAddress = getInput();
//        }
//        return ipAddress;
//    }
//    
//    private static int getPort()
//    {
//        System.out.println("What is the Port number of the server your connecting to?");
//        String portNumber = getInput();
//        while(validatePort(portNumber) != true)
//        {
//            System.out.println("Please enter a valid port number");
//            portNumber = getInput();
//        }
//        return Integer.parseInt(portNumber);
//    }
//    
//    public static boolean validateIp(final String ip)
//    {
//        return IPV4PATTERN.matcher(ip).matches();
//    }
//    
//    public static boolean validatePort(final String port)
//    {
//        return PORTPATTERN.matcher(port).matches();
//    }
//    
//    private static String getInput()
//    {
//        Scanner sc = new Scanner(System.in);
//        return sc.nextLine();
//    }
//
//    private static void sendMessage(Agent agent,String name)
//    {
//        System.out.println("Who would you like to send a message to?");
//        final String peerHandle = getInput();
//
//        System.out.println("What message would you like to send to " + peerHandle + "?");
//        String content = getInput();
//        
//        agent.sendMessage(peerHandle, content, new AgentDetails(name), sender);
//    }
//}