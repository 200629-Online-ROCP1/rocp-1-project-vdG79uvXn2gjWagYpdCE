package dao;

import java.util.Set;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Role;
import database.DBConnector;

public class RoleDAO {
    private static RoleDAO self = new RoleDAO();
    
    private RoleDAO() {}
	
	public static RoleDAO getInstance() {
		return self;
    }
    
    public boolean insert(Role accountStatus) {
        try {
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "INSERT INTO role(role) VALUES(?)";
			PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, accountStatus.getField("role"));
			
			if(!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
        }
        return false;
    }

    public boolean update(Role accountStatus) {
        try {
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "UPDATE role SET role=? WHERE role_id=?";
			PreparedStatement statement = dbconn.prepareStatement(sql);
            statement.setString(1, accountStatus.getField("role"));
            statement.setInt(2, accountStatus.getID());
			
			if(!statement.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
        }
        return false;
    }

    public Role search(String role) {
		try {
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "SELECT * FROM role WHERE role=?";
            PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setString(1, role);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return new Role(result.getInt("role_id"), result.getString("role"));
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}
        return null;
	}
	
	public Role searchID(String ID) {
		int id = Integer.parseInt(ID);
		try {
            Connection dbconn = DBConnector.getConnection();
			String sql = "SELECT * FROM role WHERE role_id=?";
            PreparedStatement statement = dbconn.prepareStatement(sql);
			statement.setInt(1, id);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return new Role(result.getInt("role_id"), result.getString("role"));
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}
        return null;
    }

    public void delete() {
        try {
            Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
			String sql = "DELETE FROM role";
            PreparedStatement statement = dbconn.prepareStatement(sql);
            statement.execute();
		}catch(SQLException e) {
			System.out.println(e);
		}
    }
}
