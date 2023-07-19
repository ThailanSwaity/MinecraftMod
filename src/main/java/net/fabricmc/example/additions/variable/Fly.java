package net.fabricmc.example.additions.variable;

import net.fabricmc.example.PacketHelper;
import net.fabricmc.example.Tickable;
import net.fabricmc.example.additions.variable.VariableHack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;

public class Fly extends VariableHack implements Tickable {

    private MinecraftClient client;
    private ClientPlayerEntity player;
    private int ticksFloating = 0;
    private float flySpeed = 0.05f;

    public Fly(MinecraftClient client) {
        super("Fly");
        this.client = client;
        this.optionsScreen.addOptionSlider("flySpeed", Text.literal(String.format("%.2f", flySpeed)),
                (sliderWidget, value) -> sliderWidget.setMessage(Text.literal(String.format("%.2f", value))),
                (sliderWidget, value) -> setFlySpeed((float)value), 0, 0, 115, 20, flySpeed
        );
    }

    public void tick() {
        if (!isEnabled() && !client.player.isCreative()) {
            client.player.getAbilities().allowFlying = false;
            return;
        }

        client.player.getAbilities().allowFlying = true;
        client.player.getAbilities().setFlySpeed(flySpeed);
        if (client.player.getVelocity().getY() <= -0.05 && client.player.getAbilities().flying) {
            PacketHelper.sendOnGroundOnly(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
        if (++ticksFloating > 10 && client.player.getSteppingBlockState().isAir() && !client.player.isOnGround()) {
            PacketHelper.sendPosition(client.player.getPos().subtract(0.0, 0.0433D, 0.0));

            ticksFloating = 0;
        }
    }

    @Override
    protected void onEnable() {
        client.player.getAbilities().allowFlying = true;
    }

    @Override
    protected void onDisable() {
        client.player.getAbilities().allowFlying = false;
    }

    public void setFlySpeed(float flySpeed) {
        this.flySpeed = flySpeed;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

}
