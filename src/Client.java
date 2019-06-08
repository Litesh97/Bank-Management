import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.io.*;

class Client
{	
	  private static Socket clientSocket = null;
	  // The output stream
	  private static DataOutputStream dout = null;
    public static void main(String args[])  throws IOException,UnknownHostException, SQLException
    {
    	clientSocket=new Socket("127.0.0.1",5000);
      	
    	//DataInputStream din=new DataInputStream(s.getInputStream());
      //DataOutputStream dout=new DataOutputStream(s.getOutputStream());
      //BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
      
      //while(!str1.equals("disconnect"))
      //{
        /*System.out.println("Enter messege : ");
        str2=br.readLine();
        dout.writeUTF(str2);
        dout.flush();
        */
    	  Client cl=new Client();
          cl.clientMenu();

      //  System.out.println("Waiting for Server's Reply...");
      //  str1=din.readUTF();
      //  System.out.println("Server : "+str1);
      //}
      //din.close();
      //dout.close();
     // s.close();
   }

    public void clientMenu() throws SQLException, IOException
    {
      int choice1;
      int opt;
      Scanner sc=new Scanner(System.in);
      database db=new database();
    	db.connect();
	    do
	    { 		
	            System.out.println("Enter choice\n1.Add account\n2.Get Account info\n3.Do transaction\n");
	            choice1 = sc.nextInt();
	            switch(choice1)
	            {
	              case 1:
	                addAccount();
	                break;
	              case 2:
	                checkInfo();
	                break;
	              case 3:
	                transaction();
	                break;
	                
	              case 4:
	                break;
	                
	              default: System.out.println("Wrong input!");
	              break;
	            }
	     }while(choice1!=4);    
    }
            
    public void addAccount() throws SQLException, IOException
	{
		Account ac = new Account();
		System.out.print("Enter details:\nName :");
		Scanner sc = new Scanner(System.in);
		ac.name = sc.nextLine();
		System.out.println("Contact No :");
		ac.contact = sc.nextLong();
		System.out.println("Initial balance :");
		ac.bal = sc.nextDouble();
		sc.nextLine();
		System.out.println("Set up password:");
		ac.passwrd = sc.nextInt();
		Account.acc_count++;
		
		//FileOutputStream file = new FileOutputStream("temp.txt");
		dout=new DataOutputStream(clientSocket.getOutputStream());
		ObjectOutputStream oot = new ObjectOutputStream(dout);
		oot.writeObject(ac);
        
        //flushing the stream
        oot.flush();
		oot.close();
		

	}

	public void checkInfo()
	{	
	    System.out.print("Enter Account No :");
	    int temp_accno;
	
	    Scanner sc = new Scanner(System.in);
	    temp_accno = sc.nextInt();
	
	    System.out.println("enter password:");
	    int pass = sc.nextInt();
	    
	    database db=new database();
			db.exePrint(temp_accno,pass);
	}
	
	public void transaction()
	{
	    System.out.print("Enter Account No :");
	    int temp_accno;
	
	    Scanner sc = new Scanner(System.in);
	    temp_accno = sc.nextInt();
	
	    System.out.println("enter password:");
	    int pass = sc.nextInt();
	    
	    database db=new database();
		db.exeTransaction(temp_accno,pass);

	}
}
 /* 

 serialize the account activities and pass the account object to the server which will execute an query to add into database */


 //add functions to this class