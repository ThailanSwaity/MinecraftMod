package net.thailan.client.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Renderer {

    public static void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, float width, float alpha, Colour colour) {

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
        vertexLine(matrices, buffer, 0, 0, 0, (float) (x2 - x1), (float) (y2 - y1), (float) (z2 - z1), alpha, colour);
        tessellator.draw();

        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        cleanup();
    }

    public static void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, float width, Colour colour) {
        drawLine(x1, y1, z1, x2, y2, z2, width, 1f, colour);
    }



    public static void drawBoxOutline(BlockPos blockPos, Colour colour, float lineWidth) {
        drawBoxOutline(new Box(blockPos), colour, lineWidth, 1.0F);
    }

    public static void drawBoxOutline(BlockPos blockPos, Colour colour, float lineWidth, float alpha) {
        drawBoxOutline(new Box(blockPos), colour, lineWidth, alpha);
    }

    public static void drawBoxOutline(Box box, Colour colour, float lineWidth, float alpha) {
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
        vertexBoxLines(matrices, buffer, moveToZero(box), colour, alpha);
        tessellator.draw();

        RenderSystem.enableCull();

        cleanup();
    }

    public static void drawBoxBoth(BlockPos blockPos, Colour colour, float lineWidth, float alpha) {
        drawBoxFill(blockPos, colour);
        drawBoxOutline(blockPos, colour, lineWidth, alpha);
    }

    public static void drawBoxFill(BlockPos blockPos, Colour colour) {
        drawBoxFill(new Box(blockPos), colour);
    }

    public static void drawBoxFill(Box box, Colour colour) {
        setup();

        MatrixStack matrices = matrixForm(box.minX, box.minY, box.minZ);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        vertexBoxQuads(matrices, buffer, moveToZero(box), colour);
        tessellator.draw();

        cleanup();
    }

    public static void vertexBoxQuads(MatrixStack matrices, VertexConsumer vertexConsumer, Box box, Colour colour) {
        float x1 = (float) box.minX;
        float y1 = (float) box.minY;
        float z1 = (float) box.minZ;
        float x2 = (float) box.maxX;
        float y2 = (float) box.maxY;
        float z2 = (float) box.maxZ;

        vertexQuad(matrices, vertexConsumer, x1, y1, z1, x2, y1, z1, x2, y1, z2, x1, y1, z2, colour);
        vertexQuad(matrices, vertexConsumer, x1, y1, z2, x1, y2, z2, x1, y2, z1, x1, y1, z1, colour);
        vertexQuad(matrices, vertexConsumer, x2, y1, z1, x2, y2, z1, x2, y2, z2, x2, y1, z2, colour);
        vertexQuad(matrices, vertexConsumer, x1, y1, z1, x1, y2, z1, x2, y2, z1, x2, y1, z1, colour);
        vertexQuad(matrices, vertexConsumer, x2, y1, z2, x2, y2, z2, x1, y2, z2, x1, y1, z2, colour);
        vertexQuad(matrices, vertexConsumer, x1, y2, z2, x2, y2, z2, x2, y2, z1, x1, y2, z1, colour);
    }

    public static void vertexQuad(MatrixStack matrices, VertexConsumer vertexConsumer, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, Colour colour) {
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x1, y1, z1).color(colour.getR(), colour.getG(), colour.getB(), 0.2F).next();
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x2, y2, z2).color(colour.getR(), colour.getG(), colour.getB(), 0.2F).next();
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x3, y3, z3).color(colour.getR(), colour.getG(), colour.getB(), 0.2F).next();
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x4, y4, z4).color(colour.getR(), colour.getG(), colour.getB(), 0.2F).next();

        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x4, y4, z4).color(colour.getR(), colour.getG(), colour.getB(), 0.2F).next();
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x3, y3, z3).color(colour.getR(), colour.getG(), colour.getB(), 0.2F).next();
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x2, y2, z2).color(colour.getR(), colour.getG(), colour.getB(), 0.2F).next();
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x1, y1, z1).color(colour.getR(), colour.getG(), colour.getB(), 0.2F).next();
    }

    public static void vertexLine(MatrixStack matrices, VertexConsumer vertexConsumer, float x1, float y1, float z1, float x2, float y2, float z2, float alpha, Colour colour) {
        Matrix4f model = matrices.peek().getPositionMatrix();
        Matrix3f normal = matrices.peek().getNormalMatrix();

        Vector3f normalVec = getNormal(x1, y1, z1, x2, y2, z2);

        vertexConsumer.vertex(model, x1, y1, z1).color(colour.getR(), colour.getG(), colour.getB(), alpha).normal(normal, normalVec.x(), normalVec.y(), normalVec.z()).next();
        vertexConsumer.vertex(model, x2, y2, z2).color(colour.getR(), colour.getG(), colour.getB(), alpha).normal(normal, normalVec.x(), normalVec.y(), normalVec.z()).next();
    }

    public static void vertexBoxLines(MatrixStack matrices, VertexConsumer vertexConsumer, Box box, Colour colour, float alpha) {
        float x1 = (float) box.minX;
        float y1 = (float) box.minY;
        float z1 = (float) box.minZ;
        float x2 = (float) box.maxX;
        float y2 = (float) box.maxY;
        float z2 = (float) box.maxZ;

        // Bottom
        vertexLine(matrices, vertexConsumer, x1, y1, z1, x2, y1, z1, alpha, colour);
        vertexLine(matrices, vertexConsumer, x2, y1, z1, x2, y1, z2, alpha, colour);
        vertexLine(matrices, vertexConsumer, x2, y1, z2, x1, y1, z2, alpha, colour);
        vertexLine(matrices, vertexConsumer, x1, y1, z2, x1, y1, z1, alpha, colour);

        // West
        vertexLine(matrices, vertexConsumer, x1, y1, z2, x1, y2, z2, alpha, colour);
        vertexLine(matrices, vertexConsumer, x1, y1, z1, x1, y2, z1, alpha, colour);

        // East
        vertexLine(matrices, vertexConsumer, x2, y1, z2, x2, y2, z2, alpha, colour);
        vertexLine(matrices, vertexConsumer, x2, y1, z1, x2, y2, z1, alpha, colour);

        // Top
        vertexLine(matrices, vertexConsumer, x1, y2, z1, x2, y2, z1, alpha, colour);
        vertexLine(matrices, vertexConsumer, x2, y2, z1, x2, y2, z2, alpha, colour);
        vertexLine(matrices, vertexConsumer, x2, y2, z2, x1, y2, z2, alpha, colour);
        vertexLine(matrices, vertexConsumer, x1, y2, z2, x1, y2, z1, alpha, colour);
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
