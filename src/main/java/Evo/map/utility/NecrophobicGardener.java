package Evo.map.utility;
import Evo.map.elements.MoveVector;
import Evo.map.world.AbstractWorldMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NecrophobicGardener extends AbstractGardener {
    //private final LowestDeaths deathManager;

    public NecrophobicGardener(AbstractWorldMap map, int width, int height, int startingPlants, int plantEnergy) {
        super(map, width, height, plantEnergy);
        this.plant(startingPlants);
    }
}
