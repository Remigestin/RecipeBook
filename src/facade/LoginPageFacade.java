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

	public void register(String firstname, String lastname, String username, String password) {

		AbstractFactory f = MySQLFactory.getInstance();
		AbstractUserDAO userDAO = f.createUserDAO();
		userDAO.register(firstname, lastname, username, password);
	}

	public boolean findUsername(String username) {

		AbstractFactory f = MySQLFactory.getInstance();
		AbstractUserDAO userDAO = f.createUserDAO();
		return userDAO.findUsername(username);
	}

	/* Singleton Holder */
	private static class LoginPageFacadeHolder {
		private final static LoginPageFacade LoginPageFacade = new LoginPageFacade();
	}

	public static LoginPageFacade getInstance() {
		return LoginPageFacadeHolder.LoginPageFacade;
	}
}