package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.*;
import database.DBConnector;

public class AccountDAO {
	private static AccountDAO self = new AccountDAO();

	private AccountDAO() {
	}

	public static AccountDAO getInstance() {
		return self;
	}

	public boolean insert(Account account) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "INSERT INTO account(uuid, balance, deleted, accounttype, accountholder, accountstatus) VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, account.getField("uuid"));
			statement.setDouble(2, Double.parseDouble(account.getField("balance")));
			statement.setBoolean(3, Boolean.parseBoolean(account.getField("deleted")));
			statement.setInt(4, account.getFKID("accounttype"));
			statement.setInt(5, account.getFKID("accountholder"));
			statement.setInt(6, account.getFKID("accountstatus"));
			
			if (!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean update(Account account) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "UPDATE account SET balance=?, deleted=?, accounttype=?, accountholder=?, accountstatus=? WHERE account_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setDouble(1, Double.parseDouble(account.getField("balance")));
			statement.setBoolean(2, Boolean.parseBoolean(account.getField("deleted")));
			statement.setInt(3, account.getFKID("accounttype"));
			statement.setInt(4, account.getFKID("accountholder"));
			statement.setInt(5, account.getFKID("accountstatus"));
			statement.setInt(6, account.getID());

			if (!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	public Account search(String uuid) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM account WHERE uuid=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, uuid);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("accounttype_id", String.valueOf(result.getInt("accounttype")));
				data.put("accountstatus_id", String.valueOf(result.getInt("accountstatus")));
				data.put("accountholder_id", String.valueOf(result.getInt("accountholder")));
				data.put("balance", String.valueOf(result.getDouble("balance")));
				data.put("deleted", String.valueOf(result.getBoolean("deleted")));
				return new Account(result.getInt("account_id"), data);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	public Account search(int ID) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM account WHERE account_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setInt(1, ID);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("accounttype_id", String.valueOf(result.getInt("accounttype")));
				data.put("accountstatus_id", String.valueOf(result.getInt("accountstatus")));
				data.put("accountholder_id", String.valueOf(result.getInt("accountholder")));
				data.put("balance", String.valueOf(result.getDouble("balance")));
				data.put("deleted", String.valueOf(result.getBoolean("deleted")));
				return new Account(result.getInt("account_id"), data);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	public void deleteAll() {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "DELETE FROM account";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void delete(int ID) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "DELETE FROM account WHERE account_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setInt(1, ID);
			statement.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ArrayList<Account> retrieveAll() {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM account";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			ArrayList<Account> all = new ArrayList<Account>();
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("accounttype_id", String.valueOf(result.getInt("accounttype")));
				data.put("accountstatus_id", String.valueOf(result.getInt("accountstatus")));
				data.put("accountholder_id", String.valueOf(result.getInt("accountholder")));
				data.put("balance", String.valueOf(result.getDouble("balance")));
				data.put("deleted", String.valueOf(result.getBoolean("deleted")));
				all.add(new Account(result.getInt("account_id"), data));
			}
			return all;

		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}
	
	public ArrayList<Account> filter(String fieldName, int ID) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM account WHERE " + fieldName + "=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setInt(1, ID);
			ArrayList<Account> all = new ArrayList<Account>();
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("accounttype_id", String.valueOf(result.getInt("accounttype")));
				data.put("accountstatus_id", String.valueOf(result.getInt("accountstatus")));
				data.put("accountholder_id", String.valueOf(result.getInt("accountholder")));
				data.put("balance", String.valueOf(result.getDouble("balance")));
				data.put("deleted", String.valueOf(result.getBoolean("deleted")));
				all.add(new Account(result.getInt("account_id"), data));
			}
			return all;

		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}
}
