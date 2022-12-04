package Evo.map.utility;
import Evo.map.elements.MoveVector;
import Evo.map.elements.Plant;
import Evo.map.world.AbstractWorldMap;

public class EquatorGardener extends AbstractGardener {
    private final int jungleLowerBorder;
    private final int jungleUpperBorder;
    public EquatorGardener(AbstractWorldMap map, int dim, int startingPlants, int plantEnergy){
        super(map, dim, plantEnergy);
        this.jungleLowerBorder = (int)(dim*0.5-(dim*0.1));
        this.jungleUpperBorder = (int)(dim*0.5+(dim*0.1));
        this.plant(startingPlants);
    }
    @Override
    public MoveVector generatePosition(){
        int generatedY;
        double random = Math.random();
        if(0.1 < random && random < 0.9){
            generatedY = (int)(Math.random()*(this.jungleUpperBorder-this.jungleLowerBorder))+this.jungleLowerBorder;
        } else if ( 0.9 <= random){
            generatedY = (int)((Math.random()*this.jungleLowerBorder)+this.jungleUpperBorder);
        } else{
            generatedY = (int)(Math.random()*this.jungleLowerBorder);
        }
        return new MoveVector((int)(Math.random()*this.dim), generatedY);
    }
    public void plant(int amount) {
        MoveVector position;
        do{
            position = generatePosition();
        } while (map.plantAt(position));
        Plant plant = new Plant(position, this.plantEnergy);
        this.map.plant(position, plant);
    }
}
