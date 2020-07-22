package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class ModelDAO {
	private String tablename;

	protected ModelDAO(String tablename) { 
		this.tablename = tablename;
	}
	
	/**
	 * Deletes all rows in the table
	 */
	public void delete() {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "DELETE FROM " + tablename;
			PreparedStatement statement = dbconn.prepareStatement(sql);
			System.out.println(statement);
			statement.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	/**
	 * Deletes a single row in the table identified by ID
	 * @param ID
	 */
	public void delete(int ID) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "DELETE FROM " + tablename + " WHERE " + tablename + "_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setInt(1, ID);
			System.out.println(statement);
			statement.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
