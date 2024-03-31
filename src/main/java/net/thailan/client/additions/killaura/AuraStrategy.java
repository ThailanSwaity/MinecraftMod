package net.thailan.client.additions.killaura;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

import java.util.Collections;
import java.util.HashSet;

public abstract class AuraStrategy {

    protected String name;

    public abstract boolean isTarget(Entity entity);

    public String getName() {
        return name;
    }
}
