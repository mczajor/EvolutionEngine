package Evo.map.elements;

import java.util.Objects;

public class MoveVector {
    public final int x;
    public final int y;
    public MoveVector(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString(){
        return String.format("(%d,%d)", this.x, this.y);
    }
    @Override
    public int hashCode(){
        return Objects.hash(this.x, this.y);
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof MoveVector otherVector)) return false;
        return (this.x == otherVector.x && this.y == otherVector.y);
    }

    public MoveVector add(MoveVector other){
        return new MoveVector(this.x+other.x, this.y+other.y);
    }
}
