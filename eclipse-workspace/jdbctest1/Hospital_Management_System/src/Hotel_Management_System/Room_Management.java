package Hotel_Management_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
public class Room_Management 
{
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
	static int roomid;
	static String type;
	static String status;
	static String complaint;
	static String soc;
	static double price;
	static int floorNumber;
	static void updateRoomDetails() throws Exception
	{

		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the room number you would like to edit");
		int id=sc.nextInt();
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		String query= "Select * from room_details where Room_ID ="+id;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(!(rs.next()))
		{
			
			
			System.out.println("Invalid Room id Avalible rooms are");
			String query1= "Select Room_ID from room_details";
			ResultSet rs1 = st.executeQuery(query1);
			int i=0;
			int rooms[]=new int[100];
			while(rs1.next())
			{
				rooms[i]=rs1.getInt("Room_ID");
				i+=1;
			}
			for(int j:rooms)
			{
				System.out.println(j);
			}
			System.out.println("Press 1 to Retry");
			System.out.println("Press 2 to Exit");
			int c=sc.nextInt();
			if(c==1)
			{
				updateRoomDetails();
			}
		}
		else
		{
			roomid=id;
			type=rs.getString("Type");
			status=rs.getString("Status");
			complaint=rs.getString("Complaints");
			soc=rs.getString("Complaint_Status");
			price=rs.getFloat("price");
			floorNumber=rs.getInt("Floor Number");
			System.out.println("what Would you like to update");
			System.out.println("1. Type of room");
			System.out.println("2. Occupied Status of room");
			System.out.println("3. Add Complaint");
			System.out.println("4. Update Status of Complaint");
			System.out.println("5. Update Price of room");
			int c=sc.nextInt();
			if(c==1)
			{
				System.out.println("Current Room type is: "+type);
				sc.nextLine();
				System.out.println("Enter now the room is AC or Non-AC");
				type=sc.nextLine();
				query="UPDATE room_details SET Type='"+type+"' WHERE Room_ID="+roomid;
				int co=st.executeUpdate(query);
				if(co==1)
				{
					System.out.println("succefully updated");
					System.out.println("Enter 1 to Update other Feild");
					System.out.println("Enter 2 to Exit");
					int ch=sc.nextInt();
					if(ch==1)
					{
						updateRoomDetails();
					}
				}
				else
				{
					System.out.println("Something went Worng");
				}
			}
			else if(c==2)
			{
				System.out.println("Currently Room is: "+status);
				sc.nextLine();
				System.out.println("Enter weather the room is Occupied or Free");
				status=sc.nextLine();
				query="UPDATE room_details SET Status='"+status+"' WHERE Room_ID="+roomid;
				int co=st.executeUpdate(query);
				if(co==1)
				{
					System.out.println("succefully updated");
					System.out.println("Enter 1 to Update other Feild");
					System.out.println("Enter 2 to Exit");
					int ch=sc.nextInt();
					if(ch==1)
					{
						updateRoomDetails();
					}
				}
				else
				{
					System.out.println("Something went Worng");
				}
			}
			else if(c==3)
			{
				System.out.println("Current Room Required : "+complaint);
				sc.nextLine();
				System.out.println("Enter your complaint: ");
				complaint=sc.nextLine();
				query="UPDATE room_details SET Complaints='"+complaint+"' WHERE Room_ID="+roomid;
				int co=st.executeUpdate(query);
				if(!(complaint.equals("nill")))
				{
					soc="Not Done";
					query="UPDATE room_details SET Complaint_Status='"+soc+"' WHERE Room_ID="+roomid;
					co=st.executeUpdate(query);
				if(co==1)
				{
					System.out.println("succefully updated");
					System.out.println("Enter 1 to Update other Feild");
					System.out.println("Enter 2 to Exit");
					int ch=sc.nextInt();
					if(ch==1)
					{
						updateRoomDetails();
					}
				}
				else
				{
					System.out.println("Something went Worng");
				}
			}
			}
			else if(c==4)
			{
				System.out.println("Currently Room Complaint Status is:"+soc);
				sc.nextLine();
				System.out.println("Enter whether the Service is Done or Not Done");
				soc=sc.nextLine();
				query="UPDATE room_details SET Complaint_Status='"+soc+"' WHERE Room_ID="+roomid;
				int co=st.executeUpdate(query);
				if(soc.equals("Done"))
				{
					query="UPDATE room_details SET Complaints='nill' WHERE Room_ID="+roomid;
					co=st.executeUpdate(query);
				}
				if(co==1)
				{
					System.out.println("succefully updated");
					System.out.println("Enter 1 to Update other Feild");
					System.out.println("Enter 2 to Exit");
					int ch=sc.nextInt();
					if(ch==1)
					{
						updateRoomDetails();
					}
				}
				else
				{
					System.out.println("Something went Worng");
				}
			}
			else if(c==5)
			{
				System.out.println("Current Room Price is: "+price);
				System.out.println("Enter the New Price of room");
				price=sc.nextInt();
				query="UPDATE room_details SET price='"+price+"' WHERE Room_ID="+roomid;
				int co=st.executeUpdate(query);
				if(co==1)
				{
					System.out.println("succefully updated");
					System.out.println("Enter 1 to Update other Feild");
					System.out.println("Enter 2 to Exit");
					int ch=sc.nextInt();
					if(ch==1)
					{
						updateRoomDetails();
					}
				}
				else
				{
					System.out.println("Something went Worng");
				}
			}
		}
		sc.close();
	}
	static void updatecomplaintstatus() throws Exception
	{
		Scanner sc=new Scanner(System.in);
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		System.out.println("Currently Room Complaint Status is:"+soc);
		sc.nextLine();
		System.out.println("Enter whether the Service is Done or Not Done");
		soc=sc.nextLine();
		String query="UPDATE room_details SET Complaint_Status'"+soc+"' WHERE Room_ID="+roomid;
		int co=st.executeUpdate(query);
		if(soc.equals("Done"))
		{
			query="UPDATE room_details SET Complaints='nill' WHERE Room_ID="+roomid;
			co=st.executeUpdate(query);
		}
		if(co==1)
		{
			System.out.println("succefully updated");
		}
		sc.close();
	}
	static int[] roomAvailability(String bd,String vd) throws Exception
	{
		LocalDate bookingDate=LocalDate.parse(bd,formatter);
		LocalDate vacateDate=LocalDate.parse(vd,formatter);
		long rDays=(ChronoUnit.DAYS.between(bookingDate, vacateDate)+1);
		System.out.println("Available rooms        Type        Floor Number         Price");
		String url="jdbc:mysql://localhost:3306/hoteldb?autoReconnect=true&useSSL=false";
		String uname="root";
		String pass="Eswar.62004";
		String query= "Select Room_ID from room_details";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		int arooms[]=new int[100];
		int i=0;
		int rooms[]=new int[100];
		while(rs.next())
		{
			rooms[i]=rs.getInt("Room_ID");
			i+=1;
		}
		int flag2=0;
		for (int k:rooms)
		{
			if(k==0)
			{
				break;
			}
			int roomid=k;
			query="select * from reservations where Room_Number='"+roomid+"'";
			ResultSet rs1=st.executeQuery(query);
			int flag=0;
			while(rs1.next())
			{
				LocalDate reserveDate=LocalDate.parse(rs1.getString("Date"),formatter);
				int d=rs1.getInt("Number_of_days");
				long diffDates=ChronoUnit.DAYS.between(bookingDate, reserveDate);
				if(diffDates>=0)
				{
					if(diffDates<rDays)
					{
						flag=1;
					}
				}
				else
				{
					if((-1*diffDates)<d)
					{
						flag=1;
					}
				}
			
			}
			if(flag==0)
			{
				flag2=1;
				System.out.print("\t"+roomid);
				query= "Select * from room_details where Room_ID="+roomid;
				rs1=st.executeQuery(query);
				rs1.next();
				System.out.print("\t\t"+rs1.getString("Type"));
				System.out.print("\t\t"+rs1.getInt("Floor Number"));
				System.out.println("\t\t"+(rs1.getFloat("price")*rDays));
				arooms[i]=roomid;
				i+=1;		
			}
		}
		if(flag2==0)
		{
			System.out.println("Sorry All rooms are booked");
			System.out.println("Thank you for visiting");
		}
		return arooms;
	}
}