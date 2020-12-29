package peerTopeer;


import java.util.Random;

public class Test {
    public static void main(String[] args){

        for(int i = 1; i <= 300; i++) {
            ThreadTest t1 = new ThreadTest(String.valueOf(i), String.valueOf(i+1000), "localhost");
            t1.start();
        }
    }
}
