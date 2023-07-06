package net.fabricmc.example.additions;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

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

    public String getName() {
        return name;
    }

    public Text getString() {
        if (!isEnabled()) return Text.literal(toString());
        return Text.literal(toString()).formatted(Formatting.GREEN);
    }

}
