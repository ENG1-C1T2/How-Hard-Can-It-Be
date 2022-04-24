package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.AI.TileMapGraph;
import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Entitys.*;
import com.mygdx.game.Faction;
import com.mygdx.game.Quests.Quest;
import com.mygdx.utils.QueueFIFO;
import com.mygdx.utils.Utilities;
import com.mygdx.game.Components.Pirate;

import java.util.ArrayList;

/**
 * Responsible for creating most entity's associated with the game.
 * Also, the cached chest and cannonballs.
 */
public final class GameManager {
    private static boolean initialized = false;
    private static ArrayList<Faction> factions;
    private static ArrayList<Ship> ships;
    private static ArrayList<College> colleges;
    private static ArrayList<String> powerUps;


    private static final int cacheSize = 20;
    private static ArrayList<CannonBall> ballCache;
    private static int currentElement;

    private static JsonValue settings;
    private static Preferences prefs;

    private static TileMapGraph mapGraph;


    /**
     * facilitates creation of the game
     */
    public static void Initialize() {


        initialized = true;
        currentElement = 0;

        prefs = Gdx.app.getPreferences("York-Pirates-2-save-data");
        settings = new JsonReader().
                parse(Gdx.files.internal("GameSettings.json"));


        factions = new ArrayList<>();
        ships = new ArrayList<>();
        ballCache = new ArrayList<>(cacheSize);
        colleges = new ArrayList<>();
        powerUps = new ArrayList<>();

        for (int i = 0; i < cacheSize; i++) {
            ballCache.add(new CannonBall());
        }

        for (JsonValue v : settings.get("factions")) {
            String name = v.getString("name");
            String col = v.getString("colour");
            Vector2 pos = new Vector2(v.get("position").getFloat("x"), v.get("position").getFloat("y"));
            pos = Utilities.tilesToDistance(pos);
            Vector2 spawn = new Vector2(v.get("shipSpawn").getFloat("x"), v.get("shipSpawn").getFloat("y"));
            spawn = Utilities.tilesToDistance(spawn);
            factions.add(new Faction(name, col, pos, spawn, factions.size() + 1));
        }
    }

    /**
     * called every frame, checks id the quests are completed
     */
    public static void update() {
        QuestManager.checkCompleted();
    }


    /**
     * Player is always in ships at index 0
     *
     * @return the ship
     */
    public static Player getPlayer() {
        return (Player) ships.get(0);
    }

    /**
     * Creates the game with player maps, NPCs, colleges
     *
     * @param mapId the resource id of the tilemap
     */
    public static void SpawnGame(int mapId) {
        CreateWorldMap(mapId);
        CreatePlayer();
        final int cnt = settings.get("factionDefaults").getInt("shipCount");
        for (int i = 0; i < factions.size(); i++) {
            CreateCollege(i + 1);
            for (int j = 0; j < cnt; j++) {
                // prevents halifax from having shipcount + player
                if (i == 0 && j > cnt - 2) {
                    break;
                }
                NPCShip s = CreateNPCShip(i + 1);
                s.getComponent(Transform.class).setPosition(getFaction(i + 1).getSpawnPos());
            }
        }
    }

    /**
     * Creates player that belongs the faction with id 1
     */
    public static void CreatePlayer() {
        tryInit();
        Player p = new Player();
        p.setFaction(1);
        ships.add(p);
    }

    /**
     * Creates an NPC ship with the given faction
     *
     * @param factionId desired faction
     * @return the created ship
     */
    public static NPCShip CreateNPCShip(int factionId) {
        tryInit();
        NPCShip e = new NPCShip();
        e.setFaction(factionId);
        ships.add(e);
        return e;
    }

    /**
     * Creates the world map
     *
     * @param mapId resource id
     */
    public static void CreateWorldMap(int mapId) {
        tryInit();
        WorldMap map = new WorldMap(mapId);
        mapGraph = new TileMapGraph(map.getTileMap());
    }

    /**
     * Creates the college with it's building for the desired college
     *
     * @param factionId desired faction
     */
    public static void CreateCollege(int factionId) {
        tryInit();
        College c = new College(factionId);
        colleges.add(c);
    }

    private static void tryInit() {
        if (!initialized) {
            Initialize();
        }
    }

    public static Faction getFaction(int factionId) {
        tryInit();
        return factions.get(factionId - 1);
    }

    /**
     * Gets the setting object from the GameSetting.json
     *
     * @return the JSON representation fo settings
     */
    public static JsonValue getSettings() {
        tryInit();
        return settings;
    }

    public static College getCollege(int factionId) {
        tryInit();
        return colleges.get(factionId - 1);
    }

    /**
     * Utilises the cached cannonballs to fire one
     *
     * @param p   parent
     * @param dir shoot direction
     */
    public static void shoot(Ship p, Vector2 dir) {
        Vector2 pos = p.getComponent(Transform.class).getPosition().cpy();
        //pos.add(dir.x * TILE_SIZE * 0.5f, dir.y * TILE_SIZE * 0.5f);
        ballCache.get(currentElement++).fire(pos, dir, p);
        currentElement %= cacheSize;
    }

    public static void load(){
        tryInit();
        Pirate player = getPlayer().getComponent(Pirate.class);

        if(prefs.getBoolean("speedIncrease")){
            player.addPlunder(25);
            player.speedUpgrade();
        }
        if(prefs.getBoolean("damageReduce")){
            player.addPlunder(30);
            player.reduceDamage();
        }

        player.setAmmo(prefs.getInteger("playerAmmo"));
        player.setHealth(prefs.getInteger("playerHealth"));
        player.addPlunder(prefs.getInteger("playerPlunder"));

        Ship p = (Ship) player.getParent();
        p.setPosition(
                prefs.getFloat("playerX"),
                prefs.getFloat("playerY"));

        for(int i = 1; i<ships.size(); i++){
            Ship s = ships.get(i);
            s.setFaction(prefs.getInteger("NPC"+String.valueOf(i)+"faction"));
            s.setPosition(
                    prefs.getFloat("NPC"+String.valueOf(i)+"x"),
                    prefs.getFloat("NPC"+String.valueOf(i)+"y"));
        }
    }

    public static void save(){
        Player player = getPlayer();

        // player data
        prefs.putBoolean("save",true);
        prefs.putInteger("playerAmmo",player.getAmmo());
        prefs.putInteger("playerHealth",player.getHealth());
        prefs.putInteger("playerPlunder",player.getPlunder());
        prefs.putFloat("playerX",player.getPosition().x);
        prefs.putFloat("playerY",player.getPosition().y);

        boolean[] upgrades = player.getComponent(Pirate.class).getActiveUpgrades();
        prefs.putBoolean("speedIncrease",upgrades[0]);
        prefs.putBoolean("damageReduce",upgrades[1]);

        // npc data
        for(int i = 1; i<ships.size(); i++){
            Ship s = ships.get(i);
            prefs.putInteger("NPC"+String.valueOf(i)+"faction", s.getFaction());
            prefs.putFloat("NPC"+String.valueOf(i)+"x", s.getPosition().x);
            prefs.putFloat("NPC"+String.valueOf(i)+"y", s.getPosition().y);
        }

        prefs.flush();
    }

    /**
     * Uses a* not sure if it works, but I think it does.
     * Assessment 1 commenter
     * @param loc src
     * @param dst dst
     * @return queue of delta positions
     */
    public static QueueFIFO<Vector2> getPath(Vector2 loc, Vector2 dst) {
        return mapGraph.findOptimisedPath(loc, dst);
    }
}
