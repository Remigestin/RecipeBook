package facade;

import abstractDAO.AbstractRecipeDAO;
import abstractDAO.AbstractUserDAO;
import businessLogic.Recipe;
import businessLogic.User;
import factory.AbstractFactory;
import factory.MySQLFactory;

public class RandomFacade {
	
	AbstractFactory f = MySQLFactory.getInstance();
	AbstractRecipeDAO recipeDAO = f.createRecipeDAO();
	
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
	
	public void changeRandomStarter () {
		User session = User.getSession();
		Recipe starter = recipeDAO.findRandomRecipe(1, session.getRandomStarter().getIdRecipe());
		session.setRandomStarter(starter);
	}
	
	public void changeRandomMain () {
		User session = User.getSession();
		Recipe main = recipeDAO.findRandomRecipe(2, session.getRandomMain().getIdRecipe());
		session.setRandomMain(main);
	}
	
	public void changeRandomDessert () {
		User session = User.getSession();
		Recipe dessert = recipeDAO.findRandomRecipe(3, session.getRandomDessert().getIdRecipe());
		session.setRandomDessert(dessert);
	}
	
	
	

}
