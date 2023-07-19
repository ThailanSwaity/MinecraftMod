package net.fabricmc.example.additions.variable;

import net.fabricmc.example.additions.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

public class Tracers extends VariableHack {

    private DetectPlayers detectPlayers;
    public Tracers(MinecraftClient client) {
        super("Tracers");

        detectPlayers = new DetectPlayers(client, this);
        subHacks.add(detectPlayers);
        subHacks.add(new PortalTracers(this));
        subHacks.add(new ChestTracers(this));
        subHacks.add(new ItemTracers(this));
        subHacks.add(new EndCrystalTracers(this));

        initSubHacks();

        optionsScreen.addOptionSlider("lineWidth",
                Text.literal("Tracer lineWidth: " + String.format("%.2f", detectPlayers.getLineWidth())),
                (sliderWidget, value) -> {
                    sliderWidget.setMessage(Text.literal("Tracer lineWidth: " + String.format("%.2f", value * 5)));
                },
                (sliderWidget, value) -> {
                    for (Hack hack : subHacks) {
                        if (hack instanceof RenderedHack) {
                            ((RenderedHack)hack).setLineWidth((float)(value * 5));
                        }
                    }
                }, 0, 0 ,115, 20, detectPlayers.getLineWidth() / 5
        );

        optionsScreen.addOptionSlider("lineAlpha",
                Text.literal("Tracer line alpha: " + String.format("%.2f", detectPlayers.getAlpha())),
                (sliderWidget, value) -> {
                    sliderWidget.setMessage(Text.literal("Tracer line alpha: " + String.format("%.2f", value)));
                },
                (sliderWidget, value) -> {
                    for (Hack hack : subHacks) {
                        if (hack instanceof RenderedHack) {
                            ((RenderedHack)hack).setAlpha((float)value);
                        }
                    }
                }, 0, 0, 115, 20, detectPlayers.getAlpha()
        );
    }

    public boolean isDetectingPlayers() {
        return detectPlayers.isEnabled();
    }
}
