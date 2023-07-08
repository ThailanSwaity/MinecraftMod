package net.fabricmc.example.additions;

import net.fabricmc.example.Tickable;
import net.fabricmc.example.WorldUtil;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;

public class BetterPortal extends Hack implements Tickable {

    MinecraftClient client;

    public BetterPortal(MinecraftClient client) {
        super("BetterPortal");
        this.client = client;
    }

    @Override
    public void tick() {
        if (!isEnabled()) return;

        if (WorldUtil.doesBoxTouchBlock(client.player.getBoundingBox(), Blocks.NETHER_PORTAL)) {
            client.player.prevNauseaIntensity = -1f;
            client.player.nauseaIntensity = -1f;
        }
    }
}
