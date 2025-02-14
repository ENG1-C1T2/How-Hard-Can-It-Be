package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.PirateGame;

import static com.mygdx.utils.Constants.TILE_SIZE;
import static com.mygdx.utils.Constants.VIEWPORT_HEIGHT;

/**
 * Added for assessment 2.
 * Provides a menu, accessed by pressing Z, where the player may spend plunder on powerups
 */
public class PauseScreen extends Page {
    private Label plunderLabel;

    public PauseScreen(PirateGame parent) {
        super(parent);
    }

    /**
     * Create menu widgets such as buttons, labels, etc.
     */
    @Override
    protected void CreateActors() {
        int plunder = GameManager.getPlayer().getComponent(Pirate.class).getPlunder();

        Table t = new Table();
        t.setFillParent(true);

        Table t2 = new Table();
        t2.setFillParent(true);
        Pirate pirate = GameManager.getPlayer().getComponent(Pirate.class);

        t2.add(new Image(parent.skin.getDrawable("coin"))).top().size(1.25f * TILE_SIZE);
        plunderLabel = new Label(String.valueOf(plunder), parent.skin);
        t2.add(plunderLabel).top().size(50);

        float space = VIEWPORT_HEIGHT * 0.08f;


        t.setBackground(new TextureRegionDrawable(ResourceManager.getTexture("menuBG.jpg")));
        Label l = new Label("Spend plunder on upgrades:", parent.skin);
        l.setFontScale(2);
        t.add(l).top().spaceBottom(space * 0.4f);
        t.row();

        Label l3 = new Label("50 plunder:", parent.skin);
        l3.setFontScale(1);
        t.add(l3).top().spaceBottom(space * 0.1f);
        t.row();

        TextButton health = new TextButton("Health upgrade", parent.skin);
        health.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pirate.healthUpgrade();
                parent.setScreen(parent.game);
            }
        });
        t.add(health).top().size(150, 25).spaceBottom(space);
        t.row();

        Label l2 = new Label("50 plunder:", parent.skin);
        l2.setFontScale(1);
        t.add(l2).top().spaceBottom(space * 0.1f);
        t.row();

        TextButton ammo = new TextButton("Ammo upgrade", parent.skin);
        ammo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pirate.ammoUpgrade();
                parent.setScreen(parent.game);
            }
        });
        t.add(ammo).size(150, 25).top().spaceBottom(space);
        t.row();

        Label l4 = new Label("100 plunder:", parent.skin);
        l4.setFontScale(1);
        t.add(l4).top().spaceBottom(space * 0.1f);
        t.row();

        TextButton multiShoot = new TextButton("Shoot multiple cannonballs", parent.skin);
        multiShoot.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pirate.multiShootUpgrade();
                parent.setScreen(parent.game);
            }
        });
        t.add(multiShoot).size(220, 25).top().spaceBottom(space);
        t.row();

        Label l5 = new Label("200 plunder:", parent.skin);
        l5.setFontScale(1);
        t.add(l5).top().spaceBottom(space * 0.1f);
        t.row();

        TextButton speed = new TextButton("Increase speed", parent.skin);
        speed.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pirate.speedUpgrade();
                parent.setScreen(parent.game);
            }
        });
        t.add(speed).size(200, 25).top().spaceBottom(space);
        t.row();

        Label l6 = new Label("200 plunder:", parent.skin);
        l6.setFontScale(1);
        t.add(l6).top().spaceBottom(space * 0.1f);
        t.row();

        TextButton damageReduce = new TextButton("Reduce damage taken", parent.skin);
        damageReduce.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pirate.reduceDamage();
                parent.setScreen(parent.game);
            }
        });
        t.add(damageReduce).size(200, 25).top().spaceBottom(space);
        t.row();

        TextButton cancel = new TextButton("Cancel", parent.skin);
        cancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(parent.game);
            }
        });
        t.add(cancel).size(200, 25).top().spaceBottom(space);
        t.row();

        TextButton saveQuit = new TextButton("Save & quit", parent.skin);
        saveQuit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.save();
                Gdx.app.exit();
                System.exit(0);
            }
        });
        t.add(saveQuit).size(200, 25).top().spaceBottom(space);
        t.row();


        t.top();
        t2.left().top();

        actors.add(t);
        actors.add(t2);
    }
    
    

    @Override
    public void show() {
        super.show();
    }

    public void update() {
        int plunder = GameManager.getPlayer().getComponent(Pirate.class).getPlunder();
        plunderLabel.setText(String.valueOf(plunder));

    }


    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Table t = (Table) actors.get(0);
        t.setBackground(new TextureRegionDrawable(ResourceManager.getTexture("menuBG.jpg"))); // prevent the bg being stretched
    }
}


