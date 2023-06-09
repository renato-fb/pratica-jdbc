package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import db.DB;
import db.DbIntegrityException;

public class Program {
	public static void main(String[] args) 	{
		
		//Retrieving data from data base then deleting it.
		
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		Statement st1 = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
			
			st1 = conn.createStatement();
			rs = st1.executeQuery("select * from seller");
			
			while(rs.next()) {
				System.out.println("Name: " + rs.getString("Name"));
			}
			
			st = conn.prepareStatement("DELETE FROM seller "
					 + "WHERE Name = ?");
			
			System.out.print("Who do you wish to remove from Sellers table?: ");
			String name = sc.nextLine();
			
			st.setString(1,name);
			
			int affectedRows = st.executeUpdate();
			
			System.out.println("Done! Affected rows: " + affectedRows);
		}catch(SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
