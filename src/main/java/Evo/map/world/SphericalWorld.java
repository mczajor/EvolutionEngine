package Evo.map.world;
import Evo.map.elements.AbstractAnimal;
import Evo.map.elements.MoveVector;

public class SphericalWorld extends AbstractWorldMap{
    public SphericalWorld(int width, int height, int energyLoss, int reproductionThreshold){
        super(width, height, energyLoss, reproductionThreshold);
    }
    public void positionChanged(AbstractAnimal abstractAnimal, MoveVector oldPosition, MoveVector newPosition){
        if (newPosition.x > this.width-1){
            newPosition = new MoveVector(0, newPosition.y);
        } else if(newPosition.x < 0){
            newPosition = new MoveVector(this.width-1, newPosition.y);
        }
        if (newPosition.y > this.height-1 || newPosition.y < 0){
            newPosition = oldPosition;
        }
        abstractAnimal.turnAround();
        abstractAnimal.setPosition(newPosition);
        super.positionChanged(abstractAnimal, oldPosition, newPosition);
    }
}

