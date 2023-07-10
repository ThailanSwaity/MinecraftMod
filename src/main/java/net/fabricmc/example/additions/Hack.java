package net.fabricmc.example.additions;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

public abstract class Hack {

    private boolean enabled = false;
    protected String name = "";
    protected Hack parentHack;

    public Hack(String name) {
        this.name = name;
    }
    public Hack(String name, Hack parentHack) {
        this(name);
        this.parentHack = parentHack;
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
        if (getParent() != null) {
            return enabled && getParent().isEnabled();
        }
        return enabled;
    }

    public boolean localEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public Text getString() {
        if (!localEnabled()) return Text.literal(toString());
        return Text.literal(toString()).formatted(Formatting.GREEN);
    }

    public Hack getParent() {
        return parentHack;
    }

}
