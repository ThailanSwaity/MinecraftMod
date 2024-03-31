package net.thailan.client.additions.killaura;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;

import java.util.Collections;
import java.util.HashSet;

public abstract class SelectorAuraStrategy extends AuraStrategy {

    private final HashSet<Entity> targets = new HashSet<>();

    private void addEntities(Entity... entities) {
        Collections.addAll(targets, entities);
    }

    @Override
    public boolean isTarget(Entity entity) {
        return targets.contains(entity);
    }

}
