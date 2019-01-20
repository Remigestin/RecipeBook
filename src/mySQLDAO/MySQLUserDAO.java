package mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import abstractDAO.AbstractUserDAO;
import businessLogic.User;
import connection.DatabaseConnection;
import exception.DAOException;
/**
 * 
 * @author MISSOUM BENZIANE Ines
 * @author gestin remi
 *
 */
public class MySQLUserDAO extends AbstractUserDAO {

	/* Private constructor */
	private MySQLUserDAO() {
	}

	// sql
	private static final String SQL_FIND_BY_USERNAME_AND_PASSWORD = "Select * FROM user WHERE username = ? AND password = ?";
	private static final String SQL_CREATE_USER = "INSERT INTO user VALUES (NULL, ?,?,?,?,false)";
	private static final String SQL_DELETE_USER = "DELETE FROM user WHERE id = ?";
	private static final String SQL_EDIT_USER = "UPDATE user SET password = ?, firstname = ?, lastname = ? WHERE id = ?";
	private static final String SQL_FIND_USERNAME = "Select * FROM user WHERE username = ?";
	private static final String SQL_FIND_PASSWORD = "Select password FROM user WHERE id = ?";
	private static final String SQL_INSERT_NEW_FAVORITE_RECIPE = "INSERT INTO favoritelist VALUES (?,?)";
	private static final String SQL_DELETE_A_FAVORITE_RECIPE = "DELETE FROM favoritelist WHERE iduser = ? and idrecipe = ?";

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

	@Override
	public void register(String firstname, String lastname, String username, String password) throws DAOException {

		User user = null;

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_CREATE_USER);
			st.setString(1, username);
			st.setString(2, password);
			st.setString(3, lastname);
			st.setString(4, firstname);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}
	
	@Override
	public void addFavoriteRecipe(int idUser, int idRecipe) throws DAOException {

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_INSERT_NEW_FAVORITE_RECIPE);
			st.setInt(1, idUser);
			st.setInt(2, idRecipe);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	@Override
	public void removeFavoriteRecipe(int idUser, int idRecipe) throws DAOException {
		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_DELETE_A_FAVORITE_RECIPE);
			st.setInt(1, idUser);
			st.setInt(2, idRecipe);
			st.executeUpdate();

			User.getSession().setFavoriteList(MySQLRecipeDAO.getInstance().loadFavoriteRecipe(idUser));
		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}
	
	/* Singleton Holder */
	private static class MySQLUserDAOHolder {
		private final static MySQLUserDAO userDAO = new MySQLUserDAO();
	}

	public static MySQLUserDAO getInstance() {
		return MySQLUserDAOHolder.userDAO;
	}

	@Override
	public boolean findUsername(String username) throws DAOException {

		boolean usernameFound = false;

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_USERNAME);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				usernameFound = true;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return usernameFound;
	}

	@Override
	public String findPassword(int idUser) throws DAOException {
		
		String password = "";
		
		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_PASSWORD);
			st.setInt(1, idUser);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				password = rs.getString("password");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return password;
	}
	@Override
	public void deleteAccount(int idUser) throws DAOException {
		User user = null;

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_DELETE_USER);
			st.setInt(1, idUser);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}		
		
	}

	@Override
	public void editAccount(User user) throws DAOException {
	
		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_EDIT_USER);		
			st.setString(1, user.getPassword());
			st.setString(2, user.getFirstname());
			st.setString(3, user.getLastname());
			st.setInt(4, user.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}
}