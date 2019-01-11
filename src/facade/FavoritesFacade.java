package facade;

import java.util.ArrayList;

import abstractDAO.AbstractRecipeDAO;
import abstractDAO.AbstractUserDAO;
import businessLogic.Recipe;
import businessLogic.User;
import factory.AbstractFactory;
import factory.MySQLFactory;

public class FavoritesFacade {
	
	/* Singleton Holder */
	private static class FavoritesFacadeHolder {
		private final static FavoritesFacade FavoritesFacade = new FavoritesFacade();
	}

	public static FavoritesFacade getInstance() {
		return FavoritesFacadeHolder.FavoritesFacade;
	}
	
	
	public ArrayList<Recipe> addFavoriteRecipe(int idUser, int idRecipe){
		
		//take the user DAO
		AbstractFactory f = MySQLFactory.getInstance();
		AbstractUserDAO userDAO = f.createUserDAO();
		AbstractRecipeDAO recipeDAO = f.createRecipeDAO();
		
		//make the changes into the DB 
		userDAO.addFavoriteRecipe(idUser, idRecipe);
		
		//retrieve the recipe and create a Recipe
		Recipe r = recipeDAO.findRecipe(idRecipe);
		
		//add the recipe in the user's favorites list
		ArrayList<Recipe> newFavoritesList = User.getSession().getFavoriteList();
		newFavoritesList.add(r);
		User.getSession().setFavoriteList(newFavoritesList);
		
		return User.getSession().getFavoriteList();
		//to do
	}
	
	public ArrayList<Recipe> removeFavoriteRecipe(int idUser, int idRecipe){
		
		//take the user DAO
		AbstractFactory f = MySQLFactory.getInstance();
		AbstractUserDAO userDAO = f.createUserDAO();
		
		//make the changes into the DB 
		userDAO.removeFavoriteRecipe(idUser, idRecipe);
		
		return null;
		//to do
	}
}
