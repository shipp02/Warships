package io.aashay.ui;

import java.util.ArrayList;
import java.util.Iterator;

// TODO: add destroy method which removes Place form PlaceList
public class Ship {
    int length;
    int[] Pos;
    int Dir;
    PlaceList PosOccupied = new PlaceList();
    Sea waters = null;
    private Sea sea;

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
    public void addOccPos(int[] occupied){
        this.PosOccupied.getPlaces().add(new Place(occupied[0], occupied[1]));
    }

    public void addOccPos(Place p){
        this.PosOccupied.getPlaces().add(p);
    }

    /**
    * shows which part of the ship has been destroyed
    * @param pos
     */
    public void destroy(int[] pos){ 
        if(equalsShip(pos)!=-1){
            PosOccupied.remove(new Place(pos[0], pos[1]));
        }
    }

    /**
    * 
    * @param pos
    * @return
     */
    private int equalsShip(int[] pos){
        Place pPos = new Place( pos[0], pos[1]);
        return this.PosOccupied.indexOf(pPos);
    }

    /**
    * returns true if the ship has been destroyed
    * @return boolean
     */
    public boolean isItDestroyed(){
        if(PosOccupied.getPlaces().size() == 0) { return true;}
        else{return false;}
    }
}

// TODO: Add remove method with both int[] and Place support
class PlaceList {

    private ArrayList<Place> arrayList = null;

    public ArrayList<Place> getPlaces(){return arrayList;}
    /**
     * This is to be used with parameter-less constructor as new PlaceList.create(aList)
     * @param aList list of places where ship is positioned
     */
    public PlaceList create(ArrayList<int[]> aList) {
        this.arrayList = new ArrayList<>();
        for (int[] is : aList) {
            this.arrayList.add(new Place(is[0], is[1]));
        }
        return this;
    }

    public PlaceList (ArrayList<Place> pList) {
        this.arrayList = pList;
    }


    /**
     * Parameter-less constructor.
     * Be careful this.arrayList can be null
     */
    public PlaceList() {
        this.arrayList =  new ArrayList<>();
    }

    /**
     * @return ArrayList where places are stored
     */
    public ArrayList<Place> getList() {
        return arrayList;
    }

    /**
     * 
     * @param aList list of places where ship is present
     * @return true if arrayList was set. False if it was not
     */
    public boolean setList(ArrayList<Place> aList) {
        if(this.arrayList == null) {
            this.arrayList = aList;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if two positions are equal
     */
    public int indexOf(int[] iarr) {
        Place ip = new Place(iarr[0], iarr[1]);
        for (int i = 0;i<this.arrayList.size();i++){
            if(this.arrayList.get(i).equals(ip)){
                return i;
            }
        }
        return -1;
    }

    public int indexOf(Place p) {
        int[] temp = {p.getX(), p.getY()};
        return this.indexOf(temp);
    }

    public void remove(Place p){
        this.arrayList.remove(this.indexOf(p));
    }
}

class Place {
    private int[] place = new int[2];

    public Place (int x, int y){
        this.place[0] = x;
        this.place[1] = y;
    }

    @Override
    public boolean equals(Object o){
        Place p = (Place) o;

        if(p.getX() == this.getX() && p.getY() == this.getY()){
            return true;
        }

        return false;
    }

    public int getX() {
        return this.place[0];
    }

    public int getY() {
        return this.place[1];
    }

}