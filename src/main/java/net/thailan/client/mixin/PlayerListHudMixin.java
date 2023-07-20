package net.thailan.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract List<PlayerListEntry> collectPlayerEntries();

    @Inject(method = "render", at = @At("TAIL"))
    private void renderPing(DrawContext context, int scaledWindowWidth, Scoreboard scoreboard, ScoreboardObjective objective, CallbackInfo ci) {
        List<PlayerListEntry> list = collectPlayerEntries();
        context.drawTextWithShadow(client.textRenderer, Text.literal("Test"), 0, 0, 0xFFFFFF);
        //context.drawTextWithShadow(client.textRenderer, Text.literal(client.getNetworkHandler().getConnection().))
    }

    @Inject(method = "applyGameModeFormatting", at = @At("HEAD"), cancellable = true)
    private void applyGameModeFormatting(PlayerListEntry entry, MutableText name, CallbackInfoReturnable<Text> cir) {
        cir.setReturnValue(entry.getGameMode() == GameMode.SPECTATOR ? name.formatted(Formatting.ITALIC).append(Text.literal(", " + entry.getLatency()).formatted(Formatting.YELLOW)) : name.append(Text.literal(", " + entry.getLatency()).formatted(Formatting.YELLOW)));
    }


}
