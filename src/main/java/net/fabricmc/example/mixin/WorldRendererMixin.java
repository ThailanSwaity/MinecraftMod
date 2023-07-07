package net.fabricmc.example.mixin;

import net.fabricmc.example.*;
import net.fabricmc.example.additions.Hack;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
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

    @Inject(at = @At("HEAD"), method = "renderWeather", cancellable = true)
    private void renderWeather(CallbackInfo ci) {
        if (ExampleMod.noWeather.isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "hasBlindnessOrDarkness", cancellable = true)
    private void hasBlindnessOrDarkness(Camera camera, CallbackInfoReturnable<Boolean> cir) {
        if (ExampleMod.glasses.isEnabled()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "render")
    private void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        Vec3d viewVec = camera.getFocusedEntity().getRotationVec(0.5f);
        Vec3d cursorPosition = camera.getPos().add(viewVec);
        if (ExampleMod.detectPlayers.isEnabled()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof PlayerEntity && entity != camera.getFocusedEntity()) {
                    if (ExampleMod.friendList.isFriend(entity.getName().getString())) {
                        Renderer.drawLine(cursorPosition.getX(), cursorPosition.getY(), cursorPosition.getZ(), entity.getX(), entity.getY(), entity.getZ(), 1f, Colour.BLUE);
                    } else {
                        Renderer.drawLine(cursorPosition.getX(), cursorPosition.getY(), cursorPosition.getZ(), entity.getX(), entity.getY(), entity.getZ(), 1f, Colour.RED);
                    }
                }
            }
        }

        for (BlockEntity blockEntity : WorldUtil.getBlockEntities()) {
            for (Hack blockEntityDetector : ExampleMod.getInstance().additionManager.getBlockEntityDetectors()) {
                if (!blockEntityDetector.isEnabled()) continue;
                if (((BlockEntityDetector)blockEntityDetector).isBlockEntity(blockEntity)) ((BlockEntityDetector)blockEntityDetector).blockEntityResponse(camera, blockEntity);
            }
        }

        for (WorldChunk chunk : WorldUtil.getLoadedChunks()) {
            for (Hack blockDetector : ExampleMod.getInstance().additionManager.getBlockDetectors()) {
                if (!blockDetector.isEnabled()) continue;
                chunk.forEachBlockMatchingPredicate((blockState) -> ((BlockDetector)blockDetector).isBlock(blockState), ((BlockDetector)blockDetector).blockResponse(camera));
            }
        }

        if (ExampleMod.waypoints.isEnabled()) {
            MinecraftClient client = ExampleMod.getInstance().client;
            for (Waypoint waypoint : ExampleMod.waypointList.getWaypoints()) {
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
