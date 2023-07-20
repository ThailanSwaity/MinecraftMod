package net.thailan.client.additions;

import net.thailan.client.triggers.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Surround extends Hack implements Tickable {

    private MinecraftClient client;
    private int ticksSinceTrigger = -1;
    private int tickDelay = 1;

    public Surround(MinecraftClient client) {
        super("Surround");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled()) return;
        if (ticksSinceTrigger == -1 || ticksSinceTrigger >= 4 * ticksSinceTrigger + 1) return;

        BlockPos steppingPos = client.player.getSteppingPos();
        if (ticksSinceTrigger == tickDelay) placeBlock(steppingPos.west());
        else if (ticksSinceTrigger == 2 * tickDelay) placeBlock(steppingPos.north());
        else if (ticksSinceTrigger == 3 * tickDelay) placeBlock(steppingPos.east());
        else if (ticksSinceTrigger == 4 * tickDelay) placeBlock(steppingPos.south());
        ticksSinceTrigger++;
    }

    public void trigger() {
        if (!isEnabled()) return;
        ticksSinceTrigger = 0;
    }

    public void setTickDelay(int tickDelay) {
        this.tickDelay = tickDelay;
    }

    private void placeBlock(BlockPos blockPos) {
        Vec3d blockPosVec = new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        BlockHitResult hitResult = new BlockHitResult(blockPosVec, Direction.UP, blockPos, false);
        client.interactionManager.interactBlock(client.player, Hand.MAIN_HAND, hitResult);
    }

}
