package Evo.map.elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Orientation {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;
    private static final List<Orientation> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    @Override
    public String toString(){
        return switch (this) {
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
        };
    }
    public Orientation next(){
        return switch (this) {
            case NORTH -> Orientation.NORTHEAST;
            case NORTHEAST -> Orientation.EAST;
            case EAST ->  Orientation.SOUTHEAST;
            case SOUTHEAST -> Orientation.SOUTH;
            case SOUTH -> Orientation.SOUTHWEST;
            case SOUTHWEST -> Orientation.WEST;
            case WEST -> Orientation.NORTHWEST;
            case NORTHWEST -> Orientation.NORTH;
        };
    }
    public Orientation previous(){
        return switch (this) {
            case NORTH -> Orientation.NORTHWEST;
            case NORTHEAST -> Orientation.NORTH;
            case EAST ->  Orientation.NORTHEAST;
            case SOUTHEAST -> Orientation.EAST;
            case SOUTH -> Orientation.SOUTHEAST;
            case SOUTHWEST -> Orientation.SOUTH;
            case WEST -> Orientation.SOUTHWEST;
            case NORTHWEST -> Orientation.WEST;
        };
    }
    public MoveVector toUnitVector(){
        return switch (this) {
            case NORTH -> new MoveVector(0, 1);
            case NORTHEAST -> new MoveVector(1, 1);
            case EAST -> new MoveVector(1, 0);
            case SOUTHEAST -> new MoveVector(1, -1);
            case SOUTH -> new MoveVector(0, -1);
            case SOUTHWEST -> new MoveVector(-1, -1);
            case WEST -> new MoveVector(-1, 0);
            case NORTHWEST -> new MoveVector(-1, 1);
        };
    }
    public static Orientation randomOrientation()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
