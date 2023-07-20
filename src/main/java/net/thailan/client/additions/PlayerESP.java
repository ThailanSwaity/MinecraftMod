package net.thailan.client.additions;

public class PlayerESP extends RenderedHack{
    public PlayerESP() {
        super("PlayerESP");
    }

    public PlayerESP(Hack parentHack) {
        super("PlayerESP", parentHack);
    }

//    @Override
//    public boolean isEntity(Entity entity) {
//        return entity instanceof PlayerEntity && entity != ExampleMod.getInstance().client.player;
//    }
//
//    @Override
//    public void entityResponse(Entity entity, Camera camera) {
//        Renderer.drawBoxOutline(entity.getBoundingBox(), Colour.WHITE, lineWidth, alpha);
//    }
}
