package dao;

import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.DBConnector;
import model.AccountStatus;

public class AccountStatusDAO {
    private static AccountStatusDAO self = new AccountStatusDAO();
    
    private AccountStatusDAO() {}
	
	public static AccountStatusDAO getInstance() {
		return self;
    }
    
    public boolean insert(AccountStatus accountStatus) {
        try {
            Connection dbconn = DBConnector.getConnection();
			String sql = "INSERT INTO accountstatus(status) VALUES(?)";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, accountStatus.getField("status"));
			
			if(!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
        }
        return false;
    }

    public boolean update(AccountStatus accountStatus) {
        try {
            Connection dbconn = DBConnector.getConnection();
			String sql = "UPDATE accountstatus SET status=? WHERE accountstatus_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
            statement.setString(1, accountStatus.getField("status"));
            statement.setInt(2, accountStatus.getID());
			
			if(!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
        }
        return false;
    }

    public AccountStatus search(String status) {
		try {
            Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM accountstatus WHERE status=?";
            PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, status);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return new AccountStatus(result.getInt("accountstatus_id"), result.getString("status"));
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}
        return null;
	}
	
	public AccountStatus search(int ID) {
		try {
            Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM accountstatus WHERE accountstatus_id=?";
            PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setInt(1, ID);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return new AccountStatus(result.getInt("accountstatus_id"), result.getString("status"));
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}
        return null;
    }

    public void delete() {
        try {
            Connection dbconn = DBConnector.getConnection();
			String sql = "DELETE FROM accountstatus";
            PreparedStatement statement = dbconn.prepareStatement(sql);
            statement.execute();
		}catch(SQLException e) {
			System.out.println(e);
		}
    }
    
    public ArrayList<AccountStatus> retrieveAll() {
    	try {
            Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM accountstatus";
            PreparedStatement statement = dbconn.prepareStatement(sql);
            ArrayList<AccountStatus> all = new ArrayList<AccountStatus>();
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				all.add(new AccountStatus(result.getInt("accountstatus_id"), result.getString("status")));
			}
			return all;
			
		}catch(SQLException e) {
			System.out.println(e);
		}
        return null;
    }
}