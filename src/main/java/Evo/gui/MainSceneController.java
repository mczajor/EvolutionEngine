package Evo.gui;

import Evo.map.Simulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainSceneController {

    ObservableList<String> mapTypes = FXCollections.observableArrayList("Piekielny portal", "Kulista planeta");
    ObservableList<String> animalTypes = FXCollections.observableArrayList("Detereministyczne", "Losowe");
    ObservableList<String> grassTypes = FXCollections.observableArrayList("Zalesione stepy", "Toksyczne trupy");
    ObservableList<String> genotypeTypes = FXCollections.observableArrayList("Pelna losowosc", "Lekka korekta");

    @FXML
    private ChoiceBox<String> animalType;

    @FXML
    private TextField startingEnergy;
    @FXML
    private TextField energyForReproduction;

    @FXML
    private TextField energyLoss;

    @FXML
    private TextField filePath;

    @FXML
    private ChoiceBox<String> gardenerType;

    @FXML
    private ChoiceBox<String> genotype;

    @FXML
    private TextField genotypeLength;

    @FXML
    private TextField mapHeight;

    @FXML
    private ChoiceBox<String> mapType;

    @FXML
    private TextField mapWidth;

    @FXML
    private TextField maxMutations;

    @FXML
    private TextField minMutations;

    @FXML
    private TextField plantEnergy;

    @FXML
    private TextField plantsPerDay;

    @FXML
    private TextField reproductionThreshold;

    @FXML
    private TextField sleepTime;

    @FXML
    private TextField startingAnimals;

    @FXML
    private TextField startingPlants;

    @FXML
    void startSimul() {
        try {
            Simulation simulation;
                if (!this.filePath.getText().equals("")) {
                Path path = Paths.get("src/main/resources/PreMadeConfigs/" + this.filePath.getText() + ".txt");
                simulation = new Simulation(path);
            } else {
                int mapType = this.mapType.getSelectionModel().getSelectedIndex();
                int mapWidth = Integer.parseInt(this.mapWidth.getText());
                int mapHeight = Integer.parseInt(this.mapHeight.getText());

                int animalType = this.animalType.getSelectionModel().getSelectedIndex();
                int startingAnimals = Integer.parseInt(this.startingAnimals.getText());
                int startingEnergy = Integer.parseInt(this.startingEnergy.getText());
                int energyLoss = Integer.parseInt(this.energyLoss.getText());
                int energyForReproduction = Integer.parseInt(this.energyForReproduction.getText());
                int reproductionThreshold = Integer.parseInt(this.reproductionThreshold.getText());

                int gardenerType = this.gardenerType.getSelectionModel().getSelectedIndex();
                int startingPlants = Integer.parseInt(this.startingPlants.getText());
                int plantsPerDay = Integer.parseInt(this.plantsPerDay.getText());
                int plantEnergy = Integer.parseInt(this.plantEnergy.getText());

                int genomeType = genotype.getSelectionModel().getSelectedIndex();
                int genotypeLength = Integer.parseInt(this.genotypeLength.getText());
                int minMutations = Integer.parseInt(this.minMutations.getText());
                int maxMutations = Integer.parseInt(this.maxMutations.getText());
                int sleepTime = Integer.parseInt(this.sleepTime.getText());
                simulation = new Simulation(mapType, mapWidth, mapHeight,
                        gardenerType, startingPlants, plantEnergy, plantsPerDay,
                        animalType, startingAnimals, startingEnergy, energyLoss, energyForReproduction, reproductionThreshold,
                        genomeType, genotypeLength, minMutations, maxMutations,
                        sleepTime);
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Evo.gui/SimulationScene.fxml"));
            //System.out.println(loader.getLocation());
            Parent root = loader.load();
            SimulationSceneController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage simulationStage = new Stage();
            simulationStage.setTitle("Evolution Simulator");
            simulationStage.setScene(scene);
            simulationStage.setOnCloseRequest(event1 -> controller.stopSimulation());

            controller.setVariables(simulation, simulation.getBoundry());
            controller.initializeGrid();
            controller.startSimulation();

            simulationStage.show();

        } catch (IOException e){
            System.err.println("Plik nie istnieje albo nie ma uprawnien do odczytu");
        }   catch (NumberFormatException | NullPointerException e){
            System.err.println("Podane dane są niepoprawne");
        } catch(Exception e){
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize(){
        mapType.setItems(mapTypes);
        animalType.setItems(animalTypes);
        gardenerType.setItems(grassTypes);
        genotype.setItems(genotypeTypes);
    }

}
