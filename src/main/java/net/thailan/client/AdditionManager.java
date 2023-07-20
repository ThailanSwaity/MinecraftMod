package net.thailan.client;

import net.thailan.client.additions.Hack;
import net.thailan.client.additions.variable.VariableHack;
import net.thailan.client.triggers.BlockDetector;
import net.thailan.client.triggers.BlockEntityDetector;
import net.thailan.client.triggers.EntityDetector;
import net.thailan.client.triggers.EntityHighlighter;

import java.util.ArrayList;

public class AdditionManager {

    private ArrayList<Hack> additions = new ArrayList<>();
    private ArrayList<Hack> blockDetectors = new ArrayList<>();
    private ArrayList<Hack> blockEntityDetectors = new ArrayList<>();
    private ArrayList<Hack> entityDetectors = new ArrayList<>();
    private ArrayList<Hack> entityHighlighters = new ArrayList<>();
    private ArrayList<Hack> displayHacks = new ArrayList<>();

    public AdditionManager() {

    }

    private void add(Hack hack) {
        additions.add(hack);
        if (hack instanceof VariableHack) for (Hack subHack : ((VariableHack) hack).getSubHacks()) add(subHack);
        if (hack instanceof BlockDetector) blockDetectors.add(hack);
        if (hack instanceof BlockEntityDetector) blockEntityDetectors.add(hack);
        if (hack instanceof EntityDetector) entityDetectors.add(hack);
        if (hack instanceof EntityHighlighter) entityHighlighters.add(hack);
    }

    public void addDisplay(Hack hack) {
        displayHacks.add(hack);
        add(hack);
    }

    public ArrayList<Hack> getAdditions() {
        return additions;
    }
    public ArrayList<Hack> getBlockDetectors() {
        return blockDetectors;
    }
    public ArrayList<Hack> getBlockEntityDetectors() {
        return blockEntityDetectors;
    }
    public ArrayList<Hack> getEntityDetectors() {
        return entityDetectors;
    }
    public ArrayList<Hack> getEntityHighlighters() {
        return entityHighlighters;
    }
    public ArrayList<Hack> getDisplayHacks() {
        return displayHacks;
    }

}
