package dao;

import java.util.Set;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.AccountStatus;
import database.DBConnector;

public class AccountStatusDAO {
    private static AccountStatusDAO self = new AccountStatusDAO();
    
    private AccountStatusDAO() {}
	
	public static AccountStatusDAO getInstance() {
		return self;
    }
    
    public boolean insert(AccountStatus accountStatus) {
        try {
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
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
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
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
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
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

    public void delete() {
        try {
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "DELETE FROM accountstatus";
            PreparedStatement statement = dbconn.prepareStatement(sql);
            statement.execute();
		}catch(SQLException e) {
			System.out.println(e);
		}
    }
}