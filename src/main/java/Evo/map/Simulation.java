package Evo.map;
import Evo.map.world.*;
import Evo.map.elements.*;
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
        List<Animal> animals = new ArrayList<>();
        AbstractUnderTaker underTaker = new UnderTaker(map);
        map.addUnderTaker(underTaker);
        map.addGardener(gardener);
        for (int i = 0; i < 200; i++){
            Animal animal = new Animal(new MoveVector((int)(Math.random()*width), (int)(Math.random()*height)), map, 100);
            animals.add(animal);
            map.place(animal);
        }
        for(int i = 0; i < 1000; i++){
            //System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(19,19)));
            underTaker.buryTheDead();
            animals = animals.stream().filter(animal -> !animal.isDead()).toList();
            for(Animal animal : animals){
                animal.move();
            }
            map.feast();
            animals = Stream.concat(animals.stream(), map.mingle().stream()).toList();
            gardener.plant(2);
        }
        System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(width-1, height-1)));
    }
}
