package net.fabricmc.example;

import net.fabricmc.example.additions.Hack;

import java.util.ArrayList;

public class AdditionManager {

    private ArrayList<Hack> additions = new ArrayList<>();
    private ArrayList<Hack> blockDetectors = new ArrayList<>();
    private ArrayList<Hack> blockEntityDetectors = new ArrayList<>();

    public AdditionManager() {

    }

    public void add(Hack hack) {
        additions.add(hack);
        if (hack instanceof BlockDetector) blockDetectors.add(hack);
        if (hack instanceof BlockEntityDetector) blockEntityDetectors.add(hack);
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

}
