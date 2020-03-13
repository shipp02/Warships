package io.aashay.ui;

import java.util.ArrayList;
import java.util.Iterator;

public class Ship {
    int length;
    int[] Pos;
    int Dir;
    ArrayList<int[]> PosOccupied = new ArrayList<>();
    Sea waters = null;
    private Sea sea;

    public Ship(int length, int[] Pos, int Dir, Sea sea){
        this.length = length;
        this.Pos = Pos;
        this.Dir = Dir;
        this.waters = sea;
    }

    public void setOccPos(int[] occupied){
        this.PosOccupied.add(occupied);
    }

    public boolean isItDestroyed(){
        boolean sunk = true;
        for (int[] pos : PosOccupied) {
            if(this.waters.getObj(pos[1], pos[0])!='-'){
                sunk = sunk && false;
                System.out.println("Ship isItDestroyed if obj=" + this.waters.getObj(pos[1], pos[0]));
            }else{
                sunk = sunk && true;
                System.out.println("Ship isItDestroyed else obj=" + this.waters.getObj(pos[1], pos[0]));
            }
        }
        return sunk;
    }

    public String toStringSunk(){
        return "Ship of size " + this.length + "was sunk.";
    }

}