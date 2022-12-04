package Evo.map;
import Evo.map.world.*;
import Evo.map.elements.*;
import Evo.map.utility.*;

public class Simulation {

    public static void main(String[] args){
        AbstractWorldMap map = new HellPortal(100, 100, 10);
        MapVisualizer visualizer = new MapVisualizer(map);
        AbstractGardener gardener = new EquatorGardener(100, 100, map);
        Animal test = new Animal(new MoveVector(0,0), map, 100);

        map.place(test);
        for(int i = 0; i < 100; i++){
            test.move();
            gardener.plant();
        }
        System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(99,99)));

    }
}
