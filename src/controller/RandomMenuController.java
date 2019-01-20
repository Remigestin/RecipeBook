package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

import businessLogic.Recipe;
import businessLogic.User;
import facade.RandomFacade;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * 
 * @author gestin remi
 *
 */
public class RandomMenuController implements Initializable {
	@FXML
	private Button changeStarter;
	@FXML
	private Label nameStarter;
	@FXML
	private Label timeStarter;
	@FXML
	private Label lvlStarter;
	@FXML
	private ImageView imageStarter;
    @FXML
    private Rating starterRating;
	
	@FXML
	private Button changeMain;

	private int idStarter;

	@FXML
	private Label nameMain;
	@FXML
	private Label timeMain;
	@FXML
	private Label lvlMain;
	@FXML
	private ImageView imageMain;
	@FXML
    private Rating mainRating;

	@FXML
	private Button changeDessert;

	private int idMain;

	@FXML
	private Label nameDessert;
	@FXML
	private Label timeDessert;
	@FXML
	private Label lvlDessert;
	@FXML
	private ImageView imageDessert;
	@FXML
    private Rating dessertRating;

	private int idDessert;

	private RandomFacade randomFacade = RandomFacade.getInstance();

	public void setIdStarter(int idStarter) {
		this.idStarter = idStarter;
	}

	public void setIdMain(int idMain) {
		this.idMain = idMain;
	}

	public void setIdDessert(int idDessert) {
		this.idDessert = idDessert;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		User session = User.getSession();
		Recipe starter = session.getRandomStarter();

		this.setIdStarter(starter.getIdRecipe());
		this.timeStarter.setText(Integer.toString(starter.getPreparationTime()) + " min");
		this.nameStarter.setText(starter.getNameRecipe());
		Image starterImg = new Image("file:../../asset/imageRecette/"+starter.getImage());
		this.imageStarter.setImage(starterImg);
		this.lvlStarter.setText(Integer.toString(starter.getDifficulty()) + "/5");
		this.starterRating.setRating(starter.getRate());

		Recipe main = session.getRandomMain();

		this.setIdMain(main.getIdRecipe());
		this.timeMain.setText(Integer.toString(main.getPreparationTime()) + " min");
		this.nameMain.setText(main.getNameRecipe());
		Image mainImg = new Image("file:../../asset/imageRecette/"+main.getImage());
		this.imageMain.setImage(mainImg);
		this.lvlMain.setText(Integer.toString(main.getDifficulty()) + "/5");
		this.mainRating.setRating(main.getRate());

		Recipe dessert = session.getRandomDessert();

		this.setIdDessert(dessert.getIdRecipe());
		this.timeDessert.setText(Integer.toString(dessert.getPreparationTime()) + " min");
		this.nameDessert.setText(dessert.getNameRecipe());
		Image dessertImg = new Image("file:../../asset/imageRecette/"+dessert.getImage());
		this.imageDessert.setImage(dessertImg);
		this.lvlDessert.setText(Integer.toString(dessert.getDifficulty()) + "/5");
		this.dessertRating.setRating(dessert.getRate());

	}

	@FXML
	/**
	 * change all the random recipes of the user in session
	 * @param event
	 */
	public void changeAll(Event event) {
		randomFacade.changeRandomStarter();
		randomFacade.changeRandomMain();
		randomFacade.changeRandomDessert();
		initialize(null, null);
	}

	@FXML
	/**
	 * change the random starter of the user in session
	 * @param event
	 */
	void changeRandomStarter(Event event) {
		randomFacade.changeRandomStarter();
		initialize(null, null);
	}

	@FXML
	/**
	 * change the random main course of the user in session
	 * @param event
	 */
	void changeRandomMain(Event event) {
		randomFacade.changeRandomMain();
		initialize(null, null);
	}

	@FXML
	/**
	 * change the random dessert of the user in session
	 * @param event
	 */
	void changeRandomDessert(Event event) {
		randomFacade.changeRandomDessert();
		initialize(null, null);
	}

	@FXML
	/**
	 * allows to consult in detail a recipe
	 * @param event
	 */
	void consultRecipe(Event event) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipePage.fxml"));

			root = loader.load();

			RecipeController controller = loader.getController();

			int idRecipe;
			
			if (((ImageView)event.getSource()).getId().equals("imageStarter")) {
				idRecipe = idStarter;
			} else if (((ImageView)event.getSource()).getId().equals("imageMain")) {
				idRecipe = idMain;
			} else {
				idRecipe = idDessert;
			}
			
			controller.setIdRecipe(idRecipe);
			controller.consultRecipe();

			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
