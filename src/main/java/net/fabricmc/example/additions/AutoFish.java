package net.fabricmc.example.additions;

import net.fabricmc.example.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

public class AutoFish extends Hack implements Tickable {

    private int reelInCount = -1;

    private MinecraftClient client;

    public AutoFish(MinecraftClient client) {
        super("AutoFish");
        this.client = client;
    }

    public void tick() {
        if (reelInCount >= 0) reelInCount--;
        if (reelInCount == 0) client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
    }

    public void catchFish() {
        client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
        reelInCount = 20;
    }

}
