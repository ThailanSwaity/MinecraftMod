package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Teleport {

    private int distance = 5;
    private MinecraftClient client;

    public Teleport(MinecraftClient client) {
        this.client = client;
    }

    public void increment(int value) {
        distance += value;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    public int getDistance() {
        return this.distance;
    }

    public void decrement(int value) {
        distance -= value;
    }

    public void teleportToCursor(double distance) {
        BlockHitResult result = (BlockHitResult)client.player.raycast(distance, client.getTickDelta(), false);
        boolean hit = (result.getType() != HitResult.Type.MISS);
        ExampleMod.LOGGER.info("Did hit: " + hit);
        if (!hit) return;
        Direction direction = result.getSide();
        BlockPos pos = result.getBlockPos();

        BlockPos targetBlock = pos.offset(direction);
        Vec3d targetPosition = new Vec3d(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ());

        teleport(targetPosition);
    }

    private void teleport(Vec3d targetPos) {
        for (int i = 0; i < 10; i++) {
            PacketHelper.sendPositionOnGround(client.player.getPos());
        }

        client.player.setPos(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        PacketHelper.sendPositionOnGround(targetPos);
    }

    public void teleport() {
        Vec3d pos = client.player.getPos();
        Vec3d targetPos = pos.add(client.player.getRotationVector().multiply(distance));
        ExampleMod.LOGGER.info("Teleported.");

        teleport(targetPos);
    }

    public String toString() {
        return "Teleport " + distance + " blocks";
    }

}
