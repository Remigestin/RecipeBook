package abstractDAO;

import businessLogic.User;
import exception.DAOException;
/**
 * 
 * @author MISSOUM BENZIANE Ines
 * @author Gestin Remi
 *
 */
public abstract class AbstractUserDAO {

	/**
	 * 
	 * @param username
	 * @param password
	 * @return the user who is logged with his favorite recipes, random recipes and his created recipes.
	 * @throws DAOException
	 */
	public abstract User login(String username, String password) throws DAOException;
	public abstract void register(String firstname, String lastname, String username, String password) throws DAOException;
	public abstract void deleteAccount(int idUser) throws DAOException;
	public abstract void editAccount(User user) throws DAOException;
	public abstract boolean findUsername(String username) throws DAOException;
	public abstract String findPassword(int idUser) throws DAOException;
	
	/**
	 * in the DB : add a recipe in the favorites of a user
	 * @param idUser id of the user
	 * @param idRecipe id of the recipe to be added
	 * @throws DAOException
	 */
	public abstract void addFavoriteRecipe(int idUser, int idRecipe)throws DAOException;
	
	/**
	 * in the DB : remove a recipe from the favorites of a user
	 * @param idUser id of the user
	 * @param idRecipe id of the recipe to be removed
	 * @throws DAOException
	 */
	public abstract void removeFavoriteRecipe(int idUser, int idRecipe)throws DAOException;

}