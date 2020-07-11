package dao;

import java.util.Set;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "INSERT INTO accountholder(status) VALUES(?)";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, accountHolder.getField("status"));
			
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
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "UPDATE accountholder SET status=? WHERE accountholder_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
            statement.setString(1, accountHolder.getField("status"));
            statement.setInt(2, accountHolder.getID());
			
			if(!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
        }
        return false;
    }

    public AccountHolder search(String status) {
		try {
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "SELECT * FROM accountholder WHERE status=?";
            PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, status);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return new AccountHolder(result.getInt("accountholder_id"), result.getString("status"));
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}
        return null;
    }

    public void delete() {
        try {
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "DELETE FROM accountholder";
            PreparedStatement statement = dbconn.prepareStatement(sql);
            statement.execute();
		}catch(SQLException e) {
			System.out.println(e);
		}
    }
}
