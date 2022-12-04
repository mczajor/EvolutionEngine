package Evo.map.world;
import Evo.map.elements.*;

public class HellPortal extends AbstractWorldMap{
    public HellPortal(int width, int height, int energyLoss){
        super(width, height, energyLoss);
    }
    public void positionChanged(Animal animal, MoveVector oldPosition, MoveVector newPosition){
        if (newPosition.x > this.width-1 || newPosition.x < 0){
            newPosition = new MoveVector((int)(Math.random()*this.width), (int)(Math.random()*this.height));
        }
        if (newPosition.y > this.height-1 || newPosition.y < 0){
            animal.turnAround();
            newPosition = oldPosition;
        }
        animal.setPosition(newPosition);
        super.positionChanged(animal, oldPosition, newPosition);
    }
}
