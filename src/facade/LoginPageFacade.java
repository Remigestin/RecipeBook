package facade;

import abstractDAO.AbstractUserDAO;
import businessLogic.User;
import factory.AbstractFactory;
import factory.MySQLFactory;

public class LoginPageFacade {

	public User login(String username, String password) {		
		
		AbstractFactory f = MySQLFactory.getInstance();
		AbstractUserDAO userDAO = f.createUserDAO();
		return userDAO.login(username, password);
	}
}