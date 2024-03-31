package net.thailan.client.additions.xray;

import net.minecraft.block.Blocks;

public class SpawnerStrategy extends XrayStrategy {

    public SpawnerStrategy() {
        this.name = "SpawnerStrategy";
        addBlocks(
                Blocks.COBBLESTONE,
                Blocks.MOSSY_COBBLESTONE,
                Blocks.SPAWNER,
                Blocks.INFESTED_COBBLESTONE
        );
    }

}
