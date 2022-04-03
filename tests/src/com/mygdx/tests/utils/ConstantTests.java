package com.mygdx.tests.utils;

import com.mygdx.tests.GdxTestRunner;
import com.mygdx.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.mygdx.utils.Constants.INIT_CONSTANTS;
import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)

public class ConstantTests{

    @Before
    public void initialise(){
        INIT_CONSTANTS();
    }

    @Test
    public void testScreenHeight(){
        assertEquals(1080, Constants.SCREEN_HEIGHT);
    }
}
