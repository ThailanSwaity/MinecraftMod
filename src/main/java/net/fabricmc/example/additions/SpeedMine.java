package net.fabricmc.example.additions;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.Tickable;
import net.fabricmc.example.additions.Hack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class SpeedMine extends Hack implements Tickable {

    private int cooldown = -1;
    private int maxCoolDown = 15;

    public SpeedMine() {
        super("SpeedMine");
    }

    public void tick() {
        if (!isEnabled()) return;
        MinecraftClient client = ExampleMod.getInstance().client;
        client.player.setStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 30, 2), client.player);

        if (cooldown >= 0) cooldown--;
    }

    public void resetCooldown() {
        cooldown = maxCoolDown;
    }

    public int getCooldown() {
        return cooldown;
    }

}
