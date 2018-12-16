package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
	
	public static void main(String args[]) throws SQLException {
		DatabaseConnection dc = DatabaseConnection.getInstance();
		Connection c = dc.getConnection();
		
	
		final String REQUETE = "Select * FROM User";
		
		try {
			
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery(REQUETE);
			while (rs.next()) {
				String rl = rs.getString(2);
				System.out.println(rl);
			}
		} finally {
			if (c != null)
				c.close();
		}
		
		
	}

}
