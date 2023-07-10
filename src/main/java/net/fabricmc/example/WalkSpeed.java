package net.fabricmc.example;

import net.fabricmc.example.additions.Hack;
import net.minecraft.client.MinecraftClient;

public class WalkSpeed extends Hack implements Tickable {

    private MinecraftClient client;

    public WalkSpeed(MinecraftClient client) {
        super("WalkSpeed");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled()) return;

        client.player.getAbilities().setWalkSpeed(1f);
    }

}
