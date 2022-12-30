package Evo.map.utility;

import Evo.map.elements.AbstractAnimal;
import Evo.map.world.AbstractWorldMap;

import java.util.ArrayList;

public abstract class AbstractUnderTaker {
    protected AbstractWorldMap map;
    protected ArrayList<AbstractAnimal> potentialyDeadAbstractAnimals = new ArrayList<>();
    protected int deadAnimals = 0;
    protected int agesofDeadAnimals = 0;

    public AbstractUnderTaker(AbstractWorldMap map){
        this.map = map;
    }
    public ArrayList<AbstractAnimal> buryTheDead() {
        ArrayList<AbstractAnimal> animals2Remove = new ArrayList<>();
        for(AbstractAnimal abstractAnimal : potentialyDeadAbstractAnimals){
            if(abstractAnimal.getEnergy() > 0){
                continue;
            }
            animals2Remove.add(abstractAnimal);
            agesofDeadAnimals += abstractAnimal.getAge();
            deadAnimals++;
            map.removeAnimal(abstractAnimal);
            abstractAnimal.die();

        }
        potentialyDeadAbstractAnimals.clear();
        return animals2Remove;
    }
    public void addDyingAnimal(AbstractAnimal abstractAnimal){
        potentialyDeadAbstractAnimals.add(abstractAnimal);
    }
    public double getAverageAgeOfDeadAnimals(){
        if(deadAnimals == 0){
            return 0;
        }
        else{
            return (double)agesofDeadAnimals/deadAnimals;
        }
    }
}
