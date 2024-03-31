package net.thailan.client.rev.addition;

import net.thailan.client.rev.Setting;

import java.util.ArrayList;

public abstract class Addition {

    private boolean enabled;
    private String name;
    private final ArrayList<? extends Setting> settings;

    public Addition(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
        settings = new ArrayList<>();
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public String getName() {
        return name;
    }

    public ArrayList<? extends Setting> getSettings() {
        return settings;
    }

}
