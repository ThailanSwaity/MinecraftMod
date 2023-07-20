package net.fabricmc.example.additions;

import net.fabricmc.example.EntityHighlighter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;

public class DragonESP extends Hack implements EntityHighlighter {
    public DragonESP() {
        super("DragonESP");
    }

    public DragonESP(Hack parentHack) {
        super("DragonESP", parentHack);
    }

    @Override
    public boolean shouldHighlight(Entity entity) {
        return entity instanceof EnderDragonEntity;
    }
}
