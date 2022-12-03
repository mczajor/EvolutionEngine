package Evo.map.world;
import Evo.map.elements.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IPositionObserver {
    protected final int width;
    protected final int height;
    /* Storing animals and Plants in different HashMaps because there can be multiple animals in one spot but only one plant*/
    public final Map<MoveVector, ArrayList<Animal>> mapAnimals;
    public final Map<MoveVector, Plant> mapPlants;
    public AbstractWorldMap(int width, int height){
        mapAnimals = new HashMap<>();
        mapPlants = new HashMap<>();
        this.width = width;
        this.height = height;
    }

    public MoveVector getDimensions(){
        return new MoveVector(this.width, this.height);
    }
    public boolean canMoveTo(MoveVector position){
        return position.x >= 0 && position.x < this.width && position.y >= 0 && position.y < this.height;
    }

    public IMapElement objectAt(MoveVector position){
        if(this.mapAnimals.get(position) == null || this.mapAnimals.get(position).isEmpty())
            return this.mapPlants.get(position);
        return this.mapAnimals.get(position).get(0);
    }

    public boolean place(Animal element){
        if(this.mapAnimals.get(element.getPosition()) != null) {
            return false;
        }
        this.mapAnimals.put(element.getPosition(), new ArrayList<>());
        this.mapAnimals.get(element.getPosition()).add(element);
        return true;
    }

    public boolean isOccupied(MoveVector vector){
        return !(mapAnimals.get(vector) == null && mapPlants.get(vector) == null);
    }

    public void positionChanged(Animal movedElement, MoveVector oldPosition, MoveVector newPosition){
        if (this.mapAnimals.get(oldPosition) != null) {
            this.mapAnimals.get(oldPosition).remove(movedElement);
            if (this.mapAnimals.get(oldPosition).isEmpty()) {
                this.mapAnimals.remove(oldPosition);
            }
        }
        this.mapAnimals.computeIfAbsent(newPosition, s -> new ArrayList<>());
        this.mapAnimals.get(newPosition).add(movedElement);
    }
}
