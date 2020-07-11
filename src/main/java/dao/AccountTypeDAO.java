package dao;

import java.util.Set;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.AccountType;
import database.DBConnector;

public class AccountTypeDAO {
    private static AccountTypeDAO self = new AccountTypeDAO();
    
    private AccountTypeDAO() {}
	
	public static AccountTypeDAO getInstance() {
		return self;
    }
    
    public boolean insert(AccountType accountType) {
        try {
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "INSERT INTO accounttype(type) VALUES(?)";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, accountType.getField("type"));
			
			if(!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
        }
        return false;
    }

    public boolean update(AccountType accountType) {
        try {
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "UPDATE accounttype SET type=? WHERE accounttype_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
            statement.setString(1, accountType.getField("type"));
            statement.setInt(2, accountType.getID());
			
			if(!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
        }
        return false;
    }

    public AccountType search(String type) {
		try {
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "SELECT * FROM accounttype WHERE type=?";
            PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, type);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return new AccountType(result.getInt("accounttype_id"), result.getString("type"));
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}
        return null;
    }

    public void delete() {
        try {
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "DELETE FROM accounttype";
            PreparedStatement statement = dbconn.prepareStatement(sql);
            statement.execute();
		}catch(SQLException e) {
			System.out.println(e);
		}
    }

    public boolean insertStatement(AccountType accountType) {
        // TODO Auto-generated method stub
        return false;
    }

    public AccountType findByFirstName(String firstName) {
        // TODO Auto-generated method stub
        return null;
    }

    public Set<AccountType> selectAll() {
        // TODO Auto-generated method stub
        return null;
    }

}
