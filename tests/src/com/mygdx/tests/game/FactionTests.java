package com.mygdx.tests.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Faction;
import com.mygdx.tests.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)

public class FactionTests {
    Faction faction;

    private float randomFloat(int min, int max){
        Random r = new Random();
        return min + r.nextFloat() * (max - min);
    }


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
        float x1 = randomFloat(1, 100);
        float y1 = randomFloat(1, 100);

        float x2 = randomFloat(1, 100);
        float y2 = randomFloat(1, 100);

        Vector2 pos = new Vector2(x1, y1);
        Vector2 spawn = new Vector2(x2, y2);

        faction = new Faction("Arsenal", "Red", pos, spawn, 4);

        assertAll(
                () -> assertEquals("Arsenal", faction.getName()),
                () -> assertEquals("Red", faction.getColour()),
                () -> assertEquals(pos, faction.getPosition()),
                () -> assertEquals(spawn, faction.getSpawnPos())
        );

    }

}
