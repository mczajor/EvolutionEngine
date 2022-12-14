package Evo.map;
import Evo.map.elements.*;
import Evo.map.world.*;
import Evo.map.utility.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Simulation {
    int mapType;
    int width;
    int height;

    int gardenerType;
    int startPlants;
    int plantEnergy;
    int grassPerDay;

    int animalType;
    int startAnimals;
    int startEnergy;
    int energyLoss;
    int energyForReproduction;
    int reproductionThreshold;

    int genomeType;
    int genomeLength;
    int minGenomeMutations;
    int maxGenomeMutations;

    public Simulation(int mapType, int width, int height,
                      int gardenerType, int startPlants, int plantEnergy, int grassPerDay,
                      int animalType, int startAnimals, int startEnergy, int energyLoss, int energyForReproduction, int reproductionThreshold,
                      int genomeType, int genomeLength, int minGenomeMutations, int maxGenomeMutations){
        this.mapType = mapType;
        this.width = width;
        this.height = height;

        this.gardenerType = gardenerType;
        this.startPlants = startPlants;
        this.plantEnergy = plantEnergy;
        this.grassPerDay = grassPerDay;

        this.animalType = animalType;
        this.startAnimals = startAnimals;
        this.startEnergy = startEnergy;
        this.energyLoss = energyLoss;
        this.energyForReproduction = energyForReproduction;
        this.reproductionThreshold = reproductionThreshold;

        this.genomeType = genomeType;
        this.genomeLength = genomeLength;
        this.minGenomeMutations = minGenomeMutations;
        this.maxGenomeMutations = maxGenomeMutations;
    }

    public Simulation(Path path) throws IOException, NumberFormatException {
        //Path path = Paths.get("src/main/resources/PreMadeConfigs/1.txt");
        Map<String, Integer> map = FileReader.byStream(path);
        this.mapType = map.get("mapType");
        this.width = map.get("width");
        this.height = map.get("height");

        this.gardenerType = map.get("gardenerType");
        this.startPlants = map.get("startPlants");
        this.plantEnergy = map.get("plantEnergy");
        this.grassPerDay = map.get("plantsPerDay");

        this.animalType = map.get("animalType");
        this.startAnimals = map.get("startAnimals");
        this.startEnergy = map.get("animalEnergy");
        this.energyLoss = map.get("energyLoss");
        this.energyForReproduction = map.get("energyForReproduction");
        this.reproductionThreshold = map.get("reproductionThreshold");

        this.genomeType = map.get("genomeType");
        this.genomeLength = map.get("genomeLength");
        this.minGenomeMutations = map.get("minGenomeMutation");
        this.maxGenomeMutations = map.get("maxGenomeMutation");
    }
    public void run(){
        AbstractWorldMap map;
        if(mapType == 0){
            map = new HellPortal(this.width, this.height, this.energyLoss, this.reproductionThreshold);
        } else{
            map = new SphericalWorld(this.width, this.height, this.energyLoss, this.reproductionThreshold);
        }
        MapVisualizer visualizer = new MapVisualizer(map);
        AbstractGardener gardener;
        AbstractUnderTaker underTaker;
        if(gardenerType == 0){
            gardener = new NecrophobicGardener(map, this.width, this.height, this.startPlants, this.plantEnergy);
            underTaker = new InformantUnderTaker(map, gardener);
        } else{
            gardener = new EquatorGardener(map, this.width, this.height, this.startPlants, this.plantEnergy);
            underTaker = new UnderTaker(map);
        }
        ArrayList<AbstractAnimal> abstractAnimals = new ArrayList<>();
        map.addUnderTaker(underTaker);
        map.addGardener(gardener);
        for (int i = 0; i < this.startAnimals; i++){
            AbstractAnimal animal;
            if(this.animalType == 0){
                animal = new DetermininisticAnimal(new MoveVector((int)(Math.random()*this.width), (int)(Math.random()*this.height)), map, this.startEnergy, this.energyForReproduction, this.genomeType, this.genomeLength, this.minGenomeMutations, this.maxGenomeMutations);
            } else{
                animal = new CrazyAnimal(new MoveVector((int)(Math.random()*this.width), (int)(Math.random()*this.height)), map, this.startEnergy, this.energyForReproduction, this.genomeType, this.genomeLength, this.minGenomeMutations, this.maxGenomeMutations);
            }
            if(map.place(animal)){
                abstractAnimals.add(animal);
            }
        }
        //System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(width-1, height-1)));
        for(int i = 0; i < 10000; i++){
            //System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(width-1, height-1)));
            underTaker.buryTheDead();
            abstractAnimals.removeIf(AbstractAnimal::isDead);
            Collections.shuffle(abstractAnimals);
            abstractAnimals.forEach(AbstractAnimal::move);
            map.feast();
            abstractAnimals.addAll(map.mingle());
            gardener.plant(grassPerDay);
        }
        for(AbstractAnimal abstractAnimal : abstractAnimals) {
            System.out.println("Energy: " + abstractAnimal.getEnergy() + " Age: " + abstractAnimal.getAge() + " BornOn: " + abstractAnimal.getBornOn() + " Children: " + abstractAnimal.getChildren() + " PlantsEaten: " + abstractAnimal.getPlantsEaten() + " Current Position: " + abstractAnimal.getPosition() + " Genotype: " + abstractAnimal.isRandom());}
        System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(width-1, height-1)));
    }
}
