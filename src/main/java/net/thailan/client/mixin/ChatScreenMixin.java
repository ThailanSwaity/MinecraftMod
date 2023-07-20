package net.thailan.client.mixin;

import net.thailan.client.utils.command.CommandHelper;
import net.thailan.client.ThaiFoodClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {

    @Shadow public abstract String normalize(String chatText);

    @Shadow protected TextFieldWidget chatField;

    @Inject(at = @At("HEAD"), method = "sendMessage", cancellable = true)
    private void sendMessage(String chatText, boolean addToHistory, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = ThaiFoodClient.getInstance().client;

        boolean isCommand = CommandHelper.process(chatText);
        if (isCommand) {
            cir.setReturnValue(true);
            return;
        } else if (chatText.charAt(0) == '.') {
            client.player.sendMessage(Text.literal("Command not recognized.").formatted(Formatting.RED));
            cir.setReturnValue(true);
            return;
        }

        if (ThaiFoodClient.sarcasm.isEnabled()) {
            String tChatText = "";
            double r;
            for (int i = 0; i < chatText.length(); i++) {
                r = Math.random();
                if (r <= 0.5D) tChatText += Character.toUpperCase(chatText.charAt(i));
                else tChatText += chatText.charAt(i);
            }
            chatText = tChatText;
            ThaiFoodClient.LOGGER.info(chatText);
            ThaiFoodClient.LOGGER.info(tChatText);
        }

        if (ThaiFoodClient.chatWatermark.isEnabled()) chatText += ThaiFoodClient.chatWatermark.getWaterMark();
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
