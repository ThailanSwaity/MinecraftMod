package net.fabricmc.example.mixin;

import net.fabricmc.example.Colour;
import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.Renderer;
import net.fabricmc.example.WorldUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow public abstract void drawEntityOutlinesFramebuffer();

    @Shadow @Final private BufferBuilderStorage bufferBuilders;

    @Shadow protected abstract void drawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double cameraX, double cameraY, double cameraZ, BlockPos pos, BlockState state);

    @Shadow @Nullable private ClientWorld world;

    @Shadow protected abstract void checkEmpty(MatrixStack matrices);

    @Inject(at = @At("HEAD"), method = "renderWeather", cancellable = true)
    private void renderWeather(CallbackInfo ci) {
        if (ExampleMod.noWeather.isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "render")
    private void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        Vec3d viewVec = camera.getFocusedEntity().getRotationVec(0.5f);
        Vec3d cursorPosition = camera.getPos().add(viewVec);
        if (ExampleMod.detectPlayers.isEnabled()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof PlayerEntity && entity != camera.getFocusedEntity()) {
                    Renderer.drawLine(cursorPosition.getX(), cursorPosition.getY(), cursorPosition.getZ(), entity.getX(), entity.getY(), entity.getZ(), 1f, Colour.RED);
                }
            }
        }
        if (ExampleMod.chestTracers.isEnabled()) {
            for (BlockEntity blockEntity : WorldUtil.getBlockEntities()) {
                if (blockEntity instanceof ChestBlockEntity) {
                    BlockPos pos = blockEntity.getPos();
                    Renderer.drawLine(cursorPosition.getX(), cursorPosition.getY(), cursorPosition.getZ(), pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 1f, Colour.GREEN);
                }
            }
        }

//        checkEmpty(matrices);
//        Profiler profiler = world.getProfiler();
//        profiler.swap("outline");
//        Vec3d cameraEntityPos = camera.getPos();
//        BlockPos steppingPos = camera.getFocusedEntity().getSteppingPos();
//        BlockState blockState = world.getBlockState(steppingPos);
//        double d = cameraEntityPos.getX();
//        double e = cameraEntityPos.getY();
//        double f = cameraEntityPos.getZ();
//        VertexConsumerProvider.Immediate immediate = this.bufferBuilders.getEntityVertexConsumers();
//        VertexConsumer vertexConsumer = immediate.getBuffer(RenderLayer.getLines());
//        drawBlockOutline(matrices, vertexConsumer, camera.getFocusedEntity(), d, e, f, steppingPos, blockState);
    }

}
