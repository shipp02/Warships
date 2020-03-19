package io.aashay.ui;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;



/**
 * This is the Sea on which the game is played.It is also gives a lot of abstraction to Application class
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
            LOGGER.setLevel(Level.INFO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(i<5){
            int pos[] = getPos();
            int ship_size = ship_sizes[i];
            int ship_dir  = random.nextInt(2); // 0-Horizontal,  1-Vertical
            char ship_shape = ship_shapes[i];


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
     * @param ship_dir
     * @param pos
     * @param ship_size
     * @return
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
     * @param ship_dir
     * @param pos
     * @param ship_size
     * @param ship_shape
     */
    private void putShip(int ship_dir,  int[] pos, int ship_size, char ship_shape){
        Ship ship = new Ship(ship_size, pos, ship_dir, this);
        this.ships.add(ship);
        if(ship_dir == 0){
            for(int i = pos[1];  i<ship_size+pos[1]; i++){
                int[] pos_occ = {pos[0],i};
                sea[pos[0]][i] = ship_shape;
                ship.setOccPos(pos_occ);
            }
        }
        else if(ship_dir==1){
            for(int i = pos[0]; i<ship_size+pos[0];i++){
                int[] pos_occ = {i,pos[1]};
                sea[i][pos[1]] = ship_shape;
                ship.setOccPos(pos_occ);
            }
        }
    }

    private int[] getPos(){
        Random random = new Random();
        int[] pos = new int[2];
        pos[0] = random.nextInt(this.sea.length);
        pos[1] = random.nextInt(this.sea.length);
        return pos;
    }

    /**
     * print sea to terminally correctly
     */
    private void printSea(){
        for(int i = 0;i<sea.length;i++){
            for(int j = 0;j<sea[0].length;j++){
                System.out.print(sea[i][j]+ " ");
            }
            System.out.println();
        }
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

    public char getObj(int x, int y){ // Returns object in a position
        return sea[y][x];
    }

    public void setObj(int x, int y, char obj){ // Sets object in a position
        sea[y][x] = obj;
        printSea();
    }

    public Canon getCanon(){
        return new Canon(this);
    }

    public ArrayList<Ship> getShips(){
        return ships;
    }

    public boolean sunk(int i){
        return ships.get(i).isItDestroyed();
    }
}