package factory;

import abstractDAO.AbstractCommentDAO;
import abstractDAO.AbstractCookingStepDAO;
import abstractDAO.AbstractRecipeDAO;
import abstractDAO.AbstractUserDAO;
import mySQLDAO.MySQLCommentDAO;
import mySQLDAO.MySQLCookingStepDAO;
import mySQLDAO.MySQLRecipeDAO;
import mySQLDAO.MySQLUserDAO;

/**
 * 
 * @author gestin remi
 * @version 1.0
 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractUserDAO createUserDAO() {
		return MySQLUserDAO.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractRecipeDAO createRecipeDAO() {
		return MySQLRecipeDAO.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractCookingStepDAO createCookingStepDAO() {
		return MySQLCookingStepDAO.getInstance();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractCommentDAO createCommentDAO() {
		return MySQLCommentDAO.getInstance();
	}
}