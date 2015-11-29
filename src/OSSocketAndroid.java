import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class OSSocketAndroid {
	
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static InputStreamReader inputStreamReader;
	private static BufferedReader bufferedReader;
	private static String message;
	
	private static DataInputStream in2;
	private static DataOutputStream out2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		getFilterIPAddresses();
		startServer();
	
	  }

	  public static String getIpAddress() {
	    InetAddress host;
	    String ipString = "false";

	    try {
	        host = InetAddress.getByName("nishant-Lenovo-G580");
	        ipString = host.getHostAddress();
	    } catch (UnknownHostException e) {
	        System.out.println(e);
	    }
	    return ipString;
	  }
	  

	  public static void getFilterIPAddresses()
	  {
		  Enumeration e;
		  try {
			  e = NetworkInterface.getNetworkInterfaces();
			  while(e.hasMoreElements()) {
				  NetworkInterface n = (NetworkInterface) e.nextElement();
				  Enumeration ee = n.getInetAddresses();
				  
				  while (ee.hasMoreElements()) {
					  InetAddress i = (InetAddress) ee.nextElement();
					  
					  if(i.getHostAddress().contains(".")) {
						  if(!i.getHostAddress().contains("127.0.")) {
							  System.out.println(i.getHostAddress());
						  }
					  }
				  }
			  }
		  } catch (SocketException e2) {
			// TODO Auto-generated catch block
				e2.printStackTrace();
		  }
	  }
	  
	  
	  //Start server
	  public static void startServer()
	  {
		  try {
		        serverSocket = new ServerSocket(4444);  //Server socket
		        

		    } catch (IOException e) {
		        System.out.println("Could not listen on port: 4444");
		    }

		    System.out.println("Server started. Listening to the port 4444");

		    while (true) {
		        try {
		        	
		            clientSocket = serverSocket.accept();   //accept the client connection
		            
                      // sending to client (pwrite object)
		            OutputStream ostream = clientSocket.getOutputStream(); 
		            PrintWriter pwrite = new PrintWriter(ostream, true);
					
					  // receiving from server ( receiveRead  object)
					  InputStream istream = clientSocket.getInputStream();
					  BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
					
					  String receiveMessage;
					  String sendMessage = "This is from PC by Nishant";
					  
					// The name of the file to open.
				     String fileName = "/home/nishant/Documents/OSfilesendTest.txt";
				     String line2 = null;
					  
					  // FileReader reads text files in the default encoding.
			            FileReader fileReader = 
			                new FileReader(fileName);

			            // Always wrap FileReader in BufferedReader.
			            BufferedReader bufferedReader = 
			                new BufferedReader(fileReader);
			            
			            String line3 = null;

			            while((line2 = bufferedReader.readLine()) != null) {
						  //line3 = line3 + line2;
						  pwrite.println(line2);             
						  pwrite.flush();
						  System.out.println(line2);
			            } 	
			            // Always close files.
			            bufferedReader.close();  
			            pwrite.println(line3);             
						pwrite.flush();
					  
					  while(true)
					  {
					    if((receiveMessage = receiveRead.readLine()) != null)  
					    {
					       System.out.println(receiveMessage);         
					    }         
					   
					  }               
							            
		            
		            
		            
		            //inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
		            //bufferedReader = new BufferedReader(inputStreamReader); //get client msg                    
		            //message = bufferedReader.readLine();
		            //System.out.println(message);
		            //inputStreamReader.close();
		            
		            //String FromAndroid = "server says: "+in2.readUTF();
	                //System.out.println(FromAndroid);
		            
		            //String s= "This is PC ";
	                //out2.writeUTF(s);
	                
	                
		           
		        } catch (IOException ex) {
		            System.out.println("Problem in message reading");
		        }
		        finally {
		        	 try {
						clientSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		        }
		    }
	  }
	  
}
