package net.thailan.client.additions.variable;

import net.thailan.client.additions.NoFog;
import net.minecraft.text.Text;

public class Fog extends VariableHack {

    protected float fogStart = 0.0f;
    protected float fogEnd = 1.0f;
    protected NoFog noFog;

    public Fog() {
        super("Fog");

        noFog = new NoFog(this);
        subHacks.add(noFog);

        initSubHacks();

        optionsScreen.addOptionSlider("fogStart",
                Text.literal("Fog start: " + String.format("%.2f", fogStart)),
                (sliderWidget, value) -> sliderWidget.setMessage(Text.literal("Fog start: " + String.format("%.2f", (float)value * 400 - 200))),
                (sliderWidget, value) -> this.fogStart = (float)value * 400 - 200, 0, 0, 230, 20, (fogStart + 200) / 400);

        optionsScreen.addOptionSlider("fogEnd",
                Text.literal("Fog end: " + String.format("%.2f", fogEnd)),
                (sliderWidget, value) -> sliderWidget.setMessage(Text.literal("Fog end: " + String.format("%.2f", (float)value * 400 - 200))),
                (sliderWidget, value) -> this.fogEnd = (float)value * 400 - 200, 0, 0, 230, 20, (fogEnd + 200) / 400);
    }

    public float getFogStart() {
        return fogStart;
    }

    public float getFogEnd() {
        return fogEnd;
    }

    public boolean bypassFog() {
        return noFog.isEnabled();
    }

}
