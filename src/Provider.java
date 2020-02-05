import java.io.*;
import java.net.*;
import java.util.Random;
public class Provider {
	private ServerSocket provider;
	
	 //-------------------Constructor Declaration------------//  
	/**
	 * @param port
	 * @param Host
	 * @throws Exception
	 */
	public Provider(int port,InetAddress Host) throws Exception {
          this.provider = new ServerSocket(port,0, Host);// 
    }
	
	//Start communication with Consumer---//
	 /**
	 * @throws Exception
	 */
	private void listen() throws Exception {
	        String data = null;
	        Socket client = this.provider.accept();
	        String clientAddress = client.getInetAddress().getHostAddress();
	        System.out.println("\r\nNew connection from client: " + clientAddress);
	        DataOutputStream out= new DataOutputStream(client.getOutputStream());
            DataInputStream in= new DataInputStream(client.getInputStream());
            data= in.readUTF();
	   		Random RNum= new Random();
	   		int number=0;
	   		System.out.println("\r\nMessage from client" + clientAddress + ": " + data);
	        while ( data != null && data.equalsIgnoreCase("Generate Random Number")) {
	        	try {
		   		 number= 1+RNum.nextInt(100);
		   		 out.write(number);
		   		 out.flush();
	        	}catch(Exception e) {
	        		System.out.println("End of Random Number Generation");
	        		break;
	        	}
	        }
	    }
	
	     //---------Fetching host address--------------//
	     /**
          * @return host address
          */
	    public InetAddress getSocketAddress() {
	        return this.provider.getInetAddress();
	    }
	    
	    //---------Fetching Port--------------//
	    /**
	     * @return port number
	     */
	    public int getPort() {
	        return this.provider.getLocalPort();
	    	
	    }
	    public static void main(String[] args) throws Exception {
	        Provider app = new Provider(8460,InetAddress.getByName("localhost"));
	        System.out.println("\r\nRunning Server: " + 
	                "Host=" + app.getSocketAddress().getHostAddress() + 
	                " Port=" + app.getPort());
	        
	        app.listen();
	    }

}
