package facade;

import abstractDAO.AbstractUserDAO;
import businessLogic.User;
import factory.AbstractFactory;
import factory.MySQLFactory;
import mySQLDAO.MySQLUserDAO;


public class LoginPageFacade {
	
	/* Private constructor */
	private LoginPageFacade() {
	}

	public User login(String username, String password) {		
		
		AbstractFactory f = MySQLFactory.getInstance();
		AbstractUserDAO userDAO = f.createUserDAO();
		return userDAO.login(username, password);
	}
	
	/* Singleton Holder */
	private static class LoginPageFacadeHolder {
		private final static LoginPageFacade LoginPageFacade = new LoginPageFacade();
	}

	public static LoginPageFacade getInstance() {
		return LoginPageFacadeHolder.LoginPageFacade;
	}
}