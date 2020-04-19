package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LabReport {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/labreport?useTimezone=true&serverTimezone=UTC","root","");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertLabReport(String num, String name, String det) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into labreports(`lReportID`,`lReportNum`,`lReportName`,`lReportDet`)"
					+ " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, num);
			preparedStmt.setString(3, name);

			preparedStmt.setString(4, det);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Lab Report Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the labreport.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readLabReports() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Lab Report Number</th><th>Lab Report Name</th><th>Lab Report Details</th></tr>";
			String query = "select * from labreports";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String lReportID = Integer.toString(rs.getInt("lReportID"));
				String lReportNum = rs.getString("lReportNum");
				String lReportName = rs.getString("lReportName");
				
				String lReportDet = rs.getString("lReportDet");
				// Add into the html table
				output += "<tr><td>" + lReportNum + "</td>";
				output += "<td>" + lReportName + "</td>";
				
				output += "<td>" + lReportDet + "</td>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateLabReport(String ID, String num, String name, String det) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE labreports SET lReportNum=?,lReportName=?,lReportDet=?WHERE lReportID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, num);
			preparedStmt.setString(2, name);
			
			preparedStmt.setString(3, det);
			preparedStmt.setInt(4, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfullly";
		} catch (Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteLabReport(String lReportID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from labreports where lReportID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(lReportID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	


}
