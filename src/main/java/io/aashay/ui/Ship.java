package io.aashay.ui;

import java.util.ArrayList;

public class Ship {
    int length;
    int[] Pos;
    int Dir;
    ArrayList<int[]> PosOccupied = new ArrayList<>();
    Sea waters = null;

    public Ship(int length, int[] Pos, int Dir, Sea sea){
        this.length = length;
        this.Pos = Pos;
        this.Dir = Dir;
        this.waters = sea;
    }

    public void setOccPos(int[] occupied){
        this.PosOccupied.add(occupied);
    }

    public void destroy(int[] pos){ 
        if(equalsShip(pos)!=-1){
            PosOccupied.remove(equalsShip(pos));
        }

    }

    private int equalsShip(int[] pos){
        for (int[] pos_occ: PosOccupied) {
            if(pos_occ[0]==pos[0] && pos_occ[1]==pos[1]){
                return PosOccupied.indexOf(pos_occ);
            }
        }
        return -1;
    }

    public boolean isItDestroyed(){
        if(PosOccupied.size()==0){
            return true;
        }
        else{
            return false;
        }
    }

    public String toStringSunk(){
        return "Ship of size " + this.length + "was sunk.";
    }

    public boolean sunk(){
        for (int[] i : PosOccupied) {
            if(this.waters.getObj(i[1], i[0]) == '-'){
                return true;
            }
        }
        return false;
    }
}