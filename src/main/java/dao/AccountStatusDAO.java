package dao;

import java.util.Set;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import model.AccountStatus;
import database.DBConnector;

public class AccountStatusDAO implements AccountStatusDAOInterface {
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
			statement.setString(1, accountStatus.Field("status"));
			
			System.out.println(statement);
            
			if(!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
        }
        return false;
    }

    public boolean insertStatement(AccountStatus celeb) {
        // TODO Auto-generated method stub
        return false;
    }

    public AccountStatus findByFirstName(String firstName) {
        // TODO Auto-generated method stub
        return null;
    }

    public Set<AccountStatus> selectAll() {
        // TODO Auto-generated method stub
        return null;
    }
}