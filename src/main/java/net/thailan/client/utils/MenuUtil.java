package net.thailan.client.utils;

import net.thailan.client.gui.widgets.OptionsButton;
import net.thailan.client.gui.widgets.OptionsSlider;
import net.thailan.client.additions.Hack;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class MenuUtil {

    public static ButtonWidget createButtonWidget(Text text, ButtonWidget.PressAction action, int width, int height) {
        return ButtonWidget.builder(text, action).size(width, height).build();
    }

    public static ButtonWidget createButtonWidget(Text text, ButtonWidget.PressAction action, int x, int y, int width, int height) {
        return ButtonWidget.builder(text, action).dimensions(x, y, width, height).build();
    }

    public static ButtonWidget createButtonWidget(Hack parentHack, Text text, ButtonWidget.PressAction action, int width, int height) {
        return OptionsButton.optionsBuilder(text, action).size(width, height).parent(parentHack).build();
    }

    public static SliderWidget createSliderWidget(String name, Text text, SliderFunction update, SliderFunction apply, int x, int y, int width, int height, double value) {
        return new OptionsSlider(name, x, y, width, height, text, value) {
            @Override
            protected void updateMessage() {
                update.method(this, value);
            }

            @Override
            protected void applyValue() {
                apply.method(this, value);
            }
        };
    }

}
