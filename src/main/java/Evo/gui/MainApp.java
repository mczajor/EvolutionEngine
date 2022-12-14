package Evo.gui;

import javafx.application.Application;

public class MainApp extends Application {

        @Override
        public void start(javafx.stage.Stage primaryStage) throws Exception {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/Evo.gui/Mainscene.fxml"));
            javafx.scene.Parent root = loader.load();
            primaryStage.setTitle("Evolution Simulator");
            primaryStage.setScene(new javafx.scene.Scene(root, 650, 600));
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
}
