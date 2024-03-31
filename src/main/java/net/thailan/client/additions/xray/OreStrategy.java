package net.thailan.client.additions.xray;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class OreStrategy extends XrayStrategy {

    public OreStrategy() {
        this.name = "OreStrategy";
        this.cull = false;
        addBlocks(
                Blocks.STONE,
                Blocks.DEEPSLATE,
                Blocks.DIRT,
                Blocks.GRASS_BLOCK,
                Blocks.SAND,
                Blocks.NETHERRACK,
                Blocks.ANDESITE,
                Blocks.DIORITE,
                Blocks.GRANITE,
                Blocks.BASALT,
                Blocks.BLACKSTONE
        );
    }

    @Override
    public boolean blockIsVisible(Block block) {
        return !super.blockIsVisible(block);
    }

}
