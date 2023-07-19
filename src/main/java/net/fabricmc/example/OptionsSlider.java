package net.fabricmc.example;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public abstract class OptionsSlider extends SliderWidget {

    private String name;

    public OptionsSlider(String name, int x, int y, int width, int height, Text text, double value) {
        super(x, y, width, height, text, value);
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    private void setValue(double value) {
        double d = this.value;
        this.value = MathHelper.clamp(value, 0.0, 1.0);
        if (d != this.value) {
            this.applyValue();
        }
        this.updateMessage();
    }

    public String getName() {
        return name;
    }

    public JsonObject toJSON() {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", this.name);
        obj.addProperty("value", this.value);
        return obj;
    }

    public void fromJSON(JsonObject jsonObject) {
        if (!jsonObject.get("name").getAsString().equalsIgnoreCase(this.name)) return;
        setValue(jsonObject.get("value").getAsDouble());
    }
}
