package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(at = @At("TAIL"), method = "onChatMessage")
    private void onChatMessage(CallbackInfo ci) {
        PositionedSoundInstance sound = new PositionedSoundInstance(SoundEvents.BLOCK_LAVA_POP, SoundCategory.PLAYERS, 1f, 1f, Random.create(), ExampleMod.getInstance().client.player.getBlockPos());
        ExampleMod.getInstance().client.getSoundManager().play(sound);
    }

}
