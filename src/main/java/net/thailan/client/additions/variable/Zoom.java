package net.thailan.client.additions.variable;

import net.thailan.client.triggers.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

public class Zoom extends VariableHack implements Tickable {

    private MinecraftClient client;
    private boolean zoomed = false;
    private int prevFOV;
    private double prevSens;
    private int newFOV = 90;
    private double newSens = 1;

    private static SimpleOption<Integer> fov_bypass;

    public Zoom(MinecraftClient client) {
        super("Zoom");
        this.client = client;

        optionsScreen.addOptionSlider("zoomFOV", Text.literal("Zoom FOV: " + newFOV),
                (sliderWidget, value) -> {
                    sliderWidget.setMessage(Text.literal("Zoom FOV: " + (int)Math.floor(value * 360)));
                },
                (sliderWidget, value) -> {
                    newFOV = (int)Math.floor(value * 360);
                }, 0, 0, 115, 20, newFOV / 360);

        fov_bypass = new SimpleOption<>(
            "options.fov", SimpleOption.emptyTooltip(), (optionText, value) -> null, SimpleOption.DoubleSliderCallbacks.INSTANCE.withModifier(d -> newFOV, d -> newFOV),
            newFOV, value -> {}
        );
    }

    @Override
    public void tick() {
        if (client.options.pickItemKey.isPressed()) {
            if (!isEnabled()) enable();
        } else if (isEnabled()) disable();
    }

    @Override
    protected void onEnable() {
        prevFOV = client.options.getFov().getValue();
        prevSens = client.options.getMouseSensitivity().getValue();
        client.options.getFov().setValue(newFOV);
        client.options.getMouseSensitivity().setValue(newSens);
    }

    @Override
    protected void onDisable() {
        client.options.getFov().setValue(prevFOV);
        client.options.getMouseSensitivity().setValue(prevSens);
    }
}
