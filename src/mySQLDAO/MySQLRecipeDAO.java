package mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import abstractDAO.AbstractRecipeDAO;
import businessLogic.Recipe;
import businessLogic.User;
import connection.DatabaseConnection;
import exception.DAOException;


public class MySQLRecipeDAO extends AbstractRecipeDAO{
	
	// sql
		private static final String SQL_FIND_FAVORITES_BY_IDUSER = "Select * FROM favoritelist F JOIN recipe R ON R.idrecipe = F.idrecipe WHERE F.iduser = ?";
	
	/* Private constructor */
	private MySQLRecipeDAO() {
	}
	
	/* Singleton Holder */
	private static class MySQLRecipeDAOHolder {
		private final static MySQLRecipeDAO recipeDAO = new MySQLRecipeDAO();
	}

	public static MySQLRecipeDAO getInstance() {
		return MySQLRecipeDAOHolder.recipeDAO;
	}
	
	
	
	@Override
	public ArrayList<Recipe> loadFavoriteRecipe(int idUser) {
		ArrayList<Recipe> favoriteList= new ArrayList<Recipe>();

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_FAVORITES_BY_IDUSER);
			st.setInt(1, idUser);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Recipe tmp = null;
				tmp = map(rs);
				favoriteList.add(tmp);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return favoriteList;
	}
	
	private static Recipe map(ResultSet resultSet) throws SQLException {
		Recipe recipe= new Recipe();
		recipe.setIdRecipe(resultSet.getInt("F.idRecipe"));
		recipe.setNameRecipe(resultSet.getString("nameRecipe"));
		recipe.setNbPersRecipe(resultSet.getInt("nbPersoRecipe"));
		recipe.setPreparationTime(resultSet.getInt("preparationTime"));

		return recipe;
	}

	

}
