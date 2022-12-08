package Evo.map.utility;
import Evo.map.elements.MoveVector;
import Evo.map.elements.Plant;
import Evo.map.world.AbstractWorldMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NecrophobicGardener extends AbstractGardener {
    private final Map<Integer, ArrayList<MoveVector>> deathCount = new LinkedHashMap<>();
    public NecrophobicGardener(AbstractWorldMap map, int width, int height, int startingPlants, int plantEnergy) {
        super(map, width, height, plantEnergy);
        ArrayList<MoveVector> temp = new ArrayList<>();
        this.deathCount.put(0, temp);
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                temp.add(new MoveVector(i, j));
            }
        }
        this.plant(startingPlants);
    }
    public void animalDied(MoveVector position){
        for(Integer key: this.deathCount.keySet()){
            ArrayList<MoveVector> temp = this.deathCount.get(key);
            if (!temp.contains(position)){
                continue;
            }
            temp.remove(position);
            if (temp.isEmpty()){
                this.deathCount.remove(key);
            }
            this.deathCount.computeIfAbsent(key+1, k -> new ArrayList<>());
            this.deathCount.get(key+1).add(position);
            break;
        }
    }
    public void plant(int amount){
        ArrayList<MoveVector> temp = new ArrayList<>();
        for (Integer key: this.deathCount.keySet()){
            temp.addAll(deathCount.get(key).stream().filter(s -> !this.plantMap.containsKey(s)).collect(Collectors.toCollection(ArrayList::new)));
            if(temp.size() >= amount){
                break;
            }
        }
        for (int i = 0; i<amount; i++){
            MoveVector position = temp.get((int)(Math.random()*temp.size()));
            this.plantMap.put(position, new Plant(position, this.plantEnergy));
            temp.remove(position);
        }
    }
}
