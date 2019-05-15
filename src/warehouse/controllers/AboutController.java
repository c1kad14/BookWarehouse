package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Controller class for About Dialog
 */
public class AboutController {
    public Button closeBtn;
    public ImageView imageView;

    @FXML
    public void initialize() {
        try {
            imageView.setImage(new Image(new File(System.getProperty("user.dir") + "//atmc.png").toURI().toURL().toString(), false));
        } catch (MalformedURLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void closeBtnClick(ActionEvent actionEvent) {
        close();
    }

    private void close() {
        ((Stage) closeBtn.getScene().getWindow()).close();
    }
}
