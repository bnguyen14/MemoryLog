/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memorylog;

import java.io.*;
import java.util.*;
import java.lang.Math;
/**
 *
 * @author Blake-LT
 */
public class test {
    public static void main(String[] args) throws FileNotFoundException{
        Random rand = new Random();
        int w1 = 0;
        int w2 = 0;
        int w3 = 0;
        int all4 = 0;
        int all3 = 0;
        int all2 = 0;
        int all1 = 0;
        int two1 = 0;
        int one1 = 0;
        int coins = 10;
        int spins = 0;
        while(spins<10000000){
            spins++;
            coins--;
            w1 = rand.nextInt(4)+1;
            w2 = rand.nextInt(4)+1;
            w3 = rand.nextInt(4)+1;
            if(w1==4&&w2==4&&w3==4){
                coins+=20;
                all4++;
            }
            if(w1==3&&w2==3&&w3==3){
                coins+=15;
                all3++;
            }
            if(w1==2&&w2==2&&w3==2){
                coins+=5;
                all2++;
            }
            if(w1==1&&w2==1&&w3==1){
                coins+=3;
                all1++;
            }else if((w1==1&&w2==1)||(w1==1&&w3==1)||(w2==1&&w3==1)){
                coins+=2;
                two1++;
            }else if(w1==1||w2==1||w3==1){
                coins+=1;
                one1++;
            }
        }
        System.out.println("spins: " + spins);
        System.out.println("all bar: " + (double)all4/spins);
        System.out.println("all bell: " + (double)all3/spins);
        System.out.println("all lemon: " + (double)all2/spins);
        System.out.println("all cherry: " + (double)all1/spins);
        System.out.println("two cherry: " + (double)two1/spins);
        System.out.println("one cherry: " + (double)one1/spins);
    }
}
