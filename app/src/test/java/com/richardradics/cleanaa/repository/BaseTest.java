package com.richardradics.cleanaa.repository;

import com.richardradics.cleanaa.util.AppRobolectricRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 *
 * Created by radicsrichard on 15. 05. 15..
 */
@RunWith(AppRobolectricRunner.class)
public class BaseTest {

    @Test
    public void testTestMethod() {
        Boolean test = true;
        assertTrue(test);
    }

}
