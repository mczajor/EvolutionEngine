package Evo.map.utility;

import Evo.map.elements.AbstractAnimal;
import Evo.map.world.AbstractWorldMap;

import java.util.ArrayList;

public class InformantUnderTaker extends AbstractUnderTaker{
    AbstractGardener gardener;
    public InformantUnderTaker(AbstractWorldMap map, AbstractGardener gardener){
        super(map);
        this.gardener = gardener;
    }
    @Override
    public ArrayList<AbstractAnimal> buryTheDead(){
        ArrayList<AbstractAnimal> animals2Remove = new ArrayList<>();
        for(AbstractAnimal abstractAnimal : potentialyDeadAbstractAnimals){
            if(abstractAnimal.getEnergy() > 0){
                continue;
            }
            animals2Remove.add(abstractAnimal);
            map.removeAnimal(abstractAnimal);
            abstractAnimal.die();
            gardener.animalDied(abstractAnimal.getPosition());
        }
        potentialyDeadAbstractAnimals.clear();
        return animals2Remove;
    }
}

