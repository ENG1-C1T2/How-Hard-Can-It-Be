package com.mygdx.game.Entitys;

import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Components.PlayerController;
import com.mygdx.game.Managers.GameManager;

/**
 * Player's ship entity.
 */
public class Player extends Ship {

    /**
     * Adds ship with PlayerController component and sets its speed.
     *
     * @param speed of movement across map
     */
    private Player(float speed) {
        super();

        PlayerController pc = new PlayerController(this, speed);
        addComponent(pc);

        setName("Player");
    }

    public int getHealth () {
        return super.getHealth();
    }

    /**
     * Adds ship with PlayerController component, loading its speed from GameManager settings.
     */
    public Player() {
        this(GameManager.getSettings().get("starting").getFloat("playerSpeed"));
    }

    public void setSpeed(float newSpeed) {
        getComponent(PlayerController.class).setSpeed(newSpeed);
    }

    public void addPlunder(int money) {
        Pirate.plunder += money;
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
    }

    public int getAmmo() {
        return getComponent(Pirate.class).getAmmo();
    }
}
