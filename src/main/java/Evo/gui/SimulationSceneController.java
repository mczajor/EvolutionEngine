package Evo.gui;
import Evo.map.Simulation;
import Evo.map.elements.MoveVector;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SimulationSceneController {
    private Simulation simulation;
    private Thread thread;
    private int width;
    private int height;
    @FXML
    private LineChart<?, ?> AnimalChart;

    @FXML
    private GridPane MapGrid;
    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }
    public void setBoundries(MoveVector boundry){
        this.width = boundry.x;
        this.height = boundry.y;
    }
    public void initializeGrid(){
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                StackPane pane = new StackPane();
                pane.setPrefSize(528/this.width,528/this.height);
                pane.setStyle("-fx-background-color: #00FF10");
                Color color = Color.BLUE;
                Circle circle = new Circle(5,color);
                pane.getChildren().add(circle);
                MapGrid.add(pane, i, j);
            }
        }
        MapGrid.setGridLinesVisible(true);
    }
    public void updateGrid(){
        //TODO
    }
    public void updateChart(){
        //TODO
    }
    public void initialize(){
    }
}
