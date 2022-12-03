package Evo.map;
import Evo.map.world.*;
import Evo.map.elements.*;
import Evo.map.utility.*;

public class Simulation {

    public static void main(String[] args){
        AbstractWorldMap map = new HellPortal(10, 10);
        MapVisualizer visualizer = new MapVisualizer(map);
        map.generateGrass(10);
        Animal test = new Animal(new MoveVector(0,0), map);
        Animal test2 = new Animal(new MoveVector(1,1), map);
        Animal test3 = new Animal(new MoveVector(2,2), map);
        Animal test4 = new Animal(new MoveVector(3,3), map);
        map.place(test);
        for(int i = 0; i < 100; i++){
            test.move();
            test2.move();
            test3.move();
            test4.move();
        }
        System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(9,9)));

    }
}
