package Evo.map.elements;

import Evo.map.world.IPositionObserver;

public class CrazyAnimal extends AbstractAnimal{
    public CrazyAnimal(MoveVector position, IPositionObserver map, int startEnergy, int reproductionEnergy, int speciesGenotype, int genomeLength, int minGenomeMutations, int maxGenomeMutations){
        super(position, map, startEnergy, reproductionEnergy, speciesGenotype, genomeLength, minGenomeMutations, maxGenomeMutations);
    }
    public CrazyAnimal(AbstractAnimal parent1, AbstractAnimal parent2){
        super(parent1, parent2);
    }
    public void deremineNextMove(){
        if(Math.random() > 0.2){
            this.activeGene = (this.activeGene + 1) % this.abstractGenotype.getLength();
        }
        else{
            this.activeGene = (int)(Math.random() * this.abstractGenotype.getLength());
        }
        int gene = this.abstractGenotype.getGenotype()[this.activeGene];
        for (int i = 0; i < gene; i++) {
            this.orientation = this.orientation.next();
        }
    }
    public AbstractAnimal reproduce(AbstractAnimal parent){
        return new CrazyAnimal(this, parent);
    }
}
