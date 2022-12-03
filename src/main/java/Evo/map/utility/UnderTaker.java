package Evo.map.utility;

import Evo.map.elements.Animal;
import Evo.map.world.AbstractWorldMap;

import java.util.ArrayList;

public class UnderTaker {
    AbstractWorldMap map;
    ArrayList<Animal> potentialyDeadAnimals;

    public UnderTaker(AbstractWorldMap map){
        this.map = map;
    }
    public void buryTheDead() {
    }
}
