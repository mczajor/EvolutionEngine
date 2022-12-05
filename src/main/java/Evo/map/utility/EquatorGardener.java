package Evo.map.utility;
import Evo.map.elements.MoveVector;
import Evo.map.elements.Plant;
import Evo.map.world.AbstractWorldMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EquatorGardener extends AbstractGardener {
    private final int jungleLowerBorder;
    private final int jungleUpperBorder;
    private final Map<Integer, ArrayList<Integer>> viableJunglePositions = new HashMap<>();
    private final Map<Integer, ArrayList<Integer>> viableBarrenPositions = new HashMap<>();
    public EquatorGardener(AbstractWorldMap map, int width, int height, int startingPlants, int plantEnergy){
        super(map, width, height, plantEnergy);
        this.jungleLowerBorder = (int)(height*0.5-(height*0.1));
        this.jungleUpperBorder = (int)(height*0.5+(height*0.1));
        for (int i = jungleLowerBorder; i < jungleUpperBorder; i++){
            this.viableJunglePositions.put(i, this.generateList(width));
        }
        for(int i = 0; i<jungleLowerBorder; i++){
            this.viableBarrenPositions.put(i, this.generateList(width));
        }
        for(int i=jungleUpperBorder; i<height; i++){
            this.viableBarrenPositions.put(i, this.generateList(width));
        }

        this.plant(startingPlants);
    }


    private ArrayList<Integer> generateList(int width){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0; i<width;i++){
            list.add(i);
        }
        return list;
    }
    public void plantGotEaten(MoveVector position){
        this.plantMap.remove(position);
        if(position.x < this.jungleLowerBorder || position.x > this.jungleUpperBorder){
             this.viableBarrenPositions.computeIfAbsent(position.x, s ->new ArrayList<Integer>());
             this.viableBarrenPositions.get(position.x).add(position.y);
        } else{
            this.viableJunglePositions.computeIfAbsent(position.x, s -> new ArrayList<Integer>());
            this.viableJunglePositions.get(position.x).add(position.y);
        }

    }
    public MoveVector generate(Map<Integer, ArrayList<Integer>> map){
        ArrayList<Integer> temp = new ArrayList<>(map.keySet());
        if (temp.isEmpty()){
            return null;
        }
        int randomX = temp.get((int)(Math.random()*temp.size()));
        temp = map.get(randomX);
        Integer randomY = temp.get((int)(Math.random()*temp.size()));
        map.get(randomX).remove(randomY);
        if (map.get(randomX).isEmpty()){
            map.remove(randomX);
        }
        return new MoveVector(randomX, randomY);
    }
    @Override
    public MoveVector generatePosition(){
        double random = Math.random();
        if(random < 0.8){
            return this.generate(this.viableJunglePositions);
        } else{
            return this.generate(this.viableBarrenPositions);
        }

    }
    public void plant(int amount) {
        for(int i=0; i<amount; i++){
            MoveVector position = this.generatePosition();
            if(position == null){
                continue;
            }
            Plant plant = new Plant(position, this.plantEnergy);
            plantMap.put(plant.getPosition(), plant);
        }
    }
}
