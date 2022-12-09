package Evo.map.elements;

import Evo.map.world.IPositionObserver;

public class DetermininisticAnimal extends AbstractAnimal{
    public DetermininisticAnimal(MoveVector position, IPositionObserver map, int startEnergy, int reproductionEnergy, int speciesGenotype, int genomeLength, int minGenomeMutations, int maxGenomeMutations){
        super(position, map, startEnergy, reproductionEnergy, speciesGenotype, genomeLength, minGenomeMutations, maxGenomeMutations);
    }
    public DetermininisticAnimal(AbstractAnimal parent1, AbstractAnimal parent2){
        super(parent1, parent2);
    }

    public void determineNextMove(){
        this.activeGene = (this.activeGene + 1) % this.abstractGenotype.getLength();
        int gene = this.abstractGenotype.getGenotype()[this.activeGene];
        for (int i = 0; i < gene; i++) {
            this.orientation = this.orientation.next();
        }
    }
    public AbstractAnimal reproduce(AbstractAnimal parent){
        return new DetermininisticAnimal(this, parent);
    }
}
