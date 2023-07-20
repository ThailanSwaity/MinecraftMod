package net.thailan.client.additions;

import net.minecraft.client.MinecraftClient;
import net.thailan.client.triggers.Tickable;

public class AutoForward extends Hack implements Tickable {

    private MinecraftClient client;

    public AutoForward(MinecraftClient client) {
        super("AutoForward");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled()) return;

        client.options.forwardKey.setPressed(true);
    }

    @Override
    protected void onDisable() {
        client.options.forwardKey.setPressed(false);
    }
    
}
