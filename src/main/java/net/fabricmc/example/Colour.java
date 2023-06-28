package net.fabricmc.example;

public class Colour {

    private float r;
    private float g;
    private float b;

    public static final Colour RED = new Colour(1.0F, 0.0F, 0.0F);
    public static final Colour GREEN = new Colour(0.0F, 1.0F, 0.0F);
    public static final Colour BLUE = new Colour(0.0F, 0.0F, 1.0F);
    public static final Colour PURPLE = new Colour(1.0F, 0.0F, 1.0F);

    public Colour(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

}
