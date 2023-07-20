package net.thailan.client.additions;

import net.thailan.client.ThaiFoodClient;
import net.thailan.client.triggers.Tickable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
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
            BlockPos steppingPos = getPlayerFootPosition();
            ThaiFoodClient.LOGGER.info("Is stepping block air: " + client.world.getBlockState(steppingPos).isAir());
            ThaiFoodClient.LOGGER.info("Block type: " + client.world.getBlockState(steppingPos).getBlock().toString());

            BlockState blockState = client.world.getBlockState(steppingPos);
            if (placeable(steppingPos)) {
                if (getBuildDirection(steppingPos) != null) {
                    Direction bridgeDirection = getBuildDirection(steppingPos);
                    ThaiFoodClient.LOGGER.info("Direction: " + bridgeDirection.toString());
                    // TODO: Make the hack place blocks when player jumps
                    tryPlaceBlock(steppingPos, bridgeDirection);
                }
            }
        }
    }

    private BlockPos getPlayerFootPosition() {
        double offset = 1.0E-5F;
        int i = MathHelper.floor(client.player.getX());
        int j = MathHelper.floor(client.player.getY() - (double)offset);
        int k = MathHelper.floor(client.player.getZ());
        return new BlockPos(i, j, k);
    }

    public boolean tryPlaceBlock(BlockPos pos, Direction side) {
        Vec3d blockPos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        BlockHitResult hitResult = new BlockHitResult(blockPos, side, pos, false);
        client.interactionManager.interactBlock(client.player, Hand.OFF_HAND, hitResult);
        return (hitResult.getType() == HitResult.Type.BLOCK);
    }

    public boolean placeable(BlockPos blockPos) {
        BlockState blockState = client.world.getBlockState(blockPos);
        return blockState.isAir() || blockState.getBlock() == Blocks.LAVA || blockState.getBlock() == Blocks.WATER;
    }

    public Direction getBuildDirection(BlockPos pos) {
        if (!placeable(pos.east())) return Direction.EAST;
        else if (!placeable(pos.west())) return Direction.WEST;
        else if (!placeable(pos.south())) return Direction.SOUTH;
        else if (!placeable(pos.north())) return Direction.NORTH;
        return null;
    }

}
