package Evo.map.elements;
import Evo.map.world.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAnimal implements IMapElement {
    protected MoveVector position;
    protected Orientation orientation;
    protected final AbstractGenotype abstractGenotype;
    protected int energy;
    protected int age = 0;
    protected int children = 0;
    protected int plantsEaten = 0;
    protected int activeGene = 0;
    protected final ArrayList<IPositionObserver> observers = new ArrayList<>();
    protected final int bornOn;
    protected boolean isDead = false;
    protected static int energyForReproduction;
    protected static int genoType;
    public static Map<AbstractGenotype, Integer> genotypes = new HashMap<>();
    public static AbstractGenotype bestGenotype;
    public static int bestGenotypeCount = 0;
    protected Circle circle = new Circle();
    //Constructor for initial animals
    public AbstractAnimal(MoveVector position, IPositionObserver map, int startEnergy, int reproductionEnergy,int speciesGenotype, int genomeLength, int minGenomeMutations, int maxGenomeMutations){
        this.position = position;
        this.orientation = Orientation.randomOrientation();
        this.observers.add(map);
        this.energy = startEnergy;
        genoType = speciesGenotype;

        this.abstractGenotype = switch (genoType) {
            case 0 -> new RandomGenotype(genomeLength, minGenomeMutations, maxGenomeMutations);
            case 1 -> new AdjustmentGenotype(genomeLength, minGenomeMutations, maxGenomeMutations);
            default -> throw new IllegalStateException("Unexpected value: " + genoType);
        };

        this.bornOn = 0;
        energyForReproduction = reproductionEnergy;
    }
    //Constructor for children of animals
    public AbstractAnimal(AbstractAnimal parent1, AbstractAnimal parent2){
        this.position = parent1.getPosition();
        this.orientation = Orientation.randomOrientation();
        double side = Math.random();
        this.abstractGenotype = switch (genoType) {
            case 0 -> {
                if(side < 0.5){
                    yield new RandomGenotype(parent1, parent2);
                } else {
                    yield new RandomGenotype(parent2, parent1);
                }
            }
            case 1 -> {
                if(side < 0.5){
                    yield new AdjustmentGenotype(parent1, parent2);
                } else {
                    yield new AdjustmentGenotype(parent2, parent1);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + genoType);
        };

        genotypes.putIfAbsent(this.abstractGenotype, 0);
        genotypes.put(this.abstractGenotype, genotypes.get(this.abstractGenotype) + 1);
        if(genotypes.get(this.abstractGenotype) > bestGenotypeCount){
            bestGenotypeCount = genotypes.get(this.abstractGenotype);
            bestGenotype = this.abstractGenotype;
        }

        this.bornOn = parent1.getAge() + parent1.getBornOn();
        this.energy = 2*energyForReproduction;
        this.observers.addAll(parent1.getObservers());
        parent1.removeEnergy(energyForReproduction);
        parent2.removeEnergy(energyForReproduction);
        parent1.addChildren();
        parent2.addChildren();
    }


    @Override
    public String toString(){ return "" + this.age; }
    public MoveVector getPosition(){
        return this.position;
    }
    public void setPosition(MoveVector newPosition){
        this.position = newPosition;
    }

    public int getAge() {
        return age;
    }
    public int getBornOn() {
        return bornOn;
    }
    protected ArrayList<IPositionObserver> getObservers() {
        return this.observers;
    }
    public int getPlantsEaten(){
        return this.plantsEaten;
    }
    public int getChildren(){
        return this.children;
    }
    public void addChildren(){
        this.children++;
    }
    public AbstractGenotype getGenotype(){
        return this.abstractGenotype;
    }
    public boolean isRandom(){
        return this.abstractGenotype instanceof RandomGenotype;
    }
    public int getEnergy(){
        return this.energy;
    }
    //public void addObserver(IPositionObserver observer){this.observers.add(observer);}
    public void removeEnergy(int energy){
        this.energy -= energy;
    }
    public void addEnergy(int energy){
        this.energy += energy;
    }
    public Circle getCircle(){
        double ratio = (double)energyForReproduction/this.energy;
        if(ratio > 9){
            ratio = 9;
        } else if(ratio < 1){
            ratio = 1;
        }
        this.circle.setFill(new Color(1.0, ratio*0.1, ratio*0.1, 1));
        return this.circle;
    }
    public void eat(Plant plant){
        this.addEnergy(plant.getEnergy());
        this.plantsEaten++;
    }
    public void die(){
        this.isDead = true;
    }
    public boolean isDead(){
        return this.isDead;
    }
    //Changes orientation of animal based on their genotype
    public Orientation getOrientation(){
        return this.orientation;
    }
    //Turns the animal around. Only for Spherical World
    public void turnAround(){
        this.orientation = this.orientation.next().next().next().next();
    }
    protected void positionChanged(MoveVector oldPosition, MoveVector newPosition){
        for(IPositionObserver observer: observers){
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }
    public void  move(){}

    public AbstractAnimal reproduce(AbstractAnimal parent){
        return null;
    }
}
