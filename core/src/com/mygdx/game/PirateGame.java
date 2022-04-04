package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.UI.DifficultyScreen;
import com.mygdx.game.UI.EndScreen;
import com.mygdx.game.UI.GameScreen;
import com.mygdx.game.UI.MenuScreen;
import com.mygdx.game.UI.PauseScreen;

/**
 * Contains class instances of game UI screens.
 */
public class PirateGame extends Game {
    public MenuScreen menu;
    public GameScreen game;
    public EndScreen end;
    public PauseScreen pause;
    public DifficultyScreen difficulty;
    public Stage stage;
    public Skin skin;

    /**
     * Create instances of game stage and UI screens.
     */
    @Override
    public void create() {
        // load resources
        int id_ship = ResourceManager.addTexture("ship.png");
        int id_map = ResourceManager.addTileMap("Map.tmx");
        int atlas_id = ResourceManager.addTextureAtlas("Boats.txt");
        int extras_id = ResourceManager.addTextureAtlas("UISkin/skin.atlas");
        int buildings_id = ResourceManager.addTextureAtlas("Buildings.txt");
        ResourceManager.addTexture("menuBG.jpg");
        ResourceManager.addTexture("Chest.png");
        ResourceManager.loadAssets();
        // can't load any more resources after this point (just functionally I chose not to implement)
        stage = new Stage(new ScreenViewport());
        createSkin();
        menu = new MenuScreen(this);
        game = new GameScreen(this, id_map);
        end = new EndScreen(this);
        pause = new PauseScreen(this);
        difficulty = new DifficultyScreen(this);
        setScreen(menu);
    }

    /**
     * Clean up prevent memory leeks
     */
    @Override
    public void dispose() {
        menu.dispose();
        game.dispose();
        stage.dispose();
        skin.dispose();
    }

    /**
     * Load ui skin from assets
     */
    private void createSkin() {
        skin = new Skin(Gdx.files.internal("UISkin/skin.json"));
    }
}
