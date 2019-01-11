package facade;


public class RandomFacade {
	/* Private constructor */
	private RandomFacade() {
	}

	/* Singleton Holder */
	private static class RandomFacadeHolder {
		private final static RandomFacade RandomFacade = new RandomFacade();
	}
	
	public static RandomFacade getInstance() {
		return RandomFacadeHolder.RandomFacade;
	}
	
	
	

}
