package dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import utils.Password;
import model.AccountHolder;
import database.DBConnector;

public class AccountHolderDAO {
    private static AccountHolderDAO self = new AccountHolderDAO();
    
    private AccountHolderDAO() {}
	
	public static AccountHolderDAO getInstance() {
		return self;
    }
    
    public boolean insert(AccountHolder accountHolder) {
        try {
            Connection dbconn = DBConnector.getConnection();
			String sql = "INSERT INTO accountholder(username, email, firstname, lastname, password, role) VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, accountHolder.getField("username"));
			statement.setString(2, accountHolder.getField("email"));
			statement.setString(3, accountHolder.getField("firstname"));
			statement.setString(4, accountHolder.getField("lastname"));
			statement.setString(5, Password.makeSHA256(accountHolder.getField("password")));
			statement.setInt(6, accountHolder.getRoleID());

			if(!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
        }
        return false;
    }

    public boolean update(AccountHolder accountHolder) {
        try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "UPDATE accountholder SET username=?, email=?, firstname=?, lastname=?, password=?, role=? WHERE accountholder_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, accountHolder.getField("username"));
			statement.setString(2, accountHolder.getField("email"));
			statement.setString(3, accountHolder.getField("firstname"));
			statement.setString(4, accountHolder.getField("lastname"));
			statement.setString(5, Password.makeSHA256(accountHolder.getField("password")));
			statement.setInt(6, accountHolder.getRoleID());
            statement.setInt(7, accountHolder.getID());
			
			if(!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
        }
        return false;
    }

	public AccountHolder searchUsername(String username) {
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
				return new AccountHolder(result.getInt("accountholder_id"), data);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
        return null;

	}

	public AccountHolder search(int ID) {
		try {
			Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM accountholder WHERE accountholder_id=?";
            PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setInt(1, ID);
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("role_id", String.valueOf(result.getInt("role")));
				data.put("username", result.getString("username"));
				data.put("firstname", result.getString("firstname"));
				data.put("lastname", result.getString("lastname"));
				data.put("password", result.getString("password"));
				data.put("email", result.getString("email"));
				return new AccountHolder(result.getInt("accountholder_id"), data);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
        return null;

	}
    public void delete() {
        try {
            Connection dbconn = DBConnector.getConnection();
			String sql = "DELETE FROM accountholder";
            PreparedStatement statement = dbconn.prepareStatement(sql);
            statement.execute();
		}catch(SQLException e) {
			System.out.println(e);
		}
    }
}
