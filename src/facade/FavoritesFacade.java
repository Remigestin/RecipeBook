package facade;

import java.util.ArrayList;

import businessLogic.Recipe;

public class FavoritesFacade {
	
	/* Singleton Holder */
	private static class FavoritesFacadeHolder {
		private final static FavoritesFacade FavoritesFacade = new FavoritesFacade();
	}

	public static FavoritesFacade getInstance() {
		return FavoritesFacadeHolder.FavoritesFacade;
	}
	
	
	public ArrayList<Recipe> addFavoriteRecipe(int idUser, int idRecipe){
		return null;
		//to do
	}
	
	public ArrayList<Recipe> removeFavoriteRecipe(int idUser, int idRecipe){
		return null;
		//to do
	}
}
