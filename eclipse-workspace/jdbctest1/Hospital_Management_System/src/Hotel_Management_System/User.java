package Hotel_Management_System;

import java.sql.*;
import java.util.*;
import java.io.IOException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
public class User {
	static String UserName;
	static int UserID;
	static String name;
	static int ph_no,aadhar_no;
	static String address;
	static String purpose;
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
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
		String query= "Select * from user_details where User_Name ='"+user_name+"'";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(query);
		if(!(rs.next()))
		{
			System.out.println("User Name Does't exit");
			System.out.println("Press 1 to try again");
			System.out.println("Press 2 to Sign up");
			int c=sc.nextInt();
			if(c==1) {
				login();
			}
			else
			{
				signup(1);
			}
		}
		else
		{
			String p= rs.getString("Password");
			if(p.equals(password))
			{
				System.out.println("Success login");
				int i = rs.getInt("id");
				name=rs.getString("Name");
				UserID=rs.getInt("id");
				UserName=rs.getString("User_Name");
				ph_no=rs.getInt("Phone_Number");
				aadhar_no=rs.getInt("Aadhar_Number");
				address=rs.getString("Address");
				purpose=rs.getString("Purpose_of_visit");
				user_features(i);
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
			else
			{
				signup(1);
			}
			}
		}
	}
	static void signup(int f) throws Exception
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter User Name");
		String user_name=sc.nextLine();
		System.out.println("Enter Password");
		String password=sc.nextLine();
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		String query= "Select * from user_details where User_Name ='"+user_name+"'";
		ResultSet rs = st.executeQuery(query);
		if(rs.next())
		{
			System.out.println("User Name already exits try other");
			signup(f);
		}
		else
		{
			String q1="Select max(id) from user_details";
			rs=st.executeQuery(q1);
			rs.next();
			int i=rs.getInt(1);
			query= "insert into user_details (User_Name,Password,id) values('"+user_name+"','"+password+"',"+(((int)i)+1)+")";
			int c=st.executeUpdate(query);
			if(c==1)
			{
				System.out.println("Successfully Signed up your User_id is "+(((int)i)+1));
				setDetails(((int)i)+1);
				if(f!=0)
				{
					System.out.println("Enter 1 to login");
					System.out.println("Enter 2 to exit");
					c=sc.nextInt();
					if(c==1)
					{
						login();
					}
				}
			}
		}
	}
	static void user_features(int i) throws Exception
	{
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Select an option to perform");
		System.out.println("1. Edit Profile");
		System.out.println("2. Reserve a Room");
		System.out.println("3. Cancel Reservation");
		System.out.println("4. Check in");
		System.out.println("5. Raise a Complainte");
		System.out.println("6. Check out");
		System.out.println("7. Log out");
		int c= sc.nextInt();
		if(c==1)
		{
			updateProfile();
		}
		else if(c==2)
		{
			reserveRoom();
		}
		else if(c==3)
		{
			cancelReservation();
		}
		else if(c==4)
		{
			checkIn(UserID,1);
		}
		else if(c==5)
		{
			System.out.println("Enter Your Room Number");
			int rno=sc.nextInt();
			raiseComplaint(rno);
		}
		else if(c==6)
		{
			checkOut(UserID,1);
		}
		else if(c==7)
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
	static void setDetails(int i) throws Exception
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter your Full Name");
		String n=sc.nextLine();
		System.out.println("Enter your Aadhar Number");
		long aa=sc.nextInt();
		System.out.println("Enter your Phone Number");
		long ph=sc.nextInt();
		System.out.println("Enter your Address");
		sc.nextLine();
		String a=sc.nextLine();
		System.out.println("Enter your Purpose of Vist");
		String pov=sc.nextLine();
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		String query1="UPDATE user_details SET Name='"+n+"' WHERE id="+i;
		String query2="UPDATE user_details SET Aadhar_Number="+aa+" WHERE id="+i;
		String query3="UPDATE user_details SET Phone_Number="+ph+" WHERE id="+i;
		String query4="UPDATE user_details SET Address='"+a+"' WHERE id="+i;
		String query5="UPDATE user_details SET Purpose_of_visit='"+pov+"' WHERE id="+i;
		Statement st = con.createStatement();
		int c=st.executeUpdate(query1);
		c=st.executeUpdate(query2);
		c=st.executeUpdate(query3);
		c=st.executeUpdate(query4);
		c=st.executeUpdate(query5);
		if(c==1)
		{
			System.out.println("Successfully Updated");
		}
		else
		{
			System.out.println("Something went Wrong");
		}
	}
	static void updateProfile() throws Exception
	{
		Scanner sc=new Scanner(System.in);
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		System.out.println("What would you like to update");
		System.out.println("1. User Name");
		System.out.println("2. Password");
		System.out.println("3. Profile Name");
		System.out.println("4. Aadhar Number");
		System.out.println("5. Phone Number");
		System.out.println("6. Address");
		System.out.println("7. Purpose of visit");
		System.out.println("8. To back to Main Menu");
		int c=sc.nextInt();
		if(c==1)
		{
			System.out.println("The Current User Name is "+UserName);
			System.out.println("Enter What you want to change: ");
			sc.nextLine();
			UserName=sc.nextLine();
			String query="UPDATE user_details SET User_Name='"+UserName+"' WHERE id="+UserID;
			int co=st.executeUpdate(query);
			if(co==1)
			{
				System.out.println("Succefully updated");
				System.out.println("Enter 1 to go to main menu");
				System.out.println("Enter 2 to Update other Feild");
				System.out.println("Enter 3 to Exit");
				int ch=sc.nextInt();
				if(ch==1)
				{
					user_features(UserID);
				}
				if(ch==2)
				{
					updateProfile();
				}
			}
			else
			{
				System.out.println("Something went Wrong");
			}
		}
		if(c==2)
		{
			System.out.println("Enter your Current Password");
			sc.nextLine();
			String p=sc.nextLine();
			String query= "Select Password from user_details where id ="+UserID;
			ResultSet rs = st.executeQuery(query);
			rs.next();
			String password= rs.getString("Password");
			if(p.equals(password))
			{
				System.out.println("Enter Your new Password");
				String pa=sc.nextLine();
				query="UPDATE user_details SET Password='"+pa+"' WHERE id="+UserID;
				int co=st.executeUpdate(query);
				if(co==1)
				{
					System.out.println("Succefully updated");
					System.out.println("Enter 1 to go to main menu");
					System.out.println("Enter 2 to Update other Feild");
					System.out.println("Enter 3 to Exit");
					int ch=sc.nextInt();
					if(ch==1)
					{
						user_features(UserID);
					}
					if(ch==2)
					{
						updateProfile();
					}
				}
				else
				{
					System.out.println("Something went Wrong");
				}
			}
			else
			{
				System.out.println("Worng password");
			}
		}
		if(c==3)
		{
			System.out.println("The Current Profile Name is "+name);
			System.out.println("Enter What you want to change: ");
			sc.nextLine();
			name=sc.nextLine();
			String query="UPDATE user_details SET Name='"+name+"' WHERE id="+UserID;
			int co=st.executeUpdate(query);
			if(co==1)
			{
				System.out.println("Succefully updated");
				System.out.println("Enter 1 to go to main menu");
				System.out.println("Enter 2 to Update other Feild");
				System.out.println("Enter 3 to Exit");
				int ch=sc.nextInt();
				if(ch==1)
				{
					user_features(UserID);
				}
				if(ch==2)
				{
					updateProfile();
				}
			}
			else
			{
				System.out.println("Something went Wrong");
			}
		}
		if(c==4)
		{
			System.out.println("The Current Aadhar Number is "+aadhar_no);
			System.out.println("Enter To What you want to change: ");
			aadhar_no=sc.nextInt();
			String query="UPDATE user_details SET Aadhar_Number="+aadhar_no+" WHERE id="+UserID;
			int co=st.executeUpdate(query);
			if(co==1)
			{
				System.out.println("Succefully updated");
				System.out.println("Enter 1 to go to main menu");
				System.out.println("Enter 2 to Update other Feild");
				System.out.println("Enter 3 to Exit");
				int ch=sc.nextInt();
				if(ch==1)
				{
					user_features(UserID);
				}
				if(ch==2)
				{
					updateProfile();
				}
			}
			else
			{
				System.out.println("Something went Wrong");
			}
		}
		if(c==5) 
		{
			System.out.println("The Current Phone Number is "+ph_no);
			System.out.println("Enter To What you want to change: ");
			ph_no=sc.nextInt();
			String query="UPDATE user_details SET Phone_Number="+ph_no+" WHERE id="+UserID;
			int co=st.executeUpdate(query);
			if(co==1)
			{
				System.out.println("Succefully updated");
				System.out.println("Enter 1 to go to main menu");
				System.out.println("Enter 2 to Update other Feild");
				System.out.println("Enter 3 to Exit");
				int ch=sc.nextInt();
				if(ch==1)
				{
					user_features(UserID);
				}
				if(ch==2)
				{
					updateProfile();
				}
			}
			else
			{
				System.out.println("Something went Wrong");
			}	
		}
		if(c==6)
		{
			System.out.println("The Current Address is "+address);
			System.out.println("Enter What you want to change: ");
			sc.nextLine();
			address=sc.nextLine();
			String query="UPDATE user_details SET Address='"+address+"' WHERE id="+UserID;
			int co=st.executeUpdate(query);
			if(co==1)
			{
				System.out.println("Succefully updated");
				System.out.println("Enter 1 to go to main menu");
				System.out.println("Enter 2 to Update other Feild");
				System.out.println("Enter 3 to Exit");
				int ch=sc.nextInt();
				if(ch==1)
				{
					user_features(UserID);
				}
				if(ch==2)
				{
					updateProfile();
				}
			}
			else
			{
				System.out.println("Something went Wrong");
			}
		}
		if(c==7)
		{
			System.out.println("The Current Purpose of visit is "+purpose);
			System.out.println("Enter What you want to change: ");
			sc.nextLine();
			purpose=sc.nextLine();
			String query="UPDATE user_details SET Purpose_of_visit='"+purpose+"' WHERE id="+UserID;
			int co=st.executeUpdate(query);
			if(co==1)
			{
				System.out.println("Succefully updated");
				System.out.println("Enter 1 to go to main menu");
				System.out.println("Enter 2 to Update other Feild");
				System.out.println("Enter 3 to Exit");
				int ch=sc.nextInt();
				if(ch==1)
				{
					user_features(UserID);
				}
				if(ch==2)
				{
					updateProfile();
				}
			}
			else
			{
				System.out.println("Something went Wrong");
			}
		}
		if(c==8)
		{
			user_features(UserID);
		}
	}	
	static void reserveRoom() throws Exception
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the Date when you Want to BookRoom in formate(yyyy-mm-dd)");
		String bd=sc.nextLine();
		System.out.println("Enter the Date when you Want to Vacate room in formate(yyyy-mm-dd)");
		String vd=sc.nextLine();
		int[]  arooms=Room_Management.roomAvailability(bd,vd);
		LocalDate bookingDate=LocalDate.parse(bd,formatter);
		LocalDate vacateDate=LocalDate.parse(vd,formatter);
		long rDays=(ChronoUnit.DAYS.between(bookingDate, vacateDate)+1);
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		
		System.out.println("Select Your Choies of Room ID:");
		int ch=sc.nextInt();
		int flag=0;
		for (int j : arooms)
		{
			if(ch==j)
			{
				flag=1;
				String q1="Select max(ReservationID) from reservations";
				ResultSet rs=st.executeQuery(q1);
				rs.next();
				int rid=rs.getInt(1);
				String query="insert into reservations values("+(rid+1)+",'"+UserName+"','"+bookingDate+"','"+ph_no+"','"+ch+"',"+rDays+",'Free')";
				int a=st.executeUpdate(query);
				if(a==1)
				{
					System.out.println("Reservation Success");
					System.out.println("Press 1 to go to main menu");
					System.out.println("Press 2 to exit");
					a=sc.nextInt();
					if(a==1)
					{
						user_features(UserID);
					}
				}
				else
				{
					System.out.println("Something went wrong");
				}
				break;
			}
			
		}
		if(flag==0)
		{
			System.out.println("Entered room is Not available");
			System.out.println("Press 1 to Retry");
			System.out.println("Press 2 to Exit");
			int c=sc.nextInt();
			if(c==1)
			{
				reserveRoom();
			}
		}
	}
	static void cancelReservation() throws Exception
	{
		Scanner sc=new Scanner(System.in);
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		String query= "Select * from reservations where User_Name='"+UserName+"'";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		System.out.println("You have Resevation on:");
		int count=0;
		int R[]=new int[100];
		while(rs.next())
		{
			System.out.println("Date : "+rs.getString("Date"));
			System.out.println("Room Number : "+rs.getString("Room_Number"));
			System.out.println("Number of Days : "+rs.getString("Number_of_days"));
			System.out.println("Reservation ID is : "+rs.getString("ReservationID"));
			R[count]=rs.getInt("ReservationID");
			count+=1;
		}
		if(count==0)
		{
			System.out.println("You have no Reservations");
			System.out.println("press 1 to make a Reservation ");
			System.out.println("press 2 to go to main Menu");
			System.out.println("Press 3 to Exit");
			int c=sc.nextInt();
			if(c==1)
			{
				reserveRoom();
			}
			if(c==2)
			{
				user_features(UserID);
			}
		}
		if(count==1)
		{
	        LocalDate dateObj = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String date = dateObj.format(formatter);
			LocalDate nowDate=LocalDate.parse(date,formatter);
			query= "Select * from reservation where User_Name='"+UserName+"'";
			ResultSet rs1=st.executeQuery(query);
			rs1.next();
			LocalDate reserveDate=LocalDate.parse(rs1.getString("Date"),formatter);
			long diffDates=ChronoUnit.DAYS.between(nowDate,reserveDate);
			if(diffDates>1)
			{
				query="delete from reservations where User_Name='"+UserName+"'";
				int a=st.executeUpdate(query);
				if(a==1)
				{
					System.out.println("Your Resevation has been Removed Successfully!!");
					System.out.println("Press 1 to go to main menu");
					System.out.println("Press 2 to Exit");
					a=sc.nextInt();
					if(a==1)
					{
						user_features(UserID);
					}
				}
			}
			else
			{
				System.out.println("Cancelation period has been Expired");
				System.out.println("Press 1 to go to main menu");
				System.out.println("Press 2 to exit");
				int a=sc.nextInt();
				if(a==1)
				{
					user_features(UserID);
				}
			}
		 }
		else
		{
			System.out.println("Enter the ReservationID you would like to Delete");
			int revID=sc.nextInt();
			int flag=0;
			for(int j: R)
			{
				if(j==revID)
				{
					flag=1;
					LocalDate dateObj = LocalDate.now();
			        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			        String date = dateObj.format(formatter);
					LocalDate nowDate=LocalDate.parse(date,formatter);
					query= "Select * from reservations where ReservationID="+revID;
					ResultSet rs1=st.executeQuery(query);
					rs1.next();
					LocalDate reserveDate=LocalDate.parse(rs1.getString("Date"),formatter);;
					
					long diffDates=ChronoUnit.DAYS.between(nowDate,reserveDate);
					System.out.println(nowDate);
					System.out.println(reserveDate);
					if(diffDates>1)
					{
						query="delete from reservations where ReservationID="+revID;
						int a=st.executeUpdate(query);
						if(a==1)
						{
							System.out.println("Your Resevation has been Removed Successfully!!");
							System.out.println("Press 1 to go to main menu");
							System.out.println("Press 2 to Exit");
							a=sc.nextInt();
							if(a==1)
							{
								user_features(UserID);
							}
						}
					}
					else
					{
						System.out.println("Cancelation period has been Expired");
						System.out.println("Press 1 to go to main menu");
						System.out.println("Press 2 to Cancel other Reservations");
						System.out.println("Press 3 to exit");
						int a=sc.nextInt();
						if(a==1)
						{
							user_features(UserID);
						}
						if(a==2)
						{
							cancelReservation();
						}
					}
					
				}
			}
			if(flag==0)
			{
				System.out.println("Entred Id iisn't Available");
				System.out.println("Press 1 to go to main menu");
				System.out.println("Press 2 to Try again");
				System.out.println("Press 3 to exit");
				int a=sc.nextInt();
				if(a==1)
				{
					user_features(UserID);
				}
				if(a==2)
				{
					cancelReservation();
				}
			}
		}
	}
	static void checkIn(int i,int f) throws Exception
	{
		Scanner sc=new Scanner(System.in);
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		String query= "Select * from user_details where id ="+i;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(!(rs.next()))
		{
			if(f==0)
			{
				System.out.println("User Name Does't exit");
				System.out.println("Press 1 if its new user to Sign up");
				System.out.println("Press 2 to Exit");
				int c=sc.nextInt();
				if(c==1)
				{
					signup(0);
				}
			}
			else
			{
				System.out.println("User Name Does't exit");
				System.out.println("Press 1 to try again");
				System.out.println("Press 2 if its new user to Sign up");
				int c=sc.nextInt();
				if(c==1) {
					login();
				}
				else
				{
					signup(1);
				}
			}
		}
		else
		{
			name=rs.getString("Name");
			UserID=rs.getInt("id");
			UserName=rs.getString("User_Name");
			ph_no=rs.getInt("Phone_Number");
			aadhar_no=rs.getInt("Aadhar_Number");
			address=rs.getString("Address");
			purpose=rs.getString("Purpose_of_visit");
			query= "Select * from reservations where User_Name ='"+UserName+"' and Status ='Free'";
			rs=st.executeQuery(query);
			if(!(rs.next()))
			{
				System.out.println("You have no resevations Please book a room now");
				System.out.println("Enter the Date when you Want to BookRoom in formate(yyyy-mm-dd)");
				String bd=sc.nextLine();
				System.out.println("Enter the Date when you Want to Vacate room in formate(yyyy-mm-dd)");
				String vd=sc.nextLine();
				int[]  arooms=Room_Management.roomAvailability(bd,vd);
				LocalDate bookingDate=LocalDate.parse(bd,formatter);
				LocalDate vacateDate=LocalDate.parse(vd,formatter);
				long rDays=(ChronoUnit.DAYS.between(bookingDate, vacateDate)+1);
				System.out.println("Select Your Choies of Room ID:");
				int ch=sc.nextInt();
				int flag=0;
				for (int j : arooms)
				{
					if(ch==j)
					{
						flag=1;
						String q1="Select max(ReservationID) from reservations";
						ResultSet rs1=st.executeQuery(q1);
						rs1.next();
						int rid=rs1.getInt(1);
						
						String query1="insert into reservations values("+(rid+1)+",'"+UserName+"','"+bookingDate+"','"+ph_no+"','"+ch+"',"+rDays+",'Checked-In')";
						int a=st.executeUpdate(query1);
						if(a==1)
						{
							System.out.println("Check In Success");
							query1="select max(Booking_ID) from bookings";
							rs1=st.executeQuery(query1);
							rs1.next();
							int k=rs1.getInt(1);
							query1="insert into bookings(Booking_ID,User_Name,CheckIn_date,Room_Id) values("+(k+1)+",'"+UserName+"','"+bookingDate+"','"+ch+"')";
							a=st.executeUpdate(query1);
							query1="UPDATE room_details SET Status='Occupied' WHERE Room_ID="+ch;
							a=st.executeUpdate(query1);
							System.out.println("Press 1 to go to main menu");
							System.out.println("Press 2 to exit");
							a=sc.nextInt();
							if(a==1)
							{
								if(f==0)
								{
									Manager.manager_features();
								}
								else
								{
									user_features(UserID);
								}
							}
						}
						else
						{
							System.out.println("Something went wrong");
						}
						break;
					}
					
				}
				if(flag==0)
				{
					System.out.println("Entered room is Not available");
					System.out.println("Press 1 to Retry");
					System.out.println("Press 2 to Exit");
					int c=sc.nextInt();
					if(c==1)
					{
						reserveRoom();
					}
				}	
			}
			else
			{
			int count=0;
			int rId[]=new int[100];
			rId[0]=rs.getInt("ReservationID");
			int c=1;
			while(rs.next())
			{
				rId[c]=rs.getInt("ReservationID");
				c+=1;
			}
				
			for(int d:rId)
			{
				if(d==0)
				{
					break;
				}
				String Q2="Select * from reservations where ReservationID="+d;
				ResultSet rs1=st.executeQuery(Q2);
				rs1.next();
				String rd=rs1.getString("Date");
		        LocalDate dateObj = LocalDate.now();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String date = dateObj.format(formatter);
		        System.out.println(rd+"  "+date);
		        if(rd.equals(date))
		        {
					System.out.println("Room Number : "+rs1.getString("Room_Number"));
					int rn=rs1.getInt("Room_Number");
					System.out.println("Number of Days : "+rs1.getString("Number_of_days"));
					System.out.println("Reservation ID is : "+rs1.getString("ReservationID"));
					count+=1;
					String query1="select max(Booking_ID) from bookings";
					rs1=st.executeQuery(query1);
					rs1.next();
					int k=rs1.getInt(1);
					query1="insert into bookings(Booking_ID,User_Name,CheckIn_date,Room_Id) values("+(k+1)+",'"+UserName+"','"+date+"','"+rn+"')";
					int a=st.executeUpdate(query1);

					query1="UPDATE room_details SET Status='Occupied' WHERE Room_ID="+rn;
					a=st.executeUpdate(query1);
					query1="UPDATE reservations SET Status='Checked-In' WHERE ReservationID="+d;
					a=st.executeUpdate(query1);
					if(a==1)
					{
						
						System.out.println("Check in Successful");
						System.out.println("Press 1 to go to main menu");
						System.out.println("Press 2 to exit");
						a=sc.nextInt();
						if(a==1)
						{
							if(f==0)
							{
								Manager.manager_features();
							}
							else
							{
								user_features(UserID);
							}
						}	
					}
					else
					{
						System.out.println("Something Went Wrong");
					
					}
		        }
			}
			if(count==0)
			{
				System.out.println("You Don't have Reservation Today");
				System.out.println("Enter the Date when you Want to BookRoom in formate(yyyy-mm-dd)");
				String bd=sc.nextLine();
				System.out.println("Enter the Date when you Want to Vacate room in formate(yyyy-mm-dd)");
				String vd=sc.nextLine();
				int[]  arooms=Room_Management.roomAvailability(bd,vd);
				LocalDate bookingDate=LocalDate.parse(bd,formatter);
				LocalDate vacateDate=LocalDate.parse(vd,formatter);
				long rDays=(ChronoUnit.DAYS.between(bookingDate, vacateDate)+1);
				System.out.println("Select Your Choies of Room ID:");
				int ch=sc.nextInt();
				int flag=0;
				for (int j : arooms)
				{
					if(ch==j)
					{
						flag=1;
						String q1="Select max(ReservationID) from reservations";
						ResultSet rs1=st.executeQuery(q1);
						rs1.next();
						int rid=rs1.getInt(1);
						String query1="insert into reservations values("+(rid+1)+",'"+UserName+"','"+bookingDate+"','"+ph_no+"','"+ch+"',"+rDays+",'Checked-In')";
						int a=st.executeUpdate(query1);
						if(a==1)
						{
							System.out.println("Check In Success");
							query1="select max(Booking_ID) from bookings";
							rs1=st.executeQuery(query1);
							rs1.next();
							int k=rs1.getInt(1);
							query1="insert into bookings(Booking_ID,User_Name,CheckIn_date,Room_Id) values("+(k+1)+",'"+UserName+"','"+bookingDate+"','"+ch+"')";
							a=st.executeUpdate(query1);
							query1="UPDATE room_details SET Status='Occupied' WHERE Room_ID="+ch;
							a=st.executeUpdate(query1);

							if(a==1)
							{
								System.out.println("Press 1 to go to main menu");
								System.out.println("Press 2 to exit");
								a=sc.nextInt();
								if(f==0)
								{
									Manager.manager_features();
								}
								else
								{
									user_features(UserID);
								}
							}
							else
							{
							System.out.println("Press 1 to go to main menu");
							System.out.println("Press 2 to exit");
							a=sc.nextInt();
							if(a==1)
							{
								user_features(UserID);
							}
							}
						}
						else
						{
							System.out.println("Something went wrong");
						}
						break;
					}
					
				}
				if(flag==0)
				{
					System.out.println("Entered room is Not available");
					System.out.println("Press 1 to Retry");
					System.out.println("Press 2 to Exit");
					c=sc.nextInt();
					if(c==1)
					{
						reserveRoom();
					}
				}
			}
			}
		}
	}
	static void checkOut(int i,int f) throws Exception
	{
		Scanner sc=new Scanner(System.in);
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		String query= "Select * from user_details where id ="+i;
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
			name=rs.getString("Name");
			UserID=rs.getInt("id");
			UserName=rs.getString("User_Name");
			ph_no=rs.getInt("Phone_Number");
			aadhar_no=rs.getInt("Aadhar_Number");
			address=rs.getString("Address");
			purpose=rs.getString("Purpose_of_visit");
			query= "Select * from bookings where User_Name ='"+UserName+"'";
			rs=st.executeQuery(query);
			if(!(rs.next()))
			{
				System.out.println("You haven't Checked-in to any room Frist you need to Check-in");
				System.out.println("Press 1 to Check-in");
				System.out.println("Press 2 to Exit");
				int c=sc.nextInt();
				if(c==1) {
					checkIn(UserID,0);
				}
			}
			System.out.println("You have Checked into following rooms");
			String Q2="Select * from bookings where User_Name='"+UserName+"'";
			int count=0;
			int b[]=new int[100];
			
			ResultSet rs1=st.executeQuery(Q2);
			while(rs1.next())
			{
				System.out.println("Booking ID is : "+rs1.getString("Booking_ID"));
				b[count]=rs1.getInt("Booking_ID");
				System.out.println("Room Number : "+rs1.getString("Room_Id"));
				System.out.println("Check In Date : "+rs1.getString("CheckIn_date"));
				count+=1;
			}
			if(count==1)
			{
		        LocalDate dateObj = LocalDate.now();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String date = dateObj.format(formatter);
				LocalDate nowDate=LocalDate.parse(date,formatter);
				query= "Select * from bookings where Booking_ID="+b[0];
                                double fbill=generatebill(b[0]);
				ResultSet rs2=st.executeQuery(query);
				rs2.next();
				LocalDate reserveDate=LocalDate.parse(rs2.getString("CheckIn_Date"),formatter);
				long diffDates=ChronoUnit.DAYS.between(reserveDate,nowDate);
				query= "Select price from room_details where Room_ID="+rs2.getInt("Room_Id");
				int room=rs2.getInt("Room_Id");
				rs2=st.executeQuery(query);
				rs2.next();
				float price=rs2.getFloat(1);
				float bill=price*(diffDates+1);
				System.out.println("Your Total bill is :"+fbill);
				String q1="Select max(Report_ID) from report";
				rs2=st.executeQuery(q1);
				rs2.next();
				int rid=rs2.getInt(1)+1;
				System.out.println("Plese Provide Your Review on Room out of 5");
				float rRoom=sc.nextFloat();
				System.out.println("Plese Provide Your Review on All over Hotel out of 5");
				float rHotel=sc.nextFloat();
				query="insert into report values("+rid+",'"+UserName+"',"+room+","+bill+",'"+reserveDate+"','"+nowDate+"','"+purpose+"',"+rRoom+","+rHotel+")";
				int a=st.executeUpdate(query);
				query="DELETE FROM bookings WHERE Booking_ID="+b[0];
				a=st.executeUpdate(query);
				query="DELETE FROM reservations WHERE User_Name='"+UserName+"' AND Date ='"+reserveDate+"' AND Room_Number ="+room;
				a=st.executeUpdate(query);
				if(a==1)
				{
					System.out.println("Check out Succeessfull");
						System.out.println("Press 1 to go to main menu");
						System.out.println("Press 2 to exit");
						a=sc.nextInt();
						if(f==0)
						{
							Manager.manager_features();
						}
						else
						{
							user_features(UserID);
						}
				}
				else
				{
					System.out.println("Something went Wrong");
				}
			}
			else
			{
				System.out.println("Enter the respective booking Id of the room you would like to Check Out");
				int ch=sc.nextInt();
		        LocalDate dateObj = LocalDate.now();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String date = dateObj.format(formatter);
				LocalDate nowDate=LocalDate.parse(date,formatter);
				query= "Select * from bookings where Booking_ID="+ch;
                                double fbill=generatebill(ch);
				ResultSet rs2=st.executeQuery(query);
				rs2.next();
				LocalDate reserveDate=LocalDate.parse(rs2.getString("CheckIn_Date"),formatter);
				long diffDates=ChronoUnit.DAYS.between(reserveDate,nowDate);
				query= "Select price from room_details where Room_ID="+rs2.getInt("Room_Id");
				int room=rs2.getInt("Room_Id");
				rs2=st.executeQuery(query);
				rs2.next();
				float price=rs2.getFloat(1);
				float bill=price*(diffDates+1);
				System.out.println("Your Total bill is :"+fbill);
                                
				String q1="Select max(Report_ID) from report";
				rs2=st.executeQuery(q1);
				rs2.next();
				int rid=rs2.getInt(1)+1;
				System.out.println("Plese Provide Your Review on Room out of 5");
				float rRoom=sc.nextFloat();
				System.out.println("Plese Provide Your Review on All over Hotel out of 5");
				float rHotel=sc.nextFloat();
				query="insert into report values("+rid+",'"+UserName+"',"+room+","+bill+",'"+reserveDate+"','"+nowDate+"','"+purpose+"',"+rRoom+","+rHotel+")";
				int a=st.executeUpdate(query);
				query="DELETE FROM bookings WHERE Booking_ID="+ch;
				a=st.executeUpdate(query);
				query="DELETE FROM reservations WHERE User_Name='"+UserName+"' AND Date ='"+reserveDate+"' AND Room_Number ="+room;
				a=st.executeUpdate(query);
				if(a==1)
				{
					System.out.println("Check out Succeessfull");
						System.out.println("Press 1 to go to main menu");
						System.out.println("Press 2 to exit");
						a=sc.nextInt();
						if(f==0)
						{
							Manager.manager_features();
						}
						else
						{
							user_features(UserID);
						}
				}
				else
				{
					System.out.println("Something went Wrong");
				}
			}
		}

        }
        static double generatebill(int b)throws Exception
        {
            String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
            String uname="root";
            String pass="Eswar.62004";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con =DriverManager.getConnection(url,uname,pass);
            Statement st = con.createStatement();
            LocalDate dateObj = LocalDate.now();
            DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = dateObj.format(form);
            LocalDate nowDate=LocalDate.parse(date,form);
            String query= "Select * from bookings where Booking_ID="+b;
            ResultSet rs2=st.executeQuery(query);
            rs2.next();
            LocalDate reserveDate=LocalDate.parse(rs2.getString("CheckIn_Date"),form);
            long diffDates=ChronoUnit.DAYS.between(reserveDate,nowDate);
            query= "Select price from room_details where Room_ID="+rs2.getInt("Room_Id");
            int room=rs2.getInt("Room_Id");
            rs2=st.executeQuery(query);
            rs2.next();
            float price=rs2.getFloat(1);
            float bill=price*(diffDates+1);
            try{
                FileWriter myWriter=new FileWriter("Bill.txt");
                myWriter.write("\t\t Hotel Bill\n");
                myWriter.write("--------------------------------------");
                myWriter.write("\n  User Name       : "+UserName1);
                myWriter.write("\n  Check In Date       : "+reserveDate);
                myWriter.write("\n  Check out Date      : "+date);
                myWriter.write("\n  Number of Days      : "+(diffDates+1));
                myWriter.write("\n  Room Number         : "+room);
                myWriter.write("\n  Cost of room Per day: "+price);
                myWriter.write("\n  Total cost for room : "+bill);
                myWriter.write("\n---------------------------------------");
                myWriter.write("\n\t\t TAXES");
                myWriter.write("\n  CGST       (12%)    : "+bill*0.12);
                myWriter.write("\n  SGST       (12%)    : "+bill*0.12);
                myWriter.write("\n service Tax (10%)    : "+bill*0.1);
                myWriter.write("\n---------------------------------------");
                myWriter.write("\nTotal Cost              : "+(bill+bill*0.34));
                myWriter.close();
                System.out.println("File Succesfully Created");
                
            } catch (IOException e)
            {
                System.out.println("An error occurred");
            }
            
            return (bill+(bill*0.34));
        }
	static void raiseComplaint(int i)throws Exception
	{
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter your complaint: ");
		String complaint=sc.nextLine();
		String query="UPDATE room_details SET Complaints='"+complaint+"' WHERE Room_ID="+i;
		int co=st.executeUpdate(query);
		if(!(complaint.equals("nill")))
		{
			String soc="Not Done";
			query="UPDATE room_details SET Complaint_Status='"+soc+"' WHERE Room_ID="+i;
			co=st.executeUpdate(query);
			if(co==1)
			{
				System.out.println("succefully updated");
				System.out.println("Enter 1 to go to Main Menu");
				System.out.println("Enter 2 to Exit");
				int ch=sc.nextInt();
				if(ch==1)
				{
					user_features(UserID);
				}
			}
			else
			{
				System.out.println("Something went Worng");
			}
		}
	}

}
