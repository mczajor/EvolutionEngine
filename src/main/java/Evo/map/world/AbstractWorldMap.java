package Evo.map.world;
import Evo.map.elements.*;
import Evo.map.utility.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IPositionObserver {
    protected final int width;
    protected final int height;
    /* Storing animals and Plants in different HashMaps because there can be multiple animals in one spot but only one plant*/
    public final Map<MoveVector, ArrayList<Animal>> mapAnimals;
    public final Map<MoveVector, Plant> mapPlants;
    private AbstractGardener gardener;
    private AbstractUnderTaker undertaker;
    private final int energyLossPerDay;
    private final int initialPlantEnergy = 50;
    public AbstractWorldMap(int width, int height, int energyLossPerDay){
        mapAnimals = new HashMap<>();
        mapPlants = new HashMap<>();
        this.width = width;
        this.height = height;
        this.energyLossPerDay = energyLossPerDay;
    }
    public void addGardener(AbstractGardener gardener){
        this.gardener = gardener;
    }
    public void addUnderTaker(AbstractUnderTaker undertaker){
        this.undertaker = undertaker;
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

    public void plant(MoveVector position){
        Plant plant = new Plant(position, this.initialPlantEnergy);
        this.mapPlants.put(position, plant);
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
        movedElement.removeEnergy(this.energyLossPerDay);
        if (this.mapAnimals.get(oldPosition) != null) {
            this.mapAnimals.get(oldPosition).remove(movedElement);
            if (this.mapAnimals.get(oldPosition).isEmpty()) {
                this.mapAnimals.remove(oldPosition);
            }
        }
        this.mapAnimals.computeIfAbsent(newPosition, s -> new ArrayList<>());
        this.mapAnimals.get(newPosition).add(movedElement);
        //if(movedElement.getEnergy() <= 0){
            //this.undertaker.addDyingAnimal(movedElement);
        //}
    }
    public void removeAnimal(Animal animal){
        this.mapAnimals.get(animal.getPosition()).remove(animal);
        if(this.mapAnimals.get(animal.getPosition()).isEmpty())
            this.mapAnimals.remove(animal.getPosition());
    }
}
