package Evo.map.elements;

public class AdjustmentGenotype extends AbstractGenotype {
    public AdjustmentGenotype(int genomeLength, int minGenomeMutations, int maxGenomeMutations){
        super(genomeLength, minGenomeMutations, maxGenomeMutations);
    }
    public AdjustmentGenotype(AbstractAnimal parent1, AbstractAnimal parent2){
        super(parent1, parent2);
        this.mutate();
    }
    private void mutate(){
        int numOfMutations = (int)(Math.random() * (maxMutations - minMutations + 1) + minMutations);
        for(int i = 0; i < numOfMutations; i++){
            int index = (int)(Math.random() * length);
            if(Math.random() < 0.5){
                genes[index] = (genes[index] + 1) % 8;
            }
            else{
                genes[index] = (genes[index] - 1) % 8;
            }
        }
    }
}
