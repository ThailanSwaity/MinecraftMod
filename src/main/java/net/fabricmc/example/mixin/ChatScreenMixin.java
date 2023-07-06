package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.additions.FriendList;
import net.fabricmc.example.additions.Hack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {

    @Shadow public abstract String normalize(String chatText);

    @Shadow protected TextFieldWidget chatField;

    @Inject(at = @At("HEAD"), method = "sendMessage", cancellable = true)
    private void sendMessage(String chatText, boolean addToHistory, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = ExampleMod.getInstance().client;

        boolean isCommand = ExampleMod.commandList.process(chatText);
        if (isCommand) {
            cir.setReturnValue(true);
            return;
        } else if (chatText.charAt(0) == '.') {
            client.player.sendMessage(Text.literal("Command not recognized.").formatted(Formatting.RED));
            cir.setReturnValue(true);
            return;
        }

        if (ExampleMod.sarcasm.isEnabled()) {
            String tChatText = "";
            double r;
            for (int i = 0; i < chatText.length(); i++) {
                r = Math.random();
                if (r <= 0.5D) tChatText += Character.toUpperCase(chatText.charAt(i));
                else tChatText += chatText.charAt(i);
            }
            chatText = tChatText;
            ExampleMod.LOGGER.info(chatText);
            ExampleMod.LOGGER.info(tChatText);
        }

        if (ExampleMod.chatWatermark.isEnabled()) chatText += ExampleMod.chatWatermark.getWaterMark();
        chatText = normalize(chatText);

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
