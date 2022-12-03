package Evo.map.utility;
import Evo.map.elements.MoveVector;
import Evo.map.elements.Plant;
import Evo.map.world.AbstractWorldMap;

public class EquatorGardener extends AbstractGardener {
    public EquatorGardener(AbstractWorldMap map){
        super(map);
    }
    @Override
    public void plant() {
        MoveVector dimensions = map.getDimensions();
        }
    }
}
}
