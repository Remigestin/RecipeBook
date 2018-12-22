package factory;

import abstractDAO.AbstractUserDAO;
import mySQLDAO.MySQLUserDAO;

public class MySQLFactory extends AbstractFactory {

	
	private MySQLFactory() {
	}
	
	private static MySQLFactory factory = null;

	public static MySQLFactory getInstance() {

		if (factory == null) {
			factory = new MySQLFactory();
		}
		return factory;
	}
	
	@Override
	public AbstractUserDAO createUserDAO() {
		return MySQLUserDAO.getInstance();
	}
}