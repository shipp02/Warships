package io.aashay.ui;

import java.util.ArrayList;

public class Ship {
    int length;
    int[] Pos;
    int Dir;
    ArrayList<int[]> PosOccupied = new ArrayList<>();
    Sea waters = null;

    /**
    * Creates a ship object with the given param
    *
    * @param length represnts the number of blicks occupied by the {@link Ship} ship
    * @param Pos represnts the position of the sip in x,y format
    * @param Dir represnts wether the direction is horizontal or vertical
    * @param sea represnts the sea object to which this ship belongs
     */
    public Ship(int length, int[] Pos, int Dir, Sea sea){
        this.length = length;
        this.Pos = Pos;
        this.Dir = Dir;
        this.waters = sea;
    }

    /**
    * adds which position is occupied by the ship to ArrayList
    * @param occupied
     */
    public void setOccPos(int[] occupied){
        this.PosOccupied.add(occupied);
    }

    /**
    * shows which part of the ship has been destroyed
    * @param pos
     */
    public void destroy(int[] pos){ 
        if(equalsShip(pos)!=-1){
            PosOccupied.remove(equalsShip(pos));
        }

    }

    /**
    * 
    * @param pos
    * @return
     */
    private int equalsShip(int[] pos){
        for (int[] pos_occ: PosOccupied) {
            if(pos_occ[0]==pos[0] && pos_occ[1]==pos[1]){
                return PosOccupied.indexOf(pos_occ);
            }
        }
        return -1;
    }

    /**
    * returns true if the ship has been destroyed
    * @return boolean
     */
    public boolean isItDestroyed(){
        boolean sunk = true;
        for (int[] pos : PosOccupied) {
            if(this.waters.getObj(pos[1], pos[0])!='-'){
                sunk = sunk && false;
            }else{
                sunk = sunk && true;
            }
        }
        return sunk;
    }
}
