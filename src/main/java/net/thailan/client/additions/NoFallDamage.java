package net.thailan.client.additions;

import net.thailan.client.utils.PacketHelper;
import net.thailan.client.triggers.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFallDamage extends Hack implements Tickable {

    private MinecraftClient client;

    public NoFallDamage(MinecraftClient client) {
        super("NoFallDamage");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled()) return;

        // TODO: Fix damage when teleporting and landing while flying

        if (client.player.fallDistance <= (client.player.isFallFlying() ? 1 : 2)) return;
        if (client.player.isFallFlying() && client.player.isSneaking() && !isFallingFastEnoughToCauseDamage(client.player)) return;
        PacketHelper.sendOnGroundOnly(new PlayerMoveC2SPacket.OnGroundOnly(true));
    }

    private boolean isFallingFastEnoughToCauseDamage(ClientPlayerEntity player) {
        return player.getVelocity().getY() < -0.5;
    }

}
