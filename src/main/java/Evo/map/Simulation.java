package Evo.map;
import Evo.map.elements.MoveVector;
import Evo.map.world.*;
import Evo.map.elements.Animal;
import Evo.map.utility.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Simulation {

    public static void main(String[] args){
        int width = 50;
        int height = 50;
        run(width, height);
    }
    public static void run(int width, int height){
        AbstractWorldMap map = new HellPortal(width, height, 5);
        MapVisualizer visualizer = new MapVisualizer(map);
        AbstractGardener gardener = new EquatorGardener(map, width, height, 50, 50);
        ArrayList<Animal> animals = new ArrayList<>();
        AbstractUnderTaker underTaker = new UnderTaker(map);
        map.addUnderTaker(underTaker);
        map.addGardener(gardener);
        for (int i = 0; i < 200; i++){
            Animal animal = new Animal(new MoveVector((int)(Math.random()*width), (int)(Math.random()*height)), map, 100);
            if(map.place(animal)){
                animals.add(animal);
            }
        }
        for(int i = 0; i < 1000; i++){
            underTaker.buryTheDead();
            animals.removeIf(Animal::isDead);
            animals.forEach(Animal::move);
            map.feast();
            animals.addAll(map.mingle());
            gardener.plant(2);
        }
        System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(width-1, height-1)));
    }
}
