package abstractDAO;

import businessLogic.User;
import exception.DAOException;

public abstract class AbstractUserDAO {

	public abstract User login(String username, String password) throws DAOException;
	public abstract void addFavoriteRecipe(int idUser, int idRecipe)throws DAOException;

}