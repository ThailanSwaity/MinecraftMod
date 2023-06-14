package net.fabricmc.example.additions;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.PacketHelper;
import net.fabricmc.example.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Teleport extends Hack implements Tickable {

    private int distance = 5;
    public static final int VERTICAL_FIRST = 0;
    public static final int DIRECT = 1;
    private Vec3d targetPos;
    private int teleportTick = 0;
    private int mode;
    private MinecraftClient client;

    public Teleport(MinecraftClient client) {
        super("Teleport");
        enable();
        mode = DIRECT;
        this.client = client;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    public int getDistance() {
        return this.distance;
    }

    public void tick() {
        if (mode == VERTICAL_FIRST && targetPos != null) {
            verticalFirstTeleport(targetPos, teleportTick);
            teleportTick++;
        }
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

        if (mode == VERTICAL_FIRST) {
            this.targetPos = targetPosition;
            teleportTick = 0;
        }
        else if (mode == DIRECT) teleport(targetPosition);
    }

    private void teleport(Vec3d targetPos) {
        for (int i = 0; i < 10; i++) {
            PacketHelper.sendPositionOnGround(client.player.getPos());
        }

        client.player.setPos(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        PacketHelper.sendPositionOnGround(targetPos);
    }

    public static void tp(ClientPlayerEntity player,  Vec3d targetPos) {
        for (int i = 0; i < 10; i++) {
            PacketHelper.sendPositionOnGround(player.getPos());
        }

        player.setPos(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        PacketHelper.sendPositionOnGround(targetPos);
    }

    public static void tpTo(ClientPlayerEntity player, Vec3d targetPos) {
        double maxMovementDist = 8.0;
        Vec3d dist = new Vec3d(targetPos.getX() - player.getX(), targetPos.getY() - player.getY(), targetPos.getZ() - player.getZ());
        Vec3d moveVector = dist.multiply(1/maxMovementDist);

        Vec3d position = new Vec3d(player.getX(), player.getY(), player.getZ());
        int steps = (int)Math.floor(dist.length() / maxMovementDist);
        for (int i = 0; i < steps; i++) {
            position = position.add(moveVector);
            PacketHelper.sendPosition(position);
        }
    }

    @Override
    public void toggle() {
        if (mode == DIRECT) mode = VERTICAL_FIRST;
        else if (mode == VERTICAL_FIRST) mode = DIRECT;
    }

    @Override
    public String toString() {
        return "Teleport mode: " + (mode == DIRECT ? "direct" : "vertical first");
    }

    public void verticalFirstTeleport(Vec3d targetPos, int tick) {

        int tpDistance = 50;
        if (tick % 5 != 0) return;

        tick = tick / 5;

        int partialTp;
        int worldHeight = client.world.getHeight();

        int playerPosY = (int)client.player.getPos().getY();
        int originToWorldHeight = worldHeight - playerPosY;
        int tps1 = originToWorldHeight / tpDistance;
        partialTp = originToWorldHeight % tpDistance;

        int worldHeightToTarget = worldHeight - (int) targetPos.getY();
        int tps2 = worldHeightToTarget / tpDistance;
        partialTp = worldHeightToTarget % tpDistance;

        // Teleport first to world height incrementing by 100 blocks
        if (tick < tps1) teleport(client.player.getPos().add(0, tpDistance, 0));
        else if (tick == tps1) teleport(client.player.getPos().add(0, partialTp, 0));

        // Teleport to target x and z
        else if (tick <= tps1 + 2) teleport(new Vec3d(targetPos.getX() + 0.5, client.player.getPos().getY(), targetPos.getZ() + 0.5));

        // Teleport to targetPos y
        else if (tick - tps1 + 2 < tps2) teleport(client.player.getPos().subtract(0, tpDistance, 0));
        else if (tick - tps1 + 2 == tps2) teleport(client.player.getPos().subtract(0, partialTp, 0));

    }

    public void teleport() {
        Vec3d pos = client.player.getPos();
        Vec3d targetPos = pos.add(client.player.getRotationVector().multiply(distance));
        ExampleMod.LOGGER.info("Teleported.");

        teleport(targetPos);
    }

}
