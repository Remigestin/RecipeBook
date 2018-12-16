package factory;

import abstractDAO.AbstractUserDAO;
import mySQLDAO.MySQLUserDAO;

public class MySQLFactory extends AbstractFactory {

	@Override
	public AbstractUserDAO createUserDAO() {
		return new MySQLUserDAO();	
	}

}
