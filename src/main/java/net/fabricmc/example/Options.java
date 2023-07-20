package net.fabricmc.example;

import net.fabricmc.example.additions.Hack;
import net.fabricmc.example.utils.MenuUtil;
import net.fabricmc.example.utils.SliderFunction;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class Options extends Screen {

    private ArrayList<Widget> widgets = new ArrayList<>();

    private Screen parentScreen;

    public Options(Text title) {
        super(title);
        this.parentScreen = parentScreen;
    }

    public void setParentScreen(Screen screen) {
        this.parentScreen = screen;
    }

    public Screen getParentScreen() {
        return parentScreen;
    }

    protected void init(){
        this.initWidgets();
    }

    private void initWidgets() {
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().margin(4, 4, 4, 0);
        GridWidget.Adder adder = gridWidget.createAdder(4);

        for (Widget widget : widgets) {
            adder.add(widget);
        }

        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, 0, this.width, this.height, 0.5f, 0.25f);
        gridWidget.forEachChild(this::addDrawableChild);

        refresh();
    }

    public void addOptionButton(Hack parentHack, Text text, ButtonWidget.PressAction action, int width, int height) {
        widgets.add(MenuUtil.createButtonWidget(parentHack, text, action, width, height));
    }

    public void addOptionButton(Text text, ButtonWidget.PressAction action, int width, int height) {
        widgets.add(MenuUtil.createButtonWidget(text, action, width, height));
    }

    public void addOptionButton(Text text, ButtonWidget.PressAction action, int x, int y, int width, int height) {
        widgets.add(MenuUtil.createButtonWidget(text, action, x, y, width, height));
    }

    public void addOptionSlider(String name, Text text, SliderFunction update, SliderFunction apply, int x, int y, int width, int height, double value) {
        widgets.add(MenuUtil.createSliderWidget(name, text, update, apply, x, y, width, height, value));
    }

    public ArrayList<Widget> getWidgets() {
        return widgets;
    }

    private void refresh() {
        for (Widget widget : widgets) {
            if (widget instanceof OptionsButton) {
                ((OptionsButton)widget).refresh();
            }
        }
    }

}
