package com.richardradics.commons.util;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Richard Radics on 2014.12.11..
 */
public class ColorUtil {

    private static Random randomGenerator = new Random();


    /**
     * Gets a random darker color.
     * @return
     */
    public static int getRandomDarkColor(){
        int randColor = getRandomColor();
        return darker(randColor, 0.75f);
    }


    /**
     * Retuns a darker color from a specified color by the factor.
     * @param color
     * @param factor
     * @return
     */
    public static int darker (int color, float factor) {
        int a = Color.alpha( color );
        int r = Color.red( color );
        int g = Color.green( color );
        int b = Color.blue( color );

        return Color.argb( a,
                Math.max( (int)(r * factor), 0 ),
                Math.max( (int)(g * factor), 0 ),
                Math.max( (int)(b * factor), 0 ) );
    }


    /**
     * Returns a random color.
     * @return
     */
    public static int getRandomColor(){
        return Color.rgb(randomGenerator.nextInt(255), randomGenerator.nextInt(255), randomGenerator.nextInt(255));
    }

}
