package Evo.map.elements;
import Evo.map.world.*;

import java.util.ArrayList;

public class Animal implements IMapElement {
    private  MoveVector position;
    private Orientation orientation;
    private final Genotype genotype;
    private int energy;
    private final ArrayList<IPositionObserver> observers = new ArrayList<>();

    //Contructor for initial animals
    public Animal(MoveVector position, IPositionObserver map){
        this.position = position;
        this.orientation = Orientation.randomOrientation();
        observers.add(map);
        genotype = new Genotype();
    }
    //Constructor for children of animals
    public Animal(MoveVector position, Animal parent1, Animal parent2){
        this.position = position;
        this.orientation = Orientation.randomOrientation();
        genotype = new Genotype(parent1, parent2);
    }

    @Override
    public String toString(){ return this.orientation.toString(); }
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
