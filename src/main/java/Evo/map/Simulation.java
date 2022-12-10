package Evo.map;
import Evo.map.elements.*;
import Evo.map.world.*;
import Evo.map.utility.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Simulation {

    public static void main(String[] args){
        try{
            Path path = Paths.get("src/main/resources/1.txt");
            Map<String, Integer> map = FileReader.byStream(path);
            int mapType = map.get("mapType");
            int width = map.get("width");
            int height = map.get("height");

            int gardenerType = map.get("gardenerType");
            int startPlants = map.get("startPlants");
            int plantEnergy = map.get("plantEnergy");
            int grassPerDay = map.get("plantsPerDay");

            int animalType = map.get("animalType");
            int startAnimals = map.get("startAnimals");
            int startEnergy = map.get("animalEnergy");
            int energyLoss = map.get("energyLoss");
            int energyForReproduction = map.get("energyForReproduction");
            int reproductionThreshold = map.get("reproductionThreshold");

            int genomeType = map.get("genomeType");
            int genomeLength = map.get("genomeLength");
            int minGenomeMutations = map.get("minGenomeMutation");
            int maxGenomeMutations = map.get("maxGenomeMutation");
            run(mapType, width, height, gardenerType, startPlants, plantEnergy, grassPerDay, animalType, startAnimals, startEnergy,
                    energyLoss, energyForReproduction, reproductionThreshold, genomeType, genomeLength, minGenomeMutations, maxGenomeMutations);
        } catch (IOException | NumberFormatException e){
            System.err.println(e.getMessage());
        } catch (NullPointerException e){
            System.err.println("File is formatted incorrectly");
        }
    }
    public static void run(int mapType, int width, int height, int gardenerType, int startPlants, int plantEnergy, int grassPerDay, int animalType, int startAnimals, int startEnergy, int energyLoss,
                           int energyForReproduction, int reproductionThreshold, int genomeType, int genomeLength, int minGenomeMutations, int maxGenomeMutations){
        AbstractWorldMap map;
        if(mapType == 0){
            map = new HellPortal(width, height, energyLoss, reproductionThreshold);
        } else{
            map = new SphericalWorld(width, height, energyLoss, reproductionThreshold);
        }
        MapVisualizer visualizer = new MapVisualizer(map);
        AbstractGardener gardener;
        AbstractUnderTaker underTaker;
        if(gardenerType == 0){
            gardener = new NecrophobicGardener(map, width, height, startPlants, plantEnergy);
            underTaker = new InformantUnderTaker(map, gardener);
        } else{
            gardener = new EquatorGardener(map, width, height, startPlants, plantEnergy);
            underTaker = new UnderTaker(map);
        }
        ArrayList<AbstractAnimal> abstractAnimals = new ArrayList<>();
        map.addUnderTaker(underTaker);
        map.addGardener(gardener);
        for (int i = 0; i < startAnimals; i++){
            AbstractAnimal animal;
            if(animalType == 0){
                animal = new DetermininisticAnimal(new MoveVector((int)(Math.random()*width), (int)(Math.random()*height)), map, startEnergy, energyForReproduction, genomeType, genomeLength, minGenomeMutations, maxGenomeMutations);
            } else{
                animal = new CrazyAnimal(new MoveVector((int)(Math.random()*width), (int)(Math.random()*height)), map, startEnergy, energyForReproduction, genomeType, genomeLength, minGenomeMutations, maxGenomeMutations);
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
