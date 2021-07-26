package io.aashay.ui;


public class Canon implements CanonInterface {
    Sea sea;
    public Canon(Sea sea){
        this.sea = sea;
    }

    @Override
    // TODO: call destroy method of ship[]
    public boolean fire(int x, int y){
        boolean isShipThere = this.isShipThere(x, y);
        if(this.isShipThere(x, y)){
            sea.setObj(x,y,'-');
        }
        int[] pos = {x,y};
        this.sea.destroyShip(pos);
        return isShipThere;
    }

    private boolean isShipThere(int x, int y){
        return sea.getObj(x, y) != '-';
    }
}