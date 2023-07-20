package net.thailan.client.utils;

import net.thailan.client.ThaiFoodClient;
import net.thailan.client.mixin.ClientConnectionInvoker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.packet.Packet;

public class PacketHelper {

    public static void sendPosition(Vec3d pos) {
        MinecraftClient client = ThaiFoodClient.getInstance().client;
        ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
        pos = PacketHelper.fixCoords(pos);
        Packet packet = new PlayerMoveC2SPacket.PositionAndOnGround(pos.getX(), pos.getY(), pos.getZ(), false);
        conn.sendImmediate(packet, null);
    }

    public static void sendVehicleMovePacket(Entity entity) {
        MinecraftClient client = ThaiFoodClient.getInstance().client;
        ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
        Packet packet = new VehicleMoveC2SPacket(entity);
        conn.sendImmediate(packet, null);
    }

    public static void sendPositionOnGround(Vec3d pos) {
        MinecraftClient client = ThaiFoodClient.getInstance().client;
        ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
        pos = PacketHelper.fixCoords(pos);
        Packet packet = new PlayerMoveC2SPacket.PositionAndOnGround(pos.getX(), pos.getY(), pos.getZ(), true);
        conn.sendImmediate(packet, null);
    }

    public static void sendPlayerAttack(Entity entity) {
        MinecraftClient client = ThaiFoodClient.getInstance().client;
        ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
        Packet packet = PlayerInteractEntityC2SPacket.attack(entity, client.player.isSneaking());
        conn.sendImmediate(packet, null);
    }

    public static void sendBlockBreakStartAndEnd(BlockPos pos, Direction direction) {
        MinecraftClient client = ThaiFoodClient.getInstance().client;
        ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
        Packet packet1 = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, direction);
        Packet packet2 = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, direction);
        conn.sendImmediate(packet1, null);
        conn.sendImmediate(packet2, null);
    }

    public static void sendBlockBreak(BlockPos pos, Direction direction) {
        MinecraftClient client = ThaiFoodClient.getInstance().client;
        ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
        Packet packet = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, direction);
        conn.sendImmediate(packet, null);
    }

    public static void sendOnGroundOnly(PlayerMoveC2SPacket packet) {
        MinecraftClient client = ThaiFoodClient.getInstance().client;
        ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
        conn.sendImmediate(packet, null);
    }

    public static Vec3d fixCoords(Vec3d pos) {
        return pos;
    }

}
