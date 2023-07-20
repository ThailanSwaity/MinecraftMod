package net.thailan.client.additions.variable;

import net.thailan.client.triggers.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class Speed extends VariableHack implements Tickable {

    private MinecraftClient client;
    private double walkSpeed = 0.1;

    public Speed(MinecraftClient client) {
        super("Speed");
        this.client = client;

        optionsScreen.addOptionSlider("walkSpeed", Text.literal(String.format("%.2f", walkSpeed)),
                (sliderWidget, value) -> {
                    sliderWidget.setMessage(Text.literal(String.format("%.2f", value)));
                },
                (sliderWidget, value) -> {
                    walkSpeed = value;
                }, 0, 0, 115, 20, walkSpeed);
    }

    @Override
    public void tick() {
        if (!isEnabled()) return;

        if (!client.player.isOnGround()) return;

        Vec3d velocity;

        if (client.options.forwardKey.isPressed()) {
            velocity = client.player.getVelocity();
            Vec3d t = client.player.getRotationVec(0.5f).multiply(walkSpeed);
            client.player.setVelocity(velocity.getX() + t.getX(), velocity.getY(), velocity.getZ() + t.getZ());
        }
        if (client.options.backKey.isPressed()) {
            velocity = client.player.getVelocity();
            Vec3d t = client.player.getRotationVec(0.5f).multiply(walkSpeed * -1);
            client.player.setVelocity(velocity.getX() + t.getX(), velocity.getY(), velocity.getZ() + t.getZ());
        }
        if (client.options.leftKey.isPressed()) {
            velocity = client.player.getVelocity();
            Vec3d t = client.player.getRotationVec(0.5f).rotateY(-55f).multiply(walkSpeed);
            client.player.setVelocity(velocity.getX() + t.getX(), velocity.getY(), velocity.getZ() + t.getZ());
        }
        if (client.options.rightKey.isPressed()) {
            velocity = client.player.getVelocity();
            Vec3d t = client.player.getRotationVec(0.5f).rotateY(55f).multiply(walkSpeed);
            client.player.setVelocity(velocity.getX() + t.getX(), velocity.getY(), velocity.getZ() + t.getZ());
        }
    }
}
