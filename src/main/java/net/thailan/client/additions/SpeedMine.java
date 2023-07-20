package net.thailan.client.additions;

import net.thailan.client.triggers.Tickable;
import net.thailan.client.additions.variable.VariableHack;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class SpeedMine extends VariableHack implements Tickable {

    private int maxCoolDown = 15;
    private double triggerChance = 0.1;
    private MinecraftClient client;

    public SpeedMine(MinecraftClient client) {
        super("SpeedMine");
        this.client = client;
        optionsScreen.addOptionSlider("speed", Text.literal(String.format("%.2f", triggerChance)),
                (sliderWidget, value) -> sliderWidget.setMessage(Text.literal(String.format("%.2f", value))),
                (sliderWidget, value) -> setTriggerChance(value), 0, 0, 115, 20, getTriggerChance()
        );
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
