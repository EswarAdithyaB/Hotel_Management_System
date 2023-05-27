package Hotel_Management_System;

import java.sql.*;
import java.util.*;

public class Staff {
	static int empId;
	static String name;
	static double salary;
	static long ph_no;
	static String Assigned_duty;
	static void login() throws Exception
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter User Name");
		String user_name=sc.nextLine();
		System.out.println("Enter Password");
		String password=sc.nextLine();
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		String query= "Select * from staff_details where User_Name ='"+user_name+"'";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(query);
		if(!(rs.next()))
		{
			System.out.println("User Name Does't exit");
			System.out.println("Press 1 to try again");
			System.out.println("Press 2 to Exit");
			int c=sc.nextInt();
			if(c==1) {
				login();
			}
		}
		else
		{
			String p= rs.getString("Password");
			if(p.equals(password))
			{
				System.out.println("Success login");
				empId=rs.getInt("id");
				name=rs.getString("User_Name");
				salary=rs.getDouble("Salary");
				ph_no=rs.getLong("Phone_Number");
				Assigned_duty=rs.getString("Assigned_duty");
				staff_features();
			}
			else
			{
				System.out.println("Incorrect Password");
				System.out.println("Press 1 to try again");
				System.out.println("Press 2 to Exit");
				int c=sc.nextInt();
				if(c==1) {
					login();
				}
				
			}
			sc.close();
		}
	}
	static void staff_features() throws Exception
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Select an option to perform");
		System.out.println("1. Check Assigned Duty");
		System.out.println("2. Check for Complaints");
		System.out.println("3. to exit");
		int c= sc.nextInt();
		if(c==1)
		{
			checkDuty();
		}
		if(c==2)
		{
			checkComplaints();
		}
		sc.close();
	}
	static void checkDuty( )throws Exception
	{
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();

		String query= "Select * from staff_details where id="+empId;
		ResultSet rs=st.executeQuery(query);
		rs.next();
		System.out.println("Today Your Duty is: "+rs.getString("Assigned_duty"));
	}
	static void checkComplaints()throws Exception
	{
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();

		String query= "Select * from room_details where Complaint_Status='Not Done'";
		ResultSet rs=st.executeQuery(query);
		if(!rs.next())
		{
			System.out.println("There are no complaints");
		}
		else
		{
			System.out.println("Room Number="+rs.getString("Room_ID"));
			System.out.println("Complaint is \""+rs.getString("Complaints")+"\"\n\n");
			while(rs.next())
			{
				System.out.println("Room Number="+rs.getString("Room_ID"));
				System.out.println("Complaint is \""+rs.getString("Complaints")+"\"\n\n");
				
			}
		}
	}
	


}

