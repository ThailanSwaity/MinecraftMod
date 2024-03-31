package net.thailan.client.rev.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class MenuConstructorVisitor implements MenuVisitor {

    private final MenuScreen rootMenu;
    private MenuScreen currentMenu;

    public MenuConstructorVisitor(Text title, Screen parentScreen) {
        rootMenu = new MenuScreen(title, parentScreen);
        currentMenu = rootMenu;
    }

    @Override
    public void visit(AdditionMenu am) {
        MenuScreen ms = new MenuScreen(Text.literal(am.getName()), currentMenu);
        currentMenu.addChild(ms);
        currentMenu = ms;
        for (MenuComponent menuComponent : am.getComponents()) {
            menuComponent.accept(this);
        }

        // The casting here is okay because any parent in this heirarchy will always be a MenuScreen.
        // The MenuScreen getParent() only returns a Screen because rootMenu will never be a MenuScreen
        currentMenu = (MenuScreen)ms.getParent();
    }

    @Override
    public void visit(AdditionMenuItem ami) {
        currentMenu.addChild(ami);
    }

    public MenuScreen getRootMenu() {
        return rootMenu;
    }
}
