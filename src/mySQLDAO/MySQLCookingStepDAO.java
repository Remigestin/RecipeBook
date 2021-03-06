package mySQLDAO;

/**
 * @author Chawaf Alia
 * @version 1.0
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import abstractDAO.AbstractCookingStepDAO;
import businessLogic.CookingStep;
import connection.DatabaseConnection;
import exception.DAOException;

public class MySQLCookingStepDAO extends AbstractCookingStepDAO {

	/* SQL */
	private static final String SQL_FIND_COOKING_STEPS_BY_IDRECIPE = "SELECT * FROM cookingstep	WHERE idRecipe = ?";
	private static final String SQL_INSERT_NEW_COOKING_STEP = "INSERT INTO cookingstep VALUES (NULL,?,?,?)";
	private static final String SQL_EDIT_COOKING_STEP = "UPDATE cookingstep SET nameStep = ? , description = ? WHERE idcookingstep = ?";

	/**
	 * Private constructor
	 */
	private MySQLCookingStepDAO() {
	}

	/**
	 * Singleton Holder
	 *
	 */
	private static class MySQLCookingStepDAOHolder {
		private final static MySQLCookingStepDAO cookingStepDAO = new MySQLCookingStepDAO();
	}

	/**
	 * Get the instance of the singleton
	 * @return the Unique instance of CookingStepDAO
	 */
	public static MySQLCookingStepDAO getInstance() {
		return MySQLCookingStepDAOHolder.cookingStepDAO;
	}

	private static CookingStep map(ResultSet resultSet) throws SQLException {
		CookingStep cookingStep = new CookingStep();

		cookingStep.setIdCookingStep(resultSet.getInt("idcookingstep"));
		cookingStep.setName(resultSet.getString("nameStep"));
		cookingStep.setDescription(resultSet.getString("description"));

		return cookingStep;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<CookingStep> loadCookingStepsOfRecipe(int idRecipe) {

		ArrayList<CookingStep> cookingSteps = new ArrayList<CookingStep>();

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_COOKING_STEPS_BY_IDRECIPE);
			st.setInt(1, idRecipe);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				CookingStep tmp = null;
				tmp = map(rs);
				cookingSteps.add(tmp);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return cookingSteps;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createCookingStep(ArrayList<CookingStep> newSteps, int idRecipe) {
		
		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			
			for (CookingStep step : newSteps) {
			
				PreparedStatement st = c.prepareStatement(SQL_INSERT_NEW_COOKING_STEP);
				
				st.setString(1, step.getName());
				st.setString(2, step.getDescription());
				st.setInt(3, idRecipe);
				st.executeUpdate();
				
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editCookingStep(ArrayList<CookingStep> editedSteps) {
		
		for (CookingStep step : editedSteps) {
			System.out.println(step.getIdCookingStep());
		}
		
		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			
			for (CookingStep step : editedSteps) {
			
				PreparedStatement st = c.prepareStatement(SQL_EDIT_COOKING_STEP);
				
				st.setString(1, step.getName());
				st.setString(2, step.getDescription());
				st.setInt(3, step.getIdCookingStep());
				st.executeUpdate();
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
	}
}
