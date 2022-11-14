
package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MSSQLConnection {
	public static Connection getJDBCConnection() {
		final String user = "sa";
		final String password = "1122288";
		final String url = "jdbc:sqlserver://localhost:1433;databasename=TheKaraoke;user="+ user + ";password="+password;
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
