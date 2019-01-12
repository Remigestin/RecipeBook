package mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
	private static final String SQL_FIND_COURSE_CATEGORY_BY_IDCOURSE = "Select nameCourse FROM coursecategory WHERE idCourse = ?";
	private static final String SQL_FIND_ALL_COURSE_CATEGORY = "Select * FROM coursecategory";
	private static final String SQL_FIND_RANDOM_RECIPE_BY_CATEGORY = "Select * FROM recipe  WHERE idCourse = ? ORDER BY RAND() LIMIT 1";
	private static final String SQL_FIND_RANDOM_RECIPE_BY_CATEGORY_AND_IDRECIPE_DIFFERENT = "Select * FROM recipe  WHERE idCourse = ? AND idrecipe != ? ORDER BY RAND() LIMIT 1";
	private static final String SQL_FIND_RATING_BY_IDRECIPE = "SELECT AVG(mark) FROM recipe R, rating N WHERE R.idrecipe = N.idrecipe AND R.idrecipe = ?";
	private static final String SQL_INSERT_NEW_RECIPE = "INSERT INTO recipe VALUES (NULL,?,?,?,?,?,?,?)";

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

	private static Recipe map(ResultSet resultSet) throws SQLException {
		Recipe recipe = new Recipe();
		recipe.setIdRecipe(resultSet.getInt("idRecipe"));
		recipe.setNameRecipe(resultSet.getString("nameRecipe"));
		recipe.setNbPersRecipe(resultSet.getInt("nbPersoRecipe"));
		recipe.setPreparationTime(resultSet.getInt("preparationTime"));
		recipe.setIdCourse(resultSet.getInt("idCourse"));
		recipe.setDifficulty(resultSet.getInt("difficulty"));
		recipe.setImage(resultSet.getString("image"));
		recipe.setRate(findRating(resultSet.getInt("idRecipe")));

		return recipe;
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

	@Override
	public Recipe findRandomRecipe(int idCourse) {
		Recipe recipe = new Recipe();

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_RANDOM_RECIPE_BY_CATEGORY);
			st.setInt(1, idCourse);
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
	public Recipe findRandomRecipe(int idCourse, int idRecipe) {
		Recipe recipe = new Recipe();

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_RANDOM_RECIPE_BY_CATEGORY_AND_IDRECIPE_DIFFERENT);
			st.setInt(1, idCourse);
			st.setInt(2, idRecipe);
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
	public ArrayList<Recipe> createRecipe(Recipe recipe) {

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_INSERT_NEW_RECIPE);
			st.setString(1, recipe.getNameRecipe());
			st.setInt(2, recipe.getPreparationTime());
			st.setInt(3, recipe.getNbPersRecipe());
			st.setInt(4, recipe.getIdCourse());
			st.setInt(5, User.getSession().getId());
			st.setInt(6, recipe.getDifficulty());
			st.setString(7, recipe.getImage());
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return this.loadCreateRecipe(User.getSession().getId());
	}

	@Override
	public HashMap<Integer, String> findAllCourseCategory() {
		
		HashMap<Integer, String> courses = new HashMap<Integer, String>();

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_ALL_COURSE_CATEGORY);
			ResultSet rs = st.executeQuery();
			String course = null;
			while (rs.next()) {
				courses.put(rs.getInt("idCourse"), rs.getString("namecourse"));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return courses;
	}
	
	private static float findRating(int idRecipe) {
		float result = 0;
		
		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_RATING_BY_IDRECIPE);
			st.setInt(1, idRecipe);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				result = rs.getFloat("avg(mark)");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return result;
	}

	

}
