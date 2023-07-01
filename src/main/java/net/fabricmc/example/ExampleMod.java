package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.additions.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");
	private static KeyBinding keyBinding_r;
	private static KeyBinding keyBinding_y;
	private static ExampleMod instance;
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
	public static DetectPlayers detectPlayers;

	public static CommandList commandList = new CommandList();
	public static ChestTracers chestTracers;
	public static PortalTracers portalTracers;
	public static ChestESP chestESP;
	public static ChatWatermark chatWatermark;
	public static FriendList friendList;
	public AdditionManager additionManager = new AdditionManager();
	public static ExampleMod getInstance() {
		return instance;
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		instance = this;

		keyBinding_r = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Xray cycle",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_R,
				"Hacks"
		));

		keyBinding_y = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Teleport",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_Y,
				"Hacks"
		));

		client = MinecraftClient.getInstance();

		autoRespawn = new AutoRespawn();
		additionManager.add(autoRespawn);
		fly = new Fly(client);
		additionManager.add(fly);
		additionManager.add(new NoFallDamage(client));
		additionManager.add(new BoatFly(client));
		additionManager.add(new AutoEat(client));
		additionManager.add(new EntityTp(client));
		additionManager.add(new KillAura(client));
		additionManager.add(new Reach(client));
		additionManager.add(new AutoTotem(client));
		playerCoordinateDisplay = new PlayerCoordinateDisplay(client);
		additionManager.add(playerCoordinateDisplay);
		spawnCoordinateDisplay = new SpawnCoordinateDisplay(client);
		additionManager.add(spawnCoordinateDisplay);
		entityNames = new EntityNames();
		additionManager.add(entityNames);
		entityControl = new EntityControl(client);
		additionManager.add(entityControl);
		deathCoordinateDisplay = new DeathCoordinateDisplay(client);
		additionManager.add(deathCoordinateDisplay);
		chunkTracking = new ChunkTracking(client);
		additionManager.add(chunkTracking);
		armourHUD = new ArmourHUD();
		additionManager.add(armourHUD);
		fullBright = new FullBright();
		additionManager.add(fullBright);
		chatWatermark = new ChatWatermark();
		additionManager.add(chatWatermark);
		friendList = new FriendList();
		additionManager.add(friendList);
		noWeather = new NoWeather();
		additionManager.add(noWeather);
		detectPlayers = new DetectPlayers(client);
		additionManager.add(detectPlayers);
		chestTracers = new ChestTracers();
		additionManager.add(chestTracers);
		portalTracers = new PortalTracers();
		additionManager.add(portalTracers);
		chestESP = new ChestESP();
		additionManager.add(chestESP);
		xray = new Xray(client);
		xray.addBlocksORE(
				Blocks.STONE,
				Blocks.DEEPSLATE,
				Blocks.DIRT,
				Blocks.GRASS_BLOCK,
				Blocks.SAND,
				Blocks.NETHERRACK,
				Blocks.ANDESITE,
				Blocks.DIORITE,
				Blocks.GRANITE,
				Blocks.BASALT,
				Blocks.BLACKSTONE
		);

		xray.addBlocksCAVE(
				Blocks.DIRT,
				Blocks.GRASS_BLOCK,
				Blocks.SAND
		);

		xray.addBlocksSPAWNER(
				Blocks.COBBLESTONE,
				Blocks.MOSSY_COBBLESTONE,
				Blocks.SPAWNER,
				Blocks.INFESTED_COBBLESTONE
		);

		additionManager.add(xray);

		// Teleporter is not a hack;
		teleport = new Teleport(client);
		teleport.setDistance(10);
		additionManager.add(teleport);

		additionManager.add(new AutoCrop(client));
		autoFish = new AutoFish(client);
		additionManager.add(autoFish);
		autoBridge = new AutoBridge(client);
		additionManager.add(autoBridge);
		speedMine = new SpeedMine(client);
		additionManager.add(speedMine);

		ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
			while (keyBinding_r.wasPressed()) {
				xray.cycle();
				client1.player.sendMessage(Text.literal(xray.toString()), false);
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
			while (keyBinding_y.wasPressed()) {
				teleport.teleportToCursor(100);
				client1.player.sendMessage(Text.literal("Tried to teleport"), false);
			}
		});

		commandList.register("obama", (args) -> {
			client.player.sendMessage(Text.literal("Oh no no no"));
		});

		commandList.register("add", (args) -> {
			for (String name : args) {
				boolean success = friendList.addFriend(name);
				client.player.sendMessage(Text.literal(success ? name + " added as a friend." : name + " is already a friend."));
			}
		});

		commandList.register("remove", (args) -> {
			for (String name : args) {
				boolean success = friendList.removeFriend(name);
				client.player.sendMessage(Text.literal(success ? "Removed " + name + " from friends list." : name + " is not in your friends list."));
			}
		});

		commandList.register("friends", (args) -> {
			ArrayList<String> friendNames = friendList.getFriends();
			if (friendList.isEmpty()) {
				client.player.sendMessage(Text.literal("Friend list is empty."));
			} else {
				client.player.sendMessage(Text.literal("Friend list:"));
				for (String name : friendNames) client.player.sendMessage(Text.literal("\t" + name));
			}
		});

		LOGGER.info("Hello Fabric world!");
	}
}
