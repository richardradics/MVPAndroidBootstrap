package com.richardradics.commons.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by richardradics on 2015.02.17..
 */
public class RandomUtil {


    /***
     * Random numbers generator.
     *
     * @param startNumber the range start number
     * @param endNumber the range end number
     * @param numberCount the count of the requested randomnumbers.
     * @return an array with random numbers
     */
    public static int[] getRandomNumbers(int startNumber, int endNumber, int numberCount){

        int[] randomNumbers = new int[numberCount];

        List<Integer> list = new ArrayList<Integer>();
        for(int i = startNumber; i <= endNumber; i++) list.add(i);
        Collections.shuffle(list);


        for(int i = 0; i< numberCount; i++){
            randomNumbers[i]  = list.get(i);
        }


        return randomNumbers;
    }

}
