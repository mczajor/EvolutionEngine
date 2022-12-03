package Evo.map.elements;

public class Plant implements IMapElement {
    MoveVector position;
    final int energy;

    public Plant(MoveVector position, int energy){
        this.position = position;
        this.energy = energy;
    }
    public MoveVector getPosition(){
        return this.position;
    }
    public int getEnergy(){
        return this.energy;
    }
    public void setPosition(MoveVector newPosition){
        this.position = newPosition;
    }
    @Override
    public String toString(){
        return "*";
    }
}
