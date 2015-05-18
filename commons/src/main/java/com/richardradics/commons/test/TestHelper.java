package com.richardradics.commons.test;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by radicsrichard on 15. 05. 18..
 */
public class TestHelper {

    public static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

    public static String getStringFromResource(String resourceName){
        String result = "";
        ClassLoader classLoader = TestHelper.class.getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(resourceName), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void printCollection(List collection){
        Log.i("Data", String.valueOf(collection));
    }

}
