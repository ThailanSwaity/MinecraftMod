package net.thailan.client.additions.variable;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.thailan.client.ThaiFoodClient;
import net.thailan.client.triggers.Tickable;

public class BlockSpacer extends VariableHack implements Tickable {

    private int spacing = 100;
    private int scanTicks = 2;

    public BlockSpacer() {
        super("BlockSpacer");

        optionsScreen.addOptionSlider("spacing",
                Text.literal("Block spacing " + spacing),
                (sliderWidget, value) -> sliderWidget.setMessage(Text.literal("Block spacing " + ((int) (value * 100)))),
                (sliderWidget, value) -> spacing = (int)(value * 100),
                0, 0, 115, 20, spacing / 100);
    }

    public void setCooldown(int cooldown) {
        scanTicks = cooldown;
    }

    @Override
    public void tick() {
        if (!isEnabled()) return;

        if (scanTicks > 0) {
            scanTicks--;
            return;
        }

        MinecraftClient client = ThaiFoodClient.getInstance().client;
        BlockPos standingBlock = client.player.getSteppingPos();
        int reachDistance = (int)client.interactionManager.getReachDistance()-2;

        for (int x = -reachDistance; x < reachDistance; x++) {
            for (int z = -reachDistance; z < reachDistance; z++) {
                for (int y = -2; y < 2; y++) {
                    BlockPos targetBlockPos = new BlockPos(standingBlock.getX() + x, standingBlock.getY() + y, standingBlock.getZ() + z);
                    if (targetBlockPos.getX() % spacing == 0 && targetBlockPos.getZ() % spacing == 0) {
                        if (tryPlace(targetBlockPos)) {
                            setCooldown(1);
                            return;
                        }
                    }
                }
            }
        }

    }

    private boolean tryPlace(BlockPos blockPos) {
        MinecraftClient client = ThaiFoodClient.getInstance().client;
        BlockState targetBlock = client.world.getBlockState(blockPos);
        if (targetBlock.isAir()) return false;
        BlockState targetBlockUp = client.world.getBlockState(blockPos.up());
        if (!targetBlockUp.isAir()) return false;

        if (targetBlock.getBlock().asItem() == client.player.getMainHandStack().getItem()) return false;

        Vec3d blockPosVec = new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        BlockHitResult hitResult = new BlockHitResult(blockPosVec, Direction.UP, blockPos, false);
        client.interactionManager.interactBlock(client.player, Hand.MAIN_HAND, hitResult);
        return true;
    }
}
