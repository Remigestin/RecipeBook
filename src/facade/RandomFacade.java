package facade;

import abstractDAO.AbstractRecipeDAO;
import businessLogic.Recipe;
import businessLogic.User;
import factory.AbstractFactory;
import factory.MySQLFactory;
/**
 * 
 * @author gestin remi
 *
 */
public class RandomFacade {
	
	private AbstractFactory f = MySQLFactory.getInstance();
	private AbstractRecipeDAO recipeDAO = f.createRecipeDAO();
	
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
	
	/**
	 * This method allows to change the random starter of the user session
	 */
	public void changeRandomStarter () {
		User session = User.getSession();
		Recipe starter = recipeDAO.findRandomRecipe(1, session.getRandomStarter().getIdRecipe());
		session.setRandomStarter(starter);
	}
	
	/**
	 * This method allows to change the random main course of the user session
	 */
	public void changeRandomMain () {
		User session = User.getSession();
		Recipe main = recipeDAO.findRandomRecipe(2, session.getRandomMain().getIdRecipe());
		session.setRandomMain(main);
	}
	
	/**
	 * This method allows to change the random dessert of the user session
	 */
	public void changeRandomDessert () {
		User session = User.getSession();
		Recipe dessert = recipeDAO.findRandomRecipe(3, session.getRandomDessert().getIdRecipe());
		session.setRandomDessert(dessert);
	}
	
	
	

}
