package abstractDAO;

import java.util.ArrayList;
import java.util.Calendar;

import businessLogic.Commentary;
import businessLogic.User;
import exception.DAOException;

public abstract class AbstractCommentDAO {

	public abstract void createComment(String text, Calendar cal,int idRecipe,  int idUser) throws DAOException;

	public abstract ArrayList<Commentary> showcomment(int idRecipe) throws DAOException;


}