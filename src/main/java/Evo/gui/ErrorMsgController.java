package Evo.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorMsgController {

    @FXML
    private Button button;
    @FXML
    private Label errorMsg;
    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    public void setMsg(String msg){
        errorMsg.setText(msg);
    }

}

