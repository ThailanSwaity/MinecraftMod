package net.fabricmc.example.additions.variable;

import net.fabricmc.example.Colour;
import net.fabricmc.example.additions.*;
import net.minecraft.text.Text;

public class ESP extends VariableHack {

    private ChestESP chestESP;
    private float r = 1;
    private float g = 1;
    private float b = 1;

    public ESP() {
        super("ESP");
        chestESP = new ChestESP(this);
        subHacks.add(chestESP);
        subHacks.add(new ShriekerESP(this));
        subHacks.add(new ShulkerESP(this));
        subHacks.add(new SignESP(this));
        subHacks.add(new ItemESP(this));
        subHacks.add(new PlayerESP(this));
        subHacks.add(new EndCrystalESP(this));
        subHacks.add(new CatalystESP(this));
        subHacks.add(new WardenESP(this));
        subHacks.add(new DragonESP(this));

        initSubHacks();

        optionsScreen.addOptionSlider("lineWidth",
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

        optionsScreen.addOptionSlider("lineAlpha",
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

        optionsScreen.addOptionSlider("red",
                Text.literal("Red: " + String.format("%.2f", r)),
                (sliderWidget, value) -> {
                    sliderWidget.setMessage(Text.literal("Red: " + String.format("%.2f", value)));
                },
                (sliderWidget, value) -> {
                    this.r = (float) value;
                }, 0, 0, 115, 20, this.r
        );

        optionsScreen.addOptionSlider("green",
                Text.literal("Green: " + String.format("%.2f", g)),
                (sliderWidget, value) -> {
                    sliderWidget.setMessage(Text.literal("Green: " + String.format("%.2f", value)));
                },
                (sliderWidget, value) -> {
                    this.g = (float) value;
                }, 0, 0, 115, 20, this.g
        );

        optionsScreen.addOptionSlider("blue",
                Text.literal("Blue: " + String.format("%.2f", b)),
                (sliderWidget, value) -> {
                    sliderWidget.setMessage(Text.literal("Red: " + String.format("%.2f", value)));
                },
                (sliderWidget, value) -> {
                    this.b = (float) value;
                }, 0, 0, 115, 20, this.b
        );
    }

    public Colour getPlayerOutlineColour() {
        return new Colour(r, g, b);
    }

}
