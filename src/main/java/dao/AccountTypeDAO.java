package dao;

import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.AccountType;
import database.DBConnector;

public class AccountTypeDAO {
	private static AccountTypeDAO self = new AccountTypeDAO();

	private AccountTypeDAO() {
	}

	public static AccountTypeDAO getInstance() {
		return self;
	}

	public boolean insert(AccountType accountType) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "INSERT INTO accounttype(type) VALUES(?)";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, accountType.getField("type"));

			if (!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean update(AccountType accountType) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "UPDATE accounttype SET type=? WHERE accounttype_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, accountType.getField("type"));
			statement.setInt(2, accountType.getID());

			if (!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	public AccountType search(String type) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM accounttype WHERE type=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, type);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				return new AccountType(result.getInt("accounttype_id"), result.getString("type"));
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	public AccountType search(int ID) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM accounttype WHERE accounttype_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setInt(1, ID);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				return new AccountType(result.getInt("accounttype_id"), result.getString("type"));
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	public void delete(int ID) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "DELETE FROM accounttype WHERE accounttype=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setInt(1, ID);
			statement.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void deleteAll() {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "DELETE FROM accounttype";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ArrayList<AccountType> retrieveAll() {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM accounttype";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			ArrayList<AccountType> types = new ArrayList<AccountType>();
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				types.add(new AccountType(result.getInt("accounttype_id"), result.getString("type")));
				System.out.println("Adding to the set");
				System.out.println(types);
			}
			return types;

		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}
}
