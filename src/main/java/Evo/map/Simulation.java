package Evo.map;
import Evo.map.elements.*;
import Evo.map.world.*;
import Evo.map.utility.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Simulation{
    private final int width;
    private final int height;
    private final AbstractWorldMap map;
    private final AbstractGardener gardener;
    private final AbstractUnderTaker underTaker;
    private final int plantsPerDay;
    private final ArrayList<AbstractAnimal> abstractAnimals = new ArrayList<>();
    private final int sleepTime;
    public Simulation(int mapType, int width, int height,
                      int gardenerType, int startPlants, int plantEnergy, int plantsPerDay,
                      int animalType, int startAnimals, int startEnergy, int energyLoss, int energyForReproduction, int reproductionThreshold,
                      int genomeType, int genomeLength, int minGenomeMutations, int maxGenomeMutations,
                      int sleepTime) throws IOException {

        this.width = width;
        this.height = height;

        if(mapType == 0){
            this.map = new HellPortal(width, height, energyLoss, reproductionThreshold);
        } else if(mapType == 1){
            this.map = new SphericalWorld(width, height, energyLoss, reproductionThreshold);
        } else{
            throw new IllegalArgumentException("Map type must be 0 or 1");
        }

        this.plantsPerDay = plantsPerDay;

        if(gardenerType == 0){
            gardener = new NecrophobicGardener(this.map, width, height, startPlants, plantEnergy);
            underTaker = new InformantUnderTaker(this.map, gardener);
        } else if(gardenerType == 1){
            gardener = new NecrophobicGardener(this.map, width, height, startPlants, plantEnergy);
            underTaker = new UnderTaker(this.map);
        } else{
            throw new IllegalArgumentException("Gardener type must be 0 or 1");
        }

        this.map.addUnderTaker(underTaker);
        this.map.addGardener(gardener);

        for (int i = 0; i < startAnimals; i++){
            AbstractAnimal animal;
            if(animalType == 0){
                animal = new DetermininisticAnimal(new MoveVector((int)(Math.random()*width), (int)(Math.random()*height)), this.map,
                        startEnergy, energyForReproduction,
                        genomeType, genomeLength,minGenomeMutations, maxGenomeMutations);
            } else if(animalType == 1){
                animal = new CrazyAnimal(new MoveVector((int)(Math.random()*width), (int)(Math.random()*height)), this.map,
                        startEnergy, energyForReproduction,
                        genomeType, genomeLength,minGenomeMutations, maxGenomeMutations);
            } else {
                throw new IllegalArgumentException("Animal type must be 0 or 1");
            }
            if(map.place(animal)){
                this.abstractAnimals.add(animal);
            }
        }
        this.sleepTime = sleepTime;
    }

    public Simulation(Path path) throws IOException, NumberFormatException {
        //Path path = Paths.get("src/main/resources/PreMadeConfigs/1.txt");
        Map<String, Integer> options = FileReader.byStream(path);
        this.width = options.get("width");
        this.height = options.get("height");

        if(options.get("mapType") == 0){
            this.map = new HellPortal(width, height, options.get("energyLoss"), options.get("reproductionThreshold"));
        } else{
            this.map = new SphericalWorld(width, height, options.get("energyLoss"), options.get("reproductionThreshold"));
        }

        this.plantsPerDay = options.get("plantsPerDay");

        if(options.get("plantGrowthType") == 0){
            gardener = new NecrophobicGardener(this.map, width, height, options.get("startPlants"), options.get("plantEnergy"));
            underTaker = new InformantUnderTaker(this.map, gardener);
        } else{
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
            AbstractAnimal animal;
            if(animalType == 0){
                animal = new DetermininisticAnimal(new MoveVector((int)(Math.random()*width), (int)(Math.random()*height)), this.map,
                        startEnergy, energyForReproduction,
                        genomeType, genomeLength,minGenomeMutations, maxGenomeMutations);
            } else{
                animal = new CrazyAnimal(new MoveVector((int)(Math.random()*width), (int)(Math.random()*height)), this.map,
                        startEnergy, energyForReproduction,
                        genomeType, genomeLength,minGenomeMutations, maxGenomeMutations);
            }
            if(map.place(animal)){
                this.abstractAnimals.add(animal);
            }
        }
        this.sleepTime = options.get("sleepTime");
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

            this.underTaker.buryTheDead();
            this.abstractAnimals.removeIf(AbstractAnimal::isDead);
            Collections.shuffle(abstractAnimals);

            this.abstractAnimals.forEach(AbstractAnimal::move);
            this.map.feast();
            this.abstractAnimals.addAll(map.mingle());
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
}
