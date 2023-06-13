package net.fabricmc.example.additions;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.PacketHelper;
import net.fabricmc.example.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Fly extends Hack implements Tickable {

    private MinecraftClient client;
    private ClientPlayerEntity player;
    private int ticksFloating = 0;

    public Fly(MinecraftClient client) {
        super("Fly");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled() && !client.player.isCreative()) {
            client.player.getAbilities().allowFlying = false;
            return;
        }

        client.player.getAbilities().allowFlying = true;
        if (client.player.getVelocity().getY() <= -0.05 && client.player.getAbilities().flying) {
            PacketHelper.sendOnGroundOnly(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
        if (++ticksFloating > 10 && client.player.getSteppingBlockState().isAir() && !client.player.isOnGround()) {
            PacketHelper.sendPosition(client.player.getPos().subtract(0.0, 0.0433D, 0.0));

            ticksFloating = 0;
        }
    }

    @Override
    protected void onEnable() {
        client.player.getAbilities().allowFlying = true;
    }

    @Override
    protected void onDisable() {
        client.player.getAbilities().allowFlying = false;
    }

}
