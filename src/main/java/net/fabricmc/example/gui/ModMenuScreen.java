package net.fabricmc.example.gui;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.additions.Hack;
import net.fabricmc.example.additions.variable.VariableHack;
import net.fabricmc.example.additions.Xray;
import net.fabricmc.example.utils.MenuUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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
        GridWidget.Adder adder = gridWidget.createAdder(4);

        ArrayList<Hack> hackList = ExampleMod.getInstance().additionManager.getDisplayHacks();
        hackList.forEach(hack -> {
            if (hack instanceof VariableHack) {
                ButtonWidget buttonWidget = MenuUtil.createButtonWidget(hack.getString(), (button) -> {
                    hack.toggle();
                    button.setMessage(hack.getString());
                }, 115, 20);
                ButtonWidget optionsWidget = MenuUtil.createButtonWidget(Text.literal(hack.getName() + " settings").formatted(Formatting.BOLD), (button) -> {
                    ((VariableHack)hack).setOptionsScreenParent(this);
                    client.setScreen(((VariableHack)hack).getScreen());
                }, 115, 20);
                adder.add(buttonWidget);
                adder.add(optionsWidget);
            } else {
                ButtonWidget buttonWidget = MenuUtil.createButtonWidget(hack.getString(), (button) -> {
                    if (hack instanceof Xray) ((Xray) hack).cycle();
                    else hack.toggle();
                    button.setMessage(hack.getString());
                }, 115, 20);
                adder.add(buttonWidget);
            }
        });

        ButtonWidget teleportButton = ButtonWidget.builder(
                Text.literal("Teleport " + ExampleMod.teleport.getDistance() + " blocks"),
                button -> {
                    ExampleMod.teleport.teleport();
                }
        ).size(115, 20).build();

        adder.add(teleportButton);

        SliderWidget teleportDistanceSlider = new SliderWidget(0, 0, 115, 20, Text.literal("Tp " + ExampleMod.teleport.getDistance() + " blocks"), ((double)ExampleMod.teleport.getDistance() / 100)) {
            @Override
            protected void updateMessage() {
                setMessage(Text.literal("Tp " + (int)(value * 100) + " blocks"));
            }

            @Override
            protected void applyValue() {
                ExampleMod.teleport.setDistance((int)(value * 100));
                teleportButton.setMessage(Text.literal("Teleport " + ExampleMod.teleport.getDistance() + " blocks"));
            }
        };

//        SliderWidget speedMineSlider = MenuUtil.createSliderWidget(Text.literal(String.format("%.2f", ExampleMod.speedMine.getTriggerChance())),
//                (sliderWidget, value) -> {
//                    sliderWidget.setMessage(Text.literal(String.format("%.2f", value)));
//                },
//                (sliderWidget, value) -> {
//                    ExampleMod.speedMine.setTriggerChance(value);
//                }, 0, 0, 115, 20, ExampleMod.speedMine.getTriggerChance());

//        SliderWidget flySpeedSlider = MenuUtil.createSliderWidget(Text.literal(String.format("%.2f", ExampleMod.fly.getFlySpeed())),
//                (sliderWidget, value) -> {
//                    sliderWidget.setMessage(Text.literal(String.format("%.2f", value)));
//                },
//                (sliderWidget, value) -> {
//                    ExampleMod.fly.setFlySpeed((float)value);
//                }, 0, 0, 115, 20, ExampleMod.fly.getFlySpeed());

//        adder.add(speedMineSlider);

        adder.add(teleportDistanceSlider);

//        adder.add(flySpeedSlider);

        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, 0, this.width, this.height, 0.5f, 0.25f);
        gridWidget.forEachChild(this::addDrawableChild);
    }

}
