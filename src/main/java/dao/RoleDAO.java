package dao;

import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.DBConnector;
import model.Role;

public class RoleDAO {
	private static RoleDAO self = new RoleDAO();

	private RoleDAO() {
	}

	public static RoleDAO getInstance() {
		return self;
	}

	public boolean insert(Role accountStatus) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "INSERT INTO role(role) VALUES(?)";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, accountStatus.getField("role"));

			if (!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean update(Role accountStatus) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "UPDATE role SET role=? WHERE role_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, accountStatus.getField("role"));
			statement.setInt(2, accountStatus.getID());

			if (!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	public Role search(String role) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM role WHERE role=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, role);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				return new Role(result.getInt("role_id"), result.getString("role"));
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	public Role search(int ID) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM role WHERE role_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setInt(1, ID);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				return new Role(result.getInt("role_id"), result.getString("role"));
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	public void delete() {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "DELETE FROM role";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ArrayList<Role> retrieveAll() {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM role";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			ArrayList<Role> all = new ArrayList<Role>();
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				all.add(new Role(result.getInt("role_id"), result.getString("role")));
			}
			return all;

		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}
}
