package view.trip;

import javafx.stage.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carga el archivo FXML del controlador
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Trip.fxml"));
            
            // Crea la escena a partir del archivo FXML
            Parent root = (Parent)loader.load();
            
            // Obtiene el controlador y llama a su método initStage
            TripController controller = loader.getController();
			controller.setStage(primaryStage);
            controller.initStage(root);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}