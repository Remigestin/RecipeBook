package abstractDAO;

import businessLogic.User;

public abstract class AbstractUserDAO {
	
	public abstract User login(String username, String password);


}
