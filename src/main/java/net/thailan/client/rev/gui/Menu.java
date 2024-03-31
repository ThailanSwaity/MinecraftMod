package net.thailan.client.rev.gui;

import java.util.ArrayList;

public abstract class Menu implements MenuComponent{

    private ArrayList<MenuComponent> menuComponents;

    public void addComponent(MenuComponent menuComponent) {
        menuComponents.add(menuComponent);
    }

    public ArrayList<MenuComponent> getComponents() {
        return menuComponents;
    }

    public abstract void onClick();

}
