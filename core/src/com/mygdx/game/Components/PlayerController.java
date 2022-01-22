package com.mygdx.game.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Entitys.Player;
import com.mygdx.game.Entitys.Ship;
import com.mygdx.game.Managers.RenderingManager;

import static com.mygdx.utils.Constants.PHYSICS_TIME_STEP;

/**
 * Responsible for the keyboard control of the player
 */
public class PlayerController extends Component {
    private Player player;
    private float speed;

    public PlayerController() {
        super();
        type = ComponentType.PlayerController;
        setRequirements(ComponentType.RigidBody);
    }

    public PlayerController(Player player, float speed) {
        this();
        this.player = player;
        this.speed = speed;
    }

    @Override
    public void update() {
        super.update();
        float s = speed * PHYSICS_TIME_STEP;

        Vector2 dir = new Vector2(0, 0);

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            dir.y += 1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            dir.y -= 1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            dir.x -= 1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            dir.x += 1;
        }

        ((Ship) parent).setShipDirection(dir);

        dir.scl(s);

        RigidBody rb = parent.getComponent(RigidBody.class);
        rb.setVelocity(dir);

        RenderingManager.getCamera().position.set(new Vector3(player.getPosition(), 0.0f));
        RenderingManager.getCamera().update();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            ((Ship) parent).shoot();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            ((Ship) parent).setFaction(1);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            ((Ship) parent).setFaction(2);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            ((Ship) parent).setFaction(3);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            ((Ship) parent).setFaction(4);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            ((Ship) parent).setFaction(5);
        }
    }
}