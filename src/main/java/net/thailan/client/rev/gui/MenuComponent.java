package net.thailan.client.rev.gui;

public interface MenuComponent {

    void accept(MenuVisitor mv);
    void onClick();
}
