package net.thailan.client.additions.variable;

import com.google.gson.JsonObject;
import net.thailan.client.ThaiFoodClient;
import net.thailan.client.gui.widgets.Options;
import net.thailan.client.gui.widgets.OptionsSlider;
import net.thailan.client.additions.Hack;
import net.thailan.client.additions.Xray;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class VariableHack extends Hack {

    protected ArrayList<Hack> subHacks = new ArrayList<>();

    protected Options optionsScreen;
    public VariableHack(String name) {
        super(name);
        optionsScreen = new Options(Text.literal(name));
        optionsScreen.addOptionButton(Text.literal("<--"), button -> {
            ThaiFoodClient.getInstance().client.setScreen(optionsScreen.getParentScreen());
        }, 40, 20);
    }

    public void setOptionsScreenParent(Screen parentScreen) {
        optionsScreen.setParentScreen(parentScreen);
    }

    public Screen getScreen() {
        return optionsScreen;
    }

    public ArrayList<Hack> getSubHacks() {
        return subHacks;
    }

    private void addSubHack(Hack hack) {
        if (hack instanceof VariableHack) {
            optionsScreen.addOptionButton(hack, hack.getString(), button -> {
                hack.toggle();
                button.setMessage(hack.getString());
            }, 90, 20);
            optionsScreen.addOptionButton(Text.literal("Set"), button -> {
                ((VariableHack)hack).setOptionsScreenParent(this.getScreen());
                ThaiFoodClient.getInstance().client.setScreen(((VariableHack)hack).getScreen());
            }, 15, 20);
        } else {
            optionsScreen.addOptionButton(hack, hack.getString(), button -> {
                if (hack instanceof Xray) ((Xray)hack).cycle();
                else hack.toggle();
                button.setMessage(hack.getString());
            }, 115, 20);
        }
    }

    protected void initSubHacks() {
        for (Hack subHack : getSubHacks()) addSubHack(subHack);
    }

    @Override
    public JsonObject toJSON() {
        JsonObject obj = super.toJSON();
        JsonObject objItem = new JsonObject();

        ArrayList<Widget> widgets = optionsScreen.getWidgets();
        for (int i = 0; i < widgets.size(); i++) {
            if (widgets.get(i) instanceof OptionsSlider) {
                JsonObject sliderObj = ((OptionsSlider)widgets.get(i)).toJSON();
                objItem.add(((OptionsSlider)widgets.get(i)).getName(), sliderObj);
            }
        }
        obj.add("settings", objItem);
        return obj;
    }

    @Override
    public void fromJSON(JsonObject jsonObject) {
        super.fromJSON(jsonObject);
        JsonObject settingsObj = jsonObject.getAsJsonObject("settings");

        ArrayList<Widget> widgets = optionsScreen.getWidgets();
        for (int i = 0; i < widgets.size(); i++) {
            Widget widget = widgets.get(i);
            if (widget instanceof OptionsSlider) {
                JsonObject sliderObj = settingsObj.get(((OptionsSlider)widget).getName()).getAsJsonObject();
                ((OptionsSlider)widgets.get(i)).fromJSON(sliderObj);
            }
        }
    }

}
