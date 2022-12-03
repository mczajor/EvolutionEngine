package Evo.map.elements;

import java.util.Arrays;

public class Genotype {
    private final int[] genes;
    private int currActive;
    public Genotype(){
        genes = new int[32];
        for(int i = 0; i < 32; i++){
            genes[i] = (int)(Math.random() * 8);
        }
        currActive = (int)(Math.random() * 32);
    }
    public Genotype(Animal parent1, Animal parent2){
        genes = new int[32];
        int energy1 = parent1.getEnergy();
        int energy2 = parent2.getEnergy();
        double ratio = (double)1/(energy1+energy2);
        for(int i = 0; i < 32; i++){
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
        for(int i = 0; i < 32; i++){
            sb.append(genes[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    public int[] getGenotype(){
        return genes;
    }
}
