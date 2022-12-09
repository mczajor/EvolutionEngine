package Evo.map.utility;

import Evo.map.elements.AbstractAnimal;
import Evo.map.world.AbstractWorldMap;

public class InformantUnderTaker extends AbstractUnderTaker{
    AbstractGardener gardener;
    public InformantUnderTaker(AbstractWorldMap map, AbstractGardener gardener){
        super(map);
        this.gardener = gardener;
    }
    @Override
    public void buryTheDead(){
        for(AbstractAnimal abstractAnimal : potentialyDeadAbstractAnimals){
            if(abstractAnimal.getEnergy() > 0){
                continue;
            }
            map.removeAnimal(abstractAnimal);
            abstractAnimal.die();
            gardener.animalDied(abstractAnimal.getPosition());
        }
        potentialyDeadAbstractAnimals.clear();
    }
}

