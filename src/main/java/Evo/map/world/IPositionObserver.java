package Evo.map.world;
import Evo.map.elements.*;

public interface IPositionObserver {
    void positionChanged(Animal movedAnimal, MoveVector oldPosition, MoveVector newPosition);
}
