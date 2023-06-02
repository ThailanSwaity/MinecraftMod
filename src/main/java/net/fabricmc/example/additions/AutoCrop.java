package net.fabricmc.example.additions;

import net.fabricmc.example.Tickable;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class AutoCrop extends Hack implements Tickable {

    private MinecraftClient client;
    private int scanTicks = 2;

    public AutoCrop(MinecraftClient client) {
        super("AutoCrop");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled()) return;

        if (scanTicks > 0) {
            scanTicks--;
            return;
        }

        BlockPos standingBlock = client.player.getSteppingPos();
        int reachDistance = (int)client.interactionManager.getReachDistance()-1;

        for (int x = -reachDistance; x < reachDistance; x++) {
            for (int z = -reachDistance; z < reachDistance; z++) {
                for (int y = -2; y < 2; y++) {
                    BlockPos targetBlockPos = new BlockPos(standingBlock.getX() + x, standingBlock.getY() + y, standingBlock.getZ() + z);
                    tryHarvest(targetBlockPos);
                    if (tryPlant(targetBlockPos)) {
                        setCooldown(1);
                        return;
                    }
                }
            }
        }
        setCooldown(1);
    }

    public void setCooldown(int cooldown) {
        scanTicks = cooldown;
    }

    public void tryHarvest(BlockPos pos) {
        BlockState targetBlock = client.world.getBlockState(pos);
        Block block = targetBlock.getBlock();
        if (block instanceof CropBlock) {
            if (block == Blocks.MELON_STEM || block == Blocks.PUMPKIN_STEM) return;
            if (((CropBlock) block).isMature(targetBlock)) {
                client.interactionManager.attackBlock(pos, Direction.UP);
            }
        }
    }

    public boolean tryPlant(BlockPos pos) {
        BlockState targetBlock = client.world.getBlockState(pos);
        if (targetBlock.getBlock() instanceof FarmlandBlock) {
            BlockState blockStateUp = client.world.getBlockState(pos.up());
            if (blockStateUp.getBlock() instanceof AirBlock) {
                if (tryUseSeed(pos, Hand.MAIN_HAND) || tryUseSeed(pos, Hand.OFF_HAND)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean tryUseSeed(BlockPos pos, Hand hand) {
        Item item = client.player.getStackInHand(hand).getItem();
        if (item == Items.WHEAT_SEEDS || item == Items.MELON_SEEDS || item == Items.PUMPKIN_SEEDS || item == Items.BEETROOT_SEEDS
        || item == Items.CARROT || item == Items.POTATO) {
            Vec3d blockPos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
            BlockHitResult hitResult = new BlockHitResult(blockPos, Direction.UP, pos, false);
            client.interactionManager.interactBlock(client.player, hand, hitResult);
            return true;
        }
        return false;
    }

}
