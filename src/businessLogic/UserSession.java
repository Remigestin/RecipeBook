package businessLogic;

public class UserSession {

	private User user;
	private static UserSession instance = null;
	
	/* Private constructor */
	private UserSession(User user) {
		this.user = user;
	}

	public static UserSession getInstance(User user) {
		
		if (instance == null) {
			instance = new UserSession(user);
		}
		
		return instance;
	}
}