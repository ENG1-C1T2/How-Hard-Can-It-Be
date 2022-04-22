package com.mygdx.tests.utils;

import com.badlogic.gdx.math.MathUtils;
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

    @Test
    public void testDistanceToTilesFloat(){
        float testDistance = randomFloat(1, 1000);

        float testTiles = Utilities.distanceToTiles(testDistance);
        float expectedTiles = (int) (testDistance/TILE_SIZE);
        float shouldFail = (int) (testDistance*TILE_SIZE);

        assertAll("An error has occurred converting the Distance to Tiles",
                () -> assertEquals(testTiles, expectedTiles),
                () -> assertNotEquals(testTiles, shouldFail)
        );
    }

    @Test
    public void testDistanceToTilesVector(){
        float x = randomFloat(1, 1000);
        float y = randomFloat(1, 1000);

        Vector2 testDistance = new Vector2(x, y);
        Vector2 testTiles = Utilities.distanceToTiles(testDistance);
        Vector2 expectedTiles = testDistance.cpy().scl(1.0f / TILE_SIZE);
        Vector2 shouldFail = testDistance.cpy().scl(TILE_SIZE);

        assertAll("An Error has occurred converting the Distance to Tiles.",
                () -> assertEquals(testTiles, expectedTiles),
                () -> assertNotEquals(testTiles, shouldFail)
        );
    }

    @Test
    public void testCheckProximity(){
        float a = randomFloat(1, 1000);
        float b = randomFloat(1, 1000);
        float c = randomFloat(1, 1000);
        float d = randomFloat(1, 1000);

        Vector2 testVectorA = new Vector2(a, b);
        Vector2 testVectorB = new Vector2(c, d);
        float testRadius = randomFloat(1, 1000);

        boolean testCheckProximity = Utilities.checkProximity(testVectorA, testVectorB, testRadius);
        boolean expectedCheckProximity = (Math.abs(testVectorA.dst2(testVectorB)) < (testRadius*testRadius));
        boolean shouldFail = (Math.abs(testVectorA.dst2(testVectorB)) > (testRadius*testRadius));

        assertAll("An error has occurred Checking the Proximity of the Vectors",
                () -> assertEquals(testCheckProximity, expectedCheckProximity),
                () -> assertNotEquals(testCheckProximity, shouldFail)
        );
    }

    @Test
    public void testAngleBetween(){
        float a = randomFloat(1, 1000);
        float b = randomFloat(1, 1000);
        float c = randomFloat(1, 1000);
        float d = randomFloat(1, 1000);

        Vector2 testVectorV = new Vector2(a, b);
        Vector2 testVectorW = new Vector2(c, d);

        float testAngle = Utilities.angleBetween(testVectorV, testVectorW);
        float expectedAngle = MathUtils.atan2(testVectorW.y * testVectorV.x - testVectorW.x * testVectorV.y,
                testVectorW.x * testVectorV.x + testVectorW.y * testVectorV.y);

        //Changing * to / should make the Angle calculation fail.
        float shouldFail = MathUtils.atan2(testVectorW.y / testVectorV.x - testVectorW.x / testVectorV.y,
                testVectorW.x / testVectorV.x + testVectorW.y / testVectorV.y);

        assertAll("Something has gone wrong calculating the angle.",
                () -> assertEquals(testAngle, expectedAngle),
                () -> assertNotEquals(testAngle, shouldFail)
        );
    }

    @Test
    public void testScaleFloat(){
        float testX = randomFloat(1, 1000);
        float testMin0 = randomFloat(1, 1000);
        float testMax0 = randomFloat(1, 1000);
        float testMin1 = randomFloat(1, 1000);
        float testMax1 = randomFloat(1, 1000);

        float testScale = Utilities.scale(testX, testMin0, testMax0, testMin1, testMax1);
        float expectedScale = (testMax1 - testMin1) * ((testX - testMin0 * testX) / (testMax0 * testX - testMin0 * testX)) + testMin1;

        //Flipped the * and / signs so this calculation should Fail
        float shouldFail = (testMax1 - testMin1) / ((testX - testMin0 * testX) * (testMax0 * testX - testMin0 * testX)) + testMin1;

        assertAll("Something has gone wrong calculating the Scale",
                () -> assertEquals(testScale, expectedScale),
                () -> assertNotEquals(testScale, shouldFail)
        );
    }

    @Test
    public void testScaleVector(){
        float testX = randomFloat(1, 1000);
        float a = randomFloat(1, 1000);
        float b = randomFloat(1, 1000);
        float c = randomFloat(1, 1000);
        float d = randomFloat(1, 1000);

        Vector2 testVectorA = new Vector2(a, b);
        Vector2 testVectorB = new Vector2(c, d);

        float testScale = Utilities.scale(testX, testVectorA, testVectorB);
        float expectedScale = (testVectorB.y - testVectorB.x) * ((testX - testVectorA.x * testX) / (testVectorA.y * testX - testVectorA.x * testX)) + testVectorB.x;

        // * and / sings have been flipped so the calculation should Fail
        float shouldFail = (testVectorB.y - testVectorB.x) / ((testX - testVectorA.x * testX) * (testVectorA.y * testX - testVectorA.x * testX)) + testVectorB.x;

        assertAll("Something has gone wrong calculating the Scale",
                () -> assertEquals(testScale, expectedScale),
                () -> assertNotEquals(testScale, shouldFail)
        );
    }

    @Test
    public void testRound(){
        float x = randomFloat(1, 1000);
        float y = randomFloat(1, 1000);

        Vector2 testVector = new Vector2(x, y);
        Vector2 testRound = Utilities.round(testVector);
        Vector2 expectedRound = new Vector2(Math.round(testVector.x), Math.round(testVector.y));
        Vector2 shouldFail = new Vector2(Math.round(testVector.y), Math.round(testVector.x));

        assertAll("An error has occurred whilst rounding the Vector",
                () -> assertEquals(testRound, expectedRound),
                () -> assertNotEquals(testRound, shouldFail)
        );
    }

    @Test
    public void testRandomPos(){
        float testMin = randomFloat(1, 1000);
        float testMax = randomFloat(1, 1000);

        Vector2 testVector = Utilities.randomPos(testMin, testMax);

        Random random = new Random();

        //test vector and expected vector should not be the same as random nums are generated.
        Vector2 expectedVector = new Vector2(testMin + random.nextFloat() * (testMax - testMin), testMin + random.nextFloat() * (testMax - testMin));

        assertAll("An error has occurred generating a random Vector",
                () -> assertNotEquals(testVector, expectedVector)
        );
    }

    @Test
    public void testFloor(){
        float x = randomFloat(1, 1000);
        float y = randomFloat(1, 1000);

        Vector2 testVector = new Vector2(x, y);
        Vector2 testFloor = Utilities.floor(testVector);
        Vector2 expectedVector = new Vector2(MathUtils.floor(testVector.x), MathUtils.floor(testVector.y));
        Vector2 shouldFail = new Vector2(MathUtils.floor(testVector.y), MathUtils.floor(testVector.x));

        assertAll("An error has occurred flooring the Vector",
                () -> assertEquals(testFloor, expectedVector),
                () -> assertNotEquals(testFloor, shouldFail)
        );
    }



}
