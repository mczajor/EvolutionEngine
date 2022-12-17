package Evo.map.utility;
import Evo.map.elements.MoveVector;
import Evo.map.elements.Plant;
import Evo.map.world.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGardener {
    AbstractWorldMap map;
    protected final int plantEnergy;
    protected final int height;
    protected final int width;
    protected final Map<MoveVector, Plant> plantMap = new HashMap<>();

    public AbstractGardener(AbstractWorldMap map, int width, int height, int plantEnergy){
        this.map = map;
        this.width = width;
        this.height = height;
        this.plantEnergy = plantEnergy;

    }
    public Map<MoveVector, Plant> getPlants(){
        return this.plantMap;
    }
    public Plant plantAt(MoveVector position){
        return plantMap.get(position);
    }
    public MoveVector generatePosition(){
        MoveVector dimensions = map.getDimensions();
        return new MoveVector((int)(Math.random()*dimensions.x), (int)(Math.random()*dimensions.y));
    }
    public void plant(int amount) {
        for(int i=0; i<amount; i++){
            MoveVector position = this.generatePosition();
            if(position == null){
                continue;
            }
            Plant plant = new Plant(position, this.plantEnergy);
            plantMap.put(plant.getPosition(), plant);
        }
    }
    public void plantGotEaten(MoveVector position){
        this.plantMap.remove(position);
    }
    public void animalDied(MoveVector position){}
}
