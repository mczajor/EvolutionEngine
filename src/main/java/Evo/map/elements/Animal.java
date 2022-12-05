package Evo.map.elements;
import Evo.map.world.*;

import java.util.ArrayList;

public class Animal implements IMapElement {
    private  MoveVector position;
    private Orientation orientation;
    private final Genotype genotype;
    private int energy;
    private final ArrayList<IPositionObserver> observers = new ArrayList<>();
    private int age;
    private boolean isDead = false;
    public final int status;

    //Contructor for initial animals
    public Animal(MoveVector position, IPositionObserver map, int startEnergy){
        this.position = position;
        this.orientation = Orientation.randomOrientation();
        this.observers.add(map);
        this.energy = startEnergy;
        this.genotype = new Genotype();
        this.age = 0;
        this.status = 0;
    }
    //Constructor for children of animals
    public Animal(MoveVector position, Animal parent1, Animal parent2){
        this.position = position;
        this.orientation = Orientation.randomOrientation();
        genotype = new Genotype(parent1, parent2);
        this.status = ((parent1.status + parent2.status)/2 + 1);
    }

    @Override
    public String toString(){ return ""+this.status; }
    public MoveVector getPosition(){
        return this.position;
    }
    public Orientation getOrientation(){
        return this.orientation;
    }
    public int[] getGenotype(){
        return this.genotype.getGenotype();
    }
    public int getEnergy(){
        return this.energy;
    }

    public void removeEnergy(int energy){
        this.energy -= energy;
    }
    public void addEnergy(int energy){
        this.energy += energy;
    }
    public boolean isDead(){
        return this.isDead;
    }
    public void die(){
        this.isDead = true;
    }
    //Changes orientation of animal based on their genotype
    private void changeOrientation(){
        int gene = genotype.getGenotype()[(int)(Math.random() * 32)];
        for (int i = 0; i < gene; i++) {
            this.orientation = this.orientation.next();
        }
    }
    //Turns the animal around. Only for Spherical World
    public void turnAround(){
        this.orientation = this.orientation.next().next().next().next();
    }
    private void positionChanged(MoveVector oldPosition, MoveVector newPosition){
        for(IPositionObserver observer: observers){
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

    public void  move(){
        changeOrientation();
        MoveVector nextMove = this.orientation.toUnitVector();
        this.age++;
        this.positionChanged(this.position, this.position.add(nextMove));
    }

    public boolean eat(Plant plant){
        energy += plant.getEnergy();
        return true;
    }

    public Animal reproduce(Animal parent){
        return new Animal(this.getPosition(), this, parent);
    }

    public void addObserver(IPositionObserver observer){
        this.observers.add(observer);
    }
    public void setPosition(MoveVector newPosition){
        this.position = newPosition;
    }
}
