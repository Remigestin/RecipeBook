package abstractDAO;

import java.util.ArrayList;
import java.util.Calendar;

import businessLogic.Commentary;
import businessLogic.User;
import exception.DAOException;

public abstract class AbstractCommentDAO {

	
	/**
	 * add a commentary to a recipe 
	 * @param text
	 * @param cal
	 * @param idRecipe
	 * @param idUser
	 * @throws DAOException
	 */
	public abstract void createComment(String text, Calendar cal,int idRecipe,  int idUser) throws DAOException;

	
	/**
	 * show all the comment of a recipe
	 * @param idRecipe
	 * @return a list of commentary that correspond to the recipe in param
	 * @throws DAOException
	 */
	public abstract ArrayList<Commentary> showcomment(int idRecipe) throws DAOException;


}