package net.fabricmc.example;

import net.fabricmc.example.mixin.ClientConnectionInvoker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.packet.Packet;

public class PacketHelper {

    public static void sendPosition(Vec3d pos) {
        MinecraftClient client = ExampleMod.getInstance().client;
        ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
        pos = PacketHelper.fixCoords(pos);
        Packet packet = new PlayerMoveC2SPacket.PositionAndOnGround(pos.getX(), pos.getY(), pos.getZ(), false);
        conn.sendImmediate(packet, null);
    }

    public static void sendPositionOnGround(Vec3d pos) {
        MinecraftClient client = ExampleMod.getInstance().client;
        ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
        pos = PacketHelper.fixCoords(pos);
        Packet packet = new PlayerMoveC2SPacket.PositionAndOnGround(pos.getX(), pos.getY(), pos.getZ(), true);
        conn.sendImmediate(packet, null);
    }

    public static void sendBlockBreakStartAndEnd(BlockPos pos, Direction direction) {
        MinecraftClient client = ExampleMod.getInstance().client;
        ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
        Packet packet1 = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, direction);
        Packet packet2 = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, direction);
        conn.sendImmediate(packet1, null);
        conn.sendImmediate(packet2, null);
    }

    public static void sendOnGroundOnly(PlayerMoveC2SPacket packet) {
        MinecraftClient client = ExampleMod.getInstance().client;
        ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
        conn.sendImmediate(packet, null);
    }

    public static Vec3d fixCoords(Vec3d pos) {
        return pos;
    }

}
