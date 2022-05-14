import java.sql.*;
import java.util.Scanner;
public class car_detail {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException,
	ClassNotFoundException{
		try
		{
			car s = new car();
			Scanner input = new Scanner(System.in);
			int choice;
			do {
			System.out.println("1.Insert all details\n 2.Update Details\n 3.Display Details\n 4.Display average price of brand\n 5.Delete the details.\n 6.Exit");
			System.out.println("Enter the choice");
			choice = input.nextInt();
				switch (choice)
				{
					case 1:
						s.getcarDetails();
						s.insert_car_detail();
						break;
					case 2:
						s.update_detail();
						break;
					case 3: 
						s.display_detail();
						break;
					case 4:
						s.display_average_price();
					case 5:
						s.delete_car_detail();
						break;
					case 6:
						break;
				}
			
			}
			while(choice!=6);
		}		
		catch(Exception E)
		{
			System.out.println(E.getMessage());
		}
	
	}
}
class car
{
	private String car_id;
	private String Model_name;
	private String Brand_Name;
	private int price;
	public void getcarDetails() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the Car ID");
		car_id = input.nextLine();
		System.out.println("Enter the Model_Name");
		Model_name = input.nextLine();
		System.out.println("Enter the Brand Name of car");
		Brand_Name = input.nextLine();
		System.out.println("Enter the Price of your Car");
		price = input.nextInt();
	}
	public void insert_car_detail() throws InstantiationException, IllegalAccessException,
	ClassNotFoundException, SQLException {
		//establish a database connection
		dbmsconnection connect = new dbmsconnection();
		Connection con = connect.getConnection("jdbc:mysql://localhost:3306/car", "root","Vinit@2573");
		String sql = "insert into car_detail values (?,?,?,?);";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, car_id);
		stmt.setString(2, Model_name);
		stmt.setString(3, Brand_Name);
		stmt.setInt(4, price);
		stmt.execute();
		System.out.println("Record inserted successfully");
		connect.closeConnection(stmt,con);
		}
	public void update_detail() throws InstantiationException, IllegalAccessException,
	ClassNotFoundException, SQLException {
		dbmsconnection connect = new dbmsconnection();
		Connection con = connect.getConnection("jdbc:mysql://localhost:3306/car", "root","Vinit@2573");
		Statement stmt = con.createStatement();
		System.out.println("Enter the Car ID of car want to update: ");
		Scanner input = new Scanner(System.in);
		String carid = input.nextLine();
		System.out.println("Enter the Model Name of Car want to update: ");
		String Modelname = input.nextLine();
		int result = stmt.executeUpdate("update car_detail set Model_Name ='"+Modelname+"' where car_id='"+carid+"';");
				if(result>0)
				{
					System.out.println("Record updated successfully");
				}
				else
				{
					System.out.println("No such user in the database");
				}
				stmt.close();
				con.close();
		}
	public void display_detail() throws InstantiationException, IllegalAccessException,
	ClassNotFoundException, SQLException {
		dbmsconnection connect = new dbmsconnection();
		Connection con = connect.getConnection("jdbc:mysql://localhost:3306/car", "root","Vinit@2573");
		Statement stmt = con.createStatement();
		System.out.println("Enter the Model Name of which you want see detail: ");
		Scanner input = new Scanner(System.in);
		String modelname = input.nextLine();
		ResultSet rs = stmt.executeQuery("select car_id, Brand_Name, price from car_detail where Model_name='"+modelname+"';");
		if(rs.next()==false)
		{
			System.out.println("No such record found in the Database");
		}
		else
		{
		do
		{
			System.out.println("Car_Id of given Model Name: ");
			System.out.println(rs.getString(1));
			System.out.println("Brand Name of given Model Name: ");
			System.out.println(rs.getString(2));
			System.out.println("price of given Model Name: ");
			System.out.println(rs.getInt(3));
		}while(rs.next());
		}
		stmt.close();
		con.close();
	}
	public void display_average_price() throws InstantiationException, IllegalAccessException,
	ClassNotFoundException, SQLException {
		dbmsconnection connect = new dbmsconnection();
		Connection con = connect.getConnection("jdbc:mysql://localhost:3306/car", "root","Vinit@2573");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT DISTINCT Brand_Name FROM car_detail;");
		int i = 1;
		while(rs.next())
		{
			ResultSet rss = stmt.executeQuery("SELECT AVG(price) FROM car_detail WHERE Brand_Name='"+rs.getString(i)+"';");
			while(rss.next()){
				System.out.println(rss.getString(1));
			}
			i++;
		}
		stmt.close();
		con.close();
	}
	public void delete_car_detail() throws InstantiationException, IllegalAccessException,
	ClassNotFoundException, SQLException {
		dbmsconnection connect = new dbmsconnection();
		Connection con = connect.getConnection("jdbc:mysql://localhost:3306/car", "root","Vinit@2573");
		Statement stmt = con.createStatement();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the Car ID you want to delete: ");
		String del_car_id = input.nextLine();
		int Result = stmt.executeUpdate("Delete from car_detail where car_id = '"+del_car_id+"'");
		if(Result>0)
		{
			System.out.println("Record Deleted Succesfully");
		}
		else {
			System.out.println("No user is found");
		}
		
		stmt.close();
		con.close();
	}
	
}
class dbmsconnection
{
	String url;
	String username;
	String password;
	public Connection getConnection(String url,String username,String password) throws
	InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	Connection con=null;
	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	con = DriverManager.getConnection(url,username,password);
	System.out.println("Connection Established Successfully");
	return con;
	}
	public void closeConnection(Statement stmt,Connection con) throws SQLException {
	stmt.close();
	con.close();
	}

}
