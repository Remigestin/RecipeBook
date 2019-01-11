package facade;

import java.util.ArrayList;

import abstractDAO.AbstractUserDAO;
import businessLogic.Recipe;
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
		
		//make the changes into the DB 
		userDAO.addFavoriteRecipe(idUser, idRecipe);
		
		return null;
		//to do
	}
	
	public ArrayList<Recipe> removeFavoriteRecipe(int idUser, int idRecipe){
		return null;
		//to do
	}
}
