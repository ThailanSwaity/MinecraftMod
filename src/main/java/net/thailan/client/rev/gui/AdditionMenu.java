package net.thailan.client.rev.gui;

public class AdditionMenu extends Menu {

    private final String name;

    public AdditionMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void accept(MenuVisitor mv) {
        mv.visit(this);
    }

    @Override
    public void onClick() {

    }
}
