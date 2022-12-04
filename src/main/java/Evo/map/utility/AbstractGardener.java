package Evo.map.utility;
import Evo.map.elements.MoveVector;
import Evo.map.elements.Plant;
import Evo.map.world.*;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGardener {
    AbstractWorldMap map;
    protected final int plantEnergy;
    private Map<Integer, MoveVector> viablePositions;
    protected final int dim;
    public AbstractGardener(AbstractWorldMap map, int dim, int plantEnergy){
        this.map = map;
        viablePositions = new HashMap<>();
        this.dim = dim;
        this.plantEnergy = plantEnergy;

    }
    public MoveVector generatePosition(){
        MoveVector dimensions = map.getDimensions();
        return new MoveVector((int)(Math.random()*dimensions.x), (int)(Math.random()*dimensions.y));
    }
    public void plant(int amount) {}
}
