package net.thailan.client.additions.killaura;

import net.minecraft.entity.Entity;

public class NoTargetStrategy extends AuraStrategy {

    public NoTargetStrategy() {
        this.name = this.getClass().getName();
    }

    public boolean isTarget(Entity entity) {
        return false;
    }

}
