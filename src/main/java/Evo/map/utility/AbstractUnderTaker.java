package Evo.map.utility;

import Evo.map.elements.AbstractAnimal;
import Evo.map.world.AbstractWorldMap;

import java.util.ArrayList;

public abstract class AbstractUnderTaker {
    protected AbstractWorldMap map;
    protected ArrayList<AbstractAnimal> potentialyDeadAbstractAnimals = new ArrayList<>();

    public AbstractUnderTaker(AbstractWorldMap map){
        this.map = map;
    }
    public void buryTheDead() {
        for(AbstractAnimal abstractAnimal : potentialyDeadAbstractAnimals){
            if(abstractAnimal.getEnergy() > 0){
                continue;
            }
            map.removeAnimal(abstractAnimal);
            abstractAnimal.die();
        }
        potentialyDeadAbstractAnimals.clear();
    }
    public void addDyingAnimal(AbstractAnimal abstractAnimal){
        potentialyDeadAbstractAnimals.add(abstractAnimal);
    }
}
