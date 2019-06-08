//package collection;
import java.sql.*;
import java.util.*;
public class database
{
	public static Connection conn=null;
	public Connection connect()
	{	
		try
		{	
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/litesh", "root", "litesh123");
			if(!conn.isClosed())
			{
				System.out.println("Database is connected !!");
			}
			else
			{
				System.out.println("Unable to connect to database...");
			}
			//conn.close();
		}
		catch (SQLException ex)
		{
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}
	
	public void exeUpdate(Account ac)		// Not for printing the entries
	{

			int modified;
			try {
				if(conn == null)
				{
					System.out.println("Conn is null");
				}
				PreparedStatement stmt = conn.prepareStatement("insert into Account(accno,passwrd,contact,bal,name) values(?,?,?,?,?)");
				stmt.setInt(1,Account.acc_count);
				stmt.setInt(2, ac.passwrd);
				stmt.setLong(3, ac.contact);
				stmt.setDouble(4, ac.bal);
				stmt.setString(5, ac.name);
				modified=stmt.executeUpdate();
				System.out.println("Total entries updated : "+ modified);
				stmt.close();
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			} 
	}
	
	public Connection exePrint(int acno,int pass)
	{

			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from Account where accno="+acno+";"); 
				if(acno==rs.getInt(1) && pass == rs.getInt(2))
				{
					rs.beforeFirst();
					while(rs.next())  
					System.out.println(rs.getInt(1)+"  "+" bal: "+rs.getLong(3)+"\n contact:"+rs.getLong(4)+"\n Name:"+rs.getString(5));  
				}
			} catch (SQLException e)
			{	
				System.out.println("SQLException: " + e.getMessage());
				e.printStackTrace();
			}  
		return conn;		
	}

	public void exeTransaction(int acno, int pass)
	{
		int modified;
		double temp;
		Statement stmt=null;
		Scanner sc=new Scanner(System.in);
		try {
			stmt = conn.createStatement();
		
			ResultSet rs = stmt.executeQuery("select * from Account where accno="+acno+";"); 
			if(acno==rs.getInt(1) && pass == rs.getInt(2))
			{	
				System.out.println("Enter the amount of trsnsaction [+/-]");
				temp=sc.nextDouble();
				PreparedStatement stmt1=conn.prepareStatement("Update Account SET bal=bal+? Where accno=?;"); 
				stmt1.setInt(2,acno);
				stmt1.setDouble(1, temp);
				modified=stmt1.executeUpdate();
				System.out.println("Your transaction has been processsed sucessfully!");
				
			}
		} catch (SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}  	
	}
	
}

/*
create table Account (accno int primary key not null,passwrd int,bal bigint,
contact bigint,name varchar(20));
*/
