package Evo.map.utility;

import Evo.map.elements.Animal;
import Evo.map.world.AbstractWorldMap;

public class InformantUnderTaker extends AbstractUnderTaker{
    NecrophobicGardener gardener;
    public InformantUnderTaker(AbstractWorldMap map, NecrophobicGardener gardener){
        super(map);
        this.gardener = gardener;
    }
    @Override
    public void buryTheDead(){
        for(Animal animal : potentialyDeadAnimals){
            if(animal.getEnergy() > 0){
                continue;
            }
            map.removeAnimal(animal);
            animal.die();
            gardener.animalDied(animal.getPosition());
        }
        potentialyDeadAnimals.clear();
    }
}

