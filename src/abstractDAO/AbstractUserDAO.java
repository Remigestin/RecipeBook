package abstractDAO;

import businessLogic.User;
import exception.DAOException;

/**
 * @author Chawaf Alia
 * @author Gestin Remi
 * @author MISSOUM BENZIANE Ines
 * @version 1.0
 */
public abstract class AbstractUserDAO {

	/**
	 * Try to login with the username and password in parameter
	 * @param username
	 * @param password
	 * @return the user who is logged with his favorite recipes, random recipes and
	 *         his created recipes, otherwise return null if login has failed
	 * @throws DAOException
	 */
	public abstract User login(String username, String password) throws DAOException;

	/**
	 * Create a new account with the information in parameter
	 * @param firstname First name of the new user
	 * @param lastname Last name of the new user
	 * @param username Unique username of the new user
	 * @param password Password of the new user
	 * @throws DAOException
	 */
	public abstract void register(String firstname, String lastname, String username, String password)
			throws DAOException;

	/**
	 * Delete the account of the user in parameter
	 * @param idUser id of the user to delete
	 * @throws DAOException
	 */
	public abstract void deleteAccount(int idUser) throws DAOException;

	/**
	 * Edit information account of the user in parameter 
	 * @param user
	 * @throws DAOException
	 */
	public abstract void editAccount(User user) throws DAOException;

	/**
	 * Check if a username is already used or not
	 * @param username
	 * @return true if the username exists in the database, otherwise return false
	 * @throws DAOException
	 */
	public abstract boolean findUsername(String username) throws DAOException;

	/**
	 * Find the password of a user from database
	 * @param idUser id of the user for who we need the password
	 * @return the password of the user in parameter
	 * @throws DAOException
	 */
	public abstract String findPassword(int idUser) throws DAOException;

	/**
	 * in the DB : add a recipe in the favorites of a user
	 * 
	 * @param idUser   id of the user
	 * @param idRecipe id of the recipe to be added
	 * @throws DAOException
	 */
	public abstract void addFavoriteRecipe(int idUser, int idRecipe) throws DAOException;

	/**
	 * in the DB : remove a recipe from the favorites of a user
	 * 
	 * @param idUser   id of the user
	 * @param idRecipe id of the recipe to be removed
	 * @throws DAOException
	 */
	public abstract void removeFavoriteRecipe(int idUser, int idRecipe) throws DAOException;

}