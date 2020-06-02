package io.aashay.ui;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;



/**
 * This is the Sea on which the game is played. It is also gives a lot of abstraction to Application class
 * This class reduces the amount of code required in application class at sometimes
 */
public class Sea {
    private char[][] sea = new char[10][10];  // form {x,y}
    private final static Logger LOGGER = Logger.getLogger(Sea.class.getName());
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;
    private ArrayList<Ship> ships = new ArrayList<>();

    /**
     * Constructor to set up the sea with default size of 10
     */
    public Sea(){
        initSea();
        setupShips();
        printSea();
    }

    /**
     * Constructor to set up the sea with custom size
     * @param size of the sea
     */
    public Sea(int size){
        this.sea = new char[size][size];
        initSea();
        setupShips();
        printSea();
    }

    /**
     * Generates random numbers and checks if a ship can be put at that position.
     * If yes it puts the ship using putShip
     */
    private void setupShips(){
        int[] ship_sizes = {5,4,3,2,2};
        char[] ship_shapes = {'A','B','C','D','D'};
        Random random = new Random();
        int i = 0;
        try {
            fileTxt= new FileHandler("logs.txt");
            formatterTxt = new SimpleFormatter();
            fileTxt.setFormatter(formatterTxt);
            LOGGER.addHandler(fileTxt);
            LOGGER.setLevel(Level.SEVERE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(i<Vehicles.values().length){
            int[] pos = getPos();
            int ship_size = Vehicles.values()[i].getSize();
            int ship_dir  = random.nextInt(2); // 0-Horizontal,  1-Vertical
            char ship_shape = Vehicles.values()[i].getShape();


            if(canPutShip(ship_dir, pos, ship_size)){
                i++;
                putShip(ship_dir, pos, ship_size, ship_shape);
                LOGGER.info("Ship Put");
                LOGGER.info("Position: " + pos[0]+","+pos[1]);
                LOGGER.info("Ship Direction:"  + ship_dir);
                LOGGER.info("Ship size: " +ship_size);
            }

        }
    }

    /**
     * Checks if a ship can be put at pos.
     * It ensures that no other ships are in the way and also that the ship will fit the map from given position
     * @param ship_dir Direction of ship
     * @param pos Position of ship
     * @param ship_size Size of Ship
     * @return whether a ship can be put at that position
     */
    private boolean canPutShip(int ship_dir,  int[] pos, int ship_size){
        if(ship_dir == 0){
            if(pos[1]+ship_size > sea[0].length){
                return false;
            }
            for(int j = pos[1]; j<pos[1] + ship_size;j++){
                if (sea[pos[0]][j] != '-'){
                    LOGGER.info("Blocked");
                    return false;
                }
            }
            return true;
        }
        else{
            if(pos[0]+ship_size > sea.length){
                return false;
            }
            for(int i = pos[0]; i<pos[0]+ship_size;i++){
                if(sea[i][pos[1]] != '-'){
                    LOGGER.info("Blocked");
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * This class puts the ships in the sea.
     * It places sips on the sea char[][] without checking if there is already any ship in that place since this method
     * is only called once it has been confirmed that that position is free.
     * @param ship_dir Direction of ship
     * @param pos Position of ship
     * @param ship_size size of Ship
     * @param ship_shape Shape of Ship
     */
    private void putShip(int ship_dir,  int[] pos, int ship_size, char ship_shape){
        Ship ship = new Ship(ship_size, pos, ship_dir, this);
        this.ships.add(ship);
        if(ship_dir == 0){
            for(int i = pos[1];  i<ship_size+pos[1]; i++){
                int[] pos_occ = {pos[0],i};
                sea[pos[0]][i] = ship_shape;
                ship.addOccPos(new Place(pos_occ[0], pos_occ[1]));
            }
        }
        else if(ship_dir==1){
            for(int i = pos[0]; i<ship_size+pos[0];i++){
                int[] pos_occ = {i,pos[1]};
                sea[i][pos[1]] = ship_shape;
                ship.addOccPos(new Place(pos_occ[0], pos_occ[1]));
            }
        }
    }

    /**
    * provides a position to place the ship to canPutShip
    * helps to remove some repeated code and makes code more readable
    * @return
     */
    private int[] getPos(){
        Random random = new Random();
        int[] pos = new int[2];
        pos[0] = random.nextInt(this.sea.length);
        pos[1] = random.nextInt(this.sea.length);
        return pos;
    }

    /**
     * print sea to terminal correctly
     */
    private void printSea(){
        for(int i = 0;i<sea.length;i++){
            for(int j = 0;j<sea[0].length;j++){
                System.out.print(sea[i][j]+ " ");
            }
            System.out.println();
        }
	System.out.println();
    }
    
    /**
    * Checks if all ships have been sunk
    * @return whether all ships have been sunk
     */
    public boolean didAllShipsSink() {
    	int ships = 0;
    	for (char[] cs : sea) {
		for (char c : cs) {
			if(c!='-') {
				ships++;
			}
		}
	}
        return ships == 0;
    }

    /**
     * Adds dashes to every point on the sea so further we can check which points are empty
     */
    private void initSea(){
        for(int i =0 ;i<sea.length;i++){
            for(int j =0 ;j<sea[0].length;j++){
                sea[i][j] = '-';
            }
        }
    }

    /**
     * Gets the object at given position
     * @param x 
     * @param y
     * @return object at given position
     */
    public char getObj(int x, int y){ // Returns object in a position
        return sea[y][x];
    }

    /**
     * Sets the object at the given position.
     * Used mainly by the Canon class to set which parts of the ship have been broken
     * @param x
     * @param y
     * @param obj 
     */
    public void setObj(int x, int y, char obj){ // Sets object in a position
        sea[y][x] = obj;
        printSea();
    }

    /**
     * Provides the canon to the user.
     * It helps in automatically binding the provided canon to this class and removes reponsibility from the App2 class
     * @return Canon bound to this sea object
     */
    public CanonInterface getCanon(){
        return new Canon(this);
    }

    /**
     * Returns all ships placed in this sea
     * @return
     */
    public ArrayList<Ship> getShips(){
        return ships;
    }

    /**
     * Checks if a particular ship has been sunk
     * @param i The number of the ship to check.
     * @return True if the ship has sunk. False otherwise
     */
    // TODO:Fix isItDestroyed
    public boolean sunk(int i){
        return ships.get(i).isItDestroyed();
    }
}
