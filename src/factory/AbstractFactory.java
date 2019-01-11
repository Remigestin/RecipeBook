package factory;

import abstractDAO.*;

public abstract class AbstractFactory {

	public abstract AbstractUserDAO createUserDAO();
	public abstract AbstractRecipeDAO createRecipeDAO();
	public abstract AbstractCookingStepDAO createCookingStepDAO();
	public abstract AbstractCommentDAO createCommentDAO();
}