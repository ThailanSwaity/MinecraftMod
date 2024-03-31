package net.thailan.client.rev.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class MenuScreen extends Screen {

    private final Screen parentScreen;
    private ArrayList<MenuScreen> subMenus = new ArrayList<>();

    public MenuScreen(Text title, Screen parentScreen) {
        super(title);
        this.parentScreen = parentScreen;
    }

    public void addChild(MenuScreen menuScreen) {
        subMenus.add(menuScreen);
    }

    public void addChild(MenuItem menuItem) {
        // TODO: Create menu buttons and stuff
    }

    @Override
    protected void init() {
        for (MenuScreen m : subMenus) {
            // TODO: Create menu buttons and stuff
        }
    }

    public Screen getParent() {
        return parentScreen;
    }
}
