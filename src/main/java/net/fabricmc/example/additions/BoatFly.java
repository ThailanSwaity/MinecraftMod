package net.fabricmc.example.additions;

import net.fabricmc.example.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class BoatFly extends Hack implements Tickable {

    private MinecraftClient client;
    private double speed = 0.15;
    private int ticksFloating = 0;

    public BoatFly(MinecraftClient client) {
        super("BoatFly");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled()) return;

        if (client.player.hasVehicle()) {
            ticksFloating++;
            Entity object = client.player.getVehicle();
            double tspeed = (client.options.sprintKey.isPressed() ? speed * 5 : speed);
            Vec3d flyVector = client.player.getRotationVector().multiply(tspeed);
            if (client.options.forwardKey.isPressed()) {
                object.setVelocity(object.getVelocity().add(flyVector.getX(), 0, flyVector.getZ()));
            }
            if (client.options.jumpKey.isPressed()) {
                object.setVelocity(object.getVelocity().add(0, tspeed, 0));

            }

            if (ticksFloating > 40) {
                object.setVelocity(object.getVelocity().getX(), -0.04D, object.getVelocity().getZ());

                ticksFloating = 0;
            }

        }
    }

}
