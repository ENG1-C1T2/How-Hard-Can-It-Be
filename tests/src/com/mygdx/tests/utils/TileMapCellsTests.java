package com.mygdx.tests.utils;

import com.mygdx.tests.GdxTestRunner;
import com.mygdx.utils.TileMapCells;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)

public class TileMapCellsTests {

    @Test
    public void testOBSTACLEid(){
        assertEquals(61, TileMapCells.OBSTACLE);
    }

    @Test
    public void testPASSABLEid(){
        assertEquals(97, TileMapCells.PASSABLE);
    }

    @Test
    public void testOBSTACLE_COSTid(){
        assertEquals(100000f, TileMapCells.OBSTACLE_COST, 0);
    }

}
