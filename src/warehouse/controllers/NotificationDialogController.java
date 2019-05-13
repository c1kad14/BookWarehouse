package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NotificationDialogController {
    public Button closeBtn;

    public void closeBtnClick(ActionEvent actionEvent) {
        close();
    }

    private void close() {
        ((Stage) closeBtn.getScene().getWindow()).close();
    }
}
