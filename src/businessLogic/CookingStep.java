package businessLogic;

public class CookingStep {

	private int idCookingStep;
	private String name;
	private String description;
	
	public CookingStep() {}
	
	public CookingStep(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public int getIdCookingStep() {
		return idCookingStep;
	}
	public void setIdCookingStep(int idCookingStep) {
		this.idCookingStep = idCookingStep;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
