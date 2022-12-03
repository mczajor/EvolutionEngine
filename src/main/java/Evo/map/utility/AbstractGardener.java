package Evo.map.utility;
import Evo.map.elements.MoveVector;
import Evo.map.elements.Plant;
import Evo.map.world.*;

public abstract class AbstractGardener {
    AbstractWorldMap map;
    public AbstractGardener(AbstractWorldMap map){
        this.map = map;
    }
    public MoveVector generatePosition(){
        MoveVector dimensions = map.getDimensions();
        return new MoveVector((int)(Math.random()*dimensions.x), (int)(Math.random()*dimensions.y));
    }
    public void plant() {

    }
}
