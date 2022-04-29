package com.mygdx.game.Components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Entitys.Ship;
import com.mygdx.game.Faction;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.PointsManager;
import com.mygdx.utils.QueueFIFO;


/**
 * Gives the concepts of health plunder, etc. Allows for firing of cannonballs, factions, death, targets
 */
public class Pirate extends Component {
    private int factionId;
    private int plunder;
    protected boolean isAlive;
    private int health;
    private int ammo;
    private int attackDmg;
    private boolean damageReduce;
    private boolean speedIncrease;
    private boolean multiShoot;

    private static final int POINTS_VALUE = 10;

    /**
     * The enemy that is being targeted by the AI.
     */
    private final QueueFIFO<Ship> targets;

    public Pirate() {
        super();
        targets = new QueueFIFO<>();
        type = ComponentType.Pirate;
        plunder = GameManager.getSettings().get("starting").getInt("plunder");
        factionId = 1;
        isAlive = true;
        JsonValue starting = GameManager.getSettings().get("starting");
        health = starting.getInt("health");
        attackDmg = starting.getInt("damage");
        ammo = starting.getInt("ammo");
        damageReduce = false;
        multiShoot = false;
    }

    //manage power ups:

    /**
     * Applies a powerup to increase the player's health by 20
     */
    public void healthUpgrade() {
        if (plunder >= 50) {
            plunder -= 50;
            health += 20;
        }
    }

    /**
     * Applies a powerup to increase the player's ammo by 5
     */
    public void ammoUpgrade() {
        if (plunder >= 50) {
            plunder -= 50;
            ammo += 5;
        }
    }

    /**
     * Applies a powerup to increase the damage done against enemy ships
     */
    public void multiShootUpgrade() {
        if (plunder >= 100) {
            plunder -= 100;
            multiShoot = true;
        }
    }

    /**
     * Applies a powerup to increase the player ship's speed
     */
    public void speedUpgrade() {
        if (plunder >= 200) {
            plunder -= 200;
            GameManager.getPlayer().setSpeed(50000.0F);
            speedIncrease = true;
        }
    }

    /**
     * Applies a powerup to reduce the damage taken by the player when fired at by enemy ships
     */
    public void reduceDamage() {
        if (plunder >= 200) {
            plunder -= 200;
            damageReduce = true;
        }
    }

    public boolean[] getActiveUpgrades(){ return new boolean[] {speedIncrease, damageReduce}; }

    public void updateSettings (int difficulty) {
        if (difficulty == 0) {
            health = 200;
            ammo = 100;
            attackDmg = 20;
        } else if (difficulty == 2) {
            health = 50;
            ammo = 25;
            attackDmg = 5;
        }

    }

    public void addTarget(Ship target) {
        targets.add(target);
    }

    public int getPlunder() {
        return plunder;
    }

    public void addPlunder(int money) {
        plunder += money;
    }




    public Faction getFaction() {
        return GameManager.getFaction(factionId);
    }

    public void setFactionId(int factionId) {
        this.factionId = factionId;
    }

    public void takeDamage(float dmg) {
            if (damageReduce) {
                health -= dmg/2;
            } else {
                health -= dmg;
            }
            if (health <= 0) {
                kill();
            }
        }

    /**
     * Will shoot a cannonball assigning 'this.parent' as the cannonball's parent (must be Ship atm)
     *
     * @param dir the direction to shoot in
     */
    public void shoot(Vector2 dir) {
        if (ammo == 0) {
            return;
        }
        ammo--;
        GameManager.shoot((Ship) parent, dir);

        if (multiShoot == true) {
            Vector2 vec = new Vector2(1, 0);
            Vector2 vec2 = new Vector2(0, 1);
            GameManager.shoot((Ship) parent, (dir.add(vec)));
            GameManager.shoot((Ship) parent, (dir.add(vec2)));
        }
    }

    /**
     * Adds ammo
     *
     * @param ammo amount to add
     */
    public void reload(int ammo) {
        this.ammo += ammo;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) { health = newHealth;}

    /**
     * if dst to target is less than attack range
     * target will be null if not in agro range
     */
    public boolean canAttack() {
        if (targets.peek() != null) {
            final Ship p = (Ship) parent;
            final Vector2 pos = p.getPosition();
            final float dst = pos.dst(targets.peek().getPosition());
            // within attack range
            return dst < Ship.getAttackRange();
        }
        return false;
    }

    public Vector2 targetPosition() {
        Ship p = (Ship) parent;
        Vector2 pos = p.getPosition();
        Vector2 targetPos = targets.peek().getPosition();
        return targetPos.sub(pos);
    }

    /**
     * if dst to target is >= attack range
     * target will be null if not in agro range
     */
    public boolean isAgro() {
        if (targets.peek() != null) {
            final Ship p = (Ship) parent;
            final Vector2 pos = p.getPosition();
            final float dst = pos.dst(targets.peek().getPosition());
            // out of attack range but in agro range
            return dst >= Ship.getAttackRange();
        }
        return false;
    }

    public Ship getTarget() {
        return targets.peek();
    }

    public void removeTarget() {
        targets.pop();
    }

    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Kill itself
     */
    public void kill() {
        health = 0;
        isAlive = false;
        onDeath();
    }

    /**
     * Called when/if the pirate dies.
     */
    protected void onDeath() {
        PointsManager.change(POINTS_VALUE);
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getAmmo() {
        return ammo;
    }

    public int targetCount() {
        return targets.size();
    }

    public QueueFIFO<Ship> getTargets() {
        return targets;
    }
}
