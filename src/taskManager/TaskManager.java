package taskManager;

import java.util.*;
import java.sql.*;

public class TaskManager {
	
	public static final String dbUrl = "jdbc:mysql://localhost:3306/task_manager";
	public static final String username = "root";
	public static final String pass = "1234";
	public static Connection mConn = null;
	
	public static void createConnection() {
		try {
			mConn = DriverManager.getConnection(dbUrl,username,pass);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static void showTask() {
		System.out.println("Your Task Status\n");
		String query = "select * from tasks";
		try {
			Statement s = mConn.createStatement();
			ResultSet rs = s.executeQuery(query);
			
			if(!rs.next()) {
				System.out.println("No Tasks to show");
				return ;
			}
			System.out.println(rs.getString(1) + "  |  " + rs.getString(2));
			while(rs.next()) {
				System.out.println(rs.getString(1) + "  |  " + rs.getString(2));
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void setTask() {
		System.out.println("Enter the task you want to do ??");
		Scanner sc = new Scanner(System.in);
		String t = sc.nextLine();
		try {
			String query = "insert into tasks(todo,status)" + "values(?,'pending')";
			PreparedStatement ps = mConn.prepareStatement(query);
			ps.setString(1,t);
			
			ps.execute();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Task added to the List\n");
		
		showTask();
		
	}
	
	public static void deleteTask() {
		String query = "Delete from tasks where todo = ?";
		
		System.out.println("What do you want to Delete ??\n");
		
		showTask();
		Scanner sc = new Scanner(System.in);
		String s = sc.next();
		
		try {
			PreparedStatement ps = mConn.prepareStatement(query);
			ps.setString(1,s );
			ps.execute();
		}catch(Exception e) {
			System.out.println(e);
		}
		
		System.out.println("Task Deleted");
		
	}
	
	public static void checkStatus() {
		
		showTask();
		
		String query1 = "select count(*) from tasks where status = 'done'";
		String query2 = "select count(*) from tasks";
		try {
			Statement st1 = mConn.createStatement();
			ResultSet rs1 = st1.executeQuery(query1);
			rs1.next();
			int c1 = rs1.getInt(1);
			Statement st2 = mConn.createStatement();
			ResultSet rs2 = st2.executeQuery(query2);
			rs2.next();
			int c2 = rs2.getInt(1);
			
			System.out.println("\nTask Completed : " + c1);
			System.out.println("Task Ramaining :" + c2);
			
			float score = (float)c1/c2 * 100;
			
			System.out.println("Total Score = " + score + "%");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static void updateTask() {
		showTask();
		
		System.out.println();
		System.out.println("Enter the task name to update");
		Scanner sc = new Scanner(System.in);
		String s = sc.next();
		try {
			String query = "update tasks set status = 'done' where todo = ?";
			PreparedStatement ps = mConn.prepareStatement(query);
			ps.setString(1,s);
			ps.execute();
			System.out.println("Status updated successfully\n");
			showTask();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static void clearTasks() {
		String s = "delete from tasks";
		try {
			Statement st = mConn.createStatement();
			st.executeUpdate(s);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		System.out.println("Task List Cleared");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createConnection();
		
		System.out.println("--MY__TASK__MANAGER\n");
		
		System.out.println("1. Set Task");
		System.out.println("2. Delete Task");
		System.out.println("3. Show Tasks");
		System.out.println("4. Update Task");
		System.out.println("5. Clear TaskList");
		System.out.println("6. Show Status");
		System.out.println("7. Exit");
		
		
		
		while(true) {
			System.out.println("\nEnter your choice :");
			Scanner sc = new Scanner(System.in);
			int ch;
			try {
				ch = sc.nextInt();
			}catch(Exception e) {
				System.out.println("Please give valid input");
				continue;
			}
			
			switch(ch) {
			
			case 1:		setTask();
						break;
						
			case 2:		deleteTask();
						break;
						
			case 3:		showTask();
						break;
						
			case 4:		updateTask();
						break;
						
			case 5: 	clearTasks();
						break;
						
			case 6: 	checkStatus();
						break;
						
			case 7:		System.out.println("See you next time");
						System.exit(0);
						
			default:	System.out.println("_____Wrong Choice______");
			}
		}
	}

}
