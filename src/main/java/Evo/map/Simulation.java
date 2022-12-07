package Evo.map;
import Evo.map.elements.MoveVector;
import Evo.map.world.*;
import Evo.map.elements.Animal;
import Evo.map.utility.*;

import java.util.ArrayList;

public class Simulation {

    public static void main(String[] args){
        int width = 50;
        int height = 50;

        int startPlants = 100;
        int plantEnergy = 50;
        int newPlantsPerDay = 10;

        int startAnimals = 100;
        int startEnergy = 200;
        int energyLoss = 10;
        int energyForReproduction = 100;
        int reproductionThreshold = 110;

        run(width, height, energyLoss, reproductionThreshold, plantEnergy, startEnergy, startPlants, startAnimals, newPlantsPerDay, energyForReproduction);
    }
    public static void run(int width, int height, int energyLoss, int reproductionThreshold, int plantEnergy, int startEnergy, int startPlants, int startAnimals, int grassPerDay, int energyForReproduction){
        if (reproductionThreshold <= energyForReproduction){
            throw new IllegalArgumentException("Reproduction threshold must be greater than energy for reproduction");
        }
        AbstractWorldMap map = new HellPortal(width, height, energyLoss, reproductionThreshold);
        MapVisualizer visualizer = new MapVisualizer(map);
        AbstractGardener gardener = new EquatorGardener(map, width, height, startPlants, plantEnergy);
        ArrayList<Animal> animals = new ArrayList<>();
        AbstractUnderTaker underTaker = new UnderTaker(map);
        map.addUnderTaker(underTaker);
        map.addGardener(gardener);
        for (int i = 0; i < startAnimals; i++){
            Animal animal = new Animal(new MoveVector((int)(Math.random()*width), (int)(Math.random()*height)), map, startEnergy, energyForReproduction, energyLoss);
            if(map.place(animal)){
                animals.add(animal);
            }
        }
        for(int i = 0; i < 10000; i++){
            underTaker.buryTheDead();
            animals.removeIf(Animal::isDead);
            animals.forEach(Animal::move);
            map.feast();
            animals.addAll(map.mingle());
            gardener.plant(grassPerDay);
        }
        System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(width-1, height-1)));
    }
}
