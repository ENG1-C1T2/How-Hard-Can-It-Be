package com.mygdx.tests.utils;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.tests.GdxTestRunner;
import com.mygdx.utils.Utilities;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)

public class UtilitiesTests{

    //to generate random floats for the Vector2 objects
    private float randomFloat(int min, int max){
        Random r = new Random();
        return min + r.nextFloat() * (max - min);
    }

    @Test
    public void testVectorToAngle(){
        float x = randomFloat(1, 1000);
        float y = randomFloat(1, 1000);

        Vector2 testVector = new Vector2(x, y);
        float testAngle = Utilities.vectorToAngle(testVector);

        float expectedAngle = (float) Math.atan2(-x, y);
        float shouldFail1 = (float) Math.atan2(x, y);
        float shouldFail2 = (float) Math.atan2(-x, -y);

        assertAll("The angle is not as expected",
                () -> assertEquals(testAngle, expectedAngle),
                () -> assertNotEquals(testAngle, shouldFail1),
                () -> assertNotEquals(testAngle, shouldFail2)
        );
    }

    @Test
    public void testAngleToVector(){
        ;
    }
}
