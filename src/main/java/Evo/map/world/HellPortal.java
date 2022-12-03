package Evo.map.world;
import Evo.map.elements.*;

public class HellPortal extends AbstractWorldMap{
    public HellPortal(int width, int height){
        super(width, height);
    }
    public void positionChanged(Animal animal, MoveVector oldPosition, MoveVector newPosition){
        if (newPosition.x > this.width-1 || newPosition.x < 0){
            newPosition = new MoveVector((int)(Math.random()*this.width), newPosition.y);
        }
        if (newPosition.y > this.height-1 || newPosition.y < 0){
            newPosition = new MoveVector(newPosition.x, (int)(Math.random()*this.height));
        }
        animal.setPosition(newPosition);
        super.positionChanged(animal, oldPosition, newPosition);
    }
}
