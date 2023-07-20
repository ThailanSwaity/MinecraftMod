package net.thailan.client.mixin;

import net.thailan.client.utils.render.EntityOutline;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Framebuffer.class)
public class FrameBufferMixin {
    @Redirect(method = "drawInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;color(IIII)Lnet/minecraft/client/render/VertexConsumer;"))
    private VertexConsumer color1(VertexConsumer instance, int r, int g, int b, int a) {
        return instance.color(EntityOutline.getOutlineColour().getIntR(), EntityOutline.getOutlineColour().getIntG(), EntityOutline.getOutlineColour().getIntB(), 255);
    }


}
