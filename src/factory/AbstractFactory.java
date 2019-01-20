package factory;

import abstractDAO.*;

/**
 * 
 * @author gestin remi
 *
 */
public abstract class AbstractFactory {

	/**
	 * 
	 * @return an instance of a UserDAO
	 */
	public abstract AbstractUserDAO createUserDAO();
	
	/**
	 * 
	 * @return an instance of a RecipeDAO
	 */
	public abstract AbstractRecipeDAO createRecipeDAO();
	
	/**
	 * 
	 * @return an instance of a CookingStepDAO
	 */
	public abstract AbstractCookingStepDAO createCookingStepDAO();
	
	/**
	 * 
	 * @return an instance of a CommentDAO
	 */
	public abstract AbstractCommentDAO createCommentDAO();
}