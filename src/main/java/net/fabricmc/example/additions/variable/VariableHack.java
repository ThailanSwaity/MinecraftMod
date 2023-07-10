package net.fabricmc.example.additions.variable;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.Options;
import net.fabricmc.example.additions.Hack;
import net.fabricmc.example.additions.Xray;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class VariableHack extends Hack {

    protected ArrayList<Hack> subHacks = new ArrayList<>();

    protected Options optionsScreen;
    public VariableHack(String name) {
        super(name);
        optionsScreen = new Options(Text.literal(name));
        optionsScreen.addOptionButton(Text.literal("<--"), button -> {
            ExampleMod.getInstance().client.setScreen(optionsScreen.getParentScreen());
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
            optionsScreen.addOptionButton(hack.getString(), button -> {
                hack.toggle();
                button.setMessage(hack.getString());
            }, 90, 20);
            optionsScreen.addOptionButton(Text.literal("Set"), button -> {
                ((VariableHack)hack).setOptionsScreenParent(this.getScreen());
                ExampleMod.getInstance().client.setScreen(((VariableHack)hack).getScreen());
            }, 15, 20);
        } else {
            optionsScreen.addOptionButton(hack.getString(), button -> {
                if (hack instanceof Xray) ((Xray)hack).cycle();
                else hack.toggle();
                button.setMessage(hack.getString());
            }, 115, 20);
        }
    }

    protected void initSubHacks() {
        for (Hack subHack : getSubHacks()) addSubHack(subHack);
    }
}
