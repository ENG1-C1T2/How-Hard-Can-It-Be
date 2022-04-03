package com.mygdx.tests.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Faction;
import com.mygdx.tests.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)

public class FactionTests {
    Faction faction;


    @Test
    public void testEmptyFaction(){
        faction = new Faction();
        assertAll(
                () -> assertEquals("Faction not named", faction.getName()),
                () -> assertEquals("", faction.getColour())


        );
    }

    @Test
    public void testFaction(){
        Vector2 pos = new Vector2(1.0F, 1.0F);
        Vector2 spawn = new Vector2(34.6F, 98.4F);

        faction = new Faction("Arsenal", "Red", pos, spawn, 4);

        assertAll(
                () -> assertEquals("Arsenal", faction.getName()),
                () -> assertEquals("Red", faction.getColour()),
                () -> assertEquals(pos, faction.getPosition()),
                () -> assertEquals(spawn, faction.getSpawnPos())
        );

    }

}
