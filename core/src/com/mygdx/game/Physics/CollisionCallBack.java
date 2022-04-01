package com.mygdx.game.Physics;

/**
 * Allows for the callbacks during collision events
 */
public interface CollisionCallBack {
    /**
     * Called once a collision has been noticed
     */
    void BeginContact(CollisionInfo info);

    /**
     * Called after the collision has been solved
     */
    void EndContact(CollisionInfo info);

    /**
     * Called on the object that enters the trigger
     */
    void EnterTrigger(CollisionInfo info);

    /**
     * Called upon exiting a trigger
     */
    void ExitTrigger(CollisionInfo info);
}
