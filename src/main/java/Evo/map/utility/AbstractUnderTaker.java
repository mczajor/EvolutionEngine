package Evo.map.utility;

import Evo.map.elements.Animal;
import Evo.map.world.AbstractWorldMap;

import java.util.ArrayList;

public abstract class AbstractUnderTaker {
    AbstractWorldMap map;
    ArrayList<Animal> potentialyDeadAnimals = new ArrayList<>();

    public AbstractUnderTaker(AbstractWorldMap map){
        this.map = map;
    }
    public void buryTheDead() {
        for(Animal animal : potentialyDeadAnimals){
            if(animal.getEnergy() <= 0)
                map.removeAnimal(animal);
        }
    }
    public void addDyingAnimal(Animal animal){
        potentialyDeadAnimals.add(animal);
    }
}
