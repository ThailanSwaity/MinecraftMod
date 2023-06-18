package net.fabricmc.example.additions;

import net.fabricmc.example.Tickable;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;

public class KillAura extends Hack implements Tickable {

    private static final int PLAYERS = 1;
    private static final int PASSIVES = 2;
    private static final int HOSTILES = 3;
    private static final int PLAYERS_AND_HOSTILES = 4;
    private static final int HOSTILES_AND_PASSIVES = 5;
    private static final int ALL = 6;
    private int mode = 0;
    private MinecraftClient client;

    public KillAura(MinecraftClient client) {
        super("KillAura");
        this.client = client;
    }

    @Override
    public void toggle() {
        if (++mode > 6) mode = 0;
        if (mode != 0) enable();
        else disable();
    }

    public void tick() {
        if (!isEnabled()) return;

        if (client.player.getAttackCooldownProgress(0.5f) < 1.0) return;
        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player || entity instanceof ZombifiedPiglinEntity) continue;
            double dist = client.player.getPos().distanceTo(entity.getPos());
            if (dist > client.interactionManager.getReachDistance()) continue;
            if (entity == null) return;
            if (entity instanceof Monster && (mode == HOSTILES || mode == HOSTILES_AND_PASSIVES || mode == PLAYERS_AND_HOSTILES)) {
                client.interactionManager.attackEntity(client.player, entity);
                return;
            }
            if (entity instanceof PassiveEntity && (mode == PASSIVES || mode == HOSTILES_AND_PASSIVES)) {
                client.interactionManager.attackEntity(client.player, entity);
                return;
            }
            if (entity instanceof PlayerEntity && (mode == PLAYERS || mode == PLAYERS_AND_HOSTILES)) {
                client.interactionManager.attackEntity(client.player, entity);
                return;
            }
            if (entity instanceof LivingEntity && mode == ALL) {
                client.interactionManager.attackEntity(client.player, entity);
                return;
            }
        }
    }

    @Override
    public String toString() {
        if (mode == 0) return super.toString();
        else if (mode == PLAYERS) return name + ": PLAYERS";
        else if (mode == PASSIVES) return name + ": PASSIVES";
        else if (mode == HOSTILES) return name + ": HOSTILES";
        else if (mode == PLAYERS_AND_HOSTILES) return name + ": PLAYERS_AND_HOSTILES";
        else if (mode == HOSTILES_AND_PASSIVES) return name + ": HOSTILES_AND_PASSIVES";
        else if (mode == ALL) return name + ": ALL";
        return "Error";
    }

}
