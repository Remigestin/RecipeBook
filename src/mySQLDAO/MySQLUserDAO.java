package mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import abstractDAO.AbstractUserDAO;
import businessLogic.User;
import connection.DatabaseConnection;
import exception.DAOException;

public class MySQLUserDAO extends AbstractUserDAO {

	/* Private constructor */
	private MySQLUserDAO() {
	}

	// sql
	private static final String SQL_FIND_BY_USERNAME_AND_PASSWORD = "Select * FROM user WHERE username = ? AND password = ?";

	// actions
	@Override
	public User login(String username, String password) throws DAOException {

		User user = null;

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_BY_USERNAME_AND_PASSWORD);
			st.setString(1, username);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				user = map(rs);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		User.setSession(user);
		return user;
	}

	private static User map(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt("id"));
		user.setUsername(resultSet.getString("username"));
		user.setFirstname(resultSet.getString("firstname"));
		user.setLastname(resultSet.getString("lastname"));
		user.setFavoriteList(MySQLRecipeDAO.getInstance().loadFavoriteRecipe(resultSet.getInt("id")));
		user.setCreateList(MySQLRecipeDAO.getInstance().loadCreateRecipe(resultSet.getInt("id")));
		user.setRandomStarter(MySQLRecipeDAO.getInstance().findRandomRecipe(1));
		user.setRandomMain(MySQLRecipeDAO.getInstance().findRandomRecipe(2));
		user.setRandomDessert(MySQLRecipeDAO.getInstance().findRandomRecipe(3));
		if (resultSet.getInt("isAdmin") == 1) {
			user.setAdmin(true);
		}
		return user;
	}

	public static void main(String args[]) {
		AbstractUserDAO dao = new MySQLUserDAO();

		User marine = dao.login("marine", "123");
		System.out.println(marine.getUsername());

	}

	/* Singleton Holder */
	private static class MySQLUserDAOHolder {
		private final static MySQLUserDAO userDAO = new MySQLUserDAO();
	}

	public static MySQLUserDAO getInstance() {
		return MySQLUserDAOHolder.userDAO;
	}
}