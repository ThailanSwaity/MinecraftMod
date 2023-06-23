package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.additions.Hack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {

    @Shadow public abstract String normalize(String chatText);

    @Inject(at = @At("HEAD"), method = "sendMessage", cancellable = true)
    private void sendMessage(String chatText, boolean addToHistory, CallbackInfoReturnable<Boolean> cir) {
        if (ExampleMod.chatWatermark.isEnabled()) {
            MinecraftClient client = ExampleMod.getInstance().client;
            chatText = normalize(chatText + " | TʜᴀɪFᴏᴏᴅ Cʟɪᴇɴᴛ");
            if (chatText.isEmpty()) {
                cir.setReturnValue(true);
            } else {
                if (addToHistory) {
                    client.inGameHud.getChatHud().addToMessageHistory(chatText);

                }

                if (chatText.startsWith("/")) {
                    client.player.networkHandler.sendChatCommand(chatText.substring(1));
                } else {
                    client.player.networkHandler.sendChatMessage(chatText);
                }

                cir.setReturnValue(true);
            }
        }

    }

}
