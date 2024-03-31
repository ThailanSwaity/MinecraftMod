package net.thailan.client.rev.gui;

import net.thailan.client.rev.addition.Addition;

public class AdditionMenuItem extends MenuItem {

    private final Addition addition;
    private final String name;

    public AdditionMenuItem(Addition addition) {
        this.addition = addition;
        this.name = addition.getName();
    }

    public void accept(MenuVisitor mv) {
        mv.visit(this);
    }

    @Override
    public void onClick() {

    }
}
