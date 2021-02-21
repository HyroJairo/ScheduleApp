package Main;

import Utils.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * @author max morales
 * This the main class that starts the login screen
 */
public class Main extends Application {

    /**
     *
     * @param primaryStage the stage
     * @throws Exception an exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/login.fxml"));
        Controllers.loginController controller = new Controllers.loginController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     *
     * @param args the arguments
     * @throws SQLException the sql exception
     */
    public static void main(String[] args) throws SQLException {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
