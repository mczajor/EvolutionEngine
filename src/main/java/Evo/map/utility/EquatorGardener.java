package Evo.map.utility;
import Evo.map.elements.MoveVector;
import Evo.map.elements.Plant;
import Evo.map.world.AbstractWorldMap;

public class EquatorGardener extends AbstractGardener {
    private final int jungleLowerBorder;
    private final int jungleUpperBorder;
    public EquatorGardener(int width, int height, AbstractWorldMap map){
        super(width, height, map);
        double center = height*0.5;
        this.jungleLowerBorder = (int)(center-(height*0.1));
        this.jungleUpperBorder = (int)(center+(height*0.1));
    }
    @Override
    public MoveVector generatePosition(){
        int generatedX;
        double random = Math.random();
        if(0.1 < random && random < 0.9){
            generatedX = (int)(Math.random()*(this.jungleUpperBorder-this.jungleLowerBorder))+this.jungleLowerBorder;
        } else if ( 0.9 <= random){
            generatedX = (int)((Math.random()*this.jungleLowerBorder)+this.jungleUpperBorder);
        } else{
            generatedX = (int)(Math.random()*this.jungleLowerBorder);
        }
        return new MoveVector(generatedX, (int)(Math.random()*this.height));
    }
    public void plant() {
        MoveVector position;
        do{
            position = generatePosition();
        } while (map.mapPlants.containsKey(position));
        this.map.plant(position);
    }
}
