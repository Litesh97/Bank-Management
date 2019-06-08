 
 import java.net.*;
import java.util.*;
import java.io.*;

class Server
{
	
	// Vector to store active clients
    public static Vector<ClientHandler> client_list = new Vector<>();
     
    // counter for clients
    static int i = 0;
    
  public static void main(String args[]) throws IOException
  {
    ServerSocket ss=new ServerSocket(5000);
    
    while(true)
    {
	    Socket s=ss.accept();
	    System.out.println("New client request received : " + s);
	    
	    DataInputStream din=new DataInputStream(s.getInputStream());
	    DataOutputStream dout=new DataOutputStream(s.getOutputStream());
	    //BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	    
	    System.out.println("Creating a new handler for this client...");
	    ClientHandler mtch = new ClientHandler(s,"client " + i, din, dout);
	    
	    // Create a new Thread with this object.
        Thread t = new Thread(mtch);
         
        System.out.println("Adding this client to active client list");

        // add this client to active clients list
        client_list.add(mtch);

        // start the thread.
        t.start();
        i++;
	    
//	    while(!str1.equals("stop"))
//	    {
//	      System.out.println("Waiting for clients reply...");
//	      str1=din.readUTF();
//	      System.out.println("Client : "+str1);
//	      System.out.println("Enter Reply : ");
//	      str2=br.readLine();
//	      dout.writeUTF(str2);
//	      dout.flush();
//	    }
    }
  }
}
