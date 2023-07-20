package net.thailan.client.utils;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.List;

public class WorldUtil {

    protected static MinecraftClient client = MinecraftClient.getInstance();

    public static List<WorldChunk> getLoadedChunks() {
        List<WorldChunk> chunks = new ArrayList<>();

        int viewDist = client.options.getViewDistance().getValue();

        for (int x = -viewDist; x <= viewDist; x++) {
            for (int z = -viewDist; z <= viewDist; z++) {
                WorldChunk chunk = client.world.getChunkManager().getWorldChunk((int)client.player.getX() / 16 + x, (int)client.player.getZ() / 16 + z);

                if (chunk != null) {
                    chunks.add(chunk);
                }
            }
        }
        return chunks;
    }

    public static List<BlockEntity> getBlockEntities() {
        List<BlockEntity> list = new ArrayList<>();
        for (WorldChunk chunk : getLoadedChunks()) {
            list.addAll(chunk.getBlockEntities().values());
        }
        return list;
    }

    public static boolean doesBoxTouchBlock(Box box, Block block) {
        for (int x = (int)Math.floor(box.minX); x < Math.ceil(box.maxX); x++) {
            for (int y = (int)Math.floor(box.minY); y < Math.ceil(box.maxY); y++) {
                for (int z = (int)Math.floor(box.minZ); z < Math.ceil(box.maxZ); z++) {
                    if (client.world.getBlockState(new BlockPos(x, y, z)).getBlock() == block) return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<Text> getNearbyPlayers() {
        ArrayList<Text> players = new ArrayList<>();
        for (Entity entity : client.world.getEntities()) {
            if (entity instanceof PlayerEntity && entity != client.player) {
                players.add(Text.literal(entity.getName().getString() + ": " + getDistance(entity)));
            }
        }
        return players;
    }

    private static String getDistance(Entity entity) {
        return String.format("%.2f", client.player.distanceTo(entity));
    }


}
