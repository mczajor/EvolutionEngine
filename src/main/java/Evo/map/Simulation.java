package Evo.map;
import Evo.map.world.*;
import Evo.map.elements.*;
import Evo.map.utility.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Simulation {

    public static void main(String[] args){
        int dim = 20;
        run(dim);
    }
    public static void run(int dim){
        AbstractWorldMap map = new HellPortal(dim, dim, 5);
        MapVisualizer visualizer = new MapVisualizer(map);
        AbstractGardener gardener = new EquatorGardener(map, dim, 100, 50);
        List<Animal> animals = new ArrayList<>();
        AbstractUnderTaker underTaker = new UnderTaker(map);
        map.addUnderTaker(underTaker);
        for (int i = 0; i < 10; i++){
            Animal animal = new Animal(new MoveVector((int)(Math.random()*dim), (int)(Math.random()*dim)), map, 100);
            animals.add(animal);
            map.place(animal);
        }
        for(int i = 0; i < 30; i++){
            underTaker.buryTheDead();
            gardener.plant(10);
            animals = animals.stream().filter(animal -> !animal.isDead()).toList();
            for(Animal animal : animals){
                animal.move();
            }
            animals = Stream.concat(animals.stream(), map.mingle().stream()).toList();
            map.feast();
        }
        System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(19,19)));
    }
}
