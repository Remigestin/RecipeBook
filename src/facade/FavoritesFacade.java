package facade;

import java.util.ArrayList;

import abstractDAO.AbstractRecipeDAO;
import abstractDAO.AbstractUserDAO;
import businessLogic.Recipe;
import businessLogic.User;
import factory.AbstractFactory;
import factory.MySQLFactory;

/**
 * 
 * @author MISSOUM BENZIANE Ines
 * @version 1.0
 */
public class FavoritesFacade {

	/* Singleton Holder */
	private static class FavoritesFacadeHolder {
		private final static FavoritesFacade FavoritesFacade = new FavoritesFacade();
	}

	/**
	 * 
	 * @return the unique instance of FavoritesFacade
	 */
	public static FavoritesFacade getInstance() {
		return FavoritesFacadeHolder.FavoritesFacade;
	}

	/**
	 * adds a recipe to the favorites of the user ( also in the DB)
	 * 
	 * @param idUser   id of the user
	 * @param idRecipe id of the recipe to be added
	 * @return the list of favorites recipe with the one added
	 */
	public ArrayList<Recipe> addFavoriteRecipe(int idUser, int idRecipe) {

		// take the user DAO
		AbstractFactory f = MySQLFactory.getInstance();
		AbstractUserDAO userDAO = f.createUserDAO();
		AbstractRecipeDAO recipeDAO = f.createRecipeDAO();

		// make the changes into the DB
		userDAO.addFavoriteRecipe(idUser, idRecipe);

		// retrieve the recipe and create a Recipe
		Recipe r = recipeDAO.findRecipe(idRecipe);

		// add the recipe in the user's favorites list
		ArrayList<Recipe> newFavoritesList = User.getSession().getFavoriteList();
		newFavoritesList.add(r);
		User.getSession().setFavoriteList(newFavoritesList);

		return User.getSession().getFavoriteList();

	}

	/**
	 * removes a recipe from the favorites of the user ( also in the DB)
	 * 
	 * @param idUser   id of the user
	 * @param idRecipe id of the recipe to be removed
	 * @return the list of favorites recipe without the one removed
	 */
	public ArrayList<Recipe> removeFavoriteRecipe(int idUser, int idRecipe) {

		// take the user DAO
		AbstractFactory f = MySQLFactory.getInstance();
		AbstractUserDAO userDAO = f.createUserDAO();

		// make the changes into the DB and remove the recipe in the user's favorites
		// list
		userDAO.removeFavoriteRecipe(idUser, idRecipe);

		return User.getSession().getFavoriteList();

	}

}
