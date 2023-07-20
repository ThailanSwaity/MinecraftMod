package net.thailan.client.additions;

import net.thailan.client.additions.Hack;
import net.thailan.client.triggers.Tickable;
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
