package net.fabricmc.example.utils;

import net.fabricmc.example.OptionsSlider;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import org.apache.logging.log4j.util.LambdaUtil;

public class MenuUtil {

    public static ButtonWidget createButtonWidget(Text text, ButtonWidget.PressAction action, int width, int height) {
        return ButtonWidget.builder(text, action).size(width, height).build();
    }

    public static ButtonWidget createButtonWidget(Text text, ButtonWidget.PressAction action, int x, int y, int width, int height) {
        return ButtonWidget.builder(text, action).dimensions(x, y, width, height).build();
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
