package factory;

import abstractDAO.AbstractCookingStepDAO;
import abstractDAO.AbstractRecipeDAO;
import abstractDAO.AbstractUserDAO;
import mySQLDAO.MySQLCookingStepDAO;
import mySQLDAO.MySQLRecipeDAO;
import mySQLDAO.MySQLUserDAO;

public class MySQLFactory extends AbstractFactory {

	/* Private constructor */
	private MySQLFactory() {
	}

	/* Singleton Holder */
	private static class MySQLFactoryHolder {
		private final static MySQLFactory factory = new MySQLFactory();
	}

	public static MySQLFactory getInstance() {
		return MySQLFactoryHolder.factory;
	}

	@Override
	public AbstractUserDAO createUserDAO() {
		return MySQLUserDAO.getInstance();
	}

	@Override
	public AbstractRecipeDAO createRecipeDAO() {
		return MySQLRecipeDAO.getInstance();
	}

	@Override
	public AbstractCookingStepDAO createCookingStepDAO() {
		return MySQLCookingStepDAO.getInstance();
	}
}