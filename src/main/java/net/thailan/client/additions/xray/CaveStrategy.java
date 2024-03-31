package net.thailan.client.additions.xray;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class CaveStrategy extends XrayStrategy {

    public CaveStrategy() {
        this.name = "CaveStrategy";
        addBlocks(
                Blocks.DIRT,
                Blocks.GRASS_BLOCK,
                Blocks.SAND
        );
    }

    @Override
    public boolean blockIsVisible(Block block) {
        return !super.blockIsVisible(block);
    }

}
