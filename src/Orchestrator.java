import java.io.*;
import java.net.*;


/**
 * @author apatri
 *
 */

public class Orchestrator {

	private ServerSocket ss;
	String serverAddress= "localhost:8460";
	
	//-------------------Constructor Declaration----------//
	/**
	 * @param host
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Orchestrator(String host, int port) throws UnknownHostException, IOException { 
		this.ss= new ServerSocket(port, 0, InetAddress.getByName(host));
	}
	
	//-----Start communication with Consumer and send the Provider IP address and Port--------//
	 /**
	 * @throws Exception
	 */
	private void listen() throws Exception {
	        String serviceReq = null;
	        Socket client = this.ss.accept();
	        String clientAddress = client.getInetAddress().getHostAddress();
	        System.out.println("\r\nNew connection from " + clientAddress);
	        	
	        DataInputStream in= new DataInputStream(client.getInputStream());
	        DataOutputStream out= new DataOutputStream(client.getOutputStream());
	        serviceReq=in.readUTF();
	        while ( serviceReq != null && serviceReq.equalsIgnoreCase("randomNumberGenerator")) {
	            System.out.println("\r\nMessage from Client: " + clientAddress + ": " + serviceReq);
	             out.writeUTF(serverAddress);
	             out.flush();
	             break;
	        	
	        }
	 	}
	     //---------Fetching host address--------------//	
	    /**
	     * @return host address
	     */
	    public InetAddress getSocketAddress() {
	        return this.ss.getInetAddress();
	     }
	    
	  //---------Fetching port--------------//
	    /**
	     * @return port number
	     */
	    public int getPort() {
	        return this.ss.getLocalPort();
	    	
	    }
	    
	public static void main(String[] args) throws Exception{
		 Orchestrator or= new Orchestrator("localhost", 8440);
		 System.out.println("\r\nRunning Orchestrator: "+"Host: "+or.getSocketAddress().getHostAddress()
				 +"Port: "+or.getPort());
		 or.listen();
	 }
}
