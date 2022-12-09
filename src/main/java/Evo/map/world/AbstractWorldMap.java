package Evo.map.world;
import Evo.map.elements.*;
import Evo.map.utility.*;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements IPositionObserver {
    protected final int width;
    protected final int height;

    protected final Map<MoveVector, ArrayList<AbstractAnimal>> mapAnimals;
    //Undertaker kills and buries animals
    private AbstractUnderTaker undertaker;
    //Gardener is responsible for all things plant related
    private AbstractGardener gardener;
    private final int reproductionThreshold;
    private final int energyLossPerDay;
    public AbstractWorldMap(int width, int height, int energyLossPerDay, int rThreshold){
        mapAnimals = new HashMap<>();
        this.width = width;
        this.height = height;
        this.energyLossPerDay = energyLossPerDay;
        this.reproductionThreshold = rThreshold;
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


    public boolean place(AbstractAnimal element){
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

    public void positionChanged(AbstractAnimal movedElement, MoveVector oldPosition, MoveVector newPosition){
        movedElement.removeEnergy(this.energyLossPerDay);
        if(movedElement.getEnergy() <= 0)
            this.undertaker.addDyingAnimal(movedElement); //Animal can be dying but eat before death making it not dead
        if(oldPosition.equals(newPosition)){
            return;
        }
        this.mapAnimals.get(oldPosition).remove(movedElement);
        if (this.mapAnimals.get(oldPosition).isEmpty()) {
            this.mapAnimals.remove(oldPosition);
        }
        this.mapAnimals.computeIfAbsent(newPosition, s -> new ArrayList<>());
        this.mapAnimals.get(newPosition).add(movedElement);
    }


    public void removeAnimal(AbstractAnimal abstractAnimal){
        this.mapAnimals.get(abstractAnimal.getPosition()).remove(abstractAnimal);
        if(this.mapAnimals.get(abstractAnimal.getPosition()).isEmpty())
            this.mapAnimals.remove(abstractAnimal.getPosition());
    }
    public void feast(){
        for(MoveVector key: this.mapAnimals.keySet()){
            if(this.gardener.plantAt(key) != null){
                AbstractAnimal max = this.mapAnimals.get(key).stream().max(Comparator.comparingInt(AbstractAnimal::getEnergy)).get();
                max.eat(this.gardener.plantAt(key));
                this.gardener.plantGotEaten(key);
            }
        }
    }
    public AbstractAnimal[] chooseParents(List<AbstractAnimal> possibleParents){
        if (possibleParents.size() < 2){
            return null;
        }
        if(possibleParents.size() == 2){
            return possibleParents.toArray(AbstractAnimal[]::new);
        }
        AbstractAnimal[] parents = new AbstractAnimal[2];
        possibleParents = possibleParents.stream().sorted(Comparator.comparingInt(AbstractAnimal::getEnergy)).collect(Collectors.toList());
        if(possibleParents.get(1).getEnergy() > possibleParents.get(2).getEnergy()){
            parents[0] = possibleParents.get(0);
            parents[1] = possibleParents.get(1);
            return parents;
        }
        possibleParents = possibleParents.stream().sorted(Comparator.comparingInt(AbstractAnimal::getAge)).collect(Collectors.toList());
        if(possibleParents.get(1).getAge() > possibleParents.get(2).getAge()){
            parents[0] = possibleParents.get(0);
            parents[1] = possibleParents.get(1);
            return parents;
        }
        possibleParents = possibleParents.stream().sorted(Comparator.comparingInt(AbstractAnimal::getChildren)).collect(Collectors.toList());
        parents[0] = possibleParents.get(0);
        parents[1] = possibleParents.get(1);
        return parents;
    }

    public List<AbstractAnimal> mingle(){
        List<AbstractAnimal> children = new ArrayList<>();
        for(MoveVector key: this.mapAnimals.keySet()) {
            AbstractAnimal[] parents = chooseParents(this.mapAnimals.get(key).stream().filter(animal -> animal.getEnergy() > this.reproductionThreshold).toList());
            if (parents == null){
                continue;
            }
            AbstractAnimal child = parents[0].reproduce(parents[1]);
            this.mapAnimals.get(key).add(child);
            children.add(child);
        }
        return children;
    }
}
