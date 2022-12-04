package Evo.map.utility;
import Evo.map.elements.MoveVector;
import Evo.map.elements.Plant;
import Evo.map.world.*;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGardener {
    AbstractWorldMap map;
    private Map<Integer, MoveVector> viablePositions;
    protected final int height;
    public AbstractGardener(int width, int height, AbstractWorldMap map){
        this.map = map;
        viablePositions = new HashMap<>();
        this.height = height;

    }
    public MoveVector generatePosition(){
        MoveVector dimensions = map.getDimensions();
        return new MoveVector((int)(Math.random()*dimensions.x), (int)(Math.random()*dimensions.y));
    }
    public void plant() {}
}
