package net.fabricmc.example.additions;

import net.fabricmc.example.EntityHighlighter;
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
