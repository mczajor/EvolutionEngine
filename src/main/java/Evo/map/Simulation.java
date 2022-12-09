package Evo.map;
import Evo.map.elements.CrazyAnimal;
import Evo.map.elements.DetermininisticAnimal;
import Evo.map.elements.MoveVector;
import Evo.map.world.*;
import Evo.map.elements.AbstractAnimal;
import Evo.map.utility.*;

import java.util.ArrayList;
import java.util.Collections;

public class Simulation {

    public static void main(String[] args){
        int mapType = 0;
        int width = 80;
        int height = 80;

        int gardenerType = 0;
        int startPlants = 300;
        int plantEnergy = 100;
        int grassPerDay = 50;

        int animalType = 0;
        int startAnimals = 200;
        int startEnergy = 200;
        int energyLoss = 20;
        int energyForReproduction = 200;
        int reproductionThreshold = 150;

        int genomeType = 1;
        int genomeLength = 32;
        int minGenomeMutations = 1;
        int maxGenomeMutations = 3;

        run(mapType, width, height, gardenerType, startPlants, plantEnergy, grassPerDay, animalType, startAnimals, startEnergy,
                energyLoss, energyForReproduction, reproductionThreshold, genomeType, genomeLength, minGenomeMutations, maxGenomeMutations);
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
            System.out.println("Energy: " + abstractAnimal.getEnergy() + " Age: " + abstractAnimal.getAge() + " BornOn: " + abstractAnimal.getBornOn() + " Children: " + abstractAnimal.getChildren() + " PlantsEaten: " + abstractAnimal.getPlantsEaten() + " Current Position: " + abstractAnimal.getPosition());}
        System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(width-1, height-1)));
    }
}
