package io.aashay.ui;

public enum Vehicles {
    AircraftCarrier(5, 'A'),
    Destroyer(2, 'D'),
    Cruiser(3, 'C'),
    BattleShip(4, 'B');

    private final int size;
    private final char shape;

    private Vehicles(int size, char shape){
        this.size = size;
        this.shape = shape;
    }

    public int getSize() {
        return size;
    }

    public char getShape() {
        return shape;
    }

    public static Vehicles getBySize(int  size) {
        for (Vehicles vehicles : Vehicles.values()) {
            if(vehicles.getSize() == size)
            {    return vehicles;}
        }
        return null;
    }

    public static Vehicles getByShape(char shape) {
        for (Vehicles vehicles : Vehicles.values()) {
            if(vehicles.getShape() == shape)
            {    return vehicles;}
        }
        return null;
    }

}
