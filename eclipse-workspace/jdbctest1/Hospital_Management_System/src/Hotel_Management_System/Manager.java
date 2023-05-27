package Hotel_Management_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Manager {
	static int empId;
	static String name;
	static double salary;
	static String qualification;
	static double experience;
	static long ph_no;
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
		String query= "Select * from manager_details where User_Name ='"+user_name+"'";
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
				salary=rs.getFloat("Salary");
				qualification=rs.getString("Qualification");
				experience=rs.getFloat("Experience");
				ph_no=rs.getLong("Phone_Number");
				manager_features();
			}
			else
			{
				System.out.println("Incorrect Password");
                                System.out.println("Press 1 to try again");
                                System.out.println("Press 2 to Sign up");
                                int c=sc.nextInt();
                                if(c==1) {
                                        login();
                                }
			}
			sc.close();
		}
	}
	static void manager_features() throws Exception
	{
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		Scanner sc=new Scanner(System.in);
		System.out.println("Select an option to perform");
		System.out.println("1. Edit Room Details");
		System.out.println("2. Check In the Customer");
		System.out.println("3. Check Out the Customer");
		System.out.println("4. Search for Room Details");
		System.out.println("5. Search for Customer Details");
		System.out.println("6. Assing Duty to the Staff");
		System.out.println("7. View Report");
		System.out.println("8. Logout");
		int c= sc.nextInt();
		if(c==1)
		{
			Room_Management.updateRoomDetails();
		}
		else if(c==2)
		{
			System.out.println("1. If the Customer is not an registered user");
			System.out.println("2. If the Customer is a registered user");
			c=sc.nextInt();
			if(c==1)
			{
				User.signup(0);
				System.out.println("Enter The User_Id");
				int i=sc.nextInt();
				User.checkIn(i,0);
			}
			else if(c==2)
			{
				System.out.println("Enter The User_Id");
				int i=sc.nextInt();
				User.checkIn(i,0);
			}
		}
		else if(c==3)
		{
			System.out.println("Enter The User_Id");
			int i=sc.nextInt();
			User.checkOut(i,0);
		}
		else if(c==4)
		{
			System.out.println("Enter The Room Number:");
			int rno=sc.nextInt();
			String q1="select * from room_details where Room_ID="+rno;
			ResultSet rs=st.executeQuery(q1);
			rs.next();
			System.out.println("Room number         : "+rs.getInt("Room_ID"));
			System.out.println("Room Type           : "+rs.getString("Type"));
			System.out.println("Currently room is   : "+rs.getString("Status"));
			System.out.println("Price of the room   : "+rs.getFloat("price")+"/- perday");
			System.out.println("Complaint Raised    : "+rs.getString("Complaints"));
			System.out.println("Complaint Status is : "+rs.getString("Complaint_Status"));
			q1="select Room_Rating from report where Room_ID="+rno;
                        rs=st.executeQuery(q1);
			float rRoom=0;
			int count=0;
			while(rs.next())
			{
				rRoom+=rs.getFloat(1);
				count+=1;
			}
			if(count==0)
				rRoom=0;
			else
				rRoom/=count;
			System.out.println("Room Raitings      : "+rRoom);
			q1="select Date,Number_of_days from reservations where Room_Number="+rno;
                        rs=st.executeQuery(q1);
			if(!(rs.next()))
				System.out.println("No Reservations available on this Room");
			else
			{
				System.out.println("The room is Reserved on \""+rs.getString("Date")+"\" for "+rs.getInt("Number_of_days")+" Days");
				while(rs.next())
					System.out.println("The room is Reserved on \""+rs.getString("Date")+"\" for "+rs.getInt("Number_of_days")+" Days");
			}
			System.out.println("\n\n1. Go to main menu");
			System.out.println("2. to Exit");
			int ch=sc.nextInt();
			if(ch==1)
			{
				manager_features();
			}
		}
		else if(c==5)
		{
                    searchUser();
		}
                else if(c==6)
                {
                    assingDuty();
                }
                else if(c==7)
                {
                    generateReport();
                }
                else if(c==8)
		{
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
		}
	}
	static void searchUser() throws Exception
	{
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		Scanner sc=new Scanner(System.in);
		System.out.println("press 1 to search by Id");
		System.out.println("press 2 to search by User Name");
		int ch= sc.nextInt();
		ResultSet r1;
		if(ch==1)
		{
			System.out.println("Enter User ID");
			int i=sc.nextInt();
			String q1="Select * from user_details where id="+i;
			r1=st.executeQuery(q1);
		}
		else
		{
                    sc.nextLine();
			System.out.println("Enter User Name");
			String na=sc.nextLine();
			String q1="Select * from user_details where Name='"+na+"'";
			r1=st.executeQuery(q1);
		}	
		
		if(!(r1.next()))
		{
			System.out.println("User Does not Exist");
			System.out.println("press 1 to Try Again");
			System.out.println("press 2 to Main Menu");
			int n= sc.nextInt();
			if(n==1)
			{
				searchUser();
			}
			if(n==2)
			{
				manager_features();
			}
		}
                else
                {
                    System.out.println("User Name: "+r1.getString("Name"));
                    System.out.println("id : "+r1.getString("id"));
                    System.out.println("Aadhar_Number: "+r1.getString("Aadhar_Number"));
                    System.out.println("Phone_Number: "+r1.getString("Phone_Number"));
                    System.out.println("Address: "+r1.getString("Address"));
                    System.out.println("Purpose of Visit: "+r1.getString("Purpose_of_visit"));
                    System.out.println("Booking Date: "+r1.getString("Booking_Date"));
                    System.out.println("\n\n1. Go to main menu");
                    System.out.println("2. to Exit");
                    ch=sc.nextInt();
                    if(ch==1)
                   {
			manager_features();
		}
                }
	}
        static void assingDuty() throws Exception
        {
            Scanner sc=new Scanner(System.in);
            String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
            String uname="root";
            String pass="Eswar.62004";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con =DriverManager.getConnection(url,uname,pass);
            Statement st = con.createStatement();
            System.out.println("Enter the Staff id :");
            int i=sc.nextInt();
            System.out.println("Enter the Job you like to assign :");
            sc.nextLine();
            String job=sc.nextLine();
            String query1="UPDATE staff_details SET Assigned_duty='"+job+"' WHERE id="+i;
            int c=st.executeUpdate(query1);
            if(c==1)
            {
                System.out.println("Succefuly updated");
                System.out.println("\n\n1. Go to main menu");
		System.out.println("2. to Exit");
		int ch=sc.nextInt();
		if(ch==1)
		{
                    manager_features();
		}
            }
            else
            {
                System.out.println("Id doesn't exist");
                System.out.println("\n\n1. Go to main menu");
		System.out.println("2. to Try again");
		int ch=sc.nextInt();
		if(ch==1)
		{
                    manager_features();
		}
                else if(ch==2)
                {
                   assingDuty(); 
                }
            }
        }
        static void generateReport() throws Exception
        {
            Scanner sc=new Scanner(System.in);
            String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
            String uname="root";
            String pass="Eswar.62004";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con =DriverManager.getConnection(url,uname,pass);
            Statement st = con.createStatement();
            String q1="Select sum(Bill_Payed) from report";
            ResultSet rs=st.executeQuery(q1);
            rs.next();
            int rid=rs.getInt(1);
            System.out.println("Total Revenue generated"+rid);
            q1="Select count(Report_ID) from report";
            rs=st.executeQuery(q1);
            rs.next();
            rid=rs.getInt(1);
            System.out.println("Total Number of Bookings done: "+rid);
            String query= "Select Room_ID from room_details";
            rs = st.executeQuery(query);
            int i=0;
            int rooms[]=new int[100];
            while(rs.next())
            {
		rooms[i]=rs.getInt("Room_ID");
		i+=1;
            }
            System.out.println("Room No \t No.of.Bookings \t Average Rating");
            for (int k:rooms)
            {
                if(k==0)
		{
                    break;
		}
                int roomid=k;
		query="select count(Report_ID) from report where Room_ID='"+roomid+"'";
		rs=st.executeQuery(query);
                rs.next();
                int c=rs.getInt(1);
                query="select sum(Room_Rating) from report where Room_ID='"+roomid+"'";
		rs=st.executeQuery(query);
                rs.next();
                int r=rs.getInt(1);
                if(c!=0)    
                    System.out.println("   "+roomid+"\t\t\t"+c+"\t\t\t"+r/c*1.0);
                else
                    System.out.println("   "+roomid+"\t\t\t"+c+"\t\t    No reviews");
            }
            q1="Select sum(Overall_Ratings) from report";
            rs=st.executeQuery(q1);
            rs.next();
            int rat=rs.getInt(1);
            System.out.println("Overall Rating of the Hotel is: "+rat/rid);
            q1="Select count(id) from staff_details";
            rs=st.executeQuery(q1);
            rs.next();
            int cs=rs.getInt(1);
            System.out.println("Number of staff Working:"+cs);
            q1="Select count(id) from manager_details";
            rs=st.executeQuery(q1);
            rs.next();
            int cm=rs.getInt(1);
            System.out.println("Number of Manager Working:"+cm);
            q1="Select sum(Salary) from manager_details";
            rs=st.executeQuery(q1);
            rs.next();
            int sm=rs.getInt(1);
            q1="Select sum(Salary) from staff_details";
            rs=st.executeQuery(q1);
            rs.next();
            int sp=rs.getInt(1);
            int sal=sm+sp;
            System.out.println("Number of Manager Working:"+sal);
            System.out.println("\n\n\n\n1. Go to main menu");
            System.out.println("2. to Exit");
            int ch=sc.nextInt();
            if(ch==1)
            {
		manager_features();
            }
        }

}


