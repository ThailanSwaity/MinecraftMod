package net.thailan.client.additions;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.thailan.client.additions.xray.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Xray extends Hack {

    private MinecraftClient client;
    private ArrayList<XrayStrategy> xrayStrategies;
    private XrayStrategy currentXrayStrategy;
    private int currentStrategy;

    public Xray(MinecraftClient client) {
        super("Xray");
        this.client = client;
        xrayStrategies = new ArrayList<>();
        xrayStrategies.add(new NullStrategy());
        xrayStrategies.add(new OreStrategy());
        xrayStrategies.add(new CaveStrategy());
        xrayStrategies.add(new SpawnerStrategy());

        currentXrayStrategy = xrayStrategies.get(0);
        currentStrategy = 0;
    }

    public void addStrategies(XrayStrategy... xrayStrategies) {
        Collections.addAll(this.xrayStrategies, xrayStrategies);
    }

    public void cycle() {
        currentStrategy++;
        currentStrategy %= xrayStrategies.size();
        currentXrayStrategy = xrayStrategies.get(currentStrategy);
        if (currentStrategy == 0) disable();
        else enable();
    }

    public boolean isEnabled() {
        return currentStrategy != 0;
    }

    public String getMode() {
        return currentXrayStrategy.getName();
    }

    public boolean isCulling() {
        return currentXrayStrategy.doCulling();
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
        return getMode();
    }

    @Override
    public Text getString() {
        if (!isEnabled()) return super.getString();
        String xrayString = toString();
        return Text.empty().append(xrayString).formatted(Formatting.GREEN);
    }

    public boolean blockIsVisible(Block block) {
        return currentXrayStrategy.blockIsVisible(block);
    }

}
