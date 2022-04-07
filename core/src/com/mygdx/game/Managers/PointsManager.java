package com.mygdx.game.Managers;

/**
 * Manages the assignment of points to the player.
 */
public class PointsManager {
    private static int points = 0;

    /**
     * Get the number of points the player has.
     */
    public static int get() {
        return points;
    }

    /**
     * Set the number of points the player has.
     */
    public static void set(int points) {
        PointsManager.points = points;
    }

    /**
     * Change the number of points the player has.
     */
    public static void change(int amount) {
        points += amount;
    }
}
