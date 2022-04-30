package com.mygdx.game.Physics;

public enum PhysicsBodyType {
    /**
     * Has infinite mass and doesn't move at all.
     */
    Static,
    /**
     * Effected by all forces (like everything in real life).
     */
    Dynamic,
    /**
     * Not effected by forces but can move.
     */
    Kinematic
}