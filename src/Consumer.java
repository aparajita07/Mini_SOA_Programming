import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 * @author apatri
 *
 */
public class Consumer {
	
	private Socket socket;
	Scanner sc= new Scanner(System.in);
	
	//-------------------Constructor Declaration-------------//
   /**
     * @param serverAddress
     * @param serverPort
     * @throws Exception
     */
    private Consumer(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
    }
    
    //---------fetch the Server IP and port from Orchestrator---------//
    /**
     * @return provider endPoints
     * @throws IOException
     * @throws InterruptedException
     */
    private String fetchServerAddress() throws IOException, InterruptedException{

    	System.out.println("Enter the service request");
    	String serviceReq= sc.nextLine();
		DataOutputStream out= new DataOutputStream(socket.getOutputStream());
        DataInputStream in= new DataInputStream(socket.getInputStream());
    	while(true) {
    		try {
            out.writeUTF(serviceReq);
            out.flush();
            String inputFromOrch= in.readUTF();
            System.out.println("\r\nServer Adress: "+inputFromOrch);
            return inputFromOrch;
    		}catch(Exception e) {
    			System.out.println("Orchestrator did not recognize the serviceRequest.");
    			 return null;
    		}
    	}
    }
   
    //-----Start connecting with Provider and request for random number generation------//
    
    /**
     * @throws IOException
     * @throws InterruptedException
     */
    private void start() throws IOException, InterruptedException {
        String input;
        while (true) {
        	DataOutputStream out= new DataOutputStream(socket.getOutputStream());
            DataInputStream in= new DataInputStream(socket.getInputStream());
            input= "Generate Random Number";
            Thread.sleep(1000);
            out.writeUTF(input);
            out.flush();
            System.out.println("\r\nNumber: " + in.read()+" , timestamp: "+java.util.Calendar.getInstance().getTime());
        	
        }
        
    }
    
    
    public static void main(String[] args) throws Exception {
        Consumer clientToOrchestrator= new Consumer(InetAddress.getByName("localhost"),8440);
        System.out.println("\r\nConnected to Orchestrator: " + "Host: "+clientToOrchestrator.socket.getInetAddress()
                                   +" port: "+clientToOrchestrator.socket.getPort());
        
        String ServerAddress= clientToOrchestrator.fetchServerAddress(); 
        String[] address= ServerAddress.split(":");
        String ProviderHost= address[0];
        int ProviderPort= Integer.parseInt(address[1]);
        
        Consumer clientToServer = new Consumer(InetAddress.getByName(ProviderHost),ProviderPort);
        System.out.println("\r\nConnected to Server: " + clientToServer.socket.getInetAddress().getHostAddress()
        								+": "+clientToServer.socket.getPort());
        clientToServer.start();       
    }

}
