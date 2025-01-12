package com.mygdx.game.Quests;

import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Entitys.Entity;
import com.mygdx.game.Entitys.Player;

/**
 * A Quest to destroy a college is only complete once that college is destroyed
 */
public class KillQuest extends Quest {
    private Pirate target;

    public KillQuest() {
        super();
        name = "Destroy the College";
        description = "KILL KILL KILL";
        reward = 100;
        target = null;
    }

    public KillQuest(Pirate target) {
        this();
        this.target = target;
        description = target.getFaction().getName();
    }

    public KillQuest(Entity target) {
        this(target.getComponent(Pirate.class));
    }

    @Override
    public boolean checkCompleted(Player p) {
        isCompleted = !target.isAlive();
        return isCompleted;
    }
}
