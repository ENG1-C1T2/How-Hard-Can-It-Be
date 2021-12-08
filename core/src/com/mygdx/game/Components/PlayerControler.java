package com.mygdx.game.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.EntityManager;
import com.mygdx.game.Entitys.Player;

public class PlayerControler extends Component {
    private Player player;
    private float speed;

    public PlayerControler() {
        super();
    }

    public PlayerControler(Player player, float speed) {
        this.player = player;
        this.speed = speed;
    }

    @Override
    public void update() {
        super.update();
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            Vector2 pos = player.getPos();
            pos.y += speed;
            player.setPos(pos);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            Vector2 pos = player.getPos();
            pos.y -= speed;
            player.setPos(pos);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            Vector2 pos = player.getPos();
            pos.x -= speed;
            player.setPos(pos);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            Vector2 pos = player.getPos();
            pos.x += speed;
            player.setPos(pos);
        }
        EntityManager.getCamera().position.set(new Vector3(player.getPos(), 0.0f));
    }
}