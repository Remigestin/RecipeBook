package mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import abstractDAO.AbstractRecipeDAO;
import businessLogic.CookingStep;
import businessLogic.Recipe;
import businessLogic.User;
import connection.DatabaseConnection;
import exception.DAOException;

public class MySQLRecipeDAO extends AbstractRecipeDAO {

	// sql
	private static final String SQL_FIND_FAVORITES_BY_IDUSER = "Select * FROM favoritelist F JOIN recipe R ON R.idrecipe = F.idrecipe WHERE F.iduser = ?";
	private static final String SQL_FIND_CREATES_BY_IDUSER = "Select * FROM recipe WHERE iduser = ?";
	private static final String SQL_FIND_RECIPE_BY_IDRECIPE = "Select * FROM recipe WHERE idRecipe = ?";
	private static final String SQL_FIND_COURSE_CATEGORY_BY_IDCOURSE = "Select nameCourse FROM recipe R JOIN coursecategory C ON R.idCourse = C.idCourse WHERE R.idCourse = ?";

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
		ArrayList<Recipe> favoriteList = new ArrayList<Recipe>();

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

	@Override
	public ArrayList<Recipe> loadCreateRecipe(int idUser) {
		ArrayList<Recipe> createList = new ArrayList<Recipe>();

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_CREATES_BY_IDUSER);
			st.setInt(1, idUser);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Recipe tmp = null;
				tmp = map(rs);
				createList.add(tmp);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return createList;
	}

	private static Recipe map(ResultSet resultSet) throws SQLException {
		Recipe recipe = new Recipe();
		recipe.setIdRecipe(resultSet.getInt("idRecipe"));
		recipe.setNameRecipe(resultSet.getString("nameRecipe"));
		recipe.setNbPersRecipe(resultSet.getInt("nbPersoRecipe"));
		recipe.setPreparationTime(resultSet.getInt("preparationTime"));
		recipe.setIdCourse(resultSet.getInt("idCourse"));

		return recipe;
	}

	@Override
	public Recipe findRecipe(int idRecipe) {

		Recipe recipe = new Recipe();

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_RECIPE_BY_IDRECIPE);
			st.setInt(1, idRecipe);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				recipe = map(rs);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return recipe;
	}

	@Override
	public String findCourseCategory(int idCourse) {

		String nameCourse = null;

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_COURSE_CATEGORY_BY_IDCOURSE);
			st.setInt(1, idCourse);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				nameCourse = rs.getString(1);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return nameCourse;
	}

}
