package Evo.map;
import Evo.map.elements.*;
import Evo.map.world.*;
import Evo.map.utility.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Simulation{
    private int width;
    private int height;
    private AbstractWorldMap map;
    private AbstractGardener gardener;
    private AbstractUnderTaker underTaker;
    private int plantsPerDay;
    private ArrayList<AbstractAnimal> abstractAnimals = new ArrayList<>();
    private Map<AbstractGenotype, Integer> genotypes = new HashMap<>();
    private AbstractGenotype bestGenotype = null;
    private int bestGenotypeCount = 0;
    private int sleepTime;
    public Simulation(int mapType, int width, int height,
                      int gardenerType, int startPlants, int plantEnergy, int plantsPerDay,
                      int animalType, int startAnimals, int startEnergy, int energyLoss, int energyForReproduction, int reproductionThreshold,
                      int genomeType, int genomeLength, int minGenomeMutations, int maxGenomeMutations,
                      int sleepTime) throws IOException {
        Map<String, Integer> options = new HashMap<>();

        options.put("mapType", mapType);
        options.put("width", width);
        options.put("height", height);

        options.put("plantGrowthType", gardenerType);
        options.put("startPlants", startPlants);
        options.put("plantEnergy", plantEnergy);
        options.put("plantsPerDay", plantsPerDay);

        options.put("animalType", animalType);
        options.put("startAnimals", startAnimals);
        options.put("startEnergy", startEnergy);
        options.put("energyLoss", energyLoss);
        options.put("energyForReproduction", energyForReproduction);
        options.put("reproductionThreshold", reproductionThreshold);

        options.put("genomeType", genomeType);
        options.put("genomeLength", genomeLength);
        options.put("minGenomeMutations", minGenomeMutations);
        options.put("maxGenomeMutations", maxGenomeMutations);

        options.put("sleepTime", sleepTime);
        for(Integer option : options.values()){
            if(option < 0){
                throw new IllegalArgumentException();
            }
        }
        this.setVariables(options);
    }

    public Simulation(Path path) throws IOException, IllegalArgumentException  {
        Map<String, Integer> options = FileReader.byStream(path);
        for(Integer option : options.values()){
            if(option < 0){
                System.err.println("Error: Invalid input");
                throw new IllegalArgumentException();
            }
        }
        this.setVariables(options);
    }
    private void setVariables(Map<String, Integer> options) {
        System.out.println("options: " + options.toString());
        this.width = options.get("width");
        this.height = options.get("height");
        this.map = switch(options.get("mapType")){
            case 0 -> new HellPortal(width, height, options.get("energyLoss"), options.get("reproductionThreshold"));
            case 1 -> new SphericalWorld(width, height, options.get("energyLoss"), options.get("reproductionThreshold"));
            default -> {
                System.err.println("Invalid map type");
                throw new IllegalArgumentException();
            }
        };

        this.plantsPerDay = options.get("plantsPerDay");

        if(options.get("plantGrowthType") == 0){
            gardener = new NecrophobicGardener(this.map, width, height, options.get("startPlants"), options.get("plantEnergy"));
            underTaker = new InformantUnderTaker(this.map, gardener);
        } else {
            gardener = new EquatorGardener(this.map, width, height, options.get("startPlants"), options.get("plantEnergy"));
            underTaker = new UnderTaker(this.map);
        }
        this.map.addUnderTaker(underTaker);
        this.map.addGardener(gardener);

        int animalType = options.get("animalType");
        int startEnergy = options.get("startEnergy");
        int energyForReproduction = options.get("energyForReproduction");
        int genomeType = options.get("genomeType");
        int genomeLength = options.get("genomeLength");
        int minGenomeMutations = options.get("minGenomeMutations");
        int maxGenomeMutations = options.get("maxGenomeMutations");

        for (int i = 0; i < options.get("startAnimals"); i++){
            System.out.println("Chuj");
            AbstractAnimal animal = switch(animalType){
                case 0 -> new DetermininisticAnimal(new MoveVector((int)(Math.random()*width), (int)(Math.random()*height)), this.map,
                        startEnergy, energyForReproduction,
                        genomeType, genomeLength,minGenomeMutations, maxGenomeMutations);
                case 1 -> new CrazyAnimal(new MoveVector((int)(Math.random()*width), (int)(Math.random()*height)), this.map,
                        startEnergy, energyForReproduction,
                        genomeType, genomeLength,minGenomeMutations, maxGenomeMutations);
                default -> {
                    System.err.println("Invalid animal type");
                    throw new IllegalArgumentException();
                }
            };
            if(map.place(animal)){
                this.abstractAnimals.add(animal);
            }
        }
        this.addGenotypes(this.abstractAnimals);

        this.sleepTime = options.get("sleepTime");
    }
    private void addGenotypes(ArrayList<AbstractAnimal> animals2Add){
        for(AbstractAnimal animal : animals2Add){
            AbstractGenotype genotype = animal.getGenotype();
            this.genotypes.putIfAbsent(genotype, 0);
            this.genotypes.put(genotype, this.genotypes.get(genotype) + 1);
            if(this.genotypes.get(genotype) > this.bestGenotypeCount){
                this.bestGenotypeCount = this.genotypes.get(genotype);
                this.bestGenotype = genotype;
            }
        }
    }

    private void findNewBestGenotype(){
        int bestGenotypeCount = 0;
        AbstractGenotype bestGenotype = null;
        for(Map.Entry<AbstractGenotype, Integer> entry : this.genotypes.entrySet()){
            if(entry.getValue() > bestGenotypeCount){
                bestGenotypeCount = entry.getValue();
                bestGenotype = entry.getKey();
            }
        }
        this.bestGenotypeCount = bestGenotypeCount;
        this.bestGenotype = bestGenotype;
    }

    private void removeGenotypes(ArrayList<AbstractAnimal> animals2Remove){
        for(AbstractAnimal animal: animals2Remove){
            AbstractGenotype genotype = animal.getGenotype();
            this.genotypes.put(genotype, this.genotypes.get(genotype) - 1);
            if(genotype == this.bestGenotype && this.genotypes.get(genotype) < this.bestGenotypeCount){
                this.findNewBestGenotype();
            }
            if(this.genotypes.get(genotype) == 0){
                this.genotypes.remove(genotype);
            }
        }
    }

    public MoveVector getBoundry(){
        return new MoveVector(width, height);
    }
    public Map<MoveVector, AbstractAnimal> getAnimals(){
        return map.getMap();
    }
    public Map<MoveVector, Plant> getListPlants(){
        return gardener.getPlants();
    }
    public int getSleepTime(){
        return sleepTime;
    }
    public void simulateDay(){

            this.removeGenotypes(underTaker.buryTheDead());
            this.abstractAnimals.removeIf(AbstractAnimal::isDead);
            Collections.shuffle(abstractAnimals);

            this.abstractAnimals.forEach(AbstractAnimal::move);
            this.map.feast();
            ArrayList<AbstractAnimal> newAnimals = map.mingle();
            this.addGenotypes(newAnimals);
            this.abstractAnimals.addAll(newAnimals);
            this.gardener.plant(this.plantsPerDay);
    }
    public ArrayList<Float> getStats(){
        ArrayList<Float> stats = new ArrayList<>();
        stats.add( (float) this.abstractAnimals.size());
        stats.add( (float) this.gardener.getPlants().size());
        stats.add( (float) (this.width*this.height - this.abstractAnimals.size()));
        stats.add( (float) this.abstractAnimals.stream().mapToInt(AbstractAnimal::getEnergy).average().orElse(0));
        stats.add( (float) this.underTaker.getAverageAgeOfDeadAnimals());
        return stats;
    }
    public int[] getBestGenotype(){
    return this.bestGenotype.getGenotype();
    }
}
