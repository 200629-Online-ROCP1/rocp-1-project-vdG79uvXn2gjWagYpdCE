package dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import utils.Password;
import model.Account;
import database.DBConnector;

public class AccountDAO {
    private static AccountDAO self = new AccountDAO();
    
    private AccountDAO() {}
	
	public static AccountDAO getInstance() {
		return self;
    }
    
    public boolean insert(Account account) {
        try {
            Connection dbconn = DBConnector.getConnection();
			String sql = "INSERT INTO accountholder(username, email, firstname, lastname, password, role) VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, account.getField("username"));
			statement.setString(2, account.getField("email"));
			statement.setString(3, account.getField("firstname"));
			statement.setString(4, account.getField("lastname"));
			statement.setString(5, Password.makeSHA256(account.getField("password")));
			statement.setInt(6, account.getRoleID());

			if(!statement.execute()) {
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
			String sql = "UPDATE accountholder SET username=?, email=?, firstname=?, lastname=?, password=?, role=? WHERE accountholder_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, account.getField("username"));
			statement.setString(2, account.getField("email"));
			statement.setString(3, account.getField("firstname"));
			statement.setString(4, account.getField("lastname"));
			statement.setString(5, Password.makeSHA256(account.getField("password")));
			statement.setInt(6, account.getRoleID());
            statement.setInt(7, account.getID());
			
			if(!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
        }
        return false;
    }

	public Account searchUsername(String username) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM accountholder WHERE username=?";
            PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("role_id", String.valueOf(result.getInt("role")));
				data.put("username", result.getString("username"));
				data.put("firstname", result.getString("firstname"));
				data.put("lastname", result.getString("lastname"));
				data.put("password", result.getString("password"));
				data.put("email", result.getString("email"));
				return new Account(result.getInt("accountholder_id"), data);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
        return null;

	}

	public void truncate() {
		try {
            Connection dbconn = DBConnector.getConnection();
			String sql = "TRUNCATE TABLE account RESTART IDENTITY;";
            PreparedStatement statement = dbconn.prepareStatement(sql);
            statement.execute();
		}catch(SQLException e) {
			System.out.println(e);
		}
	}

    public void delete(int ID) {
        try {
            Connection dbconn = DBConnector.getConnection();
			String sql = "DELETE FROM accountholder WHERE account_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setInt(1, ID);
            statement.execute();
		}catch(SQLException e) {
			System.out.println(e);
		}
    }
}
