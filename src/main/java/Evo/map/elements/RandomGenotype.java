package Evo.map.elements;

public class RandomGenotype extends AbstractGenotype {
    public RandomGenotype(int genomeLength, int minGenomeMutations, int maxGenomeMutations){
        super(genomeLength, minGenomeMutations, maxGenomeMutations);

    }
    public RandomGenotype(AbstractAnimal parent1, AbstractAnimal parent2){
        super(parent1, parent2);
        this.mutate();
    }
    private void mutate(){
        int numOfMutations = (int)(Math.random() * (maxMutations - minMutations + 1) + minMutations);
        for(int i = 0; i < numOfMutations; i++){
            int index = (int)(Math.random() * length);
            genes[index] = (int)(Math.random() * 8);
        }
    }
}
