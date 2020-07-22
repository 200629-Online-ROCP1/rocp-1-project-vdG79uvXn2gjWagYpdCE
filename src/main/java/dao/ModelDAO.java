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
	
	public void delete() {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "DELETE FROM " + tablename;
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	public void delete(int ID) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "DELETE FROM " + tablename + " WHERE " + tablename + "=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setInt(1, ID);
			statement.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
