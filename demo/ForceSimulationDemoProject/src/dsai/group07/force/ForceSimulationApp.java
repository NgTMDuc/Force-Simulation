package dsai.group07.force;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ForceSimulationApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/ForceSimulationApp.fxml") );
		Parent p = fxmlLoader.load();
		
//		BackgroundController c = fxmlLoader.getController();
		
		Scene scene = new Scene(p);

		primaryStage.setTitle( "Force Simulation App" );
		primaryStage.setScene( scene );
//		primaryStage.setOnShown(event ->  c.startAmination() );
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
