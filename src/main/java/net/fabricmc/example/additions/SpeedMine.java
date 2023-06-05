package net.fabricmc.example.additions;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.Tickable;
import net.fabricmc.example.additions.Hack;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;

public class SpeedMine extends Hack implements Tickable {

    private int maxCoolDown = 15;
    private double triggerChance = 0.1;
    private MinecraftClient client;

    public SpeedMine(MinecraftClient client) {
        super("SpeedMine");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled()) return;
    }

    public boolean shouldTrigger(BlockPos pos) {
        BlockState blockState = client.world.getBlockState(pos);
        boolean act = client.player.getMainHandStack().getItem().canMine(blockState, client.world, pos, client.player);
        return Math.random() <= triggerChance && act;
    }

    public void setTriggerChance(double triggerChance) {
        this.triggerChance = triggerChance;
    }

    public double getTriggerChance() {
        return triggerChance;
    }

}
