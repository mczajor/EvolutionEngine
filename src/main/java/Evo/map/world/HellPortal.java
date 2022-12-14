package Evo.map.world;
import Evo.map.elements.*;

public class HellPortal extends AbstractWorldMap{
    public HellPortal(int width, int height, int energyLoss, int reproductionThreshold){
        super(width, height, energyLoss, reproductionThreshold);
    }
    public void positionChanged(AbstractAnimal abstractAnimal, MoveVector oldPosition, MoveVector newPosition){
        if (newPosition.x > this.width-1 || newPosition.x < 0){
            newPosition = new MoveVector((int)(Math.random()*this.width), (int)(Math.random()*this.height));
        }
        if (newPosition.y > this.height-1 || newPosition.y < 0){
            abstractAnimal.turnAround();
            newPosition = oldPosition;
        }
        abstractAnimal.setPosition(newPosition);
        super.positionChanged(abstractAnimal, oldPosition, newPosition);
    }
}
