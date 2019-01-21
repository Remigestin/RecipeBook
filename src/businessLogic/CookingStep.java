package businessLogic;

/**
 * This class represent the cooking step of a recipe.
 * 
 * @author Chawaf Alia
 * @version 1.0
 */

public class CookingStep {

	/**
	 * The UNIQUE id of a cooking step
	 */
	private int idCookingStep;

	/**
	 * The name of a cooking step
	 */
	private String name;

	/**
	 * The description of a cooking step
	 */
	private String description;

	/**
	 * Empty constructor
	 */
	public CookingStep() {
	}

	/**
	 * Constructor to create a cooking step with a name and a description
	 * 
	 * @param name
	 * @param description
	 */
	public CookingStep(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * Getter
	 * @return the id of the cooking step
	 */
	public int getIdCookingStep() {
		return idCookingStep;
	}

	/**
	 * Setter of cookinf step ID
	 * @param idCookingStep
	 */
	public void setIdCookingStep(int idCookingStep) {
		this.idCookingStep = idCookingStep;
	}

	/**
	 * Getter
	 * @return the name of the cooking step
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter of the cooking step name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter
	 * @return the description of the cooking step
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter cooking step description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
