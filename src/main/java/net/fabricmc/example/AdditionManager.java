package net.fabricmc.example;

import net.fabricmc.example.additions.Hack;

import java.util.ArrayList;

public class AdditionManager {

    private ArrayList<Hack> additions = new ArrayList<>();

    public AdditionManager() {

    }

    public void add(Hack hack) {
        additions.add(hack);
    }

    public ArrayList<Hack> getAdditions() {
        return additions;
    }

}
