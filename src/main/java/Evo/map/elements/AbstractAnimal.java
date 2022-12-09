package Evo.map.elements;
import Evo.map.world.*;

import java.util.ArrayList;

public abstract class AbstractAnimal implements IMapElement {
    protected MoveVector position;
    protected Orientation orientation;
    protected final AbstractGenotype abstractGenotype;
    private int energy;
    protected int age = 0;
    private int children = 0;
    private int plantsEaten = 0;
    protected int activeGene = 0;
    private final ArrayList<IPositionObserver> observers = new ArrayList<>();
    private final int bornOn;
    private boolean isDead = false;
    static int energyForReproduction;
    static int genoType;

    //Contructor for initial animals
    public AbstractAnimal(MoveVector position, IPositionObserver map, int startEnergy, int reproductionEnergy,int speciesGenotype, int genomeLength, int minGenomeMutations, int maxGenomeMutations){
        this.position = position;
        this.orientation = Orientation.randomOrientation();
        this.observers.add(map);
        this.energy = startEnergy;
        genoType = speciesGenotype;
        if(genoType == 0){
            this.abstractGenotype = new RandomGenotype(genomeLength, minGenomeMutations, maxGenomeMutations);
        }
        else{
            this.abstractGenotype = new AdjustmentGenotype(genomeLength, minGenomeMutations, maxGenomeMutations);
        }
        this.bornOn = 0;
        this.age = 1;
        energyForReproduction = reproductionEnergy;
    }
    //Constructor for children of animals
    public AbstractAnimal(AbstractAnimal parent1, AbstractAnimal parent2){
        this.position = parent1.getPosition();
        this.orientation = Orientation.randomOrientation();
        if(genoType == 0){
            this.abstractGenotype = new RandomGenotype(parent1, parent2);
        }
        else{
            this.abstractGenotype = new AdjustmentGenotype(parent1, parent2);
        }
        this.bornOn = parent1.getAge() + parent1.getBornOn();
        this.energy = 2*energyForReproduction;
        this.observers.addAll(parent1.getObservers());
        parent1.removeEnergy(energyForReproduction);
        parent2.removeEnergy(energyForReproduction);
        parent1.addChildren();
        parent2.addChildren();
        //this.status = ((parent1.status + parent2.status)/2 + 1);
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
    private ArrayList<IPositionObserver> getObservers() {
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

    public int[] getGenotype(){
        return this.abstractGenotype.getGenotype();
    }
    public int getEnergy(){
        return this.energy;
    }
    //public void addObserver(IPositionObserver observer){this.observers.add(observer);}
    public void removeEnergy(int energy){
        this.energy -= energy;
    }
    public void eat(Plant plant){
        energy += plant.getEnergy();
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
