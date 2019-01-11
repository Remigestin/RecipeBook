package abstractDAO;

import java.util.ArrayList;

import businessLogic.Commentary;
import businessLogic.User;
import exception.DAOException;

public abstract class AbstractCommentDAO {

	public abstract void createComment(String text) throws DAOException;

	public abstract ArrayList<Commentary> showcomment(int idRecipe) throws DAOException;


}