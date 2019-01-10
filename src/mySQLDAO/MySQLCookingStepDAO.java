package mySQLDAO;

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

	/* Private constructor */
	private MySQLCookingStepDAO() {
	}

	/* Singleton Holder */
	private static class MySQLCookingStepDAOHolder {
		private final static MySQLCookingStepDAO cookingStepDAO = new MySQLCookingStepDAO();
	}

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
}
