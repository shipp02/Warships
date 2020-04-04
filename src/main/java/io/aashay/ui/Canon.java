package io.aashay.ui;

import java.util.Iterator;

public class Canon implements CanonInterface{
    Sea sea;
    public Canon(Sea sea){
        this.sea = sea;
    }

    @Override
    public boolean fire(int x, int y){
        boolean isShipThere = this.isShipThere(x, y);
        if(this.isShipThere(x, y)){
            sea.setObj(x,y,'-');
        }
        return isShipThere;
    }

    private boolean isShipThere(int x, int y){
        return sea.getObj(x, y) != '-';
    }
}