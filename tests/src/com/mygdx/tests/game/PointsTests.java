package com.mygdx.tests.game;

import com.mygdx.game.Managers.PointsManager;
import com.mygdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)
public class PointsTests {
    @Test
    public void testSetAndGetPoints() {
        PointsManager.set(32);
        assertEquals(32, PointsManager.get());
    }

    @Test
    public void testChangePoints() {
        int initial = PointsManager.get();

        PointsManager.change(20);
        assertEquals(initial + 20, PointsManager.get());

        PointsManager.change(-3);
        assertEquals(initial + 17, PointsManager.get());
    }
}
