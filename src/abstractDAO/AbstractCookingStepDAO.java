package abstractDAO;

import java.util.ArrayList;
import businessLogic.CookingStep;

public abstract class AbstractCookingStepDAO {

	public abstract ArrayList<CookingStep> loadCookingStepsOfRecipe(int idRecipe);
	public abstract void createCookingStep(ArrayList<CookingStep> newSteps, int idRecipe);
	public abstract void editCookingStep(ArrayList<CookingStep> editedSteps);
}
