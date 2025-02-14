package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Components.ComponentEvent;
import com.mygdx.game.Entitys.Player;
import com.mygdx.game.Managers.*;
import com.mygdx.game.PirateGame;
import com.mygdx.game.Quests.Quest;

import static com.mygdx.utils.Constants.*;

public class GameScreen extends Page {
    private Label healthLabel;
    private Label dosh;
    private Label ammo;
    private Label points;
    private final Label questDesc;
    private final Label questName;

    /**
     * Boots up the actual game: starts PhysicsManager, GameManager, EntityManager,
     * loads texture atlases into ResourceManager. Draws quest and control info.
     *
     * @param parent PirateGame UI screen container
     * @param id_map the resource id of the tile map
     */
    public GameScreen(PirateGame parent, int id_map) {
        super(parent);
        INIT_CONSTANTS();
        PhysicsManager.Initialize(false);

        GameManager.SpawnGame(id_map);

        EntityManager.raiseEvents(ComponentEvent.Awake, ComponentEvent.Start);

        Window questWindow = new Window("Current Quest", parent.skin);

        Quest q = QuestManager.currentQuest();
        Table t = new Table();
        questName = new Label("NAME", parent.skin);
        t.add(questName);
        t.row();
        questDesc = new Label("DESCRIPTION", parent.skin);
        if (q != null) {
            questName.setText(q.getName());
            questDesc.setText(q.getDescription());
        }

        t.add(questDesc).left();
        questWindow.add(t);
        actors.add(questWindow);

        Table t1 = new Table();
        t1.top().right();
        t1.setFillParent(true);
        actors.add(t1);

        Window tutWindow = new Window("Controls", parent.skin);
        Table table = new Table();
        tutWindow.add(table);
        t1.add(tutWindow);

        table.add(new Label("Movement:", parent.skin)).top().left();
        table.add(new Image(parent.skin, "key-w"));
        table.add(new Image(parent.skin, "key-a"));
        table.add(new Image(parent.skin, "key-s"));
        table.add(new Image(parent.skin, "key-d"));
        table.row();
        table.add(new Label("Shoot With Cursor:", parent.skin)).left();
        table.add(new Image(parent.skin, "mouse"));
        table.row();
        table.add(new Label("Shoot With Ship Direction:", parent.skin)).left();
        table.add(new Image(parent.skin, "space"));
        table.row();
        table.add(new Label("Pause/Powerup Menu (Z):", parent.skin)).left();
        table.add(new Image(parent.skin, "key-z"));
        table.row();
        table.add(new Label("Quit:", parent.skin)).left();
        table.add(new Image(parent.skin, "key-esc"));

    }

    private float accumulator;

    /**
     * Called every frame.
     * Calls all other functions that need to be called every frame by raising events and update methods.
     *
     * @param delta delta time
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(BACKGROUND_COLOUR.x, BACKGROUND_COLOUR.y, BACKGROUND_COLOUR.z, 1);

        EntityManager.raiseEvents(ComponentEvent.Update, ComponentEvent.Render);

        accumulator += EntityManager.getDeltaTime();

        // fixed update loop so that physics manager is called regally rather than somewhat randomly
        while (accumulator >= PHYSICS_TIME_STEP) {
            PhysicsManager.update();
            accumulator -= PHYSICS_TIME_STEP;
        }

        GameManager.update();
        // show end screen if esc is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            parent.setScreen(parent.end);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            parent.pause.update();
            parent.setScreen(parent.pause);
        }
        super.render(delta);
    }

    /**
     * Disposes of all stuff. If something is missing from this method you will get memory leaks.
     */
    @Override
    public void dispose() {
        super.dispose();
        ResourceManager.cleanUp();
        EntityManager.cleanUp();
        RenderingManager.cleanUp();
        PhysicsManager.cleanUp();
    }

    /**
     * Resize camera, effectively setting the viewport to display game assets
     * at pixel ratios other than 1:1.
     *
     * @param width  of camera viewport
     * @param height of camera viewport
     */
    @Override
    public void resize(int width, int height) {
        //((Table) actors.get(0)).setFillParent(false);
        super.resize(width, height);
        OrthographicCamera cam = RenderingManager.getCamera();
        cam.viewportWidth = width / ZOOM;
        cam.viewportHeight = height / ZOOM;
        cam.update();

        // ((Table) actors.get(0)).setFillParent(true);
    }

    /**
     * Update the UI with new values for health, quest status, etc.
     * also called once per frame but used for actors by my own convention
     */
    //private String prevQuest = "";
    @Override
    protected void update() {
        super.update();
        Player p = GameManager.getPlayer();

        if (p.getHealth() <= 0) {
            parent.setScreen(parent.end);
        }

        healthLabel.setText(String.valueOf(p.getHealth()));
        dosh.setText(String.valueOf(p.getPlunder()));
        ammo.setText(String.valueOf(p.getAmmo()));
        points.setText(String.valueOf(PointsManager.get()));
        if (!QuestManager.anyQuests()) {
            parent.end.win();
            parent.setScreen(parent.end);

        } else {
            Quest q = QuestManager.currentQuest();
            questName.setText(q.getName());
            questDesc.setText(q.getDescription());
        }
    }

    /**
     * Draw UI elements showing player health, plunder, ammo, and points.
     */
    @Override
    protected void CreateActors() {
        Table table = new Table();
        table.setFillParent(true);
        actors.add(table);

        table.add(new Image(parent.skin.getDrawable("heart"))).top().left().size(1.25f * TILE_SIZE);
        healthLabel = new Label("N/A", parent.skin);
        table.add(healthLabel).top().left().size(50);

        table.row();
        table.setDebug(false);

        table.add(new Image(parent.skin.getDrawable("coin"))).top().left().size(1.25f * TILE_SIZE);
        dosh = new Label("N/A", parent.skin);
        table.add(dosh).top().left().size(50);

        table.row();

        table.add(new Image(parent.skin.getDrawable("ball"))).top().left().size(1.25f * TILE_SIZE);
        ammo = new Label("N/A", parent.skin);
        table.add(ammo).top().left().size(50);

        table.row();

        table.add(new Image(parent.skin.getDrawable("trophy"))).top().left().size(1.25f * TILE_SIZE);
        points = new Label("N/A", parent.skin);
        table.add(points).top().left().size(50);

        table.top().left();
    }
}
