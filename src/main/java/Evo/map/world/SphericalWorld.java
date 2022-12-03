package Evo.map.world;
import Evo.map.elements.Animal;
import Evo.map.elements.MoveVector;

public class SphericalWorld extends AbstractWorldMap{
    public SphericalWorld(int width, int height){
        super(width, height);
    }
    public void positionChanged(Animal animal, MoveVector oldPosition, MoveVector newPosition){
        if (newPosition.x > this.width-1){
            newPosition = new MoveVector(0, newPosition.y);
        } else if(newPosition.x < 0){
            newPosition = new MoveVector(this.width-1, newPosition.y);
        }
        if (newPosition.y > this.height-1 || newPosition.y < 0){
        }
        animal.setPosition(newPosition);
        super.positionChanged(animal, oldPosition, newPosition);
    }
}

