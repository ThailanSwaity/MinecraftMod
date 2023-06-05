package net.fabricmc.example.gui;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.Teleport;
import net.fabricmc.example.additions.Hack;
import net.fabricmc.example.additions.Xray;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import org.lwjgl.openal.EXTStereoAngles;

import java.awt.*;
import java.util.ArrayList;

public class ModMenuScreen extends Screen {

    private Screen parentScreen;

    protected ModMenuScreen(Text title) {
        super(title);
    }

    public ModMenuScreen(Text title, Screen parent) {
        super(title);
        this.parentScreen = parent;
    }

    @Override
    protected void init() {
        this.initWidgets();
    }

    private void initWidgets() {
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().margin(4, 4, 4, 0);
        GridWidget.Adder adder = gridWidget.createAdder(2);

        ArrayList<Hack> hackList = ExampleMod.getInstance().additionManager.getAdditions();
        hackList.forEach(hack -> {
            ButtonWidget buttonWidget = createButtonWidget(Text.literal(hack.toString()), (button) -> {
                if (hack instanceof Xray) ((Xray) hack).cycle();
                else hack.toggle();
                button.setMessage(Text.literal(hack.toString()));
            }, 130, 20);
            adder.add(buttonWidget);
        });

        ButtonWidget teleportButton = ButtonWidget.builder(
                Text.literal(ExampleMod.teleport.toString()),
                button -> {
                    ExampleMod.teleport.teleport();
                }
        ).size(130, 20).build();

        adder.add(teleportButton);

        SliderWidget teleportDistanceSlider = new SliderWidget(0, 0, 130, 20, Text.literal("Tp " + ExampleMod.teleport.getDistance() + " blocks"), ((double)ExampleMod.teleport.getDistance() / 100)) {
            @Override
            protected void updateMessage() {
                setMessage(Text.literal("Tp " + (int)(value * 100) + " blocks"));
            }

            @Override
            protected void applyValue() {
                ExampleMod.teleport.setDistance((int)(value * 100));
                teleportButton.setMessage(Text.literal(ExampleMod.teleport.toString()));
            }
        };

        SliderWidget speedMineSlider = new SliderWidget(0, 0, 130, 20, Text.literal(String.format("%.2f", ExampleMod.speedMine.getTriggerChance())), ExampleMod.speedMine.getTriggerChance()) {
            @Override
            protected void updateMessage() {
                setMessage(Text.literal(String.format("%.2f", value)));
            }

            @Override
            protected void applyValue() {
                ExampleMod.speedMine.setTriggerChance(value);
            }
        };

        adder.add(speedMineSlider);

        adder.add(teleportDistanceSlider);

        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, 0, this.width, this.height, 0.5f, 0.25f);
        gridWidget.forEachChild(this::addDrawableChild);
    }

    public static ButtonWidget createButtonWidget(Text text, ButtonWidget.PressAction action, int width, int height) {
        return ButtonWidget.builder(text, action).size(width, height).build();
    }

    public static ButtonWidget createButtonWidget(Text text, ButtonWidget.PressAction action, int x, int y, int width, int height) {
        return ButtonWidget.builder(text, action).dimensions(x, y, width, height).build();
    }
}
