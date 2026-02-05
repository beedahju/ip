package ladis.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import ladis.Ladis;

/**
 * Controller for the main GUI window.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Ladis ladis;
    private Stage stage;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/LadisUser.jpg"));
    private final Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Ladis.jpg"));

    @FXML
    @SuppressWarnings("unused")
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Ladis instance.
     */
    public void setLadis(Ladis l) {
        ladis = l;
    }

    /**
     * Sets the stage for this window (needed for exit functionality).
     */
    public void setStage(Stage s) {
        stage = s;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Ladis's reply
     * and then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    @SuppressWarnings("unused")
    private void handleUserInput() {
        String input = userInput.getText();
        String response = ladis.getResponse(input);
        String commandType = ladis.getCommandType();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage, commandType)
        );
        userInput.clear();

        // Close the window after 1 second if the exit command was used
        if (ladis.isExitCommand() && stage != null) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> stage.close());
            delay.play();
        }
    }
}
