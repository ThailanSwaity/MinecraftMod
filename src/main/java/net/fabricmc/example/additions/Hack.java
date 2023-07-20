package net.fabricmc.example.additions;

import com.google.gson.JsonObject;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public abstract class Hack {

    private boolean enabled = false;
    protected String name;
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
        return Text.literal(name + " ").append((localEnabled() ? Text.literal("enabled").formatted(Formatting.GREEN) : Text.literal("disabled").formatted(Formatting.RED)));
    }

    public Hack getParent() {
        return parentHack;
    }

    public JsonObject toJSON() {
        JsonObject obj = new JsonObject();
        JsonObject objItem = new JsonObject();

        objItem.addProperty("name", getName());
        objItem.addProperty("enabled", localEnabled());
        obj.add("hack", objItem);
        return obj;
    }

    public void fromJSON(JsonObject jsonObject) {
        JsonObject objItem = jsonObject.getAsJsonObject("hack");
        if (!objItem.get("name").getAsString().equalsIgnoreCase(this.getName())) return;
        this.enabled = objItem.get("enabled").getAsBoolean();
    }

}
