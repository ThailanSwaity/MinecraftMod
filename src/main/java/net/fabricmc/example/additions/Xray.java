package net.fabricmc.example.additions;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;

import java.util.HashSet;

public class Xray extends Hack {

    private MinecraftClient client;
    private HashSet<Block> hiddenBlocks = new HashSet<>();
    private HashSet<Block> caveHiddenBlocks = new HashSet<>();
    private HashSet<Block> spawnerShownBlocks = new HashSet<>();
    private int mode = -1;
    public static final int ORE_MODE = 0;
    public static final int CAVE_MODE = 1;
    public static final int SPAWNER_MODE = 2;

    public Xray(MinecraftClient client) {
        super("Xray");
        this.client = client;
    }

    public void addBlocksORE(Block... blocks) {
        for (Block block : blocks) {
            hiddenBlocks.add(block);
        }
    }

    public void addBlocksCAVE(Block... blocks) {
        for (Block block : blocks) {
            caveHiddenBlocks.add(block);
        }
    }

    public void addBlocksSPAWNER(Block... blocks) {
        for (Block block : blocks) {
            spawnerShownBlocks.add(block);
        }
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void cycle() {
        mode++;
        if (mode > 2) {
            disable();
            mode = -1;
        } else enable();
    }

    public boolean isEnabled() {
        return mode != -1;
    }

    public int getMode() {
        return  mode;
    }

    @Override
    protected void onEnable() {
        client.chunkCullingEnabled = false;
        client.worldRenderer.reload();
    }

    @Override
    protected void onDisable() {
        client.chunkCullingEnabled = true;
        client.worldRenderer.reload();
    }

    @Override
    public String toString() {
        String xrayString = "";
        if (!isEnabled()) xrayString = "Xray disabled";
        else if (getMode() == Xray.ORE_MODE) xrayString = "Xray showing ORE";
        else if (getMode() == Xray.CAVE_MODE) xrayString = "Xray showing CAVES";
        else if (getMode() == Xray.SPAWNER_MODE) xrayString = "Xray showing SPAWNERS";
        return xrayString;
    }

    public boolean blockIsVisible(Block block) {
        if (mode == ORE_MODE) return !hiddenBlocks.contains(block);
        else if (mode == SPAWNER_MODE) return spawnerShownBlocks.contains(block);
        return !caveHiddenBlocks.contains(block);
    }

}
