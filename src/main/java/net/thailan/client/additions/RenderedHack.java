package net.thailan.client.additions;

public abstract class RenderedHack extends Hack {

    protected float lineWidth = 1f;
    protected float alpha = 1f;

    public RenderedHack(String name) {
        super(name);
    }

    public RenderedHack(String name, Hack parentHack) {
        super(name, parentHack);
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

}
