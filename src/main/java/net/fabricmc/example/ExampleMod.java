package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.additions.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public static Teleport teleport;
	public static AutoFish autoFish;
	public static AutoBridge autoBridge;
	public static AutoRespawn autoRespawn;
	public static SpeedMine speedMine;
	public static PlayerCoordinateDisplay playerCoordinateDisplay;
	public static DeathCoordinateDisplay deathCoordinateDisplay;

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
		additionManager.add(new Fly(client));
		additionManager.add(new NoFallDamage(client));
		additionManager.add(new BoatFly(client));
		additionManager.add(new AutoEat(client));
		playerCoordinateDisplay = new PlayerCoordinateDisplay(client);
		additionManager.add(playerCoordinateDisplay);
		deathCoordinateDisplay = new DeathCoordinateDisplay(client);
		additionManager.add(deathCoordinateDisplay);
		fullBright = new FullBright();
		additionManager.add(fullBright);
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

		LOGGER.info("Hello Fabric world!");
	}
}
