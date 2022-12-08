package Evo.map;
import Evo.map.elements.MoveVector;
import Evo.map.world.*;
import Evo.map.elements.Animal;
import Evo.map.utility.*;

import java.util.ArrayList;
import java.util.Collections;

public class Simulation {

    public static void main(String[] args){
        int width = 30;
        int height = 30;

        int startPlants = 300;
        int plantEnergy = 100;
        int newPlantsPerDay = 50;

        int startAnimals = 200;
        int startEnergy = 200;
        int energyLoss = 20;
        int energyForReproduction = 200;
        int reproductionThreshold = 150;

        run(width, height, energyLoss, reproductionThreshold, plantEnergy, startEnergy, startPlants, startAnimals, newPlantsPerDay, energyForReproduction);
    }
    public static void run(int width, int height, int energyLoss, int reproductionThreshold, int plantEnergy, int startEnergy, int startPlants, int startAnimals, int grassPerDay, int energyForReproduction){
        AbstractWorldMap map = new HellPortal(width, height, energyLoss, reproductionThreshold);
        MapVisualizer visualizer = new MapVisualizer(map);
        NecrophobicGardener gardener = new NecrophobicGardener(map, width, height, startPlants, plantEnergy);
        ArrayList<Animal> animals = new ArrayList<>();
        AbstractUnderTaker underTaker = new InformantUnderTaker(map, gardener);
        map.addUnderTaker(underTaker);
        map.addGardener(gardener);
        for (int i = 0; i < startAnimals; i++){
            Animal animal = new Animal(new MoveVector((int)(Math.random()*width), (int)(Math.random()*height)), map, startEnergy, energyForReproduction);
            if(map.place(animal)){
                animals.add(animal);
            }
        }
        System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(width-1, height-1)));
        for(int i = 0; i < 10000; i++){
            //System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(width-1, height-1)));
            underTaker.buryTheDead();
            animals.removeIf(Animal::isDead);
            Collections.shuffle(animals);
            animals.forEach(Animal::move);
            map.feast();
            animals.addAll(map.mingle());
            gardener.plant(grassPerDay);
        }
        for(Animal animal : animals) {
            System.out.println("Energy: " + animal.getEnergy() + " Age: " + animal.getAge() + " BornOn: " + animal.getBornOn() + " Children: " + animal.getChildren() + " PlantsEaten: " + animal.getPlantsEaten() + " Current Position: " + animal.getPosition() + " IsDead: " + animal.isDead());}
        System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(width-1, height-1)));
    }
}
