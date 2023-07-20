package net.fabricmc.example.additions;

public class NoFog extends Hack {
    public NoFog() {
        super("NoFog");
    }

    public NoFog(Hack parentHack) {
        super("NoFog", parentHack);
    }

}
