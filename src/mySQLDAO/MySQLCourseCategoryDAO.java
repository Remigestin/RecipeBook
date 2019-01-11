package mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import abstractDAO.AbstractCourseCategoryDAO;
import businessLogic.CourseCategory;
import connection.DatabaseConnection;
import exception.DAOException;

public class MySQLCourseCategoryDAO extends AbstractCourseCategoryDAO {

	/* SQL */
	private static final String SQL_FIND_NAME_BY_IDCOURSE = "SELECT nameCourse FROM coursecategory WHERE idCourse = ?";

	/* Private constructor */
	private MySQLCourseCategoryDAO() {
	}

	/* Singleton Holder */
	private static class MySQLCourseCategoryDAOHolder {
		private final static MySQLCourseCategoryDAO courseCategoryDAO = new MySQLCourseCategoryDAO();
	}

	public static MySQLCourseCategoryDAO getInstance() {
		return MySQLCourseCategoryDAOHolder.courseCategoryDAO;
	}

	private static CourseCategory map(ResultSet resultSet) throws SQLException {
		CourseCategory courseCategory = new CourseCategory();

		courseCategory.setIdCourse(resultSet.getInt("idCourse"));
		courseCategory.setNameCourse(resultSet.getString("nameCourse"));
		return courseCategory;
	}

	@Override
	public String findCourseCategoryName(int idCourse) {

		CourseCategory nameCourse = new CourseCategory();

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_FIND_NAME_BY_IDCOURSE);
			st.setInt(1, idCourse);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				nameCourse = map(rs);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return nameCourse.getNameCourse();

	}

}
