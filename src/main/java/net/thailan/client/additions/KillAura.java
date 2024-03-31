package net.thailan.client.additions;

import net.thailan.client.additions.killaura.*;
import net.thailan.client.triggers.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

public class KillAura extends Hack implements Tickable {

    private ArrayList<AuraStrategy> auraStrategies;
    private AuraStrategy currentAuraStrategy;
    private int currentStrategy;
    private MinecraftClient client;

    public KillAura(MinecraftClient client) {
        super("KillAura");
        this.client = client;
        auraStrategies = new ArrayList<>();
        auraStrategies.add(new NoTargetStrategy());
        auraStrategies.add(new HostileStrategy());
        auraStrategies.add(new HostilePassiveStrategy());
        auraStrategies.add(new PassiveStrategy());
        auraStrategies.add(new PlayerStrategy());
        auraStrategies.add(new PlayerAndHostileStrategy());
        auraStrategies.add(new KillEverythingStrategy());
        currentStrategy = 0;
        currentAuraStrategy = auraStrategies.get(currentStrategy);
    }

    @Override
    public void toggle() {
        currentStrategy++;
        currentStrategy %= auraStrategies.size();
        currentAuraStrategy = auraStrategies.get(currentStrategy);
        if (currentStrategy == 0) disable();
        else enable();
    }

    public void tick() {
        if (!isEnabled()) return;

        if (client.player.getAttackCooldownProgress(0.5f) < 1.0) return;
        for (Entity entity : client.world.getEntities()) {
            if (entity == null) continue;
            if (entity == client.player || entity instanceof ZombifiedPiglinEntity) continue;
            double dist = client.player.getPos().distanceTo(entity.getPos());
            if (dist > client.interactionManager.getReachDistance()) continue;

            if (currentAuraStrategy.isTarget(entity)) {
                client.interactionManager.attackEntity(client.player, entity);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return currentAuraStrategy.getName();
    }

    @Override
    public Text getString() {
        if (currentStrategy == 0) return super.getString();
        String text = toString();
        return Text.empty().append(text).formatted(Formatting.GREEN);
    }

}
