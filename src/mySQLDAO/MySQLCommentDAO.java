package mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import abstractDAO.AbstractCommentDAO;
import abstractDAO.AbstractUserDAO;
import businessLogic.Commentary;
import businessLogic.User;
import connection.DatabaseConnection;
import exception.DAOException;

public class MySQLCommentDAO extends AbstractCommentDAO {

	private static final String SQL_CREATE_COMMENT = "INSERT INTO comment VALUES (null,null, ? ,?,?);";
	private static final String SQL_SHOW_COMMENT = "SELECT * FROM comment	WHERE idRecipe = ?";
	@Override
	public void createComment(String text, Calendar cal, int idRecipe,  int idUser) throws DAOException {

		Commentary comment = new Commentary();
		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_CREATE_COMMENT);
			st.setString(1, text);
			st.setInt(3, idUser);
			st.setInt(2, idRecipe);
			//st.setString(1, cal);
			st.executeUpdate();
		}
			/*
			if (rs.next()) {

				comment.setIdComment(2);
				comment.setText(rs.toString());

		}
		*/
		catch(SQLException e) {
			throw new DAOException(e);
		}


	}

	/* Singleton Holder */
	private static class MySQLCommentDAOHolder {
		private final static MySQLCommentDAO commentDAO = new MySQLCommentDAO();
	}

	public static MySQLCommentDAO getInstance() {
		return MySQLCommentDAOHolder.commentDAO;
	}


	public ArrayList<Commentary> showcomment(int idRecipe) throws DAOException {

		ArrayList<Commentary> commentlist = new ArrayList<Commentary>();

		try {
			DatabaseConnection dc = DatabaseConnection.getInstance();
			Connection c = dc.getConnection();
			PreparedStatement st = c.prepareStatement(SQL_SHOW_COMMENT);
			st.setInt(1, idRecipe);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Commentary tmp = null;
				tmp = map(rs);
				commentlist.add(tmp);
}
		}
		catch (SQLException e) {
			throw new DAOException(e);
}

		return commentlist;
	}

	private static Commentary map(ResultSet resultSet) throws SQLException {
		Commentary comment = new Commentary();

		comment.setIdComment(resultSet.getInt("idcomment"));
		comment.setText(resultSet.getString("text"));

		return comment;
}

}
