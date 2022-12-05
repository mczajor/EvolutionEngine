package Evo.map.world;
import Evo.map.elements.*;
import Evo.map.utility.*;

import java.util.*;

public abstract class AbstractWorldMap implements IPositionObserver {
    protected final int width;
    protected final int height;
    /* Storing animals and Plants in different HashMaps because there can be multiple animals in one spot but only one plant*/
    protected final Map<MoveVector, ArrayList<Animal>> mapAnimals;
    private AbstractUnderTaker undertaker;
    private AbstractGardener gardener;
    private final int energyLossPerDay;
    public AbstractWorldMap(int width, int height, int energyLossPerDay){
        mapAnimals = new HashMap<>();
        this.width = width;
        this.height = height;
        this.energyLossPerDay = energyLossPerDay;
    }
    public void addUnderTaker(AbstractUnderTaker undertaker){
        this.undertaker = undertaker;
    }
    public void addGardener(AbstractGardener gardener){
        this.gardener = gardener;
    }
    public MoveVector getDimensions(){
        return new MoveVector(this.width, this.height);
    }

    public IMapElement objectAt(MoveVector position){
        if(this.mapAnimals.get(position) == null || this.mapAnimals.get(position).isEmpty())
            return gardener.plantAt(position);
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
        return !(mapAnimals.get(vector) == null && gardener.plantAt(vector) == null);
    }

    public void positionChanged(Animal movedElement, MoveVector oldPosition, MoveVector newPosition){
        movedElement.removeEnergy(this.energyLossPerDay);
        if(movedElement.getEnergy() <= 0)
            this.undertaker.addDyingAnimal(movedElement);
        if(oldPosition.equals(newPosition)){
            return;
        }
        if (this.mapAnimals.get(oldPosition) != null) {
            this.mapAnimals.get(oldPosition).remove(movedElement);
            if (this.mapAnimals.get(oldPosition).isEmpty()) {
                this.mapAnimals.remove(oldPosition);
            }
        }
        this.mapAnimals.computeIfAbsent(newPosition, s -> new ArrayList<>());
        this.mapAnimals.get(newPosition).add(movedElement);
    }


    public void removeAnimal(Animal animal){
        this.mapAnimals.get(animal.getPosition()).remove(animal);
        if(this.mapAnimals.get(animal.getPosition()).isEmpty())
            this.mapAnimals.remove(animal.getPosition());
    }
    public void feast(){
        for(MoveVector key: this.mapAnimals.keySet()){
            if(this.gardener.plantAt(key) != null && !this.mapAnimals.get(key).isEmpty()){
                Animal max = this.mapAnimals.get(key).stream().max(Comparator.comparingInt(Animal::getEnergy)).get();
                max.eat(this.gardener.plantAt(key));
                this.gardener.plantGotEaten(key);
            }
        }
    }
    public List<Animal> mingle(){
        List<Animal> children = new ArrayList<>();
        for(MoveVector key: this.mapAnimals.keySet()){
            if(this.mapAnimals.get(key).size() > 1){
                this.mapAnimals.get(key).sort(Comparator.comparingInt(Animal::getEnergy));
                Animal child = this.mapAnimals.get(key).get(0).reproduce(this.mapAnimals.get(key).get(1));
                this.mapAnimals.get(key).add(child);
                children.add(child);
            }
        }
        return children;
    }
}
