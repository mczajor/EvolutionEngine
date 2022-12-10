package Evo.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class MainSceneController {
    ObservableList<String> mapTypes = FXCollections.observableArrayList("Piekielny portal", "Kulista planeta");
    ObservableList<String> animalTypes = FXCollections.observableArrayList("Detereministyczne", "Losowe");
    ObservableList<String> grassTypes = FXCollections.observableArrayList("Zalesione stepy", "Toksyczne trupy");
    ObservableList<String> genotypeTypes = FXCollections.observableArrayList("Pełna losowosc", "Lekka korekta");
    @FXML
    private ChoiceBox<String> animaltype;

    @FXML
    private TextField energyforreproduction;

    @FXML
    private TextField energyloss;

    @FXML
    private TextField filepath;

    @FXML
    private ChoiceBox<String> gardenertype;

    @FXML
    private ChoiceBox<String> genotype;

    @FXML
    private TextField genotypelength;

    @FXML
    private Button loadfile;

    @FXML
    private TextField mapheight;

    @FXML
    private ChoiceBox<String> maptype;

    @FXML
    private TextField mapwidth;

    @FXML
    private TextField maxmutations;

    @FXML
    private TextField minmutations;

    @FXML
    private TextField plantenergy;

    @FXML
    private TextField plantsperday;

    @FXML
    private TextField reproductionThreshold;

    @FXML
    private TextField startinganimals;

    @FXML
    private TextField startingplants;

    @FXML
    private Button startsimul;
    @FXML
    public void initialize(){
        maptype.setItems(mapTypes);
        animaltype.setItems(animalTypes);
        gardenertype.setItems(grassTypes);
        genotype.setItems(genotypeTypes);
    }

}
