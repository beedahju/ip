package ladis.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ladis.Ladis;

/**
 * A GUI for Ladis using FXML.
 */
public class Main extends Application {

    private final Ladis ladis = new Ladis("data/ladis.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            scene.getStylesheets().add(this.getClass().getResource("/css/main.css").toExternalForm());
            scene.getStylesheets().add(this.getClass().getResource("/css/dialog-box.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Ladis - Task Manager");
            stage.setResizable(true);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            MainWindow controller = fxmlLoader.getController();
            controller.setLadis(ladis);
            controller.setStage(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        launch();
    }
}

