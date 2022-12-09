package Evo.map.world;
import Evo.map.elements.*;

public interface IPositionObserver {
    void positionChanged(AbstractAnimal movedAbstractAnimal, MoveVector oldPosition, MoveVector newPosition);
}
