package io.aashay.ui;

public class Canon {
    Sea sea;
    public Canon(Sea sea){
        this.sea = sea;
    }

    public boolean fire(int x, int y){
        boolean isShipThere = this.isShipThere(x, y);
        if(this.isShipThere(x, y)){
            sea.setObj(x,y,'-');
        }
        return isShipThere;
    }

    private boolean isShipThere(int x, int y){
        if(sea.getObj(x, y) == '-'){
            return false;
        }
        else{
            return true;
        }
    }
}