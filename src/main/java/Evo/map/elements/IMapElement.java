package Evo.map.elements;

public interface IMapElement {
    MoveVector getPosition();
    int getEnergy();
    void setPosition(MoveVector newPosition);
    String toString();
}
