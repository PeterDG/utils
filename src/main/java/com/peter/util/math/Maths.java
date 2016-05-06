package com.peter.util.math;

import java.util.Random;

/**
 * Created by Peter on 5/6/2016.
 */
public class Maths {
    public static Integer rnd(Integer min,Integer max){
        Random rand = new Random();
        int randomNum = min + (int)(Math.random() * ((max - min) + 1));
        return randomNum;
    }
}
