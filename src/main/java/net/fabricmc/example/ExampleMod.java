package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.additions.*;
import net.fabricmc.example.additions.variable.*;
import net.fabricmc.example.gui.ModMenuScreen;
import net.fabricmc.example.utils.CommandUtil;
import net.fabricmc.example.utils.DataUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	public static ModMenuScreen modMenuScreen;

	private static KeyBinding keyBinding_r;
	private static KeyBinding keyBinding_y;
	private static KeyBinding keyBinding_g;
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
	public static Tracers tracers;
	public static ESP esp;
	public static CommandList commandList = new CommandList();
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

		keyBinding_g = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Surround",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_G,
				"Hacks"
		));

		client = MinecraftClient.getInstance();

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

		hacksOverlay = new HacksOverlay();
		additionManager.addDisplay(hacksOverlay);
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

		ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
			while (keyBinding_r.wasPressed()) {
				xray.cycle();
				client.inGameHud.setOverlayMessage(xray.getString(), false);
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
			while (keyBinding_y.wasPressed()) {
				teleport.teleportToCursor(100);
				client1.player.sendMessage(Text.literal("Tried to teleport"), false);
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
			while (keyBinding_g.wasPressed()) {
				surround.trigger();
			}
		});

		commandList.register("obama", (args) -> {
			client.player.sendMessage(Text.literal("Oh no no no"));
		});

		commandList.register("add", "add friends to your friends list.", (args) -> {
			for (String name : args) {
				boolean success = friendList.addFriend(name);
				client.player.sendMessage(Text.literal(success ? name + " added as a friend." : name + " is already a friend."));
			}
			DataUtil.saveFriendsList(friendList);
		});

		commandList.register("remove", "remove friends from your friends list.", (args) -> {
			for (String name : args) {
				boolean success = friendList.removeFriend(name);
				client.player.sendMessage(Text.literal(success ? "Removed " + name + " from friends list." : name + " is not in your friends list."));
			}
			DataUtil.saveFriendsList(friendList);
		});

		commandList.register("friends", "show your friends list", (args) -> {
			ArrayList<String> friendNames = friendList.getFriends();
			if (friendList.isEmpty()) {
				client.player.sendMessage(Text.literal("Friend list is empty."));
			} else {
				client.player.sendMessage(Text.literal("Friend list:"));
				for (String name : friendNames) {
					PlayerListEntry playerListEntry = client.player.networkHandler.getPlayerListEntry(name);
					if (playerListEntry != null) {
						client.player.sendMessage(Text.literal("\t" + name).formatted(Formatting.GREEN));
					} else client.player.sendMessage(Text.literal("\t" + name));
				}
			}
		});

		commandList.register("surround", "change the tickDelay of surround", (args) -> {
			if (args[0] != null) {
				try {
					int tickDelay = Integer.parseInt(args[0]);
					surround.setTickDelay(tickDelay);
					client.player.sendMessage(Text.literal("Surround tickDelay set to " + tickDelay));
				} catch (NumberFormatException e) {
					client.player.sendMessage(Text.literal("First argument must be an integer!").formatted(Formatting.RED));
				}
			}
		});

		commandList.register("watermark", "edit the chat watermark", (args) -> {
			String watermark = "";
			for (int i = 0; i < args.length; i++) {
				watermark += args[i];
				if (i != args.length) watermark += " ";
			}
			chatWatermark.set(watermark);
		});

		commandList.register("help", "display all commands", (args) -> {
			ArrayList<String> list = commandList.getCommands();
			for (String command : list) client.player.sendMessage(Text.literal(command));
		});

		commandList.register("waypoint", "add a waypoint", (args) -> {
			//client.player.sendMessage(Text.literal(args.length + "").formatted(Formatting.GREEN));
			for (int i = 0; i < args.length; i++) LOGGER.info("args " + i + ": " + args[i]);
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("add")) {
					Waypoint waypoint = waypointList.addWaypoint(client.player.getPos(), client.world.getDimensionKey(), Colour.CORAL);
					client.player.sendMessage(Text.literal(waypoint.toString()).formatted(Formatting.GREEN));
				}
			} else if (args.length > 1) {

				// .waypoint add(String name)
				if (args[0].equalsIgnoreCase("add")) {
					LOGGER.info(args[1]);
					LOGGER.info("Is it a number?" + CommandUtil.isDouble(args[1]));
					if (args.length > 2 && !CommandUtil.isDouble(args[1])) {
						String name = "";
						for (int i = 1; i < args.length; i++) {
							name += args[i];
							if (i != args.length) name += " ";
						}
						Waypoint waypoint = waypointList.addWaypoint(new Vec3d(client.player.getX(), client.player.getY(), client.player.getZ()), name, client.world.getDimensionKey(), Colour.CORAL);
						client.player.sendMessage(Text.literal(waypoint.toString()).formatted(Formatting.GREEN));
						return;
					}

					// .waypoint add(double x, double y, double z, String name)
					try {
						double x = Double.parseDouble(args[1]);
						double y = Double.parseDouble(args[2]);
						double z = Double.parseDouble(args[3]);
						if (args.length >= 4) {
							String name = "";
							for (int i = 4; i < args.length; i++) {
								name += args[i];
								if (i != args.length) name += " ";
							}
							Waypoint waypoint = waypointList.addWaypoint(new Vec3d(x, y, z), name, client.world.getDimensionKey(), Colour.CORAL);
							client.player.sendMessage(Text.literal(waypoint.toString()).formatted(Formatting.GREEN));
						} else {
							Waypoint waypoint = waypointList.addWaypoint(new Vec3d(x, y, z), client.world.getDimensionKey(), Colour.CORAL);
							client.player.sendMessage(Text.literal(waypoint.toString()).formatted(Formatting.GREEN));
						}
					} catch (NumberFormatException e) {
						client.player.sendMessage(Text.literal("Incorrect syntax").formatted(Formatting.RED));
					}
				} else if (args[0].equalsIgnoreCase("remove")) {

					// .waypoint remove all
					if (args[1].equalsIgnoreCase("all")) {
						waypointList.clear();
						client.player.sendMessage(Text.literal("Removed all waypoints."));
					} else {
						// .waypoint remove(int n)
						try {
							int n = Integer.parseInt(args[1]);
							waypointList.remove(n);
							client.player.sendMessage(Text.literal("Removed waypoint " + n));
						} catch (NumberFormatException e) {
							client.player.sendMessage(Text.literal("Syntax error. Second argument must be an integer.").formatted(Formatting.RED));
						}
					}
				}
			}
		});

		commandList.register("waypoints", "lists waypoints", (args) -> {
			if (waypointList.size() == 0) {
				client.player.sendMessage(Text.literal("There are currently no waypoints."));
				return;
			}
			for (int i = 0; i < waypointList.size(); i++) {
				client.player.sendMessage(Text.literal(i + ": " + waypointList.getWaypoints().get(i)));
			}
		});

		commandList.register("test", (args) -> {
			if (client.getNetworkHandler().getServerInfo() != null) {
				ServerInfo serverInfo = client.getNetworkHandler().getServerInfo();
				client.player.sendMessage(Text.literal(serverInfo.name).formatted(Formatting.BOLD));
				client.player.sendMessage(Text.literal(serverInfo.address));
			}
		});

		commandList.register("save", (args) -> {
			if (client.getNetworkHandler().getServerInfo() != null) {
				DataUtil.saveServerWaypoints(waypointList, client.getNetworkHandler().getServerInfo().address);
				client.player.sendMessage(Text.literal("Saved waypoints for " + client.getNetworkHandler().getServerInfo().address));
			}
		});

		commandList.register("load", (args) -> {
			if (client.getNetworkHandler().getServerInfo() != null) {
				DataUtil.loadServerWaypoints(waypointList, client.getNetworkHandler().getServerInfo().address);
				client.player.sendMessage(Text.literal("Loaded waypoints for " + client.getNetworkHandler().getServerInfo().address));
			}
		});

		commandList.register("ego", (args) -> {
			String player = "";
			for (int i = 0; i < args.length; i++) {
				player += args[i];
				if (i != args.length) player += " ";
			}
			client.getNetworkHandler().sendChatMessage("u have a huge ego " + player);
		});

		commandList.register("test2", (args) -> {
			client.inGameHud.setOverlayMessage(Text.literal("Test"), false);
		});

		commandList.register("saveHackSettings", (args) -> {
			DataUtil.saveHackSettings(additionManager.getAdditions());
			client.inGameHud.setOverlayMessage(Text.literal("Hacks saved."), false);
		});

		commandList.register("loadHackSettings", (args) -> {
			DataUtil.loadHackSettings(additionManager.getAdditions());
			client.inGameHud.setOverlayMessage(Text.literal("Hacks loaded."), false);
		});

		commandList.register("ping", (args) -> {
			if (client.getNetworkHandler().getServerInfo() != null) {
				client.player.sendMessage(Text.literal("Ping: " + client.getNetworkHandler().getServerInfo().ping).formatted(Formatting.GOLD));
			}
		});

		ArrayList<String> friends = DataUtil.loadFriendsList();
		friendList.setFriends(friends);
		LOGGER.info("Loaded friends list");

		LOGGER.info("Hello Fabric world!");
	}
}
