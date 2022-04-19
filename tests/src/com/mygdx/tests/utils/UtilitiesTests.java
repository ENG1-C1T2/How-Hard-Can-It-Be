package com.mygdx.tests.utils;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.tests.GdxTestRunner;
import com.mygdx.utils.Constants;
import com.mygdx.utils.Utilities;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static com.mygdx.utils.Constants.TILE_SIZE;
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
        float x = randomFloat(1, 1000);
        float y = randomFloat(1, 1000);

        float testAngle = randomFloat(1, 360);
        Vector2 out = new Vector2();
        Vector2 testVector = Utilities.angleToVector(out, testAngle);

        //set up the correct Vector
        Vector2 expectedVector = new Vector2();
        expectedVector.x = -(float) Math.sin(testAngle);
        expectedVector.y = (float) Math.cos(testAngle);

        //A vector that should be wrong because of the (-) sign on the angles
        Vector2 shouldFailOnSign = new Vector2();
        shouldFailOnSign.x = (float) Math.sin(testAngle);
        shouldFailOnSign.y = -(float) Math.cos(testAngle);

        //A vector that should be wrong because the wrong trig function was used
        Vector2 shouldFailOnTrigFunc = new Vector2();
        shouldFailOnTrigFunc.x = -(float) Math.cos(testAngle);
        shouldFailOnTrigFunc.y = (float) Math.sin(testAngle);

        assertAll("An error has occurred when converting the Angle to the Vector.",
                () -> assertEquals(testVector, expectedVector),
                () -> assertNotEquals(testVector, shouldFailOnSign),
                () -> assertNotEquals(testVector, shouldFailOnTrigFunc)
        );
    }

    @Test
    public void testTilesToDistanceFloat(){
        float testTiles = randomFloat(1, 1000);

        float testDistance = Utilities.tilesToDistance(testTiles);
        float expectedDistance = Constants.TILE_SIZE * testTiles;
        float shouldFail = Constants.TILE_SIZE / testTiles;

        assertAll("An error has occurred converting the Tiles to a Distance.",
                () -> assertEquals(testDistance, expectedDistance),
                () -> assertNotEquals(testDistance, shouldFail)
        );
    }

    @Test
    public void testTilesToDistanceVector(){
        float x = randomFloat(1, 1000);
        float y = randomFloat(1, 1000);

        Vector2 testVector = new Vector2(x, y);
        Vector2 testDistance = Utilities.tilesToDistance(testVector);

        Vector2 expectedDistance = testVector.cpy().scl(Constants.TILE_SIZE);
        Vector2 shouldFail = testVector.cpy().scl(testVector);

        assertAll("An error has occurred converting the Tiles to a Distance",
                () -> assertEquals(testDistance, expectedDistance),
                () -> assertNotEquals(testDistance, shouldFail)
        );
    }
}
