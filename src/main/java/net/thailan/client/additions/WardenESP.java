package net.thailan.client.additions;

import net.thailan.client.triggers.EntityHighlighter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WardenEntity;

public class WardenESP extends Hack implements EntityHighlighter {
    public WardenESP() {
        super("WardenESP");
    }

    public WardenESP(Hack parentHack) {
        super("WardenESP", parentHack);
    }

    @Override
    public boolean shouldHighlight(Entity entity) {
        return entity instanceof WardenEntity;
    }
}
