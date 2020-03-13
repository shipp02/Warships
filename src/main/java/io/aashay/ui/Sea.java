package io.aashay.ui;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;




public class Sea {
    private char[][] sea = new char[10][10];  // form {x,y}
    private final static Logger LOGGER = Logger.getLogger(Sea.class.getName());
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;
    private ArrayList<Ship> ships = new ArrayList<>();

    public Sea(){
        initSea();
        setupShips();
        printSea();
    }

    public Sea(int size){
        this.sea = new char[size][size];
        initSea();
        setupShips();
        printSea();
    }

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
        ship.setSea(this);
    }

    private int[] getPos(){
        Random random = new Random();
        int[] pos = new int[2];
        pos[0] = random.nextInt(this.sea.length);
        pos[1] = random.nextInt(this.sea.length);
        return pos;
    }

    private void printSea(){
        for(int i = 0;i<sea.length;i++){
            for(int j = 0;j<sea[0].length;j++){
                System.out.print(sea[i][j]+ " ");
            }
            System.out.println();
        }
    }

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
    
    public int isItDestroyed(){
        for (Ship ship : ships) {
            if(ship.isItDestroyed()){
                return ships.indexOf(ship);
            }
        }
        return -1;
    }

    public boolean didISinkAllTheShips(){
        int A = 0;
        int B = 0;
        int C = 0;
        int D = 0;
        int blank = 0;
        for (int i =0;i<10;i++) {
            for(int j =0;j<10;j++){
                char obj  = this.getObj(i, j);
                switch(obj){
                    case 'A':
                        A++;
                        break;
                    case 'B':
                        B++;
                        break;
                    case 'C':
                        C++;
                        break;
                    case 'D':
                        D++;
                        break;
                    default:
                        blank++;
                        break;
                    
                }
            }
        }

        if(A==0 && B==0 && C==0 && D==0){
            return true;
        }else{
            return false;
        }
    }
}