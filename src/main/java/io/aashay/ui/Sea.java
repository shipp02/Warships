package io.aashay.ui;

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
    public Sea(){
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
        if(ship_dir == 0){
            for(int i = pos[1];  i<ship_size+pos[1]; i++){
                sea[pos[0]][i] = ship_shape;
            }
        }
        else if(ship_dir==1){
            for(int i = pos[0]; i<ship_size+pos[0];i++){
                sea[i][pos[1]] = ship_shape;
            }
        }
    }

    private static int[] getPos(){
        Random random = new Random();
        int[] pos = new int[2];
        pos[0] = random.nextInt(10);
        pos[1] = random.nextInt(10);
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

    public void setObj(int x, int y, char obj){ // Returns object in a position
        sea[y][x] = obj;
        printSea();
    }

    public Canon getCanon(){
        return new Canon(this);
    }
}