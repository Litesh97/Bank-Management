
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.io.*;

class ClientHandler implements Runnable 
{
    Scanner scn = new Scanner(System.in);
     String name;
    final DataInputStream din;
    final DataOutputStream dout;
    Socket s;
    boolean isloggedin;
     
    // constructor
    public ClientHandler(Socket s, String name, DataInputStream din, DataOutputStream dout)
    {
        this.din = din;
        this.dout = dout;
        this.name = name;
        this.s = s;
        this.isloggedin=true;
    }
 
    @Override
    public void run()
    {
        while (true) 
        {	
            try
            {
                // receive the string
            	//FileInputStream file = new FileInputStream("temp.txt");
            	ObjectInputStream in = new ObjectInputStream(din);

			    Account ac;
				ac = (Account)in.readObject();
				in.close();
				//file.close();
                 
//                if(received.equals("logout")){
//                    this.isloggedin=false;
//                    this.s.close();
//                    break;
//                }
                 
 
                // search for the recipient in the connected devices list.
                // ar is the vector storing client of active users
                for (ClientHandler mc : Server.client_list) 
                {
                    // if the recipient is found, write on its
                    // output stream
                    if(mc.isloggedin==true) 
                    {
                    	
                    	System.out.println("Object has been deserialized \n Data after Deserialization.");
        				System.out.println("name = " + ac.name);
        			    System.out.println("contact = " + ac.contact);
        			    System.out.println("Password = " + ac.passwrd);
        			    System.out.println("bal = " + ac.bal);
        			    
        			    database db=new database();
        				db.exeUpdate(ac);
        				System.out.println("Entry Added in database !");
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e)
            {   
            	try {
					din.close();
					dout.close(); 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
            	System.out.println("Error : "+ e.getMessage()); 
                e.printStackTrace();
            }
             
        }
         
    }
}
