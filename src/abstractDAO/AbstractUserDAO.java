package abstractDAO;

import businessLogic.User;
import exception.DAOException;

public abstract class AbstractUserDAO {

	public abstract User login(String username, String password) throws DAOException;
	public abstract void register(String firstname, String lastname, String username, String password) throws DAOException;
	public abstract void deleteAccount(int idUser) throws DAOException;
	public abstract void editAccount(User user) throws DAOException;
	public abstract boolean findUsername(String username) throws DAOException;
	public abstract void addFavoriteRecipe(int idUser, int idRecipe)throws DAOException;
	public abstract void removeFavoriteRecipe(int idUser, int idRecipe)throws DAOException;

}