package Evo.map.elements;

public abstract class AbstractGenotype {
    protected final int[] genes;
    protected int minMutations;
    protected int maxMutations;
    protected int length;
    public AbstractGenotype(int genomeLength, int minGenomeMutations, int maxGenomeMutations){
        this.length = genomeLength;
        minMutations = minGenomeMutations;
        maxMutations = maxGenomeMutations;
        this.genes = new int[this.length];
        for(int i = 0; i < this.length; i++){
            this.genes[i] = (int)(Math.random() * 8);
        }
    }
    public AbstractGenotype(AbstractAnimal parent1, AbstractAnimal parent2){
        this.length = parent1.getGenotype().getLength();
        this.genes = new int[this.length];
        int energy1 = parent1.getEnergy();
        int energy2 = parent2.getEnergy();
        int ratio = (int) (this.length * (double) energy1/(energy1+energy2));
        double side = Math.random();
        for (int i = 0; i < ratio; i++) {
            this.genes[i] = parent1.getGenotype().getGenotype()[i];
        }
        for (int i = ratio; i < this.length; i++) {
            this.genes[i] = parent2.getGenotype().getGenotype()[i];
        }

    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.length; i++){
            sb.append(genes[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof AbstractGenotype other)){
            return false;
        }
        for(int i = 0; i < this.length; i++){
            if(this.genes[i] != other.genes[i]){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode(){
        int result = 0;
        for(int i = 0; i < this.length-1; i++){
            result += i * this.genes[i];
        }
        return result;
    }

    public int[] getGenotype(){
        return this.genes;
    }
    public int getLength(){
        return this.length;
    }
}
