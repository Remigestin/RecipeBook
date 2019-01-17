package facade;

import abstractDAO.AbstractUserDAO;
import businessLogic.User;
import factory.AbstractFactory;
import factory.MySQLFactory;

public class UserFacade {

	/* Private constructor */
	private UserFacade() {
	}

	AbstractFactory f = MySQLFactory.getInstance();
	AbstractUserDAO userDAO = f.createUserDAO();

	public User login(String username, String password) {

		return userDAO.login(username, password);
	}

	public void register(String firstname, String lastname, String username, String password) {

		userDAO.register(firstname, lastname, username, password);
	}

	public void deleteAccount(int idUser) {

		userDAO.deleteAccount(idUser);
	}

	public void editAccount(User user) {

		userDAO.editAccount(user);

		User.getSession().setFirstname(user.getFirstname());
		User.getSession().setLastname(user.getLastname());
	}

	public boolean findUsername(String username) {

		return userDAO.findUsername(username);
	}
	
	public String findPassword(int idUser) {

		return userDAO.findPassword(idUser);
	}

	/* Singleton Holder */
	private static class UserFacadeHolder {
		private final static UserFacade UserFacade = new UserFacade();
	}

	public static UserFacade getInstance() {
		return UserFacadeHolder.UserFacade;
	}
}