package net.thailan.client.additions;

import net.thailan.client.ThaiFoodClient;
import net.thailan.client.utils.PacketHelper;
import net.thailan.client.triggers.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.HorseEntity;

public class EntityControl extends Hack implements Tickable {

    private MinecraftClient client;

    public EntityControl(MinecraftClient client) {
        super("EntityControl");
        this.client = client;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ThaiFoodClient.LOGGER.warn("EntityControl does not work on servers!");
    }

    public void tick() {
        if (!isEnabled()) return;

        if (client.player.hasVehicle()) {
            Entity entity = client.player.getVehicle();

            double speed = 3;
            if (entity instanceof HorseEntity) {
                LivingEntity le = entity.getControllingPassenger();
                if (le != null) ThaiFoodClient.LOGGER.info("Controllng entity " + le);
            }

            PacketHelper.sendVehicleMovePacket(entity);
        }
    }

}
