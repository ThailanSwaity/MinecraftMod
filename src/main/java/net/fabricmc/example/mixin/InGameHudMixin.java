package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.Renderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
        if (ExampleMod.detectPlayers.isEnabled()) {
            context.drawTextWithShadow(getTextRenderer(), "Nearby Players:", 3, 0, 0xFFFFFF);
            ArrayList<Text> players = ExampleMod.detectPlayers.getNearbyPlayers();
            for (int i = 0; i < players.size(); i++) {
                context.drawTextWithShadow(getTextRenderer(), players.get(i), 3, i * 13 + 13, 0xFFFFFF);
            }
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
