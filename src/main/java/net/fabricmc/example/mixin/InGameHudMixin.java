package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.Renderer;
import net.fabricmc.example.additions.Hack;
import net.fabricmc.example.additions.KillAura;
import net.fabricmc.example.additions.Xray;
import net.fabricmc.example.utils.WorldUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Iterator;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow public abstract TextRenderer getTextRenderer();

    @Shadow private int scaledHeight;

    @Shadow protected abstract void renderHotbarItem(DrawContext context, int x, int y, float f, PlayerEntity player, ItemStack stack, int seed);

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow private int scaledWidth;

//    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/PlayerListHud;render(Lnet/minecraft/client/gui/DrawContext;ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"), index = 3)
//    private ScoreboardObjective setScoreboardObject(@Nullable ScoreboardObjective objective) {
//        if (objective != null) {
//            objective.setRenderType(ScoreboardCriterion.RenderType.HEARTS);
//            return objective;
//        } else {
//            return new ScoreboardObjective(ExampleMod.getInstance().client.world.getScoreboard(), "Hearts", ScoreboardCriterion.HEALTH, Text.literal("Hearts"), ScoreboardCriterion.RenderType.HEARTS);
//        }
//    }

    @Inject(at = @At("TAIL"), method = "render")
    private void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (ExampleMod.playerCoordinateDisplay.isEnabled()) {
            context.drawTextWithShadow(getTextRenderer(), ExampleMod.playerCoordinateDisplay.getCoordinateString(), 3, scaledHeight - 10, 0xFFFFFF);
        }
        if (ExampleMod.deathCoordinateDisplay.isEnabled() && ExampleMod.deathCoordinateDisplay.hasDied()) {
            context.drawTextWithShadow(getTextRenderer(), ExampleMod.deathCoordinateDisplay.getDeathString(), 3, scaledHeight - 20, 0xFF0000);
        }
        if (ExampleMod.spawnCoordinateDisplay.isEnabled()) {
            context.drawTextWithShadow(getTextRenderer(), ExampleMod.spawnCoordinateDisplay.getCoordinateString(), 3, scaledHeight - 30, 0x00FF00);
        }
        if (ExampleMod.chunkTracking.isEnabled()) {
            context.drawTextWithShadow(getTextRenderer(), "Chunk age: " + ExampleMod.chunkTracking.playerChunkAge(), 0, 0, 0xFFFFFF);
        }
        int i = 0;
        if (ExampleMod.hacksOverlay.isEnabled()) {
            context.drawTextWithShadow(getTextRenderer(), "TʜᴀɪFᴏᴏᴅ Cʟɪᴇɴᴛ", 3, 3, 0xFFFFF);
            for (Hack hack : ExampleMod.getInstance().additionManager.getAdditions()) {
                if (hack.isEnabled()) {
                    if (hack.getName().equalsIgnoreCase("armourhud") ||
                            hack.getName().equalsIgnoreCase("detectplayers") ||
                            hack.getName().equalsIgnoreCase("playercoordinatedisplay") ||
                            hack.getName().equalsIgnoreCase("teleport") ||
                            hack.getName().equalsIgnoreCase("hacksoverlay")) continue;
                    i++;
                    if (hack instanceof KillAura || hack instanceof Xray) {
                        context.drawTextWithShadow(getTextRenderer(), hack.toString(), 3, i * 10 + 13, 0xFFFFF);
                    } else {
                        context.drawTextWithShadow(getTextRenderer(), hack.getName(), 3, i * 10 + 13, 0xFFFFF);
                    }

                }
            }
            i++;
        }
        if (ExampleMod.tracers.isDetectingPlayers()) {
            context.drawTextWithShadow(getTextRenderer(), "Nearby Players:", 3, i * 10 + (ExampleMod.hacksOverlay.isEnabled() ? 13 : 3), 0xFFFFFF);
            ArrayList<Text> players = WorldUtil.getNearbyPlayers();
            for (int j = 0; j < players.size(); j++) {
                i++;
                context.drawTextWithShadow(getTextRenderer(), players.get(j), 3, i * 10 + 13, 0xFFFFFF);
            }
        }

    }

    @Inject(at = @At("HEAD"), method = "renderPortalOverlay", cancellable = true)
    private void renderPortalOverlay(DrawContext context, float nauseaStrength, CallbackInfo ci) {
        if (ExampleMod.betterPortal.isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "renderHotbar")
    private void renderHotbar(float tickDelta, DrawContext context, CallbackInfo ci) {
        if (ExampleMod.armourHUD.isEnabled()) {
            PlayerEntity playerEntity = getCameraPlayer();
            if (playerEntity != null) {
                int l = 1;

                int m = 0;
                int i = scaledWidth / 2;
                int n;
                int o;

                for (Iterator<ItemStack> armour = playerEntity.getArmorItems().iterator(); armour.hasNext(); ++m) {
                    n = i + 90 + m * 15 + 2;
                    o = this.scaledHeight - 16 - 3;
                    renderHotbarItem(context, n, o, tickDelta, playerEntity, armour.next(), l++);
                }
            }
        }
    }

}
