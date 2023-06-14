package net.fabricmc.example.additions;

public abstract class Hack {

    private boolean enabled = false;
    protected String name = "";

    public Hack(String name) {
        this.name = name;
    }

    public String toString() {
        return name + (isEnabled() ? " enabled" : " disabled");
    }

    public void toggle() {
        if (enabled) {
            enabled = false;
            onDisable();
        } else {
            enabled = true;
            onEnable();
        }
    }

    protected void disable() {
        enabled = false;
        onDisable();
    }

    protected void enable() {
        enabled = true;
        onEnable();
    }

    protected void onEnable() {

    }

    protected void onDisable() {

    }

    public boolean isEnabled() {
        return enabled;
    }

}
