package Evo.map.elements;

abstract class AbstractGenotype {
    protected final int[] genes;
    static int minMutations;
    static int maxMutations;
    static int length;
    public AbstractGenotype(int genomeLength, int minGenomeMutations, int maxGenomeMutations){
        length = genomeLength;
        minMutations = minGenomeMutations;
        maxMutations = maxGenomeMutations;
        this.genes = new int[length];
        for(int i = 0; i < length; i++){
            genes[i] = (int)(Math.random() * 8);
        }
    }
    public AbstractGenotype(AbstractAnimal parent1, AbstractAnimal parent2){
        genes = new int[length];
        int energy1 = parent1.getEnergy();
        int energy2 = parent2.getEnergy();
        double ratio = (double)1/(energy1+energy2);
        for(int i = 0; i < length; i++){
            if(Math.random() <= energy1*ratio){
                genes[i] = parent1.getGenotype()[i];
            }
            else{
                genes[i] = parent2.getGenotype()[i];
            }
        }
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++){
            sb.append(genes[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    public int[] getGenotype(){
        return genes;
    }
    public int getLength(){
        return length;
    }
}
