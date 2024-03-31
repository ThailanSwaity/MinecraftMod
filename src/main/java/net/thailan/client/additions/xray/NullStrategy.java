package net.thailan.client.additions.xray;

import net.minecraft.block.Block;

public class NullStrategy extends OreStrategy {

    public NullStrategy() {
        this.name = "NullStrategy";
    }

    public boolean blockIsVisible(Block block) {
        return true;
    }

}
