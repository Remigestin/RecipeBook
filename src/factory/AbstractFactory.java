package factory;

import abstractDAO.AbstractUserDAO;

public abstract class AbstractFactory {
	
	public abstract AbstractUserDAO createUserDAO();
}