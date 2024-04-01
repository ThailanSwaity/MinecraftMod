package net.thailan.client.additions.killaura;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public abstract class AuraStrategy {

    protected String name;
    protected final MinecraftClient client = MinecraftClient.getInstance();

    public abstract boolean isTarget(Entity entity);
    public abstract void doKilling(Iterable<Entity> entities);

    public String getName() {
        return name;
    }
}
