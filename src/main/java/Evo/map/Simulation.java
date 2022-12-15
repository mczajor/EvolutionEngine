package Evo.map;
import Evo.map.elements.*;
import Evo.map.world.*;
import Evo.map.utility.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Simulation implements Runnable{
    private final int width;
    private final int height;
    private final AbstractWorldMap map;
    private final AbstractGardener gardener;
    private final AbstractUnderTaker underTaker;
    private final int plantsPerDay;
    private final ArrayList<AbstractAnimal> abstractAnimals = new ArrayList<>();
    public Simulation(int mapType, int width, int height,
                      int gardenerType, int startPlants, int plantEnergy, int plantsPerDay,
                      int animalType, int startAnimals, int startEnergy, int energyLoss, int energyForReproduction, int reproductionThreshold,
                      int genomeType, int genomeLength, int minGenomeMutations, int maxGenomeMutations){

        this.width = width;
        this.height = height;

        if(mapType == 0){
            this.map = new HellPortal(width, height, energyLoss, reproductionThreshold);
        } else{
            this.map = new SphericalWorld(width, height, energyLoss, reproductionThreshold);
        }

        this.plantsPerDay = plantsPerDay;

        if(gardenerType == 0){
            gardener = new NecrophobicGardener(this.map, width, height, startPlants, plantEnergy);
            underTaker = new InformantUnderTaker(this.map, gardener);
        } else{
            gardener = new NecrophobicGardener(this.map, width, height, startPlants, plantEnergy);
            underTaker = new UnderTaker(this.map);
        }

        this.map.addUnderTaker(underTaker);
        this.map.addGardener(gardener);

        for (int i = 0; i < startAnimals; i++){
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

        if(options.get("gardenerType") == 0){
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
    }
    public void run(){
        MapVisualizer visualizer = new MapVisualizer(this.map);

        //System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(width-1, height-1)));
        for(int i = 0; i < 10000; i++){
            //System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(width-1, height-1)));
            this.underTaker.buryTheDead();
            this.abstractAnimals.removeIf(AbstractAnimal::isDead);
            Collections.shuffle(abstractAnimals);
            this.abstractAnimals.forEach(AbstractAnimal::move);
            this.map.feast();
            this.abstractAnimals.addAll(map.mingle());
            this.gardener.plant(this.plantsPerDay);
        }
        //for(AbstractAnimal abstractAnimal : abstractAnimals) {
        //    System.out.println("Energy: " + abstractAnimal.getEnergy() + " Age: " + abstractAnimal.getAge() + " BornOn: " + abstractAnimal.getBornOn() + " Children: " + abstractAnimal.getChildren() + " PlantsEaten: " + abstractAnimal.getPlantsEaten() + " Current Position: " + abstractAnimal.getPosition() + " Genotype: " + abstractAnimal.isRandom());}
        System.out.println(visualizer.draw(new MoveVector(0,0), new MoveVector(this.width-1, this.height-1)));
    }
}
