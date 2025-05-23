package net.thailan.client.mixin;

import net.thailan.client.additions.Hack;
import net.thailan.client.triggers.BlockDetector;
import net.thailan.client.triggers.BlockEntityDetector;
import net.thailan.client.triggers.EntityDetector;
import net.thailan.client.utils.render.EntityOutline;
import net.thailan.client.utils.render.Renderer;
import net.thailan.client.utils.WorldUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
import net.thailan.client.ThaiFoodClient;
import net.thailan.client.Waypoint;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow @Nullable private ClientWorld world;

    @Shadow private @Nullable Framebuffer entityOutlinesFramebuffer;

    @Inject(at = @At("HEAD"), method = "renderWeather", cancellable = true)
    private void renderWeather(CallbackInfo ci) {
        if (ThaiFoodClient.noWeather.isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "hasBlindnessOrDarkness", cancellable = true)
    private void hasBlindnessOrDarkness(Camera camera, CallbackInfoReturnable<Boolean> cir) {
        if (ThaiFoodClient.glasses.isEnabled()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "drawEntityOutlinesFramebuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;draw(IIZ)V"))
    private void preEntityOutlineDraw(CallbackInfo ci) {
        EntityOutline.setOutlineColour(ThaiFoodClient.esp.getPlayerOutlineColour());
    }

    @Inject(method = "drawEntityOutlinesFramebuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;draw(IIZ)V", shift = At.Shift.AFTER))
    private void postEntityOutlineDraw(CallbackInfo ci) {
        EntityOutline.clear();
    }

    @Inject(at = @At("TAIL"), method = "render")
    private void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        Vec3d viewVec = camera.getFocusedEntity().getRotationVec(0.5f);
        Vec3d cursorPosition = camera.getPos().add(viewVec);

        for (Entity entity : world.getEntities()) {
            for (Hack entityDetector : ThaiFoodClient.getInstance().additionManager.getEntityDetectors()) {
                if (!entityDetector.isEnabled()) continue;
                if (((EntityDetector)entityDetector).isEntity(entity)) ((EntityDetector)entityDetector).entityResponse(entity, camera);
            }
        }

        for (BlockEntity blockEntity : WorldUtil.getBlockEntities()) {
            for (Hack blockEntityDetector : ThaiFoodClient.getInstance().additionManager.getBlockEntityDetectors()) {
                if (!blockEntityDetector.isEnabled()) continue;
                if (((BlockEntityDetector)blockEntityDetector).isBlockEntity(blockEntity)) ((BlockEntityDetector)blockEntityDetector).blockEntityResponse(camera, blockEntity);
            }
        }

        for (WorldChunk chunk : WorldUtil.getLoadedChunks()) {
            for (Hack blockDetector : ThaiFoodClient.getInstance().additionManager.getBlockDetectors()) {
                if (!blockDetector.isEnabled()) continue;
                chunk.forEachBlockMatchingPredicate((blockState) -> ((BlockDetector)blockDetector).isBlock(blockState), ((BlockDetector)blockDetector).blockResponse(camera));
            }
        }

        if (ThaiFoodClient.waypoints.isEnabled()) {
            MinecraftClient client = ThaiFoodClient.getInstance().client;
            for (Waypoint waypoint : ThaiFoodClient.waypointList.getWaypoints()) {
                if (waypoint.getDimension() == null) {
                    Renderer.drawLine(cursorPosition.getX(), cursorPosition.getY(), cursorPosition.getZ(), waypoint.getPosition().getX(), waypoint.getPosition().getY(), waypoint.getPosition().getZ(), 1f, waypoint.getColour());
                } else {
                    if (client.world.getDimensionKey() == waypoint.getDimension()) {
                        Renderer.drawLine(cursorPosition.getX(), cursorPosition.getY(), cursorPosition.getZ(), waypoint.getPosition().getX(), waypoint.getPosition().getY(), waypoint.getPosition().getZ(), 1f, waypoint.getColour());
                    }
                }
            }
        }
    }

}
