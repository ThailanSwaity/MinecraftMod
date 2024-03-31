package net.thailan.client.additions.xray;

import net.minecraft.block.Block;

import java.util.Collections;
import java.util.HashSet;

public abstract class XrayStrategy {

    private final HashSet<Block> visibleBlocks = new HashSet<>();
    protected boolean cull = true;
    protected String name;

    protected void addBlocks(Block... blocks) {
        Collections.addAll(visibleBlocks, blocks);
    }

    public boolean blockIsVisible(Block block) {
        return visibleBlocks.contains(block);
    }

    public String getName() {
        return name;
    }

    public boolean doCulling() {
        return cull;
    }

}
