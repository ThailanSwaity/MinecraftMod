package net.thailan.client.additions;

import net.thailan.client.triggers.EntityHighlighter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.EndermanEntity;

public class EndermanESP extends Hack implements EntityHighlighter {
    public EndermanESP(Hack parentHack) {
        super("EndermanESP", parentHack);
    }

    @Override
    public boolean shouldHighlight(Entity entity) {
        return entity instanceof EndermanEntity;
    }
}
