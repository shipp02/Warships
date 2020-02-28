package io.aashay.ui;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class SeaTest {
    

    

    @Test
    public void shipPlacement(){
        Sea sea = new Sea();
        int A = 0;
        int B = 0;
        int C = 0;
        int D = 0;
        int blank = 0;
        for (int i =0;i<10;i++) {
            for(int j =0;j<10;j++){
                char obj  = sea.getObj(i, j);
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

        assertTrue(assertions(A, B, C, D));
    }

    public boolean assertions(int A, int B, int C, int D){
        if(A==5 && B==4 && C==3 && D==4){
            return true;
        }else{
            return false;
        }
    }
}