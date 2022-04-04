package com.mygdx.tests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void testShipAssetExists(){
        assertTrue("This test will only pass if ship.png exists",
                Gdx.files.internal("ship.png").exists());
    }

    @Test
    public void testUISkinAssetExists(){
        assertTrue("This test will only pass if the UISkin asset exists",
                Gdx.files.internal("UISkin").exists());
    }

    @Test
    public void testBeachTilesetExists(){
        assertTrue("This test will only pass if the Beach Tileset asset exists",
                Gdx.files.internal("Beach Tileset.png").exists());
    }

}
