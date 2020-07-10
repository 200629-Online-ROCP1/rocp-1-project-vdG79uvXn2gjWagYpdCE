package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import logger.Logger;

public class DBConnector {
	/** Utility class to create a connection to our database.
	 *  The username and password must exist as environment variables.
	 */

	private static String username = System.getenv("BANKDB_USERNAME");
	private static String password = System.getenv("BANKDB_PASSWORD");
	
	// public int execute(String sql) {  // TODO remove
	// 	// String query = "insert into emps (name, dept, salary) values (?,?,?)";
	// 	// statement = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
	// 	// statement.setString(1, "John");
	// 	// statement.setString(2, "Acc Dept");
	// 	// statement.setInt(3, 10000);
	// 	// statement.executeUpdate();
    //         // rs = pstmt.getGeneratedKeys();
    //         // if(rs != null && rs.next()){
    //         //     System.out.println("Generated Emp Id: "+rs.getInt(1));
    //         // }

	// 	System.out.println("EXECUTING SQL => " + sql);
	// 	try {
	// 		Statement statement = dbconn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	// 		statement.setString(1, "Pending");
	// 		statement.executeUpdate();
	// 		ResultSet resultSet = statement.getGeneratedKeys();
    //         while (resultSet.next()) {
	// 			System.out.println(resultSet.getString("AccountStatus_id"));
    //             System.out.println(resultSet.getString("status"));
	// 		}
	// 		resultSet.close();
	// 		statement.close();
	// 	} catch(SQLException se){
	// 		se.printStackTrace();
	// 	} catch(Exception e){
	// 		e.printStackTrace();
	// 	} finally {
	// 		return 1;
	// 	}
	// }

	public static Connection getConnection(String hostname, String database) throws SQLException {
		String url = "jdbc:postgresql://" + hostname + ":5432/" + database;
		return DriverManager.getConnection(url, username, password);
	}
}