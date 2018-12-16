package abstractDAO;

import java.sql.SQLException;

import businessLogic.User;
import exception.DAOException;

public abstract class AbstractUserDAO {
	
	public abstract User login(String username, String password) throws DAOException;


}
