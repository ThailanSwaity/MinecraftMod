package net.fabricmc.example;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.nio.Buffer;

public class Renderer {

    public static void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, float width, Colour colour) {

        setup();

        MatrixStack matrices = matrixForm(x1, y1, z1);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        // Line
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
        RenderSystem.lineWidth(width);

        buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);
        vertexLine(matrices, buffer, 0, 0, 0, (float) (x2 - x1), (float) (y2 - y1), (float) (z2 - z1), colour);
        tessellator.draw();

        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        cleanup();
    }

    public static void drawBoxOutline(BlockPos blockPos, Colour colour, float lineWidth) {
        drawBoxOutline(new Box(blockPos), colour, lineWidth);
    }

    public static void drawBoxOutline(Box box, Colour colour, float lineWidth) {
        setup();

        MatrixStack matrices = matrixForm(box.minX, box.minY, box.minZ);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        // Outline
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
        RenderSystem.lineWidth(lineWidth);

        buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);
        vertexBoxLines(matrices, buffer, moveToZero(box), colour);
        tessellator.draw();

        RenderSystem.enableCull();

        cleanup();
    }

    public static void vertexLine(MatrixStack matrices, VertexConsumer vertexConsumer, float x1, float y1, float z1, float x2, float y2, float z2, Colour colour) {
        Matrix4f model = matrices.peek().getPositionMatrix();
        Matrix3f normal = matrices.peek().getNormalMatrix();

        Vector3f normalVec = getNormal(x1, y1, z1, x2, y2, z2);

        vertexConsumer.vertex(model, x1, y1, z1).color(colour.getR(), colour.getG(), colour.getB(), 1.0F).normal(normal, normalVec.x(), normalVec.y(), normalVec.z()).next();
        vertexConsumer.vertex(model, x2, y2, z2).color(colour.getR(), colour.getG(), colour.getB(), 1.0F).normal(normal, normalVec.x(), normalVec.y(), normalVec.z()).next();
    }

    public static void vertexBoxLines(MatrixStack matrices, VertexConsumer vertexConsumer, Box box, Colour colour) {
        float x1 = (float) box.minX;
        float y1 = (float) box.minY;
        float z1 = (float) box.minZ;
        float x2 = (float) box.maxX;
        float y2 = (float) box.maxY;
        float z2 = (float) box.maxZ;

        // Bottom
        vertexLine(matrices, vertexConsumer, x1, y1, z1, x2, y1, z1, colour);
        vertexLine(matrices, vertexConsumer, x2, y1, z1, x2, y1, z2, colour);
        vertexLine(matrices, vertexConsumer, x2, y1, z2, x1, y1, z2, colour);
        vertexLine(matrices, vertexConsumer, x1, y1, z2, x1, y1, z1, colour);

        // West
        vertexLine(matrices, vertexConsumer, x1, y1, z2, x1, y2, z2, colour);
        vertexLine(matrices, vertexConsumer, x1, y1, z1, x1, y2, z1, colour);

        // East
        vertexLine(matrices, vertexConsumer, x2, y1, z2, x2, y2, z2, colour);
        vertexLine(matrices, vertexConsumer, x2, y1, z1, x2, y2, z1, colour);

        // Top
        vertexLine(matrices, vertexConsumer, x1, y2, z1, x2, y2, z1, colour);
        vertexLine(matrices, vertexConsumer, x2, y2, z1, x2, y2, z2, colour);
        vertexLine(matrices, vertexConsumer, x2, y2, z2, x1, y2, z2, colour);
        vertexLine(matrices, vertexConsumer, x1, y2, z2, x1, y2, z1, colour);
    }

    public static Vector3f getNormal(float x1, float y1, float z1, float x2, float y2, float z2) {
        float xNormal = x2 - x1;
        float yNormal = y2 - y1;
        float zNormal = z2 - z1;
        float normalSqrt = MathHelper.sqrt(xNormal * xNormal + yNormal * yNormal + zNormal * zNormal);

        return new Vector3f(xNormal / normalSqrt, yNormal / normalSqrt, zNormal / normalSqrt);
    }

    public static void setup() {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    public static void cleanup() {
        RenderSystem.disableBlend();
    }

    public static MatrixStack matrixForm(double x, double y, double z) {
        MatrixStack matrices = new MatrixStack();

        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));

        matrices.translate(x - camera.getPos().x, y - camera.getPos().y, z - camera.getPos().z);

        return matrices;
    }

    public static Vec3d getMinVec(Box box) {
        return new Vec3d(box.minX, box.minY, box.minZ);
    }

    public static Box moveToZero(Box box) {
        return box.offset(getMinVec(box).negate());
    }

}
