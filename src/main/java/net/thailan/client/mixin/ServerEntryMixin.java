package net.thailan.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerServerListWidget.ServerEntry.class)
public class ServerEntryMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow @Final private ServerInfo server;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I", ordinal = 0))
    private void drawServerType(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
        int width = this.client.textRenderer.getWidth(this.server.name);
        context.drawText(this.client.textRenderer, Text.literal(", ").append(this.server.version).append(Text.literal(" " + this.server.ping).formatted(Formatting.YELLOW)), x + 32 + 3 + width, y + 1, 0xFFFFFF, false);
    }

}
