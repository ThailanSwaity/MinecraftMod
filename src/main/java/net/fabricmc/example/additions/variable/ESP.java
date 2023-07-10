package net.fabricmc.example.additions.variable;

import net.fabricmc.example.*;
import net.fabricmc.example.additions.*;
import net.minecraft.text.Text;

public class ESP extends VariableHack {

    private ChestESP chestESP;

    public ESP() {
        super("ESP");
        chestESP = new ChestESP(this);
        subHacks.add(chestESP);
        subHacks.add(new ShriekerESP(this));
        subHacks.add(new ShulkerESP(this));
        subHacks.add(new SignESP(this));

        initSubHacks();

        optionsScreen.addOptionSlider(
                Text.literal("Tracer lineWidth: " + String.format("%.2f", chestESP.getLineWidth())),
                (sliderWidget, value) -> {
                    sliderWidget.setMessage(Text.literal("Tracer lineWidth: " + String.format("%.2f", value * 5)));
                },
                (sliderWidget, value) -> {
                    for (Hack hack : subHacks) {
                        if (hack instanceof RenderedHack) {
                            ((RenderedHack)hack).setLineWidth((float)(value * 5));
                        }
                    }
                }, 0, 0 ,115, 20, chestESP.getLineWidth() / 5
        );

        optionsScreen.addOptionSlider(
                Text.literal("Tracer line alpha: " + String.format("%.2f", chestESP.getAlpha())),
                (sliderWidget, value) -> {
                    sliderWidget.setMessage(Text.literal("Tracer line alpha: " + String.format("%.2f", value)));
                },
                (sliderWidget, value) -> {
                    for (Hack hack : subHacks) {
                        if (hack instanceof RenderedHack) {
                            ((RenderedHack)hack).setAlpha((float)value);
                        }
                    }
                }, 0, 0, 115, 20, chestESP.getAlpha()
        );
    }

}
