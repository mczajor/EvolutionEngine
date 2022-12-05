package Evo.map.utility;
import Evo.map.elements.MoveVector;
import Evo.map.elements.Plant;
import Evo.map.world.AbstractWorldMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EquatorGardener extends AbstractGardener {
    private final int jungleLowerBorder;
    private final int jungleUpperBorder;
    private final Map<Integer, ArrayList<Integer>> viablePositions = new HashMap<>();
    public EquatorGardener(AbstractWorldMap map, int width, int height, int startingPlants, int plantEnergy){
        super(map, width, height, plantEnergy);
        this.jungleLowerBorder = (int)(height*0.5-(height*0.1));
        this.jungleUpperBorder = (int)(height*0.5+(height*0.1));
        for (int i = 0; i<height; i++){
            this.viablePositions.put(i, new ArrayList<>());
            for(int j = 0; j<width; j++){
                this.viablePositions.get(i).add(j);
                }
            }
        this.plant(startingPlants);
    }

    public void plantGotEaten(MoveVector position){
        this.plantMap.remove(position);
        this.viablePositions.computeIfAbsent(position.x, k -> new ArrayList<>());
        this.viablePositions.get(position.x).add(position.y);
    }

    @Override
    public MoveVector generatePosition(){
        ArrayList<Integer> temp;
        if(Math.random() <= 0.2){
            temp = this.viablePositions.keySet().stream().filter(s -> s < this.jungleLowerBorder ||
                    s > this.jungleUpperBorder).collect(Collectors.toCollection(ArrayList::new));
        } else {
            temp = this.viablePositions.keySet().stream().filter(s -> s >= this.jungleLowerBorder &&
                    s <= this.jungleUpperBorder).collect(Collectors.toCollection(ArrayList::new));
        }
        if (temp.size() == 0){
            return null;
        }
        int randomY = temp.get((int)(Math.random()*temp.size()));
        temp = this.viablePositions.get(randomY);
        Integer randomX = temp.get((int)(Math.random()*temp.size()));
        this.viablePositions.get(randomY).remove(randomX);
        if (this.viablePositions.get(randomY).isEmpty()){
            this.viablePositions.remove(randomY);
        }
        return new MoveVector(randomX, randomY);
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
