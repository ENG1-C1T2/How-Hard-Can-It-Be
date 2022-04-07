package com.mygdx.tests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;


@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void testUISkinFolderExists(){
        assertAll("This test will pass if all the necessary files are found in the UISkin folder.",
                () -> assertTrue("This test will pass if default.fnt exists",
                        Gdx.files.internal("UISkin/default.fnt").exists()),

                () -> assertTrue("This test will pass if skin.atlas exists",
                        Gdx.files.internal("UISkin/skin.atlas").exists()),

                () -> assertTrue("This test will pass if skin.json exists",
                        Gdx.files.internal("UISkin/skin.json").exists()),

                () -> assertTrue("This test will pass if uiskin.png exists",
                        Gdx.files.internal("UISkin/skin.png").exists())
        );
    }


    @Test
    public void testBeachTilesetPNGExists(){
        assertTrue("This test will only pass if the Beach Tileset asset exists",
                Gdx.files.internal("Beach Tileset.png").exists());
    }


    @Test
    public void testArialTTFExists(){
        assertTrue("This test will pass if the arial.ttf file exists",
                Gdx.files.internal("arial.ttf").exists());
    }


    @Test
    public void testBeachTilesetTSXExists(){
        assertTrue("This test will pass if the Beach Tileset.tsx file exists",
                Gdx.files.internal("Beach Tileset.tsx").exists());
    }


    @Test
    public void testBoatsPNGExists(){
        assertTrue("This test will pass if the boats.png file exists",
                Gdx.files.internal("boats.png").exists());
    }


    @Test
    public void testBoatsTXTExists(){
        assertTrue("This test will pass if the boats.txt file exists",
                Gdx.files.internal("Boats.txt").exists());
    }


    @Test
    public void testBuildingsTXTExists(){
        assertTrue("This test will pass if the Buildings.txt file exists",
                Gdx.files.internal("Buildings.txt").exists());
    }


    @Test
    public void testChestPNGExists(){
        assertTrue("This test will pass if the chests.png file exists",
                Gdx.files.internal("Chest.png").exists());
    }


    @Test
    public void testGameSettingsJSONExists(){
        assertTrue("This test will pass if the GameSettings.json file exists",
                Gdx.files.internal("GameSettings.json").exists());
    }


    @Test
    public void testMapTMXExists(){
        assertTrue("This test will pass if the Map.tmx file exists",
                Gdx.files.internal("Map.tmx").exists());
    }


    @Test
    public void testmenuGBJPGExists(){
        assertTrue("This test will pass if the menuBG.jpg file exists",
                Gdx.files.internal("menuBG.jpg").exists());
    }


    @Test
    public void testOtherPNGExists(){
        assertTrue("This test will pass if the other.png file exists",
                Gdx.files.internal("other.png").exists());
    }


    @Test
    public void testShipPNGExists(){
        assertTrue("This test will pass if the ship.png file exists",
                Gdx.files.internal("ship.png").exists());
    }
}
