package mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import abstractDAO.AbstractRecipeDAO;
import businessLogic.Recipe;
import businessLogic.User;
import connection.DatabaseConnection;
import exception.DAOException;

/**
 * 
 * @author MISSOUM BENZIANE Ines
 * @author gestin remi
 * @author Chawaf Alia
 * @version 1.0
 */
public class MySQLRecipeDAO extends AbstractRecipeDAO {

	// sql
	private static final String SQL_FIND_FAVORITES_BY_IDUSER = "Select * FROM favoritelist F JOIN recipe R ON R.idrecipe = F.idrecipe WHERE F.iduser = ?";
	private static final String SQL_FIND_CREATES_BY_IDUSER = "Select * FROM recipe WHERE iduser = ?";
	private static final String SQL_FIND_RECIPE_BY_IDRECIPE = "Select * FROM recipe WHERE idRecipe = ?";
	private static final String SQL_FIND_ID_USER_BY_IDRECIPE = "Select idUser FROM recipe WHERE idRecipe = ?";
	private static final String SQL_FIND_COURSE_CATEGORY_BY_IDCOURSE = "Select nameCourse FROM coursecategory WHERE idCourse = ?";
	private static final String SQL_FIND_ALL_COURSE_CATEGORY = "Select * FROM coursecategory";
	private static final String SQL_FIND_ALL_RECIPES = "Select * FROM recipe";
	private static final String SQL_FIND_RANDOM_RECIPE_BY_CATEGORY = "Select * FROM recipe  WHERE idCourse = ? ORDER BY RAND() LIMIT 1";
	private static final String SQL_FIND_RANDOM_RECIPE_BY_CATEGORY_AND_IDRECIPE_DIFFERENT = "Select * FROM recipe  WHERE idCourse = ? AND idrecipe != ? ORDER BY RAND() LIMIT 1";
	private static final String SQL_FIND_RATING_BY_IDRECIPE = "SELECT AVG(mark) FROM recipe R, rating N WHERE R.idrecipe = N.idrecipe AND R.idrecipe = ?";
	private static final String SQL_FIND_RATING_BY_IDRECIPE_AND_IDUSER = "SELECT mark FROM rating  WHERE idrecipe = ? AND iduser = ?";
	private static final String SQL_INSERT_NEW_RECIPE = "INSERT INTO recipe VALUES (NULL,?,?,?,?,?,?,?)";
	private static final String SQL_EDIT_RECIPE = "UPDATE recipe SET nameRecipe = ? , preparationTime = ? , nbPersoRecipe = ? , idCourse = ? , difficulty = ? , image = ? WHERE idRecipe = ?";
	private static final String SQL_INSERT_NEW_RATING = "INSERT INTO rating VALUES (?,?,?)";
	private static final String SQL_FIND_RECIPES_BY_SEARCH = "Select * FROM recipe WHERE namerecipe LIKE ?";
	private static final String SQL_DELETE_RATING = "DELETE FROM rating WHERE idrecipe = ? AND iduser = ? ";
	private static final String SQL_UPDATE_RATING = "UPDATE rating SET mark = ? WHERE idrecipe = ? AND iduser = ? ";
	private static final String SQL_IS_FAVORITE = "Select * FROM favoritelist WHERE idrecipe = ? AND iduser = ?";
	private static final String SQL_FIND_TOP1 = "SELECT R.idrecipe from recipe R, rating RA where R.idrecipe = RA.idrecipe GROUP BY R.idrecipe HAVING AVG(mark) >= ALL(select avg(mark) from recipe R2, rating RA2 where R2.idrecipe = RA2.idrecipe group by R2.idrecipe)";

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

	/**
	 * 
	 * @param resultSet
	 * @return a recipe
	 * @throws SQLException
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Recipe> editRecipe(Recipe recipe) {

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_EDIT_RECIPE);
			st.setString(1, recipe.getNameRecipe());
			st.setInt(2, recipe.getPreparationTime());
			st.setInt(3, recipe.getNbPersRecipe());
			st.setInt(4, recipe.getIdCourse());
			st.setInt(5, recipe.getDifficulty());
			st.setString(6, recipe.getImage());
			st.setInt(7, recipe.getIdRecipe());
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return this.loadCreateRecipe(User.getSession().getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HashMap<Integer, String> findAllCourseCategory() {

		HashMap<Integer, String> courses = new HashMap<Integer, String>();

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_ALL_COURSE_CATEGORY);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				courses.put(rs.getInt("idCourse"), rs.getString("namecourse"));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return courses;
	}
	
	/**
	 * Get the average rating of a recipe
	 * @param idRecipe
	 * @return the average rating of the recipe with the id in parameter
	 */
	public static float findRating(int idRecipe) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int findRate(int idUser, int idRecipe) {
		int result = -1;

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_RATING_BY_IDRECIPE_AND_IDUSER);
			st.setInt(1, idRecipe);
			st.setInt(2, idUser);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				result = rs.getInt("mark");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Recipe> searchRecipes(String search) {
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_RECIPES_BY_SEARCH);
			st.setString(1, "%" + search + "%");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Recipe tmp = null;
				tmp = map(rs);
				recipeList.add(tmp);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return recipeList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void rateARecipe(int idRecipe, int idUser, double ratingValue) {
		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_INSERT_NEW_RATING);
			st.setInt(1, idRecipe);
			st.setInt(2, idUser);
			st.setDouble(3, ratingValue);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteRating(int idRecipe, int idUser) {
		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_DELETE_RATING);
			st.setInt(1, idRecipe);
			st.setInt(2, idUser);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editRating(int idRecipe, int idUser, double ratingValue) {
		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_UPDATE_RATING);
			st.setDouble(1, ratingValue);
			st.setInt(2, idRecipe);
			st.setInt(3, idUser);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int findIdUserCreator(int idRecipe) {

		int idUser = 0;

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_ID_USER_BY_IDRECIPE);
			st.setInt(1, idRecipe);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				idUser = rs.getInt("idUser");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return idUser;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFavorite(int idRecipe, int idUser) {

		boolean isFavorite = false;

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_IS_FAVORITE);
			st.setInt(1, idRecipe);
			st.setInt(2, idUser);
			ResultSet rs = st.executeQuery();
			isFavorite = rs.next();

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return isFavorite;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Recipe findTop1Recipe() {

		int idRecipe = 0;

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_TOP1);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {

				idRecipe = rs.getInt("idrecipe");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return this.findRecipe(idRecipe);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Recipe> findAllRecipes() {

		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_ALL_RECIPES);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Recipe tmp = null;
				tmp = map(rs);
				recipeList.add(tmp);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return recipeList;
	}
}
