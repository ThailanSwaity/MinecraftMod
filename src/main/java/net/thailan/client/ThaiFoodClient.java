package net.thailan.client;

import net.fabricmc.api.ModInitializer;
import net.thailan.client.additions.variable.*;
import net.thailan.client.gui.ModMenuScreen;
import net.thailan.client.mixin.ExampleMixin;
import net.thailan.client.utils.KeyBinder;
import net.thailan.client.utils.command.CommandHelper;
import net.thailan.client.utils.DataUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.thailan.client.additions.*;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ThaiFoodClient implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	public static ModMenuScreen modMenuScreen;
	private static ThaiFoodClient instance;
	public MinecraftClient client;
	public static FullBright fullBright;
	public static Xray xray;
	public static Fly fly;
	public static Teleport teleport;
	public static AutoFish autoFish;
	public static AutoBridge autoBridge;
	public static AutoRespawn autoRespawn;
	public static SpeedMine speedMine;
	public static PlayerCoordinateDisplay playerCoordinateDisplay;
	public static SpawnCoordinateDisplay spawnCoordinateDisplay;
	public static DeathCoordinateDisplay deathCoordinateDisplay;
	public static ArmourHUD armourHUD;
	public static EntityControl entityControl;
	public static EntityNames entityNames;
	public static ChunkTracking chunkTracking;
	public static NoWeather noWeather;
	public static Tracers tracers;
	public static ESP esp;
	public static ChatWatermark chatWatermark;
	public static FriendList friendList;
	public static Surround surround;
	public static Sarcasm sarcasm;
	public static HacksOverlay hacksOverlay;
	public static Waypoints waypoints;
	public static Glasses glasses;
	public static BetterPortal betterPortal;
	public static NoDamageTilt noDamageTilt;
	public static WaypointList waypointList = new WaypointList();
	public static Fog fog;
	public AdditionManager additionManager = new AdditionManager();
	public static ThaiFoodClient getInstance() {
		return instance;
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		instance = this;

		client = MinecraftClient.getInstance();
		initKeyBinds();
		initAdditions();
		// For custom client commands
		CommandHelper.registerCommands();

		ArrayList<String> friends = DataUtil.loadFriendsList();
		friendList.setFriends(friends);
		LOGGER.info("Loaded friends list");

		loadHacks();

		LOGGER.info("Hello Fabric world!");
	}

	private void initKeyBinds() {
		// Registers and binds r key to toggle xray
		KeyBinding r = new KeyBinding(
				"Xray cycle",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_R,
				"Hacks");
		KeyBinder.register_END_TICK(r, client1 -> {
			while (r.wasPressed()) {
				ThaiFoodClient.xray.cycle();
				client1.inGameHud.setOverlayMessage(xray.getString(), false);
			}
		});

		KeyBinding y = new KeyBinding(
				"Teleport",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_Y,
				"Hacks"
		);
		KeyBinder.register_END_TICK(y, client1 -> {
			while (y.wasPressed()) {
				teleport.teleportToCursor(100);
				client1.inGameHud.setOverlayMessage(Text.literal("Tried to teleport"), false);
			}
		});

		KeyBinding g = new KeyBinding(
				"Surround",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_G,
				"Hacks"
		);
		KeyBinder.register_END_TICK(g, client1 -> {
			while (g.wasPressed()) surround.trigger();
		});
	}

	private void initAdditions() {
		autoRespawn = new AutoRespawn();
		additionManager.addDisplay(autoRespawn);
		fly = new Fly(client);
		esp = new ESP();
		additionManager.addDisplay(esp);
		additionManager.addDisplay(fly);
		additionManager.addDisplay(new Speed(client));
		//additionManager.addDisplay(new Zoom(client));
		additionManager.addDisplay(new NoFallDamage(client));
		additionManager.addDisplay(new BoatFly(client));
		additionManager.addDisplay(new AutoEat(client));
		additionManager.addDisplay(new EntityTp(client));
		additionManager.addDisplay(new KillAura(client));
		additionManager.addDisplay(new Reach(client));
		additionManager.addDisplay(new AutoTotem(client));
		playerCoordinateDisplay = new PlayerCoordinateDisplay(client);
		additionManager.addDisplay(playerCoordinateDisplay);
		spawnCoordinateDisplay = new SpawnCoordinateDisplay(client);
		additionManager.addDisplay(spawnCoordinateDisplay);
		entityNames = new EntityNames();
		additionManager.addDisplay(entityNames);
		entityControl = new EntityControl(client);
		additionManager.addDisplay(entityControl);
		deathCoordinateDisplay = new DeathCoordinateDisplay(client);
		additionManager.addDisplay(deathCoordinateDisplay);
		chunkTracking = new ChunkTracking(client);
		additionManager.addDisplay(chunkTracking);
		armourHUD = new ArmourHUD();
		additionManager.addDisplay(armourHUD);
		fullBright = new FullBright();
		additionManager.addDisplay(fullBright);
		chatWatermark = new ChatWatermark();
		additionManager.addDisplay(chatWatermark);
		friendList = new FriendList();
		additionManager.addDisplay(friendList);
		noWeather = new NoWeather();
		additionManager.addDisplay(noWeather);

		additionManager.addDisplay(new BlockSpacer());

		// Rendering
		tracers = new Tracers(client);
		additionManager.addDisplay(tracers);

		surround = new Surround(client);
		additionManager.addDisplay(surround);
		sarcasm = new Sarcasm();
		additionManager.addDisplay(sarcasm);
		additionManager.addDisplay(new AutoForward(client));
		waypoints = new Waypoints();
		additionManager.addDisplay(waypoints);
		glasses = new Glasses();
		additionManager.addDisplay(glasses);
		betterPortal = new BetterPortal(client);
		additionManager.addDisplay(betterPortal);
		noDamageTilt = new NoDamageTilt();
		additionManager.addDisplay(noDamageTilt);
		fog = new Fog();
		additionManager.addDisplay(fog);

		hacksOverlay = new HacksOverlay();
		additionManager.addDisplay(hacksOverlay);
		xray = new Xray(client);

		additionManager.addDisplay(xray);

		// Teleporter is not a hack (HAHA funny);
		teleport = new Teleport(client);
		teleport.setDistance(10);
		additionManager.addDisplay(teleport);

		additionManager.addDisplay(new AutoCrop(client));
		autoFish = new AutoFish(client);
		additionManager.addDisplay(autoFish);
		autoBridge = new AutoBridge(client);
		additionManager.addDisplay(autoBridge);
		speedMine = new SpeedMine(client);
		additionManager.addDisplay(speedMine);
	}

	public void saveHacks() {
		try {
			DataUtil.saveHackSettings(additionManager.getAdditions());
			LOGGER.info("Hacks saved.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadHacks() {
		try {
			DataUtil.loadHackSettings(additionManager.getAdditions());
			LOGGER.info("Hacks loaded.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
