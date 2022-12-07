package Evo.map.utility;

import Evo.map.elements.MoveVector;

public class LowestDeaths {

}



class DeathsOnPosition{
    MoveVector position;
    int deaths;
    public DeathsOnPosition(MoveVector position, int deaths){
        this.position = position;
        this.deaths = deaths;
    }
}