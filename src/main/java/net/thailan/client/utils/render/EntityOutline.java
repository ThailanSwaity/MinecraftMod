package net.thailan.client.utils.render;

public class EntityOutline {

    public static Colour outlineColour = Colour.WHITE;

    public static Colour getOutlineColour() {
        return EntityOutline.outlineColour;
    }

    public static void setOutlineColour(Colour outlineColour) {
        EntityOutline.outlineColour = outlineColour;
    }

    public static void clear() {
        setOutlineColour(Colour.WHITE);
    }

}
