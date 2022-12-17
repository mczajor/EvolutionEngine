package Evo.gui;

import Evo.map.Simulation;
import Evo.map.elements.AbstractAnimal;
import Evo.map.elements.MoveVector;
import Evo.map.elements.Plant;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.util.Map;

public class SimulationSceneController implements Runnable {
    private final int GRIDSIZE = 700;
    private final String groundColor = "#633f01";
    private Map<MoveVector, Plant> plants;
    private Map<MoveVector, AbstractAnimal> animals;
    private Simulation simulation;
    private Thread thread;
    private int width;
    private int height;
    private boolean running = true;
    @FXML
    private GridPane MapGrid;
    @FXML
    void Pause(ActionEvent event) {
        if(this.thread.isAlive()){
            this.thread.interrupt();
        } else{
            this.thread = new Thread(this);
            this.thread.start();
        }
    }
    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }
    public void setBoundries(MoveVector boundry){
        this.width = boundry.x;
        this.height = boundry.y;
    }
    public void initializeGrid(){
        for(int i = 0; i<this.width;i++){
            MapGrid.getColumnConstraints().add(new javafx.scene.layout.ColumnConstraints(GRIDSIZE/this.width));
        }
        for(int i = 0; i<this.height;i++){
            MapGrid.getRowConstraints().add(new javafx.scene.layout.RowConstraints(GRIDSIZE/this.height));
        }
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                StackPane pane = new StackPane();
                pane.setStyle("-fx-background-color:"+groundColor);
                MapGrid.add(pane, i, j);
            }
        }
    }
    public void updateGrid(){
        this.plants = simulation.getListPlants();
        this.animals = simulation.getAnimals();
        for(Node child: this.MapGrid.getChildren()){
            StackPane pane = (StackPane) child;
            pane.getChildren().clear();
            pane.setStyle("-fx-background-color:"+groundColor);
            int row = MapGrid.getRowIndex(child);
            int column = MapGrid.getColumnIndex(child);
            if(plants.containsKey(new MoveVector(column,row))){
                pane.setStyle("-fx-background-color: #00ff00");
            }
            if (animals.containsKey(new MoveVector(column,row))){
                //System.out.println("SimulationController -> Animal at: "+column+" "+row);
                Circle circle = animals.get(new MoveVector(column,row)).getCircle();
                circle.setRadius(GRIDSIZE/(Math.max(this.width, this.height)*2.5));
                pane.getChildren().add(circle);
            }
        }
    }
    public void updateChart(){
        //TODO
    }
    public void startSimulation(){
        thread = new Thread(this);
        thread.start();
    }
    public void stopSimulation(){
        thread.interrupt();
    }
    public void run(){
        while(running){
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
            this.simulation.simulateDay();
            Platform.runLater(() -> {
                updateGrid();
                updateChart();
            });
        }
    }
}
