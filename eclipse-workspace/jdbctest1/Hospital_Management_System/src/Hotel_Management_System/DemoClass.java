package Hotel_Management_System;

import java.sql.*;
import java.util.*;
public class DemoClass {
	public static void main(String[] args) throws Exception
	{

		Scanner sc=new Scanner(System.in);
		String url="jdbc:mysql://localhost:3306/mydatabase";
		String uname="root";
		String pass="Eswar.62004";
		String query= "Select name from customers where id=3";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(query);
		rs.next();
		System.out.println("Enter the Type of User");
		System.out.println("1. Customer");
		System.out.println("2. Manager");
		System.out.println("3. Hotel Staff");
		int ch=sc.nextInt();
		if(ch==1)
		{
			User.login();
		}
		else if(ch==2)
		{
			Manager.login();
		}
		else if(ch==3)
		{
			Staff.login();
		}
		sc.close();
	}

}   

