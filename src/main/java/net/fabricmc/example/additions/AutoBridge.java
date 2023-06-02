package net.fabricmc.example.additions;

import net.fabricmc.example.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class AutoBridge extends Hack implements Tickable {

    private MinecraftClient client;

    public AutoBridge(MinecraftClient client) {
        super("AutoBridge");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled()) return;

        Item item = client.player.getOffHandStack().getItem();
        if (Item.BLOCK_ITEMS.containsValue(item)) {
            BlockPos steppingPos = client.player.getSteppingPos();
            if (client.world.getBlockState(steppingPos).isAir()) {
                if (getBuildDirection(steppingPos) != null) {
                    Direction bridgeDirection = getBuildDirection(steppingPos);
                    // TODO: Make the hack place blocks when player jumps
                    tryPlaceBlock(steppingPos, bridgeDirection);
                }
            }
        }
    }

    public boolean tryPlaceBlock(BlockPos pos, Direction side) {
        Vec3d blockPos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        BlockHitResult hitResult = new BlockHitResult(blockPos, side, pos, false);
        client.interactionManager.interactBlock(client.player, Hand.OFF_HAND, hitResult);
        return (hitResult.getType() == HitResult.Type.BLOCK);
    }

    public Direction getBuildDirection(BlockPos pos) {
        if (!client.world.getBlockState(pos.east()).isAir()) return Direction.EAST;
        else if (!client.world.getBlockState(pos.west()).isAir()) return Direction.WEST;
        else if (!client.world.getBlockState(pos.south()).isAir()) return Direction.SOUTH;
        else if (!client.world.getBlockState(pos.north()).isAir()) return Direction.NORTH;
        return null;
    }

}
