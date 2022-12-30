package Evo.gui;

import Evo.map.Simulation;
import Evo.map.elements.AbstractAnimal;
import Evo.map.elements.MoveVector;
import Evo.map.elements.Plant;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SimulationSceneController implements Runnable {
    private final int GRIDSIZE = 700;
    private Simulation simulation;
    private Thread thread;
    private int width;
    private int height;
    private int sleepTime;
    @FXML
    private Label AgeBeforeDeath;

    @FXML
    private Label AmountofAnimals;

    @FXML
    private Label AmountofPlants;

    @FXML
    private Label AverageEnergy;

    @FXML
    private Label BestGenotype;

    @FXML
    private Label EmptySpaces;
    @FXML
    private GridPane MapGrid;
    @FXML
    void Pause() {
        if(this.thread.isAlive()){
            this.thread.interrupt();
        } else{
            this.thread = new Thread(this);
            this.thread.start();
        }
    }
    public void setVariables(Simulation simulation, MoveVector boundry){
        this.simulation = simulation;
        this.width = boundry.x+1;
        this.height = boundry.y+1;
        this.sleepTime = simulation.getSleepTime();
    }
    public void initializeGrid(){
        for(int i = 0; i<this.width-1;i++){
            MapGrid.getColumnConstraints().add(new javafx.scene.layout.ColumnConstraints((double)GRIDSIZE/this.width));
        }
        for(int i = 0; i<this.height-1;i++){
            MapGrid.getRowConstraints().add(new javafx.scene.layout.RowConstraints((double)GRIDSIZE/this.height));
        }
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                StackPane pane = new StackPane();
                MapGrid.add(pane, i, j);
            }
        }
    }
    public void updateScene(){
        Map<MoveVector, Plant> plants= simulation.getListPlants();
        Map<MoveVector, AbstractAnimal> animals = simulation.getAnimals();
        ArrayList<Float> stats = simulation.getStats();
        for(Node child: this.MapGrid.getChildren()){
            StackPane pane = (StackPane) child;
            pane.getChildren().clear();
            pane.setStyle("-fx-background-color:#633f01");
            int row = GridPane.getRowIndex(child);
            int column = GridPane.getColumnIndex(child);
            if(plants.containsKey(new MoveVector(column,row))){
                pane.setStyle("-fx-background-color: #00ff00");
            }
            if (animals.containsKey(new MoveVector(column,row))){
                Circle circle = animals.get(new MoveVector(column,row)).getCircle();
                circle.setRadius(GRIDSIZE/(Math.max(this.width, this.height)*2.5));
                pane.getChildren().add(circle);
            }
        }
        AmountofAnimals.setText(String.format("%.0f", stats.get(0)));
        AmountofPlants.setText(String.format("%.0f", stats.get(1)));
        EmptySpaces.setText(String.format("%.0f", stats.get(2)));
        AverageEnergy.setText(String.format("%.2f", stats.get(3)));
        AgeBeforeDeath.setText(String.format("%.2f", stats.get(4))+" days");
        BestGenotype.setText(Arrays.toString(simulation.getBestGenotype()));
    }
    public void startSimulation(){
        thread = new Thread(this);
        thread.start();
    }
    public void stopSimulation(){
        thread.interrupt();
    }
    public void run(){
        while(true){
            try{
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                break;
            }
            this.simulation.simulateDay();
            Platform.runLater(this::updateScene);
        }
    }
}
